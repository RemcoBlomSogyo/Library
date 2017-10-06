package nl.sogyo.library.persistence;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import nl.sogyo.library.model.command.Author;
import nl.sogyo.library.model.command.Book;
import nl.sogyo.library.model.command.Category;
import nl.sogyo.library.model.command.Copy;
import nl.sogyo.library.model.command.Publisher;
import nl.sogyo.library.services.rest.libraryapi.json.BookInfo;

public class HibernateDatabaseHandler {
	
	public static BookInfo selectBookById(int id) {
		HibernateDatabaseConnector connector = new HibernateDatabaseConnector();
		Session session = connector.connect();
		Transaction transaction = null;
		Book book = null;
		int numberCopies = 0;
		
		try {
			transaction = session.beginTransaction();
			
			book = session.get(Book.class, id);
			
			List<Copy> copies = null;
			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<Copy> copyQuery = criteriaBuilder.createQuery(Copy.class);
			Root<Book> bookRoot = copyQuery.from(Book.class);
			Root<Copy> copyRoot = copyQuery.from(Copy.class);
			copyQuery.select(copyRoot).where(criteriaBuilder.equal(bookRoot.get("id"), id));
			try {
				copies = session.createQuery(copyQuery).list();
				numberCopies = copies.size();
			} catch (NoResultException e) {
				numberCopies = 0;
			}
	
		} catch (HibernateException e) {
			rollbackTransaction(transaction, e);
		} finally {
			connector.disconnect(session);
		}
		
		BookInfo bookInfo = new BookInfo(book, numberCopies);
		return bookInfo;
	}
	
	public static int insertBook(Book book) {
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
				session.save(book.getCategory());
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
				session.save(book.getPublisher());
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
					session.save(book.getAuthors().get(i));
				} 
			}

			id = (int) session.save(book);
			transaction.commit();
		} catch (HibernateException e) {
			rollbackTransaction(transaction, e);
		} catch (Exception e) {
			System.err.println("ERROR: " + e.getMessage());
		} finally {
			connector.disconnect(session);
		}
		return id;
	}
	
	private static void rollbackTransaction(Transaction transaction, HibernateException e) {
		System.err.println("ERRORMESSAGE: " + e.getMessage());
		if (transaction != null) {
			transaction.rollback();
		}
	}
}
