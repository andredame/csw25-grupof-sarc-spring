package br.com.sarc.csw.modules.predio.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "predio")
@Data
public class Predio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    private String rua;

    private String numero;

    private String complemento;

    private String bairro;

    private String cidade;

    private String uf;

    private String cep;
}