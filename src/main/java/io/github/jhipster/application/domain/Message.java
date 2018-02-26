package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Message.
 */
@Entity
@Table(name = "message")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "id_randonnee", nullable = false)
    private Integer idRandonnee;

    @NotNull
    @Column(name = "id_randonneur", nullable = false)
    private Integer idRandonneur;

    @NotNull
    @Min(value = 0L)
    @Max(value = 360L)
    @Column(name = "longitude", nullable = false)
    private Long longitude;

    @NotNull
    @Min(value = 0L)
    @Max(value = 360L)
    @Column(name = "latitude", nullable = false)
    private Long latitude;

    @NotNull
    @Column(name = "date_heure", nullable = false)
    private ZonedDateTime dateHeure;

    @NotNull
    @Column(name = "s_os", nullable = false)
    private Boolean sOS;

    @Column(name = "freq_cardiaque")
    private Integer freqCardiaque;

    @ManyToMany(mappedBy = "envoyeurs")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Randonneur> positions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdRandonnee() {
        return idRandonnee;
    }

    public Message idRandonnee(Integer idRandonnee) {
        this.idRandonnee = idRandonnee;
        return this;
    }

    public void setIdRandonnee(Integer idRandonnee) {
        this.idRandonnee = idRandonnee;
    }

    public Integer getIdRandonneur() {
        return idRandonneur;
    }

    public Message idRandonneur(Integer idRandonneur) {
        this.idRandonneur = idRandonneur;
        return this;
    }

    public void setIdRandonneur(Integer idRandonneur) {
        this.idRandonneur = idRandonneur;
    }

    public Long getLongitude() {
        return longitude;
    }

    public Message longitude(Long longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(Long longitude) {
        this.longitude = longitude;
    }

    public Long getLatitude() {
        return latitude;
    }

    public Message latitude(Long latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(Long latitude) {
        this.latitude = latitude;
    }

    public ZonedDateTime getDateHeure() {
        return dateHeure;
    }

    public Message dateHeure(ZonedDateTime dateHeure) {
        this.dateHeure = dateHeure;
        return this;
    }

    public void setDateHeure(ZonedDateTime dateHeure) {
        this.dateHeure = dateHeure;
    }

    public Boolean issOS() {
        return sOS;
    }

    public Message sOS(Boolean sOS) {
        this.sOS = sOS;
        return this;
    }

    public void setsOS(Boolean sOS) {
        this.sOS = sOS;
    }

    public Integer getFreqCardiaque() {
        return freqCardiaque;
    }

    public Message freqCardiaque(Integer freqCardiaque) {
        this.freqCardiaque = freqCardiaque;
        return this;
    }

    public void setFreqCardiaque(Integer freqCardiaque) {
        this.freqCardiaque = freqCardiaque;
    }

    public Set<Randonneur> getPositions() {
        return positions;
    }

    public Message positions(Set<Randonneur> randonneurs) {
        this.positions = randonneurs;
        return this;
    }

    public Message addPositions(Randonneur randonneur) {
        this.positions.add(randonneur);
        randonneur.getEnvoyeurs().add(this);
        return this;
    }

    public Message removePositions(Randonneur randonneur) {
        this.positions.remove(randonneur);
        randonneur.getEnvoyeurs().remove(this);
        return this;
    }

    public void setPositions(Set<Randonneur> randonneurs) {
        this.positions = randonneurs;
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
        Message message = (Message) o;
        if (message.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), message.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Message{" +
            "id=" + getId() +
            ", idRandonnee=" + getIdRandonnee() +
            ", idRandonneur=" + getIdRandonneur() +
            ", longitude=" + getLongitude() +
            ", latitude=" + getLatitude() +
            ", dateHeure='" + getDateHeure() + "'" +
            ", sOS='" + issOS() + "'" +
            ", freqCardiaque=" + getFreqCardiaque() +
            "}";
    }
}
