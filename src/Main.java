import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            // Пользователь вводит ссылку на API
            System.out.println("Введите ссылку на API:");
            String apiUrl = scanner.nextLine();

            // Получение данных из API
            String apiResponse = fetchDataFromApi(apiUrl);

            if (apiResponse.isEmpty()) {
                System.err.println("Не удалось получить данные из API.");
                return;
            }

            // Определяем доступные поля
            Set<String> availableFields = determineFields(apiResponse);
            if (availableFields.isEmpty()) {
                System.err.println("Не удалось определить поля в JSON данных.");
                return;
            }

            // Показываем доступные поля пользователю
            System.out.println("Доступные поля в JSON данных:");
            System.out.println(String.join(", ", availableFields));
            System.out.println("Введите названия полей, которые хотите увидеть, через запятую:");

            // Получаем от пользователя список полей для вывода
            String[] selectedFields = scanner.nextLine().toLowerCase().split(",\\s*");

            // Выводим выбранные данные
            System.out.println("\nВывод данных из API:");
            parseAndDisplayFilteredData(apiResponse, selectedFields);
        } catch (Exception e) {
            System.err.println("Произошла ошибка: " + e.getMessage());
        }
    }

    // Метод получения данных из API
    private static String fetchDataFromApi(String apiUrl) {
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка получения данных из API: " + e.getMessage());
        }
        return result.toString();
    }

    // Метод определения доступных полей
    private static Set<String> determineFields(String json) {
        Set<String> fields = new HashSet<>();
        // Извлекаем первую запись для анализа
        String[] entries = json.replace("[", "").replace("]", "").split("\\},\\s*\\{");
        if (entries.length > 0) {
            String firstEntry = entries[0]
                    .replace("{", "")
                    .replace("}", "")
                    .replace("\"", "");
            String[] keyValuePairs = firstEntry.split(",\\s*");
            for (String pair : keyValuePairs) {
                String[] keyValue = pair.split(":\\s*", 2);
                if (keyValue.length == 2) {
                    fields.add(keyValue[0].trim());
                }
            }
        }
        return fields;
    }

    // Парсинг и вывод данных по выбранным полям
    private static void parseAndDisplayFilteredData(String json, String[] selectedFields) {
        String[] entries = json.replace("[", "").replace("]", "").split("\\},\\s*\\{");

        for (int i = 0; i < entries.length; i++) {
            String entry = entries[i]
                    .replace("{", "")
                    .replace("}", "")
                    .replace("\"", "");
            String[] keyValuePairs = entry.split(",\\s*");
            System.out.println("Запись " + (i + 1) + ":");
            for (String pair : keyValuePairs) {
                String[] keyValue = pair.split(":\\s*", 2);
                if (keyValue.length == 2) {
                    String key = keyValue[0].trim();
                    String value = keyValue[1].trim();
                    for (String field : selectedFields) {
                        if (key.equalsIgnoreCase(field)) {
                            System.out.println("  " + key + ": " + value);
                        }
                    }
                }
            }
            System.out.println(); // Пустая строка между записями
        }
    }
}