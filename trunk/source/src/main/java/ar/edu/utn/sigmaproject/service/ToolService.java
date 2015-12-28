/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.utn.sigmaproject.service;

import ar.edu.utn.sigmaproject.dao.HibernateDao;
import ar.edu.utn.sigmaproject.dao.ToolDao;
import ar.edu.utn.sigmaproject.domain.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author DanielRH
 */
@Service
public class ToolService extends AbstractService<Tool> {
    @Autowired
    private ToolDao toolDao;

    @Override
    protected HibernateDao<Tool> getServiceDao() {
        return toolDao;
    }
    
}
