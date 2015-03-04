package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the enchere database table.
 * 
 */
@Entity
@NamedQuery(name="Enchere.findAll", query="SELECT e FROM Enchere e")
public class Enchere implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	private double prix;

	//bi-directional many-to-one association to Offre
	@ManyToOne
	@JoinColumn(name="IdOffre")
	private Offre offre;

	//bi-directional many-to-one association to Investisseur
	@ManyToOne
	@JoinColumn(name="IdEncherisseur")
	private Investisseur investisseur;

	public Enchere() {
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

	public double getPrix() {
		return this.prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	public Offre getOffre() {
		return this.offre;
	}

	public void setOffre(Offre offre) {
		this.offre = offre;
	}

	public Investisseur getInvestisseur() {
		return this.investisseur;
	}

	public void setInvestisseur(Investisseur investisseur) {
		this.investisseur = investisseur;
	}

}