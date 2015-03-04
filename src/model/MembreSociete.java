package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the membre_societe database table.
 * 
 */
@Entity
@Table(name="membre_societe")
@NamedQuery(name="MembreSociete.findAll", query="SELECT m FROM MembreSociete m")
public class MembreSociete implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String login;

	private String motDePasse;

	private String nom;

	private String prenom;

	//bi-directional many-to-one association to Societe
	@ManyToOne
	@JoinColumn(name="IdSociete")
	private Societe societe;

	public MembreSociete() {
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

	public Societe getSociete() {
		return this.societe;
	}

	public void setSociete(Societe societe) {
		this.societe = societe;
	}

}