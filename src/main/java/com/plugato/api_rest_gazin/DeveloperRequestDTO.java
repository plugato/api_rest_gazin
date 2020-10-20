package com.plugato.api_rest_gazin;

import lombok.*;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Component
public class DeveloperRequestDTO {

    private String nome;
    private String sexo;
    private int idade;
    private String hobby;
    private Date datanascimento;

    public Developer transformToObject() {
        return new Developer(nome, sexo, idade, hobby, datanascimento);
    }
}