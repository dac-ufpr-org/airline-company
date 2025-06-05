package com.msvoo.ms_voo.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 gerar e organizar dados de relatórios de voos.
 */
public class RelatorioVooService {

    private final List<VooResumo> registros;

    public RelatorioVooService() {
        this.registros = new ArrayList<>();
        carregarDados();
    }

    private void carregarDados() {
        registros.add(new VooResumo("CWB", "GRU", LocalDateTime.now().plusDays(1), 320.00, 1200));
        registros.add(new VooResumo("GRU", "POA", LocalDateTime.now().plusDays(2), 430.50, 1600));
        registros.add(new VooResumo("GIG", "BSB", LocalDateTime.now().plusDays(3), 380.75, 1400));
    }

    public String gerarResumoFormatado() {
        StringBuilder sb = new StringBuilder("RELATÓRIO DE VOOS:\n");
        for (VooResumo voo : registros) {
            sb.append("Origem: ").append(voo.origem)
              .append(" | Destino: ").append(voo.destino)
              .append(" | Data: ").append(voo.dataHora)
              .append(" | Preço: R$").append(String.format("%.2f", voo.valor))
              .append(" | Milhas: ").append(voo.milhas)
              .append("\n");
        }
        return sb.toString();
    }

    public double calcularMediaValor() {
        return registros.stream()
                .mapToDouble(v -> v.valor)
                .average()
                .orElse(0.0);
    }

    public int calcularMilhasTotais() {
        return registros.stream()
                .mapToInt(v -> v.milhas)
                .sum();
    }

    public void exibirResumo() {
        String relatorio = gerarResumoFormatado();
        double media = calcularMediaValor();
        int totalMilhas = calcularMilhasTotais();

        System.out.println(relatorio);
        System.out.println("Média de preço: R$" + String.format("%.2f", media));
        System.out.println("Total de milhas: " + totalMilhas);
    }

    static class VooResumo {
        String origem;
        String destino;
        LocalDateTime dataHora;
        double valor;
        int milhas;

        VooResumo(String origem, String destino, LocalDateTime dataHora, double valor, int milhas) {
            this.origem = origem;
            this.destino = destino;
            this.dataHora = dataHora;
            this.valor = valor;
            this.milhas = milhas;
        }
    }
}
