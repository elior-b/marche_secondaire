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
	private List<DonneeFinanciere> listeDonneeFinanciereInvestisseur;

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

	public void ajoutDonneesFinancieres() {
		FacesMessage msg = new FacesMessage();
		try {
			EntityManager em = null;
			msg.setSummary("Erreur !");
			em = getEntityManager();
			em.getTransaction().begin();
			ExternalContext ec = FacesContext.getCurrentInstance()
					.getExternalContext();
			int idMembreSociete = (Integer) ec.getSessionMap().get("id");
			Query q = em.createNamedQuery("MembreSociete.findById");
			q.setParameter("IdMembreSociete", idMembreSociete);
			MembreSociete ms = (MembreSociete) q.getSingleResult();
			Societe s = ms.getSociete();

			q = em.createNamedQuery("DonneeFinanciere.findDoubleAnnee");
			q.setParameter("Annee", donneeFinanciere.getAnnee());
			q.setParameter("Societe", s);
			List<DonneeFinanciere> df = q.getResultList();

			if (df.size() > 0) {
				msg.setDetail("Erreur année");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			} else {
				DonneeFinanciere newDonneeFinanciere = new DonneeFinanciere();
				newDonneeFinanciere.setAnnee(donneeFinanciere.getAnnee());
				newDonneeFinanciere.setCa(donneeFinanciere.getCa());
				newDonneeFinanciere.setCoutRisque(donneeFinanciere
						.getCoutRisque());
				newDonneeFinanciere.setPnb(donneeFinanciere.getPnb());
				newDonneeFinanciere.setRcai(donneeFinanciere.getRcai());
				newDonneeFinanciere.setRex(donneeFinanciere.getRex());
				newDonneeFinanciere.setRn(donneeFinanciere.getRn());
				newDonneeFinanciere.setSociete(s);

				em.persist(newDonneeFinanciere);
				em.getTransaction().commit();
				em.clear();
				em.close();
				ec.redirect(ec.getRequestContextPath()
						+ "/private/membreSociete/Bienvenue.xhtml");

			}
			em.clear();
			em.close();

		} catch (Exception e) {
			msg.setDetail("Erreur membre société");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			e.printStackTrace();
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
		EntityManager em;
		em = getEntityManager();
		em.getTransaction().begin();
		ExternalContext ec = FacesContext.getCurrentInstance()
				.getExternalContext();
		int idMembreSociete = (Integer) ec.getSessionMap().get("id");
		Query q = em.createNamedQuery("MembreSociete.findById");
		q.setParameter("IdMembreSociete", idMembreSociete);
		MembreSociete ms = (MembreSociete) q.getSingleResult();
		Societe s = ms.getSociete();
		q = em.createNamedQuery("DonneeFinanciere.findBySociete");
		q.setParameter("Societe", s);
		listeDonneeFinanciere = q.getResultList();
		em.clear();
		em.close();
		return listeDonneeFinanciere;
	}

	public void setListeDonneeFinanciere(
			List<DonneeFinanciere> listeDonneeFinanciere) {
		this.listeDonneeFinanciere = listeDonneeFinanciere;
	}

	public List<DonneeFinanciere> getListeDonneeFinanciereInvestisseur() {
		return listeDonneeFinanciereInvestisseur;
	}

	public void setListeDonneeFinanciereInvestisseur(
			List<DonneeFinanciere> listeDonneeFinanciereInvestisseur) {
		this.listeDonneeFinanciereInvestisseur = listeDonneeFinanciereInvestisseur;
	}

}
