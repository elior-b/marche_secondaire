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
@NamedQuery(name="Offre.findAll", query="SELECT o FROM Offre o")
public class Offre implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date echeance;

	private double prix;

	private int quantite;

	private String statut;

	//bi-directional many-to-one association to Enchere
	@OneToMany(mappedBy="offre")
	private List<Enchere> encheres;

	//bi-directional many-to-one association to TypeOffre
	@ManyToOne
	@JoinColumn(name="IdTypeOffre")
	private TypeOffre typeOffre;

	//bi-directional many-to-one association to Contrat
	@ManyToOne
	@JoinColumn(name="IdContrat")
	private Contrat contrat;

	//bi-directional many-to-one association to Investisseur
	@ManyToOne
	@JoinColumn(name="IdEmetteur")
	private Investisseur investisseur;

	//bi-directional many-to-one association to Transaction
	@OneToMany(mappedBy="offre")
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

	public TypeOffre getTypeOffre() {
		return this.typeOffre;
	}

	public void setTypeOffre(TypeOffre typeOffre) {
		this.typeOffre = typeOffre;
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