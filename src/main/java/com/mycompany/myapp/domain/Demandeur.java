package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.Profil;
import com.mycompany.myapp.domain.enumeration.Sexe;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Demandeur.
 */
@Entity
@Table(name = "demandeur")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Demandeur implements Serializable {

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

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "profil")
    private Profil profil;

    @JsonIgnoreProperties(value = { "user", "demandes", "eleve", "etudiant", "professionnel", "demandeur" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Dossier dossier;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @JsonIgnoreProperties(value = { "user", "dossier", "demandeur", "localite" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Eleve eleve;

    @JsonIgnoreProperties(value = { "user", "dossier", "demandeur", "localite" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Etudiant etudiant;

    @JsonIgnoreProperties(value = { "user", "dossier", "demandeur", "localite" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Professionnel professionnel;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Demandeur id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public Demandeur nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public Demandeur prenom(String prenom) {
        this.setPrenom(prenom);
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDate getDateNaiss() {
        return this.dateNaiss;
    }

    public Demandeur dateNaiss(LocalDate dateNaiss) {
        this.setDateNaiss(dateNaiss);
        return this;
    }

    public void setDateNaiss(LocalDate dateNaiss) {
        this.dateNaiss = dateNaiss;
    }

    public String getLieuNaiss() {
        return this.lieuNaiss;
    }

    public Demandeur lieuNaiss(String lieuNaiss) {
        this.setLieuNaiss(lieuNaiss);
        return this;
    }

    public void setLieuNaiss(String lieuNaiss) {
        this.lieuNaiss = lieuNaiss;
    }

    public Sexe getSexe() {
        return this.sexe;
    }

    public Demandeur sexe(Sexe sexe) {
        this.setSexe(sexe);
        return this;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public Long getTelephone() {
        return this.telephone;
    }

    public Demandeur telephone(Long telephone) {
        this.setTelephone(telephone);
        return this;
    }

    public void setTelephone(Long telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return this.email;
    }

    public Demandeur email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Profil getProfil() {
        return this.profil;
    }

    public Demandeur profil(Profil profil) {
        this.setProfil(profil);
        return this;
    }

    public void setProfil(Profil profil) {
        this.profil = profil;
    }

    public Dossier getDossier() {
        return this.dossier;
    }

    public void setDossier(Dossier dossier) {
        this.dossier = dossier;
    }

    public Demandeur dossier(Dossier dossier) {
        this.setDossier(dossier);
        return this;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Demandeur user(User user) {
        this.setUser(user);
        return this;
    }

    public Eleve getEleve() {
        return this.eleve;
    }

    public void setEleve(Eleve eleve) {
        this.eleve = eleve;
    }

    public Demandeur eleve(Eleve eleve) {
        this.setEleve(eleve);
        return this;
    }

    public Etudiant getEtudiant() {
        return this.etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public Demandeur etudiant(Etudiant etudiant) {
        this.setEtudiant(etudiant);
        return this;
    }

    public Professionnel getProfessionnel() {
        return this.professionnel;
    }

    public void setProfessionnel(Professionnel professionnel) {
        this.professionnel = professionnel;
    }

    public Demandeur professionnel(Professionnel professionnel) {
        this.setProfessionnel(professionnel);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Demandeur)) {
            return false;
        }
        return id != null && id.equals(((Demandeur) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Demandeur{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", dateNaiss='" + getDateNaiss() + "'" +
            ", lieuNaiss='" + getLieuNaiss() + "'" +
            ", sexe='" + getSexe() + "'" +
            ", telephone=" + getTelephone() +
            ", email='" + getEmail() + "'" +
            ", profil='" + getProfil() + "'" +
            "}";
    }
}
