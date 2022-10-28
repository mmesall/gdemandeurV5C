package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.TypeEtablissement;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Etablissement.
 */
@Entity
@Table(name = "etablissement")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Etablissement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom_etablissement")
    private String nomEtablissement;

    @Lob
    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "photo_content_type")
    private String photoContentType;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "email")
    private String email;

    @Column(name = "telephone")
    private Long telephone;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_etablissement")
    private TypeEtablissement typeEtablissement;

    @OneToMany(mappedBy = "etablissement")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "etablissement" }, allowSetters = true)
    private Set<Filiere> filieres = new HashSet<>();

    @OneToMany(mappedBy = "etablissement")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "etablissement" }, allowSetters = true)
    private Set<Serie> series = new HashSet<>();

    @OneToMany(mappedBy = "etablissement")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "formation", "etablissement" }, allowSetters = true)
    private Set<SessionForm> sessionForms = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "etablissements", "eleves", "etudiants", "professionnels", "agents" }, allowSetters = true)
    private Localite localite;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Etablissement id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomEtablissement() {
        return this.nomEtablissement;
    }

    public Etablissement nomEtablissement(String nomEtablissement) {
        this.setNomEtablissement(nomEtablissement);
        return this;
    }

    public void setNomEtablissement(String nomEtablissement) {
        this.nomEtablissement = nomEtablissement;
    }

    public byte[] getPhoto() {
        return this.photo;
    }

    public Etablissement photo(byte[] photo) {
        this.setPhoto(photo);
        return this;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return this.photoContentType;
    }

    public Etablissement photoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
        return this;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public Etablissement adresse(String adresse) {
        this.setAdresse(adresse);
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getEmail() {
        return this.email;
    }

    public Etablissement email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getTelephone() {
        return this.telephone;
    }

    public Etablissement telephone(Long telephone) {
        this.setTelephone(telephone);
        return this;
    }

    public void setTelephone(Long telephone) {
        this.telephone = telephone;
    }

    public TypeEtablissement getTypeEtablissement() {
        return this.typeEtablissement;
    }

    public Etablissement typeEtablissement(TypeEtablissement typeEtablissement) {
        this.setTypeEtablissement(typeEtablissement);
        return this;
    }

    public void setTypeEtablissement(TypeEtablissement typeEtablissement) {
        this.typeEtablissement = typeEtablissement;
    }

    public Set<Filiere> getFilieres() {
        return this.filieres;
    }

    public void setFilieres(Set<Filiere> filieres) {
        if (this.filieres != null) {
            this.filieres.forEach(i -> i.setEtablissement(null));
        }
        if (filieres != null) {
            filieres.forEach(i -> i.setEtablissement(this));
        }
        this.filieres = filieres;
    }

    public Etablissement filieres(Set<Filiere> filieres) {
        this.setFilieres(filieres);
        return this;
    }

    public Etablissement addFiliere(Filiere filiere) {
        this.filieres.add(filiere);
        filiere.setEtablissement(this);
        return this;
    }

    public Etablissement removeFiliere(Filiere filiere) {
        this.filieres.remove(filiere);
        filiere.setEtablissement(null);
        return this;
    }

    public Set<Serie> getSeries() {
        return this.series;
    }

    public void setSeries(Set<Serie> series) {
        if (this.series != null) {
            this.series.forEach(i -> i.setEtablissement(null));
        }
        if (series != null) {
            series.forEach(i -> i.setEtablissement(this));
        }
        this.series = series;
    }

    public Etablissement series(Set<Serie> series) {
        this.setSeries(series);
        return this;
    }

    public Etablissement addSerie(Serie serie) {
        this.series.add(serie);
        serie.setEtablissement(this);
        return this;
    }

    public Etablissement removeSerie(Serie serie) {
        this.series.remove(serie);
        serie.setEtablissement(null);
        return this;
    }

    public Set<SessionForm> getSessionForms() {
        return this.sessionForms;
    }

    public void setSessionForms(Set<SessionForm> sessionForms) {
        if (this.sessionForms != null) {
            this.sessionForms.forEach(i -> i.setEtablissement(null));
        }
        if (sessionForms != null) {
            sessionForms.forEach(i -> i.setEtablissement(this));
        }
        this.sessionForms = sessionForms;
    }

    public Etablissement sessionForms(Set<SessionForm> sessionForms) {
        this.setSessionForms(sessionForms);
        return this;
    }

    public Etablissement addSessionForm(SessionForm sessionForm) {
        this.sessionForms.add(sessionForm);
        sessionForm.setEtablissement(this);
        return this;
    }

    public Etablissement removeSessionForm(SessionForm sessionForm) {
        this.sessionForms.remove(sessionForm);
        sessionForm.setEtablissement(null);
        return this;
    }

    public Localite getLocalite() {
        return this.localite;
    }

    public void setLocalite(Localite localite) {
        this.localite = localite;
    }

    public Etablissement localite(Localite localite) {
        this.setLocalite(localite);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Etablissement)) {
            return false;
        }
        return id != null && id.equals(((Etablissement) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Etablissement{" +
            "id=" + getId() +
            ", nomEtablissement='" + getNomEtablissement() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", photoContentType='" + getPhotoContentType() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", email='" + getEmail() + "'" +
            ", telephone=" + getTelephone() +
            ", typeEtablissement='" + getTypeEtablissement() + "'" +
            "}";
    }
}
