package model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;

/**
 * The persistent class for the type_offre database table.
 * 
 */
@Entity
@Table(name = "type_offre")
@NamedQueries({
		@NamedQuery(name = "TypeOffre.findAll", query = "SELECT t FROM TypeOffre t"),
		@NamedQuery(name = "TypeOffre.findById", query = "SELECT t FROM TypeOffre t WHERE t.id = :IdTypeOffre") })
public class TypeOffre implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String nom;

	// bi-directional many-to-one association to Offre
	@OneToMany(mappedBy = "typeOffre")
	private List<Offre> offres;

	public TypeOffre() {
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

	public List<Offre> getOffres() {
		return this.offres;
	}

	public void setOffres(List<Offre> offres) {
		this.offres = offres;
	}

	public Offre addOffre(Offre offre) {
		getOffres().add(offre);
		offre.setTypeOffre(this);

		return offre;
	}

	public Offre removeOffre(Offre offre) {
		getOffres().remove(offre);
		offre.setTypeOffre(null);

		return offre;
	}

}