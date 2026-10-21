package civicscope.repository;

import civicscope.Election;
import civicscope.TourElection;
import civicscope.TypeElection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.UUID;

public interface ElectionRepository extends JpaRepository<Election, UUID> {

    boolean existsByCode(String code);

    boolean existsByDateBetweenAndTypeElectionAndTourElectionAndIdNot(
            LocalDate debut,
            LocalDate fin,
            TypeElection typeElection,
            TourElection tourElection,
            UUID id
    );
}