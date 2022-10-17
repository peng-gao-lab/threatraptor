package query.tbql.executor.datetime;

import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import query.tbql.executor.handler.TBQLExecutorErrorHandler;

public class TBQLDateTimeParser {
	List<DateTimeFormatter> dateTimeFormats;
	TBQLExecutorErrorHandler errHandler;

	public TBQLDateTimeParser(TBQLExecutorErrorHandler errHandler) {
		this.errHandler = errHandler;
		dateTimeFormats = new ArrayList<DateTimeFormatter>();
		dateTimeFormats.add(DateTimeFormat.forPattern("MM/dd/yyyy HH:mm:ss"));
		dateTimeFormats.add(DateTimeFormat.forPattern("MM-dd-yyyy HH:mm:ss"));
		dateTimeFormats.add(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
		dateTimeFormats.add(DateTimeFormat.forPattern("MM/dd/yyyy HH:mm"));
		dateTimeFormats.add(DateTimeFormat.forPattern("MM-dd-yyyy HH:mm"));
		dateTimeFormats.add(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm"));
		dateTimeFormats.add(DateTimeFormat.forPattern("MM/dd/yyyy HH"));
		dateTimeFormats.add(DateTimeFormat.forPattern("MM-dd-yyyy HH"));
		dateTimeFormats.add(DateTimeFormat.forPattern("yyyy-MM-dd HH"));
		dateTimeFormats.add(DateTimeFormat.forPattern("MM/dd/yyyy"));
		dateTimeFormats.add(DateTimeFormat.forPattern("MM-dd-yyyy"));
		dateTimeFormats.add(DateTimeFormat.forPattern("yyyy-MM-dd"));
	}

	private DateTime tryToParse(String input, DateTimeFormatter format) {
		DateTime dt = null;
		try {
			dt = DateTime.parse(input, format);
		} catch (Exception e) {
		}
		return dt;
	}

	public DateTime multiParse(String input) {
		DateTime dt = null;
		for (DateTimeFormatter format : dateTimeFormats) {
			dt = tryToParse(input, format);
			if (dt != null)
				break;
		}
		return dt;
	}

	public TBQLDateTime parseAtTime(String dateTime) {
		// Convert to DateTime object
		DateTime dt = null;
		int dateTimeFormatIndex = -1;
		for (DateTimeFormatter format : dateTimeFormats) {
			dt = tryToParse(dateTime, format);
			if (dt != null) {
				dateTimeFormatIndex = dateTimeFormats.indexOf(format);
				break;
			}
		}

		// Check user input compliance
		if (dt == null) {
			errHandler.handleTimeFormatError("Undefined DateTime format.");
		}

		// Get the granularity of input
		String granularity = "";
		if (dateTimeFormatIndex < 3) {
			granularity = "second";
		} else if (dateTimeFormatIndex >= 3 && dateTimeFormatIndex < 6) {
			granularity = "minute";
		} else if (dateTimeFormatIndex >= 6 && dateTimeFormatIndex < 9) {
			granularity = "hour";
		} else {
			granularity = "day";
		}

		// Obtain startDateTimeUTC, endDateTimeUTC
		long startDateTimeUTC = dateTimeToMilliseconds(dt);
		long endDateTimeUTC = 0;
		switch (granularity) {
			case "second":
				endDateTimeUTC = dateTimeToMilliseconds(dt.plusSeconds(1)) - 1;
				break;
			case "minute":
				endDateTimeUTC = dateTimeToMilliseconds(dt.plusMinutes(1)) - 1;
				break;
			case "hour":
				endDateTimeUTC = dateTimeToMilliseconds(dt.plusHours(1)) - 1;
				break;
			case "day":
				endDateTimeUTC = dateTimeToMilliseconds(dt.plusDays(1)) - 1;
				break;
			default:
				errHandler.handleTimeFormatError("Undefined DateTime granularity.");
		}

		return new TBQLDateTime("at", startDateTimeUTC, endDateTimeUTC);
	}

	public TBQLDateTime parseFromToTime(String dateTime1, String dateTime2) {
		// Convert to DateTime object
		DateTime dt1 = multiParse(dateTime1);
		DateTime dt2 = multiParse(dateTime2);

		// Check user input compliance
		if (dt1 == null || dt2 == null) {
			errHandler.handleTimeFormatError("Undefined DateTime format.");
		}

		// Obtain startDateTimeUTC, endDateTimeUTC
		long startDateTimeUTC = dateTimeToMilliseconds(dt1);
		long endDateTimeUTC = dateTimeToMilliseconds(dt2);

		return new TBQLDateTime("fromto", startDateTimeUTC, endDateTimeUTC);
	}

	public TBQLDateTime parseBeforeTime(String dateTime) {
		// Convert to DateTime object
		DateTime dt = multiParse(dateTime);

		// Check user input compliance
		if (dt == null) {
			errHandler.handleTimeFormatError("Undefined DateTime format.");
		}

		// Obtain startDateTimeUTC, endDateTimeUTC
		long startDateTimeUTC = dateTimeToMilliseconds(dt.withTimeAtStartOfDay());
		long endDateTimeUTC = dateTimeToMilliseconds(dt);

		return new TBQLDateTime("before", startDateTimeUTC, endDateTimeUTC);
	}

	public TBQLDateTime parseAfterTime(String dateTime) {
		// Convert to DateTime object
		DateTime dt = multiParse(dateTime);

		// Check user input compliance
		if (dt == null) {
			errHandler.handleTimeFormatError("Undefined DateTime format.");
		}

		// Obtain startDateTimeUTC, endDateTimeUTC
		long startDateTimeUTC = dateTimeToMilliseconds(dt);
		long endDateTimeUTC = dateTimeToMilliseconds(dt.withTime(23, 59, 59, 999));

		return new TBQLDateTime("after", startDateTimeUTC, endDateTimeUTC);
	}

	public TBQLDateTime parseLastTime(float amount, String unit) {
		// Obtain the start time
		long numMilliseconds = durationInMilliseconds(amount, unit);
		DateTime currentTime = new DateTime();
		DateTime startTime = currentTime.minus(new Duration(numMilliseconds));
		if (startTime.isBefore(currentTime.withTimeAtStartOfDay())) {
			// Do not support across-day search for this BQL time window type
			startTime = currentTime.withTimeAtStartOfDay();
		}

		// Obtain startDateTimeUTC, endDateTimeUTC
		long startDateTimeUTC = dateTimeToMilliseconds(startTime);
		long endDateTimeUTC = dateTimeToMilliseconds(currentTime);

		return new TBQLDateTime("last", startDateTimeUTC, endDateTimeUTC);
	}

	public long dateTimeToMilliseconds(DateTime dateTime) {
		return dateTime.toDateTime(DateTimeZone.UTC).getMillis();
	}

	public DateTime millisecondsToDateTime(long milliseconds) {
		return new DateTime(milliseconds); // should use the local timezone
	}

	public String normalizeTimeUnit(String oriUnit) {
		String tmp = oriUnit.toLowerCase();
		String unit = "";
		if (tmp.charAt(tmp.length() - 1) == 's') {
			unit = tmp.substring(0, tmp.length() - 1);
		} else {
			unit = tmp;
		}

		return unit;
	}

	public long durationInMilliseconds(float amount, String unit) {
		long numMilliseconds = 0;
		switch (unit) {
			case "second":
				numMilliseconds = (long) (amount * 1000);
				break;
			case "minute":
				numMilliseconds = (long) (amount * 1000 * 60);
				break;
			case "hour":
				numMilliseconds = (long) (amount * 1000 * 60 * 60);
				break;
			case "day":
				numMilliseconds = (long) (amount * 1000 * 60 * 60 * 24);
				break;
			case "month":
				numMilliseconds = (long) (amount * 1000 * 60 * 60 * 24 * 30);
				break;
			case "year":
				numMilliseconds = (long) (amount * 1000 * 60 * 60 * 24 * 30 * 365);
				break;
			default:
				errHandler.handleTimeFormatError("Undefined DateTime granularity.");
		}

		return numMilliseconds;
	}
}
