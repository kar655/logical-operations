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
    public void test_negation() {
        Expression expected = a.or(b.neg());
        Expression result = parser.parseLine("a | ~ b");
        assertEquals(expected.toString(), result.toString());
        assertEquals(expected, result);
    }
}