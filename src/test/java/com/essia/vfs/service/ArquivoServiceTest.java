package com.essia.vfs.service;

import com.essia.vfs.model.Arquivo;
import com.essia.vfs.repository.ArquivoRepository;
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
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class ArquivoServiceTest {

    @Mock
    ArquivoRepository arquivoRepository;

    @InjectMocks
    ArquivoService arquivoService;

    @Test
    void deveListarTodosOsArquivos(){
        ArquivoRepository arquivoRepository = Mockito.mock(ArquivoRepository.class);
        ArquivoService arquivoService = new ArquivoService(arquivoRepository);

        Arquivo arquivo1 = new Arquivo();
        arquivo1.setNome("Arquivo 1");

        Arquivo arquivo2 = new Arquivo();
        arquivo2.setNome("Arquivo 2");

        List<Arquivo> listaArquivos = Arrays.asList(arquivo1, arquivo2);
        Mockito.when(arquivoRepository.findAll()).thenReturn(listaArquivos);

        List<Arquivo> resultado = arquivoService.listarTodos();
        assertEquals(2, resultado.size());
    }

    @Test
    void deveChamarRepositoryUmaVezAoListarArquivos() {
        ArquivoRepository arquivoRepository = Mockito.mock(ArquivoRepository.class);
        ArquivoService arquivoService = new ArquivoService(arquivoRepository);

        // Arrange
        Mockito.when(arquivoRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        arquivoService.listarTodos();

        // Assert
        Mockito.verify(arquivoRepository, Mockito.times(1)).findAll();
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHouverArquivos() {
        // Arrange
        ArquivoRepository arquivoRepository = Mockito.mock(ArquivoRepository.class);
        ArquivoService arquivoService = new ArquivoService(arquivoRepository);
        Mockito.when(arquivoRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        List<Arquivo> resultado = arquivoService.listarTodos();

        // Assert
        assertEquals(0, resultado.size());
    }

    @Test
    void deveRetornarArquivoPorId() {
        // Arrange
        Long id = 1L;
        Arquivo arquivo = new Arquivo();
        arquivo.setId(id);
        arquivo.setNome("Arquivo 1");

        Mockito.when(arquivoService.obterPorId(id)).thenReturn(Optional.of(arquivo));

        // Act
        Optional<Arquivo> resultado = arquivoService.obterPorId(id);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals(arquivo, resultado.get());
    }


}
