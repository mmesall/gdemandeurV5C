package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Commission.
 */
@Entity
@Table(name = "commission")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Commission implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom_commission")
    private String nomCommission;

    @Lob
    @Column(name = "mission")
    private String mission;

    @OneToMany(mappedBy = "commission")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "user", "agent", "commission" }, allowSetters = true)
    private Set<Membre> membres = new HashSet<>();

    @JsonIgnoreProperties(value = { "commission", "agents" }, allowSetters = true)
    @OneToOne(mappedBy = "commission")
    private Services services;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Commission id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomCommission() {
        return this.nomCommission;
    }

    public Commission nomCommission(String nomCommission) {
        this.setNomCommission(nomCommission);
        return this;
    }

    public void setNomCommission(String nomCommission) {
        this.nomCommission = nomCommission;
    }

    public String getMission() {
        return this.mission;
    }

    public Commission mission(String mission) {
        this.setMission(mission);
        return this;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

    public Set<Membre> getMembres() {
        return this.membres;
    }

    public void setMembres(Set<Membre> membres) {
        if (this.membres != null) {
            this.membres.forEach(i -> i.setCommission(null));
        }
        if (membres != null) {
            membres.forEach(i -> i.setCommission(this));
        }
        this.membres = membres;
    }

    public Commission membres(Set<Membre> membres) {
        this.setMembres(membres);
        return this;
    }

    public Commission addMembre(Membre membre) {
        this.membres.add(membre);
        membre.setCommission(this);
        return this;
    }

    public Commission removeMembre(Membre membre) {
        this.membres.remove(membre);
        membre.setCommission(null);
        return this;
    }

    public Services getServices() {
        return this.services;
    }

    public void setServices(Services services) {
        if (this.services != null) {
            this.services.setCommission(null);
        }
        if (services != null) {
            services.setCommission(this);
        }
        this.services = services;
    }

    public Commission services(Services services) {
        this.setServices(services);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Commission)) {
            return false;
        }
        return id != null && id.equals(((Commission) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Commission{" +
            "id=" + getId() +
            ", nomCommission='" + getNomCommission() + "'" +
            ", mission='" + getMission() + "'" +
            "}";
    }
}
