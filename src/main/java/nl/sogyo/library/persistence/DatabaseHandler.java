package nl.sogyo.library.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
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
import nl.sogyo.library.services.rest.libraryapi.json.message.AddCopyMessage;
import nl.sogyo.library.services.rest.libraryapi.json.message.DeleteCopyMessage;

public class DatabaseHandler {
	
	public static List<Book> selectBooksByTitle(String titleInput) {
		DatabaseConnector connector = new DatabaseConnector();
		Session session = connector.connect();
		Transaction transaction = null;
		List<Book> books = null;
		
		try {
			transaction = session.beginTransaction();
			
			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<Book> bookQuery = criteriaBuilder.createQuery(Book.class);
			Root<Book> bookRoot = bookQuery.from(Book.class);
			ParameterExpression<String> titleParam = criteriaBuilder.parameter(String.class);
			bookQuery.select(bookRoot).where(criteriaBuilder.like(bookRoot.get("title"), titleParam));
			
			TypedQuery<Book> typedQuery = session.createQuery(bookQuery);
			typedQuery.setParameter(titleParam, addPercentWildcards(titleInput));
			books = typedQuery.getResultList();
			
			for (Book book : books) {
				initializeReferencedEntities(book);
			}
			transaction.commit();
		} catch (HibernateException e) {
			rollbackTransaction(transaction, e);
		} finally {
			session.close();
			connector.disconnect(session);
		}
		
		return books;
	}
	
	public static List<Book> selectBooksByAuthorSingleName(String authorInput) {
		DatabaseConnector connector = new DatabaseConnector();
		Session session = connector.connect();
		Transaction transaction = null;
		List<Book> books = new ArrayList<Book>();
		System.out.println("test author hibernate");
		try {	
			transaction = session.beginTransaction();
			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			
			CriteriaQuery<Book> bookQuery = criteriaBuilder.createQuery(Book.class);
			Root<Book> bookRoot = bookQuery.from(Book.class);
			bookQuery.select(bookRoot);
			List<Book> allBooks = session.createQuery(bookQuery).list();
						
			CriteriaQuery<Author> authorQuery = criteriaBuilder.createQuery(Author.class);
			Root<Author> authorRoot = authorQuery.from(Author.class);
			ParameterExpression<String> authorParam = criteriaBuilder.parameter(String.class);
			authorQuery.select(authorRoot).where(criteriaBuilder.or(
					criteriaBuilder.like(authorRoot.get("forename"), authorParam),
					criteriaBuilder.like(authorRoot.get("surname"), authorParam)));
			TypedQuery<Author> typedQuery = session.createQuery(authorQuery);
			typedQuery.setParameter(authorParam, addPercentWildcards(authorInput));
			List<Author> authorMatches = typedQuery.getResultList();
			
			for (Book book : allBooks) {
				initializeReferencedEntities(book);
				BOOK: for (Author authorBook : book.getAuthors()) {
					for (Author authorMatch : authorMatches) {
						if (authorMatch.getForename().equals(authorBook.getForename())
								&& authorMatch.getSurname().equals(authorBook.getSurname())) {
							books.add(book);
							break BOOK;
						}
					}
				}
			}

			transaction.commit();
		} catch (HibernateException e) {
			rollbackTransaction(transaction, e);
		} finally {
			session.close();
			connector.disconnect(session);
		}
		
		return books;
	}
	
	public static List<Book> selectBooksByIsbn(String isbnInput) {
		DatabaseConnector connector = new DatabaseConnector();
		Session session = connector.connect();
		Transaction transaction = null;
		List<Book> books = null;
		
		try {
			transaction = session.beginTransaction();
			
			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<Book> bookQuery = criteriaBuilder.createQuery(Book.class);
			Root<Book> bookRoot = bookQuery.from(Book.class);
			bookQuery.select(bookRoot).where(criteriaBuilder.like(bookRoot.get("isbn"), "%" + isbnInput + "%"));
			books = (List<Book>) session.createQuery(bookQuery).list();
			
			for (Book book : books) {
				initializeReferencedEntities(book);
			}
			transaction.commit();
		} catch (HibernateException e) {
			rollbackTransaction(transaction, e);
		} finally {
			session.close();
			connector.disconnect(session);
		}
		
		return books;
	}
	
