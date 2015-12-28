package ar.edu.utn.sigmaproject.dao;

import ar.edu.utn.sigmaproject.domain.filter.EntityFilter;
import ar.edu.utn.sigmaproject.domain.filter.UserFilter;
import ar.edu.utn.sigmaproject.domain.security.User;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserDao extends HibernateDao<User> {

	public User getUserByUsername(final String username) {
		final Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("username", username);
		return findUnique("from User where username = :username", paramMap);
	}

    @Override
    protected Class<User> getType() {
        return User.class;
    }

    @Override
    protected DetachedCriteria fillCriteria(EntityFilter<? extends User> entityFilter, DetachedCriteria criteria) {
        final UserFilter filter = (UserFilter) entityFilter;

        if (filter != null) {
            if (StringUtils.hasText(filter.getName())) {
                criteria.add(Restrictions.or(Restrictions.ilike("firstName", filter.getName(), MatchMode.ANYWHERE),
                        Restrictions.ilike("lastName", filter.getName(), MatchMode.ANYWHERE)));
            }
        }

        return criteria;
    }
}
