package by.mk_jd2_92_22.foodCounter.services.util;

import by.mk_jd2_92_22.foodCounter.services.dto.AuditDTO;

import by.mk_jd2_92_22.foodCounter.services.dto.Type;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
public class AuditProvider {

    private final RestTemplate restTemplate;
    private final HeaderFromTokenUtil createHeader;

    public AuditProvider(RestTemplate restTemplate, HeaderFromTokenUtil createHeader) {
        this.restTemplate = restTemplate;
        this.createHeader = createHeader;
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
        final HttpHeaders headers = createHeader.create(token);

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
}
