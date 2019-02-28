package DAOs;
import org.junit.Before;
import org.junit.Test;

import shared.models.Person;

/**
 * Created by Riley on 3/13/18.
 */

public class PersonDAOTest
{

	PersonDAO personDAO = new PersonDAO();
	Person person1 =new Person();
	Person person2 = new Person();
	Person person3 = new Person();

	@Before
	public void setup()
	{
		person1.setFirstName("bob");
		person1.setFirstName("jenner");

		person2.setFirstName("jenny");
		person2.setLastName("jermain");

		person3.setFirstName("grant");
		person3.setLastName("magelby");

	}


	@Test
	public void addSinglePerson() throws Exception
	{

	}

	@Test
	public void addPersonList() throws Exception
	{
	}

	@Test
	public void removePerson() throws Exception
	{
	}

	@Test
	public void removeAllPerson() throws Exception
	{
	}

	@Test
	public void createTable() throws Exception
	{
	}

	@Test
	public void getSinglePerson() throws Exception
	{
	}

	@Test
	public void getMultiplePerson() throws Exception
	{
	}

	@Test
	public void purgeDescendant() throws Exception
	{
	}

}