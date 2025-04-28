package com.jiangying.functionCall;

import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

@Component
class MathCalculator {

    @Tool
    double add(int a, int b) {
        return a + b;
    }

    @Tool
    double squareRoot(double x) {
        return Math.sqrt(x);
    }
}