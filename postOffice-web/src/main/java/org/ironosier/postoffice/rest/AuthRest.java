package org.ironosier.postoffice.rest;

import java.util.List;
import java.util.UUID;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.ironosier.postoffice.model.TestTable;
import org.ironosier.postoffice.service.TestTableService;
import org.ironosier.postoffice.service.TokenService;

@RequestScoped
@Path("/auth")
public class AuthRest {

	@Inject
	private TestTableService test; // change to token service

	@Inject
	private TokenService tokenService;

	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response login(TestTable testTable) {

		Response response = null;

		try {

			if (testTable.getName() == null || testTable.getPass() == 0) {
				throw new Exception("Логин или пароль не заданы");
			}

			for (TestTable t : test.getList()) {
				if (t.getName().equals(testTable.getName()) && t.getPass() == testTable.getPass()) {
					String token = UUID.randomUUID().toString();
					tokenService.setToken(token);
					response = Response.ok(token).build();
				}
			}

			if (response == null) {
				throw new Exception("Не авторизовано");
			}

			// 1)auth
			// 2)create token
			// 3)return token

		} catch (Exception e) {
			response = Response.status(Response.Status.UNAUTHORIZED).build();
		}
		return response;
	}

}
