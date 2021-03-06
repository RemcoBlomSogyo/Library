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

import nl.sogyo.library.model.entity.Author;
import nl.sogyo.library.model.entity.Book;
import nl.sogyo.library.model.entity.Category;
import nl.sogyo.library.model.entity.Copy;
import nl.sogyo.library.model.entity.Publisher;
import nl.sogyo.library.model.entity.User;
import nl.sogyo.library.services.rest.libraryapi.json.BookInfo;
import nl.sogyo.library.services.rest.libraryapi.json.message.AddCopyMessage;
import nl.sogyo.library.services.rest.libraryapi.json.message.BorrowCopyMessage;
import nl.sogyo.library.services.rest.libraryapi.json.message.DeleteCopyMessage;
import nl.sogyo.library.services.rest.libraryapi.json.message.RegisterMessage;

public class DatabaseHandler {
	
	private DatabaseConnector connector;
	private Session session;
	private Transaction transaction;
	private CriteriaBuilder criteriaBuilder;
	
	private CriteriaQuery<Book> bookQuery;
	private CriteriaQuery<Author> authorQuery;
	private CriteriaQuery<Copy> copyQuery;
	private CriteriaQuery<User> userQuery;
	
	private Root<Book> bookRoot;
	private Root<Author> authorRoot;
	private Root<Copy> copyRoot;
	private Root<User> userRoot;
	
	private List<Book> books;
	private List<Book> booksWithTitleInput;
	private List<Author> authors;
	private List<User> users;
	
	private Book book;
	private Author author;
	private Copy copy;
	private User user;
	
	public DatabaseHandler() {
		connector = new DatabaseConnector();
	}
	
	private void initialize() {
		session = connector.connect();
		transaction = session.beginTransaction();
		criteriaBuilder = session.getCriteriaBuilder();
		
		bookQuery = criteriaBuilder.createQuery(Book.class);
		authorQuery = criteriaBuilder.createQuery(Author.class);
		copyQuery = criteriaBuilder.createQuery(Copy.class);
		userQuery = criteriaBuilder.createQuery(User.class);
		
		bookRoot = bookQuery.from(Book.class);
		authorRoot = authorQuery.from(Author.class);
		copyRoot = copyQuery.from(Copy.class);
		userRoot = userQuery.from(User.class);
	}
	
	public List<Book> selectBooksByTitle(String titleInput) {
		try {
			initialize();
			setBooksWithTitleInput(titleInput);
			
			for (Book book : booksWithTitleInput) {
				initializeReferencedEntities(book);
			}
			transaction.commit();
		} catch (HibernateException e) {
			rollbackTransaction(e);
		} finally {
			connector.disconnect(session);
		}
		
		return booksWithTitleInput;
	}
	
	public List<Book> selectBooksByAuthorSingleName(String authorInput) {
		try {	
			initialize();
			bookQuery.select(bookRoot);
			List<Book> allBooksInTable = session.createQuery(bookQuery).list();
			
			setAuthorsWithSingleNameInput(authorInput);
			setBooksOfSelectedAuthors(allBooksInTable, authors, new ArrayList<Book>());
			transaction.commit();
		} catch (HibernateException e) {
			rollbackTransaction(e);
		} finally {
			connector.disconnect(session);
		}
		
		return books;
	}
	
	public List<Book> selectBooksByIsbn(String isbnInput) {
		try {
			initialize();
			ParameterExpression<String> isbnParam = criteriaBuilder.parameter(String.class);
			bookQuery.select(bookRoot).where(criteriaBuilder.like(bookRoot.get("isbn"), isbnParam));
			TypedQuery<Book> typedQuery = session.createQuery(bookQuery);
			typedQuery.setParameter(isbnParam, addPercentWildcards(isbnInput));
			books = typedQuery.getResultList();
			
			for (Book book : books) {
				initializeReferencedEntities(book);
			}
			transaction.commit();
		} catch (HibernateException e) {
			rollbackTransaction(e);
		} finally {
			connector.disconnect(session);
		}
		
		return books;
	}
	
