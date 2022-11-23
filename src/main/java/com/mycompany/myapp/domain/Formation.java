package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.Admission;
import com.mycompany.myapp.domain.enumeration.DiplomeRequis;
import com.mycompany.myapp.domain.enumeration.NomDiplome;
import com.mycompany.myapp.domain.enumeration.Secteur;
import com.mycompany.myapp.domain.enumeration.TypeFormation;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Formation.
 */
@Entity
@Table(name = "formation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Formation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom_formation")
    private String nomFormation;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_formation")
    private TypeFormation typeFormation;

    @Column(name = "duree")
    private String duree;

    @Enumerated(EnumType.STRING)
    @Column(name = "admission")
    private Admission admission;

    @Enumerated(EnumType.STRING)
    @Column(name = "diplome_requis")
    private DiplomeRequis diplomeRequis;

    @Column(name = "autre_diplome")
    private String autreDiplome;

    @Enumerated(EnumType.STRING)
    @Column(name = "secteur")
    private Secteur secteur;

    @Column(name = "autre_secteur")
    private String autreSecteur;

    @Lob
    @Column(name = "fiche_formation")
    private byte[] ficheFormation;

    @Column(name = "fiche_formation_content_type")
    private String ficheFormationContentType;

    @Lob
    @Column(name = "programmes")
    private String programmes;

    @Column(name = "nom_concours")
    private String nomConcours;

    @Column(name = "date_ouverture")
    private LocalDate dateOuverture;

    @Column(name = "date_cloture")
    private LocalDate dateCloture;

    @Column(name = "date_concours")
    private LocalDate dateConcours;

    @Column(name = "libelle_pc")
    private String libellePC;

    @Column(name = "montant_prise_en_charge")
    private Double montantPriseEnCharge;

    @Lob
    @Column(name = "detail_pc")
    private String detailPC;

    @Enumerated(EnumType.STRING)
    @Column(name = "nom_diplome")
    private NomDiplome nomDiplome;

    @Column(name = "nom_debouche")
    private String nomDebouche;

    @OneToMany(mappedBy = "formation")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "bailleur", "formation" }, allowSetters = true)
    private Set<PriseEnCharge> priseEnCharges = new HashSet<>();

    @OneToMany(mappedBy = "formation")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "formation" }, allowSetters = true)
    private Set<Concours> concours = new HashSet<>();

    @OneToMany(mappedBy = "formation")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "formation", "etablissement" }, allowSetters = true)
    private Set<SessionForm> sessionForms = new HashSet<>();

    @JsonIgnoreProperties(value = { "formation", "pieceJointes", "dossier" }, allowSetters = true)
    @OneToOne(mappedBy = "formation")
    private Demande demande;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Formation id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomFormation() {
        return this.nomFormation;
    }

    public Formation nomFormation(String nomFormation) {
        this.setNomFormation(nomFormation);
        return this;
    }

    public void setNomFormation(String nomFormation) {
        this.nomFormation = nomFormation;
    }

    public TypeFormation getTypeFormation() {
        return this.typeFormation;
    }

    public Formation typeFormation(TypeFormation typeFormation) {
        this.setTypeFormation(typeFormation);
        return this;
    }

    public void setTypeFormation(TypeFormation typeFormation) {
        this.typeFormation = typeFormation;
    }

    public String getDuree() {
        return this.duree;
    }

    public Formation duree(String duree) {
        this.setDuree(duree);
        return this;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public Admission getAdmission() {
        return this.admission;
    }

    public Formation admission(Admission admission) {
        this.setAdmission(admission);
        return this;
    }

    public void setAdmission(Admission admission) {
        this.admission = admission;
    }

    public DiplomeRequis getDiplomeRequis() {
        return this.diplomeRequis;
    }

    public Formation diplomeRequis(DiplomeRequis diplomeRequis) {
        this.setDiplomeRequis(diplomeRequis);
        return this;
    }

    public void setDiplomeRequis(DiplomeRequis diplomeRequis) {
        this.diplomeRequis = diplomeRequis;
    }

    public String getAutreDiplome() {
        return this.autreDiplome;
    }

    public Formation autreDiplome(String autreDiplome) {
        this.setAutreDiplome(autreDiplome);
        return this;
    }

    public void setAutreDiplome(String autreDiplome) {
        this.autreDiplome = autreDiplome;
    }

    public Secteur getSecteur() {
        return this.secteur;
    }

    public Formation secteur(Secteur secteur) {
        this.setSecteur(secteur);
        return this;
    }

    public void setSecteur(Secteur secteur) {
        this.secteur = secteur;
    }

    public String getAutreSecteur() {
        return this.autreSecteur;
    }

    public Formation autreSecteur(String autreSecteur) {
        this.setAutreSecteur(autreSecteur);
        return this;
    }

    public void setAutreSecteur(String autreSecteur) {
        this.autreSecteur = autreSecteur;
    }

    public byte[] getFicheFormation() {
        return this.ficheFormation;
    }

    public Formation ficheFormation(byte[] ficheFormation) {
        this.setFicheFormation(ficheFormation);
        return this;
    }

    public void setFicheFormation(byte[] ficheFormation) {
        this.ficheFormation = ficheFormation;
    }

    public String getFicheFormationContentType() {
        return this.ficheFormationContentType;
    }

    public Formation ficheFormationContentType(String ficheFormationContentType) {
        this.ficheFormationContentType = ficheFormationContentType;
        return this;
    }

    public void setFicheFormationContentType(String ficheFormationContentType) {
        this.ficheFormationContentType = ficheFormationContentType;
    }

    public String getProgrammes() {
        return this.programmes;
    }

    public Formation programmes(String programmes) {
        this.setProgrammes(programmes);
        return this;
    }

    public void setProgrammes(String programmes) {
        this.programmes = programmes;
    }

    public String getNomConcours() {
        return this.nomConcours;
    }

    public Formation nomConcours(String nomConcours) {
        this.setNomConcours(nomConcours);
        return this;
    }

    public void setNomConcours(String nomConcours) {
        this.nomConcours = nomConcours;
    }

    public LocalDate getDateOuverture() {
        return this.dateOuverture;
    }

    public Formation dateOuverture(LocalDate dateOuverture) {
        this.setDateOuverture(dateOuverture);
        return this;
    }

    public void setDateOuverture(LocalDate dateOuverture) {
        this.dateOuverture = dateOuverture;
    }

    public LocalDate getDateCloture() {
        return this.dateCloture;
    }

    public Formation dateCloture(LocalDate dateCloture) {
        this.setDateCloture(dateCloture);
        return this;
    }

    public void setDateCloture(LocalDate dateCloture) {
        this.dateCloture = dateCloture;
    }

    public LocalDate getDateConcours() {
        return this.dateConcours;
    }

    public Formation dateConcours(LocalDate dateConcours) {
        this.setDateConcours(dateConcours);
        return this;
    }

    public void setDateConcours(LocalDate dateConcours) {
        this.dateConcours = dateConcours;
    }

    public String getLibellePC() {
        return this.libellePC;
    }

    public Formation libellePC(String libellePC) {
        this.setLibellePC(libellePC);
        return this;
    }

    public void setLibellePC(String libellePC) {
        this.libellePC = libellePC;
    }

    public Double getMontantPriseEnCharge() {
        return this.montantPriseEnCharge;
    }

    public Formation montantPriseEnCharge(Double montantPriseEnCharge) {
        this.setMontantPriseEnCharge(montantPriseEnCharge);
        return this;
    }

    public void setMontantPriseEnCharge(Double montantPriseEnCharge) {
        this.montantPriseEnCharge = montantPriseEnCharge;
    }

    public String getDetailPC() {
        return this.detailPC;
    }

    public Formation detailPC(String detailPC) {
        this.setDetailPC(detailPC);
        return this;
    }

    public void setDetailPC(String detailPC) {
        this.detailPC = detailPC;
    }

    public NomDiplome getNomDiplome() {
        return this.nomDiplome;
    }

    public Formation nomDiplome(NomDiplome nomDiplome) {
        this.setNomDiplome(nomDiplome);
        return this;
    }

    public void setNomDiplome(NomDiplome nomDiplome) {
        this.nomDiplome = nomDiplome;
    }

    public String getNomDebouche() {
        return this.nomDebouche;
    }

    public Formation nomDebouche(String nomDebouche) {
        this.setNomDebouche(nomDebouche);
        return this;
    }

    public void setNomDebouche(String nomDebouche) {
        this.nomDebouche = nomDebouche;
    }

    public Set<PriseEnCharge> getPriseEnCharges() {
        return this.priseEnCharges;
    }

    public void setPriseEnCharges(Set<PriseEnCharge> priseEnCharges) {
        if (this.priseEnCharges != null) {
            this.priseEnCharges.forEach(i -> i.setFormation(null));
        }
        if (priseEnCharges != null) {
            priseEnCharges.forEach(i -> i.setFormation(this));
        }
        this.priseEnCharges = priseEnCharges;
    }

    public Formation priseEnCharges(Set<PriseEnCharge> priseEnCharges) {
        this.setPriseEnCharges(priseEnCharges);
        return this;
    }

    public Formation addPriseEnCharge(PriseEnCharge priseEnCharge) {
        this.priseEnCharges.add(priseEnCharge);
        priseEnCharge.setFormation(this);
        return this;
    }

    public Formation removePriseEnCharge(PriseEnCharge priseEnCharge) {
        this.priseEnCharges.remove(priseEnCharge);
        priseEnCharge.setFormation(null);
        return this;
    }

    public Set<Concours> getConcours() {
        return this.concours;
    }

    public void setConcours(Set<Concours> concours) {
        if (this.concours != null) {
            this.concours.forEach(i -> i.setFormation(null));
        }
        if (concours != null) {
            concours.forEach(i -> i.setFormation(this));
        }
        this.concours = concours;
    }

    public Formation concours(Set<Concours> concours) {
        this.setConcours(concours);
        return this;
    }

    public Formation addConcours(Concours concours) {
        this.concours.add(concours);
        concours.setFormation(this);
        return this;
    }

    public Formation removeConcours(Concours concours) {
        this.concours.remove(concours);
        concours.setFormation(null);
        return this;
    }

    public Set<SessionForm> getSessionForms() {
        return this.sessionForms;
    }

    public void setSessionForms(Set<SessionForm> sessionForms) {
        if (this.sessionForms != null) {
            this.sessionForms.forEach(i -> i.setFormation(null));
        }
        if (sessionForms != null) {
            sessionForms.forEach(i -> i.setFormation(this));
        }
        this.sessionForms = sessionForms;
    }

    public Formation sessionForms(Set<SessionForm> sessionForms) {
        this.setSessionForms(sessionForms);
        return this;
    }

    public Formation addSessionForm(SessionForm sessionForm) {
        this.sessionForms.add(sessionForm);
        sessionForm.setFormation(this);
        return this;
    }

    public Formation removeSessionForm(SessionForm sessionForm) {
        this.sessionForms.remove(sessionForm);
        sessionForm.setFormation(null);
        return this;
    }

    public Demande getDemande() {
        return this.demande;
    }

    public void setDemande(Demande demande) {
        if (this.demande != null) {
            this.demande.setFormation(null);
        }
        if (demande != null) {
            demande.setFormation(this);
        }
        this.demande = demande;
    }

    public Formation demande(Demande demande) {
        this.setDemande(demande);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Formation)) {
            return false;
        }
        return id != null && id.equals(((Formation) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Formation{" +
            "id=" + getId() +
            ", nomFormation='" + getNomFormation() + "'" +
            ", typeFormation='" + getTypeFormation() + "'" +
            ", duree='" + getDuree() + "'" +
            ", admission='" + getAdmission() + "'" +
            ", diplomeRequis='" + getDiplomeRequis() + "'" +
            ", autreDiplome='" + getAutreDiplome() + "'" +
            ", secteur='" + getSecteur() + "'" +
            ", autreSecteur='" + getAutreSecteur() + "'" +
            ", ficheFormation='" + getFicheFormation() + "'" +
            ", ficheFormationContentType='" + getFicheFormationContentType() + "'" +
            ", programmes='" + getProgrammes() + "'" +
            ", nomConcours='" + getNomConcours() + "'" +
            ", dateOuverture='" + getDateOuverture() + "'" +
            ", dateCloture='" + getDateCloture() + "'" +
            ", dateConcours='" + getDateConcours() + "'" +
            ", libellePC='" + getLibellePC() + "'" +
            ", montantPriseEnCharge=" + getMontantPriseEnCharge() +
            ", detailPC='" + getDetailPC() + "'" +
            ", nomDiplome='" + getNomDiplome() + "'" +
            ", nomDebouche='" + getNomDebouche() + "'" +
            "}";
    }
}
