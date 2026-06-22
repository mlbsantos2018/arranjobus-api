package com.transporte.adapters.out.persistence.mongo.repository;

import com.transporte.adapters.out.persistence.mongo.document.UsuarioDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioMongoRepository extends MongoRepository<UsuarioDocument, String> {

    Optional<UsuarioDocument> findByUsername(String username);

    boolean existsByUsername(String username);
}