	public List<Book> selectBooksByAuthorTotalName(String authorForenameInput, String authorSurnameInput) {
		try {
			initialize();
			bookQuery.select(bookRoot);
			List<Book> allBooksInTable = session.createQuery(bookQuery).list();
			
			setAuthorsWithForenameAndSurnameParameters(authorForenameInput, authorSurnameInput);
			setBooksOfSelectedAuthors(allBooksInTable, authors, new ArrayList<Book>());
			transaction.commit();
		} catch (HibernateException e) {
			rollbackTransaction(e);
		} finally {
			connector.disconnect(session);
		}
		return books;
	}
	
	public List<Book> selectBooksByTitleAndAuthorSingleName(String titleInput, String authorInput) {
		try {
			initialize();
			setAuthorsWithSingleNameInput(authorInput);
			setBooksWithTitleInput(titleInput);
			setBooksOfSelectedAuthors(booksWithTitleInput, authors, new ArrayList<Book>());
			transaction.commit();
		} catch (HibernateException e) {
			rollbackTransaction(e);
		} finally {
			connector.disconnect(session);
		}
		return books;
	}
	
	public List<Book> selectBooksByTitleAndAuthorTotalName(String titleInput, String authorForenameInput, String authorSurnameInput) {
		try {
			initialize();
			setAuthorsWithForenameAndSurnameParameters(authorForenameInput, authorSurnameInput);
			setBooksWithTitleInput(titleInput);
			setBooksOfSelectedAuthors(booksWithTitleInput, authors, new ArrayList<Book>());
			transaction.commit();
		} catch (HibernateException e) {
			rollbackTransaction(e);
		} finally {
			connector.disconnect(session);
		}
		return books;
	}
	
	public BookInfo selectBookById(int id) {
		long copiesAvailable = 0;
		long copiesBorrowed = 0;
		
		try {
			initialize();
			book = session.get(Book.class, id);
			if (book == null) {
				book = new Book(0, "", "", new ArrayList<Author>(), "", "", (short) 0, "", (short) 0, "");
			} else {
				copiesAvailable = getCopiesAvailable(book.getId());
				copiesBorrowed = getCopiesBorrowed(book.getId());
				initializeReferencedEntities(book);
			}
			
			transaction.commit();
		} catch (HibernateException e) {
			rollbackTransaction(e);
		} finally {
			connector.disconnect(session);
		}
		
		BookInfo bookInfo = new BookInfo(book, copiesAvailable, copiesBorrowed);
		return bookInfo;
	}
	
	public int insertBook(Book book) {
		try {
			initialize();
			setForeignKeyIdentifiers(book);
			int id = (int) session.save(book);
			transaction.commit();
			return id;
		} catch (HibernateException e) {
			rollbackTransaction(e);
			return 0;
		} finally {
			connector.disconnect(session);
		}
	}
	
	public boolean updateBook(Book book) {
		try {
			initialize();
			if (isBookIdInTable(book.getId())) {
				setForeignKeyIdentifiers(book);
				session.update(book);
				transaction.commit();
				return true;
			} else {
				throw new HibernateException("Book ID is not in table");
			}
		} catch (HibernateException e) {
			rollbackTransaction(e);
			return false;
		} finally {
			connector.disconnect(session);
		}
	}
	
	public AddCopyMessage insertCopy(int bookId) {
		try {
			initialize();
			book = session.get(Book.class, bookId);
			if (book == null) {
				throw new HibernateException("Book ID is not in table");
			} else {
				copy = new Copy(book);
				session.save(copy);
				long copiesAvailable = getCopiesAvailable(book.getId());
				
				transaction.commit();
				return new AddCopyMessage(true, copiesAvailable);
			}
		} catch (HibernateException e) {
			rollbackTransaction(e);
			return new AddCopyMessage(false, 0);
		} finally {
			connector.disconnect(session);
		}
	}
	
