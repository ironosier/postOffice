package org.ironosier.postoffice.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.ironosier.postoffice.model.TestTable;
import org.ironosier.postoffice.repository.TestTableRepository;

@Stateless
public class TableService {

	@Inject
	private TestTableRepository repository;
	
	public List<TestTable> getList(){
		return repository.getList();
	}
}
