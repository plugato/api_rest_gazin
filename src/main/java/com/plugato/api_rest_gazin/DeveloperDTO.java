package com.plugato.api_rest_gazin;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeveloperDTO {

    private String nome;
    private String sexo;
    private int idade;
    private String hobby;
    private Date datanascimento;

    public Developer transformToObject(){
        //return new Developer( nome, sexo, idade, hobby, datanascimento );
        return new Developer( nome, sexo, idade, hobby, datanascimento );
    }
}