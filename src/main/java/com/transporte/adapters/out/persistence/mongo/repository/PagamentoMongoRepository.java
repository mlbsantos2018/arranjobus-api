package com.transporte.adapters.out.persistence.mongo.repository;

import com.transporte.adapters.out.persistence.mongo.document.PagamentoDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagamentoMongoRepository extends MongoRepository<PagamentoDocument, String> {

    @Query("{ 'participacaoId': ?0 }")
    List<PagamentoDocument> findByParticipacaoId(String participacaoId);

    @Query("{ 'eventoId': ?0 }")
    List<PagamentoDocument> findByEventoId(String eventoId);
}
