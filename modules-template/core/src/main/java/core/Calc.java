package core;

import java.util.ArrayList;
import java.util.List;

public class Calc {
    
    private final List<Double> operandStack;

    public Calc() {
        operandStack = new ArrayList<>(2);
        operandStack.add(20.0);
    }
}