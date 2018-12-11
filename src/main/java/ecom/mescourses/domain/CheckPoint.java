package ecom.mescourses.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CheckPoint.
 */
@Entity
@Table(name = "check_point")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "checkpoint")
public class CheckPoint implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "heure")
    private String heure;

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

    public String getHeure() {
        return heure;
    }

    public CheckPoint heure(String heure) {
        this.heure = heure;
        return this;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public Integer getUser() {
        return user;
    }

    public CheckPoint user(Integer user) {
        this.user = user;
        return this;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public Integer getCourse() {
        return course;
    }

    public CheckPoint course(Integer course) {
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
        CheckPoint checkPoint = (CheckPoint) o;
        if (checkPoint.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), checkPoint.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CheckPoint{" +
            "id=" + getId() +
            ", heure='" + getHeure() + "'" +
            ", user=" + getUser() +
            ", course=" + getCourse() +
            "}";
    }
}
