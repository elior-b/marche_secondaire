package bean;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import model.Administrateur;
import model.Investisseur;
import utilitaire.JavaPersistenceUtilitaire;

/**
 * Session Bean implementation class InvestisseurSessionBean
 */
@ManagedBean
@RequestScoped
public class InvestisseurSessionBean extends JavaPersistenceUtilitaire{
	Investisseur investisseur = new Investisseur();

    /**
     * Default constructor. 
     */
    public InvestisseurSessionBean() {
        // TODO Auto-generated constructor stub
    	emf = JavaPersistenceUtilitaire.getEmf();
    }
    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    
    public String getCarteBleueInvestisseur() {
		return investisseur.getCarteBleue();
	}

	public void setCarteBleueInvestisseur(String carteBleue) {
		investisseur.setCarteBleue(carteBleue);
	}

	public String getCryptogrammeInvestisseur() {
		return investisseur.getCryptogramme();
	}

	public void setCryptogrammeInvestisseur(String cryptogramme) {
		investisseur.setCryptogramme(cryptogramme);
	}

	public String getEmailInvestisseur() {
		return investisseur.getEmail();
	}

	public void setEmailInvestisseur(String email) {
		investisseur.setEmail(email);
	}
	
	
	public String getNomInvestisseur() {
		return investisseur.getNom();
	}

	public void setNomInvestisseur(String nom) {
		investisseur.setNom(nom);
	}

	public String getPrenomInvestisseur() {
		return investisseur.getPrenom();
	}

	public void setPrenomInvestisseur(String prenom) {
		investisseur.setPrenom(prenom);
	}

	public double getSoldeInvestisseur() {
		return investisseur.getSolde();
	}

	public void setSoldeInvestisseur(double solde) {
		investisseur.setSolde(solde);
	}
	
	public Object getStatutInvestisseur() {
		return investisseur.getStatut();
	}

	public void setStatutInvestisseur(Object statut) {
		investisseur.setStatut(statut);
	}

	public String getLoginInvestisseur() {
		return investisseur.getLogin();
	}

	public void setLoginInvestisseur(String login) {
		investisseur.setLogin(login);
	}

	public String getMotDePasseInvestisseur() {
		return investisseur.getMotDePasse();
	}

	public void setMotDePasseInvestisseur(String motDePasse) {
		investisseur.setMotDePasse(motDePasse);
	}
    
	
	
	public void inscriptionInvestisseur(){
		try{
	    	  EntityManager em = null;
	          em = getEntityManager();
	          em.getTransaction().begin();
	          
	          Investisseur newInvestisseur = new Investisseur();
	          boolean statut = false;
	         
	          newInvestisseur.setNom(investisseur.getNom());
	          newInvestisseur.setPrenom(investisseur.getPrenom());
	          newInvestisseur.setEmail(investisseur.getEmail());
	          newInvestisseur.setSolde(investisseur.getSolde());
	          newInvestisseur.setCarteBleue(investisseur.getCarteBleue());
	          newInvestisseur.setCryptogramme(investisseur.getCryptogramme());
	          newInvestisseur.setStatut((Object) statut);

	          
	          /*
	           * Génération Login & MotDePasse
	           */

	          
	          
	          
	         
	          
	        
	          em.persist(newInvestisseur);
	          em.getTransaction().commit();
	          em.clear();
	    	}catch(Exception e){
	            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage( "ERREUR INSCRIPTION"));

	    	}
	          
	          FacesContext.getCurrentInstance().addMessage(null, new FacesMessage( "SUCCES INSCRIPTION"));

	    
	}
	
	
	
    
    

}
