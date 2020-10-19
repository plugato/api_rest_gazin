package com.plugato.api_rest_gazin;

import org.springframework.data.domain.Page;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DeveloperService {
    public Boolean fillFilter(Developer developerFilter, Map<String,String> allParams){

        String where = new String();
        //Developer developerFilter = new Developer();
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
        Developer teste = new Developer();
        return ! developerFilter.equals( teste );
    }
    public List<DeveloperResponseDTO> makeResponseList(Page<Developer> developerList){
        List<DeveloperResponseDTO> developerResponseList = new ArrayList<>();

        for (Developer list : developerList ) {
            developerResponseList.add(DeveloperResponseDTO.transformToDTO(list));
        }

        return developerResponseList;
    }

    public Developer makeUpdateDeveloper(Optional<Developer> developer, DeveloperDTO developerDTO, Long id ){
        Developer devel = null; //new Developer();

        if ( developer.isPresent() ) {
            devel = developerDTO.transformToObject();
            devel.setId(id);

        }
        return devel;

    }


}
