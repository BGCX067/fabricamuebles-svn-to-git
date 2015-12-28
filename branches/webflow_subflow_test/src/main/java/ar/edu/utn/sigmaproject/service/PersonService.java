package ar.edu.utn.sigmaproject.service;

import ar.edu.utn.sigmaproject.dao.HibernateDao;
import ar.edu.utn.sigmaproject.dao.PersonDao;
import ar.edu.utn.sigmaproject.domain.Identity;
import ar.edu.utn.sigmaproject.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * User: Gian Franco Zabarino
 * Date: 27/08/12
 */
@Service
public class PersonService<T extends Person> extends AbstractService<T> {

    @Autowired
    private PersonDao personDao;

    @Override
    protected HibernateDao<T> getServiceDao() {
        return personDao;
    }

    @Transactional(readOnly = true)
    public List<Identity.IdentityType> getEntityTypes() {
        return Arrays.asList(Identity.IdentityType.values());
    }
}