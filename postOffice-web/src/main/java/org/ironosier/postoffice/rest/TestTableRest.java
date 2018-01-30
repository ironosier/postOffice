package org.ironosier.postoffice.rest;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.ironosier.postoffice.model.TestTable;
import org.ironosier.postoffice.security.Secured;
import org.ironosier.postoffice.service.TestTableService;
import org.ironosier.postoffice.service.TokenService;

@RequestScoped
@Path("/test")
public class TestTableRest {

	@Inject
	private TestTableService testTableService;
	 
	@Inject
	private TokenService tokenService;
	
	@GET
	@Path("/get_list")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getList() {
		Response response = null;
		try {
			List<TestTable> list = testTableService.getList();
			response = Response.ok(list).build();
		} catch (Exception e) {
			response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		return response;
	}
	
	@Secured
	@GET
	@Path("/get_first")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFirst() {
		
		try {
			return Response.ok(testTableService.getFirst()).header("Bearer", "жопа").build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}
}
