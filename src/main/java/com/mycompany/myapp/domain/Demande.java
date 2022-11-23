package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.EtatDemande;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Demande.
 */
@Entity
@Table(name = "demande")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Demande implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "niveau_etude")
    private String niveauEtude;

    @Enumerated(EnumType.STRING)
    @Column(name = "etat_demande")
    private EtatDemande etatDemande;

    @JsonIgnoreProperties(value = { "priseEnCharges", "concours", "sessionForms", "demande" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Formation formation;

    @OneToMany(mappedBy = "demande")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "demande" }, allowSetters = true)
    private Set<PieceJointe> pieceJointes = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "user", "demandes", "eleve", "etudiant", "professionnel", "demandeur" }, allowSetters = true)
    private Dossier dossier;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Demande id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public Demande libelle(String libelle) {
        this.setLibelle(libelle);
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getNiveauEtude() {
        return this.niveauEtude;
    }

    public Demande niveauEtude(String niveauEtude) {
        this.setNiveauEtude(niveauEtude);
        return this;
    }

    public void setNiveauEtude(String niveauEtude) {
        this.niveauEtude = niveauEtude;
    }

    public EtatDemande getEtatDemande() {
        return this.etatDemande;
    }

    public Demande etatDemande(EtatDemande etatDemande) {
        this.setEtatDemande(etatDemande);
        return this;
    }

    public void setEtatDemande(EtatDemande etatDemande) {
        this.etatDemande = etatDemande;
    }

    public Formation getFormation() {
        return this.formation;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }

    public Demande formation(Formation formation) {
        this.setFormation(formation);
        return this;
    }

    public Set<PieceJointe> getPieceJointes() {
        return this.pieceJointes;
    }

    public void setPieceJointes(Set<PieceJointe> pieceJointes) {
        if (this.pieceJointes != null) {
            this.pieceJointes.forEach(i -> i.setDemande(null));
        }
        if (pieceJointes != null) {
            pieceJointes.forEach(i -> i.setDemande(this));
        }
        this.pieceJointes = pieceJointes;
    }

    public Demande pieceJointes(Set<PieceJointe> pieceJointes) {
        this.setPieceJointes(pieceJointes);
        return this;
    }

    public Demande addPieceJointe(PieceJointe pieceJointe) {
        this.pieceJointes.add(pieceJointe);
        pieceJointe.setDemande(this);
        return this;
    }

    public Demande removePieceJointe(PieceJointe pieceJointe) {
        this.pieceJointes.remove(pieceJointe);
        pieceJointe.setDemande(null);
        return this;
    }

    public Dossier getDossier() {
        return this.dossier;
    }

    public void setDossier(Dossier dossier) {
        this.dossier = dossier;
    }

    public Demande dossier(Dossier dossier) {
        this.setDossier(dossier);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Demande)) {
            return false;
        }
        return id != null && id.equals(((Demande) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Demande{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", niveauEtude='" + getNiveauEtude() + "'" +
            ", etatDemande='" + getEtatDemande() + "'" +
            "}";
    }
}
