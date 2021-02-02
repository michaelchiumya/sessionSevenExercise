//Using Recursion to calcu;ate a Fibonacci Sequence

public class FibonacciCalculator{

    public static void main(String args[]) {
        int endNumber = 20;

        System.out.print("Fibonacci Series of "+endNumber+" numbers: ");
        for(int i = 0; i < endNumber; i++){
            System.out.print(recursiveFibonacci(i) +" ");
        }
    }


    public static int recursiveFibonacci(int num){
        if(num == 0){
            return 0;
        }
        if(num == 1 || num == 2){
            return 1;
        }
        return recursiveFibonacci(num-2) + recursiveFibonacci(num-1);
    }


}