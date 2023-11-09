package com.proyecto.g2.equipay.models;

import com.proyecto.g2.equipay.commons.enums.EstadoUsuario;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Entity
public class Usuario
        extends UsuarioBase {

    // Propiedades
    private EstadoUsuario estadoUsuario;
    private LocalDate fechaCreacion;

    // Asociaciones
    @OneToMany(mappedBy = "dueño", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Grupo> dueñoDe;

    @ManyToMany(mappedBy = "miembros")
    private List<Grupo> miembroDe;

    @OneToMany(mappedBy = "cubiertoPor")
    private List<Gasto> gastosCubiertos;

    @ManyToMany(mappedBy = "beneficiados")
    private List<Gasto> beneficiadoEn;

    @OneToMany(mappedBy = "realiza")
    private List<Pago> pagosRealizados;

    @OneToMany(mappedBy = "recibe")
    private List<Pago> pagosRecibidos;

    @OneToMany(mappedBy = "usuario")
    private List<Balance> balances;

    public static String getRoles() {
        return "Usuario";
    }
    
}
