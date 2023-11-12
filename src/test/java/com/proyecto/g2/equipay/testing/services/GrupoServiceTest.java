package com.proyecto.g2.equipay.testing.services;

import com.proyecto.g2.equipay.commons.dtos.grupo.GrupoAddDto;
import com.proyecto.g2.equipay.commons.dtos.usuario.UsuarioAddDto;
import com.proyecto.g2.equipay.commons.dtos.usuario.UsuarioDto;
import com.proyecto.g2.equipay.models.Grupo;
import com.proyecto.g2.equipay.models.Usuario;
import com.proyecto.g2.equipay.repositories.IGrupoRepository;
import com.proyecto.g2.equipay.repositories.IUsuarioRepository;
import com.proyecto.g2.equipay.services.GrupoService;
import com.proyecto.g2.equipay.testing.services.context.GrupoServiceTestContextConfig;
import jakarta.persistence.EntityExistsException;
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
        Mockito.when(usuarioRepo.findAll()).thenReturn(allUsuarios);

    }

    @Test
    public void buscarGrupoExistente_success() {
        Grupo grupoTest = Grupo.builder()
                .nombre("Grupo Test")
                .descripcion("Este es un grupo de prueba")
                .dueño(usuarioRepo.findById("alejandro.perez@test.com").orElseThrow())
                .build();
        Mockito.when(grupoRepo.findById(grupoTest.getId())).thenReturn(Optional.of(grupoTest));
        Mockito.when(grupoRepo.existsById(grupoTest.getId())).thenReturn(Boolean.TRUE);

        assertDoesNotThrow(() -> grupoSvc.buscarGrupo(grupoTest.getId()));
    }

    @Test
    public void crearNuevoGrupo_success() {
        GrupoAddDto dto = new GrupoAddDto("Juntos son dinamita", "Gastos en gral.", "alejandro.perez@mail.com");
        assertDoesNotThrow(() -> grupoSvc.crearGrupo(dto));
    }

    @Test
    public void agregarUsuarioAGrupo_success() {

    }

}
