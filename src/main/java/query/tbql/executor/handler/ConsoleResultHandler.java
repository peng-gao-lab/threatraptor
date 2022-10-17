package query.tbql.executor.handler;

import java.util.Collection;

public class ConsoleResultHandler implements TBQLExecutorResultHandler {

	@Override
	public void handleCount(int count) {
		System.out.println(count);
	}

	@Override
	public void handleRow(String row) {
		System.out.println(row);
	}

	@Override
	public void handleHeader(Collection<String> headers) {
		System.out.println(headers);
	}
}
