package ecom.mescourses.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import ecom.mescourses.domain.enumeration.Sexe;

/**
 * A UserDocuments.
 */
@Entity
@Table(name = "user_documents")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "userdocuments")
public class UserDocuments implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_de_naissance")
    private LocalDate dateDeNaissance;

    @Enumerated(EnumType.STRING)
    @Column(name = "sexe")
    private Sexe sexe;

    @Lob
    @Column(name = "certificat_medical")
    private byte[] certificatMedical;

    @Column(name = "certificat_medical_content_type")
    private String certificatMedicalContentType;

    @Lob
    @Column(name = "license_federale")
    private byte[] licenseFederale;

    @Column(name = "license_federale_content_type")
    private String licenseFederaleContentType;

    @Lob
    @Column(name = "document_complementaire")
    private byte[] documentComplementaire;

    @Column(name = "document_complementaire_content_type")
    private String documentComplementaireContentType;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateDeNaissance() {
        return dateDeNaissance;
    }

    public UserDocuments dateDeNaissance(LocalDate dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
        return this;
    }

    public void setDateDeNaissance(LocalDate dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }

    public Sexe getSexe() {
        return sexe;
    }

    public UserDocuments sexe(Sexe sexe) {
        this.sexe = sexe;
        return this;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public byte[] getCertificatMedical() {
        return certificatMedical;
    }

    public UserDocuments certificatMedical(byte[] certificatMedical) {
        this.certificatMedical = certificatMedical;
        return this;
    }

    public void setCertificatMedical(byte[] certificatMedical) {
        this.certificatMedical = certificatMedical;
    }

    public String getCertificatMedicalContentType() {
        return certificatMedicalContentType;
    }

    public UserDocuments certificatMedicalContentType(String certificatMedicalContentType) {
        this.certificatMedicalContentType = certificatMedicalContentType;
        return this;
    }

    public void setCertificatMedicalContentType(String certificatMedicalContentType) {
        this.certificatMedicalContentType = certificatMedicalContentType;
    }

    public byte[] getLicenseFederale() {
        return licenseFederale;
    }

    public UserDocuments licenseFederale(byte[] licenseFederale) {
        this.licenseFederale = licenseFederale;
        return this;
    }

    public void setLicenseFederale(byte[] licenseFederale) {
        this.licenseFederale = licenseFederale;
    }

    public String getLicenseFederaleContentType() {
        return licenseFederaleContentType;
    }

    public UserDocuments licenseFederaleContentType(String licenseFederaleContentType) {
        this.licenseFederaleContentType = licenseFederaleContentType;
        return this;
    }

    public void setLicenseFederaleContentType(String licenseFederaleContentType) {
        this.licenseFederaleContentType = licenseFederaleContentType;
    }

    public byte[] getDocumentComplementaire() {
        return documentComplementaire;
    }

    public UserDocuments documentComplementaire(byte[] documentComplementaire) {
        this.documentComplementaire = documentComplementaire;
        return this;
    }

    public void setDocumentComplementaire(byte[] documentComplementaire) {
        this.documentComplementaire = documentComplementaire;
    }

    public String getDocumentComplementaireContentType() {
        return documentComplementaireContentType;
    }

    public UserDocuments documentComplementaireContentType(String documentComplementaireContentType) {
        this.documentComplementaireContentType = documentComplementaireContentType;
        return this;
    }

    public void setDocumentComplementaireContentType(String documentComplementaireContentType) {
        this.documentComplementaireContentType = documentComplementaireContentType;
    }

    public User getUser() {
        return user;
    }

    public UserDocuments user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
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
        UserDocuments userDocuments = (UserDocuments) o;
        if (userDocuments.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userDocuments.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserDocuments{" +
            "id=" + getId() +
            ", dateDeNaissance='" + getDateDeNaissance() + "'" +
            ", sexe='" + getSexe() + "'" +
            ", certificatMedical='" + getCertificatMedical() + "'" +
            ", certificatMedicalContentType='" + getCertificatMedicalContentType() + "'" +
            ", licenseFederale='" + getLicenseFederale() + "'" +
            ", licenseFederaleContentType='" + getLicenseFederaleContentType() + "'" +
            ", documentComplementaire='" + getDocumentComplementaire() + "'" +
            ", documentComplementaireContentType='" + getDocumentComplementaireContentType() + "'" +
            "}";
    }
}
