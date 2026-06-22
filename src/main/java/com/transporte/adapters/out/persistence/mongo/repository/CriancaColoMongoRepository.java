package com.transporte.adapters.out.persistence.mongo.repository;

import com.transporte.adapters.out.persistence.mongo.document.CriancaColoDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CriancaColoMongoRepository extends MongoRepository<CriancaColoDocument, String> {

    Optional<CriancaColoDocument> findByDocumento(String documento);
}
