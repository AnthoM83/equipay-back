package com.proyecto.g2.equipay.commons.helpers;

import com.proyecto.g2.equipay.commons.dtos.admin.AdminAddDto;
import com.proyecto.g2.equipay.commons.dtos.gasto.GastoAddDto;
import com.proyecto.g2.equipay.commons.dtos.grupo.GrupoAddDto;
import com.proyecto.g2.equipay.commons.dtos.pago.PagoAddDto;
import com.proyecto.g2.equipay.commons.dtos.usuario.UsuarioAddDto;
import com.proyecto.g2.equipay.models.Categoria;
import com.proyecto.g2.equipay.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;

@Component
public class DataInitializer {

    @Autowired
    UsuarioService svcUsuario;
    @Autowired
    AdminService svcAdmin;
    @Autowired
    GrupoService svcGrupo;
    @Autowired
    GastoService svcGasto;
    @Autowired
    PagoService svcPago;
    @Autowired
    CategoriaService svcCategoria;
    @Autowired
    NotificationService svcNotificacion;

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (svcCategoria.existeCategoria(1)) {
            return;
        }

        // Categorías
        svcCategoria.crearCategoria(new Categoria(1, "Comida"));
        svcCategoria.crearCategoria(new Categoria(2, "Supermercado"));
        svcCategoria.crearCategoria(new Categoria(3, "Transporte"));
        svcCategoria.crearCategoria(new Categoria(4, "Viaje"));
        svcCategoria.crearCategoria(new Categoria(5, "Combustible"));

        // Admins
        svcAdmin.crearAdmin(new AdminAddDto("admin@equipay.com", "admin", null, "1234"));
        svcAdmin.crearAdmin(new AdminAddDto("admin2@equipay.com", "admin2", null, "1234"));
        svcAdmin.crearAdmin(new AdminAddDto("admin3@equipay.com", "admin3", null, "1234"));
        svcAdmin.crearAdmin(new AdminAddDto("admin-con-apellido@equipay.com", "admin", "apellidoadmin", "1234"));

        // Usuario
        //Seguridad contraseña, se genera la contraseña arriba para todos igual, pero se cifra en cada instancia nueva para que genere un salt aleatorio
        svcUsuario.crearUsuario(new UsuarioAddDto("sofia.perez@mail.com", "Sofia", "Perez", "1234"));
        svcUsuario.crearUsuario(new UsuarioAddDto("alejandro.perez@mail.com", "Alejandro", "Perez", "1234"));
        svcUsuario.crearUsuario(new UsuarioAddDto("veronica.lopez@mail.com", "Veronica", "Lopez", "1234"));
        svcUsuario.crearUsuario(new UsuarioAddDto("eduardo.martinez@mail.com", "Eduardo", "Martinez", "1234"));
        svcUsuario.crearUsuario(new UsuarioAddDto("agustina.montes@mail.com", "Agustina", "Montes", "1234"));
        svcUsuario.crearUsuario(new UsuarioAddDto("matias.sanchez@mail.com", "Matias", "Sanchez", "1234"));
        svcUsuario.crearUsuario(new UsuarioAddDto("cecilia.estrella@mail.com", "Cecilia", "Estrella", "1234"));
        svcUsuario.crearUsuario(new UsuarioAddDto("leonardo.villa@mail.com", "Leonardo", "Villa", "1234"));
        svcUsuario.crearUsuario(new UsuarioAddDto("victoria.montano@mail.com", "Victoria", "Montano", "1234"));
        svcUsuario.crearUsuario(new UsuarioAddDto("lucas.fagundez@mail.com", "Lucas", "Fagundez", "1234"));
        svcUsuario.crearUsuario(new UsuarioAddDto("agustin@mail.com", "Agustin", null, "1234"));
        svcUsuario.crearUsuario(new UsuarioAddDto("brian@mail.com", "Brian", null, "1234"));
        svcUsuario.crearUsuario(new UsuarioAddDto("lucia@mail.com", "Lucia", null, "1234"));
        svcUsuario.crearUsuario(new UsuarioAddDto("leandro@mail.com", "Leandro", null, "1234"));
        svcUsuario.crearUsuario(new UsuarioAddDto("romina@mail.com", "Romina", null, "1234"));
        svcUsuario.crearUsuario(new UsuarioAddDto("diego@mail.com", "Diego", null, "1234"));
        svcUsuario.crearUsuario(new UsuarioAddDto("sara@mail.com", "Sara", null, "1234"));
        svcUsuario.crearUsuario(new UsuarioAddDto("ignacio@mail.com", "Ignacio", null, "1234"));
        svcUsuario.crearUsuario(new UsuarioAddDto("maria@mail.com", "Maria", null, "1234"));
        svcUsuario.crearUsuario(new UsuarioAddDto("juan.manuel@mail.com", "Juan Manuel", null, "1234"));

