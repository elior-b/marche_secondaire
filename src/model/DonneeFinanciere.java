package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the donnee_financiere database table.
 * 
 */
@Entity
@Table(name="donnee_financiere")
@NamedQuery(name="DonneeFinanciere.findAll", query="SELECT d FROM DonneeFinanciere d")
public class DonneeFinanciere implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private int annee;

	private double ca;

	private double coutRisque;

	private double pnb;

	private double rcai;

	private double rex;

	private double rn;

	//bi-directional many-to-one association to Societe
	@ManyToOne
	@JoinColumn(name="IdSociete")
	private Societe societe;

	public DonneeFinanciere() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAnnee() {
		return this.annee;
	}

	public void setAnnee(int annee) {
		this.annee = annee;
	}

	public double getCa() {
		return this.ca;
	}

	public void setCa(double ca) {
		this.ca = ca;
	}

	public double getCoutRisque() {
		return this.coutRisque;
	}

	public void setCoutRisque(double coutRisque) {
		this.coutRisque = coutRisque;
	}

	public double getPnb() {
		return this.pnb;
	}

	public void setPnb(double pnb) {
		this.pnb = pnb;
	}

	public double getRcai() {
		return this.rcai;
	}

	public void setRcai(double rcai) {
		this.rcai = rcai;
	}

	public double getRex() {
		return this.rex;
	}

	public void setRex(double rex) {
		this.rex = rex;
	}

	public double getRn() {
		return this.rn;
	}

	public void setRn(double rn) {
		this.rn = rn;
	}

	public Societe getSociete() {
		return this.societe;
	}

	public void setSociete(Societe societe) {
		this.societe = societe;
	}

}