package by.mk_jd2_92_22.reportService.security.customDatail;

import by.mk_jd2_92_22.reportService.security.customDatail.util.GetUserFromUserService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final GetUserFromUserService getUser;

    public CustomUserDetailsService(GetUserFromUserService getUser) {
        this.getUser = getUser;
    }

    @Override
    public UserDetails loadUserByUsername(String token) throws UsernameNotFoundException {

        final UserMe userMe = this.getUser.get(token);

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
