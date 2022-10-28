package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Services.
 */
@Entity
@Table(name = "services")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Services implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(name = "logo")
    private byte[] logo;

    @Column(name = "logo_content_type")
    private String logoContentType;

    @Column(name = "nom_service")
    private String nomService;

    @Column(name = "chef_service")
    private String chefService;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "is_pilotage")
    private Boolean isPilotage;

    @JsonIgnoreProperties(value = { "membres", "services" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Commission commission;

    @OneToMany(mappedBy = "services")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "user", "bailleur", "membre", "services", "localite" }, allowSetters = true)
    private Set<Agent> agents = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Services id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getLogo() {
        return this.logo;
    }

    public Services logo(byte[] logo) {
        this.setLogo(logo);
        return this;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getLogoContentType() {
        return this.logoContentType;
    }

    public Services logoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
        return this;
    }

    public void setLogoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
    }

    public String getNomService() {
        return this.nomService;
    }

    public Services nomService(String nomService) {
        this.setNomService(nomService);
        return this;
    }

    public void setNomService(String nomService) {
        this.nomService = nomService;
    }

    public String getChefService() {
        return this.chefService;
    }

    public Services chefService(String chefService) {
        this.setChefService(chefService);
        return this;
    }

    public void setChefService(String chefService) {
        this.chefService = chefService;
    }

    public String getDescription() {
        return this.description;
    }

    public Services description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsPilotage() {
        return this.isPilotage;
    }

    public Services isPilotage(Boolean isPilotage) {
        this.setIsPilotage(isPilotage);
        return this;
    }

    public void setIsPilotage(Boolean isPilotage) {
        this.isPilotage = isPilotage;
    }

    public Commission getCommission() {
        return this.commission;
    }

    public void setCommission(Commission commission) {
        this.commission = commission;
    }

    public Services commission(Commission commission) {
        this.setCommission(commission);
        return this;
    }

    public Set<Agent> getAgents() {
        return this.agents;
    }

    public void setAgents(Set<Agent> agents) {
        if (this.agents != null) {
            this.agents.forEach(i -> i.setServices(null));
        }
        if (agents != null) {
            agents.forEach(i -> i.setServices(this));
        }
        this.agents = agents;
    }

    public Services agents(Set<Agent> agents) {
        this.setAgents(agents);
        return this;
    }

    public Services addAgent(Agent agent) {
        this.agents.add(agent);
        agent.setServices(this);
        return this;
    }

    public Services removeAgent(Agent agent) {
        this.agents.remove(agent);
        agent.setServices(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Services)) {
            return false;
        }
        return id != null && id.equals(((Services) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Services{" +
            "id=" + getId() +
            ", logo='" + getLogo() + "'" +
            ", logoContentType='" + getLogoContentType() + "'" +
            ", nomService='" + getNomService() + "'" +
            ", chefService='" + getChefService() + "'" +
            ", description='" + getDescription() + "'" +
            ", isPilotage='" + getIsPilotage() + "'" +
            "}";
    }
}
