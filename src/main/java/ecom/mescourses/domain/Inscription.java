package ecom.mescourses.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

import ecom.mescourses.domain.enumeration.Metier;

/**
 * A Inscription.
 */
@Entity
@Table(name = "inscription")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "inscription")
public class Inscription implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_role")
    private Metier role;

    @Column(name = "jhi_user")
    private Integer user;

    @Column(name = "course")
    private Integer course;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Metier getRole() {
        return role;
    }

    public Inscription role(Metier role) {
        this.role = role;
        return this;
    }

    public void setRole(Metier role) {
        this.role = role;
    }

    public Integer getUser() {
        return user;
    }

    public Inscription user(Integer user) {
        this.user = user;
        return this;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public Integer getCourse() {
        return course;
    }

    public Inscription course(Integer course) {
        this.course = course;
        return this;
    }

    public void setCourse(Integer course) {
        this.course = course;
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
        Inscription inscription = (Inscription) o;
        if (inscription.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), inscription.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Inscription{" +
            "id=" + getId() +
            ", role='" + getRole() + "'" +
            ", user=" + getUser() +
            ", course=" + getCourse() +
            "}";
    }
}
