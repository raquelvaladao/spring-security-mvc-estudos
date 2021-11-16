package com.mvc.project.models;

import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Nome não pode ser vazio.")
    private String name;

    @NotNull(message = "Preço não pode ser nulo.")
    @Digits(fraction = 2, integer = 10)
    @DecimalMin(value = "0.01", message = "Valor não pode ser menor que 0.01")
    private BigDecimal price;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date deliveryDate;

    @NotNull(message = "O estado de entrega não pode ser nulo.")
    private Boolean deliveryState;


}
