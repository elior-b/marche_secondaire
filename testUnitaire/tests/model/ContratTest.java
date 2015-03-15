package model;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class ContratTest {
	Contrat contrat;
	
	@Before
	public void setUp() throws Exception {
	 contrat = new Contrat();
	}

	@Test
	public void testGetId() {
		contrat.setId(1);
		assertEquals("L'id du contrat n'est pas correct ",1, contrat.getId());
		
	}
	@Test
	public void testSetId() {
		contrat.setId(1);
		assertEquals("L'id du contrat n'est pas correct ",1, contrat.getId());
		
	}
	@Test
	public void testGetQuantite() {
		contrat.setQuantite(23);
		assertEquals("La quantité de titres n'est pas correct ",23, contrat.getQuantite());

	}
	@Test
	public void testSetQuantite() {
		contrat.setQuantite(23);
		assertEquals("La quantité de titres n'est pas correct ",23, contrat.getQuantite());

	}
	@Test 
	public void testGetDate()
	{
	    Date date = new Date("23/10/2000");
	    contrat.setDate(date);
	   
	    assertEquals("La date du contrat est erronée :", date,contrat.getDate());
	}
	@Test 
	public void testSetDate()
	{
	    Date date = new Date("23/10/2000");
	    contrat.setDate(date);
	   
	    assertEquals("La date du contrat est erronée :", date,contrat.getDate());
	}

}
