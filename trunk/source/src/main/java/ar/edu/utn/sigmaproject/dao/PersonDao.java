package ar.edu.utn.sigmaproject.dao;

import ar.edu.utn.sigmaproject.domain.Identity;
import ar.edu.utn.sigmaproject.domain.Person;
import ar.edu.utn.sigmaproject.domain.filter.EntityFilter;
import ar.edu.utn.sigmaproject.domain.filter.PersonFilter;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

/**
 * Fills criteria with common product's properties.<br/><br/>
 *
 * User: Gian Franco Zabarino
 * Date: 23/08/12
 */
@Repository
public class PersonDao<T extends Person> extends HibernateDao<T> {

    @Override
    protected Class<T> getType() {
        return (Class<T>) Person.class;
    }

    @Override
    protected DetachedCriteria fillCriteria(EntityFilter<? extends T> entityFilter, DetachedCriteria criteria) {
        PersonFilter filter = (PersonFilter) entityFilter;
        if (filter != null) {
            final String firstName = filter.getFirstName();
            if (StringUtils.hasText(firstName)) {
                criteria.add(Restrictions.ilike("name.first", firstName.trim() + '%'));
            }
            final String lastName = filter.getLastName();
            if (StringUtils.hasText(lastName)) {
                criteria.add(Restrictions.ilike("name.last", lastName.trim() + '%'));
            }
            final Identity.IdentityType identityType = filter.getIdentityType();
            if (identityType != null) {
                criteria.add(Restrictions.eq("identity.identityType", identityType));
            }
            final String identityNumber = filter.getIdentityNumber();
            if (StringUtils.hasText(identityNumber)) {
                criteria.add(Restrictions.ilike("identity.identityNumber", '%' + identityNumber.trim() + '%'));
            }
        }
        return criteria;
    }
}
