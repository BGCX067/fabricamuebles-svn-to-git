package ar.edu.utn.sigmaproject.service;

import ar.edu.utn.sigmaproject.dao.HibernateDao;
import ar.edu.utn.sigmaproject.dao.PermissionDao;
import ar.edu.utn.sigmaproject.dao.RoleDao;
import ar.edu.utn.sigmaproject.domain.filter.PermissionFilter;
import ar.edu.utn.sigmaproject.domain.security.Permission;
import ar.edu.utn.sigmaproject.domain.security.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("roleService")
public class RoleService extends AbstractService<Role> {

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private PermissionDao permissionDao;

	@Transactional(readOnly = true)
	public List<Permission> getAllPermissions() {
		return permissionDao.getList(new PermissionFilter());
	}

    @Override
    protected HibernateDao<Role> getServiceDao() {
        return roleDao;
    }
}

