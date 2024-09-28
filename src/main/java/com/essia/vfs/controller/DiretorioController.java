package com.essia.vfs.controller;

import com.essia.vfs.exception.ResourceNotFoundException;
import com.essia.vfs.model.Arquivo;
import com.essia.vfs.model.Diretorio;
import com.essia.vfs.service.DiretorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/diretorios")
public class DiretorioController {

    @Autowired
    private DiretorioService diretorioService;

    @GetMapping
    public List<Diretorio> listarTodos(){
        return diretorioService.listarTodos();
    }

    @GetMapping("/{id}")
    public Optional<Diretorio> obterId(@PathVariable Long id){
        return diretorioService.obterPorId(id);
    }

    @PostMapping
    public Diretorio criarDiretorio(@RequestBody Diretorio diretorio){
        return diretorioService.salvar(diretorio);
    }

    @PostMapping("/{id}/subdiretorios")
    public ResponseEntity<Diretorio> adicionarSubDiretorio(@PathVariable Long id, @RequestBody Diretorio subDiretorio) {
        Diretorio novoSubDiretorio = diretorioService.adicionarSubDiretorio(id, subDiretorio);
        return ResponseEntity.ok(novoSubDiretorio);
    }

    @PostMapping("/{id}/subdiretorios/{subId}/arquivos")
    public ResponseEntity<Arquivo> adicionarArquivoNoSubDiretorio(@PathVariable Long id, @PathVariable Long subId, @RequestBody Arquivo arquivo) {
        Arquivo novoArquivo = diretorioService.adicionarArquivoNoSubDiretorio(id, subId, arquivo);
        return ResponseEntity.ok(novoArquivo);
    }

    @PostMapping("/{id}/arquivos")
    public ResponseEntity<Arquivo> adicionarArquivo(@PathVariable Long id, @RequestBody Arquivo arquivo) {
        Arquivo novoArquivo = diretorioService.adicionarArquivo(id, arquivo);
        return ResponseEntity.ok(novoArquivo);
    }

    @DeleteMapping("{id}")
    public void deletarDiretorio(@PathVariable Long id){
        diretorioService.deletar(id);
    }

    @DeleteMapping("/{diretorioId}/arquivos/{arquivoId}")
    public ResponseEntity<Void> deletarArquivo(@PathVariable Long diretorioId, @PathVariable Long arquivoId) {
        try {
            diretorioService.deletarArquivo(diretorioId, arquivoId);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Diretorio> atualizarDiretorio(@PathVariable Long id, @RequestBody Diretorio diretorioAtualizado) {
        Diretorio diretorio = diretorioService.atualizar(id, diretorioAtualizado);
        return ResponseEntity.ok(diretorio);
    }
    @PutMapping("/{id}/subdiretorios/{subId}")
    public ResponseEntity<Diretorio> atualizarSubDiretorio(@PathVariable Long id,@PathVariable Long subId,@RequestBody Diretorio subDiretorioAtualizado) {
        Diretorio subDiretorio = diretorioService.atualizarSubDiretorio(id, subId, subDiretorioAtualizado);
        return ResponseEntity.ok(subDiretorio);
    }

    @DeleteMapping("/{id}/subdiretorios/{subId}")
    public ResponseEntity<Void> deletarSubDiretorio(@PathVariable Long id, @PathVariable Long subId) {
        try {
            diretorioService.deletarSubDiretorio(id, subId);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
