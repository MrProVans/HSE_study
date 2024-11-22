package first_lab;

public class ComplexMatrixDemo {
    public static void main(String[] args) {
        ComplexMatrix matrix1 = new ComplexMatrix(2, 2);
        ComplexMatrix matrix2 = new ComplexMatrix(2, 2);

        matrix1.setElement(0, 0, new ComplexNumber(1, 1));  // 1 + 1i
        matrix1.setElement(0, 1, new ComplexNumber(2, 2));  // 2 + 2i
        matrix1.setElement(1, 0, new ComplexNumber(3, 3));  // 3 + 3i
        matrix1.setElement(1, 1, new ComplexNumber(4, 4));  // 4 + 4i

        matrix2.setElement(0, 0, new ComplexNumber(5, 5));  // 5 + 5i
        matrix2.setElement(0, 1, new ComplexNumber(6, 6));  // 6 + 6i
        matrix2.setElement(1, 0, new ComplexNumber(7, 7));  // 7 + 7i
        matrix2.setElement(1, 1, new ComplexNumber(8, 8));  // 8 + 8i

        System.out.println("Matrix 1:");
        matrix1.display();
        System.out.println();

        System.out.println("Matrix 2:");
        matrix2.display();
        System.out.println();

        ComplexMatrix sum = matrix1.add(matrix2);
        System.out.println("Matrix 1 + Matrix 2:");
        sum.display();
        System.out.println();

        ComplexMatrix difference = matrix1.subtract(matrix2);
        System.out.println("Matrix 1 - Matrix 2:");
        difference.display();
        System.out.println();

        ComplexMatrix product = matrix1.multiply(matrix2);
        System.out.println("Matrix 1 * Matrix 2:");
        product.display();
        System.out.println();

        ComplexMatrix transpose = matrix1.transpose();
        System.out.println("Transpose of Matrix 1:");
        transpose.display();
    }
}

