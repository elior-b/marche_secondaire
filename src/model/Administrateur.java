package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the administrateur database table.
 * 
 */
@Entity
@NamedQueries({
@NamedQuery(name="Administrateur.findAll", query="SELECT a FROM Administrateur a"),
@NamedQuery(name="Administrateur.findByLogin", query="SELECT a FROM Administrateur a WHERE a.login = :LoginAdministrateur ")
})
public class Administrateur implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String login;

	private String motDePasse;

	private String nom;

	private String prenom;

	//bi-directional many-to-one association to Societe
	@OneToMany(mappedBy="administrateur")
	private List<Societe> societes;

	public Administrateur() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
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

	public List<Societe> getSocietes() {
		return this.societes;
	}

	public void setSocietes(List<Societe> societes) {
		this.societes = societes;
	}

	public Societe addSociete(Societe societe) {
		getSocietes().add(societe);
		societe.setAdministrateur(this);

		return societe;
	}

	public Societe removeSociete(Societe societe) {
		getSocietes().remove(societe);
		societe.setAdministrateur(null);

		return societe;
	}

}