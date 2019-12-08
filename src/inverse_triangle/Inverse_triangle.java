package inverse_triangle;

import Basic_operation.Method;

public class Inverse_triangle {
    public static void main(String[] args) {
        double mat[][] = {
                {1, 0, 0, 0, 0, 0, 0, 0},
                {2, 1, 0, 0, 0, 0, 0, 0},
                {1, 0, 1, 0, 0, 0, 0, 0},
                {0, 1, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 1, 1, 0, 0, 0},
                {1, 0, 0, 0, 1, 1, 0, 0},
                {0, 1, 1, 1, 0, 1, 1, 0},
                {1, 1, 0, 0, 0, 1, 0, 1}
        };
        if ((mat.length != mat[0].length) || (mat.length & (mat.length - 1)) != 0) {
            System.out.println("Ìàòðèöÿ íå ðîçì³ðó 2^k! Íåìîæëèâî çàñòîñóâàòè àëãîðèòì.");
        } else {
            int flag = 0;
            for (int i = 0; i < mat.length; i++) {
                for (int j = i + 1; j < mat[0].length; j++) {
                    if (mat[i][j] != 0) {
                        flag = 1;
                        System.out.println("Ìàòðèöÿ íå òðèêóòíà! Íåìîæëèâî çàñòîñóâàòè àëãîðèòì.");
                        break;
                    }
                }
            }
            if (flag != 1) {
                double[][] res = invTriangle.invTriangle(mat);
                System.out.println("Ìàòðèöÿ À:");
                Method.print_matrix(mat);
                System.out.println("Îáåðíåíà ìàòðèöÿ À^(-1):");
                Method.print_matrix(res);
                double[][] check = Method.mult(mat, res);
                System.out.println("Ïåðåâ³ðêà ( À * À^(-1) ):");
                Method.print_matrix(check);
            }
        }
    }

}
