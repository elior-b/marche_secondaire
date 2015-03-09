package bean;

import java.io.IOException;

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
    

    
    
    public boolean verificationConnexion() throws IOException{
    	
        EntityManager em = null;
        boolean retour = false;
        em = getEntityManager();
        em.getTransaction().begin();
        Query q = em.createNamedQuery("Administrateur.findByLogin");
        q.setParameter("LoginAdministrateur", administrateur.getLogin());
        Administrateur a = (Administrateur) q.getSingleResult();
        FacesContext ctx = FacesContext.getCurrentInstance(); 
        em.close();

        
        if(a.getMotDePasse().equals(administrateur.getMotDePasse())){
        	retour = true;
        	   ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        	   ec.getSessionMap().put("test","JE TEST");

               ec.redirect(ec.getRequestContextPath() + "/bienvenue.xhtml");

        }else{
        	//FacesMessage message = new FacesMessage( "ERREUR DE CONNEXION" );
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage( "ERREUR DE CONNEXION" ));
        	
        	// ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            // ec.redirect(ec.getRequestContextPath() + "/index.xhtml");
        }
        
    	return retour;
    	  	
    }
    
    
    
    public void inscription(){
    	
    	try{
    	  EntityManager em = null;
          em = getEntityManager();
          em.getTransaction().begin();
          
          Administrateur a = new Administrateur();
          
          a.setNom(administrateur.getNom());
          a.setPrenom(administrateur.getPrenom());
          a.setLogin(administrateur.getLogin());
          a.setMotDePasse(administrateur.getMotDePasse());
         
//          a.setNom("azzae");
//          a.setPrenom("aze");
//          a.setLogin("test");
//          a.setMotDePasse("test");
         
          
        
          em.persist(a);
          em.getTransaction().commit();
          em.clear();
    	}catch(Exception e){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage( "ERREUR INSCRIPTION"));

    	}
          
          FacesContext.getCurrentInstance().addMessage(null, new FacesMessage( "SUCCES INSCRIPTION"));

    
          
          
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
   
   public String getNomAdministrateur(){
	   return administrateur.getNom();
   }
   
   public void setNomAdministrateur(String nom){
	   administrateur.setNom(nom);
   }
   
   public String getPrenomAdministrateur(){
	   return administrateur.getPrenom();
   }
   
   public void setPrenomAdministrateur(String prenom){
	   administrateur.setPrenom(prenom);
   }
   
   
   

}
