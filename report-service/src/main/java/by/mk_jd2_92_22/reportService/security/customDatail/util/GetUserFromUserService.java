package by.mk_jd2_92_22.reportService.security.customDatail.util;

import by.mk_jd2_92_22.reportService.security.customDatail.UserMe;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class GetUserFromUserService {

    private final RestTemplate restTemplate;

    public GetUserFromUserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public UserMe get(HttpHeaders token) {

        String url = "http://user-service:8080/users/me";
        HttpEntity<String> jwtEntity = new HttpEntity<>(token);

        try {
            final ResponseEntity<UserMe> responseEntity = this.restTemplate
                    .exchange(url, HttpMethod.GET, jwtEntity, UserMe.class);

            return responseEntity.getBody();
        } catch (RestClientException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(
                    "Не удалось получить пользователя для report-service: " + e.getMessage());
        }

    }

    public UserMe get(String token) {

        final HttpHeaders header = createHeaderFromToken(token);

        String url = "http://user-service:8080/users/me";
        HttpEntity<String> jwtEntity = new HttpEntity<>(header);

        try {
            final ResponseEntity<UserMe> responseEntity = this.restTemplate
                    .exchange(url, HttpMethod.GET, jwtEntity, UserMe.class);

            return responseEntity.getBody();
        } catch (RestClientException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(
                    "Не удалось получить пользователя для product-service: " + e.getMessage());
        }
    }

    private HttpHeaders createHeaderFromToken(String token){

        String tokenHead = "Bearer " + token;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.set("Authorization", tokenHead);
        return headers;
    }

}
