package model;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class OffreTest {
	Offre offre ;

	@Before
	public void setUp() throws Exception {
		offre = new Offre();
	}

	@Test
	public void testGetId() {
		offre.setId(1);
		assertEquals("L'id de l'enchère n'est pas correct ",1,offre.getId());
	}
	@Test
	public void testSetId() {
		offre.setId(1);
		assertEquals("L'id de l'enchère n'est pas correct ",1,offre.getId());
	}
	@Test
	public void testGetQuantite() {
		offre.setQuantite(23);
		assertEquals("La quantité de titres n'est pas correct ",23, offre.getQuantite());

	}
	@Test
	public void testSetQuantite() {
		offre.setQuantite(23);
		assertEquals("La quantité de titres n'est pas correct ",23, offre.getQuantite());

	}
	@Test
	public void testGetType() {
		offre.setQuantite(1);
		assertEquals("La quantité de titres n'est pas correct ",1, offre.getQuantite());

	}
	@Test
	public void testSetType() {
		offre.setQuantite(1);
		assertEquals("La quantité de titres n'est pas correct ",1, offre.getQuantite());

	}
    @Test
    public void testGetPrix() {
    	offre.setPrix(2000.123);
	   assertEquals(2000.123, offre.getPrix(), 0.001);
     }
    @Test
    public void testSetPrix() {
    	offre.setPrix(2000.123);
	   assertEquals(2000.123, offre.getPrix(), 0.001);
     }
	public void testGetDate()
	{
	    Date date = new Date("23/10/2000");
	    offre.setEcheance(date);
	   
	    assertEquals("La date de l'offre est erronée :", date,offre.getEcheance());
	}
	public void testSetDate()
	{
	    Date date = new Date("23/10/2000");
	    offre.setEcheance(date);
	   
	    assertEquals("La date de l'offre est erronée :", date,offre.getEcheance());
	}
}
