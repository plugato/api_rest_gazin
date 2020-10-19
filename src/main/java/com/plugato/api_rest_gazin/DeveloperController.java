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
    DeveloperService service = new DeveloperService();
    //DeveloperRepository developerRepository;

    @PostMapping(consumes="application/json")
    public ResponseEntity<DeveloperResponseDTO> AddDeveloper(@RequestBody DeveloperDTO developerDTO ){

        DeveloperResponseDTO developerResponseDTO = service.save( developerDTO );
        return ResponseEntity.created( service.createURI( developerResponseDTO ) ).body( developerResponseDTO );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeveloperResponseDTO> DeleteDeveloper( @PathVariable Long id ){
        DeveloperResponseDTO developerDelete = service.delete( id );
        if( developerDelete.getId() != 0 )
            return ResponseEntity.ok().body( developerDelete );
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

        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.Direction.ASC,
                "nome");

        PageImpl pagaImpl = new PageImpl<>(
                service.makeResponseList( service.listDeveloper( allParams, pageRequest ) ),
                pageRequest,
                size);

        return ResponseEntity.ok().body( pagaImpl );

    }

    @GetMapping("/{id}")
    public ResponseEntity<DeveloperResponseDTO> findById(@PathVariable Long id ){

        DeveloperResponseDTO developerResponseDTO = service.findById( id );

        if ( developerResponseDTO.getId() != 0 )
            return ResponseEntity.ok().body( developerResponseDTO );

        return ResponseEntity.notFound().build();

    }

    @PutMapping("/{id}")
    public ResponseEntity<DeveloperResponseDTO> modeifyDeveloper(@PathVariable Long id, @RequestBody DeveloperDTO developerDTO ){

        DeveloperResponseDTO developerResponseDTO = service.modeifyDeveloper( id, developerDTO );
        if(  developerResponseDTO.getId() != 0  ){
            return ResponseEntity.ok().body( developerResponseDTO );
        }
        return ResponseEntity.badRequest().build();
    }


}
