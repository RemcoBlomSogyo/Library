package nl.sogyo.library.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import nl.sogyo.library.model.command.Author;
import nl.sogyo.library.model.command.Book;
import nl.sogyo.library.model.command.Category;
import nl.sogyo.library.model.command.Publisher;

public class HibernateDatabaseConnector {

	private ServiceRegistry serviceRegistry;
	private SessionFactory sessionFactory;
	
	public HibernateDatabaseConnector() {
		addConfigurations();
	}
	
	public Session connect() {
		Session session = sessionFactory.openSession();
		return session;
	}
	
	public void disconnect(Session session) {
		session.close();
	}
	
	private void addConfigurations() {
		Configuration configuration = new Configuration();
		configuration.configure();
		configuration.addAnnotatedClass(Book.class);
		configuration.addAnnotatedClass(Author.class);
		configuration.addAnnotatedClass(Category.class);
		configuration.addAnnotatedClass(Publisher.class);
		configuration.addResource("Book.hbm.xml");
		configuration.addResource("Author.hbm.xml");
		configuration.addResource("Category.hbm.xml");
		configuration.addResource("Publisher.hbm.xml");
		serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	}
}
