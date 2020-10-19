package com.plugato.api_rest_gazin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.sql.Date;
import java.text.ParseException;
import java.util.*;


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
//    @GetMapping
//    public ResponseEntity<List<DeveloperResponseDTO>> ListDeveloper(@RequestParam Map<String,String> allParams ){
//        List<Developer> developerList = developerRepository.findAll();
//        List<DeveloperResponseDTO> developerResponseList = new ArrayList<>();
//
//        for( Developer list : developerList ){
//            developerResponseList.add( DeveloperResponseDTO.transformToDTO( list ) );
//        }
//        return ResponseEntity.ok().body( developerResponseList );
//
//    }
//Logger logger = LoggerFactory.getLogger(ApiRestGazinApplication.class);

    @GetMapping
    public ResponseEntity<Page<DeveloperResponseDTO>> ListDeveloper(@RequestParam Map<String,String> allParams,
                                                                    @RequestParam(
                                                                            value = "page",
                                                                            required = false,
                                                                            defaultValue = "0") int page,
                                                                    @RequestParam(
                                                                            value = "size",
                                                                            required = false,
                                                                            defaultValue = "2") int size ) throws ParseException {
        List<DeveloperResponseDTO> developerResponseList = new ArrayList<>();
        Page<Developer> developerList ;


        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.Direction.ASC,
                "nome");

        if(allParams.isEmpty() ) {
            developerList = developerRepository.findAll( pageRequest );
        } else {
            String where = new String();
            Developer developerFilter = new Developer();
            for (Map.Entry<String, String> entry : allParams.entrySet())
            {

                if( entry.getKey().equals( "id" ) ) {
                    developerFilter.setId( Long.parseLong(entry.getValue() ) );
                }
                if( entry.getKey().equals( "nome" )) {
                    developerFilter.setNome( entry.getValue() );
                }
                if( entry.getKey().equals( "sexo" )) {
                    developerFilter.setSexo( entry.getValue() );
                }
                if( entry.getKey().equals( "idade" )) {
                    developerFilter.setIdade( Integer.parseInt( entry.getValue() ) );
                }
                if( entry.getKey().equals( "hobby" )) {
                    developerFilter.setHobby( entry.getValue() );
                }
                if( entry.getKey().equals( "datanascimento" )) {
                    //estava invertendo dpois que troquei para date sql
//                    String dataString = entry.getValue();
//                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    developerFilter.setDatanascimento( Date.valueOf( entry.getValue() ) );
                }

            }
            if( !developerFilter.equals( new Developer() ) ){

                developerList = developerRepository.queryWhere( developerFilter.getId(),
                        developerFilter.getNome(),
                        developerFilter.getSexo(),
                        developerFilter.getIdade(),
                        developerFilter.getHobby(),
                        developerFilter.getDatanascimento(), pageRequest );

            }else
                developerList =  developerRepository.findAll( pageRequest );//findAll( pageRequest, size );

        }

        for (Developer list : developerList) {
            developerResponseList.add(DeveloperResponseDTO.transformToDTO(list));
        }

        return ResponseEntity.ok().body( new PageImpl<>(developerResponseList, pageRequest, size) );

    }
    public PageImpl<Developer> findAll(PageRequest pageRequest, int size ) {

        return new PageImpl<Developer>(
                (List<Developer>) developerRepository.findAll( pageRequest ),
                pageRequest, size);
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
