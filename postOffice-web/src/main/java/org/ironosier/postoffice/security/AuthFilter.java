package org.ironosier.postoffice.security;

import java.io.IOException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.ironosier.postoffice.service.TokenService;

@ApplicationScoped
@Secured
@Provider
public class AuthFilter implements ContainerRequestFilter {
	
	@Inject
	private TokenService tokenService;

	@Override
	public void filter(ContainerRequestContext context) throws IOException {

		String authHeader = context.getHeaderString(HttpHeaders.AUTHORIZATION);

		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			throw new NotAuthorizedException("Auth header do not provided!");
		}

		String token = authHeader.substring("Bearer".length()).trim();

		try {
			if (!tokenService.getToken().equals(token)) {
				throw new Exception("Токены не совпадают");
			}
		} catch (Exception e) {
			context.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
		}
	}

}
