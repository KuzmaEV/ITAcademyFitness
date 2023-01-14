package by.mk_jd2_92_22.userSecurity.services;

import by.mk_jd2_92_22.userSecurity.dao.UserFullRepository;
import by.mk_jd2_92_22.userSecurity.model.UserFull;
import by.mk_jd2_92_22.userSecurity.security.SecurityUser;
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


        return SecurityUser.fromUser(user);
    }




}
