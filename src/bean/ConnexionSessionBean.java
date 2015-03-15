package bean;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.webapp.FacesServlet;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import model.*;
import utilitaire.Fonction;
import utilitaire.JavaPersistenceUtilitaire;

/**
 * Session Bean implementation class ConnexionSessionBean
 */
@ManagedBean
@RequestScoped
public class ConnexionSessionBean extends JavaPersistenceUtilitaire {
	Administrateur administrateur = new Administrateur();
	MembreSociete membreSociete = new MembreSociete();
	Investisseur investisseur = new Investisseur();
	String login;
	String motDePasse;

	// EntityManager em;

	/**
	 * Default constructor.
	 */
	public ConnexionSessionBean() {
		// TODO Auto-generated constructor stub
		emf = JavaPersistenceUtilitaire.getEmf();
	}

	private EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public void ValidationConnexion() {
		try {
			String tmp = "";
			tmp = login.substring(0, 3);
			if (tmp.equals("INV")) {
				connexionInvestisseur();
			} else if (tmp.equals("MEM")) {
				connexionMembreSociete();
			} else if (tmp.equals("ADM")) {
				connexionAdministrateur();
			} else {

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void connexionInvestisseur() {
		FacesMessage msg = new FacesMessage();
		try {
			EntityManager em = null;
			em = getEntityManager();
			msg.setSummary("Erreur !");
			em.getTransaction().begin();
			Query q = em.createNamedQuery("Investisseur.findByLogin");
			q.setParameter("LoginInvestisseur", login);
			Investisseur i = (Investisseur) q.getSingleResult();
			String url = "";
			String message = "Bienvenue !";
			em.clear();
			em.close();

			if (i != null) {
				if (i.getMotDePasse().equals(
						Fonction.hashMotDePasse(motDePasse))) {
					if (i.getStatut() == 0) {
						url = "/private/investisseur/ComplementsInformations.xhtml";
					} else if (i.getStatut() == 1) {
						message = "En attente validation admin";
						url = "/public/Connexion.xhtml";
						;
					} else {
						url = "/private/investisseur/Bienvenue.xhtml";
					}

					redirection(i.getId(), i.getNom(), i.getPrenom(), url);
				} else {

					msg.setDetail("Login et/ou mot de passe incorrect(s)");
					FacesContext.getCurrentInstance().addMessage(null, msg);
				}

			} else {

				msg.setDetail("Login et/ou mot de passe incorrect(s)");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}

		} catch (Exception e) {
			msg.setDetail("Une erreur est survenue lors de la connexion.");
			FacesContext.getCurrentInstance().addMessage(null, msg);

		} finally {
		}

	}

	public void connexionMembreSociete() {
		FacesMessage msg = new FacesMessage();
		try {
			EntityManager em = null;
			msg.setSummary("Erreur !");
			em = getEntityManager();
			em.getTransaction().begin();
			Query q = em.createNamedQuery("MembreSociete.findByLogin");
			q.setParameter("LoginMembreSociete", login);
			MembreSociete ms = (MembreSociete) q.getSingleResult();
			String url = "";
			em.clear();
			em.close();

			if (ms != null) {
				if (ms.getMotDePasse().equals(
						Fonction.hashMotDePasse(motDePasse))) {
					url = "/private/membreSociete/Bienvenue.xhtml";
					redirection(ms.getId(), ms.getNom(), ms.getPrenom(), url);
				} else {
					msg.setDetail("Login et/ou mot de passe incorrect(s)");
					FacesContext.getCurrentInstance().addMessage(null, msg);
				}

			} else {
				msg.setDetail("Login et/ou mot de passe incorrect(s)");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}

		} catch (Exception e) {
			msg.setDetail("Une erreur est survenue lors de la connexion");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public void connexionAdministrateur() {
		FacesMessage msg = new FacesMessage();

		try {
			EntityManager em = null;
			msg.setSummary("Erreur !");
			em = getEntityManager();
			em.getTransaction().begin();
			Query q = em.createNamedQuery("Administrateur.findByLogin");
			q.setParameter("LoginAdministrateur", login);
			Administrateur a = (Administrateur) q.getSingleResult();
			String url = "";
			em.clear();
			em.close();

			if (a != null) {
				if (a.getMotDePasse().equals(
						Fonction.hashMotDePasse(motDePasse))) {
					url = "/private/administrateur/Bienvenue.xhtml";
					redirection(a.getId(), a.getNom(), a.getPrenom(), url);
				} else {
					msg.setDetail("Login et/ou mot de passe incorrect(s)");
					FacesContext.getCurrentInstance().addMessage(null, msg);
				}

			} else {
				msg.setDetail("Login et/ou mot de passe incorrect(s)");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}

		} catch (Exception e) {
			msg.setDetail("Une erreur est survenue lors de la connexion");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public void redirection(int id, String nom, String prenom, String url) {
		try {
			ExternalContext ec = FacesContext.getCurrentInstance()
					.getExternalContext();
			ec.getSessionMap().put("id", id);
			ec.getSessionMap().put("nom", nom);
			ec.getSessionMap().put("prenom", prenom);
			ec.redirect(ec.getRequestContextPath() + url);
			// ec.redirect(ec.getRequestContextPath() + "/bienvenue.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

}
