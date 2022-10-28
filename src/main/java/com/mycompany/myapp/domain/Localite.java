package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.DAKAR;
import com.mycompany.myapp.domain.enumeration.DIOURBEL;
import com.mycompany.myapp.domain.enumeration.FATICK;
import com.mycompany.myapp.domain.enumeration.KAFFRINE;
import com.mycompany.myapp.domain.enumeration.KAOLACK;
import com.mycompany.myapp.domain.enumeration.KEDOUGOU;
import com.mycompany.myapp.domain.enumeration.KOLDA;
import com.mycompany.myapp.domain.enumeration.LOUGA;
import com.mycompany.myapp.domain.enumeration.MATAM;
import com.mycompany.myapp.domain.enumeration.NomRegion;
import com.mycompany.myapp.domain.enumeration.SAINTLOUIS;
import com.mycompany.myapp.domain.enumeration.SEDHIOU;
import com.mycompany.myapp.domain.enumeration.TAMBACOUNDA;
import com.mycompany.myapp.domain.enumeration.THIES;
import com.mycompany.myapp.domain.enumeration.ZIGUINCHOR;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Localite.
 */
@Entity
@Table(name = "localite")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Localite implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "region", nullable = false)
    private NomRegion region;

    @Column(name = "autre_region")
    private String autreRegion;

    @Enumerated(EnumType.STRING)
    @Column(name = "departement_dakar")
    private DAKAR departementDakar;

    @Enumerated(EnumType.STRING)
    @Column(name = "departement_diourbel")
    private DIOURBEL departementDiourbel;

    @Enumerated(EnumType.STRING)
    @Column(name = "departement_fatick")
    private FATICK departementFatick;

    @Enumerated(EnumType.STRING)
    @Column(name = "departement_kaffrine")
    private KAFFRINE departementKaffrine;

    @Enumerated(EnumType.STRING)
    @Column(name = "departement_kaolack")
    private KAOLACK departementKaolack;

    @Enumerated(EnumType.STRING)
    @Column(name = "departement_kedougou")
    private KEDOUGOU departementKedougou;

    @Enumerated(EnumType.STRING)
    @Column(name = "departement_kolda")
    private KOLDA departementKolda;

    @Enumerated(EnumType.STRING)
    @Column(name = "departement_louga")
    private LOUGA departementLouga;

    @Enumerated(EnumType.STRING)
    @Column(name = "departement_matam")
    private MATAM departementMatam;

    @Enumerated(EnumType.STRING)
    @Column(name = "departement_saint")
    private SAINTLOUIS departementSaint;

    @Enumerated(EnumType.STRING)
    @Column(name = "departement_sedhiou")
    private SEDHIOU departementSedhiou;

    @Enumerated(EnumType.STRING)
    @Column(name = "departement_tambacounda")
    private TAMBACOUNDA departementTambacounda;

    @Enumerated(EnumType.STRING)
    @Column(name = "departement_this")
    private THIES departementThis;

    @Enumerated(EnumType.STRING)
    @Column(name = "departement_ziguinchor")
    private ZIGUINCHOR departementZiguinchor;

    @Column(name = "autredepartement_dakar")
    private String autredepartementDakar;

    @Column(name = "autredepartement_diourbel")
    private String autredepartementDiourbel;

    @Column(name = "autredepartement_fatick")
    private String autredepartementFatick;

    @Column(name = "autredepartement_kaffrine")
    private String autredepartementKaffrine;

    @Column(name = "autredepartement_kaolack")
    private String autredepartementKaolack;

    @Column(name = "autredepartement_kedougou")
    private String autredepartementKedougou;

    @Column(name = "autredepartement_kolda")
    private String autredepartementKolda;

    @Column(name = "autredepartement_louga")
    private String autredepartementLouga;

    @Column(name = "autredepartement_matam")
    private String autredepartementMatam;

    @Column(name = "autredepartement_saint")
    private String autredepartementSaint;

    @Column(name = "autredepartement_sedhiou")
    private String autredepartementSedhiou;

    @Column(name = "autredepartement_tambacounda")
    private String autredepartementTambacounda;

    @Column(name = "autredepartement_this")
    private String autredepartementThis;

    @Column(name = "autredepartement_ziguinchor")
    private String autredepartementZiguinchor;

    @NotNull
    @Column(name = "commune", nullable = false)
    private String commune;

    @Column(name = "nom_quartier")
    private String nomQuartier;

    @OneToMany(mappedBy = "localite")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "filieres", "series", "sessionForms", "localite" }, allowSetters = true)
    private Set<Etablissement> etablissements = new HashSet<>();

    @OneToMany(mappedBy = "localite")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "user", "dossier", "demandeur", "localite" }, allowSetters = true)
    private Set<Eleve> eleves = new HashSet<>();

    @OneToMany(mappedBy = "localite")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "user", "dossier", "demandeur", "localite" }, allowSetters = true)
    private Set<Etudiant> etudiants = new HashSet<>();

    @OneToMany(mappedBy = "localite")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "user", "dossier", "demandeur", "localite" }, allowSetters = true)
    private Set<Professionnel> professionnels = new HashSet<>();

    @OneToMany(mappedBy = "localite")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "user", "bailleur", "membre", "services", "localite" }, allowSetters = true)
    private Set<Agent> agents = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Localite id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NomRegion getRegion() {
        return this.region;
    }

    public Localite region(NomRegion region) {
        this.setRegion(region);
        return this;
    }

    public void setRegion(NomRegion region) {
        this.region = region;
    }

    public String getAutreRegion() {
        return this.autreRegion;
    }

    public Localite autreRegion(String autreRegion) {
        this.setAutreRegion(autreRegion);
        return this;
    }

    public void setAutreRegion(String autreRegion) {
        this.autreRegion = autreRegion;
    }

    public DAKAR getDepartementDakar() {
        return this.departementDakar;
    }

    public Localite departementDakar(DAKAR departementDakar) {
        this.setDepartementDakar(departementDakar);
        return this;
    }

    public void setDepartementDakar(DAKAR departementDakar) {
        this.departementDakar = departementDakar;
    }

    public DIOURBEL getDepartementDiourbel() {
        return this.departementDiourbel;
    }

    public Localite departementDiourbel(DIOURBEL departementDiourbel) {
        this.setDepartementDiourbel(departementDiourbel);
        return this;
    }

    public void setDepartementDiourbel(DIOURBEL departementDiourbel) {
        this.departementDiourbel = departementDiourbel;
    }

    public FATICK getDepartementFatick() {
        return this.departementFatick;
    }

    public Localite departementFatick(FATICK departementFatick) {
        this.setDepartementFatick(departementFatick);
        return this;
    }

    public void setDepartementFatick(FATICK departementFatick) {
        this.departementFatick = departementFatick;
    }

    public KAFFRINE getDepartementKaffrine() {
        return this.departementKaffrine;
    }

    public Localite departementKaffrine(KAFFRINE departementKaffrine) {
        this.setDepartementKaffrine(departementKaffrine);
        return this;
    }

    public void setDepartementKaffrine(KAFFRINE departementKaffrine) {
        this.departementKaffrine = departementKaffrine;
    }

    public KAOLACK getDepartementKaolack() {
        return this.departementKaolack;
    }

    public Localite departementKaolack(KAOLACK departementKaolack) {
        this.setDepartementKaolack(departementKaolack);
        return this;
    }

    public void setDepartementKaolack(KAOLACK departementKaolack) {
        this.departementKaolack = departementKaolack;
    }

    public KEDOUGOU getDepartementKedougou() {
        return this.departementKedougou;
    }

    public Localite departementKedougou(KEDOUGOU departementKedougou) {
        this.setDepartementKedougou(departementKedougou);
        return this;
    }

    public void setDepartementKedougou(KEDOUGOU departementKedougou) {
        this.departementKedougou = departementKedougou;
    }

    public KOLDA getDepartementKolda() {
        return this.departementKolda;
    }

    public Localite departementKolda(KOLDA departementKolda) {
        this.setDepartementKolda(departementKolda);
        return this;
    }

    public void setDepartementKolda(KOLDA departementKolda) {
        this.departementKolda = departementKolda;
    }

    public LOUGA getDepartementLouga() {
        return this.departementLouga;
    }

    public Localite departementLouga(LOUGA departementLouga) {
        this.setDepartementLouga(departementLouga);
        return this;
    }

    public void setDepartementLouga(LOUGA departementLouga) {
        this.departementLouga = departementLouga;
    }

    public MATAM getDepartementMatam() {
        return this.departementMatam;
    }

    public Localite departementMatam(MATAM departementMatam) {
        this.setDepartementMatam(departementMatam);
        return this;
    }

    public void setDepartementMatam(MATAM departementMatam) {
        this.departementMatam = departementMatam;
    }

    public SAINTLOUIS getDepartementSaint() {
        return this.departementSaint;
    }

    public Localite departementSaint(SAINTLOUIS departementSaint) {
        this.setDepartementSaint(departementSaint);
        return this;
    }

    public void setDepartementSaint(SAINTLOUIS departementSaint) {
        this.departementSaint = departementSaint;
    }

    public SEDHIOU getDepartementSedhiou() {
        return this.departementSedhiou;
    }

    public Localite departementSedhiou(SEDHIOU departementSedhiou) {
        this.setDepartementSedhiou(departementSedhiou);
        return this;
    }

    public void setDepartementSedhiou(SEDHIOU departementSedhiou) {
        this.departementSedhiou = departementSedhiou;
    }

    public TAMBACOUNDA getDepartementTambacounda() {
        return this.departementTambacounda;
    }

    public Localite departementTambacounda(TAMBACOUNDA departementTambacounda) {
        this.setDepartementTambacounda(departementTambacounda);
        return this;
    }

    public void setDepartementTambacounda(TAMBACOUNDA departementTambacounda) {
        this.departementTambacounda = departementTambacounda;
    }

    public THIES getDepartementThis() {
        return this.departementThis;
    }

    public Localite departementThis(THIES departementThis) {
        this.setDepartementThis(departementThis);
        return this;
    }

    public void setDepartementThis(THIES departementThis) {
        this.departementThis = departementThis;
    }

    public ZIGUINCHOR getDepartementZiguinchor() {
        return this.departementZiguinchor;
    }

    public Localite departementZiguinchor(ZIGUINCHOR departementZiguinchor) {
        this.setDepartementZiguinchor(departementZiguinchor);
        return this;
    }

    public void setDepartementZiguinchor(ZIGUINCHOR departementZiguinchor) {
        this.departementZiguinchor = departementZiguinchor;
    }

    public String getAutredepartementDakar() {
        return this.autredepartementDakar;
    }

    public Localite autredepartementDakar(String autredepartementDakar) {
        this.setAutredepartementDakar(autredepartementDakar);
        return this;
    }

    public void setAutredepartementDakar(String autredepartementDakar) {
        this.autredepartementDakar = autredepartementDakar;
    }

    public String getAutredepartementDiourbel() {
        return this.autredepartementDiourbel;
    }

    public Localite autredepartementDiourbel(String autredepartementDiourbel) {
        this.setAutredepartementDiourbel(autredepartementDiourbel);
        return this;
    }

    public void setAutredepartementDiourbel(String autredepartementDiourbel) {
        this.autredepartementDiourbel = autredepartementDiourbel;
    }

    public String getAutredepartementFatick() {
        return this.autredepartementFatick;
    }

    public Localite autredepartementFatick(String autredepartementFatick) {
        this.setAutredepartementFatick(autredepartementFatick);
        return this;
    }

    public void setAutredepartementFatick(String autredepartementFatick) {
        this.autredepartementFatick = autredepartementFatick;
    }

    public String getAutredepartementKaffrine() {
        return this.autredepartementKaffrine;
    }

    public Localite autredepartementKaffrine(String autredepartementKaffrine) {
        this.setAutredepartementKaffrine(autredepartementKaffrine);
        return this;
    }

    public void setAutredepartementKaffrine(String autredepartementKaffrine) {
        this.autredepartementKaffrine = autredepartementKaffrine;
    }

    public String getAutredepartementKaolack() {
        return this.autredepartementKaolack;
    }

    public Localite autredepartementKaolack(String autredepartementKaolack) {
        this.setAutredepartementKaolack(autredepartementKaolack);
        return this;
    }

    public void setAutredepartementKaolack(String autredepartementKaolack) {
        this.autredepartementKaolack = autredepartementKaolack;
    }

    public String getAutredepartementKedougou() {
        return this.autredepartementKedougou;
    }

    public Localite autredepartementKedougou(String autredepartementKedougou) {
        this.setAutredepartementKedougou(autredepartementKedougou);
        return this;
    }

    public void setAutredepartementKedougou(String autredepartementKedougou) {
        this.autredepartementKedougou = autredepartementKedougou;
    }

    public String getAutredepartementKolda() {
        return this.autredepartementKolda;
    }

    public Localite autredepartementKolda(String autredepartementKolda) {
        this.setAutredepartementKolda(autredepartementKolda);
        return this;
    }

    public void setAutredepartementKolda(String autredepartementKolda) {
        this.autredepartementKolda = autredepartementKolda;
    }

    public String getAutredepartementLouga() {
        return this.autredepartementLouga;
    }

    public Localite autredepartementLouga(String autredepartementLouga) {
        this.setAutredepartementLouga(autredepartementLouga);
        return this;
    }

    public void setAutredepartementLouga(String autredepartementLouga) {
        this.autredepartementLouga = autredepartementLouga;
    }

    public String getAutredepartementMatam() {
        return this.autredepartementMatam;
    }

    public Localite autredepartementMatam(String autredepartementMatam) {
        this.setAutredepartementMatam(autredepartementMatam);
        return this;
    }

    public void setAutredepartementMatam(String autredepartementMatam) {
        this.autredepartementMatam = autredepartementMatam;
    }

    public String getAutredepartementSaint() {
        return this.autredepartementSaint;
    }

    public Localite autredepartementSaint(String autredepartementSaint) {
        this.setAutredepartementSaint(autredepartementSaint);
        return this;
    }

    public void setAutredepartementSaint(String autredepartementSaint) {
        this.autredepartementSaint = autredepartementSaint;
    }

    public String getAutredepartementSedhiou() {
        return this.autredepartementSedhiou;
    }

    public Localite autredepartementSedhiou(String autredepartementSedhiou) {
        this.setAutredepartementSedhiou(autredepartementSedhiou);
        return this;
    }

    public void setAutredepartementSedhiou(String autredepartementSedhiou) {
        this.autredepartementSedhiou = autredepartementSedhiou;
    }

    public String getAutredepartementTambacounda() {
        return this.autredepartementTambacounda;
    }

    public Localite autredepartementTambacounda(String autredepartementTambacounda) {
        this.setAutredepartementTambacounda(autredepartementTambacounda);
        return this;
    }

    public void setAutredepartementTambacounda(String autredepartementTambacounda) {
        this.autredepartementTambacounda = autredepartementTambacounda;
    }

    public String getAutredepartementThis() {
        return this.autredepartementThis;
    }

    public Localite autredepartementThis(String autredepartementThis) {
        this.setAutredepartementThis(autredepartementThis);
        return this;
    }

    public void setAutredepartementThis(String autredepartementThis) {
        this.autredepartementThis = autredepartementThis;
    }

    public String getAutredepartementZiguinchor() {
        return this.autredepartementZiguinchor;
    }

    public Localite autredepartementZiguinchor(String autredepartementZiguinchor) {
        this.setAutredepartementZiguinchor(autredepartementZiguinchor);
        return this;
    }

    public void setAutredepartementZiguinchor(String autredepartementZiguinchor) {
        this.autredepartementZiguinchor = autredepartementZiguinchor;
    }

    public String getCommune() {
        return this.commune;
    }

    public Localite commune(String commune) {
        this.setCommune(commune);
        return this;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }

    public String getNomQuartier() {
        return this.nomQuartier;
    }

    public Localite nomQuartier(String nomQuartier) {
        this.setNomQuartier(nomQuartier);
        return this;
    }

    public void setNomQuartier(String nomQuartier) {
        this.nomQuartier = nomQuartier;
    }

    public Set<Etablissement> getEtablissements() {
        return this.etablissements;
    }

    public void setEtablissements(Set<Etablissement> etablissements) {
        if (this.etablissements != null) {
            this.etablissements.forEach(i -> i.setLocalite(null));
        }
        if (etablissements != null) {
            etablissements.forEach(i -> i.setLocalite(this));
        }
        this.etablissements = etablissements;
    }

    public Localite etablissements(Set<Etablissement> etablissements) {
        this.setEtablissements(etablissements);
        return this;
    }

    public Localite addEtablissement(Etablissement etablissement) {
        this.etablissements.add(etablissement);
        etablissement.setLocalite(this);
        return this;
    }

    public Localite removeEtablissement(Etablissement etablissement) {
        this.etablissements.remove(etablissement);
        etablissement.setLocalite(null);
        return this;
    }

    public Set<Eleve> getEleves() {
        return this.eleves;
    }

    public void setEleves(Set<Eleve> eleves) {
        if (this.eleves != null) {
            this.eleves.forEach(i -> i.setLocalite(null));
        }
        if (eleves != null) {
            eleves.forEach(i -> i.setLocalite(this));
        }
        this.eleves = eleves;
    }

    public Localite eleves(Set<Eleve> eleves) {
        this.setEleves(eleves);
        return this;
    }

    public Localite addEleve(Eleve eleve) {
        this.eleves.add(eleve);
        eleve.setLocalite(this);
        return this;
    }

    public Localite removeEleve(Eleve eleve) {
        this.eleves.remove(eleve);
        eleve.setLocalite(null);
        return this;
    }

    public Set<Etudiant> getEtudiants() {
        return this.etudiants;
    }

    public void setEtudiants(Set<Etudiant> etudiants) {
        if (this.etudiants != null) {
            this.etudiants.forEach(i -> i.setLocalite(null));
        }
        if (etudiants != null) {
            etudiants.forEach(i -> i.setLocalite(this));
        }
        this.etudiants = etudiants;
    }

    public Localite etudiants(Set<Etudiant> etudiants) {
        this.setEtudiants(etudiants);
        return this;
    }

    public Localite addEtudiant(Etudiant etudiant) {
        this.etudiants.add(etudiant);
        etudiant.setLocalite(this);
        return this;
    }

    public Localite removeEtudiant(Etudiant etudiant) {
        this.etudiants.remove(etudiant);
        etudiant.setLocalite(null);
        return this;
    }

    public Set<Professionnel> getProfessionnels() {
        return this.professionnels;
    }

    public void setProfessionnels(Set<Professionnel> professionnels) {
        if (this.professionnels != null) {
            this.professionnels.forEach(i -> i.setLocalite(null));
        }
        if (professionnels != null) {
            professionnels.forEach(i -> i.setLocalite(this));
        }
        this.professionnels = professionnels;
    }

    public Localite professionnels(Set<Professionnel> professionnels) {
        this.setProfessionnels(professionnels);
        return this;
    }

    public Localite addProfessionnel(Professionnel professionnel) {
        this.professionnels.add(professionnel);
        professionnel.setLocalite(this);
        return this;
    }

    public Localite removeProfessionnel(Professionnel professionnel) {
        this.professionnels.remove(professionnel);
        professionnel.setLocalite(null);
        return this;
    }

    public Set<Agent> getAgents() {
        return this.agents;
    }

    public void setAgents(Set<Agent> agents) {
        if (this.agents != null) {
            this.agents.forEach(i -> i.setLocalite(null));
        }
        if (agents != null) {
            agents.forEach(i -> i.setLocalite(this));
        }
        this.agents = agents;
    }

    public Localite agents(Set<Agent> agents) {
        this.setAgents(agents);
        return this;
    }

    public Localite addAgent(Agent agent) {
        this.agents.add(agent);
        agent.setLocalite(this);
        return this;
    }

    public Localite removeAgent(Agent agent) {
        this.agents.remove(agent);
        agent.setLocalite(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Localite)) {
            return false;
        }
        return id != null && id.equals(((Localite) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Localite{" +
            "id=" + getId() +
            ", region='" + getRegion() + "'" +
            ", autreRegion='" + getAutreRegion() + "'" +
            ", departementDakar='" + getDepartementDakar() + "'" +
            ", departementDiourbel='" + getDepartementDiourbel() + "'" +
            ", departementFatick='" + getDepartementFatick() + "'" +
            ", departementKaffrine='" + getDepartementKaffrine() + "'" +
            ", departementKaolack='" + getDepartementKaolack() + "'" +
            ", departementKedougou='" + getDepartementKedougou() + "'" +
            ", departementKolda='" + getDepartementKolda() + "'" +
            ", departementLouga='" + getDepartementLouga() + "'" +
            ", departementMatam='" + getDepartementMatam() + "'" +
            ", departementSaint='" + getDepartementSaint() + "'" +
            ", departementSedhiou='" + getDepartementSedhiou() + "'" +
            ", departementTambacounda='" + getDepartementTambacounda() + "'" +
            ", departementThis='" + getDepartementThis() + "'" +
            ", departementZiguinchor='" + getDepartementZiguinchor() + "'" +
            ", autredepartementDakar='" + getAutredepartementDakar() + "'" +
            ", autredepartementDiourbel='" + getAutredepartementDiourbel() + "'" +
            ", autredepartementFatick='" + getAutredepartementFatick() + "'" +
            ", autredepartementKaffrine='" + getAutredepartementKaffrine() + "'" +
            ", autredepartementKaolack='" + getAutredepartementKaolack() + "'" +
            ", autredepartementKedougou='" + getAutredepartementKedougou() + "'" +
            ", autredepartementKolda='" + getAutredepartementKolda() + "'" +
            ", autredepartementLouga='" + getAutredepartementLouga() + "'" +
            ", autredepartementMatam='" + getAutredepartementMatam() + "'" +
            ", autredepartementSaint='" + getAutredepartementSaint() + "'" +
            ", autredepartementSedhiou='" + getAutredepartementSedhiou() + "'" +
            ", autredepartementTambacounda='" + getAutredepartementTambacounda() + "'" +
            ", autredepartementThis='" + getAutredepartementThis() + "'" +
            ", autredepartementZiguinchor='" + getAutredepartementZiguinchor() + "'" +
            ", commune='" + getCommune() + "'" +
            ", nomQuartier='" + getNomQuartier() + "'" +
            "}";
    }
}
