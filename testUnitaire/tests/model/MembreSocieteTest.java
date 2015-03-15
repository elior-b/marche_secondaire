package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MembreSocieteTest {
	MembreSociete membreSociete;
	@Before
	public void setUp() throws Exception {
		membreSociete = new MembreSociete();
	}

	@Test
	public void testGetId() {
		membreSociete.setId(2);
		assertEquals("L'id du membre societe n'est pas correct",2, membreSociete.getId());

	}
	@Test
	public void testSetId() {
		membreSociete.setId(2);
		assertEquals("L'id du membre societe n'est pas correct",2, membreSociete.getId());

	}
	@Test
	public void testGetEmail() {
		membreSociete.setEmail("alain@yahoo.fr");
		assertEquals("L'email de l'investisseur n'est pas correct","alain@yahoo.fr", membreSociete.getEmail());

	}
	@Test
	public void testSetEmail() {
		membreSociete.setEmail("alain@yahoo.fr");
		assertEquals("L'email de l'investisseur n'est pas correct","alain@yahoo.fr", membreSociete.getEmail());

	}
	@Test
	public void testGetLogin() {
		membreSociete.setLogin("MEM223");
		assertEquals("Le login de l'investisseur n'est pas correct","MEM223", membreSociete.getLogin());
    }
	@Test
	public void testSetLogin() {
		membreSociete.setLogin("MEM223");
		assertEquals("Le login de l'investisseur n'est pas correct","MEM223", membreSociete.getLogin());
    }
	@Test
	public void testGetMotDePasse() {
		membreSociete.setMotDePasse("dnsjhs");
		assertEquals("Le mot de passe de l'investisseur n'est pas correct","dnsjhs", membreSociete.getMotDePasse());
    }
	@Test
	public void testSetMotDePasse() {
		membreSociete.setMotDePasse("dnsjhs");
		assertEquals("Le mot de passe de l'investisseur n'est pas correct","dnsjhs", membreSociete.getMotDePasse());
    }
	@Test
	public void testGetNom() {
		membreSociete.setNom("Tsila");
		assertEquals("Le nom de l'investisseur n'estpas  correct ","Tsila",membreSociete.getNom());	
	}
	@Test
	public void testSetNom() {
		membreSociete.setNom("Tsila");
		assertEquals("Le nom de l'investisseur n'estpas  correct ","Tsila",membreSociete.getNom());	
	}
	@Test
	public void testGetPrenom() {
		membreSociete.setPrenom("Claude");
		assertEquals("Le prénom de l'investisseur n'est pas correct ","Claude",membreSociete.getPrenom());	
	}
	@Test
	public void testSetPrenom() {
		membreSociete.setPrenom("Claude");
		assertEquals("Le prénom de l'investisseur n'est pas correct ","Claude",membreSociete.getPrenom());	
	}


}
