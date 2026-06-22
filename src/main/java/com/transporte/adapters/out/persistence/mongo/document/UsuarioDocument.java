package com.transporte.adapters.out.persistence.mongo.document;

import com.transporte.domain.enums.Role;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UsuarioDocument {

    @Id
    private String id;

    @Indexed(unique = true)
    private String username;

    private String password;

    private Role role;

    private Boolean ativo;
}
