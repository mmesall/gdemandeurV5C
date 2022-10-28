package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PriseEnCharge.
 */
@Entity
@Table(name = "prise_en_charge")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PriseEnCharge implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "montant")
    private Double montant;

    @Column(name = "nbre_pc")
    private Long nbrePC;

    @Column(name = "details")
    private String details;

    @ManyToOne
    @JsonIgnoreProperties(value = { "agent", "priseEnCharges" }, allowSetters = true)
    private Bailleur bailleur;

    @ManyToOne
    @JsonIgnoreProperties(value = { "priseEnCharges", "concours", "sessionForms", "demande" }, allowSetters = true)
    private Formation formation;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PriseEnCharge id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public PriseEnCharge libelle(String libelle) {
        this.setLibelle(libelle);
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Double getMontant() {
        return this.montant;
    }

    public PriseEnCharge montant(Double montant) {
        this.setMontant(montant);
        return this;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Long getNbrePC() {
        return this.nbrePC;
    }

    public PriseEnCharge nbrePC(Long nbrePC) {
        this.setNbrePC(nbrePC);
        return this;
    }

    public void setNbrePC(Long nbrePC) {
        this.nbrePC = nbrePC;
    }

    public String getDetails() {
        return this.details;
    }

    public PriseEnCharge details(String details) {
        this.setDetails(details);
        return this;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Bailleur getBailleur() {
        return this.bailleur;
    }

    public void setBailleur(Bailleur bailleur) {
        this.bailleur = bailleur;
    }

    public PriseEnCharge bailleur(Bailleur bailleur) {
        this.setBailleur(bailleur);
        return this;
    }

    public Formation getFormation() {
        return this.formation;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }

    public PriseEnCharge formation(Formation formation) {
        this.setFormation(formation);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PriseEnCharge)) {
            return false;
        }
        return id != null && id.equals(((PriseEnCharge) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PriseEnCharge{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", montant=" + getMontant() +
            ", nbrePC=" + getNbrePC() +
            ", details='" + getDetails() + "'" +
            "}";
    }
}
