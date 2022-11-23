package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.NiveauEtude;
import com.mycompany.myapp.domain.enumeration.NomFiliere;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Filiere.
 */
@Entity
@Table(name = "filiere")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Filiere implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "nom_filiere")
    private NomFiliere nomFiliere;

    @Enumerated(EnumType.STRING)
    @Column(name = "niveau_etude")
    private NiveauEtude niveauEtude;

    @Lob
    @Column(name = "programme")
    private String programme;

    @Column(name = "autre_niveau_etude")
    private String autreNiveauEtude;

    @Column(name = "autre_filiere")
    private String autreFiliere;

    @ManyToOne
    @JsonIgnoreProperties(value = { "filieres", "series", "sessionForms", "localite" }, allowSetters = true)
    private Etablissement etablissement;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Filiere id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NomFiliere getNomFiliere() {
        return this.nomFiliere;
    }

    public Filiere nomFiliere(NomFiliere nomFiliere) {
        this.setNomFiliere(nomFiliere);
        return this;
    }

    public void setNomFiliere(NomFiliere nomFiliere) {
        this.nomFiliere = nomFiliere;
    }

    public NiveauEtude getNiveauEtude() {
        return this.niveauEtude;
    }

    public Filiere niveauEtude(NiveauEtude niveauEtude) {
        this.setNiveauEtude(niveauEtude);
        return this;
    }

    public void setNiveauEtude(NiveauEtude niveauEtude) {
        this.niveauEtude = niveauEtude;
    }

    public String getProgramme() {
        return this.programme;
    }

    public Filiere programme(String programme) {
        this.setProgramme(programme);
        return this;
    }

    public void setProgramme(String programme) {
        this.programme = programme;
    }

    public String getAutreNiveauEtude() {
        return this.autreNiveauEtude;
    }

    public Filiere autreNiveauEtude(String autreNiveauEtude) {
        this.setAutreNiveauEtude(autreNiveauEtude);
        return this;
    }

    public void setAutreNiveauEtude(String autreNiveauEtude) {
        this.autreNiveauEtude = autreNiveauEtude;
    }

    public String getAutreFiliere() {
        return this.autreFiliere;
    }

    public Filiere autreFiliere(String autreFiliere) {
        this.setAutreFiliere(autreFiliere);
        return this;
    }

    public void setAutreFiliere(String autreFiliere) {
        this.autreFiliere = autreFiliere;
    }

    public Etablissement getEtablissement() {
        return this.etablissement;
    }

    public void setEtablissement(Etablissement etablissement) {
        this.etablissement = etablissement;
    }

    public Filiere etablissement(Etablissement etablissement) {
        this.setEtablissement(etablissement);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Filiere)) {
            return false;
        }
        return id != null && id.equals(((Filiere) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Filiere{" +
            "id=" + getId() +
            ", nomFiliere='" + getNomFiliere() + "'" +
            ", niveauEtude='" + getNiveauEtude() + "'" +
            ", programme='" + getProgramme() + "'" +
            ", autreNiveauEtude='" + getAutreNiveauEtude() + "'" +
            ", autreFiliere='" + getAutreFiliere() + "'" +
            "}";
    }
}
