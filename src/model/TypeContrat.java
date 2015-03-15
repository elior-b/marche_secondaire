package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the type_contrat database table.
 * 
 */
@Entity
@Table(name = "type_contrat")
@NamedQueries({
		@NamedQuery(name = "TypeContrat.findAll", query = "SELECT t FROM TypeContrat t"),
		@NamedQuery(name = "TypeContrat.findById", query = "SELECT t FROM TypeContrat t WHERE t.id = :IdTypeContrat") })
public class TypeContrat implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String nom;

	// bi-directional many-to-one association to Contrat
	@OneToMany(mappedBy = "typeContrat")
	private List<Contrat> contrats;

	// bi-directional many-to-one association to Offre
	@OneToMany(mappedBy = "typeContrat")
	private List<Offre> offres;

	public TypeContrat() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public List<Contrat> getContrats() {
		return this.contrats;
	}

	public void setContrats(List<Contrat> contrats) {
		this.contrats = contrats;
	}

	public Contrat addContrat(Contrat contrat) {
		getContrats().add(contrat);
		contrat.setTypeContrat(this);

		return contrat;
	}

	public Contrat removeContrat(Contrat contrat) {
		getContrats().remove(contrat);
		contrat.setTypeContrat(null);

		return contrat;
	}

	public List<Offre> getOffres() {
		return this.offres;
	}

	public void setOffres(List<Offre> offres) {
		this.offres = offres;
	}

	public Offre addOffre(Offre offre) {
		getOffres().add(offre);
		offre.setTypeContrat(this);

		return offre;
	}

	public Offre removeOffre(Offre offre) {
		getOffres().remove(offre);
		offre.setTypeContrat(null);

		return offre;
	}

}