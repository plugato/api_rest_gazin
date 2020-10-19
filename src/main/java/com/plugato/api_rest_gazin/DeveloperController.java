package com.plugato.api_rest_gazin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.text.ParseException;
import java.util.*;


@RestController
@RequestMapping("/developers")
public class DeveloperController {

    @Autowired(required=true)
    DeveloperRepository developerRepository;
    DeveloperService service = new DeveloperService();

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
    public ResponseEntity<Page<DeveloperResponseDTO>> ListDeveloper(@RequestParam Map<String,String> allParams,
                                                                    @RequestParam(
                                                                            value = "page",
                                                                            required = false,
                                                                            defaultValue = "0") int page,
                                                                    @RequestParam(
                                                                            value = "size",
                                                                            required = false,
                                                                            defaultValue = "10") int size ){
        Page<Developer> developerList ;
        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.Direction.ASC,
                "nome");

        Developer developerFilter = new Developer();

        if( service.fillFilter( developerFilter, allParams ) ){
            developerList = developerRepository.queryWhere( developerFilter.getId(),
                    developerFilter.getNome(),
                    developerFilter.getSexo(),
                    developerFilter.getIdade(),
                    developerFilter.getHobby(),
                    developerFilter.getDatanascimento(), pageRequest );
        }else {
            developerList = developerRepository.findAll(pageRequest);
        }

        return ResponseEntity.ok().body( new PageImpl<>( service.makeResponseList( developerList ), pageRequest, size) );

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
        Developer developer1 = service.makeUpdateDeveloper( developer, developerDTO, id );
        if(  developer1.getId() != 0  ){
            developerRepository.save( developer1 );
            return ResponseEntity.ok().body( DeveloperResponseDTO.transformToDTO( developer1 ) );
        }

        return ResponseEntity.badRequest().build();
    }


}
