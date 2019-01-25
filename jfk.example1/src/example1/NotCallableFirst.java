package example1;

import sample.Description;

public class NotCallableFirst {

    @Description(description = "Concatenating two numbers")
    public double concatenateNumbers(double firstNumber, double secondNumber) {
        double result=Double.valueOf(String.valueOf(firstNumber)+String.valueOf(secondNumber));
        System.out.println("Concatenating two numbers : ( " + firstNumber + " + " + secondNumber + " ) --- Result = " + result);
        return result;
    }

    public double powerNumbers(double firstNumber, double secondNumber) {
        double result=Math.pow(firstNumber,secondNumber);
        System.out.println("First number power second number : ( " + firstNumber + " + " + secondNumber + " ) --- Result = " + result);
        return result;
    }



}
