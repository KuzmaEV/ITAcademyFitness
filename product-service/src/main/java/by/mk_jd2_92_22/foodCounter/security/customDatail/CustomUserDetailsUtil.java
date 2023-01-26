package by.mk_jd2_92_22.foodCounter.security.customDatail;

import by.mk_jd2_92_22.foodCounter.model.UserMe;
import by.mk_jd2_92_22.foodCounter.security.customDatail.entity.CustomUserDetails;
import by.mk_jd2_92_22.foodCounter.model.Status;
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
                    user.getDtCreate(),
                    user.getDtUpdate());
    }

    private List<SimpleGrantedAuthority> createAuthorities(String role){

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        return authorities;
    }
}
