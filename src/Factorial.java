import java.util.Scanner;

/**
 *
 * @author seank - This program demonstrates the calculation of a Factorial
 * value by using recursion
 *
 */
public class Factorial {

    public static void main(String[] args) {

        int n;

        Scanner s = new Scanner(System.in);
        System.out.print("Enter an integer to calculate the Factorial value :");

        n = s.nextInt();

        System.out.println("Factorial of "+ n + " is " + factorialUsingRecursion(n));
        System.out.println("Factorial iterative of "+ n + " is " + factorialUsingIterative(n));

    }


    public static long factorialUsingRecursion(int n) {
        // This is the 'exit condition' for our recursive method
        if (n <= 2) {
            return n;
        }
        return n * factorialUsingRecursion(n - 1);
    }

  //alternative factorial method using iteration
    public static long factorialUsingIterative(int n) {

            //set variable to equal 1
            int  result = 1;

            //loop n times and minus one from n each time
            for(int i = 0; i < n; n--){

                //multiply result by n to get value of result
                result *= n;
            }
            //return result
            return result;
        }


}