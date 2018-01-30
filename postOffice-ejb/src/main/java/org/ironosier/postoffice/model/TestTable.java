package org.ironosier.postoffice.model;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "test_table")
@NamedQueries({
	@NamedQuery(name = "getAll", query = "SELECT t FROM TestTable t"),
	@NamedQuery(name = "getFirst", query = "SELECT t FROM TestTable t WHERE t.pass = 1")
})
public class TestTable implements Serializable {

	@Id
	@GeneratedValue
	private UUID id;
	private String name;
	private int pass;

	public TestTable() {
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPass() {
		return pass;
	}

	public void setPass(int pass) {
		this.pass = pass;
	}
}
