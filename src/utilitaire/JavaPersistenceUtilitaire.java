/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitaire;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JavaPersistenceUtilitaire {

	public static EntityManagerFactory emf;

	static {
		emf = Persistence.createEntityManagerFactory("marche_secondaire");
	}

	public static EntityManagerFactory getEmf() {
		return emf;
	}
}
