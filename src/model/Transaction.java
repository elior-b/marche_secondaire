package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the transaction database table.
 * 
 */
@Entity
@NamedQueries({
		@NamedQuery(name = "Transaction.findAll", query = "SELECT t FROM Transaction t"),
		@NamedQuery(name = "Transaction.findByOffre", query = "SELECT t FROM Transaction t WHERE t.offre = :Offre"),
		@NamedQuery(name = "Transaction.findByRecepteur", query = "SELECT t FROM Transaction t WHERE t.investisseur = :Recepteur") })
public class Transaction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	// bi-directional many-to-one association to Investisseur
	@ManyToOne
	@JoinColumn(name = "IdRecepteur")
	private Investisseur investisseur;

	// bi-directional many-to-one association to Offre
	@ManyToOne
	@JoinColumn(name = "IdOffre")
	private Offre offre;

	public Transaction() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Investisseur getInvestisseur() {
		return this.investisseur;
	}

	public void setInvestisseur(Investisseur investisseur) {
		this.investisseur = investisseur;
	}

	public Offre getOffre() {
		return this.offre;
	}

	public void setOffre(Offre offre) {
		this.offre = offre;
	}

}