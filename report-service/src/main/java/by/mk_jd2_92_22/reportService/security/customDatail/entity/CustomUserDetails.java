package by.mk_jd2_92_22.reportService.security.customDatail.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private final String username;
    private final String password;
    private final List<SimpleGrantedAuthority> authorities;
    private final boolean isActive;

    private final String email;

//    private final UUID uuid_user;


    public CustomUserDetails(String username, String password, List<SimpleGrantedAuthority> authorities,
                             boolean isActive, String email) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.isActive = isActive;
        this.email = email;
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

    public boolean isActive() {
        return isActive;
    }

    public String getEmail() {
        return email;
    }
}
