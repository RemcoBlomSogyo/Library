package nl.sogyo.library.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import nl.sogyo.library.model.entity.Author;
import nl.sogyo.library.model.entity.Book;
import nl.sogyo.library.model.entity.Category;
import nl.sogyo.library.model.entity.Copy;
import nl.sogyo.library.model.entity.Publisher;
import nl.sogyo.library.model.entity.User;

public class DatabaseConnector {

	private ServiceRegistry serviceRegistry;
	private SessionFactory sessionFactory;
	
	public DatabaseConnector() {
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
		configuration.addAnnotatedClass(Copy.class);
		configuration.addAnnotatedClass(User.class);
		configuration.addResource("Book.hbm.xml");
		configuration.addResource("Author.hbm.xml");
		configuration.addResource("Category.hbm.xml");
		configuration.addResource("Publisher.hbm.xml");
		configuration.addResource("Copy.hbm.xml");
		configuration.addResource("User.hbm.xml");
		serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	}
}
