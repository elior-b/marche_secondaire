package bean;

import javax.ejb.Stateful;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
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
    

    
    
    public String verificationConnexion(){
    	
        EntityManager em = null;
        String retour = "PASOK";
        em = getEntityManager();
        em.getTransaction().begin();
        Query q = em.createNamedQuery("Administrateur.findByLogin");
        q.setParameter("LoginAdministrateur", administrateur.getLogin());
        Administrateur a = (Administrateur) q.getSingleResult();
        
        if(a.getMotDePasse().equals(administrateur.getMotDePasse())){
        	retour = "OK";
        }

    	return retour;
    	  	
    }
    
    
    
   public String getLoginAdministrateur(){
	   return administrateur.getLogin();
   }
   
   public void setLoginAdministrateur(String login){
	   administrateur.setLogin(login);
   }
   public void setMotDePasseAdministrateur(String motDePasse){
	   administrateur.setMotDePasse(motDePasse);
   }
   
   
   public String getMotDePasseAdministrateur(){
	   return administrateur.getMotDePasse();
   }
   

}
