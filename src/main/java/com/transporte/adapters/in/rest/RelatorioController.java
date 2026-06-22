package com.transporte.adapters.in.rest;

import com.transporte.application.service.RelatorioService;
import com.transporte.application.service.EventoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.transporte.domain.enums.DiaEvento;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reports")
@AllArgsConstructor
@Tag(name = "Reports", description = "Geração de Relatórios")
@SecurityRequirement(name = "Bearer Authentication")
public class RelatorioController {

    private final RelatorioService relatorioService;
    private final EventoService eventoService;

    @GetMapping("/events/{eventId}/friday")
    @Operation(summary = "Relatório Sexta-feira", description = "Gerar relatório de participantes da sexta-feira")
    public ResponseEntity<?> relatorioSexta(@PathVariable UUID eventId) {
        var participantes = relatorioService.gerarRelatorioParticipantes(
                eventId,
                DiaEvento.SEXTA);
        return ResponseEntity.ok(participantes);
    }

    @GetMapping("/events/{eventId}/saturday")
    @Operation(summary = "Relatório Sábado", description = "Gerar relatório de participantes do sábado")
    public ResponseEntity<?> relatorioSabado(@PathVariable UUID eventId) {
        var participantes = relatorioService.gerarRelatorioParticipantes(
                eventId,
                DiaEvento.SABADO);
        return ResponseEntity.ok(participantes);
    }

    @GetMapping("/events/{eventId}/sunday")
    @Operation(summary = "Relatório Domingo", description = "Gerar relatório de participantes do domingo")
    public ResponseEntity<?> relatorioDomingo(@PathVariable UUID eventId) {
        var participantes = relatorioService.gerarRelatorioParticipantes(
                eventId,
                DiaEvento.DOMINGO);
        return ResponseEntity.ok(participantes);
    }

    @GetMapping("/events/{eventId}/excel")
    @Operation(summary = "Relatório Excel", description = "Gerar relatório em Excel")
    public ResponseEntity<byte[]> relatorioExcel(
            @PathVariable UUID eventId,
            @RequestParam DiaEvento dia) throws IOException {

        var evento = eventoService.buscarPorId(eventId);

        var participantes = relatorioService.gerarRelatorioParticipantes(
                eventId,
                dia);

        byte[] excelBytes = relatorioService.gerarExcel(
                participantes,
                evento.getNome(),
                dia.name());

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=relatorio_" + dia.name() + ".xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(excelBytes);
    }
}
