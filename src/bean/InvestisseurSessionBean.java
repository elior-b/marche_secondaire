package bean;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import model.Administrateur;
import model.Contrat;
import model.DonneeFinanciere;
import model.Investisseur;
import model.MembreSociete;
import model.Offre;
import model.Societe;
import utilitaire.Fonction;
import utilitaire.JavaPersistenceUtilitaire;

/**
 * Session Bean implementation class InvestisseurSessionBean
 */
@ManagedBean
@RequestScoped
public class InvestisseurSessionBean extends JavaPersistenceUtilitaire {
	Investisseur investisseur = new Investisseur();
	String confirmationMotDePasse;
	private List<Investisseur> ListeInvestisseurAValider;
	private List<Investisseur> ListeInvestisseurs;
	double solde;

	/**
	 * Default constructor.
	 */
	public InvestisseurSessionBean() {
		// TODO Auto-generated constructor stub
		emf = JavaPersistenceUtilitaire.getEmf();
	}

	private EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public void inscriptionInvestisseur() throws IOException {
		FacesMessage msg = new FacesMessage();
		try {
			EntityManager em = null;
			msg.setSummary("Erreur !");
			em = getEntityManager();
			em.getTransaction().begin();
			Investisseur newInvestisseur = new Investisseur();

			/*
			 * Verification de la conformiter du champ SOLDE
			 */
			if (investisseur.getSolde() > 0) {
				/*
				 * Verification de la conformiter des champs CARTE BLEUE &
				 * CRYTPOGRAMME
				 */
				if (Fonction.verificationChaineRegex(
						investisseur.getCarteBleue(), 16)
						&& Fonction.verificationNumRegex(
								investisseur.getCryptogramme(), 3)) {
					/*
					 * Verification de la conformiter du champs MAIL
					 */
					if (investisseur.getEmail().contains("@")) {
						/*
						 * Verification du mot de passe
						 */
						if (investisseur.getMotDePasse().equals(
								confirmationMotDePasse)) {

							/*
							 * Tout est OK
							 */

							newInvestisseur.setNom(investisseur.getNom());
							newInvestisseur.setPrenom(investisseur.getPrenom());
							newInvestisseur.setEmail(investisseur.getEmail());
							newInvestisseur.setSolde(investisseur.getSolde());
							newInvestisseur.setCarteBleue(investisseur
									.getCarteBleue());
							newInvestisseur.setCryptogramme(investisseur
									.getCryptogramme());
							newInvestisseur.setStatut(0);
							String login = Fonction.generateString(5, "INV");
							newInvestisseur.setLogin(login);
							newInvestisseur.setMotDePasse(Fonction
									.hashMotDePasse(investisseur
											.getMotDePasse()));

							em.persist(newInvestisseur);
							em.getTransaction().commit();

							msg.setSummary("Felicitations !");
							msg.setDetail("Inscription effectuee - Votre login est :"
									+ login);
							FacesContext.getCurrentInstance().addMessage(null,
									msg);

						} else {
							msg.setDetail("Erreur mot de passe");
							FacesContext.getCurrentInstance().addMessage(null,
									msg);
						}

					} else {
						msg.setDetail("Erreur email");
						FacesContext.getCurrentInstance().addMessage(null, msg);
					}

				} else {
					msg.setDetail("Erreur CB/Cryto");
					FacesContext.getCurrentInstance().addMessage(null, msg);
				}
			} else {
				msg.setDetail("Erreur montant");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}

			em.clear();
			em.close();
		} catch (Exception e) {
			msg.setDetail("Erreur inscription");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

	}

	public String getCarteBleueInvestisseur() {
		return investisseur.getCarteBleue();
	}

	public void setCarteBleueInvestisseur(String carteBleue) {
		investisseur.setCarteBleue(carteBleue);
	}

	public String getCryptogrammeInvestisseur() {
		return investisseur.getCryptogramme();
	}

	public void setCryptogrammeInvestisseur(String cryptogramme) {
		investisseur.setCryptogramme(cryptogramme);
	}

	public String getEmailInvestisseur() {
		return investisseur.getEmail();
	}

	public void setEmailInvestisseur(String email) {
		investisseur.setEmail(email);
	}

	public String getNomInvestisseur() {
		return investisseur.getNom();
	}

	public void setNomInvestisseur(String nom) {
		investisseur.setNom(nom);
	}

	public String getPrenomInvestisseur() {
		return investisseur.getPrenom();
	}

	public void setPrenomInvestisseur(String prenom) {
		investisseur.setPrenom(prenom);
	}

	public double getSoldeInvestisseur() {
		return investisseur.getSolde();
	}

	public void setSoldeInvestisseur(double solde) {
		investisseur.setSolde(solde);
	}

	public Object getStatutInvestisseur() {
		return investisseur.getStatut();
	}

	public void setStatutInvestisseur(int statut) {
		investisseur.setStatut(statut);
	}

	public String getLoginInvestisseur() {
		return investisseur.getLogin();
	}

	public void setLoginInvestisseur(String login) {
		investisseur.setLogin(login);
	}

	public String getMotDePasseInvestisseur() {
		return investisseur.getMotDePasse();
	}

	public void setMotDePasseInvestisseur(String motDePasse) {
		investisseur.setMotDePasse(motDePasse);
	}

	public String getConfirmationMotDePasse() {
		return confirmationMotDePasse;
	}

	public void setConfirmationMotDePasse(String confirmationMotDePasse) {
		this.confirmationMotDePasse = confirmationMotDePasse;
	}

	public List<Investisseur> getListeInvestisseurAValider() {
		EntityManager em;
		em = getEntityManager();
		em.getTransaction().begin();

		Query q = em.createNamedQuery("Investisseur.findByStatut");
		q.setParameter("Statut", 1);
		ListeInvestisseurAValider = q.getResultList();

		em.clear();
		em.close();
		return ListeInvestisseurAValider;
	}

	public void setListeInvestisseurAValider(
			List<Investisseur> listeInvestisseurAValider) {
		ListeInvestisseurAValider = listeInvestisseurAValider;
	}

	public void voirContrat() {
		try {
			EntityManager em = null;
			em = getEntityManager();
			em.getTransaction().begin();
			ExternalContext ec = FacesContext.getCurrentInstance()
					.getExternalContext();
			Map<String, String> params = ec.getRequestParameterMap();
			int idInvestisseurParam = Integer.parseInt(params.get("id"));
			Query q = em.createNamedQuery("Investisseur.findById");
			q.setParameter("IdInvestisseur", idInvestisseurParam);
			Investisseur i = (Investisseur) q.getSingleResult();

			q = em.createNamedQuery("Contrat.findByInvestisseur");
			q.setParameter("Investisseur", i);
			List<Contrat> listeContrats = q.getResultList();
			ec.getSessionMap().put("listeContrats", listeContrats);
			ec.getSessionMap().put("idInvestisseur", idInvestisseurParam);
			em.clear();
			em.close();
			ec.redirect(ec.getRequestContextPath()
					+ "/private/administrateur/ValidationContratInvestisseur.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void validerProfil() {
		FacesMessage msg = new FacesMessage();
		try {
			EntityManager em = null;
			em = getEntityManager();
			msg.setSummary("Erreur !");
			em.getTransaction().begin();
			ExternalContext ec = FacesContext.getCurrentInstance()
					.getExternalContext();
			int idInvestisseur = (Integer) ec.getSessionMap().get(
					"idInvestisseur");
			Query q = em.createNamedQuery("Investisseur.findById");
			q.setParameter("IdInvestisseur", idInvestisseur);
			Investisseur i = (Investisseur) q.getSingleResult();

			i.setStatut(2);
			em.persist(i);
			em.getTransaction().commit();
			em.clear();
			em.close();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("INVESTISSEUR VALIDE"));

			ec.redirect(ec.getRequestContextPath()
					+ "/private/administrateur/ValidationInvestisseur.xhtml");
		} catch (IOException e) {
			msg.setDetail("Erreur validation");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

	}

	public List<Investisseur> getListeInvestisseurs() {
		EntityManager em = null;
		em = getEntityManager();
		em.getTransaction().begin();
		Query q = em.createNamedQuery("Investisseur.findAll");
		List<Investisseur> ListeInvestisseurs = q.getResultList();
		em.clear();
		em.close();
		return ListeInvestisseurs;
	}

	public void setListeInvestisseurs(List<Investisseur> listeInvestisseurs) {
		ListeInvestisseurs = listeInvestisseurs;
	}

	public double getSolde() {
		EntityManager em = null;
		em = getEntityManager();
		em.getTransaction().begin();
		ExternalContext ec = FacesContext.getCurrentInstance()
				.getExternalContext();
		int idInvestisseur = (Integer) ec.getSessionMap().get("id");
		Query q = em.createNamedQuery("Investisseur.findById");
		q.setParameter("IdInvestisseur", idInvestisseur);
		Investisseur i = (Investisseur) q.getSingleResult();
		solde = i.getSolde();
		em.clear();
		em.close();
		return solde;
	}

	public void setSolde(double solde) {
		this.solde = solde;
	}
	
	

}
