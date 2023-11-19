package com.proyecto.g2.equipay.unit;

import com.proyecto.g2.equipay.commons.dtos.usuario.UsuarioAddDto;
import com.proyecto.g2.equipay.commons.dtos.usuario.UsuarioDto;
import com.proyecto.g2.equipay.commons.dtos.usuario.UsuarioUpdateDto;
import com.proyecto.g2.equipay.models.Usuario;
import com.proyecto.g2.equipay.repositories.IUsuarioRepository;
import com.proyecto.g2.equipay.services.UsuarioService;
import com.proyecto.g2.equipay.unit.context.UsuarioServiceTestContextConfig;
import jakarta.persistence.EntityExistsException;
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
@Import(UsuarioServiceTestContextConfig.class)
public class UsuarioServiceTest {

    @Autowired
    private UsuarioService usuarioSvc;

    @Autowired
    PasswordEncoder encoder;

    @MockBean
    private IUsuarioRepository usuarioRepo;

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
    public void buscarUsuario_usuarioNoExiste_success() {
        String correo = "alejandro.perez@mail.com";
        UsuarioDto found = usuarioSvc.buscarUsuario(correo);
        assertThat(found.getCorreo()).isEqualTo(correo);
    }

    @Test
    public void buscarUsuario_usuarioNoExiste_exception() {
        assertThrows(NoSuchElementException.class, () -> {
            usuarioSvc.buscarUsuario("correo_inexistente");
        });
    }

    @Test
    public void agregarUsuario_success() {
        UsuarioAddDto sofia = new UsuarioAddDto("sofia.perez@mail.com", "Sofia", "Perez", "1234");
        assertDoesNotThrow(() -> usuarioSvc.crearUsuario(sofia));
    }

    @Test
    public void agregarUsuario_UsuarioYaExiste_exception() {
        UsuarioAddDto alejandro = new UsuarioAddDto("alejandro.perez@mail.com", "Alejandro", "Perez", "1234");
        assertThrows(EntityExistsException.class, () -> {
            usuarioSvc.crearUsuario(alejandro);
        });
    }

    @Test
    public void listarUsuarios_success() {
        List<UsuarioDto> found = usuarioSvc.listarUsuarios();
        assertThat(found).hasSize(3);
    }

    @Test
    public void listarUsuarios_usuarioNoExisten_success_listaVacia() {
        Mockito.when(usuarioRepo.findAll()).thenReturn(new ArrayList<>());
        List<UsuarioDto> found = usuarioSvc.listarUsuarios();
        assertThat(found).hasSize(0);
    }

    @Test
    public void bloquearUsuario_success() {
        assertDoesNotThrow(() -> usuarioSvc.bloquearUsuario("alejandro.perez@mail.com"));
    }

    @Test
    public void desbloquearUsuario_success() {
        assertDoesNotThrow(() -> usuarioSvc.desbloquearUsuario("alejandro.perez@mail.com"));
    }

    @Test
    public void modificarUsuario_success() {
        UsuarioUpdateDto dto = new UsuarioUpdateDto("Ale", "Pérez", "12345");
        assertDoesNotThrow(() -> usuarioSvc.modificarUsuario("alejandro.perez@mail.com", dto));
    }

    @Test
    public void eliminarUsuario_success() {
        assertDoesNotThrow(() -> usuarioSvc.eliminarUsuario("alejandro.perez@mail.com"));
    }

    @Test
    public void eliminarUsuario_UsuarioNoExiste_exception() {
        assertThrows(NoSuchElementException.class, () -> {
            usuarioSvc.eliminarUsuario("eduardo.martinez@mail.com");
        });
    }

}
