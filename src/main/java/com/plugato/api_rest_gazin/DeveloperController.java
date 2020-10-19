package com.plugato.api_rest_gazin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/developers")
public class DeveloperController {

    @Autowired(required=true)
    DeveloperService service = new DeveloperService();

    @PostMapping(consumes="application/json")
    public ResponseEntity<DeveloperResponseDTO> addDeveloper(@RequestBody DeveloperRequestDTO developerRequestDTO){
        DeveloperResponseDTO developerResponseDTO = service.save(developerRequestDTO);
        return ResponseEntity.created( service.createURI( developerResponseDTO ) ).body( developerResponseDTO );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeveloperResponseDTO> deleteDeveloper( @PathVariable Long id ){
        DeveloperResponseDTO developerDelete = service.delete( id );
        if( developerDelete.getId() != 0 )
            return ResponseEntity.ok().body( developerDelete );
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeveloperResponseDTO> getById(@PathVariable Long id ){
        DeveloperResponseDTO developerResponseDTO = service.getById( id );
        if ( developerResponseDTO.getId() != 0 )
            return ResponseEntity.ok().body( developerResponseDTO );

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeveloperResponseDTO> putDeveloper(@PathVariable Long id, @RequestBody DeveloperRequestDTO developerRequestDTO){
        DeveloperResponseDTO developerResponseDTO = service.modeifyDeveloper( id, developerRequestDTO);
        if(  developerResponseDTO.getId() != 0  )
            return ResponseEntity.ok().body( developerResponseDTO );

        return ResponseEntity.badRequest().build();
    }

    @GetMapping
    public ResponseEntity<Page<DeveloperResponseDTO>> listDeveloper(@RequestParam Map<String,String> allParams,
                                                                    @RequestParam(
                                                                            value = "page",
                                                                            required = false,
                                                                            defaultValue = "0") int page,
                                                                    @RequestParam(
                                                                            value = "size",
                                                                            required = false,
                                                                            defaultValue = "10") int size ){
        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.Direction.ASC,
                "nome");

        PageImpl pageImpl = service.PageableMakeResponseList( allParams, pageRequest, size );

        return ResponseEntity.ok().body( pageImpl );

    }




}
