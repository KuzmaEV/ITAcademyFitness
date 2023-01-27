package by.mk_jd2_92_22.auditService.service.exception.entity;

import java.util.List;

public class MultipleErrorResponse {

    private String logref;
    private List<ErrorForMultipleErrorResponse> errors;

    public MultipleErrorResponse() {
    }

    public MultipleErrorResponse(String logref, List<ErrorForMultipleErrorResponse> errors) {
        this.logref = logref;
        this.errors = errors;
    }

    public String getLogref() {
        return logref;
    }

    public List<ErrorForMultipleErrorResponse> getErrors() {
        return errors;
    }

    public void setLogref(String logref) {
        this.logref = logref;
    }

    public void setErrors(List<ErrorForMultipleErrorResponse> errors) {
        this.errors = errors;
    }
}


