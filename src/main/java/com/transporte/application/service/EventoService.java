package com.transporte.application.service;

import com.transporte.domain.exception.RecursoNaoEncontradoException;
import com.transporte.domain.model.Evento;
import com.transporte.domain.model.Pagamento;
import com.transporte.domain.enums.StatusEvento;
import com.transporte.domain.enums.TipoEvento;
import com.transporte.domain.ports.out.EventoRepositoryPort;
import com.transporte.domain.ports.out.ParticipacaoRepositoryPort;
import com.transporte.domain.ports.out.PagamentoRepositoryPort;
import com.transporte.application.dto.response.EventoDetailResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class EventoService {

    private final EventoRepositoryPort eventoRepository;
    private final ParticipacaoRepositoryPort participacaoRepository;
    private final PagamentoRepositoryPort pagamentoRepository;

    public Evento criar(Evento evento) {
        validarDiaAssembleia(evento);
        return eventoRepository.criar(evento);
    }

    public Evento atualizar(UUID id, Evento evento) {
        Evento existente = eventoRepository.buscarPorId(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Evento", id.toString()));
        
        evento.setId(id);
        evento.setCriadoEm(existente.getCriadoEm());
        evento.setStatus(existente.getStatus());

        if (evento.getTipo() == TipoEvento.ASSEMBLEIA) {
            if (evento.getDiaAssembleia() == null) {
                evento.setDiaAssembleia(existente.getDiaAssembleia());
            }
        } else {
            evento.setDiaAssembleia(null);
        }

        validarDiaAssembleia(evento);

        Evento eventoAtualizado = eventoRepository.atualizar(evento);

        if (evento.getTipo() == TipoEvento.ASSEMBLEIA
                && existente.getTipo() == TipoEvento.ASSEMBLEIA
                && evento.getDiaAssembleia() != null
                && !evento.getDiaAssembleia().equals(existente.getDiaAssembleia())) {
            var participacoes = participacaoRepository.buscarPorEventoId(id);
            participacoes.forEach(participacao -> {
                participacao.setDias(List.of(evento.getDiaAssembleia()));
                participacaoRepository.atualizar(participacao);
            });
        }

        return eventoAtualizado;
    }

    public void excluir(UUID id) {
        eventoRepository.buscarPorId(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Evento", id.toString()));
        
        // Deletar todas as participações relacionadas ao evento e seus pagamentos associados
        var participacoes = participacaoRepository.buscarPorEventoId(id);
        participacoes.forEach(participacao -> {
            pagamentoRepository.buscarPorParticipacaoId(participacao.getId())
                    .ifPresent(pagamento -> pagamentoRepository.excluir(pagamento.getId()));
            participacaoRepository.excluir(participacao.getId());
        });
        
        eventoRepository.excluir(id);
    }

    private void validarDiaAssembleia(Evento evento) {
        if (evento.getTipo() == TipoEvento.ASSEMBLEIA && evento.getDiaAssembleia() == null) {
            throw new IllegalArgumentException("Dia da assembleia é obrigatório para eventos tipo ASSEMBLEIA");
        }
    }

    public Evento buscarPorId(UUID id) {
        return eventoRepository.buscarPorId(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Evento", id.toString()));
    }

    public List<Evento> listar() {
        return eventoRepository.listar();
    }

    public Evento encerrar(UUID id) {
        Evento evento = buscarPorId(id);
        evento.setStatus(StatusEvento.ENCERRADO);
        return eventoRepository.atualizar(evento);
    }

    public Evento arquivar(UUID id) {
        Evento evento = buscarPorId(id);
        evento.setStatus(StatusEvento.ARQUIVADO);
        return eventoRepository.atualizar(evento);
    }

    public EventoDetailResponse buscarPorIdComDetalhes(UUID id) {
        Evento evento = buscarPorId(id);
        
        // Calcular total de participantes
        int totalParticipantes = participacaoRepository.buscarPorEventoId(id).size();
        
        // Calcular total arrecadado pelo evento inteiro (inclui valores mantidos após participação excluída)
        BigDecimal totalArrecadado = pagamentoRepository.buscarPorEventoId(id).stream()
                .map(Pagamento::getValorPago)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        // Calcular percentual de capacidade
        Double percentualCapacidade = evento.getVagasTotais() > 0 
                ? (totalParticipantes * 100.0) / evento.getVagasTotais()
                : 0.0;
        
        // Calcular percentual de arrecadação com base no total possível de vagas
        BigDecimal valorEsperado;
        valorEsperado = evento.getValorPassagem().multiply(BigDecimal.valueOf(evento.getVagasTotais()));
        Double percentualArrecadacao = valorEsperado.compareTo(BigDecimal.ZERO) > 0
                ? (totalArrecadado.doubleValue() / valorEsperado.doubleValue()) * 100.0
                : 0.0;
        
        return EventoDetailResponse.builder()
                .id(evento.getId())
                .nome(evento.getNome())
                .tipo(evento.getTipo())
                .valorPassagem(evento.getValorPassagem())
                .vagasTotais(evento.getVagasTotais())
                .limiteIdadeCriancaColo(evento.getLimiteIdadeCriancaColo())
                .status(evento.getStatus())
                .temas(evento.getTemas())
                .criadoEm(evento.getCriadoEm())
                .dataOcorrencia(evento.getDataOcorrencia())
                .diaAssembleia(evento.getDiaAssembleia())
                .valorTotalEsperado(valorEsperado)
                .totalParticipantes(totalParticipantes)
                .totalArrecadado(totalArrecadado)
                .percentualCapacidade(percentualCapacidade)
                .percentualArrecadacao(percentualArrecadacao)
                .build();
    }
}
