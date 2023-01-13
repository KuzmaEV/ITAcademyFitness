package by.mk_jd2_92_22.auditService.security.customDatail;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomUserDetailsService implements UserDetailsService {

//    private final RestTemplate restTemplate;
//    private final ObjectMapper objectMapper;
//
//    public CustomUserDetailsService(RestTemplate restTemplate, ObjectMapper objectMapper) {
//        this.restTemplate = restTemplate;
//        this.objectMapper = objectMapper;
//    }


    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {

//        String userResourceUrl = "http://user-service:8080/api/v1/users/me";
//
//        ResponseEntity<String> responseEntity = restTemplate
//                .getForEntity(userResourceUrl , String.class);
//
//        String userJson = responseEntity.getBody();
//
//        if (responseEntity.getStatusCode().equals(HttpStatus.OK)){
//
//            try {
//                UserMe user = objectMapper.readValue(userJson, UserMe.class);
//                return SecurityUser.fromUser(user);
//            } catch (JsonProcessingException e) {
//                e.printStackTrace();
//                throw new IllegalArgumentException("Не удалось получить пользователя: " + e);
//            }
//
//        }else {
//            throw new IllegalArgumentException("Не удалось получить пользователя: " + userJson);
//        }

        return SecurityUser.fromUser(new UserMe(UUID.fromString("03472117-a855-4a37-972e-a8d5976d021d"),
                Role.USER,
                Status.ACTIVATED));
    }




}
