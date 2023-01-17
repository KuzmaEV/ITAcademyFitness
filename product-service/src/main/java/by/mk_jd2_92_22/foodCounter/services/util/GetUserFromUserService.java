package by.mk_jd2_92_22.foodCounter.services.util;

import by.mk_jd2_92_22.foodCounter.model.UserMe;
import by.mk_jd2_92_22.foodCounter.model.UserProfile;
import by.mk_jd2_92_22.foodCounter.security.customDatail.UserHolder;
import by.mk_jd2_92_22.foodCounter.security.customDatail.entity.CustomUserDetails;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
public class GetUserFromUserService {

    private final RestTemplate restTemplate;
    private final UserHolder holder;

    public GetUserFromUserService(RestTemplate restTemplate, UserHolder holder) {
        this.restTemplate = restTemplate;
        this.holder = holder;
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
                    "Не удалось получить пользователя для product-service: " + e.getMessage());
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

    public UserProfile getUserProfileHolder(){

        final CustomUserDetails user = this.holder.getUser();

        return new UserProfile(UUID.fromString(user.getUsername()),
                user.getDtCreate(), user.getDtUpdate());
    }

    public UserProfile getUserProfileHolder(HttpHeaders token){

        final UserMe userMe = this.get(token);
        return new UserProfile(userMe.getUuid(),
                userMe.getDtCreate(), userMe.getDtUpdate());
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
