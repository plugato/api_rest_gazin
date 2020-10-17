package com.plugato.api_rest_gazin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import lombok.experimental.SuperBuilder;
//@AllArgsConstructor
@NoArgsConstructor
@Table
@Data
@Entity
@Getter
@SuperBuilder

public class Developer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nome;
    private char sexo;
    private int idade;
    private String hobby;
    private Date datanascimento;

    public Developer(String nome, char sexo, int idade, String hobby, Date datanascimento ) {
        this.nome = nome;
        this.sexo = sexo;
        this.idade = idade;
        this.hobby = hobby;
        this.datanascimento = datanascimento;
    }

}

