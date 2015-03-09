package bean;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
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

import model.Administrateur;
import model.Investisseur;
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
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();

			Investisseur newInvestisseur = new Investisseur();

			/*
			 * Verification de la conformiter du champ SOLDE
			 */
			if (investisseur.getSolde() > 0 ) {
				/*
				 * Verification de la conformiter des champs CARTE BLEUE &
				 * CRYTPOGRAMME
				 */
				if (Fonction.verificationChaineRegex(investisseur.getCarteBleue(), 16) && Fonction.verificationNumRegex(investisseur.getCryptogramme(), 3)) {
					/*
					 * Verification de la conformiter du champs MAIL
					 */
					if (investisseur.getEmail().contains("@")) {
						/*
						 * Verification du mot de passe
						 */
						if (investisseur.getMotDePasse().equals(confirmationMotDePasse)) {

							/*
							 * Tout est OK
							 */

							newInvestisseur.setNom(investisseur.getNom());
							newInvestisseur.setPrenom(investisseur.getPrenom());
							newInvestisseur.setEmail(investisseur.getEmail());
							newInvestisseur.setSolde(investisseur.getSolde());
							newInvestisseur.setCarteBleue(investisseur.getCarteBleue());
							newInvestisseur.setCryptogramme(investisseur.getCryptogramme());
							newInvestisseur.setStatut(0);
							String login = Fonction.generateString(5, "INV");
							newInvestisseur.setLogin(login);
							newInvestisseur.setMotDePasse(Fonction.hashMotDePasse(investisseur.getMotDePasse()));

							em.persist(newInvestisseur);
							em.getTransaction().commit();
							//em.close();
							
							FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Inscription Effectuee - Votre Login est :" +login ));

						}else{
							FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Erreur Sur le Mot De Passe"));
						}

					}else{
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Erreur Sur l'EMAIL"));

					}

				}else{
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Erreur Sur la CARTE BLEUE OU CRYPTO"));

				}
			}else{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Erreur Sur le montant"));

			}


		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Une Erreur est survenue lors de cette inscription"));

		} finally {
			em.close();

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
	
	
	
	

}
