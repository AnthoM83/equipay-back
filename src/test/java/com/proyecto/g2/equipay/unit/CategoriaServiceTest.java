package com.proyecto.g2.equipay.unit;

import com.proyecto.g2.equipay.models.Categoria;
import com.proyecto.g2.equipay.repositories.ICategoriaRepository;
import com.proyecto.g2.equipay.services.CategoriaService;
import com.proyecto.g2.equipay.unit.context.CategoriaServiceTestContextConfig;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@Import(CategoriaServiceTestContextConfig.class)
public class CategoriaServiceTest {

    @Autowired
    private CategoriaService categoriaSvc;

    @MockBean
    private ICategoriaRepository categoriaRepo;

    @BeforeEach
    public void init() {
        Categoria compras = Categoria.builder()
                .id(1)
                .nombre("Compras")
                .build();
        Categoria salidas = Categoria.builder()
                .nombre("Salidas")
                .build();
        Categoria viajes = Categoria.builder()
                .nombre("Viajes")
                .build();
        List<Categoria> allCategorias = Arrays.asList(compras, salidas, viajes);
        Mockito.when(categoriaRepo.findById(1)).thenReturn(Optional.of(compras));
        Mockito.when(categoriaRepo.existsById(1)).thenReturn(Boolean.TRUE);
        Mockito.when(categoriaRepo.findAll()).thenReturn(allCategorias);
    }

    @Test
    public void buscarCategoria_success() {
        Categoria found = categoriaSvc.buscarCategoria(1);
        assertThat(found.getId()).isEqualTo(1);
    }

    @Test
    public void buscarCategoria_categoriaNoExiste_exception() {
        assertThrows(NoSuchElementException.class, () -> {
            categoriaSvc.buscarCategoria(99);
        });
    }

    @Test
    public void agregarCategoria_success() {
        Categoria categoria = Categoria.builder().nombre("Otros").build();
        assertDoesNotThrow(() -> categoriaSvc.crearCategoria(categoria));
    }

    @Test
    public void listarCategorias_success() {
        List<Categoria> found = categoriaSvc.listarCategorias();
        assertThat(found).hasSize(3);
    }

    @Test
    public void listarCategorias_categoriasNoExisten_entoncesListaVacia() {
        Mockito.when(categoriaRepo.findAll()).thenReturn(new ArrayList<>());
        List<Categoria> found = categoriaSvc.listarCategorias();
        assertThat(found).hasSize(0);
    }

    @Test
    public void modificarCategoria_success() {
        Categoria categoria = Categoria.builder().nombre("Compras cambiado").build();
        assertDoesNotThrow(() -> categoriaSvc.modificarCategoria(1, categoria));
    }

    @Test
    public void eliminarCategoria_success() {
        assertDoesNotThrow(() -> categoriaSvc.eliminarCategoria(1));
    }

    @Test
    public void eliminarCategoria_categoriaNoExiste_exception() {
        assertThrows(NoSuchElementException.class, () -> {
            categoriaSvc.eliminarCategoria(99);
        });
    }

}
