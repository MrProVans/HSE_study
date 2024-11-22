package first_lab;

public class ComplexMatrixDemonstration {
    public static void main(String[] args) {

        ComplexMatrix matrix1 = new ComplexMatrix(2, 2);
        matrix1.setElement(0, 0, new ComplexNumber(1, 2));
        matrix1.setElement(0, 1, new ComplexNumber(3, 4));
        matrix1.setElement(1, 0, new ComplexNumber(5, 6));
        matrix1.setElement(1, 1, new ComplexNumber(7, 8));

        System.out.println("Matrix 1:");
        matrix1.display();
        System.out.println();

        ComplexNumber det = matrix1.determinant();
        System.out.println("Determinant of Matrix 1: " + det);
        System.out.println();

        ComplexMatrix inverseMatrix = matrix1.inverse();
        System.out.println("Inverse of Matrix 1:");
        inverseMatrix.display();
        System.out.println();

        ComplexMatrix result = matrix1.divide(matrix1);
        System.out.println("Matrix 1 / Matrix 1 (should be identity matrix):");
        result.display();
    }
}
