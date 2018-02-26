package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Randonnee.
 */
@Entity
@Table(name = "randonnee")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Randonnee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lieu_de_rdv")
    private String lieuDeRDV;

    @Column(name = "denivele_positif")
    private Integer denivelePositif;

    @Column(name = "duree")
    private Integer duree;

    @Column(name = "jhi_date")
    private ZonedDateTime date;

    @ManyToMany(mappedBy = "marcheurs")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Randonneur> parcours = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLieuDeRDV() {
        return lieuDeRDV;
    }

    public Randonnee lieuDeRDV(String lieuDeRDV) {
        this.lieuDeRDV = lieuDeRDV;
        return this;
    }

    public void setLieuDeRDV(String lieuDeRDV) {
        this.lieuDeRDV = lieuDeRDV;
    }

    public Integer getDenivelePositif() {
        return denivelePositif;
    }

    public Randonnee denivelePositif(Integer denivelePositif) {
        this.denivelePositif = denivelePositif;
        return this;
    }

    public void setDenivelePositif(Integer denivelePositif) {
        this.denivelePositif = denivelePositif;
    }

    public Integer getDuree() {
        return duree;
    }

    public Randonnee duree(Integer duree) {
        this.duree = duree;
        return this;
    }

    public void setDuree(Integer duree) {
        this.duree = duree;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public Randonnee date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Set<Randonneur> getParcours() {
        return parcours;
    }

    public Randonnee parcours(Set<Randonneur> randonneurs) {
        this.parcours = randonneurs;
        return this;
    }

    public Randonnee addParcours(Randonneur randonneur) {
        this.parcours.add(randonneur);
        randonneur.getMarcheurs().add(this);
        return this;
    }

    public Randonnee removeParcours(Randonneur randonneur) {
        this.parcours.remove(randonneur);
        randonneur.getMarcheurs().remove(this);
        return this;
    }

    public void setParcours(Set<Randonneur> randonneurs) {
        this.parcours = randonneurs;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Randonnee randonnee = (Randonnee) o;
        if (randonnee.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), randonnee.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Randonnee{" +
            "id=" + getId() +
            ", lieuDeRDV='" + getLieuDeRDV() + "'" +
            ", denivelePositif=" + getDenivelePositif() +
            ", duree=" + getDuree() +
            ", date='" + getDate() + "'" +
            "}";
    }
}
