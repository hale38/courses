package DAOs;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import shared.models.User;

import static org.junit.Assert.assertTrue;

/**
 * Created by Riley on 3/13/18.
 */
public class AuthDAOTest
{
	AuthDAO authDAO;
	User user = new User();
	User user1 = new User();
	User user2 = new User();

	;
	@Before
	public void setup() throws Exception
	{
		authDAO = new AuthDAO();
		user.setUserName("bob");
		user.setAuthToken("78787686");
		user1.setUserName("grant");
		user1.setAuthToken("76");
		user2.setUserName("bo");
		user2.setAuthToken("dasdasd6");
	}


	@Test
	public void addSingleAuth() throws Exception
	{
		String uid = "Peanuts";
		String auth ="2210-2210";
		assertTrue(authDAO.addSingleAuth(uid,auth)==true);
	}

	@Test
	public void addMultipleAuths() throws Exception
	{
		ArrayList<User>users=new ArrayList<>();
		users.add(user);
		users.add(user1);
		users.add(user2);

		assertTrue(authDAO.addMultipleAuths(users)==true);
	}

	@Test
	public void removeAllAuth() throws Exception
	{
		authDAO.removeAllAuth();
		authDAO.addSingleAuth(user.getUserName(),user.getAuthToken());
		authDAO.removeAllAuth();
		String result = authDAO.getPersonID(user.getAuthToken());
		assertTrue(result==null);

	}



	@Test
	public void getPersonID() throws Exception
	{
		authDAO.removeAllAuth();
		authDAO.addSingleAuth(user.getUserName(),user.getAuthToken());

		String result = authDAO.getPersonID(user.getAuthToken());
		assertTrue(result.equals(user.getUserName()));
	}

	@Test
	public void getMultipleValid() throws Exception
	{
		ArrayList<User> users = new ArrayList<>();
		users.add(user);
		users.add(user1);
		users.add(user2);


		assertTrue(authDAO.addMultipleAuths(users)==true);
	}

	@Test
	public void getMultipleInValid() throws Exception
	{
		ArrayList<User> users = new ArrayList<>();
		users.add(user);
		users.add(user1);
		users.add(user2);
		User badUser = new User();
		users.add(badUser);

		assertTrue(authDAO.addMultipleAuths(users)==false);
	}

}