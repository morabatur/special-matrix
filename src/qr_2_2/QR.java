package qr_2_2;

import Basic_operation.Method;

public class QR {

    public static void main(String[] args) {
        double[][] A = {
                {3, 5},
                {1, 6}
        };
        double[][][] qr = QR2(A);
        System.out.println("Матриця А:");
        Method.print_matrix(A);
        System.out.println("Матриця Q:");
        Method.print_matrix(qr[0]);
        System.out.println("Матриця R:");
        Method.print_matrix(qr[1]);
        System.out.println("Перевірка Q*R:");
        Method.print_matrix(Method.mult(qr[0], qr[1]));

    }

    public static double[][][] QR2(double A[][]) {
        int n = 2;
        double[][][] res = new double[2][][];
        res[0] = new double[n][n];     //Q
        res[1] = new double[n][n];     //R
        double a = A[0][0];
        double b = A[1][0];
        double d = 1.0 / Math.sqrt(a * a + b * b);
        double s = -1.0 * b * d;
        double c = a * d;
        res[0][0][0] = c;
        res[0][0][1] = s;
        res[0][1][0] = -1.0 * s;
        res[0][1][1] = c;
        res[1] = Method.mult(Method.transpose(res[0]), A);
        return res;
    }

}
