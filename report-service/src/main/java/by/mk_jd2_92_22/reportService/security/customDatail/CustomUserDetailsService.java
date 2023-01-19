package by.mk_jd2_92_22.reportService.security.customDatail;

import by.mk_jd2_92_22.reportService.service.utils.GetFromAnotherService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final GetFromAnotherService getUser;

    public CustomUserDetailsService(GetFromAnotherService getUser) {
        this.getUser = getUser;
    }

    @Override
    public UserDetails loadUserByUsername(String token) throws UsernameNotFoundException {

        final UserMe userMe = this.getUser.getUserMe(token);

        return User.builder().username(userMe.getUuid().toString())
                .password("userMe.getPassword()")
                .disabled(!userMe.getStatus().equals(Status.ACTIVATED))
//                .accountExpired()
//                .credentialsExpired()
//                .accountLocked()
                .roles(userMe.getRole().name())
                .build();
    }
}
