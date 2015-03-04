package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the type_contrat database table.
 * 
 */
@Entity
@Table(name="type_contrat")
@NamedQuery(name="TypeContrat.findAll", query="SELECT t FROM TypeContrat t")
public class TypeContrat implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String nom;

	//bi-directional many-to-one association to Contrat
	@OneToMany(mappedBy="typeContrat")
	private List<Contrat> contrats;

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

}