package by.mk_jd2_92_22.reportService.service.exception;


public class NotFoundException extends RuntimeException{

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
