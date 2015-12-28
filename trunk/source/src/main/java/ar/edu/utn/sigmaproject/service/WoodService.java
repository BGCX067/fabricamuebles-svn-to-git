package ar.edu.utn.sigmaproject.service;

import ar.edu.utn.sigmaproject.dao.HibernateDao;
import ar.edu.utn.sigmaproject.dao.WoodDao;
import ar.edu.utn.sigmaproject.domain.Wood;
import ar.edu.utn.sigmaproject.domain.WoodType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: zaba
 * Date: 25/07/12
 */
@Service
public class WoodService extends AbstractService<Wood> {
    @Autowired
    private WoodDao woodDao;

    @Transactional(readOnly = true)
    public List<WoodType> getAllWoodTypes() {
        return woodDao.getAll(WoodType.class);
    }

    @Override
    protected HibernateDao<Wood> getServiceDao() {
        return woodDao;
    }
}
