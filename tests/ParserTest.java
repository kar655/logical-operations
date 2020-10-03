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

    public void doubleCompare(Expression e1, Expression e2) {
        assertEquals(e1.toString(), e2.toString());
        assertEquals(e1, e2);
    }

    @Test
    public void test_simple() {
        Expression expected = a.or(b.and(c));
        Expression result = parser.parseLine("a | b & c");
        doubleCompare(expected, result);
    }

    @Test
    public void test_negation() {
        Expression expected = a.or(b.neg());
        Expression result = parser.parseLine("a | ~ b");
        doubleCompare(expected, result);
    }

    @Test
    public void test_parentheses() {
        Expression expected = (a.or(b)).and(a.neg());
        Expression result = parser.parseLine("( a | b ) & ~ a");
        doubleCompare(expected, result);

        expected = a.and(b).or(c);
        result = parser.parseLine("( ( a & b ) | c )");
        doubleCompare(expected, result);
    }

    @Test
    public void test_parentheses_neg() {
        Expression expected = (a.and(b.neg())).neg().or(b);
        Expression result = parser.parseLine("~ ( a & ~ b ) | b");
        doubleCompare(expected, result);

        expected = (a.or((b.and(c)).neg())).neg();
        result = parser.parseLine("~ ( a | ( ~ ( b & c ) ) )");
        doubleCompare(expected, result);
    }
}