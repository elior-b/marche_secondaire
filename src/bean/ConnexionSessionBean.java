package bean;

import java.io.IOException;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
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
	}

	public void connexionInvestisseur() {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			Query q = em.createNamedQuery("Investisseur.findByLogin");
			q.setParameter("LoginInvestisseur", login);
			Investisseur i = (Investisseur) q.getSingleResult();
			String url = "";
			String message = "Bienvenue !";

			if (i != null) {
				if (i.getMotDePasse().equals(Fonction.hashMotDePasse(motDePasse))) {					
					if(i.getStatut() == 0){
						url = "/private/investisseur/ComplementsInformations.xhtml";
					}else if(i.getStatut() == 1){
						message = "En attente validation admin";
						url = "/public/Connexion.xhtml";;
					}else{
						url = "/private/investisseur/Bienvenue.xhtml";
					}
					FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(message));
					redirection(i.getId(), i.getNom(), i.getPrenom(), url);
				} else {
					FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("ERREUR DE CONNEXION"));
				}

			}else{
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("ERREUR LOGIN - MOT DE PASSE"));
			}

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("ERREUR SURVENUE"));

		} finally {
			em.close();
		}

	}

	public void connexionMembreSociete(){
		EntityManager em = null;

		try {
			em = getEntityManager();
			em.getTransaction().begin();
			Query q = em.createNamedQuery("MembreSociete.findByLogin");
			q.setParameter("LoginMembreSociete", login);
			MembreSociete ms = (MembreSociete) q.getSingleResult();
			String url = "";

			if (ms != null) {
				if (ms.getMotDePasse().equals(motDePasse)) {					
					url = "/private/membreSociete/Bienvenue.xhtml";
					redirection(ms.getId(), ms.getNom(), ms.getPrenom(), url);
				} else {
					FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("ERREUR DE CONNEXION"));
				}

			}else{
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("ERREUR LOGIN - MOT DE PASSE"));
			}

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("ERREUR SURVENUE"));

		} finally {
			em.close();
		}

	}
	
	public void connexionAdministrateur(){
		EntityManager em = null;

		try {

			em = getEntityManager();
			em.getTransaction().begin();
			Query q = em.createNamedQuery("Administrateur.findByLogin");
			q.setParameter("LoginAdministrateur", login);
			Administrateur a = (Administrateur) q.getSingleResult();
			String url = "";

			if (a != null) {
				if (a.getMotDePasse().equals(motDePasse)) {					
					url = "/private/Administrateur/Bienvenue.xhtml";
					redirection(a.getId(), a.getNom(), a.getPrenom(), url);
				} else {
					FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("ERREUR DE CONNEXION"));
				}

			}else{
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("ERREUR LOGIN - MOT DE PASSE"));
			}

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("ERREUR SURVENUE"));

		} finally {
			em.close();
		}
	}
	
	
	
	public void redirection(int id, String nom, String prenom, String url) {
		try {
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
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
