package query.tbql.executor.handler;

import org.apache.commons.lang3.exception.ExceptionUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class WebResultHandler implements TBQLExecutorResultHandler, TBQLExecutorErrorHandler {
    private final List<String> headersList;
    private final List<String> rowsList;
    private String err;
    private long executionTime;

    public WebResultHandler() {
        headersList = new ArrayList<>();
        rowsList = new ArrayList<>();
    }

    public List<String> getHeaders() {
        return headersList;
    }

    public List<String> getRows() {
        return rowsList;
    }

    public String getErr() {
        return err;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }

    @Override
    public void handleCount(int count) {
        System.out.println(count);
    }

    @Override
    public void handleRow(String row) {
        rowsList.add(row);
    }

    @Override
    public void handleHeader(Collection<String> headers) {
        headersList.addAll(headers);
    }

    @Override
    public void handleLexerParserError(String errMsg) {
        err = errMsg;
    }

    @Override
    public void handleTimeFormatError(String errMsg) {
        err = errMsg;
    }

    @Override
    public void handleUndefinedError(String errMsg) {
        err = errMsg;
    }

    @Override
    public void handleIDError(String errMsg) {
        err = errMsg;
    }

    @Override
    public void handleOtherError(String errMsg) {
        err = errMsg;
    }

    @Override
    public void handleOtherError(String errMsg, Exception e) {
        err = errMsg;
        System.err.println(ExceptionUtils.getStackTrace(e));
    }
}
