package by.mk_jd2_92_22.reportService.security.customDatail;

import by.mk_jd2_92_22.reportService.security.customDatail.entity.CustomUserDetails;
import by.mk_jd2_92_22.reportService.security.customDatail.entity.UserMe;
import by.mk_jd2_92_22.reportService.service.utils.ProviderMicroservice;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final ProviderMicroservice getUser;
    private final CustomUserDetailsUtil customUserDetailsUtil;

    public CustomUserDetailsService(ProviderMicroservice getUser, CustomUserDetailsUtil customUserDetailsUtil) {
        this.getUser = getUser;
        this.customUserDetailsUtil = customUserDetailsUtil;
    }

    @Override
    public CustomUserDetails loadUserByUsername(String token) throws UsernameNotFoundException {

        final UserMe userMe = this.getUser.getUserMe(token);

        return this.customUserDetailsUtil.create(userMe);



//        return User.builder().username(userMe.getUuid().toString())
//                .password("userMe.getPassword()")
//                .disabled(!userMe.getStatus().equals(Status.ACTIVATED))
////                .accountExpired()
////                .credentialsExpired()
////                .accountLocked()
//                .roles(userMe.getRole().name())
//                .build();
    }
}
