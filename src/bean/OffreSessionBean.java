package bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import model.Contrat;
import model.DonneeFinanciere;
import model.Enchere;
import model.Investisseur;
import model.MembreSociete;
import model.Offre;
import model.Societe;
import model.Transaction;
import model.TypeContrat;
import model.TypeOffre;
import utilitaire.JavaPersistenceUtilitaire;

/**
 * Session Bean implementation class OffreSessionBean
 */
/**
 * @author Dori
 *
 */
@ManagedBean
@RequestScoped
public class OffreSessionBean extends JavaPersistenceUtilitaire {
	int responseSelectTypeContrat;
	int responseSelectTypeOffre;
	int responseSelectSociete;
	int responseSelectContrat;
	double montantMin = 0.0;
	double montantMax = 0.0;
	Offre offre = new Offre();
	private List<Offre> listeOffreAchat;
	private List<Offre> listeOffreVente;
	private List<Offre> listeOffreAdministrateurAchat;
	private List<Offre> listeOffreAdministrateurVente;
	private List<Offre> listeOffreInvestisseurAchat;
	private List<Offre> listeOffreInvestisseurVente;
	private List<Enchere> listeEnchereInvestisseur;
	double montantEnchere;
	@EJB
	MyTimer timer;

	/**
	 * Default constructor.
	 */
	public OffreSessionBean() {
		// TODO Auto-generated constructor stub
		emf = JavaPersistenceUtilitaire.getEmf();
	}

