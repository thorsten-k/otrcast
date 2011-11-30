package net.sf.otrcutmp4.test;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class IgnoreOtherRule implements TestRule
{
    private String applyMethod;
    
    public IgnoreOtherRule(String applyMethod)
    {
        this.applyMethod = applyMethod;
    }
    
    @Override
    public Statement apply(final Statement statement, final Description description)
    {
        return new Statement()
        {
            @Override
            public void evaluate() throws Throwable {
                if (applyMethod.equals(description.getMethodName())) {
                    statement.evaluate();
                }
            }
        };
    }
}