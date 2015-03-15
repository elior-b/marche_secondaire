package bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import model.Offre;
import model.TypeContrat;
import model.TypeOffre;
import utilitaire.JavaPersistenceUtilitaire;

/**
 * Session Bean implementation class TypeOffreSessionBean
 */
@ManagedBean
@RequestScoped
public class TypeOffreSessionBean extends JavaPersistenceUtilitaire {
	TypeOffre typeOffre = new TypeOffre();
	private List<TypeOffre> listeTypeOffre;

	// EntityManager em;
	/**
	 * Default constructor.
	 */
	public TypeOffreSessionBean() {
		// TODO Auto-generated constructor stub
		emf = JavaPersistenceUtilitaire.getEmf();
	}

	private EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public int getIdTypeOffre() {
		return typeOffre.getId();
	}

	public void setIdTypeOffre(int id) {
		typeOffre.setId(id);
	}

	public String getNomTypeOffre() {
		return typeOffre.getNom();
	}

	public void setNomTypeOffre(String nom) {
		typeOffre.setNom(nom);
	}

	public List<TypeOffre> getListeTypeOffre() {
		EntityManager em = null;
		em = getEntityManager();
		em.getTransaction().begin();
		Query q = em.createNamedQuery("TypeOffre.findAll");
		listeTypeOffre = q.getResultList();
		em.clear();
		em.close();
		return listeTypeOffre;
	}

	public void setListeTypeOffre(List<TypeOffre> listeTypeOffre) {
		this.listeTypeOffre = listeTypeOffre;
	}

}
