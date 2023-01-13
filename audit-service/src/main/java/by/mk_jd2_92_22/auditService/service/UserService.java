package by.mk_jd2_92_22.auditService.service;

import by.mk_jd2_92_22.auditService.model.Role;
import by.mk_jd2_92_22.auditService.model.Status;
import by.mk_jd2_92_22.auditService.model.UserAudit;
import by.mk_jd2_92_22.auditService.repository.UserRepository;
import by.mk_jd2_92_22.auditService.service.api.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserService implements IUserService {

    private final UserRepository dao;

    public UserService(UserRepository dao) {
        this.dao = dao;
    }

    //    private final ObjectMapper mapper;
//    private final RestTemplate restTemplate;
//
//    public UserService(UserRepository dao, ObjectMapper mapper, RestTemplate restTemplate) {
//        this.dao = dao;
//        this.mapper = mapper;
//        this.restTemplate = restTemplate;
//    }

    @Override
    @Transactional
    public UserAudit create(UUID uuid) {

//        String userResourceUrl = "http://user-service:8080/api/v1/users/";
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

        return dao.save(new UserAudit(uuid,
                LocalDateTime.of(2022, 12, 28, 19,29,8,824),
                LocalDateTime.of(2022, 12, 30, 18,14,39,293),
                "ya@ya",
                "yauheni",
                Role.USER,
                Status.ACTIVATED));

    }
}
