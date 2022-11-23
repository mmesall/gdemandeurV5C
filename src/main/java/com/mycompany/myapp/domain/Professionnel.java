package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.Sexe;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Professionnel.
 */
@Entity
@Table(name = "professionnel")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Professionnel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "date_naiss")
    private LocalDate dateNaiss;

    @Column(name = "lieu_naiss")
    private String lieuNaiss;

    @Enumerated(EnumType.STRING)
    @Column(name = "sexe")
    private Sexe sexe;

    @Column(name = "telephone")
    private Long telephone;

    @Column(name = "adresse_physique")
    private String adressePhysique;

    @Column(name = "email")
    private String email;

    @Column(name = "cni")
    private Long cni;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @JsonIgnoreProperties(value = { "user", "demandes", "eleve", "etudiant", "professionnel", "demandeur" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Dossier dossier;

    @JsonIgnoreProperties(value = { "dossier", "user", "eleve", "etudiant", "professionnel" }, allowSetters = true)
    @OneToOne(mappedBy = "professionnel")
    private Demandeur demandeur;

    @ManyToOne
    @JsonIgnoreProperties(value = { "etablissements", "eleves", "etudiants", "professionnels", "agents" }, allowSetters = true)
    private Localite localite;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Professionnel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public Professionnel nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public Professionnel prenom(String prenom) {
        this.setPrenom(prenom);
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDate getDateNaiss() {
        return this.dateNaiss;
    }

    public Professionnel dateNaiss(LocalDate dateNaiss) {
        this.setDateNaiss(dateNaiss);
        return this;
    }

    public void setDateNaiss(LocalDate dateNaiss) {
        this.dateNaiss = dateNaiss;
    }

    public String getLieuNaiss() {
        return this.lieuNaiss;
    }

    public Professionnel lieuNaiss(String lieuNaiss) {
        this.setLieuNaiss(lieuNaiss);
        return this;
    }

    public void setLieuNaiss(String lieuNaiss) {
        this.lieuNaiss = lieuNaiss;
    }

    public Sexe getSexe() {
        return this.sexe;
    }

    public Professionnel sexe(Sexe sexe) {
        this.setSexe(sexe);
        return this;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public Long getTelephone() {
        return this.telephone;
    }

    public Professionnel telephone(Long telephone) {
        this.setTelephone(telephone);
        return this;
    }

    public void setTelephone(Long telephone) {
        this.telephone = telephone;
    }

    public String getAdressePhysique() {
        return this.adressePhysique;
    }

    public Professionnel adressePhysique(String adressePhysique) {
        this.setAdressePhysique(adressePhysique);
        return this;
    }

    public void setAdressePhysique(String adressePhysique) {
        this.adressePhysique = adressePhysique;
    }

    public String getEmail() {
        return this.email;
    }

    public Professionnel email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getCni() {
        return this.cni;
    }

    public Professionnel cni(Long cni) {
        this.setCni(cni);
        return this;
    }

    public void setCni(Long cni) {
        this.cni = cni;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Professionnel user(User user) {
        this.setUser(user);
        return this;
    }

    public Dossier getDossier() {
        return this.dossier;
    }

    public void setDossier(Dossier dossier) {
        this.dossier = dossier;
    }

    public Professionnel dossier(Dossier dossier) {
        this.setDossier(dossier);
        return this;
    }

    public Demandeur getDemandeur() {
        return this.demandeur;
    }

    public void setDemandeur(Demandeur demandeur) {
        if (this.demandeur != null) {
            this.demandeur.setProfessionnel(null);
        }
        if (demandeur != null) {
            demandeur.setProfessionnel(this);
        }
        this.demandeur = demandeur;
    }

    public Professionnel demandeur(Demandeur demandeur) {
        this.setDemandeur(demandeur);
        return this;
    }

    public Localite getLocalite() {
        return this.localite;
    }

    public void setLocalite(Localite localite) {
        this.localite = localite;
    }

    public Professionnel localite(Localite localite) {
        this.setLocalite(localite);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Professionnel)) {
            return false;
        }
        return id != null && id.equals(((Professionnel) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Professionnel{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", dateNaiss='" + getDateNaiss() + "'" +
            ", lieuNaiss='" + getLieuNaiss() + "'" +
            ", sexe='" + getSexe() + "'" +
            ", telephone=" + getTelephone() +
            ", adressePhysique='" + getAdressePhysique() + "'" +
            ", email='" + getEmail() + "'" +
            ", cni=" + getCni() +
            "}";
    }
}
