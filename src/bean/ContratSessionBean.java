package bean;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import model.Contrat;
import model.Investisseur;
import model.Societe;
import model.TypeContrat;
import utilitaire.JavaPersistenceUtilitaire;

/**
 * Session Bean implementation class ContratSessionBean
 */
@ManagedBean
@RequestScoped
public class ContratSessionBean extends JavaPersistenceUtilitaire {
	Contrat contrat = new Contrat();
	int responseSelectSociete;
	int responseSelectTypeContrat;
	boolean checkbox;
    /**
     * Default constructor. 
     */
    public ContratSessionBean() {
        // TODO Auto-generated constructor stub
		emf = JavaPersistenceUtilitaire.getEmf();
    }
    
    private EntityManager getEntityManager() {
		return emf.createEntityManager();
	}
    
    
    
    public void insertionContrat(){
    	
    	
    	  EntityManager em = null;
    	  
    	 // try{
          em = getEntityManager();
          em.getTransaction().begin();
          
          ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
          int idInvestisseur = (Integer) ec.getSessionMap().get("id");
          
          
          /*
           * Recuperation INVESTISSEUR EN DUR ---> TEST AVANT VAR DE SESSION
           */
          Query q = em.createNamedQuery("Investisseur.findById");
          q.setParameter("IdInvestisseur", idInvestisseur);
	      Investisseur i = (Investisseur) q.getSingleResult();
          
         Contrat newContrat = new Contrat();
         Societe s =  transfoSociete(responseSelectSociete);
         newContrat.setSociete(s);
         newContrat.setQuantite(contrat.getQuantite());
         TypeContrat tc = transfoTypeContrat(responseSelectTypeContrat);
         newContrat.setTypeContrat(tc);
         newContrat.setInvestisseur(i);
         
         if(contrat.getDate() != null){
        	 newContrat.setDate(contrat.getDate());
         }
         
         if(isCheckbox()){
        	 i.setStatut(1);
        	 em.persist(i);
        	 FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("OK OK TERMINE"));
        	 em.persist(newContrat);
             em.getTransaction().commit();
             em.close();
        	 try {
				ec.redirect(ec.getRequestContextPath() + "/index.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	 
         }else{
        	 FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("OK CONTINUEZ OKLM "));
        	 em.persist(newContrat);
             em.getTransaction().commit();
             em.close();
        	 try {
				ec.redirect(ec.getRequestContextPath() + "/private/investisseur/ComplementsInformations.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	// ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());

         }
         
         
        
//    	  }catch(Exception e){
//    		  
//    	  }
         
   	
          
    }
    
    
    
    
    
    
    
    
	public Date getDateContrat() {
		return contrat.getDate();
	}

	public void setDateContrat(Date date) {
		contrat.setDate(date);
	}

	public int getQuantiteContrat() {
		return contrat.getQuantite();
	}

	public void setQuantiteContrat(int quantite) {
		contrat.setQuantite(quantite);
	}

	public Investisseur getInvestisseurContrat() {
		return contrat.getInvestisseur();
	}

	public void setInvestisseurContrat(Investisseur investisseur) {
		contrat.setInvestisseur(investisseur);
	}

	public Societe getSocieteContrat() {
		return contrat.getSociete();
	}

	public void setSocieteContrat(Societe societe) {
		contrat.setSociete(societe);
	}

	public TypeContrat getTypeContratContrat() {
		return contrat.getTypeContrat();
	}

	public void setTypeContratContrat(TypeContrat typeContrat) {
		contrat.setTypeContrat(typeContrat);
	}
	
	public int getResponseSelectSociete() {
		return responseSelectSociete;
	}
	
	public boolean isCheckbox() {
		return checkbox;
	}

	public void setCheckbox(boolean checkbox) {
		this.checkbox = checkbox;
	}

	public int getResponseSelectTypeContrat() {
		return responseSelectTypeContrat;
	}

	public void setResponseSelectTypeContrat(int responseSelectTypeContrat) {
		this.responseSelectTypeContrat = responseSelectTypeContrat;
	}

	public void setResponseSelectSociete(int responseSelect) {
		this.responseSelectSociete = responseSelect;
	}
	
	   public Societe transfoSociete(int id){
			
		  	EntityManager em = null;
	        em = getEntityManager();
	        em.getTransaction().begin();
	        Query q = em.createNamedQuery("Societe.findById");
	        q.setParameter("IdSociete", id);
	        Societe s = (Societe) q.getSingleResult();  
            em.close();

   		return s;

  }
	   
	   public TypeContrat transfoTypeContrat(int id){
			
		  	EntityManager em = null;
	        em = getEntityManager();
	        em.getTransaction().begin();
	        Query q = em.createNamedQuery("TypeContrat.findById");
	        q.setParameter("IdTypeContrat", id);
	        TypeContrat tc = (TypeContrat) q.getSingleResult();  
            em.close();


  		return tc;

 }
    
    

}
