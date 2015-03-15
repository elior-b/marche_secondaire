package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class AdministrateurTest {
	Administrateur adm;

	@Before
	public void setUp() throws Exception {
		 adm = new Administrateur();
	}

	@Test
	public void testGetId() {
		adm.setId(2);
		assertEquals("L'id de l'administrateur n'est pas correct ",2,adm.getId());	
	}
	@Test
	public void testSetId() {
		adm.setId(2);
		assertEquals("L'id de l'administrateur n'est pas correct ",2,adm.getId());	
	}
	@Test
	public void testGetLogin() {
		adm.setLogin("ADM12");
		assertEquals("Le login de l'administrateur n'est correct ","ADM12",adm.getLogin());	
	}
	@Test
	public void testSetLogin() {
		adm.setLogin("ADM12");
		assertEquals("Le login de l'administrateur n'est correct ","ADM12",adm.getLogin());	
	}
	@Test
	public void testGetNom() {
		adm.setNom("Tsila");
		assertEquals("Le nom de l'administrateur n'est pas correct ","Tsila",adm.getNom());	
	}
	@Test
	public void testSetNom() {
		adm.setNom("Tsila");
		assertEquals("Le nom de l'administrateur n'est pas correct ","Tsila",adm.getNom());	
	}
	@Test
	public void testGetPrenom() {
		adm.setPrenom("Claude");
		assertEquals("Le nom de l'administrateur n'est pas correct ","Claude",adm.getPrenom());	
	}
	@Test
	public void testSetPrenom() {
		adm.setPrenom("Claude");
		assertEquals("Le nom de l'administrateur n'est pas correct ","Claude",adm.getPrenom());	
	}


	@Test
	public void testGetMotDePasse() {
		adm.setMotDePasse("asdf");
		assertEquals("Le mot de passe de l'administrateur n'est correct ","asdf",adm.getMotDePasse());	
	}
	@Test
	public void testSetMotDePasse() {
		adm.setMotDePasse("asdf");
		assertEquals("Le mot de passe de l'administrateur n'est correct ","asdf",adm.getMotDePasse());	
	}

}
