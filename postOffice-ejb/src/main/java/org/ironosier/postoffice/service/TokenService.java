package org.ironosier.postoffice.service;


import javax.ejb.Stateful;

@Stateful
public class TokenService {
	
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	

}
