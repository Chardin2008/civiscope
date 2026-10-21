package civicscope.service;

import civicscope.Election;
import civicscope.TypeElection;
import civicscope.repository.ElectionRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class ElectionService {

    private final ElectionRepository electionRepository;

    public ElectionService(ElectionRepository electionRepository) {
        this.electionRepository = electionRepository;
    }

    public List<Election> lister(Integer limit) {
        if (limit != null && limit <= 0) {
            throw new IllegalArgumentException(
                    "La limite doit être strictement positive."
            );
        }

        Sort tri = Sort.by(Sort.Direction.DESC, "date");

        if (limit == null) {
            return electionRepository.findAll(tri);
        }

        return electionRepository
                .findAll(PageRequest.of(0, limit, tri))
                .getContent();
    }

    public Election trouverParId(UUID id) {
        return electionRepository.findById(id)
                .orElseThrow(() -> new ElectionIntrouvableException(id));
    }

    public Election creer(Election election) {
        verifierElection(election);

        if (electionRepository.existsByCode(election.getCode())) {
            throw new ConflitElectionException(
                    "Ce code d'élection est déjà utilisé."
            );
        }

        election.setId(UUID.randomUUID());

        verifierUniciteMetier(
                election.getDate(),
                election.getTypeElection(),
                election.getTourElection(),
                election.getId()
        );

        return electionRepository.save(election);
    }

    public Election modifier(UUID id, Election donnees) {
        Election existante = trouverParId(id);
        verifierElection(donnees);

        if (electionRepository.existsByCode(donnees.getCode())
                && !existante.getCode().equals(donnees.getCode())) {
            throw new ConflitElectionException(
                    "Ce code d'élection est déjà utilisé."
            );
        }

        verifierUniciteMetier(
                donnees.getDate(),
                donnees.getTypeElection(),
                donnees.getTourElection(),
                id
        );

        existante.setCode(donnees.getCode());
        existante.setDate(donnees.getDate());
        existante.setTypeElection(donnees.getTypeElection());
        existante.setTourElection(donnees.getTourElection());
        existante.setCodePays(donnees.getCodePays());

        return electionRepository.save(existante);
    }

    public void supprimer(UUID id) {
        Election election = trouverParId(id);
        electionRepository.delete(election);
    }

    private void verifierElection(Election election) {
        if (election == null
                || election.getCode() == null
                || election.getCode().isBlank()
                || election.getDate() == null
                || election.getTypeElection() == null) {
            throw new IllegalArgumentException(
                    "Le code, la date et le type d'élection sont obligatoires."
            );
        }
    }

    private void verifierUniciteMetier(
            LocalDate date,
            TypeElection type,
            civicscope.TourElection tour,
            UUID idIgnore
    ) {
        LocalDate debutAnnee = LocalDate.of(date.getYear(), 1, 1);
        LocalDate finAnnee = LocalDate.of(date.getYear(), 12, 31);

        boolean conflit = electionRepository
                .existsByDateBetweenAndTypeElectionAndTourElectionAndIdNot(
                        debutAnnee,
                        finAnnee,
                        type,
                        tour,
                        idIgnore
                );

        if (conflit) {
            throw new ConflitElectionException(
                    "Une élection de même année, de même type et de même tour existe déjà."
            );
        }
    }
}