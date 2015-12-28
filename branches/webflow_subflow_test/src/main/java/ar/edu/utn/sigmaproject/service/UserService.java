package ar.edu.utn.sigmaproject.service;

import ar.edu.utn.sigmaproject.dao.HibernateDao;
import ar.edu.utn.sigmaproject.dao.UserDao;
import ar.edu.utn.sigmaproject.domain.security.Role;
import ar.edu.utn.sigmaproject.domain.security.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService extends AbstractService<User> {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Transactional(readOnly = true)
	public User getUserByUsername(String username) {
		return userDao.getUserByUsername(username);
	}

	@Transactional(readOnly = true)
	public List<Role> getAllRoles() {
		return userDao.getAll(Role.class);
	}

    /**
     * Saves an user without encoding its password. To encode the user's password
     * use @link{saveUser(User editedUser, boolean encodePassword)} instead
     * @param editedUser
     */
    @Override
    @Transactional
    public void saveEntity(User editedUser) {
        saveUser(editedUser, false);
    }

	@Transactional
	public void saveUser(User editedUser, boolean encodePassword) {
		if (encodePassword) {
			editedUser.setPassword(passwordEncoder.encodePassword(editedUser.getPassword(), editedUser.getUid()));
		}
		userDao.save(editedUser);
	}

    @Override
    protected HibernateDao<User> getServiceDao() {
        return userDao;
    }
}
