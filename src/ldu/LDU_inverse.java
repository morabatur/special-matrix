package ldu;
import Basic_operation.Method;
import inverse_triangle.invTriangle;
import fb.FB_algorithms;

public class LDU_inverse {

    public static void main(String[] args) {
        double[][] A = new double[][] {
                {5, 1, 1, 0},
                {1, 1, 2, 1},
                {2, 3, 1, 1},
                {2, 1, 2, 1}
        };
        if(Method.if_size_square(A)){
            System.out.println("Матриця A:");
            Method.print_matrix(A);
            double[][]B = LDU_inv(A);
            System.out.println("Матриця A^(-1):");
            Method.print_matrix(B);
            System.out.println("Перевірка А * A^(-1):");
            Method.print_matrix(Method.mult(A, B));
        } else {
            System.out.println("Матриця не квадратна!");
        }
    }
    
    public static double [][]LDU_inv(double [][]A){
        int n = A.length;
        double [][]A_inv = new double[n][n];

        //LDU розклад
        double [][][]ldu = LDU.decompositionByLDU(A);

        //знаходження L^(-1), L = ldu[0]
        double [][]L_inv = invTriangle.invTriangle(ldu[0]);
        System.out.println("Матриця L^(-1):");
        Method.print_matrix(L_inv);

        //знаходження D^(-1) = ldu[1]
        for(int i = 0; i < n; i++){
            ldu[1][i][i] = 1.0/ldu[1][i][i];
        }
        System.out.println("Матриця D^(-1):");
        Method.print_matrix(ldu[1]);

        //знаходження U^(-1), U = ldu[2]
        double [][]U_inv = FB_algorithms.inv_FB(ldu[2]);
        System.out.println("Матриця U^(-1):");
        Method.print_matrix(U_inv);

        //добуток L^(-1), D^(-1), U^(-1)
        A_inv = Method.mult(U_inv, Method.mult(ldu[1], L_inv));
        return A_inv;
    }
    
}
