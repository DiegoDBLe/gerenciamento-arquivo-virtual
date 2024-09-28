package com.essia.vfs.service;

import com.essia.vfs.model.Diretorio;
import com.essia.vfs.repository.ArquivoRepository;
import com.essia.vfs.repository.DiretorioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class DiretorioServiceTest {

    @Mock
    private DiretorioRepository diretorioRepository;

    @InjectMocks
    private DiretorioService diretorioService;

    @Test
    void deveCriarDiretorio() {
        Diretorio novoDiretorio = new Diretorio();
        novoDiretorio.setNome("novoDiretorio");

        Mockito.when(diretorioRepository.save(Mockito.any(Diretorio.class))).thenReturn(novoDiretorio);

        Diretorio diretorioCriado = diretorioService.salvar(novoDiretorio);

        assertEquals("novoDiretorio", diretorioCriado.getNome());
        Mockito.verify(diretorioRepository, Mockito.times(1)).save(novoDiretorio);
    }

    @Test
    void deveListarTodosOsDiretorios(){
        DiretorioRepository diretorioRepository = Mockito.mock(DiretorioRepository.class);
        DiretorioService diretorioService = new DiretorioService(diretorioRepository);

        Diretorio diretorio1 = new Diretorio();
        diretorio1.setNome("Direotrio 1");

        Diretorio diretorio2 = new Diretorio();
        diretorio2.setNome("Direotrio 2");

        List<Diretorio> listaDiretorios = Arrays.asList(diretorio1, diretorio2);
        Mockito.when(diretorioRepository.findAll()).thenReturn(listaDiretorios);

        List<Diretorio> resultado = diretorioService.listarTodos();
        assertEquals(2, resultado.size());
    }

    @Test
    void deveDeletarSubDiretorioComSucesso() {
        // Arrange
        Long idDiretorio = 1L;
        Long idSubDiretorio = 2L;

        Diretorio diretorioPai = new Diretorio();
        diretorioPai.setId(idDiretorio);
        Diretorio subDiretorio = new Diretorio();
        subDiretorio.setId(idSubDiretorio);
        diretorioPai.setSubDiretorios(List.of(subDiretorio));

        Mockito.when(diretorioRepository.findById(idDiretorio)).thenReturn(Optional.of(diretorioPai));

        // Act
        diretorioService.deletarSubDiretorio(idDiretorio, idSubDiretorio);

        // Assert
        Mockito.verify(diretorioRepository).save(diretorioPai);
        Assertions.assertTrue(diretorioPai.getSubDiretorios().isEmpty());
    }

    @Test
    void deveLancarExcecaoQuandoSubDiretorioNaoEncontrado() {
        // Arrange
        Long idDiretorio = 1L;
        Long idSubDiretorio = 2L;

        Diretorio diretorioPai = new Diretorio();
        diretorioPai.setId(idDiretorio);
        diretorioPai.setSubDiretorios(new ArrayList<>());

        Mockito.when(diretorioRepository.findById(idDiretorio)).thenReturn(Optional.of(diretorioPai));

        // Act & Assert
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            diretorioService.deletarSubDiretorio(idDiretorio, idSubDiretorio);
        });

        assertEquals("Subdiretório não encontrado", thrown.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoDiretorioPaiNaoEncontrado() {
        // Arrange
        Long idDiretorio = 1L;
        Long idSubDiretorio = 2L;

        Mockito.when(diretorioRepository.findById(idDiretorio)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            diretorioService.deletarSubDiretorio(idDiretorio, idSubDiretorio);
        });

        Assertions.assertEquals("Diretório pai não encontrado", thrown.getMessage());
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHouverDiretorios() {
        // Arrange
        Mockito.when(diretorioRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        List<Diretorio> resultado = diretorioService.listarTodos();

        // Assert
        assertEquals(0, resultado.size());
    }

    @Test
    void deveDeletarSubDiretorioEspecificoDeUmDiretorioComVariosSubDiretorios() {
        // Arrange
        Long idDiretorio = 1L;
        Long idSubDiretorioParaDeletar = 2L;

        Diretorio diretorioPai = new Diretorio();
        diretorioPai.setId(idDiretorio);
        Diretorio subDiretorio1 = new Diretorio();
        subDiretorio1.setId(1L);
        Diretorio subDiretorio2 = new Diretorio();
        subDiretorio2.setId(idSubDiretorioParaDeletar);
        diretorioPai.setSubDiretorios(Arrays.asList(subDiretorio1, subDiretorio2));

        Mockito.when(diretorioRepository.findById(idDiretorio)).thenReturn(Optional.of(diretorioPai));

        // Act
        diretorioService.deletarSubDiretorio(idDiretorio, idSubDiretorioParaDeletar);

        // Assert
        Mockito.verify(diretorioRepository).save(diretorioPai);
        Assertions.assertEquals(1, diretorioPai.getSubDiretorios().size());
        Assertions.assertFalse(diretorioPai.getSubDiretorios().contains(subDiretorio2));
    }

    @Test
    void deveLancarExcecaoQuandoTentarDeletarSubdiretorioDeUmDiretorioVazio() {
        // Arrange
        Long idDiretorio = 1L;
        Long idSubDiretorio = 2L;

        Diretorio diretorioPai = new Diretorio();
        diretorioPai.setId(idDiretorio);
        diretorioPai.setSubDiretorios(new ArrayList<>());

        Mockito.when(diretorioRepository.findById(idDiretorio)).thenReturn(Optional.of(diretorioPai));

        // Act & Assert
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            diretorioService.deletarSubDiretorio(idDiretorio, idSubDiretorio);
        });

        assertEquals("Subdiretório não encontrado", thrown.getMessage());
    }

}
