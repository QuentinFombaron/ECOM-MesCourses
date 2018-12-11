package ecom.mescourses.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import ecom.mescourses.domain.enumeration.Sport;

/**
 * A Course.
 */
@Entity
@Table(name = "course")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "course")
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "organisateur")
    private Integer organisateur;

    @Column(name = "titre")
    private String titre;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "sport")
    private Sport sport;

    @Column(name = "jhi_date")
    private LocalDate date;

    @Column(name = "heure")
    private String heure;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "lieu")
    private String lieu;

    @Column(name = "prix")
    private Double prix;

    @Lob
    @Column(name = "image_1")
    private byte[] image1;

    @Column(name = "image_1_content_type")
    private String image1ContentType;

    @Lob
    @Column(name = "image_2")
    private byte[] image2;

    @Column(name = "image_2_content_type")
    private String image2ContentType;

    @Lob
    @Column(name = "image_3")
    private byte[] image3;

    @Column(name = "image_3_content_type")
    private String image3ContentType;

    @Lob
    @Column(name = "image_4")
    private byte[] image4;

    @Column(name = "image_4_content_type")
    private String image4ContentType;

    @Lob
    @Column(name = "image_5")
    private byte[] image5;

    @Column(name = "image_5_content_type")
    private String image5ContentType;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "course_participants",
               joinColumns = @JoinColumn(name = "courses_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "participants_id", referencedColumnName = "id"))
    private Set<User> participants = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrganisateur() {
        return organisateur;
    }

    public Course organisateur(Integer organisateur) {
        this.organisateur = organisateur;
        return this;
    }

    public void setOrganisateur(Integer organisateur) {
        this.organisateur = organisateur;
    }

    public String getTitre() {
        return titre;
    }

    public Course titre(String titre) {
        this.titre = titre;
        return this;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public Course description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Sport getSport() {
        return sport;
    }

    public Course sport(Sport sport) {
        this.sport = sport;
        return this;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }

    public LocalDate getDate() {
        return date;
    }

    public Course date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getHeure() {
        return heure;
    }

    public Course heure(String heure) {
        this.heure = heure;
        return this;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Course longitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Course latitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getLieu() {
        return lieu;
    }

    public Course lieu(String lieu) {
        this.lieu = lieu;
        return this;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public Double getPrix() {
        return prix;
    }

    public Course prix(Double prix) {
        this.prix = prix;
        return this;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    @Basic(fetch = FetchType.LAZY)
    public byte[] getImage1() {
        return image1;
    }

    public Course image1(byte[] image1) {
        this.image1 = image1;
        return this;
    }

    public void setImage1(byte[] image1) {
        this.image1 = image1;
    }

    public String getImage1ContentType() {
        return image1ContentType;
    }

    public Course image1ContentType(String image1ContentType) {
        this.image1ContentType = image1ContentType;
        return this;
    }

    public void setImage1ContentType(String image1ContentType) {
        this.image1ContentType = image1ContentType;
    }

    @Basic(fetch = FetchType.LAZY)
    public byte[] getImage2() {
        return image2;
    }

    public Course image2(byte[] image2) {
        this.image2 = image2;
        return this;
    }

    public void setImage2(byte[] image2) {
        this.image2 = image2;
    }

    public String getImage2ContentType() {
        return image2ContentType;
    }

    public Course image2ContentType(String image2ContentType) {
        this.image2ContentType = image2ContentType;
        return this;
    }

    public void setImage2ContentType(String image2ContentType) {
        this.image2ContentType = image2ContentType;
    }
    
    @Basic(fetch = FetchType.LAZY)
    public byte[] getImage3() {
        return image3;
    }

    public Course image3(byte[] image3) {
        this.image3 = image3;
        return this;
    }

    public void setImage3(byte[] image3) {
        this.image3 = image3;
    }

    public String getImage3ContentType() {
        return image3ContentType;
    }

    public Course image3ContentType(String image3ContentType) {
        this.image3ContentType = image3ContentType;
        return this;
    }

    public void setImage3ContentType(String image3ContentType) {
        this.image3ContentType = image3ContentType;
    }

    @Basic(fetch = FetchType.LAZY)
    public byte[] getImage4() {
        return image4;
    }

    public Course image4(byte[] image4) {
        this.image4 = image4;
        return this;
    }

    public void setImage4(byte[] image4) {
        this.image4 = image4;
    }

    public String getImage4ContentType() {
        return image4ContentType;
    }

    public Course image4ContentType(String image4ContentType) {
        this.image4ContentType = image4ContentType;
        return this;
    }

    public void setImage4ContentType(String image4ContentType) {
        this.image4ContentType = image4ContentType;
    }
    
    @Basic(fetch = FetchType.LAZY)
    public byte[] getImage5() {
        return image5;
    }

    public Course image5(byte[] image5) {
        this.image5 = image5;
        return this;
    }

    public void setImage5(byte[] image5) {
        this.image5 = image5;
    }

    public String getImage5ContentType() {
        return image5ContentType;
    }

    public Course image5ContentType(String image5ContentType) {
        this.image5ContentType = image5ContentType;
        return this;
    }

    public void setImage5ContentType(String image5ContentType) {
        this.image5ContentType = image5ContentType;
    }

    public Set<User> getParticipants() {
        return participants;
    }

    public Course participants(Set<User> users) {
        this.participants = users;
        return this;
    }

    public Course addParticipants(User user) {
        this.participants.add(user);
        return this;
    }

    public Course removeParticipants(User user) {
        this.participants.remove(user);
        return this;
    }

    public void setParticipants(Set<User> users) {
        this.participants = users;
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
        Course course = (Course) o;
        if (course.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), course.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Course{" +
            "id=" + getId() +
            ", organisateur=" + getOrganisateur() +
            ", titre='" + getTitre() + "'" +
            ", description='" + getDescription() + "'" +
            ", sport='" + getSport() + "'" +
            ", date='" + getDate() + "'" +
            ", heure='" + getHeure() + "'" +
            ", longitude=" + getLongitude() +
            ", latitude=" + getLatitude() +
            ", lieu='" + getLieu() + "'" +
            ", prix=" + getPrix() +
            ", image1='" + getImage1() + "'" +
            ", image1ContentType='" + getImage1ContentType() + "'" +
            ", image2='" + getImage2() + "'" +
            ", image2ContentType='" + getImage2ContentType() + "'" +
            ", image3='" + getImage3() + "'" +
            ", image3ContentType='" + getImage3ContentType() + "'" +
            ", image4='" + getImage4() + "'" +
            ", image4ContentType='" + getImage4ContentType() + "'" +
            ", image5='" + getImage5() + "'" +
            ", image5ContentType='" + getImage5ContentType() + "'" +
            "}";
    }
}
