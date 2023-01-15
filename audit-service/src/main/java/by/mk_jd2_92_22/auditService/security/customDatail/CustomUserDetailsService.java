package by.mk_jd2_92_22.auditService.security.customDatail;

import by.mk_jd2_92_22.auditService.service.util.CreateHeaderFromToken;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final RestTemplate restTemplate;
    private final CreateHeaderFromToken createHeader;

    public CustomUserDetailsService(RestTemplate restTemplate, CreateHeaderFromToken createHeader) {
        this.restTemplate = restTemplate;
        this.createHeader = createHeader;
    }


    @Override
    public UserDetails loadUserByUsername(String token) throws UsernameNotFoundException {

        final String url = "http://user-service:8080/users/me";

        final HttpHeaders headers = createHeader.create(token);
        HttpEntity<String> jwtEntity = new HttpEntity<>(headers);


        final ResponseEntity<UserMe> responseEntity = restTemplate
                    .exchange(url,
                            HttpMethod.GET, jwtEntity, UserMe.class);

        final UserMe userMe = responseEntity.getBody();

        if (userMe == null){
            throw new IllegalArgumentException("Проблебы с user-service при получении пользователя для аудита");
        }

        return SecurityUser.fromUser(userMe);
    }




}
