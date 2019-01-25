package example1;

import sample.Description;
import sample.IArithmeticOperation;

public class CallableFirst implements IArithmeticOperation {

    @Description(description = "Method adds two numbers.")
    @Override
    public double doFirstOperation(double firstNumber, double secondNumber) {
        double result=firstNumber+secondNumber;
        System.out.println("Adding two numbers : ( " + firstNumber + " + " + secondNumber + " ) --- Result = " + result);
        return result;
    }
    @Description(description = "Method subtracts two numbers")
    @Override
    public double doSecondOperation(double firstNumber, double secondNumber) {
        double result=firstNumber-secondNumber;
        System.out.println("Subtracting two numbers : ( " + firstNumber + " - " + secondNumber + " ) --- Result = " + result);
        return result;

    }
    @Description(description = "Method finds max value from two numbers.")
    @Override
    public double doThirdOperation(double firstNumber, double secondNumber) {
        double result=Math.max(firstNumber,secondNumber);
        System.out.println("Finding max from two numbers : ( " + firstNumber + " , " + secondNumber + " ) --- Result = " + result);
        return result;
    }
}
