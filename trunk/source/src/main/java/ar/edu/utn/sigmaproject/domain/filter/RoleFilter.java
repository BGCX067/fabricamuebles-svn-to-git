package ar.edu.utn.sigmaproject.domain.filter;

import ar.edu.utn.sigmaproject.domain.security.Role;

public class RoleFilter extends DefaultEntityFilter<Role> {
	
	private static final long serialVersionUID = -5928141084872237783L;
	
	private String name;
	private String description;

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
