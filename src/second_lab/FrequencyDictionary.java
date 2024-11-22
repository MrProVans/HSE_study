package second_lab;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FrequencyDictionary {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите имя файла для чтения: ");
        String inputFile = scanner.nextLine();

        File file = new File(inputFile);
        if (!file.exists()) {
            System.out.println("Файл не найден. Убедитесь, что файл существует и повторите попытку.");
            return;
        }

        Map<Character, Integer> frequencyMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            int character;
            while ((character = reader.read()) != -1) {
                char ch = (char) character;
                if (Character.isLetter(ch) && Character.isAlphabetic(ch)) {
                    frequencyMap.put(ch, frequencyMap.getOrDefault(ch, 0) + 1);
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
            return;
        }

        System.out.print("Введите имя файла для записи результатов: ");
        String outputFile = scanner.nextLine();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
                writer.write(entry.getKey() + ": " + entry.getValue());
                writer.newLine();
            }
            System.out.println("Частотный словарь успешно записан в файл " + outputFile);
        } catch (IOException e) {
            System.out.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }
}
