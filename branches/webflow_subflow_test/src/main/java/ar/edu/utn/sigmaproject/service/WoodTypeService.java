package ar.edu.utn.sigmaproject.service;

import ar.edu.utn.sigmaproject.dao.HibernateDao;
import ar.edu.utn.sigmaproject.dao.WoodTypeDao;
import ar.edu.utn.sigmaproject.domain.WoodType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: zaba
 * Date: 26/07/12
 */
@Service
public class WoodTypeService extends AbstractService<WoodType> {
    @Autowired
    private WoodTypeDao woodTypeDao;

    @Override
    protected HibernateDao<WoodType> getServiceDao() {
        return woodTypeDao;
    }
}
