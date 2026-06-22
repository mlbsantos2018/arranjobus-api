package com.transporte.adapters.out.persistence.mongo.repository;

import com.transporte.adapters.out.persistence.mongo.document.EventoDocument;
import com.transporte.domain.enums.StatusEvento;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventoMongoRepository extends MongoRepository<EventoDocument, String> {

    List<EventoDocument> findByStatus(StatusEvento status);
}
