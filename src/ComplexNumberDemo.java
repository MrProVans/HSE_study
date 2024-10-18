public class ComplexNumberDemo {
    public static void main(String[] args) {
        // Создание двух комплексных чисел
        ComplexNumber num1 = new ComplexNumber(3, 4); // 3 + 4i
        ComplexNumber num2 = new ComplexNumber(1, 2); // 1 + 2i

        // Сложение комплексных чисел
        ComplexNumber sum = num1.add(num2);
        System.out.println(num1 + " + (" + num2 + ") = " + sum);
        System.out.println();

        // Вычитание комплексных чисел
        ComplexNumber difference = num1.subtract(num2);
        System.out.println(num1 + " - (" + num2 + ") = " + difference);
        System.out.println();

        // Умножение комплексных чисел
        ComplexNumber product = num1.multiply(num2);
        System.out.println(num1 + " * (" + num2 + ") = " + product);
        System.out.println();

        // Деление комплексных чисел
        ComplexNumber quotient = num1.divide(num2);
        System.out.println(num1 + " / (" + num2 + ") = " + quotient);
        System.out.println();

        // Дополнительно: вывод созданных комплексных чисел
        System.out.println("Первое комплексное число: " + num1);
        System.out.println("Второе комплексное число: " + num2);
    }
}
