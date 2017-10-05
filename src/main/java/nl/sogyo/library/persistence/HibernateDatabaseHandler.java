package nl.sogyo.library.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import nl.sogyo.library.model.command.Author;
import nl.sogyo.library.model.command.Book;
import nl.sogyo.library.model.command.Category;
import nl.sogyo.library.model.command.Publisher;

public class HibernateDatabaseHandler {
	
	private static ServiceRegistry serviceRegistry;
	private static SessionFactory sessionFactory;
	
	public static void addConfigurations() {
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
	
	public static int insertBook(Book book) {
//		addConfigurations();
		
//		Session session = sessionFactory.openSession();
		HibernateDatabaseConnector connector = new HibernateDatabaseConnector();
		Session session = connector.connect();
		Transaction transaction = null;
		int id = 0;
		
		try {
			transaction = session.beginTransaction();
			
			Category category = null;
			try {
				CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
				CriteriaQuery<Category> categoryQuery = criteriaBuilder.createQuery(Category.class);
				Root<Category> categoryRoot = categoryQuery.from(Category.class);
				categoryQuery.select(categoryRoot).where(criteriaBuilder.equal(categoryRoot.get("name"), book.getCategory().getName()));
				category = (Category) session.createQuery(categoryQuery).setMaxResults(1).getSingleResult();
				book.setCategory(category);
			} catch (NoResultException e) {
				System.out.println("test exp");
			} finally {
				if (category == null) {
					session.save(book.getCategory());
				}
			}
			
			Publisher publisher = null;
			try {
				CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
				CriteriaQuery<Publisher> publisherQuery = criteriaBuilder.createQuery(Publisher.class);
				Root<Publisher> publisherRoot = publisherQuery.from(Publisher.class);
				publisherQuery.select(publisherRoot).where(criteriaBuilder.equal(publisherRoot.get("name"), book.getPublisher().getName()));
				publisher = (Publisher) session.createQuery(publisherQuery).setMaxResults(1).getSingleResult();
				book.setPublisher(publisher);
			} catch (NoResultException e) {
				System.out.println("test exp");
			} finally {
				if (publisher == null) {
					session.save(book.getPublisher());
				}
			}

			for (int i = 0; i < book.getAuthors().size(); i++) {
				Author author = null;
				try {
					CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
					CriteriaQuery<Author> authorQuery = criteriaBuilder.createQuery(Author.class);
					Root<Author> authorRoot = authorQuery.from(Author.class);
					authorQuery.select(authorRoot).where(criteriaBuilder.and(
							criteriaBuilder.equal(authorRoot.get("forename"), book.getAuthors().get(i).getForename()),
							criteriaBuilder.equal(authorRoot.get("surname"), book.getAuthors().get(i).getSurname())));
					author = (Author) session.createQuery(authorQuery).setMaxResults(1).getSingleResult();
					book.getAuthors().set(i, author);
				} catch (NoResultException e) {
					System.out.println("test exp");
				} finally {
					if (author == null) {
						session.save(book.getAuthors().get(i));
					}
				}
			}

			id = (int) session.save(book);
			transaction.commit();
		} catch (HibernateException e) {
			System.err.println("ERRORMESSAGE: " + e.getMessage());
			if (transaction != null) {
				transaction.rollback();
			}
		} catch (Exception e) {
			System.err.println("ERROR: " + e.getMessage());
		} finally {
//			session.close();
			connector.disconnect(session);
		}
		return id;
	}

}