	public DeleteCopyMessage deleteCopy(int bookId) {
		DeleteCopyMessage deleteCopyMessage;
		try {
			initialize();
			book = session.get(Book.class, bookId);
			if (book == null) {
				throw new HibernateException("Book ID is not in table");
			} else {
				copyQuery.select(copyRoot).where(criteriaBuilder.and(criteriaBuilder.equal(copyRoot.get("book"), book), criteriaBuilder.isNull(copyRoot.get("user"))));
				copy = session.createQuery(copyQuery).setMaxResults(1).getSingleResult();
				copy.setBook(null);
				session.delete(copy);
				
				long copiesAvailable = getCopiesAvailable(book.getId());
				transaction.commit();
				deleteCopyMessage = new DeleteCopyMessage(true, copiesAvailable);
			}
		} catch (HibernateException e) {
			rollbackTransaction(e);
			deleteCopyMessage = new DeleteCopyMessage(false, 0);
		} finally {
			connector.disconnect(session);
		}
		return deleteCopyMessage;
	}
	
	public boolean deleteBookAndCopies(int bookId) {
		boolean commandSucceeded = false;
		try {
			initialize();
			book = session.get(Book.class, bookId);
			if (book == null) {
				throw new HibernateException("Book ID is not in table");
			} else {
				CriteriaDelete<Copy> copyDelete = criteriaBuilder.createCriteriaDelete(Copy.class);
				Root<Copy> copyRoot = copyDelete.from(Copy.class);
				copyDelete.where(criteriaBuilder.equal(copyRoot.get("book").get("id"), bookId));
				session.createQuery(copyDelete).executeUpdate();

				CriteriaDelete<Book> bookDelete = criteriaBuilder.createCriteriaDelete(Book.class);
				Root<Book> bookRoot = bookDelete.from(Book.class);
				bookDelete.where(criteriaBuilder.equal(bookRoot.get("id"), bookId));
				session.createQuery(bookDelete).executeUpdate();
				transaction.commit();
				commandSucceeded = true;
			}
		} catch (HibernateException e) {
			rollbackTransaction(e);
		} finally {
			connector.disconnect(session);
		}
		return commandSucceeded;
	}
	
	public List<Author> selectAllAuthors() {
		try {
			initialize();
			authorQuery.select(authorRoot);
			authors = (List<Author>) session.createQuery(authorQuery).list();
			transaction.commit();
		} catch (HibernateException e) {
			rollbackTransaction(e);
		} finally {
			connector.disconnect(session);
		}
		return authors;
	}
	
	public List<User> selectAllUsers() {
		try {
			initialize();
			userQuery.select(userRoot);
			users = (List<User>) session.createQuery(userQuery).list();
			transaction.commit();
		} catch (HibernateException e) {
			rollbackTransaction(e);
		} finally {
			connector.disconnect(session);
		}
		return users;
	}
	
	public RegisterMessage insertUserIfNotInTable(User incompleteUser) {
		boolean commandSucceeded = false;
		boolean userInTable = false;
		String errorDescription = "No error";
		
		try {
			initialize();
			user = getUser(incompleteUser);
			if (user == null) {
				int id = (int) session.save(incompleteUser);
				commandSucceeded = true;
				user = session.get(User.class, id);
			}
			userInTable = true;
			transaction.commit();
		} catch (HibernateException e) {
			rollbackTransaction(e);
			errorDescription = "Something went wrong in the database";
		} finally {
			connector.disconnect(session);
		}
		
		RegisterMessage registerMessage = null;
		try {
			registerMessage = new RegisterMessage(commandSucceeded, userInTable, errorDescription,
				user.getId(), user.getGivenName(), user.getFamilyName(), user.getUserType());
		} catch (NullPointerException e) {
			registerMessage = new RegisterMessage(commandSucceeded, userInTable, errorDescription,
					0, "", "", (byte) 0);
		}
		return registerMessage;
	}
	
