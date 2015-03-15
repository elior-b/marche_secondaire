package model;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class TransactionTest {
	Transaction transaction;

	@Before
	public void setUp() throws Exception {
		transaction = new Transaction();
	}
	@Test
	public void testGetId() {
		transaction.setId(1);
		assertEquals("L'id de la transaction n'est pas correct ",1,transaction.getId());
	}
	@Test
	public void testSetId() {
		transaction.setId(1);
		assertEquals("L'id de la transaction n'est pas correct ",1,transaction.getId());
	}
	@Test
	public void testGetDate()
	{
	    Date date = new Date("23/10/2000");
	    transaction.setDate(date);
	   
	    assertEquals("La date de l'offre est erronée :", date,transaction.getDate());
	}
	@Test
	public void testSetDate()
	{
	    Date date = new Date("23/10/2000");
	    transaction.setDate(date);
	   
	    assertEquals("La date de l'offre est erronée :", date,transaction.getDate());
	}

}