	public static List<Book> selectBooksByAuthorTotalName(String authorForenameInput, String authorSurnameInput) {
		DatabaseConnector connector = new DatabaseConnector();
		Session session = connector.connect();
		Transaction transaction = null;
		List<Book> books = new ArrayList<Book>();
		
		try {
			transaction = session.beginTransaction();
			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			
			CriteriaQuery<Book> bookQuery = criteriaBuilder.createQuery(Book.class);
			Root<Book> bookRoot = bookQuery.from(Book.class);
			bookQuery.select(bookRoot);
			List<Book> allBooksInTable = session.createQuery(bookQuery).list();
						
			CriteriaQuery<Author> authorQuery = criteriaBuilder.createQuery(Author.class);
			Root<Author> authorRoot = authorQuery.from(Author.class);
			ParameterExpression<String> forenameParam = criteriaBuilder.parameter(String.class);
			ParameterExpression<String> surnameParam = criteriaBuilder.parameter(String.class);
			authorQuery.select(authorRoot).where(criteriaBuilder.and(
					criteriaBuilder.like(authorRoot.get("forename"), forenameParam),
					criteriaBuilder.like(authorRoot.get("surname"), surnameParam)));
			TypedQuery<Author> typedQuery = session.createQuery(authorQuery);
			typedQuery.setParameter(forenameParam, addPercentWildcards(authorForenameInput));
			typedQuery.setParameter(surnameParam, addPercentWildcards(authorSurnameInput));
			List<Author> authorMatches = typedQuery.getResultList();
			
			for (Book book : allBooksInTable) {
				initializeReferencedEntities(book);
				BOOK: for (Author authorBook : book.getAuthors()) {
					for (Author authorMatch : authorMatches) {
						if (authorMatch.getForename().equals(authorBook.getForename())
								&& authorMatch.getSurname().equals(authorBook.getSurname())) {
							books.add(book);
							break BOOK;
						}
					}
				}
			}
			transaction.commit();
		} catch (HibernateException e) {
			rollbackTransaction(transaction, e);
		} finally {
			session.close();
			connector.disconnect(session);
		}
		
		return books;
	}
	
	public static List<Book> selectBooksByTitleAndAuthorSingleName(String titleInput, String authorInput) {
		DatabaseConnector connector = new DatabaseConnector();
		Session session = connector.connect();
		Transaction transaction = null;
		List<Book> books = new ArrayList<Book>();
		
		try {
			transaction = session.beginTransaction();
			
			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			
			CriteriaQuery<Author> authorQuery = criteriaBuilder.createQuery(Author.class);
			Root<Author> authorRoot = authorQuery.from(Author.class);
			ParameterExpression<String> authorParam = criteriaBuilder.parameter(String.class);
			authorQuery.select(authorRoot).where(criteriaBuilder.or(
					criteriaBuilder.like(authorRoot.get("forename"), authorParam),
					criteriaBuilder.like(authorRoot.get("surname"), authorParam)));
			TypedQuery<Author> typedQuery = session.createQuery(authorQuery);
			typedQuery.setParameter(authorParam, addPercentWildcards(authorInput));
			List<Author> authorMatches = typedQuery.getResultList();
			
			CriteriaQuery<Book> bookQuery = criteriaBuilder.createQuery(Book.class);
			Root<Book> bookRoot = bookQuery.from(Book.class);
			bookQuery.select(bookRoot).where(
					criteriaBuilder.like(bookRoot.get("title"), "%" + titleInput + "%"));
			List<Book> booksWithTitleInput = (List<Book>) session.createQuery(bookQuery).list();
			
			for (Book book : booksWithTitleInput) {
				initializeReferencedEntities(book);
				BOOK: for (Author authorBook : book.getAuthors()) {
					for (Author authorMatch : authorMatches) {
						if (authorMatch.getForename().equals(authorBook.getForename())
								&& authorMatch.getSurname().equals(authorBook.getSurname())) {
							books.add(book);
							break BOOK;
						}
					}
				}
			}
			transaction.commit();
		} catch (HibernateException e) {
			rollbackTransaction(transaction, e);
		} finally {
			session.close();
			connector.disconnect(session);
		}
		
		return books;
	}
	
