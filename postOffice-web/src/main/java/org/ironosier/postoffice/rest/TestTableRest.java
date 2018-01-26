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
import org.ironosier.postoffice.service.TestTableService;

@RequestScoped
@Path("/test")
public class TestTableRest {

	@Inject
	private TestTableService tableService;

	@GET
	@Path("/getList")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getList() {
		Response response = null;
		try {
			List<TestTable> list = tableService.getList();
			response = Response.ok(list).build();
		} catch (Exception e) {
			response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		return response;
	}
}
