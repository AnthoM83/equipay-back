package com.proyecto.g2.equipay.unit;

import com.proyecto.g2.equipay.commons.dtos.admin.AdminAddDto;
import com.proyecto.g2.equipay.commons.dtos.admin.AdminDto;
import com.proyecto.g2.equipay.commons.dtos.admin.AdminUpdateDto;
import com.proyecto.g2.equipay.models.Admin;
import com.proyecto.g2.equipay.repositories.IAdminRepository;
import com.proyecto.g2.equipay.services.AdminService;
import com.proyecto.g2.equipay.unit.context.AdminServiceTestContextConfig;
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
@Import(AdminServiceTestContextConfig.class)
public class AdminServiceTest {

    @Autowired
    private AdminService adminSvc;

    @Autowired
    PasswordEncoder encoder;

    @MockBean
    private IAdminRepository adminRepo;

    @BeforeEach
    public void init() {
        Admin alejandro = Admin.builder()
                .correo("alejandro.perez@mail.com")
                .nombre("Alejandro")
                .apellido("Perez")
                .password(encoder.encode("1234"))
                .build();
        Admin veronica = Admin.builder()
                .correo("veronica.lopez@mail.com")
                .nombre("Veronica")
                .apellido("Lopez")
                .password(encoder.encode("1234"))
                .build();
        Admin eduardo = Admin.builder()
                .correo("eduardo.martinez@mail.com")
                .nombre("Eduardo")
                .apellido("Martinez")
                .password(encoder.encode("1234"))
                .build();
        List<Admin> allAdmins = Arrays.asList(alejandro, veronica, eduardo);
        Mockito.when(adminRepo.findById(alejandro.getCorreo())).thenReturn(Optional.of(alejandro));
        Mockito.when(adminRepo.existsById(alejandro.getCorreo())).thenReturn(Boolean.TRUE);
        Mockito.when(adminRepo.findAll()).thenReturn(allAdmins);
    }

    @Test
    public void buscarAdmin_success() {
        String correo = "alejandro.perez@mail.com";
        AdminDto found = adminSvc.buscarAdmin(correo);
        assertThat(found.getCorreo()).isEqualTo(correo);
    }

    @Test
    public void buscarAdmin_adminNoExiste_exception() {
        assertThrows(NoSuchElementException.class, () -> {
            adminSvc.buscarAdmin("correo_inexistente");
        });
    }

    @Test
    public void agregarAdmin_success() {
        AdminAddDto sofia = new AdminAddDto("sofia.perez@mail.com", "Sofia", "Perez", "1234");
        assertDoesNotThrow(() -> adminSvc.crearAdmin(sofia));
    }

    @Test
    public void agregarAdmin_adminYaExiste_exception() {
        AdminAddDto alejandro = new AdminAddDto("alejandro.perez@mail.com", "Alejandro", "Perez", "1234");
        assertThrows(EntityExistsException.class, () -> {
            adminSvc.crearAdmin(alejandro);
        });
    }

    @Test
    public void listarAdmin_success() {
        List<AdminDto> found = adminSvc.listarAdmins();
        assertThat(found).hasSize(3);
    }

    @Test
    public void listarAdmin_adminsNoExisten_success_listaVacia() {
        Mockito.when(adminRepo.findAll()).thenReturn(new ArrayList<>());
        List<AdminDto> found = adminSvc.listarAdmins();
        assertThat(found).hasSize(0);
    }

    @Test
    public void modificarAdmin_success() {
        AdminUpdateDto dto = new AdminUpdateDto("Ale", "Pérez", "12345");
        assertDoesNotThrow(() -> adminSvc.modificarAdmin("alejandro.perez@mail.com", dto));
    }

    @Test
    public void eliminarAdmin_success() {
        assertDoesNotThrow(() -> adminSvc.eliminarAdmin("alejandro.perez@mail.com"));
    }

    @Test
    public void eliminarAdmin_adminNoExiste_exception() {
        assertThrows(NoSuchElementException.class, () -> {
            adminSvc.eliminarAdmin("eduardo.martinez@mail.com");
        });
    }

}
