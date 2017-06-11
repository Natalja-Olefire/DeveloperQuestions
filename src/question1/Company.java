package question1;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Company")
public class Company implements Serializable {

	private static final long serialVersionUID = 6369580252909342557L;

	@Id
	@Column(unique = true)
	String id;

	@Column
	String name;

	@Column
	String description;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "Job", joinColumns = { @JoinColumn(name = "CompanyId", nullable = false) }, inverseJoinColumns = {
			@JoinColumn(name = "PersonId", nullable = false) })
	public Set<Person> employees;

	public Company(String id, String name, String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String descirption) {
		this.description = descirption;
	}

	public Set<Person> getEmployees() {
		return employees;
	}

	public void setEmployees(Set<Person> employees) {
		this.employees = employees;
	}

	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + ", description=" + description + ", employees=" + employees
				+ "]";
	}

}
