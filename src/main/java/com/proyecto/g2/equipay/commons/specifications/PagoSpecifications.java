package com.proyecto.g2.equipay.commons.specifications;

import com.proyecto.g2.equipay.models.Pago;
import java.time.YearMonth;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class PagoSpecifications {
    
        public static Specification<Pago> onMonth(YearMonth yearMonth) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.between(root.get("fecha"),yearMonth.atDay(1), yearMonth.atEndOfMonth());
        };
    }

}
