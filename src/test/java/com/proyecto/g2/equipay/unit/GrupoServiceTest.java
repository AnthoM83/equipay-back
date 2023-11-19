package com.proyecto.g2.equipay.unit;

import com.proyecto.g2.equipay.commons.dtos.grupo.GrupoAddDto;
import com.proyecto.g2.equipay.commons.dtos.grupo.GrupoDto;
import com.proyecto.g2.equipay.models.Grupo;
import com.proyecto.g2.equipay.models.Usuario;
import com.proyecto.g2.equipay.repositories.IGrupoRepository;
import com.proyecto.g2.equipay.repositories.IUsuarioRepository;
import com.proyecto.g2.equipay.services.GrupoService;
import com.proyecto.g2.equipay.unit.context.GrupoServiceTestContextConfig;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@Import(GrupoServiceTestContextConfig.class)
public class GrupoServiceTest {

    @Autowired
    private GrupoService grupoSvc;

    @Autowired
    PasswordEncoder encoder;

    @MockBean
    private IUsuarioRepository usuarioRepo;

    @MockBean
    private IGrupoRepository grupoRepo;

    @BeforeEach
    public void init() {
        Usuario alejandro = Usuario.builder()
                .correo("alejandro.perez@mail.com")
                .nombre("Alejandro")
                .apellido("Perez")
                .password(encoder.encode("1234"))
                .build();
        Usuario veronica = Usuario.builder()
                .correo("veronica.lopez@mail.com")
                .nombre("Veronica")
                .apellido("Lopez")
                .password(encoder.encode("1234"))
                .build();
        Usuario eduardo = Usuario.builder()
                .correo("eduardo.martinez@mail.com")
                .nombre("Eduardo")
                .apellido("Martinez")
                .password(encoder.encode("1234"))
                .build();
        List<Usuario> allUsuarios = Arrays.asList(alejandro, veronica, eduardo);
        Mockito.when(usuarioRepo.findById(alejandro.getCorreo())).thenReturn(Optional.of(alejandro));
        Mockito.when(usuarioRepo.existsById(alejandro.getCorreo())).thenReturn(Boolean.TRUE);
        Mockito.when(usuarioRepo.findById(eduardo.getCorreo())).thenReturn(Optional.of(eduardo));
        Mockito.when(usuarioRepo.existsById(eduardo.getCorreo())).thenReturn(Boolean.TRUE);
        Mockito.when(usuarioRepo.findAll()).thenReturn(allUsuarios);

        Grupo grupoTest = Grupo.builder()
                .nombre("Grupo Test")
                .descripcion("Este es un grupo de prueba")
                .dueño(alejandro)
                .miembros(new ArrayList<>())
                .build();
        List<Grupo> allGrupos = Arrays.asList(grupoTest);
        Mockito.when(grupoRepo.findById(1)).thenReturn(Optional.of(grupoTest));
        Mockito.when(grupoRepo.existsById(1)).thenReturn(Boolean.TRUE);
        Mockito.when(grupoRepo.findAll()).thenReturn(allGrupos);
    }

    @Test
    public void buscarGrupo_success() {
        assertDoesNotThrow(() -> grupoSvc.buscarGrupo(1));
    }

    @Test
    public void buscarGrupo_grupoNoExiste_exception() {
        assertThrows(NoSuchElementException.class, () -> {
            grupoSvc.buscarGrupo(2);
        });
    }

    @Test
    public void listarGrupos_success() {
        List<GrupoDto> found = grupoSvc.listarGrupos();
        assertThat(found).hasSize(1);
    }

    @Test
    public void listarGrupos_gruposNoExisten_success_listaVacia() {
        Mockito.when(grupoRepo.findAll()).thenReturn(new ArrayList<>());
        List<GrupoDto> found = grupoSvc.listarGrupos();
        assertThat(found).hasSize(0);
    }

    @Test
    public void crearGrupo_success() {
        GrupoAddDto dto = new GrupoAddDto("Juntos son dinamita", "Gastos en gral.", "alejandro.perez@mail.com");
        assertDoesNotThrow(() -> grupoSvc.crearGrupo(dto));
    }

    @Test
    public void agregarUsuarioAGrupo_success() {
        assertDoesNotThrow(() -> grupoSvc.agregarUsuarioAGrupo(1, "eduardo.martinez@mail.com"));
    }

    @Test
    public void agregarUsuarioAGrupo_usuarioYaEsMiembro_exception() {
        assertThrows(IllegalArgumentException.class, () -> {
            grupoSvc.agregarUsuarioAGrupo(1, "alejandro.perez@mail.com");
        });
    }

    @Test
    public void eliminarGrupo_success() {
        assertDoesNotThrow(() -> grupoSvc.eliminarGrupo(1));
    }

    @Test
    public void eliminarGrupo_grupoNoExiste_exception() {
        assertThrows(NoSuchElementException.class, () -> {
            grupoSvc.eliminarGrupo(2);
        });
    }

}
