package org.ironosier.postoffice.repository;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.ironosier.postoffice.model.TestTable;

import java.util.List;

@Singleton
public class TestTableRepository {

	@PersistenceContext(name = "postPU")
	private EntityManager em;

	public List<TestTable> getList() {
		TypedQuery<TestTable> query = em.createNamedQuery("getAll", TestTable.class);

		return query.getResultList();
	}
}
