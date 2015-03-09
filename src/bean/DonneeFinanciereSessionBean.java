package bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import model.Administrateur;
import model.DonneeFinanciere;
import model.MembreSociete;
import model.Societe;
import utilitaire.JavaPersistenceUtilitaire;

/**
 * Session Bean implementation class DonneeFinanciereSessionBean
 */
@ManagedBean
@RequestScoped
public class DonneeFinanciereSessionBean extends JavaPersistenceUtilitaire {
	DonneeFinanciere donneeFinanciere = new DonneeFinanciere();
	private List<DonneeFinanciere> listeDonneeFinanciere;

    /**
     * Default constructor. 
     */
    public DonneeFinanciereSessionBean() {
        // TODO Auto-generated constructor stub
     	emf = JavaPersistenceUtilitaire.getEmf();
    }
	
    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    
	@PostConstruct
	public void init(){
		 EntityManager em;
	     em = getEntityManager();
	     em.getTransaction().begin();
	     ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
	     
	     listeDonneeFinanciere = recupererListeDonneeFinanciere(em);
	     
	}
    
	
	public List<DonneeFinanciere> recupererListeDonneeFinanciere(EntityManager em){
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        int idMembreSociete = (Integer) ec.getSessionMap().get("id");
        Query q = em.createNamedQuery("MembreSociete.findById");
        q.setParameter("IdMembreSociete", idMembreSociete);
        MembreSociete ms = (MembreSociete) q.getSingleResult();
        Societe s = ms.getSociete();
        
        q = em.createNamedQuery("DonneeFinanciere.findBySociete");
        q.setParameter("Societe", s);
 
        List<DonneeFinanciere> l = q.getResultList();
        em.close();
	    return  l; 
   }
	
	
    
    
    public void ajoutDonneesFinancieres(){	
    	EntityManager em = null;
  	  
   	  try{
         em = getEntityManager();
         em.getTransaction().begin();        
         ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
         int idMembreSociete = (Integer) ec.getSessionMap().get("id");
         Query q = em.createNamedQuery("MembreSociete.findById");
         q.setParameter("IdMembreSociete", idMembreSociete);
         MembreSociete ms = (MembreSociete) q.getSingleResult();
         Societe s = ms.getSociete();

         q = em.createNamedQuery("DonneeFinanciere.findDoubleAnnee");
         q.setParameter("Annee", donneeFinanciere.getAnnee());
         q.setParameter("Societe", s);
         List<DonneeFinanciere> df = q.getResultList();
         
         if(df.size() > 0){
        	 FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("L'Année Existe Deja"));    	 
         }else{
        	 DonneeFinanciere newDonneeFinanciere = new DonneeFinanciere();
        	 newDonneeFinanciere.setAnnee(donneeFinanciere.getAnnee());
        	 newDonneeFinanciere.setCa(donneeFinanciere.getCa());
        	 newDonneeFinanciere.setCoutRisque(donneeFinanciere.getCoutRisque());
        	 newDonneeFinanciere.setPnb(donneeFinanciere.getPnb());
        	 newDonneeFinanciere.setRcai(donneeFinanciere.getRcai());
        	 newDonneeFinanciere.setRex(donneeFinanciere.getRex());
        	 newDonneeFinanciere.setRn(donneeFinanciere.getRn());
        	 newDonneeFinanciere.setSociete(s);
        	 
        	 em.persist(newDonneeFinanciere);
	          em.getTransaction().commit();
        	 
	        	 FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("SUCCESS"));    
	             ec.redirect(ec.getRequestContextPath() + "/private/membreSociete/Bienvenue.xhtml");

         }
         
         
         
     
    	
   	  }catch(Exception e){
   		  e.printStackTrace();
   	  }finally{
          em.close();
   	  }
    	
    }
    
    
    
    
    
    


	public int getIdDonneeFinanciere() {
		return donneeFinanciere.getId();
	}

	public void setIdDonneeFinanciere(int id) {
		donneeFinanciere.setId(id);
	}

	public int getAnneeDonneeFinanciere() {
		return donneeFinanciere.getAnnee();
	}

	public void setAnneeDonneeFinanciere(int annee) {
		donneeFinanciere.setAnnee(annee);
	}

	public double getCaDonneeFinanciere() {
		return donneeFinanciere.getCa();
	}

	public void setCaDonneeFinanciere(double ca) {
		donneeFinanciere.setCa(ca);
	}

	public double getCoutRisqueDonneeFinanciere() {
		return donneeFinanciere.getCoutRisque();
	}

	public void setCoutRisqueDonneeFinanciere(double coutRisque) {
		donneeFinanciere.setCoutRisque(coutRisque);
	}

	public double getPnbDonneeFinanciere() {
		return donneeFinanciere.getPnb();
	}

	public void setPnbDonneeFinanciere(double pnb) {
		donneeFinanciere.setPnb(pnb);
	}

	public double getRcaiDonneeFinanciere() {
		return donneeFinanciere.getRcai();
	}

	public void setRcaiDonneeFinanciere(double rcai) {
		donneeFinanciere.setRcai(rcai);
	}

	public double getRexDonneeFinanciere() {
		return donneeFinanciere.getRex();
	}

	public void setRexDonneeFinanciere(double rex) {
		donneeFinanciere.setRex(rex);
	}

	public double getRnDonneeFinanciere() {
		return donneeFinanciere.getRn();
	}

	public void setRnDonneeFinanciere(double rn) {
		donneeFinanciere.setRn(rn);
	}

	public Societe getSocieteDonneeFinanciere() {
		return donneeFinanciere.getSociete();
	}

	public void setSocieteDonneeFinanciere(Societe societe) {
		donneeFinanciere.setSociete(societe);
	}

	public List<DonneeFinanciere> getListeDonneeFinanciere() {
		return listeDonneeFinanciere;
	}

	public void setListeDonneeFinanciere(
			List<DonneeFinanciere> listeDonneeFinanciere) {
		this.listeDonneeFinanciere = listeDonneeFinanciere;
	}
    
    
    
    
    
    
    
    

}
