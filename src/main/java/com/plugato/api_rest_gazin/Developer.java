package com.plugato.api_rest_gazin;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.sql.Date;

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
    private String sexo;
    private int idade;
    private String hobby;
    private Date datanascimento;

    public Developer(String nome, String sexo, int idade, String hobby, Date datanascimento) {
        this.nome = nome;
        this.sexo = sexo;
        this.idade = idade;
        this.hobby = hobby;
        this.datanascimento = datanascimento;
    }

}

