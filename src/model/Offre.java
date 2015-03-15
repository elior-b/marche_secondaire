package model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.util.List;

/**
 * The persistent class for the offre database table.
 * 
 */
@Entity
@NamedQueries({
		@NamedQuery(name = "Offre.findAll", query = "SELECT o FROM Offre o"),
		@NamedQuery(name = "Offre.findByContratStatut", query = "SELECT o FROM Offre o WHERE o.contrat = :Contrat AND  o.statut = :Statut"),
		@NamedQuery(name = "Offre.findForListeAffichage", query = "SELECT o FROM Offre o WHERE NOT o.investisseur = :Investisseur AND  o.statut = :Statut AND o.type = :Type"),
		@NamedQuery(name = "Offre.findForIdOffreVente", query = "SELECT o FROM Offre o WHERE o.echeance = :Echeance AND  o.contrat = :Contrat AND o.typeOffre = :TypeOffre AND o.prix = :Prix AND o.quantite = :Quantite"),
		@NamedQuery(name = "Offre.findForIdOffreAchat", query = "SELECT o FROM Offre o WHERE o.echeance = :Echeance AND  o.typeContrat = :TypeContrat AND o.societe = :Societe AND o.typeOffre = :TypeOffre AND o.prix = :Prix AND o.quantite = :Quantite"),
		@NamedQuery(name = "Offre.findById", query = "SELECT o FROM Offre o WHERE o.id = :IdOffre"),
		@NamedQuery(name = "Offre.findByStatutType", query = "SELECT o FROM Offre o WHERE o.statut = :Statut AND o.type = :Type"),
		@NamedQuery(name = "Offre.findByMontantMinMontantMax", query = "SELECT o FROM Offre o WHERE o.prix >= :MontantMin AND o.prix <= :MontantMax"),
		@NamedQuery(name = "Offre.findByMontantMin", query = "SELECT o FROM Offre o WHERE o.prix >= :MontantMin"),
		@NamedQuery(name = "Offre.findByEmetteurType", query = "SELECT o FROM Offre o WHERE o.investisseur = :Emetteur AND o.statut  = :Statut AND o.type = :Type"),
		@NamedQuery(name = "Offre.findByEmetteur", query = "SELECT o FROM Offre o WHERE o.investisseur = :Emetteur AND o.statut  = :Statut") })
public class Offre implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date echeance;

	private double prix;

	private int quantite;

	private String statut;

	private int type;

	// bi-directional many-to-one association to Enchere
	@OneToMany(mappedBy = "offre")
	private List<Enchere> encheres;

	// bi-directional many-to-one association to Contrat
	@ManyToOne
	@JoinColumn(name = "IdContrat")
	private Contrat contrat;

	// bi-directional many-to-one association to Investisseur
	@ManyToOne
	@JoinColumn(name = "IdEmetteur")
	private Investisseur investisseur;

	// bi-directional many-to-one association to Societe
	@ManyToOne
	@JoinColumn(name = "IdSociete")
	private Societe societe;

	// bi-directional many-to-one association to TypeContrat
	@ManyToOne
	@JoinColumn(name = "idTypeContrat")
	private TypeContrat typeContrat;

	// bi-directional many-to-one association to TypeOffre
	@ManyToOne
	@JoinColumn(name = "IdTypeOffre")
	private TypeOffre typeOffre;

	// bi-directional many-to-one association to Transaction
	@OneToMany(mappedBy = "offre")
	private List<Transaction> transactions;

	public Offre() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getEcheance() {
		return this.echeance;
	}

	public void setEcheance(Date echeance) {
		this.echeance = echeance;
	}

	public double getPrix() {
		return this.prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	public int getQuantite() {
		return this.quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	public String getStatut() {
		return this.statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

	public int getType() {
		return this.type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public List<Enchere> getEncheres() {
		return this.encheres;
	}

	public void setEncheres(List<Enchere> encheres) {
		this.encheres = encheres;
	}

	public Enchere addEnchere(Enchere enchere) {
		getEncheres().add(enchere);
		enchere.setOffre(this);

		return enchere;
	}

	public Enchere removeEnchere(Enchere enchere) {
		getEncheres().remove(enchere);
		enchere.setOffre(null);

		return enchere;
	}

	public Contrat getContrat() {
		return this.contrat;
	}

	public void setContrat(Contrat contrat) {
		this.contrat = contrat;
	}

	public Investisseur getInvestisseur() {
		return this.investisseur;
	}

	public void setInvestisseur(Investisseur investisseur) {
		this.investisseur = investisseur;
	}

	public Societe getSociete() {
		return this.societe;
	}

	public void setSociete(Societe societe) {
		this.societe = societe;
	}

	public TypeContrat getTypeContrat() {
		return this.typeContrat;
	}

	public void setTypeContrat(TypeContrat typeContrat) {
		this.typeContrat = typeContrat;
	}

	public TypeOffre getTypeOffre() {
		return this.typeOffre;
	}

	public void setTypeOffre(TypeOffre typeOffre) {
		this.typeOffre = typeOffre;
	}

	public List<Transaction> getTransactions() {
		return this.transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public Transaction addTransaction(Transaction transaction) {
		getTransactions().add(transaction);
		transaction.setOffre(this);

		return transaction;
	}

	public Transaction removeTransaction(Transaction transaction) {
		getTransactions().remove(transaction);
		transaction.setOffre(null);

		return transaction;
	}

}