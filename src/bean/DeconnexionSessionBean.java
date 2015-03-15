package bean;

import java.io.IOException;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import utilitaire.JavaPersistenceUtilitaire;

/**
 * Session Bean implementation class DeconnexionSessionBean
 */
@ManagedBean
@RequestScoped
public class DeconnexionSessionBean extends JavaPersistenceUtilitaire {

	/**
	 * Default constructor.
	 */
	public DeconnexionSessionBean() {
		// TODO Auto-generated constructor stub
		emf = JavaPersistenceUtilitaire.getEmf();
	}

	private EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public void deconnexion() {

		try {
			ExternalContext ec = FacesContext.getCurrentInstance()
					.getExternalContext();
			ec.getSessionMap().clear();
			ec.redirect(ec.getRequestContextPath() + "/index.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
