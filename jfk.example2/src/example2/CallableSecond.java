package example2;

import sample.Description;
import sample.IArithmeticOperation;

public class CallableSecond implements IArithmeticOperation {

    @Description(description = "Method divides two numbers.")
    @Override
    public double doFirstOperation(double firstNumber, double secondNumber) {
        if(secondNumber==0)
            throw new ArithmeticException("Dividing by 0");
        System.out.println("Dividing first number by second : ( " + firstNumber + " / " + secondNumber + " ) --- Result = " + (firstNumber/secondNumber));
        return firstNumber/secondNumber;
    }

    @Description(description = "Method multiplies two numbers.")
    @Override
    public double doSecondOperation(double firstNumber, double secondNumber) {
        System.out.println("Multiplying two numbers : ( " + firstNumber + " * " + secondNumber + " ) --- Result = " + (firstNumber*secondNumber));
        return firstNumber * secondNumber;
    }

    @Description(description = "Method finds minimum value from two numbers.")
    @Override
    public double doThirdOperation(double firstNumber, double secondNumber) {
        System.out.println("Finding minimum value from two numbers : ( " + firstNumber + " , " + secondNumber + " ) --- Result = " + (Math.min(firstNumber,secondNumber)));
        return Math.min(firstNumber, secondNumber);
    }
}
