package io.github.jhipster.application.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import io.github.jhipster.application.domain.enumeration.Sexe;

/**
 * A Randonneur.
 */
@Entity
@Table(name = "randonneur")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Randonneur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "prenom", nullable = false)
    private String prenom;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @Enumerated(EnumType.STRING)
    @Column(name = "sexe")
    private Sexe sexe;

    @Column(name = "age")
    private Integer age;

    @Column(name = "date_de_naissance")
    private ZonedDateTime dateDeNaissance;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "randonneur_marcheur",
               joinColumns = @JoinColumn(name="randonneurs_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="marcheurs_id", referencedColumnName="id"))
    private Set<Randonnee> marcheurs = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "randonneur_envoyeur",
               joinColumns = @JoinColumn(name="randonneurs_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="envoyeurs_id", referencedColumnName="id"))
    private Set<Message> envoyeurs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrenom() {
        return prenom;
    }

    public Randonneur prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public Randonneur nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Sexe getSexe() {
        return sexe;
    }

    public Randonneur sexe(Sexe sexe) {
        this.sexe = sexe;
        return this;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public Integer getAge() {
        return age;
    }

    public Randonneur age(Integer age) {
        this.age = age;
        return this;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public ZonedDateTime getDateDeNaissance() {
        return dateDeNaissance;
    }

    public Randonneur dateDeNaissance(ZonedDateTime dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
        return this;
    }

    public void setDateDeNaissance(ZonedDateTime dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }

    public Set<Randonnee> getMarcheurs() {
        return marcheurs;
    }

    public Randonneur marcheurs(Set<Randonnee> randonnees) {
        this.marcheurs = randonnees;
        return this;
    }

    public Randonneur addMarcheur(Randonnee randonnee) {
        this.marcheurs.add(randonnee);
        randonnee.getParcours().add(this);
        return this;
    }

    public Randonneur removeMarcheur(Randonnee randonnee) {
        this.marcheurs.remove(randonnee);
        randonnee.getParcours().remove(this);
        return this;
    }

    public void setMarcheurs(Set<Randonnee> randonnees) {
        this.marcheurs = randonnees;
    }

    public Set<Message> getEnvoyeurs() {
        return envoyeurs;
    }

    public Randonneur envoyeurs(Set<Message> messages) {
        this.envoyeurs = messages;
        return this;
    }

    public Randonneur addEnvoyeur(Message message) {
        this.envoyeurs.add(message);
        message.getPositions().add(this);
        return this;
    }

    public Randonneur removeEnvoyeur(Message message) {
        this.envoyeurs.remove(message);
        message.getPositions().remove(this);
        return this;
    }

    public void setEnvoyeurs(Set<Message> messages) {
        this.envoyeurs = messages;
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
        Randonneur randonneur = (Randonneur) o;
        if (randonneur.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), randonneur.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Randonneur{" +
            "id=" + getId() +
            ", prenom='" + getPrenom() + "'" +
            ", nom='" + getNom() + "'" +
            ", sexe='" + getSexe() + "'" +
            ", age=" + getAge() +
            ", dateDeNaissance='" + getDateDeNaissance() + "'" +
            "}";
    }
}
