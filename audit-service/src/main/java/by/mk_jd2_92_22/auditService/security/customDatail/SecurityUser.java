package by.mk_jd2_92_22.auditService.security.customDatail;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class SecurityUser implements UserDetails {

    private final String username;
    private final String password;
    private final List<SimpleGrantedAuthority> authorities;
    private final boolean isActive;

//    private final UUID uuid_user;

    public SecurityUser(String username, String password, List<SimpleGrantedAuthority> authorities, boolean isActive, UUID uuid_user) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.isActive = isActive;
//        this.uuid_user = uuid_user;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }



    public static UserDetails fromUser(UserMe user){

        return User.builder()
                .username(user.getUuid().toString())
                .password("2")
//                .disabled()
//                .accountExpired()
//                .credentialsExpired()
//                .accountLocked()
                .roles(user.getRole().name())
                .build();

//        return new User(user.getMail(), user.getPassword(),
//                user.getStatus().equals(Status.ACTIVATED),
//                user.getStatus().equals(Status.ACTIVATED),
//                user.getStatus().equals(Status.ACTIVATED),
//                user.getStatus().equals(Status.ACTIVATED),
//                user.getRole().getAuthorities());
    }


}
