package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TypeContratTest {
	TypeContrat typeContrat ;

	@Before
	public void setUp() throws Exception {
	          typeContrat = new TypeContrat();
	}

	@Test
	public void testGetId() {
		typeContrat.setId(1);
		assertEquals("L'id du type contrat n'est pas correct ",1,typeContrat.getId());
	}
	@Test
	public void testSetId() {
		typeContrat.setId(1);
		assertEquals("L'id du type contrat n'est pas correct ",1,typeContrat.getId());
	}
	@Test
	public void testGetNom() {
		typeContrat.setNom("STOCK OPTION");
		assertEquals("Le nom du type contrat n'est pas correct ","STOCK OPTION",typeContrat.getNom());	
	}
	@Test
	public void testSetNom() {
		typeContrat.setNom("STOCK OPTION");
		assertEquals("Le nom du type contrat n'est pas correct ","STOCK OPTION",typeContrat.getNom());	
	}

}
