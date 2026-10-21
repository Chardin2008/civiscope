package civicscope.service;

import java.util.UUID;

public class ElectionIntrouvableException extends RuntimeException {

    public ElectionIntrouvableException(UUID id) {
        super("Aucune élection trouvée avec l'identifiant " + id);
    }
}