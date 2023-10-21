package com.proyecto.g2.equipay.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
