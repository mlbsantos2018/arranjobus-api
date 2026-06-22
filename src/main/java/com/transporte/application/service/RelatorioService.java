package com.transporte.application.service;

import com.transporte.domain.ports.out.ParticipacaoRepositoryPort;
import com.transporte.domain.ports.out.PessoaRepositoryPort;
import lombok.AllArgsConstructor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import com.transporte.domain.enums.DiaEvento;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RelatorioService {

    private final ParticipacaoRepositoryPort participacaoRepository;
    private final PessoaRepositoryPort pessoaRepository;

    public List<RelatorioParticipacaoDTO> gerarRelatorioParticipantes(
            UUID eventoId,
            DiaEvento dia) {
        var participacoes = participacaoRepository.buscarPorEventoIdEDia(eventoId, dia);

        return participacoes.stream()
                .map(p -> {
                    var pessoa = pessoaRepository
                            .buscarPorId(p.getPessoaId())
                            .orElse(null);

                    return new RelatorioParticipacaoDTO(
                            pessoa != null ? pessoa.getNomeCompleto() : "N/A",
                            pessoa != null ? pessoa.getNumeroDocumento() : "N/A",
                            pessoa != null ? pessoa.getObservacaoCurta() : "N/A");
                })
                .sorted((a, b) -> a.nomeCompleto.compareTo(b.nomeCompleto))
                .collect(Collectors.toList());
    }

    public byte[] gerarExcel(List<RelatorioParticipacaoDTO> dados, String nomeEvento, String dia) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Participantes");

            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Evento: " + nomeEvento + " - Dia: " + dia);

            Row columnRow = sheet.createRow(2);
            columnRow.createCell(0).setCellValue("Nome Completo");
            columnRow.createCell(1).setCellValue("Documento");
            columnRow.createCell(2).setCellValue("Observação");

            int rowNum = 3;
            for (RelatorioParticipacaoDTO dado : dados) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(dado.nomeCompleto);
                row.createCell(1).setCellValue(dado.documento);
                row.createCell(2).setCellValue(dado.observacao);
            }

            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            workbook.write(baos);
            return baos.toByteArray();
        }
    }

    public static class RelatorioParticipacaoDTO {
        public String nomeCompleto;
        public String documento;
        public String observacao;

        public RelatorioParticipacaoDTO(String nomeCompleto, String documento, String observacao) {
            this.nomeCompleto = nomeCompleto;
            this.documento = documento;
            this.observacao = observacao;
        }
    }
}
