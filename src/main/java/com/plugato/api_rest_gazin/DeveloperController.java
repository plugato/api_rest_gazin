package com.plugato.api_rest_gazin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/developers")
public class DeveloperController {

    @Autowired(required = true)
    DeveloperService service = new DeveloperService();

    @PostMapping(consumes = "application/json")
    public ResponseEntity<DeveloperResponseDTO> addDeveloper(@RequestBody DeveloperRequestDTO developerRequestDTO) {
        DeveloperResponseDTO developerResponseDTO = service.save(developerRequestDTO);
        return ResponseEntity.created(service.createURI(developerResponseDTO)).body(developerResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeveloperResponseDTO> deleteDeveloper(@PathVariable Long id) throws Exception {
        try {
            DeveloperResponseDTO developerDelete = service.delete(id);
            return ResponseEntity.accepted().body(developerDelete);
        } catch (NoSuchElementException e) {
            return ResponseEntity.noContent().build();
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<DeveloperResponseDTO> getById(@PathVariable Long id) {
        try {
            DeveloperResponseDTO developerResponseDTO = service.getById(id);
            return ResponseEntity.ok().body(developerResponseDTO);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<DeveloperResponseDTO> putDeveloper(@PathVariable Long id, @RequestBody DeveloperRequestDTO developerRequestDTO) {

        DeveloperResponseDTO developerResponseDTO = service.modeifyDeveloper(id, developerRequestDTO);
        if (developerResponseDTO.getId() != 0)
            return ResponseEntity.ok().body(developerResponseDTO);

        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<Iterable<Developer>> listDeveloper(@RequestParam Map<String, String> allParams) {
        return ResponseEntity.ok().body(service.ControllParameterPageable(allParams));
    }

}
