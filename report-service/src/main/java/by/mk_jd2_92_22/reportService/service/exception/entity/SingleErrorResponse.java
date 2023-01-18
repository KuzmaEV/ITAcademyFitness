package by.mk_jd2_92_22.reportService.service.exception.entity;

public class SingleErrorResponse {

    private final String logref;
    private final String message;

    public SingleErrorResponse(String logref, String message) {
        this.logref = logref;
        this.message = message;
    }

    public String getLogref() {
        return logref;
    }

    public String getMessage() {
        return message;
    }
}
