package chol_decomp;

import Basic_operation.Method;

public class Cholesky_decomposition {

    public static void main(String[] args) {
        double mat[][] = {
                {1, 2, 3, 2},
                {2, 5, 8, 5},
                {3, 8, 14, 12},
                {2, 5, 12, 30}
        };
        System.out.println("Матриця A:");
        Method.print_matrix(mat);
        double[][][] chol_decomp = chol(mat);
        System.out.println("Матриця L:");
        Method.print_matrix(chol_decomp[0]);
        System.out.println("Матриця L^(-1):");
        Method.print_matrix(chol_decomp[1]);
        System.out.println("Матриця L транспонована:");
        Method.print_matrix(Method.transpose(chol_decomp[0]));
        System.out.println("Перевірка:");
        Method.print_matrix(Method.mult(chol_decomp[0], Method.transpose(chol_decomp[0])));
    }

    public static double[][][] chol(double[][] A) {
        int n = A.length;
        double[][][] L_L_inv = new double[2][][];
        L_L_inv[0] = new double[n][n];           //L
        L_L_inv[1] = new double[n][n];           //L^(-1)
        double[][][] x_x_inv = new double[2][][];
        x_x_inv[0] = new double[n / 2][n / 2];      //x
        x_x_inv[1] = new double[n / 2][n / 2];      //x^(-1)
        double[][] z = new double[n / 2][n / 2];
        double[][][] y_y_inv = new double[2][][];
        y_y_inv[0] = new double[n / 2][n / 2];      //y
        y_y_inv[1] = new double[n / 2][n / 2];      //y^(-1)

        if (n == 2) {
            L_L_inv[0][0][0] = Math.sqrt(A[0][0]);                 //x
            L_L_inv[0][0][1] = 0.0;                                //0
            L_L_inv[0][1][0] = A[0][1] / L_L_inv[0][0][0];            //z
            L_L_inv[0][1][1] = Math.sqrt(A[1][1] - (A[0][1] * A[0][1] / A[0][0])); //y

            L_L_inv[1][0][0] = 1.0 / L_L_inv[0][0][0];                  //x^(-1)
            L_L_inv[1][0][1] = 0.0;                                //0
            L_L_inv[1][1][0] = ((-1.0) * L_L_inv[0][1][0]) /
                    (L_L_inv[0][0][0] * L_L_inv[0][1][1]);  //z^(-1)
            L_L_inv[1][1][1] = 1.0 / L_L_inv[0][1][1];                  //y^(-1)
        } else {
            x_x_inv = chol(part(A, 0, 0));
            z = Method.mult(Method.transpose(part(A, 0, 1)), //b транспоноване
                    Method.transpose(x_x_inv[1]));   //x^(-1) транспоноване
            y_y_inv = chol(Method.minus(part(A, 1, 1),
                    Method.mult(z, Method.transpose(z))));
            //Скласти матрицю L із отриманих блоків
            L_L_inv[0] = gather(x_x_inv[0],
                    y_y_inv[0],
                    z);
            //Скласти транспоновану матрицю L із отриманих блоків
            L_L_inv[1] = gather(x_x_inv[1],
                    y_y_inv[1],
                    Method.mult(
                            Method.mult(
                                    Method.mult(y_y_inv[1], -1.0),
                                    x_x_inv[1]
                            ),
                            z
                    )
            );
        }

        return L_L_inv;
    }

    //зібрання нижньотрикутної матриці з трьох блоків 
    public static double[][] gather(double[][] x, double[][] y, double[][] z) {
        double[][] res = new double[x.length * 2][x.length * 2];
        //Первым параметром является массив-источник
        //Вторым параметром является позиция массива-джерела.
        //Третий параметр — массив-назначения.
        //Четвертый параметр является начальным положением целевого массива.
        //Последний параметр это количество элементов, которые будут скопированы. 
        for (int i = 0; i < x.length; i++) {
            System.arraycopy(x[i], 0, res[i], 0, x[i].length);
        }
        for (int i = 0, j = res.length / 2; i < y.length; j++, i++) {
            System.arraycopy(y[i], 0, res[j], res.length / 2, y[i].length);
        }
        for (int i = 0, j = res.length / 2; i < z.length; i++, j++) {
            System.arraycopy(z[i], 0, res[j], 0, z[i].length);
        }
        for (int i = 0; i < res.length / 2; i++) {
            for (int j = res.length / 2; j < res.length; j++) {
                res[i][j] = 0.0;
            }
        }
        return res;
    }

    //виділення блока матриці
    public static double[][] part(double[][] A, int x, int y) {
        double[][] subMatrix = new double[A.length / 2][A.length / 2];
        if (x == 0 && y == 0) {
            for (int i = 0; i < subMatrix.length; i++)
                System.arraycopy(A[i], 0, subMatrix[i], 0, subMatrix[i].length);
        }
        if (x == 0 && y == 1) { //!!!
            for (int i = 0; i < subMatrix.length; i++)
                System.arraycopy(A[i], A.length / 2, subMatrix[i], 0, subMatrix[i].length);
        }
        if (x == 1 && y == 0) {
            for (int i = 0, j = A.length / 2; i < subMatrix.length; i++, j++)
                System.arraycopy(A[j], 0, subMatrix[i], 0, subMatrix[i].length);
        }
        if (x == 1 && y == 1) {
            for (int i = 0, j = A.length / 2; i < subMatrix.length; i++, j++)
                System.arraycopy(A[j], A.length / 2, subMatrix[i], 0, subMatrix[i].length);
        }
        return subMatrix;
    }

}
