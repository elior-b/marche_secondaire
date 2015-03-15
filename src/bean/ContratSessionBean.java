package bean;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import model.Contrat;
import model.Investisseur;
import model.Societe;
import model.TypeContrat;
import utilitaire.JavaPersistenceUtilitaire;

/**
 * Session Bean implementation class ContratSessionBean
 */
@ManagedBean
@RequestScoped
public class ContratSessionBean extends JavaPersistenceUtilitaire {
	Contrat contrat = new Contrat();
	int responseSelectSociete;
	int responseSelectTypeContrat;
	List<Contrat> listeContratInvestisseur;
	boolean checkbox;

	// EntityManager em;
	/**
	 * Default constructor.
	 */
	public ContratSessionBean() {
		// TODO Auto-generated constructor stub
		emf = JavaPersistenceUtilitaire.getEmf();
	}

	private EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public void insertionContrat() {

		FacesMessage msg = new FacesMessage();
		try {
			EntityManager em = null;
			em = getEntityManager();
			em.getTransaction().begin();
			ExternalContext ec = FacesContext.getCurrentInstance()
					.getExternalContext();
			int idInvestisseur = (Integer) ec.getSessionMap().get("id");
			Query q = em.createNamedQuery("Investisseur.findById");
			q.setParameter("IdInvestisseur", idInvestisseur);
			Investisseur i = (Investisseur) q.getSingleResult();

			if (i != null) {
				if (contrat.getQuantite() > 0) {
					Contrat newContrat = new Contrat();
					Societe s = transfoSociete(responseSelectSociete);
					newContrat.setSociete(s);
					newContrat.setQuantite(contrat.getQuantite());
					TypeContrat tc = transfoTypeContrat(responseSelectTypeContrat);
					newContrat.setTypeContrat(tc);
					newContrat.setInvestisseur(i);

					if (contrat.getDate() != null) {
						newContrat.setDate(contrat.getDate());
					}

					if (isCheckbox()) {
						i.setStatut(1);
						em.persist(i);
						em.persist(newContrat);
						em.getTransaction().commit();
						em.clear();
						em.close();

						ec.redirect(ec.getRequestContextPath() + "/index.xhtml");

					} else {
						msg.setDetail("Votre contrat à bien été ajouté. Vous pouvez continuer !");
						FacesContext.getCurrentInstance().addMessage(null, msg);
						em.persist(newContrat);
						em.getTransaction().commit();
						em.clear();
						em.close();
						ec.redirect(ec.getRequestContextPath()
								+ "/private/investisseur/ComplementsInformations.xhtml");

					}
				} else {
					msg.setSummary("Erreur !");
					msg.setDetail("Erreur quantité");
					FacesContext.getCurrentInstance().addMessage(null, msg);
				}
			} else {
				msg.setSummary("Erreur !");
				msg.setDetail("Erreur investisseur");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
			em.clear();
			em.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Date getDateContrat() {
		return contrat.getDate();
	}

	public void setDateContrat(Date date) {
		contrat.setDate(date);
	}

	public int getQuantiteContrat() {
		return contrat.getQuantite();
	}

	public void setQuantiteContrat(int quantite) {
		contrat.setQuantite(quantite);
	}

	public Investisseur getInvestisseurContrat() {
		return contrat.getInvestisseur();
	}

	public void setInvestisseurContrat(Investisseur investisseur) {
		contrat.setInvestisseur(investisseur);
	}

	public Societe getSocieteContrat() {
		return contrat.getSociete();
	}

	public void setSocieteContrat(Societe societe) {
		contrat.setSociete(societe);
	}

	public TypeContrat getTypeContratContrat() {
		return contrat.getTypeContrat();
	}

	public void setTypeContratContrat(TypeContrat typeContrat) {
		contrat.setTypeContrat(typeContrat);
	}

	public int getResponseSelectSociete() {
		return responseSelectSociete;
	}

	public boolean isCheckbox() {
		return checkbox;
	}

	public void setCheckbox(boolean checkbox) {
		this.checkbox = checkbox;
	}

	public int getResponseSelectTypeContrat() {
		return responseSelectTypeContrat;
	}

	public void setResponseSelectTypeContrat(int responseSelectTypeContrat) {
		this.responseSelectTypeContrat = responseSelectTypeContrat;
	}

	public void setResponseSelectSociete(int responseSelect) {
		this.responseSelectSociete = responseSelect;
	}

	public Societe transfoSociete(int id) {

		EntityManager em = null;
		em = getEntityManager();
		em.getTransaction().begin();
		Query q = em.createNamedQuery("Societe.findById");
		q.setParameter("IdSociete", id);
		Societe s = (Societe) q.getSingleResult();
		em.clear();
		em.close();

		return s;

	}

	public TypeContrat transfoTypeContrat(int id) {

		EntityManager em = null;
		em = getEntityManager();
		em.getTransaction().begin();
		Query q = em.createNamedQuery("TypeContrat.findById");
		q.setParameter("IdTypeContrat", id);
		TypeContrat tc = (TypeContrat) q.getSingleResult();
		em.clear();
		em.close();

		return tc;

	}

	public List<Contrat> getListeContratInvestisseur() {
		EntityManager em = null;
		em = getEntityManager();
		em.getTransaction().begin();
		ExternalContext ec = FacesContext.getCurrentInstance()
				.getExternalContext();
		int idInvestisseur = (Integer) ec.getSessionMap().get("id");
		Query q = em.createNamedQuery("Investisseur.findById");
		q.setParameter("IdInvestisseur", idInvestisseur);
		Investisseur i = (Investisseur) q.getSingleResult();

		q = em.createNamedQuery("Contrat.findByInvestisseur");
		q.setParameter("Investisseur", i);
		listeContratInvestisseur = q.getResultList();
		em.clear();
		em.close();

		return listeContratInvestisseur;
	}

	public void setListeContratInvestisseur(List<Contrat> listeContrat) {
		this.listeContratInvestisseur = listeContrat;
	}

}
