import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ForkJoinTesterAdaptation {

    //array size initialization
    static int arraySize = 100000;

    //array initialization
    private static double[] testArray = new double[arraySize];
    private static String systemProperty = "java.util.concurrent.ForkJoinPool.common.parallelism";

    public static void main(String[] args){
        System.setProperty(systemProperty, "2");

        //create 3 events
        for (int executeEvent = 0; executeEvent < 3; executeEvent++) {
            System.out.printf("\nComparison Event %d\n", executeEvent + 1);
              arraySumSequential(testArray);
               arraySumParallel(testArray);
        }
    }

    private static void arraySumParallel(double[] testArray) {
        long startPoint = System.nanoTime();
        AddPrime addPrime = new AddPrime(testArray, 0, testArray.length);
        ForkJoinPool.commonPool().invoke(addPrime);
        long nanoRunTime = System.nanoTime() - startPoint;
        printOutcome("PARALLEL_FORK_JOIN", nanoRunTime);
    }

    private static void arraySumSequential(double[] testArray) {
        long startPoint = System.nanoTime();
        int count = 0;
        for (int i = 0; i  < testArray.length ; i++) {

            //check if array is full and if number is prime
               if(!isFull() && isPrime(i)){
                   //add prime to array
                   testArray[count++] = i;
               }
        }
        //get run time
        long nanoRunTime = System.nanoTime() - startPoint;
        printOutcome("SEQUENTIAL_FORK_JOIN", nanoRunTime);
    }

    //method to check if array is empty
    private static boolean isFull() {
        for (int x = 0; x < testArray.length; x++) {
            if (x == testArray.length - 1){
                return false;
            }
        }
        return true;
    }


    private static void printOutcome(String label, long runTime) {
        System.out.printf(" %s process runtime was:  %8.3f milliseconds \n", label, runTime / 1e6);
    }

   //method to check if number is prime
    public static boolean isPrime(int numberToCheck) {
        for (int i = 2; i <= numberToCheck / 2; i++) {
            if (numberToCheck % i == 0) { return false;}
        }
        return true;
    }
}


class AddPrime extends RecursiveAction {

    int high;
    int low;
    double[] arr;

    AddPrime(double[] a, int l, int h) {
        arr = a;
        high = h;
        low = l;
    }

    //override compute from RecursiveAction
    protected void compute() {

     //counter for indexing array
     int count = 0;

        if (high - low <= arr.length) {
            for (int i = low; i < high; i++) {

                if(!isFull() && isPrime(i)){
                    arr[count++] = i;
                }
            }
        }
          else{
                AddPrime left = new AddPrime(arr, low, (high + low) / 2);
                AddPrime right = new AddPrime(arr, (high + low) / 2, high);
                left.fork();
                right.compute();
               left.join();
             }
    }

    public static boolean isPrime(int numberToCheck) {
        for (int i = 2; i <= numberToCheck / 2; i++) {
            if (numberToCheck % i == 0) { return false;}
        }
        return true;
    }

    public boolean isFull() {
        for (int x = 0; x < arr.length; x++) {
            if (x == arr.length - 1){ return false; }
        }
        return true;
    }

}