	public boolean isUserAuthorized(String googleUserId, byte minimumUserType) {
		boolean userAuthorized = false;
		
		try {
			initialize();
			userQuery.select(userRoot).where(criteriaBuilder.and(
					criteriaBuilder.equal(userRoot.get("googleUserId"), googleUserId),
					criteriaBuilder.greaterThanOrEqualTo(userRoot.get("userType"), minimumUserType)));
			session.createQuery(userQuery).setMaxResults(1).getSingleResult();
			userAuthorized = true;
			transaction.commit();
		} catch (Exception e) {
			rollbackTransaction(e);
		} finally {
			connector.disconnect(session);
		}
		return userAuthorized;
	}
	
	public boolean isUserAuthorized(String googleUserId, int userId) {
		boolean userAuthorized = false;
		
		try {
			initialize();
			userQuery.select(userRoot).where(criteriaBuilder.and(
					criteriaBuilder.equal(userRoot.get("googleUserId"), googleUserId),
					criteriaBuilder.equal(userRoot.get("id"), userId)));
			session.createQuery(userQuery).setMaxResults(1).getSingleResult();
			userAuthorized = true;
			transaction.commit();
		} catch (Exception e) {
			rollbackTransaction(e);
		} finally {
			connector.disconnect(session);
		}
		return userAuthorized;
	}
	
	public boolean updateUsers(List<User> users) {
		try {
			initialize();
			for (User user : users) {
				session.update(user);
			}
			transaction.commit();
			return true;
		} catch (HibernateException e) {
			rollbackTransaction(e);
			return false;
		} finally {
			connector.disconnect(session);
		}
	}
	
	public BorrowCopyMessage borrowCopy(int userId, int bookId) {
		boolean commandSucceeded = false;
		long copiesBorrowed = 0;
		long copiesAvailable = 0;
		
		try {
			initialize();
			User user = session.get(User.class, userId);
			Book book = session.get(Book.class, bookId);
			
			if (book != null) {
				copiesBorrowed = getCopiesBorrowed(book.getId());
				copiesAvailable = getCopiesAvailable(book.getId());
				
				if (copiesAvailable > 0) {
					copyQuery.select(copyRoot).where(criteriaBuilder.and(
							criteriaBuilder.equal(copyRoot.get("book"), book),
							criteriaBuilder.isNull(copyRoot.get("user"))));
					Copy copy = session.createQuery(copyQuery).setMaxResults(1).getSingleResult();
					copy.setUser(user);
					session.update(copy);
					commandSucceeded = true;
					copiesBorrowed++;
					copiesAvailable--;
				}
			}
			transaction.commit();
		} catch (Exception e) {
			rollbackTransaction(e);
		}
		BorrowCopyMessage borrowCopyMessage = new BorrowCopyMessage(commandSucceeded, copiesAvailable, copiesBorrowed);
		return borrowCopyMessage;
	}
	
	private User getUser(User incompleteUser) {
		User user = null;
		try {
			userQuery.select(userRoot).where(criteriaBuilder.equal(userRoot.get("googleUserId"), incompleteUser.getGoogleUserId()));
			user = session.createQuery(userQuery).setMaxResults(1).getSingleResult();
		} catch (NoResultException e) {
			// return null
		}
		return user;
	}
	
	private boolean isBookIdInTable(int id) {
		boolean isBookIdInTable = session.get(Book.class, id) != null;
		session.clear();
		return isBookIdInTable;
	}
	
	private void initializeReferencedEntities(Book book) {
		Hibernate.initialize(book.getCategory());
		Hibernate.initialize(book.getPublisher());
		for (Author author : book.getAuthors()) {
			Hibernate.initialize(author);
		}
	}
	
	private String addPercentWildcards(String param) {
		return "%" + param + "%";
	}
	
