package nz.netvalue.domain.model;

import nz.netvalue.persistence.model.Role;
import nz.netvalue.persistence.model.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashSet;
import java.util.Set;

public class UserDetailsImpl implements UserDetails {

    private final User user;
    private final Set<GrantedAuthorityImpl> authorities = new HashSet<>();

    public UserDetailsImpl(User user) {
        this.user = user;
        Role role = user.getRole();
        if (role != null) {
            authorities.add(new GrantedAuthorityImpl(role.getRoleName()));
        }
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public Set<GrantedAuthorityImpl> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
