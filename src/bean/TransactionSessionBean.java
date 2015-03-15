package bean;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import model.Investisseur;
import model.Offre;
import model.Transaction;
import utilitaire.JavaPersistenceUtilitaire;

/**
 * Session Bean implementation class TransactionSessionBean
 */
@ManagedBean
@RequestScoped
public class TransactionSessionBean extends JavaPersistenceUtilitaire {
	private List<Transaction> listeTransactions;
	private List<Transaction> listeAdministrateurTransactions;

	/**
	 * Default constructor.
	 */
	public TransactionSessionBean() {
		// TODO Auto-generated constructor stub
		emf = JavaPersistenceUtilitaire.getEmf();
	}

	private EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public List<Transaction> getListeTransactions() {
		EntityManager em;
		em = getEntityManager();
		em.getTransaction().begin();
		ExternalContext ec = FacesContext.getCurrentInstance()
				.getExternalContext();
		int idInvestisseur = (Integer) ec.getSessionMap().get("id");
		Query q = em.createNamedQuery("Investisseur.findById");
		q.setParameter("IdInvestisseur", idInvestisseur);
		Investisseur i = (Investisseur) q.getSingleResult();
		q = em.createNamedQuery("Transaction.findByRecepteur");
		q.setParameter("Recepteur", i);
		listeTransactions = q.getResultList();

		q = em.createNamedQuery("Offre.findByEmetteur");
		q.setParameter("Emetteur", i);
		q.setParameter("Statut", "Terminee");
		List<Offre> listeTmp = q.getResultList();

		for (int j = 0; j < listeTmp.size(); j++) {
			Offre o = listeTmp.get(j);
			q = em.createNamedQuery("Transaction.findByOffre");
			q.setParameter("Offre", o);
			Transaction t = (Transaction) q.getSingleResult();
			listeTransactions.add(t);
		}

		em.clear();
		em.close();
		return listeTransactions;
	}

	public void setListeTransactions(List<Transaction> listeTransactions) {
		this.listeTransactions = listeTransactions;
	}

	public List<Transaction> getListeAdministrateurTransactions() {
		EntityManager em;
		em = getEntityManager();
		em.getTransaction().begin();
		Query q = em.createNamedQuery("Transaction.findAll");
		listeAdministrateurTransactions = q.getResultList();
		em.clear();
		em.close();
		return listeAdministrateurTransactions;
	}

	public void setListeAdministrateurTransactions(
			List<Transaction> listeAdministrateurTransactions) {
		this.listeAdministrateurTransactions = listeAdministrateurTransactions;
	}

}
