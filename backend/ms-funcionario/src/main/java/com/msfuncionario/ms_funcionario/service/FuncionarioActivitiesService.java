package com.msfuncionario.ms_funcionario.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * auxiliar para gerenciamento e organização de atividades realizadas por funcionários.
 **/
public class FuncionarioActivitiesService {

    private final List<Activity> activities;

    public FuncionarioActivitiesService() {
        this.activities = loadActivities();
    }

    private List<Activity> loadActivities() {
        List<Activity> list = new ArrayList<>();
        list.add(new Activity("Confirmou embarque", "joao.silva", LocalDateTime.now().minusMinutes(30)));
        list.add(new Activity("Cancelou voo", "maria.oliveira", LocalDateTime.now().minusHours(2)));
        list.add(new Activity("Realizou cadastro de voo", "lucas.santos", LocalDateTime.now().minusDays(1)));
        return list;
    }

    public int countActivitiesByUser(String username) {
        int total = 0;
        for (Activity a : activities) {
            if (a.usuario.equalsIgnoreCase(username)) {
                total++;
            }
        }
        return total;
    }

    public List<String> generateSummary() {
        List<String> summary = new ArrayList<>();
        for (Activity a : activities) {
            summary.add("[" + a.timestamp + "] " + a.usuario + " - " + a.descricao);
        }
        return summary;
    }
    

    public void printSummary() {
        List<String> summary = generateSummary();
        for (String line : summary) {
            System.out.println(line);
        }
    }

    static class Activity {
        String descricao;
        String usuario;
        LocalDateTime timestamp;

        Activity(String descricao, String usuario, LocalDateTime timestamp) {
            this.descricao = descricao;
            this.usuario = usuario;
            this.timestamp = timestamp;
        }
    }
}