package ldu;

import Basic_operation.Method;

public class LDU {

    public static void main(String[] args) {
        double[][] A = new double[][]{
                {5, 1, 1, 0},
                {1, 1, 2, 1},
                {2, 3, 1, 1},
                {2, 1, 2, 1}
        };
        if (Method.if_size_square(A)) {
            System.out.println("Матриця А:");
            Method.print_matrix(A);
            double[][][] ldu = decompositionByLDU(A);
            System.out.println("Матриця L:");
            Method.print_matrix(ldu[0]);
            System.out.println("Матриця D:");
            Method.print_matrix(ldu[1]);
            System.out.println("Матриця U:");
            Method.print_matrix(ldu[2]);
            System.out.println("Перевірка L*D*U:");
            Method.print_matrix(Method.mult(ldu[0], Method.mult(ldu[1], ldu[2])));
        } else {
            System.out.println("Матриця не квадратна!");
        }
    }

    //LDU-розклад
    public static double[][][] decompositionByLDU(double[][] A) {
        int n = A.length;
        double[][][] LDU = new double[3][][];
        LDU[0] = new double[n][n];  //L       
        LDU[1] = new double[n][n];  //D
        LDU[2] = new double[n][n];  //U 

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                LDU[2][i][j] = A[i][j];//Скопіювати вхідну матриці А в матрицю U
            }
        }

        //записати перший стовпчик з А в L
        for (int i = 0; i < n; i++) {
            LDU[0][i][0] = A[i][0];
        }

        double akk;//тимчасова змінна для обчислень
        for (int k = 0; k < n - 1; k++) { //кількість ходів: на один менше ніж порядок квадратної матриці А, оскільки ми вже скопіювали перший стовпчик з А в L
            akk = (k == 0) ? 1 : LDU[2][k - 1][k - 1];//вибір елементу з діагоналі матриці U
//            System.out.println("Step = " + k);
//            System.out.println("Matrix L:");
//            Method.print_matrix(LDU[0]);
//            System.out.println("Matrix D:");
//            Method.print_matrix(LDU[1]);
//            System.out.println("Matrix U:");
//            Method.print_matrix(LDU[2]);
            //Формування матриці U
            for (int i = k + 1; i < n; i++) {//починаємо опрацьовувати матрицю стартуючи з другої колонки (не забуваємо, що першу ми вже опрацювали)
                for (int j = k + 1; j < n; j++) {
                    LDU[2][i][j] = (LDU[2][i][j] * LDU[2][k][k] - LDU[2][i][k] * LDU[2][k][j]) / akk;
                }
                int d = 0;
                //занулення елементів відбувається після кожного опрацювання стовпчика для того щоб побудувати трикутну матрицю
                while (d < k + 1) {
                    LDU[2][i][d] = 0;
                    d++;
                }
            }
            //записати стовпчик в L
            for (int i = k + 1; i < n; i++) {
                LDU[0][i][k + 1] = LDU[2][i][k + 1];
            }
        }
        //сформувати матрицю D
        LDU[1][0][0] = 1.0 / LDU[2][0][0];
        for (int i = 1; i < n; i++) {
            LDU[1][i][i] = 1.0 / (LDU[2][i][i] * LDU[2][i - 1][i - 1]);
        }
        return LDU;
    }

}
