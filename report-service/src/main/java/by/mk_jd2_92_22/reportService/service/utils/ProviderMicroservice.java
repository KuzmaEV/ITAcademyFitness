package by.mk_jd2_92_22.reportService.service.utils;

import by.mk_jd2_92_22.reportService.model.JournalFoodDTO;
import by.mk_jd2_92_22.reportService.dto.ProfileDTO;
import by.mk_jd2_92_22.reportService.model.JournalFood;
import by.mk_jd2_92_22.reportService.model.Report;
import by.mk_jd2_92_22.reportService.security.JwtProvider;
import by.mk_jd2_92_22.reportService.security.customDatail.entity.UserMe;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

@Component
public class ProviderMicroservice {

    private final RestTemplate restTemplate;
    private final JwtProvider jwtProvider;

    public ProviderMicroservice(RestTemplate restTemplate, JwtProvider jwtProvider) {
        this.restTemplate = restTemplate;
        this.jwtProvider = jwtProvider;
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

    public JournalFoodDTO getJournalFood(HttpHeaders token, UUID profileId, LocalDate dtFrom, LocalDate dtTo) {

        final long from = localDateToMillis(dtFrom);
        final long to = localDateToMillis(dtTo);

        String url = "http://product-service:8080/profile/" + profileId
                + "/journal/food/dt_from/" + from + "/dt_to/" + to;
        HttpEntity<String> jwtEntity = new HttpEntity<>(token);

        try {
            final ResponseEntity<JournalFoodDTO> responseEntity = this.restTemplate
                    .exchange(url, HttpMethod.GET, jwtEntity, JournalFoodDTO.class);

            return responseEntity.getBody();
        } catch (RestClientException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(
                    "Не удалось получить Profile для report-service: " + e.getMessage());
        }
    }

    public List<JournalFood> getJournalFoodEntries(Report report) {

        UUID profileId = report.getProfileId();
        LocalDate dtFrom = report.getParams().getDtFrom();
        LocalDate dtTo = report.getParams().getDtTo();

        final long from = localDateToMillis(dtFrom);
        final long to = localDateToMillis(dtTo);

        String url = "http://product-service:8080/profile/" + profileId
                + "/journal/food/dt_from/" + from + "/dt_to/" + to;

        String tokenStr = this.jwtProvider.createToken(report.getEmail());
        final HttpHeaders token = this.createHeaderFromToken(tokenStr);
        HttpEntity<String> jwtEntity = new HttpEntity<>(token);

        JournalFoodDTO journalFoodDTO;
        try {
            final ResponseEntity<JournalFoodDTO> responseEntity = this.restTemplate
                    .exchange(url, HttpMethod.GET, jwtEntity, JournalFoodDTO.class);

            journalFoodDTO = responseEntity.getBody();

        } catch (RestClientException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(
                    "Не удалось получить записи журнала для report-service: " + e.getMessage());
        }

        if (journalFoodDTO == null){
            throw new IllegalArgumentException("Не удалось получить записи журнала для report-service");
        }
        return journalFoodDTO.getList();

    }

    public JournalFoodDTO getJournalFood(HttpHeaders token, Report report) {

        UUID profileId = report.getProfileId();
        LocalDate dtFrom = report.getParams().getDtFrom();
        LocalDate dtTo = report.getParams().getDtTo();

        final long from = localDateToMillis(dtFrom);
        final long to = localDateToMillis(dtTo);

        String url = "http://product-service:8080/profile/" + profileId
                + "/journal/food/dt_from/" + from + "/dt_to/" + to;
        HttpEntity<String> jwtEntity = new HttpEntity<>(token);

        try {
            final ResponseEntity<JournalFoodDTO> responseEntity = this.restTemplate
                    .exchange(url, HttpMethod.GET, jwtEntity, JournalFoodDTO.class);

            return responseEntity.getBody();
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

    private long localDateToMillis(LocalDate date){
        return date.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli();
    }

}
