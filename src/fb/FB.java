package fb;
import Basic_operation.Method;

public class FB {
    public static void main(String[] args) {
        double[][] test = new double[][] {
                {2, 1, 1, 2, 3},
                {1, 1, 2, 1, 1},
                {2, 0, 1, 0, -1},
                {1, 1, 1, 1, -2}
        };
        int[]x_order = new int[test.length];
        for(int i = 0; i < x_order.length; i++ ){
            x_order[i] = i;
        }
      
        System.out.println("Початкова матриця: ");
        Method.print_matrix(test);
        //ïðÿìèé õ³ä
        double [][]f = FB_algorithms.forward(test, x_order);
        //çâîðîòí³é õ³ä
        double [] x = FB_algorithms.backward(f, x_order);
        //ïåðåâ³ðêà
        System.out.println("Перевірка: ");
        FB_algorithms.chek_SLAE(x, test);
        
        
    }    
    
}
