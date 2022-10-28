package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SessionForm.
 */
@Entity
@Table(name = "session_form")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SessionForm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom_session")
    private String nomSession;

    @Column(name = "annee")
    private String annee;

    @Column(name = "date_debut_sess")
    private LocalDate dateDebutSess;

    @Column(name = "date_fin_sess")
    private LocalDate dateFinSess;

    @ManyToOne
    @JsonIgnoreProperties(value = { "priseEnCharges", "concours", "sessionForms", "demande" }, allowSetters = true)
    private Formation formation;

    @ManyToOne
    @JsonIgnoreProperties(value = { "filieres", "series", "sessionForms", "localite" }, allowSetters = true)
    private Etablissement etablissement;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SessionForm id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomSession() {
        return this.nomSession;
    }

    public SessionForm nomSession(String nomSession) {
        this.setNomSession(nomSession);
        return this;
    }

    public void setNomSession(String nomSession) {
        this.nomSession = nomSession;
    }

    public String getAnnee() {
        return this.annee;
    }

    public SessionForm annee(String annee) {
        this.setAnnee(annee);
        return this;
    }

    public void setAnnee(String annee) {
        this.annee = annee;
    }

    public LocalDate getDateDebutSess() {
        return this.dateDebutSess;
    }

    public SessionForm dateDebutSess(LocalDate dateDebutSess) {
        this.setDateDebutSess(dateDebutSess);
        return this;
    }

    public void setDateDebutSess(LocalDate dateDebutSess) {
        this.dateDebutSess = dateDebutSess;
    }

    public LocalDate getDateFinSess() {
        return this.dateFinSess;
    }

    public SessionForm dateFinSess(LocalDate dateFinSess) {
        this.setDateFinSess(dateFinSess);
        return this;
    }

    public void setDateFinSess(LocalDate dateFinSess) {
        this.dateFinSess = dateFinSess;
    }

    public Formation getFormation() {
        return this.formation;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }

    public SessionForm formation(Formation formation) {
        this.setFormation(formation);
        return this;
    }

    public Etablissement getEtablissement() {
        return this.etablissement;
    }

    public void setEtablissement(Etablissement etablissement) {
        this.etablissement = etablissement;
    }

    public SessionForm etablissement(Etablissement etablissement) {
        this.setEtablissement(etablissement);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SessionForm)) {
            return false;
        }
        return id != null && id.equals(((SessionForm) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SessionForm{" +
            "id=" + getId() +
            ", nomSession='" + getNomSession() + "'" +
            ", annee='" + getAnnee() + "'" +
            ", dateDebutSess='" + getDateDebutSess() + "'" +
            ", dateFinSess='" + getDateFinSess() + "'" +
            "}";
    }
}
