/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.utn.sigmaproject.service;

import ar.edu.utn.sigmaproject.dao.HibernateDao;
import ar.edu.utn.sigmaproject.dao.MachineDao;
import ar.edu.utn.sigmaproject.domain.Machine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author DanielRH
 */
@Service
public class MachineService extends AbstractService<Machine> {
    @Autowired
    private MachineDao machineDao;

    @Override
    protected HibernateDao<Machine> getServiceDao() {
        return machineDao;
    }
}
