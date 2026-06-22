package com.transporte.adapters.out.persistence.mongo.repository;

import com.transporte.adapters.out.persistence.mongo.document.PagamentoDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PagamentoMongoRepository extends MongoRepository<PagamentoDocument, String> {

    Optional<PagamentoDocument> findByParticipacaoId(String participacaoId);
}
