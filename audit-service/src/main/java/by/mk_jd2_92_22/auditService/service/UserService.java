package by.mk_jd2_92_22.auditService.service;

import by.mk_jd2_92_22.auditService.model.UserAudit;
import by.mk_jd2_92_22.auditService.repository.UserRepository;
import by.mk_jd2_92_22.auditService.service.api.IUserService;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;


import java.util.UUID;

@Service
public class UserService implements IUserService {

    private final RestTemplate restTemplate;
    private final UserRepository dao;

    public UserService(UserRepository dao,  RestTemplate restTemplate) {
        this.dao = dao;
        this.restTemplate = restTemplate;
    }

    @Override
    @Transactional
    public UserAudit create(UUID uuid, HttpHeaders token) {


        String url = "http://user-service:8080/users/me";


        //из строки делает HttpHeaders
//        String tokenHead = "Bearer " + token;
//        HttpHeaders headers = new HttpHeaders();//Создаем HEADER
//        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
//        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
//        headers.set("Authorization", tokenHead);

        HttpEntity<String> jwtEntity = new HttpEntity<>(token);


            final ResponseEntity<UserAudit> responseEntity = restTemplate
                    .exchange(url, HttpMethod.GET, jwtEntity, UserAudit.class);



            if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                    throw new IllegalArgumentException("Не удалось получить пользователя для аудита");

            }
             if (responseEntity.getBody() == null) {
                    throw new IllegalArgumentException("Не удалось получить пользователя для аудита");
        }

        final UserAudit userAudit = responseEntity.getBody();

            return dao.save(userAudit);



//        String userResourceUrl = "http://user-service:8080/users/";
//
//        ResponseEntity<String> responseEntity = restTemplate
//                .getForEntity(userResourceUrl + uuid, String.class);
//
//        String userJson = responseEntity.getBody();
//
//        if (responseEntity.getStatusCode().equals(HttpStatus.OK)){
//
//            try {
//                UserAudit userAudit = mapper.readValue(userJson, UserAudit.class);
//                return dao.save(userAudit);
//            } catch (JsonProcessingException e) {
//                e.printStackTrace();
//                throw new IllegalArgumentException("Не удалось получить пользователя для аудита: " + e);
//            }
//
//        }else {
//            throw new IllegalArgumentException("Не удалось получить пользователя для аудита: " + userJson);
//        }



//        return dao.save(new UserAudit(uuid,
//                LocalDateTime.of(2022, 12, 28, 19,29,8,824),
//                LocalDateTime.of(2022, 12, 30, 18,14,39,293),
//                "ya@ya",
//                "yauheni",
//                Role.USER,
//                Status.ACTIVATED));

    }
}
