import logicalOperations.Expression;
import logicalOperations.Variable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import parsing.Parser;

import static org.junit.Assert.*;

public class ParserTest {

    private Expression a = Variable.give("a");
    private Expression b = Variable.give("b");
    private Expression c = Variable.give("c");
    private Parser parser = new Parser();

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test_whitespaces() {
        // TODO
    }

    @Test
    public void test_simple() {
        Expression expected = a.or(b.and(c));
        Expression result = parser.parseLine("a | b & c");
        assertEquals(expected.toString(), result.toString());
        assertEquals(expected, result);
    }

    @Test
    public void test_expression_plus_tautology() {
        Expression e1 = a.or(b);
        Expression e2 = (a.or(b)).and(a.or(a.neg()));

        assertEquals(true, e1.equals(e2));
    }
}