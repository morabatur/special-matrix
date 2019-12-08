package fb;
import Basic_operation.Method;

public class inverseFB {
    
    public static void main(String[] args) {
        double[][] A = new double[][] {
                {5, 1, 1, 0},
                {1, 1, 2, 1},
                {2, 3, 1, 1},
                {2, 1, 2, 1}
        };
        if(Method.if_size_square(A)){
            System.out.println("Початкова матриця A: ");
            Method.print_matrix(A);
            double [][]B = FB_algorithms.inv_FB(A);
            System.out.println("Обернена матриця A^(-1): ");
            Method.print_matrix(B);
            System.out.println("Перевірка A * A^(-1): ");
            Method.print_matrix(Method.mult(A, B));       
        } else {
            System.out.println("Матриця не квадратна!");
        }
    }
    
 
    
}
