package utils;

import model.Task;
import model.enus.StatusTask;

import java.io.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class JsonDb {

    private static final String FILE_PATH = "db.json";

    public static List<Task> readTask() throws IOException {
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            // Cria o arquivo vazio com conteúdo inicial "[]"
            file.createNewFile();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
                writer.write("[]");
            }
            return new ArrayList<>();
        }

        // Lê o conteúdo do arquivo JSON
        StringBuilder json = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
        }

        // Parse do JSON manualmente
        String jsonString = json.toString().trim();
        if (jsonString.equals("[]") || jsonString.isEmpty()) {
            return new ArrayList<>();
        }

        List<Task> tasks = new ArrayList<>();
        String[] entries = jsonString.substring(1, jsonString.length() - 1).split("},");
        for (String entry : entries) {
            entry = entry.trim();
            if (!entry.endsWith("}")) {
                entry += "}";
            }

            String id = extractValue(entry, "\"id\":");
            String description = extractValue(entry, "\"description\":");
            String statusStr = extractValue(entry, "\"status\":");
            String createdAtStr = extractValue(entry, "\"createdAt\":");
            String updatedAtStr = extractValue(entry, "\"updatedAt\":");

            StatusTask status = StatusTask.valueOf(statusStr);
            Instant createdAt = Instant.parse(createdAtStr);
            Instant updatedAt = updatedAtStr.equals("null") ? null : Instant.parse(updatedAtStr);

            tasks.add(new Task(id, description, status, createdAt, updatedAt));
        }

        return tasks;
    }

    public static void writeTasks(List<Task> tasks) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write("[");
            for (int i = 0; i < tasks.size(); i++) {
                Task task = tasks.get(i);

                // Criação manual do JSON
                String jsonTask = String.format(
                        "{\"id\": \"%s\", \"description\": \"%s\", \"status\": \"%s\", \"createdAt\": \"%s\", \"updatedAt\": %s}",
                        task.getId(),
                        task.getDescription(),
                        task.getStatus(),
                        task.getCreatedAt(),
                        task.getUpdatedAt() == null ? "null" : "\"" + task.getUpdatedAt() + "\""
                );

                writer.write(jsonTask);
                if (i < tasks.size() - 1) {
                    writer.write(",");
                }
            }
            writer.write("]");
        }
    }

    // Método auxiliar para extrair valores do JSON
    private static String extractValue(String json, String key) {
        int start = json.indexOf(key) + key.length();
        int end = json.indexOf(",", start);
        if (end == -1) {
            end = json.indexOf("}", start);
        }
        String value = json.substring(start, end).trim();
        if (value.startsWith("\"") && value.endsWith("\"")) {
            value = value.substring(1, value.length() - 1);
        }
        return value;
    }
}
