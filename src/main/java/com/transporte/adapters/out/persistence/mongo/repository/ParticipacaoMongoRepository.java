package com.transporte.adapters.out.persistence.mongo.repository;

import com.transporte.adapters.out.persistence.mongo.document.ParticipacaoDocument;
import com.transporte.domain.enums.DiaEvento;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipacaoMongoRepository
                extends MongoRepository<ParticipacaoDocument, String> {

        List<ParticipacaoDocument> findByEventoId(String eventoId);

        List<ParticipacaoDocument> findByPessoaId(String pessoaId);

        boolean existsByPessoaIdAndEventoId(
                        String pessoaId,
                        String eventoId);

        List<ParticipacaoDocument> findByEventoIdAndDiasContaining(
                        String eventoId,
                        DiaEvento dia);
}
