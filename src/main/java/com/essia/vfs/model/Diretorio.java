package com.essia.vfs.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Diretorio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @OneToMany(mappedBy = "diretorio", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Arquivo> arquivos;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Diretorio> subDiretorios;

    @ManyToOne
    @JoinColumn(name = "diretorio_pai_id")
    @JsonBackReference
    private Diretorio diretorioPai;


}
