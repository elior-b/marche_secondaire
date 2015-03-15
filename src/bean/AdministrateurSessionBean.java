package bean;

import java.io.IOException;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import utilitaire.JavaPersistenceUtilitaire;
import model.Administrateur;

/**
 * Session Bean implementation class AdministrateurSessionBean
 */
@ManagedBean
@RequestScoped
public class AdministrateurSessionBean extends JavaPersistenceUtilitaire {
	Administrateur administrateur = new Administrateur();

	@EJB
	MyTimer timer;

	/**
	 * Default constructor.
	 */
	public AdministrateurSessionBean() {
		// TODO Auto-generated constructor stub
		emf = JavaPersistenceUtilitaire.getEmf();
	}

	private EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public String getLoginAdministrateur() {
		return administrateur.getLogin();
	}

	public void setLoginAdministrateur(String login) {
		administrateur.setLogin(login);
	}

	public void setMotDePasseAdministrateur(String motDePasse) {
		administrateur.setMotDePasse(motDePasse);
	}

	public String getMotDePasseAdministrateur() {
		return administrateur.getMotDePasse();
	}

	public String getNomAdministrateur() {
		return administrateur.getNom();
	}

	public void setNomAdministrateur(String nom) {
		administrateur.setNom(nom);
	}

	public String getPrenomAdministrateur() {
		return administrateur.getPrenom();
	}

	public void setPrenomAdministrateur(String prenom) {
		administrateur.setPrenom(prenom);
	}

}
