package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the investisseur database table.
 * 
 */
@Entity
@NamedQueries({
		@NamedQuery(name = "Investisseur.findAll", query = "SELECT i FROM Investisseur i"),
		@NamedQuery(name = "Investisseur.findById", query = "SELECT i FROM Investisseur i WHERE i.id = :IdInvestisseur"),
		@NamedQuery(name = "Investisseur.findByStatut", query = "SELECT i FROM Investisseur i WHERE i.statut = :Statut"),
		@NamedQuery(name = "Investisseur.findByLogin", query = "SELECT i FROM Investisseur i WHERE i.login = :LoginInvestisseur") })
public class Investisseur implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String carteBleue;

	private String cryptogramme;

	private String email;

	private String login;

	private String motDePasse;

	private String nom;

	private String prenom;

	private double solde;

	private int statut;

	// bi-directional many-to-one association to Contrat
	@OneToMany(mappedBy = "investisseur")
	private List<Contrat> contrats;

	// bi-directional many-to-one association to Enchere
	@OneToMany(mappedBy = "investisseur")
	private List<Enchere> encheres;

	// bi-directional many-to-one association to Offre
	@OneToMany(mappedBy = "investisseur")
	private List<Offre> offres;

	// bi-directional many-to-one association to Transaction
	@OneToMany(mappedBy = "investisseur")
	private List<Transaction> transactions;

	public Investisseur() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCarteBleue() {
		return this.carteBleue;
	}

	public void setCarteBleue(String carteBleue) {
		this.carteBleue = carteBleue;
	}

	public String getCryptogramme() {
		return this.cryptogramme;
	}

	public void setCryptogramme(String cryptogramme) {
		this.cryptogramme = cryptogramme;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getMotDePasse() {
		return this.motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return this.prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public double getSolde() {
		return this.solde;
	}

	public void setSolde(double solde) {
		this.solde = solde;
	}

	public int getStatut() {
		return this.statut;
	}

	public void setStatut(int statut) {
		this.statut = statut;
	}

	public List<Contrat> getContrats() {
		return this.contrats;
	}

	public void setContrats(List<Contrat> contrats) {
		this.contrats = contrats;
	}

	public Contrat addContrat(Contrat contrat) {
		getContrats().add(contrat);
		contrat.setInvestisseur(this);

		return contrat;
	}

	public Contrat removeContrat(Contrat contrat) {
		getContrats().remove(contrat);
		contrat.setInvestisseur(null);

		return contrat;
	}

	public List<Enchere> getEncheres() {
		return this.encheres;
	}

	public void setEncheres(List<Enchere> encheres) {
		this.encheres = encheres;
	}

	public Enchere addEnchere(Enchere enchere) {
		getEncheres().add(enchere);
		enchere.setInvestisseur(this);

		return enchere;
	}

	public Enchere removeEnchere(Enchere enchere) {
		getEncheres().remove(enchere);
		enchere.setInvestisseur(null);

		return enchere;
	}

	public List<Offre> getOffres() {
		return this.offres;
	}

	public void setOffres(List<Offre> offres) {
		this.offres = offres;
	}

	public Offre addOffre(Offre offre) {
		getOffres().add(offre);
		offre.setInvestisseur(this);

		return offre;
	}

	public Offre removeOffre(Offre offre) {
		getOffres().remove(offre);
		offre.setInvestisseur(null);

		return offre;
	}

	public List<Transaction> getTransactions() {
		return this.transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public Transaction addTransaction(Transaction transaction) {
		getTransactions().add(transaction);
		transaction.setInvestisseur(this);

		return transaction;
	}

	public Transaction removeTransaction(Transaction transaction) {
		getTransactions().remove(transaction);
		transaction.setInvestisseur(null);

		return transaction;
	}

}