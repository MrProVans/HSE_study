import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Lab3Strings {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Введите ФИО (Фамилия Имя Отчество): ");
            String fullName = scanner.nextLine().trim();
            String[] nameParts = fullName.split("\\s+");

            if (nameParts.length < 2 || nameParts.length > 3) {
                throw new IllegalArgumentException("Неверный формат ввода ФИО.");
            }

            String surname = nameParts[0];
            String name = nameParts[1];
            String patronymic = nameParts.length == 3 ? nameParts[2] : null;

            System.out.print("Введите дату рождения (дд.мм.гггг): ");
            String birthDateInput = scanner.nextLine().trim();
            LocalDate birthDate = parseDate(birthDateInput);

            String initials = surname + " " + name.charAt(0) + ".";
            if (patronymic != null) {
                initials += patronymic.charAt(0) + ".";
            }

            String gender = "ОПРЕДЕЛИТЬ_НЕ_УДАЛОСЬ";
            if (patronymic != null) {
                if (patronymic.endsWith("ич")) {
                    gender = "М";
                } else if (patronymic.endsWith("на")) {
                    gender = "Ж";
                }
            }

            int age = calculateAge(birthDate);
            String ageSuffix = getAgeSuffix(age);

            System.out.println("Инициалы: " + initials);
            System.out.println("Пол: " + gender);
            System.out.println("Возраст: " + age + " " + ageSuffix);

        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private static LocalDate parseDate(String date) throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return LocalDate.parse(date, formatter);
    }

    private static int calculateAge(LocalDate birthDate) {
        LocalDate today = LocalDate.now();
        return today.getYear() - birthDate.getYear() -
                (today.isBefore(birthDate.withYear(today.getYear())) ? 1 : 0);
    }

    private static String getAgeSuffix(int age) {
        int lastDigit = age % 10;
        int lastTwoDigits = age % 100;

        if (lastTwoDigits >= 11 && lastTwoDigits <= 19) {
            return "лет";
        }

        return switch (lastDigit) {
            case 1 -> "год";
            case 2, 3, 4 -> "года";
            default -> "лет";
        };
    }
}
