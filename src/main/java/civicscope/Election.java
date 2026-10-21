package civicscope;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "election")
public class Election {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "code", nullable = false, unique = true, length = 50)
    private String code;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_election", nullable = false, length = 50)
    private TypeElection typeElection;

    @Enumerated(EnumType.STRING)
    @Column(name = "tour_election", length = 50)
    private TourElection tourElection;

    @Column(name = "code_pays", length = 10)
    private String codePays;

    protected Election() {
        // Constructeur utilisé par JPA
    }

    public Election(
            UUID id,
            String code,
            LocalDate date,
            TypeElection typeElection,
            TourElection tourElection,
            String codePays
    ) {
        this.id = id;
        this.code = code;
        this.date = date;
        this.typeElection = typeElection;
        this.tourElection = tourElection;
        this.codePays = codePays;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public TypeElection getTypeElection() {
        return typeElection;
    }

    public void setTypeElection(TypeElection typeElection) {
        this.typeElection = typeElection;
    }

    public TourElection getTourElection() {
        return tourElection;
    }

    public void setTourElection(TourElection tourElection) {
        this.tourElection = tourElection;
    }

    public String getCodePays() {
        return codePays;
    }

    public void setCodePays(String codePays) {
        this.codePays = codePays;
    }
}