package com.proyecto.g2.equipay.commons.specifications;

import com.proyecto.g2.equipay.models.Gasto;
import com.proyecto.g2.equipay.models.Grupo;
import com.proyecto.g2.equipay.models.Usuario;
import jakarta.persistence.criteria.*;
import java.time.YearMonth;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class GastoSpecifications {

    public static Specification<Gasto> hasUsuarioComoBeneficiado(String idUsuario) {
        return (root, query, criteriaBuilder) -> {
            Join<Usuario, Gasto> gastoContieneUsuario = root.join("beneficiados");
            return criteriaBuilder.equal(gastoContieneUsuario.get("correo"), idUsuario);
        };
    }

    public static Specification<Gasto> hasUsuarioEnGrupo(String idUsuario, Integer idGrupo) {
        return (root, query, criteriaBuilder) -> {
            Join<Gasto, Usuario> gastoContieneUsuario = root.join("beneficiados");
            Join<Grupo, Gasto> gastoContieneGrupo = gastoContieneUsuario.join("grupo");
            return criteriaBuilder.or(criteriaBuilder.equal(gastoContieneUsuario.get("beneficiado_id"), idUsuario), criteriaBuilder.equal(gastoContieneGrupo.get("grupo_id"), idGrupo));
        };
    }
    
        public static Specification<Gasto> onMonth(YearMonth yearMonth) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.between(root.get("fecha"), yearMonth.atDay(1), yearMonth.atEndOfMonth());
        };
    }

}
