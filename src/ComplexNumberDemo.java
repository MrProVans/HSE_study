public class ComplexNumberDemo {
    public static void main(String[] args) {
        ComplexNumber num1 = new ComplexNumber(3, 4); // 3 + 4i
        ComplexNumber num2 = new ComplexNumber(1, 2); // 1 + 2i

        ComplexNumber sum = num1.add(num2);
        System.out.println(num1 + " + (" + num2 + ") = " + sum);
        System.out.println();

        ComplexNumber difference = num1.subtract(num2);
        System.out.println(num1 + " - (" + num2 + ") = " + difference);
        System.out.println();

        ComplexNumber product = num1.multiply(num2);
        System.out.println(num1 + " * (" + num2 + ") = " + product);
        System.out.println();

        ComplexNumber quotient = num1.divide(num2);
        System.out.println(num1 + " / (" + num2 + ") = " + quotient);
        System.out.println();

        System.out.println("Первое комплексное число: " + num1);
        System.out.println("Второе комплексное число: " + num2);
    }
}
