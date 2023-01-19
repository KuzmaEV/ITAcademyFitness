package by.mk_jd2_92_22.reportService.service.utils;

import by.mk_jd2_92_22.reportService.dto.JournalFoodList;
import by.mk_jd2_92_22.reportService.dto.ProfileDTO;
import by.mk_jd2_92_22.reportService.model.JournalFood;
import by.mk_jd2_92_22.reportService.security.customDatail.UserMe;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Component
public class GetFromAnotherService {

    private final RestTemplate restTemplate;

    public GetFromAnotherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public UserMe getUserMe(HttpHeaders token) {

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

    public UserMe getUserMe(String token) {

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

    public ProfileDTO getProfile(HttpHeaders token, UUID profileId) {

        String url = "http://product-service:8080/profile/" + profileId;
        HttpEntity<String> jwtEntity = new HttpEntity<>(token);

        try {
            final ResponseEntity<ProfileDTO> responseEntity = this.restTemplate
                    .exchange(url, HttpMethod.GET, jwtEntity, ProfileDTO.class);

            return responseEntity.getBody();
        } catch (RestClientException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(
                    "Не удалось получить Profile для report-service: " + e.getMessage());
        }
    }

    public List<JournalFood> getJournalFoods(HttpHeaders token, UUID profileId, LocalDate dtFrom, LocalDate dtTo) {

        final long from = dtFrom.toEpochDay();
        final long to = dtTo.toEpochDay();

        String url = "http://product-service:8080/profile/" + profileId + "/report?from=" + from + "&to=" + to;
        HttpEntity<String> jwtEntity = new HttpEntity<>(token);

        try {
            final ResponseEntity<JournalFoodList> responseEntity = this.restTemplate
                    .exchange(url, HttpMethod.GET, jwtEntity, JournalFoodList.class);

            return responseEntity.getBody().getList();
        } catch (RestClientException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(
                    "Не удалось получить Profile для report-service: " + e.getMessage());
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
