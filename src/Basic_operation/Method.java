package Basic_operation;

//����� �������� ��� �������
public class Method {
    //�������� ���� �������
    public static double[][] mult(double[][] a, double[][] b){
        int a_colums = a[0].length;
        int b_rows = b.length;
        if(a_colums != b_rows) {
            System.out.println("������� �� ��������! ��������� ������ ���� �������.");
            return null;
        }
        int resRowLength = a.length;    // m result rows length
        int resColLength = b[0].length; // m result columns length
        double [][] res_matrix = new double[resRowLength][resColLength];
        for(int i = 0; i < resRowLength; i++) {         // rows from a
            for(int j = 0; j < resColLength; j++) {     // columns from b
                for(int k = 0; k < a_colums; k++) { // columns from a
                    res_matrix[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return res_matrix;
    }

    //�������� ������� �� �����
    public static double[][] mult(double[][] x, double y){
        for(int i = 0; i < x.length; i++) {         // rows from a
            for(int j = 0; j < x[0].length; j++) {  // columns from b
                x[i][j] *= y;
            }
        }
        return x;
    }

    //��������� ��������� �������
    public static void print_matrix(double A[][]){
        for(int i = 0; i < A.length; i++){
            for(int j = 0; j < A[0].length; j++){
                System.out.printf("%.2f \t\t", A[i][j]);
            }
            System.out.println();
        }
    }

    //�������������� �������
    public static double[][] transpose(double [][]a){
        double [][] transpose_m = new double [a.length][a[0].length];
        for (int i = 0; i < a.length; i++)  {
            for (int j = 0; j < a[0].length; j++)
                transpose_m[i][j] = a[j][i];
        }
        return transpose_m;
    }

    //�������� �������
    public static double[][] minus(double[][] x, double[][] y){
        for(int i = 0; i < x.length; i++) {
            for(int j = 0; j < x[0].length; j++) {
                x[i][j]-= y[i][j];
            }
        }
        return x;
    }

    //�������� ���������� ������� �� �����
    public static double[] mult(double[] x, double y){
        double[] res = new double[x.length];
        for(int i = 0; i < res.length; i++) {
                res[i] = x[i] * y;
            }
        return res;
    }

    //������ ���������� ������� �� �����
    public static double[] divide(double[] x, double y){
        double[] res = new double[x.length];
        for(int i = 0; i < res.length; i++) {
                res[i] = x[i] / y;
            }
        return res;
    }

    //������� ����������� �������
    public static double[] minus(double[] x, double[] y){
        double[] res = new double[x.length];
        for(int i = 0; i < res.length; i++) {
                res[i] = x[i] - y[i];
            }
        return res;
    }

    //��������, �� ������� ������ 2^k
    public static boolean if_size_2k(double [][]a){
        if( (a.length != a[0].length) || (a.length & (a.length - 1)) != 0 ){
            return false;
        }
        else{
            return true;
        }
    }

    //��������, �� ������� ���������
    public static boolean if_size_square(double [][]a){
        if( (a.length == a[0].length) ){
            return true;
        }
        else{
            return false;
        }
    } 
    
}
