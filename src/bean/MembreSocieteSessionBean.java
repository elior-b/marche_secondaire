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


import model.MembreSociete;
import model.Societe;
import utilitaire.JavaPersistenceUtilitaire;

/**
 * Session Bean implementation class MembreSocieteSessionBean
 */
@ManagedBean
@RequestScoped
public class MembreSocieteSessionBean  extends JavaPersistenceUtilitaire{
	MembreSociete membreSociete = new MembreSociete();
	int responseSelect;
    /**
     * Default constructor. 
     */	    

	public MembreSocieteSessionBean() {
    	emf = JavaPersistenceUtilitaire.getEmf();    
    	}
	
	private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
	
	 public void inscription(){
	    	
	    	
	    	  EntityManager em = null;
	          em = getEntityManager();
	          em.getTransaction().begin();
	          
	          MembreSociete ms = new MembreSociete();
	          
	          ms.setNom(membreSociete.getNom());
	          ms.setPrenom(membreSociete.getPrenom());
	          ms.setLogin("azeaz");
	          ms.setMotDePasse(membreSociete.getMotDePasse());
	          ms.setEmail(membreSociete.getEmail());
	         
	          Societe s = transfoSociete(getResponseSelect());
	          //membreSociete.setSociete(s);
	          ms.setSociete(s);
	          em.persist(ms);
	          em.getTransaction().commit();
	          em.close();
	    	
//	    	catch(Exception e){
//	            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage( "ERREUR INsdfsdfSCRIPTION"));
//
//	    	}
	          
	          FacesContext.getCurrentInstance().addMessage(null, new FacesMessage( "SUCCES INSCRIPTION"));

	    
	          
	          
	    }
//	   public String getLoginmembreSociete(){
//		   return membreSociete.getLogin();
//	   }
//	   
//	   public void setLoginmembreSociete(String login){
//		   membreSociete.setLogin(login);
//	   }
	   public void setMotDePasseMembreSociete(String motDePasse){
		   membreSociete.setMotDePasse(motDePasse);
	   }
	   
	   
	   public String getMotDePasseMembreSociete(){
		   return membreSociete.getMotDePasse();
	   }
	   
	   public String getNomMembreSociete(){
		   return membreSociete.getNom();
	   }
	   
	   public void setNomMembreSociete(String nom){
		   membreSociete.setNom(nom);
	   }
	   
	   public String getPrenomMembreSociete(){
		   return membreSociete.getPrenom();
	   }
	   
	   public void setPrenomMembreSociete(String prenom){
		   membreSociete.setPrenom(prenom);
	   }
	   public String getEmailMembreSociete(){
		   return membreSociete.getEmail();
	   }
	   
	   public void setEmailMembreSociete(String email){
		   membreSociete.setEmail(email);
	   }
	   

		public Societe getSociete() {
			return membreSociete.getSociete();
		}

		public void setSociete(Societe societe) {
			membreSociete.setSociete(societe);
		}
	   
	   
	   
	   public Societe transfoSociete(int id){
		
			  	EntityManager em = null;
		        em = getEntityManager();
		        em.getTransaction().begin();
		        Query q = em.createNamedQuery("Societe.findById");
		        q.setParameter("IdSociete", id);
		        Societe s = (Societe) q.getSingleResult();  
	
	    		return s;
   
	   }

	public int getResponseSelect() {
		return responseSelect;
	}

	public void setResponseSelect(int responseSelect) {
		this.responseSelect = responseSelect;
	}
	   
	   
	   
	   
	   


}
