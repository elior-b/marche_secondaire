package bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJBException;
import javax.ejb.NoMoreTimeoutsException;
import javax.ejb.NoSuchObjectLocalException;
import javax.ejb.ScheduleExpression;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerHandle;
import javax.ejb.TimerService;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import model.Contrat;
import model.Enchere;
import model.Investisseur;
import model.Offre;
import model.Transaction;
import model.TypeContrat;

import org.jboss.logging.Logger;

import utilitaire.JavaPersistenceUtilitaire;

@Stateless
@ManagedBean
@RequestScoped
public class TimerBean extends JavaPersistenceUtilitaire implements MyTimer {
	private static Logger logger = Logger.getLogger(TimerBean.class.getName());
	int idOffre;
	@Resource
	TimerService timerService;

	public void startTimer(Serializable info, int idOffre, long duration) {
		emf = JavaPersistenceUtilitaire.getEmf();
		Timer timer = timerService.createTimer(duration, info);
		this.idOffre = idOffre;
	}

	private EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public void stopTimer(Serializable info) {
		Timer timer;
		Collection timers = timerService.getTimers();
		for (Object object : timers) {
			timer = ((Timer) object);
			if (timer.getInfo().equals(info)) {
				timer.cancel();
				break;
			}
		}
	}

	@Timeout
	public void logMessage(Timer timer) {
		logger.info("Message produit par :" + timer.getInfo() + " at "
				+ System.currentTimeMillis());
		EntityManager em = null;
		em = getEntityManager();
		em.getTransaction().begin();
		Query q = em.createNamedQuery("Offre.findById");
		q.setParameter("IdOffre", idOffre);
		Offre o = (Offre) q.getSingleResult();

		if (o.getTypeOffre().getId() == 1) {
			o.setStatut("Terminee");
			em.persist(o);
			em.getTransaction().commit();
			em.clear();
			em.close();
		} else {

			if (o.getType() == 0) {
				// TYPE ACHAT
				q = em.createNamedQuery("Enchere.findForListeEnchereAchat");
				q.setParameter("Offre", o);
				List<Enchere> listeEnchere = q.getResultList();

				if (listeEnchere.size() > 0) {
					boolean continuer = true;
					Investisseur emetteur = o.getInvestisseur();
					if (o.getEcheance() != null) {
						q = em.createNamedQuery("Contrat.findByInvestisseurSocieteTypeContratDate");
						q.setParameter("Investisseur", emetteur);
						q.setParameter("Societe", o.getSociete());
						q.setParameter("TypeContrat", o.getTypeContrat());
						q.setParameter("Date", o.getEcheance());
					} else {
						q = em.createNamedQuery("Contrat.findByInvestisseurSocieteTypeContrat");
						q.setParameter("Investisseur", emetteur);
						q.setParameter("Societe", o.getSociete());
						q.setParameter("TypeContrat", o.getTypeContrat());
					}
					List<Contrat> listeCEmetteur = q.getResultList();
					Contrat cEmetteur = null;
					if (listeCEmetteur.size() > 0) {
						cEmetteur = listeCEmetteur.get(0);
					}
					for (int i = 0; i < listeEnchere.size() && continuer; i++) {
						Enchere e = listeEnchere.get(i);
						Investisseur investisseur = e.getInvestisseur();
						if (accepterOffreAchat(o, investisseur, emetteur,
								cEmetteur, e)) {
							continuer = false;
						}
						if (continuer) {
							em.remove(e);
							em.getTransaction().commit();

						}

					}
					em.clear();
					em.close();
				} else {
					o.setStatut("Terminee");
					em.persist(o);
					em.getTransaction().commit();
					em.clear();
					em.close();
				}

			} else {
				// TYPE VENTE
				q = em.createNamedQuery("Enchere.findForListeEnchereVente");
				q.setParameter("Offre", o);
				List<Enchere> listeEnchere = q.getResultList();

				if (listeEnchere.size() > 0) {
					boolean continuer = true;
					Investisseur emetteur = o.getInvestisseur();
					q = em.createNamedQuery("Contrat.findById");
					q.setParameter("IdContrat", o.getContrat().getId());
					List<Contrat> listeCEmetteur = q.getResultList();
					Contrat cEmetteur = null;
					if (listeCEmetteur.size() > 0) {
						cEmetteur = listeCEmetteur.get(0);
					}
					for (int i = 0; i < listeEnchere.size() && continuer; i++) {
						Enchere e = listeEnchere.get(i);
						Investisseur investisseur = e.getInvestisseur();
						if (accepterOffreVente(o, investisseur, emetteur,
								cEmetteur, e)) {
							continuer = false;
						}
						if (continuer) {
							em.remove(e);
							em.getTransaction().commit();
						}
					}
					em.clear();
					em.close();
				} else {
					o.setStatut("Terminee");
					em.persist(o);
					em.getTransaction().commit();
					em.clear();
					em.close();
				}

			}

		}

		timer.cancel();
	}

	@Override
	public void logMessage(MyTimer timer) {
		// TODO Auto-generated method stub

	}

