package nl.sogyo.library.model;

import org.junit.Assert;
import org.junit.Test;

import nl.sogyo.library.model.entity.User;

public class UserTest {

	@Test (expected = IllegalArgumentException.class)
	public void gmailAccountNotOfSogyoGivesException() {
		new User("seji29ewh23oj43o2odhhvfe", "Frits Peters", "fritspeters@gmail.com");
	}
	
	@Test
	public void sogyoAccountGivesNoException() {
		try {
			User user = new User("wfefoj20dhwuih47fiuhnc9l", "Evert Bakker", "ebakker@sogyo.nl");
			Assert.assertTrue(user.getEmail().contains("@sogyo.nl"));
		} catch (IllegalArgumentException e) {
			Assert.fail("Sogyo account gives illegalargumentexception");
		}
	}
	
	@Test
	public void notUsingIdInConstructorGivesIdIsZero() {
		User user = new User("238dhwewo4dfjw18dfgiwfih", "John de Boer", "jdboer@sogyo.nl");
		Assert.assertTrue(user.getId() == 0);
	}
}
