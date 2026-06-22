package com.transporte.adapters.out.persistence.mongo.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "lap_children")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CriancaColoDocument {

    @Id
    private String id;

    private String nomeCompleto;

    @Indexed(unique = true)
    private String documento;

    private LocalDate dataNascimento;
}