        // Grupos
        svcGrupo.crearGrupo(new GrupoAddDto("Juntos son dinamita", "Gastos en gral.", "alejandro.perez@mail.com"));
        svcGrupo.crearGrupo(new GrupoAddDto("Paseos", "Grupo para paseos a cualquier lado", "sofia.perez@mail.com"));
        svcGrupo.crearGrupo(new GrupoAddDto("Juntadas", "Gastos de juntada", "sofia.perez@mail.com"));
        svcGrupo.crearGrupo(new GrupoAddDto("Juntos son dinamita 2", "Gastos en gral.", "matias.sanchez@mail.com"));

        // Añadir miembros a grupos
        svcGrupo.agregarUsuarioAGrupo(1, "eduardo.martinez@mail.com");
        svcGrupo.agregarUsuarioAGrupo(1, "matias.sanchez@mail.com");
        svcGrupo.agregarUsuarioAGrupo(1, "leonardo.villa@mail.com");
        svcGrupo.agregarUsuarioAGrupo(2, "veronica.lopez@mail.com");
        svcGrupo.agregarUsuarioAGrupo(2, "agustina.montes@mail.com");
        svcGrupo.agregarUsuarioAGrupo(3, "diego@mail.com");
        svcGrupo.agregarUsuarioAGrupo(3, "sara@mail.com");
        svcGrupo.agregarUsuarioAGrupo(3, "ignacio@mail.com");
        svcGrupo.agregarUsuarioAGrupo(3, "maria@mail.com");
        svcGrupo.agregarUsuarioAGrupo(4, "alejandro.perez@mail.com");

        // Gastos
        svcGasto.crearGasto(new GastoAddDto(1300.50, "UYU", "La Pasiva", LocalDate.parse("2023-10-20"), 1, "alejandro.perez@mail.com", Arrays.asList("eduardo.martinez@mail.com", "matias.sanchez@mail.com"), 1));
        svcGasto.crearGasto(new GastoAddDto(800.0, "UYU", "Nafta", LocalDate.parse("2023-10-20"), 1, "alejandro.perez@mail.com", Arrays.asList("eduardo.martinez@mail.com", "matias.sanchez@mail.com"), 1));
        svcGasto.crearGasto(new GastoAddDto(5460.30, "USD", "Pasajes a Noruega", LocalDate.parse("2023-10-20"), 1, "alejandro.perez@mail.com", Arrays.asList("eduardo.martinez@mail.com", "matias.sanchez@mail.com", "leonardo.villa@mail.com"), 1));
        svcGasto.crearGasto(new GastoAddDto(1800.15, "UYU", "Rudy Burger", LocalDate.parse("2023-10-10"), 1, "eduardo.martinez@mail.com", Arrays.asList("alejandro.perez@mail.com", "matias.sanchez@mail.com", "leonardo.villa@mail.com"), 1));

        //Notificacion
        svcNotificacion.crearNotificacion("aaa1", "Esto es una notificacion de prueba", "ok", "agustina.montes@mail.com", "sofia");
        // Pagos
        svcPago.crearPago(new PagoAddDto(1150.21, "UYU", LocalDate.parse("2023-10-21"), 1, "matias.sanchez@mail.com", "alejandro.perez@mail.com"));
    }
}
