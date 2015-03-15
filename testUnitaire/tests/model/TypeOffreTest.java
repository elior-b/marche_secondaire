package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TypeOffreTest {
	
	TypeOffre typeOffre;

	@Before
	public void setUp() throws Exception {
	    typeOffre = new TypeOffre();
	}

	@Test
	public void testGetId() {
		typeOffre.setId(1);
		assertEquals("L'id du type d'offre n'est pas correct ",1,typeOffre.getId());
	}
	@Test
	public void testSetId() {
		typeOffre.setId(1);
		assertEquals("L'id du type d'offre n'est pas correct ",1,typeOffre.getId());
	}
	@Test
	public void testGetNom() {
		typeOffre.setNom("OPTION 120");
		assertEquals("Le nom du type d'offre n'est pas correct ","OPTION 120",typeOffre.getNom());	
	}
	@Test
	public void testSetNom() {
		typeOffre.setNom("OPTION 120");
		assertEquals("Le nom du type d'offre n'est pas correct ","OPTION 120",typeOffre.getNom());	
	}

}
