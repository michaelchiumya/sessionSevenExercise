/**
 *
 * @author seank This Java application allows the experimentation with the Java
 * Fork Join Framework
 */
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ForkJoinTester {

    private static double[] testArray;
    private static String systemProperty = "java.util.concurrent.ForkJoinPool.common.parallelism";

    public static void main(String[] args) {

        int rnNumber;
        int arraySize = 100000000;
        testArray = new double[arraySize];

        Random rn = new Random();

        for (int i = 0; i < testArray.length; i++) {
            rnNumber = rn.nextInt(1000000 - 10 + 1) + 1;
            testArray[i] = rnNumber;
        }

        System.setProperty(systemProperty, "2");

        for (int executeEvent = 0; executeEvent < 10; executeEvent++) {
            System.out.printf("\nComparison Event %d\n", executeEvent + 1);
            arraySumSequential(testArray);
            arraySumParallel(testArray);
        }
    }

    private static double arraySumSequential(double[] arr) {

        long startPoint = System.nanoTime();
        double arraySum = 0.0;

        for (int i = 0; i < arr.length; i++) {
            arraySum += 1 / arr[i];
        }

        long nanoRunTime = System.nanoTime() - startPoint;
        printOutcome("SEQUENTIAL        ", nanoRunTime, arraySum);
        return arraySum;
    }

    private static double arraySumParallel(double[] arr) {

        long startPoint = System.nanoTime();
        ArraySum s = new ArraySum(arr, 0, arr.length);
        ForkJoinPool.commonPool().invoke(s);
        double sumFromArraySum = s.answer;
        long nanoRunTime = System.nanoTime() - startPoint;
        printOutcome("PARALLEL_FORK_JOIN", nanoRunTime, sumFromArraySum);
        return sumFromArraySum;
    }

    private static class ArraySum extends RecursiveAction {

        static int THRESHOLD_SEQUENTIAL = 10;
        int high;
        int low;
        double[] arr;
        double answer = 0;

        ArraySum(double[] a, int l, int h) {
            arr = a;
            high = h;
            low = l;
        }

        // We must override the abstract method compute()- - the main
        // computation performed by the task
        protected void compute() {
            if (high - low <= THRESHOLD_SEQUENTIAL) {
                for (int i = low; i < high; i++) {
                    answer += 1 / arr[i];
                }
            } else {
                ArraySum left = new ArraySum(arr, low, (high + low) / 2);
                ArraySum right = new ArraySum(arr, (high + low) / 2, high);
                left.fork();
                right.compute();
                left.join();

                answer = left.answer + right.answer;
            }
        }
    }

    private static void printOutcome(String label, long runTime, double sum) {
        System.out.printf(" %s process runtime was:  %8.3f milliseconds with final sum as: %8.5f \n", label, runTime / 1e6, sum);
    }

}