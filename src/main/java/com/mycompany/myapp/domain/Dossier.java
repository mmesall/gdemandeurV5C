package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.DiplomeRequis;
import com.mycompany.myapp.domain.enumeration.NiveauEtude;
import com.mycompany.myapp.domain.enumeration.NomRegion;
import com.mycompany.myapp.domain.enumeration.TypeDemandeur;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Dossier.
 */
@Entity
@Table(name = "dossier")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Dossier implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "num_dossier")
    private String numDossier;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_demandeur")
    private TypeDemandeur typeDemandeur;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "adresse")
    private String adresse;

    @Enumerated(EnumType.STRING)
    @Column(name = "region")
    private NomRegion region;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "email")
    private String email;

    @Lob
    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "photo_content_type")
    private String photoContentType;

    @Lob
    @Column(name = "cv")
    private byte[] cv;

    @Column(name = "cv_content_type")
    private String cvContentType;

    @Lob
    @Column(name = "lettre_motivation")
    private String lettreMotivation;

    @Enumerated(EnumType.STRING)
    @Column(name = "diplome_requis")
    private DiplomeRequis diplomeRequis;

    @Enumerated(EnumType.STRING)
    @Column(name = "niveau_etude")
    private NiveauEtude niveauEtude;

    @Column(name = "profession")
    private String profession;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(mappedBy = "dossier")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "formation", "pieceJointes", "dossier" }, allowSetters = true)
    private Set<Demande> demandes = new HashSet<>();

    @JsonIgnoreProperties(value = { "user", "dossier", "demandeur", "localite" }, allowSetters = true)
    @OneToOne(mappedBy = "dossier")
    private Eleve eleve;

    @JsonIgnoreProperties(value = { "user", "dossier", "demandeur", "localite" }, allowSetters = true)
    @OneToOne(mappedBy = "dossier")
    private Etudiant etudiant;

    @JsonIgnoreProperties(value = { "user", "dossier", "demandeur", "localite" }, allowSetters = true)
    @OneToOne(mappedBy = "dossier")
    private Professionnel professionnel;

    @JsonIgnoreProperties(value = { "dossier", "user", "eleve", "etudiant", "professionnel" }, allowSetters = true)
    @OneToOne(mappedBy = "dossier")
    private Demandeur demandeur;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Dossier id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumDossier() {
        return this.numDossier;
    }

    public Dossier numDossier(String numDossier) {
        this.setNumDossier(numDossier);
        return this;
    }

    public void setNumDossier(String numDossier) {
        this.numDossier = numDossier;
    }

    public TypeDemandeur getTypeDemandeur() {
        return this.typeDemandeur;
    }

    public Dossier typeDemandeur(TypeDemandeur typeDemandeur) {
        this.setTypeDemandeur(typeDemandeur);
        return this;
    }

    public void setTypeDemandeur(TypeDemandeur typeDemandeur) {
        this.typeDemandeur = typeDemandeur;
    }

    public String getNom() {
        return this.nom;
    }

    public Dossier nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public Dossier prenom(String prenom) {
        this.setPrenom(prenom);
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public Dossier adresse(String adresse) {
        this.setAdresse(adresse);
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public NomRegion getRegion() {
        return this.region;
    }

    public Dossier region(NomRegion region) {
        this.setRegion(region);
        return this;
    }

    public void setRegion(NomRegion region) {
        this.region = region;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public Dossier telephone(String telephone) {
        this.setTelephone(telephone);
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return this.email;
    }

    public Dossier email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getPhoto() {
        return this.photo;
    }

    public Dossier photo(byte[] photo) {
        this.setPhoto(photo);
        return this;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return this.photoContentType;
    }

    public Dossier photoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
        return this;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public byte[] getCv() {
        return this.cv;
    }

    public Dossier cv(byte[] cv) {
        this.setCv(cv);
        return this;
    }

    public void setCv(byte[] cv) {
        this.cv = cv;
    }

    public String getCvContentType() {
        return this.cvContentType;
    }

    public Dossier cvContentType(String cvContentType) {
        this.cvContentType = cvContentType;
        return this;
    }

    public void setCvContentType(String cvContentType) {
        this.cvContentType = cvContentType;
    }

    public String getLettreMotivation() {
        return this.lettreMotivation;
    }

    public Dossier lettreMotivation(String lettreMotivation) {
        this.setLettreMotivation(lettreMotivation);
        return this;
    }

    public void setLettreMotivation(String lettreMotivation) {
        this.lettreMotivation = lettreMotivation;
    }

    public DiplomeRequis getDiplomeRequis() {
        return this.diplomeRequis;
    }

    public Dossier diplomeRequis(DiplomeRequis diplomeRequis) {
        this.setDiplomeRequis(diplomeRequis);
        return this;
    }

    public void setDiplomeRequis(DiplomeRequis diplomeRequis) {
        this.diplomeRequis = diplomeRequis;
    }

    public NiveauEtude getNiveauEtude() {
        return this.niveauEtude;
    }

    public Dossier niveauEtude(NiveauEtude niveauEtude) {
        this.setNiveauEtude(niveauEtude);
        return this;
    }

    public void setNiveauEtude(NiveauEtude niveauEtude) {
        this.niveauEtude = niveauEtude;
    }

    public String getProfession() {
        return this.profession;
    }

    public Dossier profession(String profession) {
        this.setProfession(profession);
        return this;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Dossier user(User user) {
        this.setUser(user);
        return this;
    }

    public Set<Demande> getDemandes() {
        return this.demandes;
    }

    public void setDemandes(Set<Demande> demandes) {
        if (this.demandes != null) {
            this.demandes.forEach(i -> i.setDossier(null));
        }
        if (demandes != null) {
            demandes.forEach(i -> i.setDossier(this));
        }
        this.demandes = demandes;
    }

    public Dossier demandes(Set<Demande> demandes) {
        this.setDemandes(demandes);
        return this;
    }

    public Dossier addDemande(Demande demande) {
        this.demandes.add(demande);
        demande.setDossier(this);
        return this;
    }

    public Dossier removeDemande(Demande demande) {
        this.demandes.remove(demande);
        demande.setDossier(null);
        return this;
    }

    public Eleve getEleve() {
        return this.eleve;
    }

    public void setEleve(Eleve eleve) {
        if (this.eleve != null) {
            this.eleve.setDossier(null);
        }
        if (eleve != null) {
            eleve.setDossier(this);
        }
        this.eleve = eleve;
    }

    public Dossier eleve(Eleve eleve) {
        this.setEleve(eleve);
        return this;
    }

    public Etudiant getEtudiant() {
        return this.etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        if (this.etudiant != null) {
            this.etudiant.setDossier(null);
        }
        if (etudiant != null) {
            etudiant.setDossier(this);
        }
        this.etudiant = etudiant;
    }

    public Dossier etudiant(Etudiant etudiant) {
        this.setEtudiant(etudiant);
        return this;
    }

    public Professionnel getProfessionnel() {
        return this.professionnel;
    }

    public void setProfessionnel(Professionnel professionnel) {
        if (this.professionnel != null) {
            this.professionnel.setDossier(null);
        }
        if (professionnel != null) {
            professionnel.setDossier(this);
        }
        this.professionnel = professionnel;
    }

    public Dossier professionnel(Professionnel professionnel) {
        this.setProfessionnel(professionnel);
        return this;
    }

    public Demandeur getDemandeur() {
        return this.demandeur;
    }

    public void setDemandeur(Demandeur demandeur) {
        if (this.demandeur != null) {
            this.demandeur.setDossier(null);
        }
        if (demandeur != null) {
            demandeur.setDossier(this);
        }
        this.demandeur = demandeur;
    }

    public Dossier demandeur(Demandeur demandeur) {
        this.setDemandeur(demandeur);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Dossier)) {
            return false;
        }
        return id != null && id.equals(((Dossier) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Dossier{" +
            "id=" + getId() +
            ", numDossier='" + getNumDossier() + "'" +
            ", typeDemandeur='" + getTypeDemandeur() + "'" +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", region='" + getRegion() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", email='" + getEmail() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", photoContentType='" + getPhotoContentType() + "'" +
            ", cv='" + getCv() + "'" +
            ", cvContentType='" + getCvContentType() + "'" +
            ", lettreMotivation='" + getLettreMotivation() + "'" +
            ", diplomeRequis='" + getDiplomeRequis() + "'" +
            ", niveauEtude='" + getNiveauEtude() + "'" +
            ", profession='" + getProfession() + "'" +
            "}";
    }
}
