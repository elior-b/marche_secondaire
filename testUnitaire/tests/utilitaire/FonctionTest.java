/**
 * 
 */
package utilitaire;

import static org.junit.Assert.*;

import java.security.NoSuchAlgorithmException;

import org.junit.Before;
import org.junit.Test;

import utilitaire.Fonction;

/**
 * @author haka team
 *
 */
public class FonctionTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		Fonction fonction = new Fonction();
	}

	@Test
	public void testHashMotDePasse() throws NoSuchAlgorithmException {
		String mot_de_passe = "test";
		String hash = "c00501f3f0524a6a74cdbe2cc80b4ecc841282492e751157a55f0133039a558f";
		assertEquals( "Erreur", hash, Fonction.hashMotDePasse(mot_de_passe) );
	}
	
	@Test
	public void testVerificationChaineRegexTrue(){
		String chaine = "test123";
		int num =  7;
		assertEquals("Erreur", true, Fonction.verificationChaineRegex(chaine, num));
	}
	
	@Test
	public void testVerificationChaineRegexFalse(){
		String chaine = "test*$%";
		int num =  7;
		assertEquals("Erreur", false, Fonction.verificationChaineRegex(chaine, num));
	}
	
	@Test
	public void testVerificationNumRegexTrue(){
		String chaine = "12345";
		int num =  5;
		assertEquals("Erreur", true, Fonction.verificationNumRegex(chaine, num));
	}
	
	@Test
	public void testVerificationNumRegexFalse(){
		String chaine = "a2345";
		int num =  5;
		assertEquals("Erreur", false, Fonction.verificationNumRegex(chaine, num));
	}
	
	@Test
	public void testGenerateString(){
		String base = "abcde";
		int num =  5;
		assertEquals("Erreur", 10, Fonction.generateString(num, base).length());
	}
	
}
