package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class InvestisseurTest {
	Investisseur investisseur ;

	@Before
	public void setUp() throws Exception {
		investisseur = new Investisseur();
	}

	@Test
	public void testGetId() {
		investisseur.setId(2);
		assertEquals("L'id de l'investisseur n'est pas correct",2, investisseur.getId());

	}
	@Test
	public void testSetId() {
		investisseur.setId(2);
		assertEquals("L'id de l'investisseur n'est pas correct",2, investisseur.getId());

	}
	@Test
	public void testGetCarteBleue() {
		investisseur.setCarteBleue("ASDLKSK");
		assertEquals("La carte bleue de l'investisseur n'est pas correcte","ASDLKSK", investisseur.getCarteBleue());

	}
	@Test
	public void testSetCarteBleue() {
		investisseur.setCarteBleue("ASDLKSK");
		assertEquals("La carte bleue de l'investisseur n'est pas correcte","ASDLKSK", investisseur.getCarteBleue());

	}
	@Test
	public void testGetCryptogramme() {
		investisseur.setCryptogramme("hdfkl");
		assertEquals("Le cryptogramme de l'investisseur n'est pas correct","hdfkl", investisseur.getCryptogramme());

	}
	@Test
	public void testSetCryptogramme() {
		investisseur.setCryptogramme("hdfkl");
		assertEquals("Le cryptogramme de l'investisseur n'est pas correct","hdfkl", investisseur.getCryptogramme());

	}
	@Test
	public void testGetEmail() {
		investisseur.setEmail("alain@yahoo.fr");
		assertEquals("L'email de l'investisseur n'est pas correct","alain@yahoo.fr", investisseur.getEmail());

	}
	@Test
	public void testSetEmail() {
		investisseur.setEmail("alain@yahoo.fr");
		assertEquals("L'email de l'investisseur n'est pas correct","alain@yahoo.fr", investisseur.getEmail());

	}
	@Test
	public void testGetLogin() {
		investisseur.setLogin("INV223");
		assertEquals("Le login de l'investisseur n'est pas correct","INV223", investisseur.getLogin());
    }
	@Test
	public void testSetLogin() {
		investisseur.setLogin("INV223");
		assertEquals("Le login de l'investisseur n'est pas correct","INV223", investisseur.getLogin());
    }
	@Test
	public void testGetMotDePasse() {
		investisseur.setMotDePasse("dnsjhs");
		assertEquals("Le mot de passe de l'investisseur n'est pas correct","dnsjhs", investisseur.getMotDePasse());
    }
	@Test
	public void testSetMotDePasse() {
		investisseur.setMotDePasse("dnsjhs");
		assertEquals("Le mot de passe de l'investisseur n'est pas correct","dnsjhs", investisseur.getMotDePasse());
    }
	@Test
	public void testGetNom() {
		investisseur.setNom("Tsila");
		assertEquals("Le nom de l'investisseur n'estpas  correct ","Tsila",investisseur.getNom());	
	}
	@Test
	public void testSetNom() {
		investisseur.setNom("Tsila");
		assertEquals("Le nom de l'investisseur n'estpas  correct ","Tsila",investisseur.getNom());	
	}
	@Test
	public void testGetPrenom() {
		investisseur.setPrenom("Claude");
		assertEquals("Le prénom de l'investisseur n'est pas correct ","Claude",investisseur.getPrenom());	
	}
	@Test
	public void testSetPrenom() {
		investisseur.setPrenom("Claude");
		assertEquals("Le prénom de l'investisseur n'est pas correct ","Claude",investisseur.getPrenom());	
	}
	@Test
	public void testGetStatut() {
		investisseur.setStatut(0);
		assertEquals("Le statut de l'investisseur n'est pas correct ",0,investisseur.getStatut());	
	}
	@Test
	public void testSetStatut() {
		investisseur.setStatut(0);
		assertEquals("Le statut de l'investisseur n'est pas correct ",0,investisseur.getStatut());	
	}
    @Test
    public void testSetSolde() {
    	investisseur.setSolde(2000.123);
	   assertEquals(2000.123, investisseur.getSolde(), 0.001);
     }
    @Test
    public void testGetSolde() {
    	investisseur.setSolde(2000.123);
	   assertEquals(2000.123, investisseur.getSolde(), 0.001);
     }

}