	private EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public void ajoutOffreAchat() {
		FacesMessage msg = new FacesMessage();
		try {
			EntityManager em = null;
			em = getEntityManager();
			msg.setSummary("Erreur !");
			em.getTransaction().begin();
			Offre newOffre = new Offre();
			ExternalContext ec = FacesContext.getCurrentInstance()
					.getExternalContext();
			int idEmetteur = (Integer) ec.getSessionMap().get("id");
			Query q = em.createNamedQuery("Investisseur.findById");
			q.setParameter("IdInvestisseur", idEmetteur);
			Investisseur i = (Investisseur) q.getSingleResult();

			if (i != null) {

				newOffre.setTypeContrat(transfoTypeContrat(responseSelectTypeContrat));
				newOffre.setSociete(transfoSociete(responseSelectSociete));
				newOffre.setTypeOffre(transfoTypeOffre(responseSelectTypeOffre));
				newOffre.setPrix(offre.getPrix());
				newOffre.setQuantite(offre.getQuantite());
				boolean continuer = true;
				if (offre.getEcheance() != null) {
					newOffre.setEcheance(offre.getEcheance());
					
				} else {
					if (responseSelectTypeOffre == 2) {
						
						continuer = false;
					}

				}
				if(continuer){
				/*
				 * Achat -> 0 & Vente -> 1
				 */
				newOffre.setType(0);
				newOffre.setStatut("En Cours");
				newOffre.setInvestisseur(i);

				em.persist(newOffre);
				em.getTransaction().commit();

				if (offre.getEcheance() != null) {
					int idOffreForTimer = retourIdOffreAchat(
							newOffre.getEcheance(), newOffre.getTypeContrat(),
							newOffre.getSociete(), newOffre.getTypeOffre(),
							newOffre.getPrix(), newOffre.getQuantite());
					if (idOffreForTimer != 0) {
						Date today = new Date();
						long duration = newOffre.getEcheance().getTime()
								- today.getTime();
						timer.startTimer("OffreAchat " + newOffre.getId(),
								idOffreForTimer, duration);
					}
				}

				em.clear();
				em.close();

				ec.redirect(ec.getRequestContextPath()
						+ "/private/investisseur/Bienvenue.xhtml");
				}else{
					msg.setDetail("Erreur date");
					FacesContext.getCurrentInstance().addMessage(null, msg);
				}
			} else {
				msg.setDetail("Erreur emetteur - investisseur");
				FacesContext.getCurrentInstance().addMessage(null, msg);

			}
				
			em.clear();
			em.close();
		} catch (Exception e) {
			msg.setDetail("Erreur ajout offre achat");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

	}

	public void ajoutOffreVente() {
		FacesMessage msg = new FacesMessage();
		try {
			EntityManager em = null;
			em = getEntityManager();
			msg.setSummary("Erreur !");
			em.getTransaction().begin();
			Offre newOffre = new Offre();

			ExternalContext ec = FacesContext.getCurrentInstance()
					.getExternalContext();
			int idEmetteur = (Integer) ec.getSessionMap().get("id");
			Query q = em.createNamedQuery("Investisseur.findById");
			q.setParameter("IdInvestisseur", idEmetteur);
			Investisseur i = (Investisseur) q.getSingleResult();

			if (i != null) {
				q = em.createNamedQuery("Contrat.findById");
				q.setParameter("IdContrat", responseSelectContrat);
				Contrat c = (Contrat) q.getSingleResult();

				List<Offre> listeOffreParContrat;
				q = em.createNamedQuery("Offre.findByContratStatut");
				q.setParameter("Statut", "En Cours");
				q.setParameter("Contrat", c);
				listeOffreParContrat = q.getResultList();
				int quantiteTotale = 0;
				for (int z = 0; z < listeOffreParContrat.size(); z++) {
					quantiteTotale += listeOffreParContrat.get(z).getQuantite();
				}

				if ((quantiteTotale + offre.getQuantite()) <= c.getQuantite()) {
					boolean continuer = true;
					if (offre.getEcheance() != null) {
						newOffre.setEcheance(offre.getEcheance());
					} else {
						if (responseSelectTypeOffre == 2) {

							continuer = false;

						} else {
							if (c.getDate() != null) {
								newOffre.setEcheance(c.getDate());
							}
						}
					}
					if (continuer) {
						newOffre.setContrat(transfoContrat(responseSelectContrat));
						newOffre.setTypeOffre(transfoTypeOffre(responseSelectTypeOffre));
						newOffre.setPrix(offre.getPrix());
						newOffre.setQuantite(offre.getQuantite());

						/*
						 * Achat -> 0 & Vente -> 1
						 */
						newOffre.setType(1);
						newOffre.setStatut("En Cours");
						newOffre.setInvestisseur(i);

						em.persist(newOffre);
						em.getTransaction().commit();
						if (offre.getEcheance() != null) {
							int idOffreForTimer = retourIdOffreVente(
									newOffre.getEcheance(),
									newOffre.getContrat(),
									newOffre.getTypeOffre(),
									newOffre.getPrix(), newOffre.getQuantite());
							if (idOffreForTimer != 0) {
								Date today = new Date();
								long duration = newOffre.getEcheance()
										.getTime() - today.getTime();
								timer.startTimer(
										"OffreVente " + newOffre.getId(),
										idOffreForTimer, duration);
							}
						}
						em.clear();
						em.close();
						ec.redirect(ec.getRequestContextPath()
								+ "/private/investisseur/Bienvenue.xhtml");
					} else {
						msg.setDetail("Erreur date");
						FacesContext.getCurrentInstance().addMessage(null, msg);
					}
					

				} else {
					msg.setDetail("Erreur quantite");
					FacesContext.getCurrentInstance().addMessage(null, msg);
				}

			} else {
				msg.setDetail("Erreur emetteur - investisseur");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
			em.clear();
			em.close();
		} catch (Exception e) {
			msg.setDetail("Erreur ajout offre vente");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

	}

	public int retourIdOffreVente(Date echeance, Contrat contrat,
			TypeOffre typeOffre, double prix, int quantite) {
		EntityManager em = null;
		em = getEntityManager();
		em.getTransaction().begin();
		Query q = em.createNamedQuery("Offre.findForIdOffreVente");
		q.setParameter("Echeance", echeance);
		q.setParameter("Contrat", contrat);
		q.setParameter("TypeOffre", typeOffre);
		q.setParameter("Prix", prix);
		q.setParameter("Quantite", quantite);
		Offre i = (Offre) q.getSingleResult();
		em.clear();
		em.close();
		return i.getId();

	}

	public int retourIdOffreAchat(Date echeance, TypeContrat typeContrat,
			Societe societe, TypeOffre typeOffre, double prix, int quantite) {

		EntityManager em = null;
		em = getEntityManager();
		em.getTransaction().begin();
		Query q = em.createNamedQuery("Offre.findForIdOffreAchat");
		q.setParameter("Echeance", echeance);
		q.setParameter("TypeContrat", typeContrat);
		q.setParameter("Societe", societe);
		q.setParameter("TypeOffre", typeOffre);
		q.setParameter("Prix", prix);
		q.setParameter("Quantite", quantite);
		Offre i = (Offre) q.getSingleResult();
		em.clear();
		em.close();
		return i.getId();
	}

	public void voirOffre() {

		try {
			EntityManager em = null;
			em = getEntityManager();
			em.getTransaction().begin();
			ExternalContext ec = FacesContext.getCurrentInstance()
					.getExternalContext();
			Map<String, String> params = ec.getRequestParameterMap();
			int idOffreParam = Integer.parseInt(params.get("id"));
			String typeOffre = params.get("type");

			Query q = em.createNamedQuery("Offre.findById");
			q.setParameter("IdOffre", idOffreParam);
			Offre newOffre = (Offre) q.getSingleResult();
			ec.getSessionMap().put("offre", newOffre);
			em.clear();
			em.close();
			if (typeOffre.equals("achat")) {
				ec.redirect(ec.getRequestContextPath()
						+ "/private/investisseur/OffreAchat.xhtml");
			} else {
				ec.redirect(ec.getRequestContextPath()
						+ "/private/investisseur/OffreVente.xhtml");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void accepterOffreAchatFixe() {
		FacesMessage msg = new FacesMessage();

		try {
			ExternalContext ec = FacesContext.getCurrentInstance()
					.getExternalContext();
			EntityManager em = null;
			msg.setSummary("Erreur !");
			em = getEntityManager();
			em.getTransaction().begin();
			/*
			 * On récupère l'Offre en session dans une nouvelle instance et on
			 * la supprime de la session. De cette nouvelle instance, on
			 * récupère son Id puis on récupère monOffre, instance sur laquelle
			 * nous allons travailler
			 */
			Offre tmp = (Offre) ec.getSessionMap().get("offre");
			ec.getSessionMap().remove("offre");
			int idOffre = tmp.getId();
			Query q = em.createNamedQuery("Offre.findById");
			q.setParameter("IdOffre", idOffre);
			Offre monOffre = (Offre) q.getSingleResult();

			/*
			 * On récupère l'investisseur connecté (avec son Id en session)
			 */
			int idInvestisseur = (Integer) ec.getSessionMap().get("id");
			q = em.createNamedQuery("Investisseur.findById");
			q.setParameter("IdInvestisseur", idInvestisseur);
			Investisseur i = (Investisseur) q.getSingleResult();
			/*
			 * On récupère le contrat de l'investisseur connecté, qui comprend
			 * la societe / type offre / type contrat de l'OFFRE en question Si
			 * l'offre en question possède une échéance, alors on recherche le
			 * contrat qui a EN PLUS la même date.
			 */
			if (monOffre.getEcheance() != null) {
				q = em.createNamedQuery("Contrat.findByInvestisseurSocieteTypeContratDate");
				q.setParameter("Investisseur", i);
				q.setParameter("Societe", monOffre.getSociete());
				q.setParameter("TypeContrat", monOffre.getTypeContrat());
				q.setParameter("Date", monOffre.getEcheance());
			} else {
				q = em.createNamedQuery("Contrat.findByInvestisseurSocieteTypeContrat");
				q.setParameter("Investisseur", i);
				q.setParameter("Societe", monOffre.getSociete());
				q.setParameter("TypeContrat", monOffre.getTypeContrat());
			}

			List<Contrat> listeC = q.getResultList();
			/*
			 * On récupère l'Emetteur de l'offre (un investisseur)
			 */
			int IdEmetteur = monOffre.getInvestisseur().getId();
			q = em.createNamedQuery("Investisseur.findById");
			q.setParameter("IdInvestisseur", IdEmetteur);
			Investisseur emetteur = (Investisseur) q.getSingleResult();
			/*
			 * De même que précédent mais c'est le contrat de l'emetteur
			 */
			if (monOffre.getEcheance() != null) {
				q = em.createNamedQuery("Contrat.findByInvestisseurSocieteTypeContratDate");
				q.setParameter("Investisseur", emetteur);
				q.setParameter("Societe", monOffre.getSociete());
				q.setParameter("TypeContrat", monOffre.getTypeContrat());
				q.setParameter("Date", monOffre.getEcheance());
			} else {
				q = em.createNamedQuery("Contrat.findByInvestisseurSocieteTypeContrat");
				q.setParameter("Investisseur", emetteur);
				q.setParameter("Societe", monOffre.getSociete());
				q.setParameter("TypeContrat", monOffre.getTypeContrat());
			}
			List<Contrat> listeCEmetteur = q.getResultList();

			if (listeC.size() > 0) {
				Contrat c = listeC.get(0);
				/*
				 * On vérifie que la quantité du contrat du vendeur
				 * (investisseur connecté) est supérieure ou égale à celle de
				 * l'offre
				 */
				if (c.getQuantite() >= monOffre.getQuantite()) {
					/*
					 * On vérifie que le solde de l'acheteur du contrat
					 * (emetteur) est supérieur ou égal au montant de l'offre
					 */
					if (emetteur.getSolde() > (monOffre.getPrix() * monOffre
							.getQuantite())) {
						/*
						 * Mise à jour de la quantité du contrat du vendeur
						 * (investisseur connecté)
						 */
						c.setQuantite(c.getQuantite() - monOffre.getQuantite());
						em.persist(c);
						if (listeCEmetteur.size() > 0) {
							Contrat cEmetteur = listeCEmetteur.get(0);
							/*
							 * Mise à jour de la quantité du contrat de
							 * l'emetteur
							 */
							cEmetteur.setQuantite(cEmetteur.getQuantite()
									+ monOffre.getQuantite());
							em.persist(cEmetteur);

						} else {
							/*
							 * Création du contrat de l'emetteur si il n'existe
							 * pas deja
							 */
							Contrat newContrat = new Contrat();
							newContrat.setInvestisseur(emetteur);
							newContrat.setQuantite(monOffre.getQuantite());
							newContrat.setSociete(monOffre.getSociete());
							newContrat
									.setTypeContrat(monOffre.getTypeContrat());
							if (c.getDate() != null) {
								newContrat.setDate(c.getDate());
							}
							em.persist(newContrat);
						}
						/*
						 * Mise à jour du solde de l'emetteur & du vendeur
						 * (investisseur connecté)
						 */
						emetteur.setSolde(emetteur.getSolde()
								- (monOffre.getPrix() * monOffre.getQuantite()));
						i.setSolde(i.getSolde()
								+ (monOffre.getPrix() * monOffre.getQuantite()));
						em.persist(i);
						em.persist(emetteur);

						/*
						 * Création de la Transaction
						 */

						Transaction newTransaction = new Transaction();
						newTransaction.setDate(new Date());
						newTransaction.setOffre(monOffre);
						newTransaction.setInvestisseur(i);
						/*
						 * Cloture de l'offre
						 */
						monOffre.setStatut("Terminee");
						em.persist(monOffre);
						em.persist(newTransaction);
						em.getTransaction().commit();
						em.clear();
						em.close();
						/*
						 * Mise en session de l'offre
						 */
						ec.getSessionMap().put("offre", monOffre);

						ec.redirect(ec.getRequestContextPath()
								+ "/private/investisseur/Bienvenue.xhtml");

					} else {
						msg.setDetail("Erreur solde acheteur");
						FacesContext.getCurrentInstance().addMessage(null, msg);
					}
				} else {
					msg.setDetail("Erreur quantite");
					FacesContext.getCurrentInstance().addMessage(null, msg);
				}
			} else {
				msg.setDetail("Erreur contrat");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
			/*
			 * Mise en session de l'offre
			 */
			em.clear();
			em.close();
			ec.getSessionMap().put("offre", monOffre);
		} catch (Exception e) {
			msg.setDetail("Erreur accepter offre");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

	}

	public void accepterOffreVenteFixe() {
		FacesMessage msg = new FacesMessage();

		try {

			ExternalContext ec = FacesContext.getCurrentInstance()
					.getExternalContext();
			EntityManager em = null;
			msg.setSummary("Erreur !");
			em = getEntityManager();
			em.getTransaction().begin();
			/*
			 * On récupère l'Offre en session dans une nouvelle instance et on
			 * la supprime de la session. De cette nouvelle instance, on
			 * récupère son Id puis on récupère monOffre, instance sur laquelle
			 * nous allons travailler
			 */
			Offre tmp = (Offre) ec.getSessionMap().get("offre");
			ec.getSessionMap().remove("offre");
			int idOffre = tmp.getId();
			Query q = em.createNamedQuery("Offre.findById");
			q.setParameter("IdOffre", idOffre);
			Offre monOffre = (Offre) q.getSingleResult();

			/*
			 * On récupère l'Emetteur de l'offre (un investisseur)
			 */
			int IdEmetteur = monOffre.getInvestisseur().getId();
			q = em.createNamedQuery("Investisseur.findById");
			q.setParameter("IdInvestisseur", IdEmetteur);
			Investisseur emetteur = (Investisseur) q.getSingleResult();
			/*
			 * De même que précédent mais c'est le contrat de l'emetteur
			 */
			Contrat cEmetteur = monOffre.getContrat();
			if (cEmetteur != null) {
				if (cEmetteur.getQuantite() >= monOffre.getQuantite()) {
					/*
					 * On récupère l'investisseur connecté (avec son Id en
					 * session)
					 */
					int idInvestisseur = (Integer) ec.getSessionMap().get("id");
					q = em.createNamedQuery("Investisseur.findById");
					q.setParameter("IdInvestisseur", idInvestisseur);
					Investisseur i = (Investisseur) q.getSingleResult();
					/*
					 * On récupère le contrat de l'investisseur connecté, qui
					 * comprend la societe / type offre / type contrat de
					 * l'OFFRE en question Si l'offre en question possède une
					 * échéance, alors on recherche le contrat qui a EN PLUS la
					 * même date.
					 */
					if (monOffre.getEcheance() != null) {
						q = em.createNamedQuery("Contrat.findByInvestisseurSocieteTypeContratDate");
						q.setParameter("Investisseur", i);
						q.setParameter("Societe", monOffre.getContrat()
								.getSociete());
						q.setParameter("TypeContrat", monOffre.getContrat()
								.getTypeContrat());
						q.setParameter("Date", monOffre.getEcheance());
					} else {
						q = em.createNamedQuery("Contrat.findByInvestisseurSocieteTypeContrat");
						q.setParameter("Investisseur", i);
						q.setParameter("Societe", monOffre.getContrat()
								.getSociete());
						q.setParameter("TypeContrat", monOffre.getContrat()
								.getTypeContrat());
					}
					List<Contrat> listeC = q.getResultList();

					if (i.getSolde() >= (monOffre.getQuantite() * monOffre
							.getPrix())) {
						/*
						 * Mise à jour de la quantité du contrat du vendeur
						 */
						cEmetteur.setQuantite(cEmetteur.getQuantite()
								- monOffre.getQuantite());
						em.persist(cEmetteur);
						if (listeC.size() > 0) {
							FacesContext.getCurrentInstance().addMessage(null,
									new FacesMessage("size"));

							Contrat c = listeC.get(0);
							/*
							 * Mise à jour de la quantité du contrat de acheteur
							 * (investisseur connecté)
							 */
							c.setQuantite(c.getQuantite()
									+ monOffre.getQuantite());
							em.persist(c);

						} else {
							/*
							 * Création du contrat de l'acheteur (investisseur
							 * connecté) si il n'existe pas deja
							 */

							Contrat newContrat = new Contrat();
							newContrat.setInvestisseur(i);
							newContrat.setQuantite(monOffre.getQuantite());
							newContrat.setSociete(monOffre.getContrat()
									.getSociete());
							newContrat.setTypeContrat(monOffre.getContrat()
									.getTypeContrat());
							if (cEmetteur.getDate() != null) {
								newContrat.setDate(cEmetteur.getDate());
							}

							em.persist(newContrat);
						}
						/*
						 * Mise à jour du solde de l'acheteur (investisseur
						 * connecté) & du vendeur
						 */
						emetteur.setSolde(emetteur.getSolde()
								+ (monOffre.getPrix() * monOffre.getQuantite()));
						i.setSolde(i.getSolde()
								- (monOffre.getPrix() * monOffre.getQuantite()));
						em.persist(i);
						em.persist(emetteur);

						/*
						 * Création de la Transaction
						 */

						Transaction newTransaction = new Transaction();
						newTransaction.setDate(new Date());
						newTransaction.setOffre(monOffre);
						newTransaction.setInvestisseur(i);
						/*
						 * Cloture de l'offre
						 */
						monOffre.setStatut("Terminee");
						em.persist(monOffre);
						em.persist(newTransaction);
						em.getTransaction().commit();
						em.clear();
						em.close();
						/*
						 * Mise en session de l'offre
						 */
						ec.getSessionMap().put("offre", monOffre);

						ec.redirect(ec.getRequestContextPath()
								+ "/private/investisseur/Bienvenue.xhtml");

					} else {
						msg.setDetail("Erreur solde acheteur");
						FacesContext.getCurrentInstance().addMessage(null, msg);
					}

				} else {
					msg.setDetail("Erreur quantite");
					FacesContext.getCurrentInstance().addMessage(null, msg);
				}

			} else {
				msg.setDetail("Erreur contrat");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
			/*
			 * Mise en session de l'offre
			 */
			em.clear();
			em.close();
			ec.getSessionMap().put("offre", monOffre);

		} catch (Exception e) {

		}

	}

	public void encherirOffreAchat() {
		FacesMessage msg = new FacesMessage();

		try {
			ExternalContext ec = FacesContext.getCurrentInstance()
					.getExternalContext();
			EntityManager em = null;
			msg.setSummary("Erreur !");
			em = getEntityManager();
			em.getTransaction().begin();
			/*
			 * On récupère l'Offre en session dans une nouvelle instance et on
			 * la supprime de la session. De cette nouvelle instance, on
			 * récupère son Id puis on récupère monOffre, instance sur laquelle
			 * nous allons travailler
			 */
			Offre tmp = (Offre) ec.getSessionMap().get("offre");
			ec.getSessionMap().remove("offre");
			int idOffre = tmp.getId();
			Query q = em.createNamedQuery("Offre.findById");
			q.setParameter("IdOffre", idOffre);
			Offre monOffre = (Offre) q.getSingleResult();

			/*
			 * On récupère l'investisseur connecté (avec son Id en session)
			 */
			int idInvestisseur = (Integer) ec.getSessionMap().get("id");
			q = em.createNamedQuery("Investisseur.findById");
			q.setParameter("IdInvestisseur", idInvestisseur);
			Investisseur i = (Investisseur) q.getSingleResult();

			Date today = new Date();

			if (today.before(monOffre.getEcheance())) {
				if (montantEnchere <= monOffre.getPrix()) {
					Enchere newEnchere = new Enchere();
					newEnchere.setDate(today);
					newEnchere.setInvestisseur(i);
					newEnchere.setOffre(monOffre);
					newEnchere.setPrix(montantEnchere);

					em.persist(newEnchere);
					em.getTransaction().commit();
					em.clear();
					em.close();
					/*
					 * Mise en session de l'offre
					 */
					ec.getSessionMap().put("offre", monOffre);

					ec.redirect(ec.getRequestContextPath()
							+ "/private/investisseur/OffreAchat.xhtml");

				} else {
					msg.setDetail("Erreur montant");
					FacesContext.getCurrentInstance().addMessage(null, msg);
				}
			} else {
				msg.setDetail("Erreur date");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}

			/*
			 * Mise en session de l'offre
			 */
			em.clear();
			em.close();
			ec.getSessionMap().put("offre", monOffre);
		} catch (Exception e) {
			msg.setDetail("Erreur enchère");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

	}

	public void encherirOffreVente() {
		FacesMessage msg = new FacesMessage();

		try {
			ExternalContext ec = FacesContext.getCurrentInstance()
					.getExternalContext();
			EntityManager em = null;
			msg.setSummary("Erreur !");
			em = getEntityManager();
			em.getTransaction().begin();
			/*
			 * On récupère l'Offre en session dans une nouvelle instance et on
			 * la supprime de la session. De cette nouvelle instance, on
			 * récupère son Id puis on récupère monOffre, instance sur laquelle
			 * nous allons travailler
			 */
			Offre tmp = (Offre) ec.getSessionMap().get("offre");
			ec.getSessionMap().remove("offre");
			int idOffre = tmp.getId();
			Query q = em.createNamedQuery("Offre.findById");
			q.setParameter("IdOffre", idOffre);
			Offre monOffre = (Offre) q.getSingleResult();

			/*
			 * On récupère l'investisseur connecté (avec son Id en session)
			 */
			int idInvestisseur = (Integer) ec.getSessionMap().get("id");
			q = em.createNamedQuery("Investisseur.findById");
			q.setParameter("IdInvestisseur", idInvestisseur);
			Investisseur i = (Investisseur) q.getSingleResult();

			Date today = new Date();

			if (today.before(monOffre.getEcheance())) {
				if (montantEnchere >= monOffre.getPrix()) {
					Enchere newEnchere = new Enchere();
					newEnchere.setDate(today);
					newEnchere.setInvestisseur(i);
					newEnchere.setOffre(monOffre);
					newEnchere.setPrix(montantEnchere);

					em.persist(newEnchere);
					em.getTransaction().commit();
					em.clear();
					em.close();
					/*
					 * Mise en session de l'offre
					 */
					ec.getSessionMap().put("offre", monOffre);

					ec.redirect(ec.getRequestContextPath()
							+ "/private/investisseur/OffreVente.xhtml");

				} else {
					msg.setDetail("Erreur montant");
					FacesContext.getCurrentInstance().addMessage(null, msg);
				}
			} else {
				msg.setDetail("Erreur date");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}

			/*
			 * Mise en session de l'offre
			 */
			em.clear();
			em.close();
			ec.getSessionMap().put("offre", monOffre);
		} catch (Exception e) {
			msg.setDetail("Erreur enchère");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public int getResponseSelectTypeContrat() {
		return responseSelectTypeContrat;
	}

	public void setResponseSelectTypeContrat(int responseSelectTypeContrat) {
		this.responseSelectTypeContrat = responseSelectTypeContrat;
	}

	public int getResponseSelectTypeOffre() {
		return responseSelectTypeOffre;
	}

	public void setResponseSelectTypeOffre(int responseSelectTypeOffre) {
		this.responseSelectTypeOffre = responseSelectTypeOffre;
	}

	public TypeContrat transfoTypeContrat(int id) {

		EntityManager em = null;
		em = getEntityManager();
		em.getTransaction().begin();
		Query q = em.createNamedQuery("TypeContrat.findById");
		q.setParameter("IdTypeContrat", id);
		TypeContrat tc = (TypeContrat) q.getSingleResult();
		em.clear();
		em.close();
		return tc;
	}

	public TypeOffre transfoTypeOffre(int id) {

		EntityManager em = null;
		em = getEntityManager();
		em.getTransaction().begin();
		Query q = em.createNamedQuery("TypeOffre.findById");
		q.setParameter("IdTypeOffre", id);
		TypeOffre to = (TypeOffre) q.getSingleResult();
		em.clear();
		em.close();
		return to;
	}

	public Societe transfoSociete(int id) {

		EntityManager em = null;
		em = getEntityManager();
		em.getTransaction().begin();
		Query q = em.createNamedQuery("Societe.findById");
		q.setParameter("IdSociete", id);
		Societe s = (Societe) q.getSingleResult();
		em.clear();
		em.close();
		return s;

	}

	public Contrat transfoContrat(int id) {

		EntityManager em = null;
		em = getEntityManager();
		em.getTransaction().begin();
		Query q = em.createNamedQuery("Contrat.findById");
		q.setParameter("IdContrat", id);
		Contrat c = (Contrat) q.getSingleResult();
		em.clear();
		em.close();
		return c;

	}

	public int getResponseSelectSociete() {
		return responseSelectSociete;
	}

	public void setResponseSelectSociete(int responseSelectSociete) {
		this.responseSelectSociete = responseSelectSociete;
	}

	public int getIdOffre() {
		return offre.getId();
	}

	public void setIdOffre(int id) {
		offre.setId(id);
	}

	public Date getEcheanceOffre() {
		return offre.getEcheance();
	}

	public void setEcheanceOffre(Date echeance) {
		offre.setEcheance(echeance);
	}

	public double getPrixOffre() {
		return offre.getPrix();
	}

	public void setPrixOffre(double prix) {
		offre.setPrix(prix);
	}

	public int getQuantiteOffre() {
		return offre.getQuantite();
	}

	public void setQuantiteOffre(int quantite) {
		offre.setQuantite(quantite);
	}

	public String getStatutOffre() {
		return offre.getStatut();
	}

	public void setStatutOffre(String statut) {
		offre.setStatut(statut);
	}

	public int getTypeOffre() {
		return offre.getType();
	}

	public void setTypeOffre(int type) {
		offre.setType(type);
	}

	public int getResponseSelectContrat() {
		return responseSelectContrat;
	}

	public void setResponseSelectContrat(int responseSelectContrat) {
		this.responseSelectContrat = responseSelectContrat;
	}

	public List<Offre> getListeOffreAchat() {
		EntityManager em;
		em = getEntityManager();
		em.getTransaction().begin();
		ExternalContext ec = FacesContext.getCurrentInstance()
				.getExternalContext();
		int idInvestisseur = (Integer) ec.getSessionMap().get("id");
		Query q = em.createNamedQuery("Investisseur.findById");
		q.setParameter("IdInvestisseur", idInvestisseur);
		Investisseur i = (Investisseur) q.getSingleResult();

		q = em.createNamedQuery("Offre.findForListeAffichage");
		q.setParameter("Investisseur", i);
		q.setParameter("Type", 0);
		q.setParameter("Statut", "En Cours");
		listeOffreAchat = q.getResultList();
		em.clear();
		em.close();
		return listeOffreAchat;
	}

	public void setListeOffreAchat(List<Offre> listeOffreAchat) {
		this.listeOffreAchat = listeOffreAchat;
	}

	public List<Offre> getListeOffreVente() {
		EntityManager em;
		em = getEntityManager();
		em.getTransaction().begin();
		ExternalContext ec = FacesContext.getCurrentInstance()
				.getExternalContext();
		int idInvestisseur = (Integer) ec.getSessionMap().get("id");
		Query q = em.createNamedQuery("Investisseur.findById");
		q.setParameter("IdInvestisseur", idInvestisseur);
		Investisseur i = (Investisseur) q.getSingleResult();

		q = em.createNamedQuery("Offre.findForListeAffichage");
		q.setParameter("Investisseur", i);
		q.setParameter("Type", 1);
		q.setParameter("Statut", "En Cours");
		listeOffreVente = q.getResultList();
		em.clear();
		em.close();
		return listeOffreVente;
	}

	public void setListeOffreVente(List<Offre> listeOffreVente) {
		this.listeOffreVente = listeOffreVente;
	}

	public Offre getOffre() {

		return offre;
	}

	public void setOffre(Offre offre) {
		this.offre = offre;
	}

	public double getMontantEnchere() {
		return montantEnchere;
	}

	public void setMontantEnchere(double montantEnchere) {
		this.montantEnchere = montantEnchere;
	}

	public List<Offre> getListeOffreInvestisseurAchat() {
		EntityManager em;
		em = getEntityManager();
		em.getTransaction().begin();
		ExternalContext ec = FacesContext.getCurrentInstance()
				.getExternalContext();
		int idInvestisseur = (Integer) ec.getSessionMap().get("id");
		Query q = em.createNamedQuery("Investisseur.findById");
		q.setParameter("IdInvestisseur", idInvestisseur);
		Investisseur i = (Investisseur) q.getSingleResult();
		q = em.createNamedQuery("Offre.findByEmetteurType");
		q.setParameter("Emetteur", i);
		q.setParameter("Statut", "En Cours");
		q.setParameter("Type", 0);
		listeOffreInvestisseurAchat = q.getResultList();
		em.clear();
		em.close();
		return listeOffreInvestisseurAchat;
	}

	public List<Offre> getListeOffreInvestisseurVente() {
		EntityManager em;
		em = getEntityManager();
		em.getTransaction().begin();
		ExternalContext ec = FacesContext.getCurrentInstance()
				.getExternalContext();
		int idInvestisseur = (Integer) ec.getSessionMap().get("id");
		Query q = em.createNamedQuery("Investisseur.findById");
		q.setParameter("IdInvestisseur", idInvestisseur);
		Investisseur i = (Investisseur) q.getSingleResult();
		q = em.createNamedQuery("Offre.findByEmetteurType");
		q.setParameter("Emetteur", i);
		q.setParameter("Statut", "En Cours");
		q.setParameter("Type", 1);
		listeOffreInvestisseurVente = q.getResultList();
		em.clear();
		em.close();
		return listeOffreInvestisseurVente;
	}

	public void setListeOffreInvestisseurAchat(
			List<Offre> listeOffreInvestisseurAchat) {
		this.listeOffreInvestisseurAchat = listeOffreInvestisseurAchat;
	}

	public void setListeOffreInvestisseurVente(
			List<Offre> listeOffreInvestisseurVente) {
		this.listeOffreInvestisseurVente = listeOffreInvestisseurVente;
	}

	public List<Enchere> getListeEnchereInvestisseur() {
		EntityManager em;
		em = getEntityManager();
		em.getTransaction().begin();
		ExternalContext ec = FacesContext.getCurrentInstance()
				.getExternalContext();
		int idInvestisseur = (Integer) ec.getSessionMap().get("id");
		Query q = em.createNamedQuery("Investisseur.findById");
		q.setParameter("IdInvestisseur", idInvestisseur);
		Investisseur i = (Investisseur) q.getSingleResult();
		q = em.createNamedQuery("Enchere.findByEmetteur");
		q.setParameter("Emetteur", i);
		listeEnchereInvestisseur = q.getResultList();
		em.clear();
		em.close();
		return listeEnchereInvestisseur;
	}

	public void setListeEnchereInvestisseur(
			List<Enchere> listeEnchereInvestisseur) {
		this.listeEnchereInvestisseur = listeEnchereInvestisseur;
	}

	public double getMontantMin() {
		return montantMin;
	}

	public void setMontantMin(double montantMin) {
		this.montantMin = montantMin;
	}

	public double getMontantMax() {
		return montantMax;
	}

	public void setMontantMax(double montantMax) {
		this.montantMax = montantMax;
	}

	public void rechercher() {
		EntityManager em;
		em = getEntityManager();
		FacesMessage msg = new FacesMessage();
		msg.setSummary("Erreur !");
		try {
			em.getTransaction().begin();
			Query q;
			ExternalContext ec = FacesContext.getCurrentInstance()
					.getExternalContext();
			ArrayList<Offre> resultListAchat = new ArrayList<Offre>();
			ArrayList<Offre> resultListVente = new ArrayList<Offre>();

			Societe s = transfoSociete(responseSelectSociete);
			TypeContrat tc = transfoTypeContrat(responseSelectTypeContrat);

			if (montantMin >= 0) {
				if (montantMax > 0) {
					q = em.createNamedQuery("Offre.findByMontantMinMontantMax");
					q.setParameter("MontantMin", montantMin);
					q.setParameter("MontantMax", montantMax);
					List<Offre> listeOffre = q.getResultList();

					for (int i = 0; i < listeOffre.size(); i++) {
						Offre o = listeOffre.get(i);
						if (o.getType() == 0) {
							// Offre Achat
							if (o.getTypeContrat().getId() == tc.getId()
									&& o.getSociete().getId() == s.getId()) {
								resultListAchat.add(o);
							}
						} else {
							// Offre Vente
							if (o.getContrat().getTypeContrat().getId() == tc
									.getId()
									&& o.getContrat().getSociete().getId() == s
											.getId()) {
								resultListVente.add(o);
							}
						}
					}
					ec.getSessionMap().put("listeOffreAchat", resultListAchat);
					ec.getSessionMap().put("listeOffreVente", resultListVente);

					em.clear();
					em.close();
					ec.redirect(ec.getRequestContextPath()
							+ "/private/investisseur/ResultatRechercheOffre.xhtml");

				} else {
					// Pas de MAX
					q = em.createNamedQuery("Offre.findByMontantMin");
					q.setParameter("MontantMin", montantMin);
					List<Offre> listeOffre = q.getResultList();

					for (int i = 0; i < listeOffre.size(); i++) {
						Offre o = listeOffre.get(i);
						if (o.getType() == 0) {
							// Offre Achat
							if (o.getTypeContrat().getId() == tc.getId()) {
								resultListAchat.add(o);
							}
						} else {
							// Offre Vente
							if (o.getContrat().getTypeContrat().getId() == tc
									.getId()) {
								resultListVente.add(o);
							}
						}
					}
					ec.getSessionMap().put("listeOffreAchat", resultListAchat);
					ec.getSessionMap().put("listeOffreVente", resultListVente);

					em.clear();
					em.close();
					ec.redirect(ec.getRequestContextPath()
							+ "/private/investisseur/ResultatRechercheOffre.xhtml");

				}

			} else {
				if (montantMax > 0) {
					q = em.createNamedQuery("Offre.findByMontantMinMontantMax");
					q.setParameter("MontantMin", 0);
					q.setParameter("MontantMax", montantMax);
					List<Offre> listeOffre = q.getResultList();

					for (int i = 0; i < listeOffre.size(); i++) {
						Offre o = listeOffre.get(i);
						if (o.getType() == 0) {
							// Offre Achat
							if (o.getTypeContrat().getId() == tc.getId()) {
								resultListAchat.add(o);
							}
						} else {
							// Offre Vente
							if (o.getContrat().getTypeContrat().getId() == tc
									.getId()) {
								resultListVente.add(o);
							}
						}
					}
					ec.getSessionMap().put("listeOffreAchat", resultListAchat);
					ec.getSessionMap().put("listeOffreVente", resultListVente);

					em.clear();
					em.close();
					ec.redirect(ec.getRequestContextPath()
							+ "/private/investisseur/ResultatRechercheOffre.xhtml");

				} else {
					q = em.createNamedQuery("Offre.findByMontantMin");
					q.setParameter("MontantMin", 0);
					List<Offre> listeOffre = q.getResultList();

					for (int i = 0; i < listeOffre.size(); i++) {
						Offre o = listeOffre.get(i);
						if (o.getType() == 0) {
							// Offre Achat
							if (o.getTypeContrat().getId() == tc.getId()) {
								resultListAchat.add(o);
							}
						} else {
							// Offre Vente
							if (o.getContrat().getTypeContrat().getId() == tc
									.getId()) {
								resultListVente.add(o);
							}
						}
					}
					ec.getSessionMap().put("listeOffreAchat", resultListAchat);
					ec.getSessionMap().put("listeOffreVente", resultListVente);

					em.clear();
					em.close();
					ec.redirect(ec.getRequestContextPath()
							+ "/private/investisseur/ResultatRechercheOffre.xhtml");

				}
				em.clear();
				em.close();

			}

		} catch (Exception e) {
			msg.setDetail("Erreur recherche");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

	}

	public List<Offre> getListeOffreAdministrateurAchat() {
		EntityManager em;
		em = getEntityManager();
		em.getTransaction().begin();
		Query q = em.createNamedQuery("Offre.findByStatutType");
		q.setParameter("Statut", "En Cours");
		q.setParameter("Type", 0);
		List<Offre> listeOffreAdministrateurAchat = q.getResultList();
		em.clear();
		em.close();
		return listeOffreAdministrateurAchat;
	}

	public void setListeOffreAdministrateurAchat(
			List<Offre> listeOffreAdministrateurAchat) {
		this.listeOffreAdministrateurAchat = listeOffreAdministrateurAchat;
	}

	public List<Offre> getListeOffreAdministrateurVente() {
		EntityManager em;
		em = getEntityManager();
		em.getTransaction().begin();
		Query q = em.createNamedQuery("Offre.findByStatutType");
		q.setParameter("Statut", "En Cours");
		q.setParameter("Type", 1);
		List<Offre> listeOffreAdministrateurVente = q.getResultList();
		em.clear();
		em.close();
		return listeOffreAdministrateurVente;
	}

	public void setListeOffreAdministrateurVente(
			List<Offre> listeOffreAdministrateurVente) {
		this.listeOffreAdministrateurVente = listeOffreAdministrateurVente;
	}

}
