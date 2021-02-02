//Using Recursion to calculate a Fibonacci Sequence

import java.util.Scanner;

public class FibonacciCalculator{

    public static void main(String args[]) {




        System.out.print("enter end number......" + "\n");

        //scanner object to get user input
        Scanner scan =  new Scanner(System.in);

        //set variable endUser to user input value
        int endNumber = scan.nextInt();

        System.out.print("Fibonacci Series of "+endNumber+" numbers: ");

        /*multiply endNumber by 2 on the loop to go beyond end number
        * or we can set i < 22 if we want to get sequence up to 22 numbers
        **/
        for(int i = 0; i < endNumber*2; i++){

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