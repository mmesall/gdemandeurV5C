package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.TypePiece;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PieceJointe.
 */
@Entity
@Table(name = "piece_jointe")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PieceJointe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_piece")
    private TypePiece typePiece;

    @Column(name = "nom_piece")
    private String nomPiece;

    @ManyToOne
    @JsonIgnoreProperties(value = { "formation", "pieceJointes", "dossier" }, allowSetters = true)
    private Demande demande;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PieceJointe id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypePiece getTypePiece() {
        return this.typePiece;
    }

    public PieceJointe typePiece(TypePiece typePiece) {
        this.setTypePiece(typePiece);
        return this;
    }

    public void setTypePiece(TypePiece typePiece) {
        this.typePiece = typePiece;
    }

    public String getNomPiece() {
        return this.nomPiece;
    }

    public PieceJointe nomPiece(String nomPiece) {
        this.setNomPiece(nomPiece);
        return this;
    }

    public void setNomPiece(String nomPiece) {
        this.nomPiece = nomPiece;
    }

    public Demande getDemande() {
        return this.demande;
    }

    public void setDemande(Demande demande) {
        this.demande = demande;
    }

    public PieceJointe demande(Demande demande) {
        this.setDemande(demande);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PieceJointe)) {
            return false;
        }
        return id != null && id.equals(((PieceJointe) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PieceJointe{" +
            "id=" + getId() +
            ", typePiece='" + getTypePiece() + "'" +
            ", nomPiece='" + getNomPiece() + "'" +
            "}";
    }
}
