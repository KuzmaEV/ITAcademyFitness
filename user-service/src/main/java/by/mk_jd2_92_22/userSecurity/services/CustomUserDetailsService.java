package by.mk_jd2_92_22.userSecurity.services;

import by.mk_jd2_92_22.userSecurity.model.Status;
import by.mk_jd2_92_22.userSecurity.repositories.UserFullRepository;
import by.mk_jd2_92_22.userSecurity.model.UserFull;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserFullRepository dao;


    public CustomUserDetailsService(UserFullRepository dao) {
        this.dao = dao;
    }


    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {

        UserFull user = dao.findByMail(mail).orElseThrow(() ->
        new UsernameNotFoundException("Unknown User: " + mail));


        return User.builder().username(user.getMail())
                .password(user.getPassword())
                .disabled(!user.getStatus().equals(Status.ACTIVATED))
//                .accountExpired()
//                .credentialsExpired()
//                .accountLocked()
                .roles(user.getRole().name())
                .build();
    }




}
