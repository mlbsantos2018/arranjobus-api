package com.transporte.adapters.out.persistence.mongo.repository;

import com.transporte.adapters.out.persistence.mongo.document.PessoaDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PessoaMongoRepository extends MongoRepository<PessoaDocument, String> {

    Optional<PessoaDocument> findByNumeroDocumento(String numeroDocumento);

    boolean existsByNumeroDocumento(String numeroDocumento);

    @Query("{ 'nomeCompleto': { '$regex': ?0, '$options': 'i' } }")
    List<PessoaDocument> searchByText(String texto);
}
