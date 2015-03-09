package bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.MembreSociete;
import model.Societe;
import utilitaire.JavaPersistenceUtilitaire;

@ManagedBean
@RequestScoped
public class SocieteSessionBean  extends JavaPersistenceUtilitaire{
	Societe societe = new Societe();
	private List<Societe> listeSocietes;
	private Societe societeAffichage;
	
	
	public List<Societe> getListeSocietes() {
		return listeSocietes;
	}

	public void setListeSocietes(List<Societe> listeSocietes) {
		this.listeSocietes = listeSocietes;
	}

	@PostConstruct
	public void init(){
		 EntityManager em;
	     em = getEntityManager();
	     em.getTransaction().begin();
	     ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();

		listeSocietes = recupererlisteSocietes(em);

          int idMembreSociete = (Integer) ec.getSessionMap().get("id");
          em = getEntityManager();
			em.getTransaction().begin();
			Query q = em.createNamedQuery("MembreSociete.findById");
			q.setParameter("IdMembreSociete", idMembreSociete);
			MembreSociete ms = (MembreSociete) q.getSingleResult();
			societeAffichage = ms.getSociete();

		em.close();
	}
	
	public SocieteSessionBean() {
    	emf = JavaPersistenceUtilitaire.getEmf();    
    	}
	
	private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
	public void setIdSociete(int id){
		   societe.setId(id);
	   }
	   
	public int getIdSociete(){
		   return societe.getId();
	   }
	
	
	public void setNomSociete(String nom){
		   societe.setNom(nom);
	   }
	   
	public String getNomSociete(){
		   return societe.getNom();
	   }
	public void setSecteurActiviteSociete(String secteurActivite){
		   societe.setSecteurActivite(secteurActivite);;
	   }
	   
	public String getSecteurActiviteSociete(){
		   return societe.getSecteurActivite();
	   }
	public void setCapitalisationBoursiereSociete(double capitalisationBoursiere){
		   societe.setCapitalisationBoursiere(capitalisationBoursiere);;
	   }
	   
	public double getCapitalisationBoursiereSociete(){
		   return societe.getCapitalisationBoursiere();
	   }
	public void setDescriptionSociete(String description){
		   societe.setDescription(description);;
	   }
	   
	public String getDescriptionSociete(){
		   return societe.getDescription();
	   }
	public void setDirigeantSociete(String dirigeant){
		   societe.setDirigeant(dirigeant);;
	   }
	   
	public String getDirigeantSociete(){
		   return societe.getDirigeant();
	   }
	public void setRaisonSocialeSociete(String raisonSociale){
		   societe.setRaisonSociale(raisonSociale);;
	   }
	   
	public String getRaisonSocialeSociete(){
		   return societe.getRaisonSociale();
	   }
	public void setAdresseSociete(String adresse){
		   societe.setAdresse(adresse);;
	   }
	   
	public String getAdresseSociete(){
		   return societe.getAdresse();
	   }
	public void setTelephoneSociete(String telephone){
		   societe.setTelephone(telephone);;
	   }
	   
	public String getTelephoneSociete(){
		   return societe.getTelephone();
	   }
	public void setSiteWebSociete(String siteWeb){
		   societe.setSiteWeb(siteWeb);;
	   }
	   
	public String getSiteWebSociete(){
		   return societe.getSiteWeb();
	   }
	
	public List<Societe> recupererlisteSocietes(EntityManager em){
		   
		   
	        Query q = em.createNamedQuery("Societe.findAll");
	        List<Societe> l = q.getResultList();
	        em.close();
		    return  l; 
	   }

	public Societe getSocieteAffichage() {
		return societeAffichage;
	}

	public void setSocieteAffichage(Societe societeAffichage) {
		this.societeAffichage = societeAffichage;
	}


	
	
	
	
	
	
}
