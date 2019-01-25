package example2;

import sample.Description;

import java.util.Random;

public class NotCallableSecond {


    public double findMean(double firstNumber, double secondNumber) {
        double result=(firstNumber+secondNumber)/2;
        System.out.println("Mean of two numbers is : ( " + firstNumber + " + " + secondNumber + " ) --- Result = " + result);
        return result;
    }

    @Description(description = "Generating a random integer number from <a,b> range")
    public double generateRandomFromRange(double firstNumber, double secondNumber) {
        Random random = new Random();
        double result=random.nextInt((int)(secondNumber-firstNumber+1))+firstNumber;
        System.out.println("First number power second number : ( " + firstNumber + " + " + secondNumber + " ) --- Result = " + result);
        return result;
    }



}

