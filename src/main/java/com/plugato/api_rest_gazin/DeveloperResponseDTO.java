package com.plugato.api_rest_gazin;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class DeveloperResponseDTO{

    private long id;
    private String nome;
    private char sexo;
    private int idade;
    private String hobby;
    private Date datanascimento;
    //teste nao retornar data nascimento

    public static DeveloperResponseDTO transformToDTO( Developer developer) {
        return new DeveloperResponseDTO(developer.getId(), developer.getNome(), developer.getSexo(), developer.getIdade(), developer.getHobby(), developer.getDatanascimento() );

    }
//    public static DeveloperResponseDTO transformToDTO(Developer developer) {///somente teste se esta transformando o dto certo
//        //return new DeveloperResponseDTO(developer.getId(), developer.getNome(), developer.getSexo(), developer.getIdade(), developer.getHobby(), developer.getDatanascimento() );
//        return new DeveloperResponseDTO(developer.getId(), developer.getNome(), developer.getSexo(), developer.getIdade(), developer.getHobby() );
//    }
}