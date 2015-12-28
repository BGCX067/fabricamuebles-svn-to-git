package ar.edu.utn.sigmaproject.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import ar.edu.utn.sigmaproject.domain.security.Permission;
import ar.edu.utn.sigmaproject.domain.security.Role;
import ar.edu.utn.sigmaproject.domain.security.User;

public class UserDetailsWrapper implements UserDetails {

	private static final long serialVersionUID = 700960117937121989L;
	
	private final User user;
    private final Collection<GrantedAuthority> authorities;

    public UserDetailsWrapper(User user) {
            super();
            this.user = user;

            List<Role> roles = this.user.getRoles();

            this.authorities = new HashSet<GrantedAuthority>();

            for (Role role : roles) {
                    List<Permission> permissions = role.getPermissions();
                    for (Permission permission : permissions) {
                    		/**
                    		 * Doesn't work with other GrantedAuthority that it is not SimpleGrantedAuthority
                    		 * (regarding with sec:authorize tags)
                    		 */
                            /*authorities.add(new PermissionAuthorityWrapper(permission
                                            .getName()));*/
                    	authorities.add(new SimpleGrantedAuthority("ROLE_" + permission.getName()));
                    }
            }
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
            return authorities;
    }

    @Override
    public String getPassword() {
            return user.getPassword();
    }

    @Override
    public String getUsername() {
            return user.getUsername();
    }

    public String getCompleteName() {
            return user.getLastName() + "," + user.getFirstName();
    }

    public String getUid() {
            return user.getUid();
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
