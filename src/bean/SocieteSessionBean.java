package bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.Administrateur;
import model.Investisseur;
import model.MembreSociete;
import model.Societe;
import utilitaire.JavaPersistenceUtilitaire;

@ManagedBean
@RequestScoped
public class SocieteSessionBean extends JavaPersistenceUtilitaire {
	Societe societe = new Societe();
	private List<Societe> listeSocietes;
	private Societe societeAffichage;
	int nombreContratMinimum = 0;

	public List<Societe> getListeSocietes() {
		EntityManager em;
		em = getEntityManager();
		em.getTransaction().begin();
		Query q = em.createNamedQuery("Societe.findAll");
		listeSocietes = q.getResultList();
		em.clear();
		em.close();
		return listeSocietes;
	}

	public void setListeSocietes(List<Societe> listeSocietes) {
		this.listeSocietes = listeSocietes;
	}

	public void accrediterSociete() {
		FacesMessage msg = new FacesMessage();

		try {
			EntityManager em;
			em = getEntityManager();
			msg.setSummary("Erreur !");
			em.getTransaction().begin();
			Societe newSociete = new Societe();
			ExternalContext ec = FacesContext.getCurrentInstance()
					.getExternalContext();
			int idAdministrateur = (Integer) ec.getSessionMap().get("id");
			Query q = em.createNamedQuery("Administrateur.findById");
			q.setParameter("IdAdministrateur", idAdministrateur);
			Administrateur a = (Administrateur) q.getSingleResult();
			if (a != null) {
				q = em.createNamedQuery("Societe.findByNom");
				q.setParameter("Nom", societe.getNom());
				List<Societe> listeSociete = q.getResultList();

				if (listeSociete.isEmpty()) {
					newSociete.setNom(societe.getNom());
					newSociete.setSecteurActivite(societe.getSecteurActivite());
					newSociete.setCapitalisationBoursiere(societe
							.getCapitalisationBoursiere());
					newSociete.setDescription(societe.getDescription());
					newSociete.setDirigeant(societe.getDirigeant());
					newSociete.setRaisonSociale(societe.getRaisonSociale());
					newSociete.setAdresse(societe.getDescription());
					newSociete.setTelephone(societe.getTelephone());
					newSociete.setSiteWeb(societe.getSiteWeb());
					newSociete.setAdministrateur(a);

					em.persist(newSociete);
					em.getTransaction().commit();
					em.clear();
					em.close();

					ec.redirect(ec.getRequestContextPath()
							+ "/private/administrateur/Bienvenue.xhtml");

				} else {
					msg.setDetail("Erreur societe");
					FacesContext.getCurrentInstance().addMessage(null, msg);
				}
			} else {
				msg.setDetail("Erreur administrateur");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
			em.clear();
			em.close();
		} catch (Exception e) {

		}
	}

	public SocieteSessionBean() {
		emf = JavaPersistenceUtilitaire.getEmf();
	}

	private EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public void setIdSociete(int id) {
		societe.setId(id);
	}

	public int getIdSociete() {
		return societe.getId();
	}

	public void setNomSociete(String nom) {
		societe.setNom(nom);
	}

	public String getNomSociete() {
		return societe.getNom();
	}

	public void setSecteurActiviteSociete(String secteurActivite) {
		societe.setSecteurActivite(secteurActivite);
		;
	}

	public String getSecteurActiviteSociete() {
		return societe.getSecteurActivite();
	}

	public void setCapitalisationBoursiereSociete(double capitalisationBoursiere) {
		societe.setCapitalisationBoursiere(capitalisationBoursiere);
		;
	}

	public double getCapitalisationBoursiereSociete() {
		return societe.getCapitalisationBoursiere();
	}

	public void setDescriptionSociete(String description) {
		societe.setDescription(description);
		;
	}

	public String getDescriptionSociete() {
		return societe.getDescription();
	}

	public void setDirigeantSociete(String dirigeant) {
		societe.setDirigeant(dirigeant);
		;
	}

	public String getDirigeantSociete() {
		return societe.getDirigeant();
	}

	public void setRaisonSocialeSociete(String raisonSociale) {
		societe.setRaisonSociale(raisonSociale);
		;
	}

	public String getRaisonSocialeSociete() {
		return societe.getRaisonSociale();
	}

	public void setAdresseSociete(String adresse) {
		societe.setAdresse(adresse);
		;
	}

	public String getAdresseSociete() {
		return societe.getAdresse();
	}

	public void setTelephoneSociete(String telephone) {
		societe.setTelephone(telephone);
		;
	}

	public String getTelephoneSociete() {
		return societe.getTelephone();
	}

	public void setSiteWebSociete(String siteWeb) {
		societe.setSiteWeb(siteWeb);
		;
	}

	public String getSiteWebSociete() {
		return societe.getSiteWeb();
	}

	public Societe getSocieteAffichage() {
		EntityManager em;
		em = getEntityManager();
		em.getTransaction().begin();
		ExternalContext ec = FacesContext.getCurrentInstance()
				.getExternalContext();
		int idMembreSociete = (Integer) ec.getSessionMap().get("id");
		Query q = em.createNamedQuery("MembreSociete.findById");
		q.setParameter("IdMembreSociete", idMembreSociete);
		MembreSociete ms = (MembreSociete) q.getSingleResult();
		societeAffichage = ms.getSociete();

		em.clear();
		em.close();
		return societeAffichage;
	}

	public void voirSocieteInvestisseur() {
		FacesMessage msg = new FacesMessage();

		try {
			EntityManager em;
			em = getEntityManager();
			msg.setSummary("Erreur !");
			em.getTransaction().begin();
			ExternalContext ec = FacesContext.getCurrentInstance()
					.getExternalContext();
			Map<String, String> params = ec.getRequestParameterMap();
			int idSocieteParam = Integer.parseInt(params.get("id"));
			Query q = em.createNamedQuery("Societe.findById");
			q.setParameter("IdSociete", idSocieteParam);
			societeAffichage = (Societe) q.getSingleResult();
			ec.getSessionMap().put("societeAffichage", societeAffichage);
			ec.redirect(ec.getRequestContextPath()
					+ "/private/investisseur/Societe.xhtml");

		} catch (Exception e) {
			msg.setDetail("Erreir voir societe investisseur");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public void voirSocieteAdministrateur() {
		try {
			EntityManager em;
			em = getEntityManager();
			em.getTransaction().begin();
			ExternalContext ec = FacesContext.getCurrentInstance()
					.getExternalContext();
			Map<String, String> params = ec.getRequestParameterMap();
			int idSocieteParam = Integer.parseInt(params.get("id"));
			Query q = em.createNamedQuery("Societe.findById");
			q.setParameter("IdSociete", idSocieteParam);
			societeAffichage = (Societe) q.getSingleResult();
			ec.getSessionMap().put("societeAffichage", societeAffichage);

			ec.redirect(ec.getRequestContextPath()
					+ "/private/administrateur/Societe.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setSocieteAffichage(Societe societeAffichage) {
		this.societeAffichage = societeAffichage;
	}

	public void rechercher() {
		FacesMessage msg = new FacesMessage();
		try {
			msg.setSummary("Erreur !");
			EntityManager em;
			em = getEntityManager();
			em.getTransaction().begin();
			Query q;
			ExternalContext ec = FacesContext.getCurrentInstance()
					.getExternalContext();
			ArrayList<Societe> resultList = new ArrayList<Societe>();

			if (!societe.getNom().isEmpty()) {
				if (!societe.getSecteurActivite().isEmpty()) {
					q = em.createNamedQuery("Societe.findLikeNomSecteur");
					q.setParameter("Nom", "%" + societe.getNom() + "%");
					q.setParameter("SecteurActivite",
							"%" + societe.getSecteurActivite() + "%");
					List<Societe> listeSociete = q.getResultList();
					if (nombreContratMinimum != 0) {
						for (int i = 0; i < listeSociete.size(); i++) {
							Societe s = listeSociete.get(i);
							q = em.createNamedQuery("Contrat.findForCountSociete");
							q.setParameter("Societe", s);
							Long result = (Long) q.getSingleResult();
							if (result > nombreContratMinimum) {
								resultList.add(s);
							}
						}
					}
					ec.getSessionMap().put("listeSociete", listeSociete);
				} else {
					q = em.createNamedQuery("Societe.findLikeNom");
					q.setParameter("Nom", "%" + societe.getNom() + "%");
					List<Societe> listeSociete = q.getResultList();
					if (nombreContratMinimum != 0) {
						for (int i = 0; i < listeSociete.size(); i++) {
							Societe s = listeSociete.get(i);
							q = em.createNamedQuery("Contrat.findForCountSociete");
							q.setParameter("Societe", s);
							Long result = (Long) q.getSingleResult();
							if (result > nombreContratMinimum) {
								resultList.add(s);
							}
						}
					}
					ec.getSessionMap().put("listeSociete", listeSociete);
				}
			} else {
				if (!societe.getSecteurActivite().isEmpty()) {
					q = em.createNamedQuery("Societe.findLikeSecteur");
					q.setParameter("SecteurActivite",
							"%" + societe.getSecteurActivite() + "%");
					List<Societe> listeSociete = q.getResultList();
					if (nombreContratMinimum != 0) {
						for (int i = 0; i < listeSociete.size(); i++) {
							Societe s = listeSociete.get(i);
							q = em.createNamedQuery("Contrat.findForCountSociete");
							q.setParameter("Societe", s);
							Long result = (Long) q.getSingleResult();
							if (result > nombreContratMinimum) {
								resultList.add(s);
							}
						}
					}
					ec.getSessionMap().put("listeSociete", listeSociete);
				} else {
					q = em.createNamedQuery("Societe.findAll");
					List<Societe> listeSociete = q.getResultList();

					if (nombreContratMinimum != 0) {
						for (int i = 0; i < listeSociete.size(); i++) {
							Societe s = listeSociete.get(i);
							q = em.createNamedQuery("Contrat.findForCountSociete");
							q.setParameter("Societe", s);
							Long result = (Long) q.getSingleResult();
							if (result > nombreContratMinimum) {
								resultList.add(s);
							}
						}
					} else {
						em.clear();
						em.close();

						ec.redirect(ec.getRequestContextPath()
								+ "/private/investisseur/Recherche.xhtml");

					}
					ec.getSessionMap().put("listeSociete", resultList);
				}
			}
			em.clear();
			em.close();
			ec.redirect(ec.getRequestContextPath()
					+ "/private/investisseur/ResultatRechercheSociete.xhtml");
		} catch (Exception e) {
			msg.setDetail("Erreur recherche");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

	}

	public int getNombreContratMinimum() {
		return nombreContratMinimum;
	}

	public void setNombreContratMinimum(int nombreContratMinimum) {
		this.nombreContratMinimum = nombreContratMinimum;
	}

}
