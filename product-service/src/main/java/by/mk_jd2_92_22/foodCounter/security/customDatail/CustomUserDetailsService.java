package by.mk_jd2_92_22.foodCounter.security.customDatail;

import by.mk_jd2_92_22.foodCounter.model.UserMe;
import by.mk_jd2_92_22.foodCounter.security.customDatail.entity.CustomUserDetails;
import by.mk_jd2_92_22.foodCounter.services.util.GetUserFromUserService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {


    private final GetUserFromUserService getUser;
    private final CreateCustomUserDetails createCustomUserDetails;

    public CustomUserDetailsService(GetUserFromUserService getUser, CreateCustomUserDetails createCustomUserDetails) {
        this.getUser = getUser;
        this.createCustomUserDetails = createCustomUserDetails;
    }


    @Override
    public CustomUserDetails loadUserByUsername(String token) throws UsernameNotFoundException {

        final UserMe user = this.getUser.get(token);

        return createCustomUserDetails.create(user);

//        return User.builder()
//                .username(user.getUuid().toString())
//                .password("3")
////                .disabled(user.getStatus().equals(Status.WAITING_ACTIVATION)
//                .disabled(!user.getStatus().equals(Status.ACTIVATED))
////                .accountExpired()
////                .credentialsExpired()
////                .accountLocked(user.getStatus().equals(Status.DEACTIVATED))
//
//                .roles(user.getRole().name())
//                .build();
    }


}
