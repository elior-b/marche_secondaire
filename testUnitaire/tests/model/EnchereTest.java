package model;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class EnchereTest {
	Enchere enchere ;
	 
	@Before
	public void setUp() throws Exception {
		enchere = new Enchere();
	}

	@Test
	public void testGetId() {
		enchere.setId(1);
		assertEquals("L'id de l'enchère n'est pas correct ",1,enchere.getId());
	}
	@Test
	public void testSetId() {
		enchere.setId(1);
		assertEquals("L'id de l'enchère n'est pas correct ",1,enchere.getId());
	}
	@Test
	public void testGetNom() {
		Investisseur investisseur = new Investisseur();
		investisseur.setNom("TORO");
		enchere.setInvestisseur(investisseur);
		
		assertEquals("Le nom de l'investisseur n'est pas correct ","TORO",enchere.getInvestisseur().getNom());
	}
	@Test
	public void testSetNom() {
		Investisseur investisseur = new Investisseur();
		investisseur.setNom("TORO");
		enchere.setInvestisseur(investisseur);
		
		assertEquals("Le nom de l'investisseur n'est pas correct ","TORO",enchere.getInvestisseur().getNom());
	}
    @Test
    public void testGetPrix() {
  	  enchere.setPrix(2000.123);
	   assertEquals(2000.123, enchere.getPrix(), 0.001);
     }
    @Test
    public void testSetPrix() {
  	  enchere.setPrix(2000.123);
	   assertEquals(2000.123, enchere.getPrix(), 0.001);
     }
	public void testGetDate()
	{
	    Date date = new Date("23/10/2000");
	    enchere.setDate(date);
	   
	    assertEquals("La date du contrat est erronée :", date,enchere.getDate());
	}
	public void testSetDate()
	{
	    Date date = new Date("23/10/2000");
	    enchere.setDate(date);
	   
	    assertEquals("La date du contrat est erronée :", date,enchere.getDate());
	}
}
