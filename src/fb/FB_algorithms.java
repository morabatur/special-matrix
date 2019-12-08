package fb;

import Basic_operation.Method;

//алгоритми з використанням прямого та зворотного ходів
public class FB_algorithms {
    //додавання одиничної матриці розміру як в А
    public static double[][] add_unit_matrix(double[][] A) {
        int n = A.length;
        double[][] unit_matrix = new double[n][n];
        double[][] extended_matrix = new double[n][n * 2];
        //створити діагональну матрицю
        for (int i = 0; i < unit_matrix.length; i++) {
            unit_matrix[i][i] = 1.0;
        }
        for (int i = 0; i < extended_matrix.length; i++) {
            System.arraycopy(A[i], 0, extended_matrix[i], 0, A[i].length);
            System.arraycopy(unit_matrix[i], 0, extended_matrix[i],extended_matrix[i].length / 2, unit_matrix[i].length);
        }
        return extended_matrix;
    }

    //перестановка рядків та стовпців
    public static double[][] swap(double[][] A, int n_it, int[] x_order) {
        int rows = A.length;
        int columns = A[0].length;
        int k_ = n_it - 1; //поточний крок - 1
        double res[][] = new double[rows][columns];//створити аналогічну пусту матрицю
        //скопіювати всі елементи в нову матрицю
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                res[i][j] = A[i][j];
            }
        }

        int number_row = -1;//ініціалізація невалідним значенням
        //почати проглядати рядки з кроку-1 який передали в метод проглядаючи лише один елмент в рядку
        for (int j = k_; j < rows; j++) {
            if (res[j][k_] != 0) {
                //якщо елемент, що знаходиться в якомусь із рядків на заданій позиції буде НЕ рівним нулю,
                //то записати значення рядка де він був виявлений в змінну
                number_row = j;
                break;
            }
        }
        //якщо нульовий елемент НЕ виявлено, тоді необхідно виконати перестановку рядків:
        if (number_row != -1) {
            //перестановка рядків n_row та k
            for (int j = 0; j < columns; j++) {
                //поміняти місцями елементи масиву
                double temp = res[k_][j];//тимчасова змінна для запам'ятовування елементу
                res[k_][j] = res[number_row][j];
                res[number_row][j] = temp;
            }
        } else {
            //перестановка стовпців
            //якщо елемент не було виявлено в рядках - необхідно перевірити стовпці
            int number_col = -1;
            for (int i = k_; i < columns - 1; i++) {
                if (res[k_][i] != 0) {
                    number_col = i;
                    break;
                }
            }
            if (number_col != -1) {
                //перестановка стовпців n_col та k
                for (int j = 0; j < rows; j++) {
                    double temp = res[j][k_];
                    res[j][k_] = res[j][number_col];
                    res[j][number_col] = temp;
                }
                //Цей блок необіхідний для того, щоб можна було відслідковувати які зміни СТОВПЦІВ ми виконали
                //Оскільки при перестановці рядків результат не буде змінений, а от при зміні СТОВПЦІВ - буде
                int tmp = x_order[number_col];
                x_order[number_col] = x_order[k_];
                x_order[k_] = tmp;
            }
        }
        return res;
    }

    //прямий хід
    public static double[][] forward(double[][] initial_matrix, int[] x_order) {
        double[][] A = new double[initial_matrix.length][initial_matrix[0].length];//створити пусту матрицю А такого розміру як і вхідна матриця
        int rows = A.length;//довжина рядків
        int columns = A[0].length;//довжина стовпців
        for (int i = 0; i < rows; i++) {//пройтись по всіх елементах вхідної матриці та записати їх в матрицю А
            for (int j = 0; j < columns; j++) {
                A[i][j] = initial_matrix[i][j];
            }
        }
        double akk;
        for (int k = 0; k < rows - 1; k++) { //кількість ітерацій
            if (k == 0) akk = 1;
            else
                akk = A[k - 1][k - 1];
            if (akk == 0.0) {
                A = swap(A, k, x_order);
                akk = A[k - 1][k - 1];
            }
            for (int i = k + 1; i < rows; i++) {
                for (int j = k + 1; j < columns; j++) {
                    A[i][j] = (A[i][j] * A[k][k] - A[i][k] * A[k][j]) / akk;
                }
                int d = 0;
                while (d < k + 1) {
                    A[i][d] = 0;
                    d++;
                }
            }
        }
        System.out.println("Результат прямого ходу: ");
        Method.print_matrix(A);
        return A;
    }

    //зворотний хід
    public static double[] backward(double[][] AF, int[] x_order) {
        int rows = AF.length;//кількість радяків в матриці
        int columns = AF[0].length;//кількість колонок в матриці
        double delta = AF[rows - 1][columns - 2];//обрати елемент дельта із вхідної матриці, він рівний останньому елементу в результуючій матриці (без матриці вільних членів)
        double[] result_x = new double[columns - 1];//створити пустий масив рівний кількості стовпців вхідної матриці
        double[] result_delta = new double[columns - 1];//створити результуючий масив
        result_delta[rows - 1] = AF[rows - 1][columns - 1];//записати останній елемент з вхідної матриці в результуючий масив
        //почати з передостанньої строки
        for (int k = rows - 2; k >= 0; k--) {
            result_delta[k] = delta * AF[k][columns - 1];//дельта помножити на останній елемент строки К (без вільних членів)
            //columns - 2: означає, що ми беремо останню колонку матрицю (там де Xn - останння невідома)
            for (int j = columns - 2; j > k; j--) {
                result_delta[k] -= AF[k][j] * result_delta[j];//взяти вільний член з поточного кроку та розрахувати його нове значення:
                                                                // вільний член з поточного кроку - вільний член з поточного кроку * дельта
            }

            result_delta[k] /= AF[k][k];//поділити вільний член з поточного кроку на елемент головної діагоналі на цьому рядку
        }
        //відповідь
        for (int i = 0; i < result_delta.length; i++) {
            result_x[x_order[i]] = result_delta[i] / delta;//масив результуючих значень поділити на дельту
        }
        System.out.println("Відповідь:");
        for (int i = 0; i < result_delta.length; i++) {
            System.out.println("x[" + (i + 1) + "] = " + result_x[i]);
        }
        return result_x;
    }

    //зворотний хід при інверсії
    public static double[][] inv_backward(double[][] AF) {
        double delta = AF[AF.length - 1][(AF[0].length / 2) - 1];//взяти останній елемент не приєднаної матриці (остання строка, елемент(довжина рядка / 2 - 1)
        System.out.println("delta = " + delta);
        double[][] result_delta = new double[AF.length][AF.length];
        //копіювати останній рядок вхідної матриці в останній рядок матриці result_delta
        System.arraycopy(AF[AF.length - 1],AF[0].length / 2, result_delta[result_delta.length - 1],0, result_delta[result_delta.length - 1].length);
        //почати з прохід з передостаннього рядку матриці result_delta
        for (int k = result_delta.length - 2; k >= 0; k--) {
            double left_m[] = new double[result_delta.length];//тимчасова матриця
            //копіювати рядок з приєднаної матриці в матрицю left_m
            System.arraycopy(AF[k],AF[k].length / 2, left_m,0,left_m.length);
            //домножити кожен елемент матриці на дельту
            result_delta[k] = Method.mult(left_m, delta);
            //Залжено від кількості НЕ НУЛЬОВИХ елементів в рядку виконати віднімання  матриць
            for (int j = result_delta.length - 1; j > k; j--) {
                result_delta[k] = Method.minus(result_delta[k], Method.mult(result_delta[j], AF[k][j]));
            }
            //поділити поточний рядок на елемент з головної діагоналі
            result_delta[k] = Method.divide(result_delta[k], AF[k][k]);
        }
        //поділити кожен елемент на дельту
        for (int i = 0; i < result_delta.length; i++) {
            for (int j = 0; j < result_delta.length; j++) {
                result_delta[i][j] /= delta;
            }
        }
        return result_delta;
    }

    //інверсія квадратної матриці з використаням методу прямого і зворотного ходів
    public static double[][] inv_FB(double[][] A) {
        int[] x_order = new int[A.length];
        for (int i = 0; i < x_order.length; i++) {
            x_order[i] = i;
        }
        double[][] AI = add_unit_matrix(A);
        double[][] AIF = forward(AI, x_order);
        double[][] B = inv_backward(AIF);
        return B;
    }

    //множення матриці на вектор х, перевірка рішення СЛАР
    public static void chek_SLAE(double[] x, double[][] A) {
        for (int i = 0; i < A.length; i++) {
            double sum = 0.0;
            System.out.print("sum(   ");
            for (int j = 0; j < A.length; j++) {
                sum += x[j] * A[i][j];
                System.out.print(x[j] + "*" + A[i][j] + "   ");
            }
            System.out.println(") = " + sum);
        }
    }

}
    

    
    