	public boolean accepterOffreAchat(Offre o, Investisseur i, Investisseur e1,
			Contrat cEmetteur, Enchere e) {
		EntityManager em = null;
		em = getEntityManager();
		em.getTransaction().begin();
		Query q;
		q = em.createNamedQuery("Offre.findById");
		q.setParameter("IdOffre", o.getId());
		Offre monOffre = (Offre) q.getSingleResult();
		q = em.createNamedQuery("Investisseur.findById");
		q.setParameter("IdInvestisseur", e1.getId());
		Investisseur emetteur = (Investisseur) q.getSingleResult();

		/*
		 * On récupère le contrat de l'investisseur connecté, qui comprend la
		 * societe / type offre / type contrat de l'OFFRE en question Si l'offre
		 * en question possède une échéance, alors on recherche le contrat qui a
		 * EN PLUS la même date.
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

		if (listeC.size() > 0) {
			Contrat c = listeC.get(0);
			/*
			 * On vérifie que la quantité du contrat du vendeur (investisseur
			 * connecté) est supérieure ou égale à celle de l'offre
			 */
			if (c.getQuantite() >= monOffre.getQuantite()) {
				/*
				 * On vérifie que le solde de l'acheteur du contrat (emetteur)
				 * est supérieur ou égal au montant de l'offre
				 */
				if (emetteur.getSolde() > (e.getPrix() * monOffre.getQuantite())) {
					/*
					 * Mise à jour de la quantité du contrat du vendeur
					 * (investisseur connecté)
					 */
					c.setQuantite(c.getQuantite() - monOffre.getQuantite());
					em.persist(c);
					if (cEmetteur != null) {
						/*
						 * Mise à jour de la quantité du contrat de l'emetteur
						 */
						cEmetteur.setQuantite(cEmetteur.getQuantite()
								+ monOffre.getQuantite());
						em.persist(cEmetteur);

					} else {
						/*
						 * Création du contrat de l'emetteur si il n'existe pas
						 * deja
						 */
						Contrat newContrat = new Contrat();
						newContrat.setInvestisseur(emetteur);
						newContrat.setQuantite(monOffre.getQuantite());
						newContrat.setSociete(monOffre.getSociete());
						newContrat.setTypeContrat(monOffre.getTypeContrat());
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
							- (e.getPrix() * monOffre.getQuantite()));
					q = em.createNamedQuery("Investisseur.findById");
					q.setParameter("IdInvestisseur", i.getId());
					Investisseur investisseur = (Investisseur) q
							.getSingleResult();
					investisseur.setSolde(investisseur.getSolde()
							+ (e.getPrix() * monOffre.getQuantite()));
					em.persist(investisseur);

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
					return true;

				} else {
					em.clear();
					em.close();
					return false;
				}
			} else {
				em.clear();
				em.close();
				return false;
			}
		} else {
			em.clear();
			em.close();
			return false;
		}

	}

	public boolean accepterOffreVente(Offre o, Investisseur inv,
			Investisseur e1, Contrat cE, Enchere e) {
		EntityManager em = null;
		em = getEntityManager();
		em.getTransaction().begin();

		Query q = em.createNamedQuery("Offre.findById");
		q.setParameter("IdOffre", o.getId());
		Offre monOffre = (Offre) q.getSingleResult();

		q = em.createNamedQuery("Investisseur.findById");
		q.setParameter("IdInvestisseur", e1.getId());
		Investisseur emetteur = (Investisseur) q.getSingleResult();
		/*
		 * De même que précédent mais c'est le contrat de l'emetteur
		 */
		q = em.createNamedQuery("Contrat.findById");
		q.setParameter("IdContrat", cE.getId());
		Contrat cEmetteur = (Contrat) q.getSingleResult();

		if (cEmetteur != null) {
			if (cEmetteur.getQuantite() >= monOffre.getQuantite()) {
				/*
				 * On récupère l'investisseur connecté (avec son Id en session)
				 */
				q = em.createNamedQuery("Investisseur.findById");
				q.setParameter("IdInvestisseur", inv.getId());
				Investisseur i = (Investisseur) q.getSingleResult();

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

				if (i.getSolde() >= (monOffre.getQuantite() * e.getPrix())) {
					/*
					 * Mise à jour de la quantité du contrat du vendeur
					 */
					cEmetteur.setQuantite(cEmetteur.getQuantite()
							- monOffre.getQuantite());
					em.persist(cEmetteur);
					if (listeC.size() > 0) {

						Contrat c = listeC.get(0);
						/*
						 * Mise à jour de la quantité du contrat de acheteur
						 * (investisseur connecté)
						 */
						c.setQuantite(c.getQuantite() + monOffre.getQuantite());
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
							+ (e.getPrix() * monOffre.getQuantite()));
					i.setSolde(i.getSolde()
							- (e.getPrix() * monOffre.getQuantite()));
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
					return true;

				} else {
					em.clear();
					em.close();
					return false;
				}

			} else {
				em.clear();
				em.close();
				return false;
			}

		} else {
			em.clear();
			em.close();
			return false;
		}

	}

}