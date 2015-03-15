package bean;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.ejb.Stateful;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import model.Investisseur;
import model.MembreSociete;
import model.Societe;
import utilitaire.Fonction;
import utilitaire.JavaPersistenceUtilitaire;

/**
 * Session Bean implementation class MembreSocieteSessionBean
 */
@ManagedBean
@RequestScoped
public class MembreSocieteSessionBean extends JavaPersistenceUtilitaire {
	MembreSociete membreSociete = new MembreSociete();
	private List<MembreSociete> listeMembres;
	int responseSelectSociete;
	String confirmationMotDePasse;

	/**
	 * Default constructor.
	 */

	public MembreSocieteSessionBean() {
		emf = JavaPersistenceUtilitaire.getEmf();
	}

	private EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public void inscription() {
		FacesMessage msg = new FacesMessage();

		try {
			EntityManager em = null;
			em = getEntityManager();
			msg.setSummary("Erreur !");
			em.getTransaction().begin();
			MembreSociete ms = new MembreSociete();
			ExternalContext ec = FacesContext.getCurrentInstance()
					.getExternalContext();

			if (membreSociete.getMotDePasse().equals(confirmationMotDePasse)) {

				ms.setNom(membreSociete.getNom());
				ms.setPrenom(membreSociete.getPrenom());
				String login = Fonction.generateString(5, "MEM");
				ms.setLogin(login);

				ms.setMotDePasse(Fonction.hashMotDePasse(membreSociete
						.getMotDePasse()));
				ms.setEmail(membreSociete.getEmail());

				Societe s = transfoSociete(getResponseSelectSociete());
				ms.setSociete(s);
				em.persist(ms);
				em.getTransaction().commit();
				em.clear();
				em.close();

				ec.redirect(ec.getRequestContextPath()
						+ "/private/administrateur/Bienvenue.xhtml");

			} else {
				msg.setDetail("Erreur mot de passe");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
			em.clear();
			em.close();
		} catch (Exception e) {
			msg.setDetail("Erreur inscription");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

	}

	public void setMotDePasseMembreSociete(String motDePasse) {
		membreSociete.setMotDePasse(motDePasse);
	}

	public String getMotDePasseMembreSociete() {
		return membreSociete.getMotDePasse();
	}

	public String getNomMembreSociete() {
		return membreSociete.getNom();
	}

	public void setNomMembreSociete(String nom) {
		membreSociete.setNom(nom);
	}

	public String getPrenomMembreSociete() {
		return membreSociete.getPrenom();
	}

	public void setPrenomMembreSociete(String prenom) {
		membreSociete.setPrenom(prenom);
	}

	public String getEmailMembreSociete() {
		return membreSociete.getEmail();
	}

	public void setEmailMembreSociete(String email) {
		membreSociete.setEmail(email);
	}

	public Societe getSociete() {
		return membreSociete.getSociete();
	}

	public void setSociete(Societe societe) {
		membreSociete.setSociete(societe);
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

	public int getResponseSelectSociete() {
		return responseSelectSociete;
	}

	public void setResponseSelectSociete(int responseSelectSociete) {
		this.responseSelectSociete = responseSelectSociete;
	}

	public String getConfirmationMotDePasse() {
		return confirmationMotDePasse;
	}

	public void setConfirmationMotDePasse(String confirmationMotDePasse) {
		this.confirmationMotDePasse = confirmationMotDePasse;
	}

	public List<MembreSociete> getListeMembres() {
		EntityManager em = null;
		em = getEntityManager();
		em.getTransaction().begin();
		Query q = em.createNamedQuery("MembreSociete.findAll");
		List<MembreSociete> listeMembres = q.getResultList();
		em.clear();
		em.close();
		return listeMembres;
	}

	public void setListeMembres(List<MembreSociete> listeMembres) {
		this.listeMembres = listeMembres;
	}

}
