package com.essia.vfs.controller;

import com.essia.vfs.model.Arquivo;
import com.essia.vfs.service.ArquivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/arquivos")
public class ArquivoController {

    @Autowired
    private ArquivoService arquivoService;

    @GetMapping
    public List<Arquivo> listarTodos(){
        return arquivoService.listarTodos();
    }

    @GetMapping("/{id}")
    public Optional<Arquivo> obterPorId(@PathVariable Long id){
        return arquivoService.obterPorId(id);
    }

    @PostMapping
    public Arquivo criarArquivo(@RequestBody Map<String, Object> payLoad){
        String nome = (String) payLoad.get("nome");
        Long diretorioId = ((Number) payLoad.get("diretorioId")).longValue();

        Arquivo arquivo = new Arquivo();
        arquivo.setNome(nome);
        return arquivoService.salvar(arquivo, diretorioId);
    }

    @PutMapping("/{id}")
    public Arquivo atualizarArquivo(@PathVariable Long id, @RequestBody Map<String, Object> payLoad){
        String nome = (String) payLoad.get("nome");
        Long diretorioId = payLoad.containsKey("diretorio") ? ((Number) payLoad.get("diretorioId")).longValue() : null;

        return arquivoService.atualizar(id, nome, diretorioId);
    }

    @DeleteMapping("/{id}")
    public void deletarArquivo(@PathVariable Long id){
        arquivoService.deletar(id);
    }
}
