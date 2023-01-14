package by.mk_jd2_92_22.userSecurity.exception;

import by.mk_jd2_92_22.userSecurity.exception.entity.ErrorForMultipleErrorResponse;
import by.mk_jd2_92_22.userSecurity.exception.entity.MultipleErrorResponse;
import by.mk_jd2_92_22.userSecurity.exception.entity.SingleErrorResponse;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class HandlerAdviceController {

        @ExceptionHandler
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public SingleErrorResponse singleErrorBadRequest(IllegalStateException e) {
                return new SingleErrorResponse(
                        "error",
                        e.getMessage());
        }
        @ExceptionHandler
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public SingleErrorResponse jsonException(InvalidFormatException e) {
                return new SingleErrorResponse(
                        "error",
                        e.getMessage());
        }

        @ExceptionHandler/*({ProductNotFoundException.class})*/
        @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
        public SingleErrorResponse singleErrorServer(IllegalArgumentException e) {
                return new SingleErrorResponse(
                        "error",
                        e.getMessage());
        }

        @ExceptionHandler/*({ProductNotFoundException.class})*/
        @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
        public SingleErrorResponse singleErrorServer(RestClientException e) {
                return new SingleErrorResponse(
                        "error",
                        e.getMessage());
        }

        @ExceptionHandler
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public MultipleErrorResponse handleValidationExceptions(
                MethodArgumentNotValidException e) {

                List<ErrorForMultipleErrorResponse> errors = new ArrayList<>();

                e.getBindingResult().getAllErrors()
                        .forEach((error) -> {

                        String fieldName = ((FieldError) error).getField();
                        String errorMessage = error.getDefaultMessage();
                        errors.add(new ErrorForMultipleErrorResponse(fieldName, errorMessage));
                });

                return new MultipleErrorResponse("structured_error", errors);
        }


}



