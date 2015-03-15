package bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.Administrateur;
import model.TypeContrat;
import sun.net.spi.nameservice.NameService;
import utilitaire.JavaPersistenceUtilitaire;

/**
 * Session Bean implementation class TypeCompteSessionBean
 */
@ManagedBean
@RequestScoped
public class TypeContratSessionBean extends JavaPersistenceUtilitaire {
	TypeContrat typeContrat = new TypeContrat();
	private List<TypeContrat> listeTypeContrat;

	// EntityManager em;

	/**
	 * Default constructor.
	 */
	public TypeContratSessionBean() {
		// TODO Auto-generated constructor stub
		emf = JavaPersistenceUtilitaire.getEmf();
	}

	private EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public TypeContrat getTypeContrat() {

		return typeContrat;
	}

	public void setTypeContrat(TypeContrat typeContrat) {
		this.typeContrat = typeContrat;
	}

	public List<TypeContrat> getListeTypeContrat() {
		EntityManager em = null;
		em = getEntityManager();

		em.getTransaction().begin();
		Query q = em.createNamedQuery("TypeContrat.findAll");
		listeTypeContrat = q.getResultList();
		em.clear();
		em.close();
		return listeTypeContrat;
	}

	public void setListeTypeContrat(List<TypeContrat> listeTypeContrat) {
		this.listeTypeContrat = listeTypeContrat;
	}

	public int getIdTypeContrat() {
		return typeContrat.getId();
	}

	public void setIdTypeContrat(int id) {
		typeContrat.setId(id);
	}

	public String getNom() {
		return typeContrat.getNom();
	}

	public void setNom(String nom) {
		typeContrat.setNom(nom);
	}

}
