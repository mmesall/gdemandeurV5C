package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.NiveauEtude;
import com.mycompany.myapp.domain.enumeration.NomSerie;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Serie.
 */
@Entity
@Table(name = "serie")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Serie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "nom_serie")
    private NomSerie nomSerie;

    @Enumerated(EnumType.STRING)
    @Column(name = "niveau_etude")
    private NiveauEtude niveauEtude;

    @Lob
    @Column(name = "programme")
    private String programme;

    @Column(name = "autre_serie")
    private String autreSerie;

    @ManyToOne
    @JsonIgnoreProperties(value = { "filieres", "series", "sessionForms", "localite" }, allowSetters = true)
    private Etablissement etablissement;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Serie id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NomSerie getNomSerie() {
        return this.nomSerie;
    }

    public Serie nomSerie(NomSerie nomSerie) {
        this.setNomSerie(nomSerie);
        return this;
    }

    public void setNomSerie(NomSerie nomSerie) {
        this.nomSerie = nomSerie;
    }

    public NiveauEtude getNiveauEtude() {
        return this.niveauEtude;
    }

    public Serie niveauEtude(NiveauEtude niveauEtude) {
        this.setNiveauEtude(niveauEtude);
        return this;
    }

    public void setNiveauEtude(NiveauEtude niveauEtude) {
        this.niveauEtude = niveauEtude;
    }

    public String getProgramme() {
        return this.programme;
    }

    public Serie programme(String programme) {
        this.setProgramme(programme);
        return this;
    }

    public void setProgramme(String programme) {
        this.programme = programme;
    }

    public String getAutreSerie() {
        return this.autreSerie;
    }

    public Serie autreSerie(String autreSerie) {
        this.setAutreSerie(autreSerie);
        return this;
    }

    public void setAutreSerie(String autreSerie) {
        this.autreSerie = autreSerie;
    }

    public Etablissement getEtablissement() {
        return this.etablissement;
    }

    public void setEtablissement(Etablissement etablissement) {
        this.etablissement = etablissement;
    }

    public Serie etablissement(Etablissement etablissement) {
        this.setEtablissement(etablissement);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Serie)) {
            return false;
        }
        return id != null && id.equals(((Serie) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Serie{" +
            "id=" + getId() +
            ", nomSerie='" + getNomSerie() + "'" +
            ", niveauEtude='" + getNiveauEtude() + "'" +
            ", programme='" + getProgramme() + "'" +
            ", autreSerie='" + getAutreSerie() + "'" +
            "}";
    }
}