	private Book setForeignKeyIdentifiers(Book book) {
		Category category = null;
		try {
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
			CriteriaQuery<Publisher> publisherQuery = criteriaBuilder.createQuery(Publisher.class);
			Root<Publisher> publisherRoot = publisherQuery.from(Publisher.class);
			publisherQuery.select(publisherRoot).where(criteriaBuilder.equal(publisherRoot.get("name"), book.getPublisher().getName()));
			publisher = (Publisher) session.createQuery(publisherQuery).setMaxResults(1).getSingleResult();
			book.setPublisher(publisher);
		} catch (NoResultException e) {
			session.save(book.getPublisher());
		} 

		for (int i = 0; i < book.getAuthors().size(); i++) {
			try {
				authorQuery.select(authorRoot).where(criteriaBuilder.and(
						criteriaBuilder.equal(authorRoot.get("forename"), book.getAuthors().get(i).getForename()),
						criteriaBuilder.equal(authorRoot.get("surname"), book.getAuthors().get(i).getSurname())));
				author = (Author) session.createQuery(authorQuery).setMaxResults(1).getSingleResult();
				book.getAuthors().set(i, author);
			} catch (NoResultException e) {
				session.save(book.getAuthors().get(i));
			} 
		}
		return book;
	}
	
	private void setAuthorsWithSingleNameInput(String authorInput) {
		ParameterExpression<String> authorParam = criteriaBuilder.parameter(String.class);
		authorQuery.select(authorRoot).where(criteriaBuilder.or(
				criteriaBuilder.like(authorRoot.get("forename"), authorParam),
				criteriaBuilder.like(authorRoot.get("surname"), authorParam)));
		TypedQuery<Author> typedQuery = session.createQuery(authorQuery);
		typedQuery.setParameter(authorParam, addPercentWildcards(authorInput));
		authors = typedQuery.getResultList();
	}
	
	private void setAuthorsWithForenameAndSurnameParameters(String authorForenameInput, String authorSurnameInput) {
		ParameterExpression<String> forenameParam = criteriaBuilder.parameter(String.class);
		ParameterExpression<String> surnameParam = criteriaBuilder.parameter(String.class);
		authorQuery.select(authorRoot).where(criteriaBuilder.and(
				criteriaBuilder.like(authorRoot.get("forename"), forenameParam),
				criteriaBuilder.like(authorRoot.get("surname"), surnameParam)));
		
		TypedQuery<Author> typedQuery = session.createQuery(authorQuery);
		typedQuery.setParameter(forenameParam, addPercentWildcards(authorForenameInput));
		typedQuery.setParameter(surnameParam, addPercentWildcards(authorSurnameInput));
		authors = typedQuery.getResultList();
	}
	
	private void setBooksWithTitleInput(String titleInput) {
		ParameterExpression<String> titleParam = criteriaBuilder.parameter(String.class);
		bookQuery.select(bookRoot).where(
				criteriaBuilder.like(bookRoot.get("title"), titleParam));
		TypedQuery<Book> typedQuery = session.createQuery(bookQuery);
		typedQuery.setParameter(titleParam, addPercentWildcards(titleInput));
		booksWithTitleInput = typedQuery.getResultList();
	}
	
	private void setBooksOfSelectedAuthors(List<Book> selectedBooks, List<Author> selectedAuthors, List<Book> authorMatchingBooks) {
		books = authorMatchingBooks;
		for (Book book : selectedBooks) {
			initializeReferencedEntities(book);
			BOOK: for (Author authorBook : book.getAuthors()) {
				for (Author authorMatch : selectedAuthors) {
					if (authorMatch.getForename().equals(authorBook.getForename())
							&& authorMatch.getSurname().equals(authorBook.getSurname())) {
						authorMatchingBooks.add(book);
						break BOOK;
					}
				}
			}
		}
	}
	
	private long getCopiesAvailable(int bookId) {
		long copiesAvailable = 0;
		try {
			copiesAvailable = (long) session.createQuery("select count(*) from Copy where bookId = " + bookId + " and userId is null").getSingleResult();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return copiesAvailable;
	}
	
	private long getCopiesBorrowed(int bookId) {
		long copiesBorrowed = 0;
		try {
			copiesBorrowed = (long) session.createQuery("select count(*) from Copy where bookId = " + bookId + " and userId is not null").getSingleResult();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return copiesBorrowed;
	}
	
	private void rollbackTransaction(Exception e) {
		System.err.println("ERRORMESSAGE: " + e.getMessage());
		if (transaction != null) {
			transaction.rollback();
		}
	}
}
