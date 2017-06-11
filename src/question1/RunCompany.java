package question1;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Simple class demonstrating hibernate mapping.
 * Creates company, adds two employees, then lists company employees. 
 * You need to define DB_URL, DB_USER and DB_PASSWORD environment variables, 
 * and add hibernate libraries (can be found in lib folder) and 
 * your DB driver to classpath to make it running.   
 * @author Natalja Olefire
 *
 */
public class RunCompany {

	public static void main(String[] args) {
		Session session = getSessionFactory().openSession();
		session.beginTransaction();

		System.out.println("Create companies...");
		Company company = new Company("1", "Google", "Google Corporation");
		Person person1 = new Person("1", "John", "Smith", new Date());
		Person person2 = new Person("2", "Jane", "Bright", new Date());

		System.out.println("Add some employees...");
		Set<Person> employees = new HashSet<Person>();
		employees.add(person1);
		employees.add(person2);
		company.setEmployees(employees);
		session.save(company);
		session.getTransaction().commit();

		// do another query in another transaction
		session.beginTransaction();
		System.out.println("Obtain created company from DB...");
		System.out.println(session.createQuery("from Company").list());
		session.getTransaction().commit();

		System.out.println("Done");
		shutdown();
	}
	
	private static final SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {
		try {
			Configuration config = new Configuration(); 
			config.setProperty("hibernate.connection.url", System.getenv("DB_URL"));
			config.setProperty("hibernate.connection.username", System.getenv("DB_USER"));
			config.setProperty("hibernate.connection.password", System.getenv("DB_PASSWORD"));
			config.setProperty("hibernate.show_sql", "true");
			config.setProperty("hibernate.hbm2ddl.auto", "create");
			config.addAnnotatedClass(Company.class);
			config.addAnnotatedClass(Person.class);
			return config.buildSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}
 
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
 
	public static void shutdown() {
		getSessionFactory().close();
	}
	
}
