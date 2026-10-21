package civicscope.controller;

import civicscope.Election;
import civicscope.service.ElectionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/elections")
public class ElectionController {

    private final ElectionService electionService;

    public ElectionController(ElectionService electionService) {
        this.electionService = electionService;
    }

    @GetMapping
    public List<Election> lister(
            @RequestParam(required = false) Integer limit
    ) {
        return electionService.lister(limit);
    }

    @GetMapping("/{id}")
    public Election trouverParId(@PathVariable UUID id) {
        return electionService.trouverParId(id);
    }

    @PostMapping
    public ResponseEntity<Election> creer(@RequestBody Election election) {
        Election creee = electionService.creer(election);
        return ResponseEntity.status(HttpStatus.CREATED).body(creee);
    }

    @PutMapping("/{id}")
    public Election modifier(
            @PathVariable UUID id,
            @RequestBody Election election
    ) {
        return electionService.modifier(id, election);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable UUID id) {
        electionService.supprimer(id);
        return ResponseEntity.noContent().build();
    }
}