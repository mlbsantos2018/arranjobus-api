package com.transporte.adapters.out.persistence.mongo.document;

import com.transporte.domain.enums.TipoDocumento;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "people")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PessoaDocument {

    @Id
    private String id;

    @TextIndexed
    private String nomeCompleto;

    private TipoDocumento tipoDocumento;

    @Indexed(unique = true)
    private String numeroDocumento;

    private String telefone;

    @TextIndexed
    private String observacaoCurta;

    @TextIndexed
    private String observacaoDetalhada;

    private LocalDateTime criadoEm;

    private LocalDateTime atualizadoEm;
}
