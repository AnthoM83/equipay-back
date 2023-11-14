package com.proyecto.g2.equipay.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Grupo {

    // Propiedades
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String descripcion;
    private LocalDate fechaCreacion;
    private String codigo;

    // Asociaciones
    @ManyToOne
    @JoinColumn(name = "dueño")
    private Usuario dueño;

    @ManyToMany
    @JoinTable(name = "miembro_grupo",
            joinColumns = @JoinColumn(name = "id_grupo"),
            inverseJoinColumns = @JoinColumn(name = "id_miembro"))
    private List<Usuario> miembros;

    @OneToMany(mappedBy = "grupo")
    private List<Gasto> gasto;

    @OneToMany(mappedBy = "grupo")
    private List<Pago> pago;

    @OneToMany(mappedBy = "grupo")
    private List<Balance> balances;

}
