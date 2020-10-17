package com.plugato.api_rest_gazin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/developers")
public class DeveloperController {

    @Autowired(required=true)
    DeveloperRepository developerRepository;

    @PostMapping(consumes="application/json")
    public ResponseEntity<DeveloperResponseDTO> AddDeveloper(@RequestBody DeveloperDTO developerDTO ){

        Developer developer  = developerRepository.save( developerDTO.transformToObject() );

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().replacePath("/developers").path("/{id}")
                .buildAndExpand( developer.getId() ).toUri();

        return ResponseEntity.created(uri).body( DeveloperResponseDTO.transformToDTO(developer) );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeveloperResponseDTO> DeleteDeveloper( @PathVariable Long id ){
        Optional<Developer> developerDelete = developerRepository.findById( id );

        if (developerDelete.isPresent() ){
           developerRepository.delete( developerDelete.get() );
           return ResponseEntity.ok().body( DeveloperResponseDTO.transformToDTO( developerDelete.get() ) );

        }
        return ResponseEntity.noContent().build();

    }
    @GetMapping
    public ResponseEntity<List<DeveloperResponseDTO>> ListDeveloper(){
        List<Developer> developerList = developerRepository.findAll();
        List<DeveloperResponseDTO> developerResponseList = new ArrayList<>();

        for( Developer list : developerList ){
            developerResponseList.add( DeveloperResponseDTO.transformToDTO( list ) );
        }
        return ResponseEntity.ok().body( developerResponseList );
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeveloperResponseDTO> findById(@PathVariable Long id ){

        Optional<Developer> developerFind = developerRepository.findById( id ) ;

        if ( developerFind.isPresent() )
            return ResponseEntity.ok().body(  DeveloperResponseDTO.transformToDTO( developerFind.get() )  );

        return ResponseEntity.notFound().build();


    }

    @PutMapping("/{id}")
    public ResponseEntity<DeveloperResponseDTO> modeifyDeveloper(@PathVariable Long id, @RequestBody DeveloperDTO developerDTO ){

        Optional<Developer> developer = developerRepository.findById( id );

        if ( developer.isPresent() ){
            Developer devel = developerDTO.transformToObject();
            devel.setId(id);
            developerRepository.save( devel );
            return ResponseEntity.ok().body( DeveloperResponseDTO.transformToDTO( devel ) );
        }

        return ResponseEntity.badRequest().build();
    }


}
