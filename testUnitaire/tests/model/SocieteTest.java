package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SocieteTest {
	Societe societe;

	@Before
	public void setUp() throws Exception {
		societe = new Societe();
	}

	public void testGetId() {
		societe.setId(1);
		assertEquals("L'id de l'enchère n'est pas correct ",1,societe.getId());
	}
	public void testSetId() {
		societe.setId(1);
		assertEquals("L'id de l'enchère n'est pas correct ",1,societe.getId());
	}
	public void testGetAdresse() {
		societe.setAdresse("23, Rue Coupet Paris");
		assertEquals("L'adresse de la société n'est pas correcte ","23, Rue Coupet Paris",societe.getAdresse());
	}
	public void testSetAdresse() {
		societe.setAdresse("23, Rue Coupet Paris");
		assertEquals("L'adresse de la société n'est pas correcte ","23, Rue Coupet Paris",societe.getAdresse());
	}
	public void testGetDescription() {
		societe.setDescription("Société du secteur Immobilier");
		assertEquals("L'adresse de la société n'est pas correcte ","Société du secteur Immobilier",societe.getDescription());
	}
	public void testSetDescription() {
		societe.setDescription("Société du secteur Immobilier");
		assertEquals("L'adresse de la société n'est pas correcte ","Société du secteur Immobilier",societe.getDescription());
	}
	@Test
	public void testGetDirigeant() {
		societe.setDirigeant("Dori");
		assertEquals("Le nom du dirgeant n'est pas correct ","Dori",societe.getDirigeant());	
	}
	@Test
	public void testSetDirigeant() {
		societe.setDirigeant("Dori");
		assertEquals("Le nom du dirgeant n'est pas correct ","Dori",societe.getDirigeant());	
	}

	@Test
	public void testGetNom() {
		societe.setNom("E-BODY SPA");
		assertEquals("Le nom de la  sociétén'est pas correcte ","E-BODY SPA",societe.getNom());	
	}
	@Test
	public void testSetNom() {
		societe.setNom("E-BODY SPA");
		assertEquals("Le nom de la  sociétén'est pas correcte ","E-BODY SPA",societe.getNom());	
	}
	@Test
	public void testGetRaisonSociale() {
		societe.setRaisonSociale("E-BODY MERCHANDISING SPA");
		assertEquals("Le raison sociale de la  sociétén'est pas correcte ","E-BODY MERCHANDISING SPA",societe.getRaisonSociale());	
	}
	@Test
	public void testSetRaisonSociale() {
		societe.setRaisonSociale("E-BODY MERCHANDISING SPA");
		assertEquals("Le raison sociale de la  sociétén'est pas correcte ","E-BODY MERCHANDISING SPA",societe.getRaisonSociale());	
	}
	@Test
	public void testGetSecteurActivite() {
		societe.setSecteurActivite("Vente d'armes");
		assertEquals("Le secteur d'activité n'est pas correct ","Vente d'armes",societe.getSecteurActivite());	
	
	}
	@Test
	public void testSetSecteurActivite() {
		societe.setSecteurActivite("Vente d'armes");
		assertEquals("Le secteur d'activité n'est pas correct ","Vente d'armes",societe.getSecteurActivite());	
	
	}
	@Test
	public void testGetSiteWeb() {
		societe.setSiteWeb("www.tsilamerchandising.com");
		assertEquals("Le nom de l'administrateur n'est pas correct ","www.tsilamerchandising.com",societe.getSiteWeb());	
	}
	@Test
	public void testSetSiteWeb() {
		societe.setSiteWeb("www.tsilamerchandising.com");
		assertEquals("Le nom de l'administrateur n'est pas correct ","www.tsilamerchandising.com",societe.getSiteWeb());	
	}
	@Test
	public void testGetTelephone() {
		societe.setTelephone("0220030300");
		assertEquals("Le téléphone de la société n'est pas correct ","0220030300",societe.getTelephone());	
	}
	@Test
	public void testSetTelephone() {
		societe.setTelephone("0220030300");
		assertEquals("Le téléphone de la société n'est pas correct ","0220030300",societe.getTelephone());	
	}
    @Test
    public void testGetCapitalisationBoursiere() {
  	  societe.setCapitalisationBoursiere(2000.123);
	   assertEquals(2000.123, societe.getCapitalisationBoursiere(), 0.001);
     }
}
