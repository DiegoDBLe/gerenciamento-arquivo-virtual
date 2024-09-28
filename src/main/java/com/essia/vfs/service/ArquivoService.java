package com.essia.vfs.service;

import com.essia.vfs.model.Arquivo;
import com.essia.vfs.model.Diretorio;
import com.essia.vfs.repository.ArquivoRepository;
import com.essia.vfs.repository.DiretorioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArquivoService {

    private final ArquivoRepository arquivoRepository;

    @Autowired
    private DiretorioRepository diretorioRepository;

    @Autowired
    public ArquivoService(ArquivoRepository arquivoRepository) {
        this.arquivoRepository = arquivoRepository;
    }

    public List<Arquivo> listarTodos(){
        return arquivoRepository.findAll();
    }

    public Optional<Arquivo> obterPorId(Long id){
        return arquivoRepository.findById(id);
    }

    public Arquivo salvar(Arquivo arquivo, Long diretorioId){
        Optional<Diretorio> diretorioOptional = diretorioRepository.findById(diretorioId);
        if(diretorioOptional.isPresent()){
            Diretorio diretorio = diretorioOptional.get();
            arquivo.setDiretorio(diretorio);
            return arquivoRepository.save(arquivo);
        }else {
            throw new RuntimeException("Direório não encontrado");
        }
    }

    public Arquivo atualizar(Long id, String nome, Long diretorioId){
        Arquivo arquivo = arquivoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Arquivo não encontrado"));

        arquivo.setNome(nome);

        if (diretorioId != null){
            Diretorio diretorio = diretorioRepository.findById(diretorioId)
                    .orElseThrow(() -> new RuntimeException("Direotrio não encontrado"));
            arquivo.setDiretorio(diretorio);
        }

        return arquivoRepository.save(arquivo);
    }

    public void deletar(Long id){
        arquivoRepository.deleteById(id);
    }
}
