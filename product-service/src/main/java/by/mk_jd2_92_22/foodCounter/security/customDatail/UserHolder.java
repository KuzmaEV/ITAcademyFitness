package by.mk_jd2_92_22.foodCounter.security.customDatail;

import by.mk_jd2_92_22.foodCounter.security.customDatail.entity.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserHolder {

    public CustomUserDetails getUser(){
        return (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
