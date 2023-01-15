package by.mk_jd2_92_22.auditService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;
import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;

@Component
public class RestTemplateResponseErrorHandler
        implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse httpResponse)
            throws IOException {

        return (
                httpResponse.getStatusCode().series() == CLIENT_ERROR
                        || httpResponse.getStatusCode().series() == SERVER_ERROR);
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse)
            throws IOException {


        final HttpStatus statusCode = httpResponse.getStatusCode();

        if (statusCode.series() == HttpStatus.Series.SERVER_ERROR ||
            statusCode.series() == HttpStatus.Series.CLIENT_ERROR ||
                statusCode == HttpStatus.NOT_FOUND
        ) {
            throw new IOException("error returned by remote API. HTTP-status: "
                   + httpResponse.getRawStatusCode());
        }



//        if (httpResponse.getStatusCode()
//                .series() == HttpStatus.Series.SERVER_ERROR) {
//            // handle SERVER_ERROR
//            throw new IOException("error returned by remote API. HTTP-status: "
//                   + httpResponse.getRawStatusCode() + " " + httpResponse.getStatusText());
//        } else if (httpResponse.getStatusCode()
//                .series() == HttpStatus.Series.CLIENT_ERROR) {
//            throw new IOException();
//            // handle CLIENT_ERROR
//            if (httpResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
//                throw new NotFoundException();
//            }
//        }
    }
}
