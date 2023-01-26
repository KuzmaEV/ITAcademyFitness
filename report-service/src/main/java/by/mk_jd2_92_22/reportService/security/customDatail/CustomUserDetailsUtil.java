package by.mk_jd2_92_22.reportService.security.customDatail;

import by.mk_jd2_92_22.reportService.security.customDatail.entity.CustomUserDetails;
import by.mk_jd2_92_22.reportService.security.customDatail.entity.Status;
import by.mk_jd2_92_22.reportService.security.customDatail.entity.UserMe;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomUserDetailsUtil {

    public CustomUserDetails create(UserMe user){

            return new CustomUserDetails(user.getUuid().toString(),
                    "user.getPassword()",
                    createAuthorities(user.getRole().name()),
                    user.getStatus().equals(Status.ACTIVATED),
                    user.getMail());
    }

    private List<SimpleGrantedAuthority> createAuthorities(String role){

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        return authorities;
    }
}
