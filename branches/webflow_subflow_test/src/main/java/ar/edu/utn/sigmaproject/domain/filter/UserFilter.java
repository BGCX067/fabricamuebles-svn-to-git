package ar.edu.utn.sigmaproject.domain.filter;

import ar.edu.utn.sigmaproject.domain.security.User;

public class UserFilter extends DefaultEntityFilter<User> {
	
	private static final long serialVersionUID = -4631556726082698073L;
	
	private String name;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
