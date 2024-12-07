import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.net.URISyntaxException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Привет! У меня есть данные из двух файлов:");
            System.out.println("1. companies.json — информация о компаниях.");
            System.out.println("2. blogs.json — информация о блогах.");
            System.out.println("Я могу показать данные для следующих полей.");
            System.out.println("Для компаний: id, name, address, zip, country, employeeCount, industry, marketCap, domain, logo, ceoName.");
            System.out.println("Для блогов: userId, id, title, body, link, comment_count.");
            System.out.println("Введите названия полей, которые хотите увидеть, через запятую (например: name, address):");

            String[] selectedFields = scanner.nextLine().toLowerCase().split(",\\s*");

            System.out.println("Теперь выводим данные из companies.json...");
            String companiesContent = readResourceFile("companies.json");
            parseAndDisplayFilteredData(companiesContent, selectedFields);

            System.out.println("\nТеперь выводим данные из blogs.json...");
            String blogsContent = readResourceFile("blogs.json");
            parseAndDisplayFilteredData(blogsContent, selectedFields);

        } catch (IOException | URISyntaxException e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
        }
    }

    private static String readResourceFile(String fileName) throws IOException, URISyntaxException {
        Path path = Paths.get(Main.class.getClassLoader().getResource(fileName).toURI());
        return Files.readString(path);
    }

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
            System.out.println();
        }
    }
}