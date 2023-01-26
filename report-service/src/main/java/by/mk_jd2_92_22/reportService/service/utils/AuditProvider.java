package by.mk_jd2_92_22.reportService.service.utils;

import by.mk_jd2_92_22.reportService.dto.AuditDTO;
import by.mk_jd2_92_22.reportService.dto.Type;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
public class AuditProvider {

    private final RestTemplate restTemplate;

    public AuditProvider(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void create(UUID uuidUser, String text, Type type, HttpHeaders token){
        final AuditDTO auditDTO = new AuditDTO(uuidUser, text, type);
        final String url = "http://audit-service:8080/audit";

        HttpEntity<AuditDTO> jwtEntity = new HttpEntity<>(auditDTO, token);


        try {
            final ResponseEntity<String> responseEntity = restTemplate
                    .exchange(url,
                            HttpMethod.POST, jwtEntity, String.class);
        } catch (RestClientException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Не удалось создать " + type.name() + ": " + e.getMessage());
        }
    }

    public void create(UUID uuidUser, String text, Type type, String token){


        final AuditDTO auditDTO = new AuditDTO(uuidUser, text, type);
        final String url = "http://audit-service:8080/audit";
        final HttpHeaders headers = createHeader(token);

        HttpEntity<AuditDTO> jwtEntity = new HttpEntity<>(auditDTO, headers);

        try {
            final ResponseEntity<String> responseEntity = restTemplate
                    .exchange(url,
                            HttpMethod.POST, jwtEntity, String.class);
        } catch (RestClientException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Не удалось создать " + type.name() + ": " + e.getMessage());
        }
    }

    public HttpHeaders createHeader(String token){

        String tokenHead = "Bearer " + token;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.set("Authorization", tokenHead);
        return headers;
    }
}
