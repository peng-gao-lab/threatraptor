package query.tbql.executor.handler;

import java.util.Collection;

public interface TBQLExecutorResultHandler {
	public void handleCount(int count);
	public void handleRow(String row);
	public void handleHeader(Collection<String> headers);
}
