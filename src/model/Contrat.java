package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the contrat database table.
 * 
 */
@Entity
@NamedQuery(name="Contrat.findAll", query="SELECT c FROM Contrat c")
public class Contrat implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	private int quantite;

	//bi-directional many-to-one association to Societe
	@ManyToOne
	@JoinColumn(name="IdSociete")
	private Societe societe;

	//bi-directional many-to-one association to Investisseur
	@ManyToOne
	@JoinColumn(name="IdInvestisseur")
	private Investisseur investisseur;

	//bi-directional many-to-one association to TypeContrat
	@ManyToOne
	@JoinColumn(name="IdTypeContrat")
	private TypeContrat typeContrat;

	//bi-directional many-to-one association to Offre
	@OneToMany(mappedBy="contrat")
	private List<Offre> offres;

	public Contrat() {
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

	public int getQuantite() {
		return this.quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	public Societe getSociete() {
		return this.societe;
	}

	public void setSociete(Societe societe) {
		this.societe = societe;
	}

	public Investisseur getInvestisseur() {
		return this.investisseur;
	}

	public void setInvestisseur(Investisseur investisseur) {
		this.investisseur = investisseur;
	}

	public TypeContrat getTypeContrat() {
		return this.typeContrat;
	}

	public void setTypeContrat(TypeContrat typeContrat) {
		this.typeContrat = typeContrat;
	}

	public List<Offre> getOffres() {
		return this.offres;
	}

	public void setOffres(List<Offre> offres) {
		this.offres = offres;
	}

	public Offre addOffre(Offre offre) {
		getOffres().add(offre);
		offre.setContrat(this);

		return offre;
	}

	public Offre removeOffre(Offre offre) {
		getOffres().remove(offre);
		offre.setContrat(null);

		return offre;
	}

}