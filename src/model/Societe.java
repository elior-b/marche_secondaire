package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the societe database table.
 * 
 */
@Entity
@NamedQueries({
		@NamedQuery(name = "Societe.findAll", query = "SELECT s FROM Societe s"),
		@NamedQuery(name = "Societe.findByNom", query = "SELECT s FROM Societe s WHERE s.nom = :Nom"),
		@NamedQuery(name = "Societe.findLikeNom", query = "SELECT s FROM Societe s WHERE s.nom LIKE :Nom"),
		@NamedQuery(name = "Societe.findLikeSecteur", query = "SELECT s FROM Societe s WHERE s.secteurActivite LIKE :SecteurActivite"),
		@NamedQuery(name = "Societe.findLikeNomSecteur", query = "SELECT s FROM Societe s WHERE s.nom LIKE :Nom AND s.secteurActivite LIKE :SecteurActivite"),
		@NamedQuery(name = "Societe.findById", query = "SELECT s FROM Societe s WHERE s.id = :IdSociete") })
public class Societe implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String adresse;

	private double capitalisationBoursiere;

	private String description;

	private String dirigeant;

	private String nom;

	private String raisonSociale;

	private String secteurActivite;

	private String siteWeb;

	private String telephone;

	// bi-directional many-to-one association to Contrat
	@OneToMany(mappedBy = "societe")
	private List<Contrat> contrats;

	// bi-directional many-to-one association to DonneeFinanciere
	@OneToMany(mappedBy = "societe")
	private List<DonneeFinanciere> donneeFinancieres;

	// bi-directional many-to-one association to MembreSociete
	@OneToMany(mappedBy = "societe")
	private List<MembreSociete> membreSocietes;

	// bi-directional many-to-one association to Offre
	@OneToMany(mappedBy = "societe")
	private List<Offre> offres;

	// bi-directional many-to-one association to Administrateur
	@ManyToOne
	@JoinColumn(name = "IdAdministrateur")
	private Administrateur administrateur;

	public Societe() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAdresse() {
		return this.adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public double getCapitalisationBoursiere() {
		return this.capitalisationBoursiere;
	}

	public void setCapitalisationBoursiere(double capitalisationBoursiere) {
		this.capitalisationBoursiere = capitalisationBoursiere;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDirigeant() {
		return this.dirigeant;
	}

	public void setDirigeant(String dirigeant) {
		this.dirigeant = dirigeant;
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getRaisonSociale() {
		return this.raisonSociale;
	}

	public void setRaisonSociale(String raisonSociale) {
		this.raisonSociale = raisonSociale;
	}

	public String getSecteurActivite() {
		return this.secteurActivite;
	}

	public void setSecteurActivite(String secteurActivite) {
		this.secteurActivite = secteurActivite;
	}

	public String getSiteWeb() {
		return this.siteWeb;
	}

	public void setSiteWeb(String siteWeb) {
		this.siteWeb = siteWeb;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public List<Contrat> getContrats() {
		return this.contrats;
	}

	public void setContrats(List<Contrat> contrats) {
		this.contrats = contrats;
	}

	public Contrat addContrat(Contrat contrat) {
		getContrats().add(contrat);
		contrat.setSociete(this);

		return contrat;
	}

	public Contrat removeContrat(Contrat contrat) {
		getContrats().remove(contrat);
		contrat.setSociete(null);

		return contrat;
	}

	public List<DonneeFinanciere> getDonneeFinancieres() {
		return this.donneeFinancieres;
	}

	public void setDonneeFinancieres(List<DonneeFinanciere> donneeFinancieres) {
		this.donneeFinancieres = donneeFinancieres;
	}

	public DonneeFinanciere addDonneeFinanciere(
			DonneeFinanciere donneeFinanciere) {
		getDonneeFinancieres().add(donneeFinanciere);
		donneeFinanciere.setSociete(this);

		return donneeFinanciere;
	}

	public DonneeFinanciere removeDonneeFinanciere(
			DonneeFinanciere donneeFinanciere) {
		getDonneeFinancieres().remove(donneeFinanciere);
		donneeFinanciere.setSociete(null);

		return donneeFinanciere;
	}

	public List<MembreSociete> getMembreSocietes() {
		return this.membreSocietes;
	}

	public void setMembreSocietes(List<MembreSociete> membreSocietes) {
		this.membreSocietes = membreSocietes;
	}

	public MembreSociete addMembreSociete(MembreSociete membreSociete) {
		getMembreSocietes().add(membreSociete);
		membreSociete.setSociete(this);

		return membreSociete;
	}

	public MembreSociete removeMembreSociete(MembreSociete membreSociete) {
		getMembreSocietes().remove(membreSociete);
		membreSociete.setSociete(null);

		return membreSociete;
	}

	public List<Offre> getOffres() {
		return this.offres;
	}

	public void setOffres(List<Offre> offres) {
		this.offres = offres;
	}

	public Offre addOffre(Offre offre) {
		getOffres().add(offre);
		offre.setSociete(this);

		return offre;
	}

	public Offre removeOffre(Offre offre) {
		getOffres().remove(offre);
		offre.setSociete(null);

		return offre;
	}

	public Administrateur getAdministrateur() {
		return this.administrateur;
	}

	public void setAdministrateur(Administrateur administrateur) {
		this.administrateur = administrateur;
	}

}