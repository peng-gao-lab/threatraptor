package query.tbql.executor.datetime;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import com.google.gson.Gson;

public class TBQLDateTime {	
	private String timeWindowType;
	private Long startDateTimeUTC;
	private Long endDateTimeUTC;
	
	public TBQLDateTime(String timeWindowType, Long startDateTimeUTC, Long endDateTimeUTC) {
		this.timeWindowType = timeWindowType;
		this.startDateTimeUTC = startDateTimeUTC;
		this.endDateTimeUTC = endDateTimeUTC;
	}
	
	public TBQLDateTime(String timeWindowType, DateTime startDateTime, DateTime endDateTime) {
		this.timeWindowType = timeWindowType;
		this.startDateTimeUTC = startDateTime.toDateTime(DateTimeZone.UTC).getMillis();
		this.endDateTimeUTC = endDateTime.toDateTime(DateTimeZone.UTC).getMillis();
	}
	
	public String getTimeWindowType() {
		return timeWindowType;
	}
	
	public void setTimeWindowType(String timeWindowType) {
		this.timeWindowType = timeWindowType;
	}
	
	public Long getStartDateTimeUTC() {
		return startDateTimeUTC;
	}
	
	public void setStartDateTimeUTC(Long startDateTimeUTC) {
		this.startDateTimeUTC = startDateTimeUTC;
	}
	
	public Long getEndDateTimeUTC() {
		return endDateTimeUTC;
	}
	
	public void setEndDateTimeUTC(Long endDateTimeUTC) {
		this.endDateTimeUTC = endDateTimeUTC;
	}

	@Override
	public String toString() {
		return "BQLDateTime [timeWindowType=" + timeWindowType + ", startDateTimeUTC=" + startDateTimeUTC
				+ ", endDateTimeUTC=" + endDateTimeUTC + "]";
	}
	
	public String toJsonString() {
		return (new Gson()).toJson(this);
	}
	
	public static TBQLDateTime fromJsonString(String decodedString) {
		return (new Gson()).fromJson(decodedString, TBQLDateTime.class);
	}
}
