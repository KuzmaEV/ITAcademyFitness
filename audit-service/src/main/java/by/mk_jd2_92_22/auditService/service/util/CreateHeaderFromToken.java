package by.mk_jd2_92_22.auditService.service.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class CreateHeaderFromToken {

    public HttpHeaders create(String token){

        String tokenHead = "Bearer " + token;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.set("Authorization", tokenHead);
        return headers;
    }
}
