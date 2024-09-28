package com.essia.vfs.service;

import com.essia.vfs.exception.ResourceNotFoundException;
import com.essia.vfs.model.Arquivo;
import com.essia.vfs.model.Diretorio;
import com.essia.vfs.repository.ArquivoRepository;
import com.essia.vfs.repository.DiretorioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DiretorioService {

    private final DiretorioRepository diretorioRepository;

    @Autowired
    private  ArquivoRepository arquivoRepository;

    @Autowired
    public DiretorioService(DiretorioRepository diretorioRepository) {
        this.diretorioRepository = diretorioRepository;
    }

    public List<Diretorio> listarTodos(){
        return diretorioRepository.findAll().stream().filter(diretorio -> diretorio.getDiretorioPai() == null)
                .collect(Collectors.toList());
    }

    public Optional<Diretorio> obterPorId(Long id){
        return diretorioRepository.findById(id);
    }

    public Diretorio salvar(Diretorio diretorio) {
        return diretorioRepository.save(diretorio);
    }

    public void deletar(Long id){
         diretorioRepository.deleteById(id);
    }

    public void deletarArquivo(Long diretorioId, Long arquivoId) {
        Diretorio diretorio = diretorioRepository.findById(diretorioId)
                .orElseThrow(() -> new ResourceNotFoundException("Diretório não encontrado com id " + diretorioId));

        Arquivo arquivoParaDeletar = diretorio.getArquivos().stream()
                .filter(arquivo -> arquivo.getId().equals(arquivoId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Arquivo não encontrado com id " + arquivoId));

        diretorio.getArquivos().remove(arquivoParaDeletar);
        arquivoRepository.delete(arquivoParaDeletar);  // Se você tiver um repositório de Arquivos
        diretorioRepository.save(diretorio);  // Salva o diretório sem o arquivo
    }


    public Diretorio adicionarSubDiretorio(Long id, Diretorio subDiretorio) {
        Diretorio diretorioPai = diretorioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Diretório não encontrado com id " + id));

        subDiretorio.setDiretorioPai(diretorioPai);
        diretorioPai.getSubDiretorios().add(subDiretorio);

        diretorioRepository.save(diretorioPai);
        return subDiretorio;
    }

    public Arquivo adicionarArquivo(Long diretorioId, Arquivo arquivo) {
        Diretorio diretorio = diretorioRepository.findById(diretorioId)
                .orElseThrow(() -> new ResourceNotFoundException("Diretório não encontrado com id " + diretorioId));

        arquivo.setDiretorio(diretorio);
        diretorio.getArquivos().add(arquivo);

        diretorioRepository.save(diretorio);
        return arquivo;
    }

    public Diretorio atualizar(Long id, Diretorio diretorioAtualizado) {
        Diretorio diretorioExistente = obterPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Diretório não encontrado com ID: " + id));

        diretorioExistente.setNome(diretorioAtualizado.getNome());

        return salvar(diretorioExistente);
    }

    public Diretorio atualizarSubDiretorio(Long id, Long subId, Diretorio subDiretorioAtualizado) {

        Optional<Diretorio> diretorioPaiOpt = diretorioRepository.findById(id);
        if (diretorioPaiOpt.isPresent()) {
            Diretorio diretorioPai = diretorioPaiOpt.get();


            Optional<Diretorio> subDiretorioOpt = diretorioPai.getSubDiretorios().stream()
                    .filter(subDiretorio -> subDiretorio.getId().equals(subId))
                    .findFirst();

            if (subDiretorioOpt.isPresent()) {
                Diretorio subDiretorio = subDiretorioOpt.get();

                subDiretorio.setNome(subDiretorioAtualizado.getNome());

                return diretorioRepository.save(diretorioPai);
            } else {
                throw new RuntimeException("Subdiretório não encontrado");
            }
        } else {
            throw new RuntimeException("Diretório pai não encontrado");
        }
    }

    public void deletarSubDiretorio(Long id, Long subId) {
        // Recupera o diretório pai do repositório
        Diretorio diretorioPai = diretorioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Diretório pai não encontrado"));

        // Obtém a lista de subdiretórios como uma lista imutável
        List<Diretorio> subDiretoriosImutaveis = diretorioPai.getSubDiretorios();

        // Converte a lista imutável em uma lista mutável
        List<Diretorio> subDiretoriosMutaveis = new ArrayList<>(subDiretoriosImutaveis);

        // Filtra o subdiretório que deseja remover
        Diretorio subDiretorioParaDeletar = subDiretoriosMutaveis.stream()
                .filter(subDiretorio -> subDiretorio.getId().equals(subId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Subdiretório não encontrado"));

        // Remove o subdiretório da lista mutável
        subDiretoriosMutaveis.remove(subDiretorioParaDeletar);

        // Atualiza o diretório pai com a lista de subdiretórios modificada
        diretorioPai.setSubDiretorios(subDiretoriosMutaveis);

        // Salva as alterações no repositório
        diretorioRepository.save(diretorioPai);
    }

    public Arquivo adicionarArquivoNoSubDiretorio(Long id, Long subId, Arquivo arquivo) {
        // Primeiro, busca o diretório pai (nesse caso, 'dir2' com ID = id)
        Diretorio diretorioPai = diretorioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Diretório pai não encontrado"));

        // Depois, busca o subdiretório (nesse caso, 'subdir1' com ID = subId)
        Diretorio subDiretorio = diretorioPai.getSubDiretorios().stream()
                .filter(sub -> sub.getId().equals(subId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Subdiretório não encontrado"));

        // Associa o arquivo ao subdiretório
        subDiretorio.getArquivos().add(arquivo);
        arquivo.setDiretorio(subDiretorio); // Aqui é importante associar o arquivo ao subdiretório

        // Salva a hierarquia
        diretorioRepository.save(diretorioPai); // Salva o diretório pai, isso vai persistir o subdiretório e o arquivo
        return arquivo;
    }
}
