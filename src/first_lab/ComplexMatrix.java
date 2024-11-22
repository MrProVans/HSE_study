package first_lab;

public class ComplexMatrix {
    private ComplexNumber[][] matrix;
    private int rows;
    private int cols;

    public ComplexMatrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        matrix = new ComplexNumber[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = new ComplexNumber(0, 0);
            }
        }
    }

    public void setElement(int row, int col, ComplexNumber value) {
        matrix[row][col] = value;
    }

    public ComplexNumber getElement(int row, int col) {
        return matrix[row][col];
    }

    public void display() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public ComplexMatrix add(ComplexMatrix other) {
        ComplexMatrix result = new ComplexMatrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.setElement(i, j, this.getElement(i, j).add(other.getElement(i, j)));
            }
        }
        return result;
    }

    public ComplexMatrix subtract(ComplexMatrix other) {
        ComplexMatrix result = new ComplexMatrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.setElement(i, j, this.getElement(i, j).subtract(other.getElement(i, j)));
            }
        }
        return result;
    }

    public ComplexMatrix multiply(ComplexMatrix other) {
        ComplexMatrix result = new ComplexMatrix(this.rows, other.cols);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < other.cols; j++) {
                ComplexNumber sum = new ComplexNumber(0, 0);
                for (int k = 0; k < this.cols; k++) {
                    sum = sum.add(this.getElement(i, k).multiply(other.getElement(k, j)));
                }
                result.setElement(i, j, sum);
            }
        }
        return result;
    }

    public ComplexMatrix transpose() {
        ComplexMatrix result = new ComplexMatrix(cols, rows);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.setElement(j, i, this.getElement(i, j));
            }
        }
        return result;
    }

    public ComplexNumber determinant() {
        if (rows == 2 && cols == 2) {
            ComplexNumber a = getElement(0, 0);
            ComplexNumber b = getElement(0, 1);
            ComplexNumber c = getElement(1, 0);
            ComplexNumber d = getElement(1, 1);
            return a.multiply(d).subtract(b.multiply(c));
        } else if (rows == 3 && cols == 3) {
            ComplexNumber a = getElement(0, 0);
            ComplexNumber b = getElement(0, 1);
            ComplexNumber c = getElement(0, 2);
            ComplexNumber d = getElement(1, 0);
            ComplexNumber e = getElement(1, 1);
            ComplexNumber f = getElement(1, 2);
            ComplexNumber g = getElement(2, 0);
            ComplexNumber h = getElement(2, 1);
            ComplexNumber i = getElement(2, 2);
            ComplexNumber term1 = e.multiply(i).subtract(f.multiply(h));
            ComplexNumber term2 = d.multiply(i).subtract(f.multiply(g));
            ComplexNumber term3 = d.multiply(h).subtract(e.multiply(g));
            return a.multiply(term1).subtract(b.multiply(term2)).add(c.multiply(term3));
        }
        throw new UnsupportedOperationException("Детерминант поддерживается только для матриц 2x2 и 3x3");
    }

    public ComplexMatrix inverse() {
        ComplexNumber det = determinant();
        if (det.getReal() == 0 && det.getImaginary() == 0) {
            throw new ArithmeticException("Матрица вырождена, обратная матрица не существует");
        }

        ComplexNumber a = getElement(0, 0);
        ComplexNumber b = getElement(0, 1);
        ComplexNumber c = getElement(1, 0);
        ComplexNumber d = getElement(1, 1);

        ComplexMatrix inverseMatrix = new ComplexMatrix(2, 2);
        inverseMatrix.setElement(0, 0, d.divide(det));
        inverseMatrix.setElement(0, 1, b.multiply(new ComplexNumber(-1, 0)).divide(det));
        inverseMatrix.setElement(1, 0, c.multiply(new ComplexNumber(-1, 0)).divide(det));
        inverseMatrix.setElement(1, 1, a.divide(det));

        return inverseMatrix;
    }

    public ComplexMatrix divide(ComplexMatrix other) {
        ComplexMatrix inverseOther = other.inverse();
        return this.multiply(inverseOther);
    }
}