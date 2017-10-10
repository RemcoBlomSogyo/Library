package nl.sogyo.library.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Hibernate;
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
			if (book == null) {
				book = new Book(0, "", "", new ArrayList<Author>(), "", "", (short) 0, "", (short) 0, "");
			} else {
				Hibernate.initialize(book.getCategory());
				Hibernate.initialize(book.getPublisher());
				for (Author author : book.getAuthors()) {
					Hibernate.initialize(author);
				}
			}
			
			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<Copy> copyQuery = criteriaBuilder.createQuery(Copy.class);
			Root<Copy> copyRoot = copyQuery.from(Copy.class);
			copyQuery.select(copyRoot).where(criteriaBuilder.equal(copyRoot.get("book").get("id"), id));

			List<Copy> copies = (List<Copy>) session.createQuery(copyQuery).list();
			numberCopies = copies.size();
			
			transaction.commit();
		} catch (HibernateException e) {
			System.out.println("hibernateexcpetion");
			rollbackTransaction(transaction, e);
		} finally {
			session.close();
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
			book = setForeignKeyIdentifiers(book, session);
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
	
	public static boolean updateBook(Book book) {
		HibernateDatabaseConnector connector = new HibernateDatabaseConnector();
		Session session = connector.connect();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			if (isBookIdInTable(book.getId(), session)) {
				setForeignKeyIdentifiers(book, session);
				session.update(book);
				transaction.commit();
				return true;
			} else {
				return false;
			}
		} catch (HibernateException e) {
			rollbackTransaction(transaction, e);
			return false;
		} finally {
			connector.disconnect(session);
		}
	}
	
	private static boolean isBookIdInTable(int id, Session session) {
		boolean isBookIdInTable = session.get(Book.class, id) != null;
		session.clear();
		return isBookIdInTable;
	}
	
	private static Book setForeignKeyIdentifiers(Book book, Session session) {
		Category category = null;
		try {
			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<Category> categoryQuery = criteriaBuilder.createQuery(Category.class);
			Root<Category> categoryRoot = categoryQuery.from(Category.class);
			categoryQuery.select(categoryRoot).where(criteriaBuilder.equal(categoryRoot.get("name"), book.getCategory().getName()));
			category = (Category) session.createQuery(categoryQuery).getSingleResult();
			System.out.println(category.getName());
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
			publisher = (Publisher) session.createQuery(publisherQuery).getSingleResult();
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
				author = (Author) session.createQuery(authorQuery).getSingleResult();
				book.getAuthors().set(i, author);
			} catch (NoResultException e) {
				session.save(book.getAuthors().get(i));
			} 
		}
		System.out.println("test test test");
		return book;
	}
	
	private static void rollbackTransaction(Transaction transaction, HibernateException e) {
		System.err.println("ERRORMESSAGE: " + e.getMessage());
		if (transaction != null) {
			transaction.rollback();
		}
	}
}