	public static List<Book> selectBooksByTitleAndAuthorTotalName(String titleInput, String authorForenameInput, String authorSurnameInput) {
		DatabaseConnector connector = new DatabaseConnector();
		Session session = connector.connect();
		Transaction transaction = null;
		List<Book> books = new ArrayList<Book>();
		
		try {
			transaction = session.beginTransaction();
			
			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

			CriteriaQuery<Author> authorQuery = criteriaBuilder.createQuery(Author.class);
			Root<Author> authorRoot = authorQuery.from(Author.class);
			
			ParameterExpression<String> forenameParam = criteriaBuilder.parameter(String.class);
			ParameterExpression<String> surnameParam = criteriaBuilder.parameter(String.class);
			authorQuery.select(authorRoot).where(criteriaBuilder.and(
					criteriaBuilder.like(authorRoot.get("forename"), forenameParam),
					criteriaBuilder.like(authorRoot.get("surname"), surnameParam)));
			
			TypedQuery<Author> typedQuery = session.createQuery(authorQuery);
			typedQuery.setParameter(forenameParam, addPercentWildcards(authorForenameInput));
			typedQuery.setParameter(surnameParam, addPercentWildcards(authorSurnameInput));
			List<Author> authorMatches = typedQuery.getResultList();
			
			CriteriaQuery<Book> bookQuery = criteriaBuilder.createQuery(Book.class);
			Root<Book> bookRoot = bookQuery.from(Book.class);
			bookQuery.select(bookRoot).where(
					criteriaBuilder.like(bookRoot.get("title"), "%" + titleInput + "%"));
			List<Book> booksWithTitleInput = (List<Book>) session.createQuery(bookQuery).list();
			
			for (Book book : booksWithTitleInput) {
				initializeReferencedEntities(book);
				BOOK: for (Author authorBook : book.getAuthors()) {
					for (Author authorMatch : authorMatches) {
						if (authorMatch.getForename().equals(authorBook.getForename())
								&& authorMatch.getSurname().equals(authorBook.getSurname())) {
							books.add(book);
							break BOOK;
						}
					}
				}
			}
			
			transaction.commit();
		} catch (HibernateException e) {
			rollbackTransaction(transaction, e);
		} finally {
			session.close();
			connector.disconnect(session);
		}
		
		return books;
	}
	
	public static BookInfo selectBookById(int id) {
		DatabaseConnector connector = new DatabaseConnector();
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
				initializeReferencedEntities(book);
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
			connector.disconnect(session);
		}
		
