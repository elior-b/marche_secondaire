package bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import model.Enchere;
import model.Investisseur;
import model.Offre;
import utilitaire.JavaPersistenceUtilitaire;

/**
 * Session Bean implementation class EnchereSessionBean
 */
@ManagedBean
@RequestScoped
public class EnchereSessionBean extends JavaPersistenceUtilitaire {

	/**
	 * Default constructor.
	 */
	public EnchereSessionBean() {
		// TODO Auto-generated constructor stub
		emf = JavaPersistenceUtilitaire.getEmf();
	}

	private EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public double meilleureEnchere(int idOffre) {
		// on initialise à la première valeur du tableau
		double retour = 0.0;
		try {
			EntityManager em = null;
			em = getEntityManager();
			em.getTransaction().begin();
			Query q = em.createNamedQuery("Offre.findById");
			q.setParameter("IdOffre", idOffre);
			Offre o = (Offre) q.getSingleResult();

			q = em.createNamedQuery("Enchere.findByOffre");
			q.setParameter("Offre", o);
			List<Enchere> listeEnchere = q.getResultList();
			em.clear();
			em.close();

			ArrayList<Double> array_L = new ArrayList<Double>();
			if (listeEnchere.size() > 0) {
				if (o.getType() == 0) {
					// Achat
					for (int i = 0; i < listeEnchere.size(); i++) {
						array_L.add(listeEnchere.get(i).getPrix());
					}

					Object obj = Collections.min(array_L);
					retour = (Double) obj;
				}

				if (o.getType() == 1) {
					// Vente
					for (int i = 0; i < listeEnchere.size(); i++) {
						array_L.add(listeEnchere.get(i).getPrix());
					}
					Object obj = Collections.max(array_L);
					retour = (Double) obj;
				}
			} else {
				retour = 0.0;
			}
			return retour;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return retour;
		}

	}
}
