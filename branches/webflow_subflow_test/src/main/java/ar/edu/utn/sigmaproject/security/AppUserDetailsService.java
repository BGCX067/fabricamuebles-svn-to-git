package ar.edu.utn.sigmaproject.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.utn.sigmaproject.domain.security.User;
import ar.edu.utn.sigmaproject.service.UserService;

public class AppUserDetailsService implements UserDetailsService {
	
	@Autowired
    private UserService userService;

	@Transactional(readOnly = true)
    @Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		final User user = userService.getUserByUsername(username);
        if (user == null) {
                return null;
        }
        return new UserDetailsWrapper(user);

	}

}