		BookInfo bookInfo = new BookInfo(book, numberCopies);
		return bookInfo;
	}
	
	public static int insertBook(Book book) {
		DatabaseConnector connector = new DatabaseConnector();
		Session session = connector.connect();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			book = setForeignKeyIdentifiers(book, session);
			int id = (int) session.save(book);
			transaction.commit();
			return id;
		} catch (HibernateException e) {
			rollbackTransaction(transaction, e);
			return 0;
		} finally {
			connector.disconnect(session);
		}
	}
	
	public static boolean updateBook(Book book) {
		DatabaseConnector connector = new DatabaseConnector();
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
				throw new HibernateException("Book ID is not in table");
			}
		} catch (HibernateException e) {
			rollbackTransaction(transaction, e);
			return false;
		} finally {
			connector.disconnect(session);
		}
	}
	
	public static AddCopyMessage insertCopy(int bookId) {
		DatabaseConnector connector = new DatabaseConnector();
		Session session = connector.connect();
		Transaction transaction = null;
		
		try {
			transaction = session.beginTransaction();
			
			Book book = session.get(Book.class, bookId);
			if (book == null) {
				throw new HibernateException("Book ID is not in table");
			} else {
				Copy copy = new Copy(book);
				session.save(copy);
				
				CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
				CriteriaQuery<Copy> copyQuery = criteriaBuilder.createQuery(Copy.class);
				Root<Copy> copyRoot = copyQuery.from(Copy.class);

				copyQuery.select(copyRoot).where(criteriaBuilder.equal(copyRoot.get("book"), book));
				List<Copy> copies = (List<Copy>) session.createQuery(copyQuery).list();
				int numberCopies = copies.size();
				
				transaction.commit();
				return new AddCopyMessage(true, numberCopies);
			}
		} catch (HibernateException e) {
			rollbackTransaction(transaction, e);
			return new AddCopyMessage(false, 0);
		} finally {
			connector.disconnect(session);
		}
	}
	
	public static DeleteCopyMessage deleteCopy(int bookId) {
		DatabaseConnector connector = new DatabaseConnector();
		Session session = connector.connect();
		Transaction transaction = null;
		
		try {
			transaction = session.beginTransaction();
			
			Book book = session.get(Book.class, bookId);
			if (book == null) {
				throw new HibernateException("Book ID is not in table");
			} else {
				CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

				CriteriaQuery<Copy> copyQuery = criteriaBuilder.createQuery(Copy.class); 
				Root<Copy> copyRoot = copyQuery.from(Copy.class);
				
				copyQuery.select(copyRoot).where(criteriaBuilder.equal(copyRoot.get("book"), book));
				List<Copy> copies = (List<Copy>) session.createQuery(copyQuery).list();
				if (copies.size() == 0) {
					throw new HibernateException("TEST0 No copies of book in table");
				} else {
					Copy copy = copies.remove(0);
					copy.setBook(null);
					session.delete(copy);
				}
				
				int numberCopies = copies.size() - 1;
				System.out.println("hallo test");
				transaction.commit();
				return new DeleteCopyMessage(true, numberCopies);
			}
		} catch (HibernateException e) {
			rollbackTransaction(transaction, e);
			return new DeleteCopyMessage(false, 0);
		} finally {
			connector.disconnect(session);
		}
	}
	
	public static boolean deleteBookAndCopies(int bookId) {
		DatabaseConnector connector = new DatabaseConnector();
		Session session = connector.connect();
		Transaction transaction = null;
		
		try {
			transaction = session.beginTransaction();
			
			Book book = session.get(Book.class, bookId);
			if (book == null) {
				throw new HibernateException("Book ID is not in table");
			} else {
				CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

				CriteriaDelete<Copy> copyDelete = criteriaBuilder.createCriteriaDelete(Copy.class);
				Root<Copy> copyRoot = copyDelete.from(Copy.class);
				copyDelete.where(criteriaBuilder.equal(copyRoot.get("book").get("id"), bookId));
				System.out.println("voor");
				session.createQuery(copyDelete).executeUpdate();
				System.out.println("na");
				
				CriteriaDelete<Book> bookDelete = criteriaBuilder.createCriteriaDelete(Book.class);
				Root<Book> bookRoot = bookDelete.from(Book.class);
				bookDelete.where(criteriaBuilder.equal(bookRoot.get("id"), bookId));
				System.out.println("voor2");
				session.createQuery(bookDelete).executeUpdate();
				System.out.println("na2");
				transaction.commit();
				return true;
			}
		} catch (HibernateException e) {
			rollbackTransaction(transaction, e);
			return false;
		} catch (Exception e) {
			System.out.println("EXCEPTION: " + e.getMessage());
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
	
	private static void initializeReferencedEntities(Book book) {
		Hibernate.initialize(book.getCategory());
		Hibernate.initialize(book.getPublisher());
		for (Author author : book.getAuthors()) {
			Hibernate.initialize(author);
		}
	}
	
	private static String addPercentWildcards(String param) {
		return "%" + param + "%";
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
