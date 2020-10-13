import logicalOperations.Expression;
import logicalOperations.False;
import logicalOperations.True;
import logicalOperations.Variable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import parsing.*;

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

    public void doubleCompare(Expression e1, Expression e2) {
        assertEquals(e1.toString(), e2.toString());
        assertEquals(e1, e2);
    }

    @Test
    public void test_whitespaces() {
        Expression expected = a.or(b.neg().and(a.neg()));
        Expression result = parser.parserHelper("a\t|     ( ~\nb & ~ a )");
        doubleCompare(expected, result);
    }

    @Test
    public void test_simple() {
        Expression expected = a.or(b.and(c));
        Expression result = parser.parserHelper("a | b & c");
        doubleCompare(expected, result);
    }

    @Test
    public void test_negation() {
        Expression expected = a.or(b.neg());
        Expression result = parser.parserHelper("a | ~ b");
        doubleCompare(expected, result);
    }

    @Test
    public void test_parentheses() {
        Expression expected = (a.or(b)).and(a.neg());
        Expression result = parser.parserHelper("( a | b ) & ~ a");
        doubleCompare(expected, result);

        expected = a.and(b).or(c);
        result = parser.parserHelper("( ( a & b ) | c )");
        doubleCompare(expected, result);
    }

    @Test
    public void test_parentheses_neg() {
        Expression expected = (a.and(b.neg())).neg().or(b);
        Expression result = parser.parserHelper("~ ( a & ~ b ) | b");
        doubleCompare(expected, result);

        expected = (a.or((b.and(c)).neg())).neg();
        result = parser.parserHelper("~ ( a | ( ~ ( b & c ) ) )");
        doubleCompare(expected, result);
    }

    @Test(expected = NegationError.class)
    public void test_negation_error() {
        Expression e = parser.parserHelper("a | ~");
    }

    @Test(expected = ParserError.class)
    public void test_parentheses_error() {
        parser.parserHelper("a ( & b )");
    }

    @Test(expected = ParenthesesError.class)
    public void test_checkParentheses() {
        Expression e = parser.parserHelper(") a (");
    }

    @Test(expected = VariableError.class)
    public void test_variableError() {
        Expression e = parser.parserHelper("q w");
    }

    @Test
    public void test_trueRead() {
        Expression result = parser.parserHelper("a | T");
        doubleCompare(result, True.getInstance());
    }

    @Test
    public void test_falseRead() {
        Expression result = parser.parserHelper("a & F");
        doubleCompare(result, False.getInstance());
    }

    @Test(expected = ParsingElementsError.class)
    public void test_nullLastExpression() {
        Expression e = parser.parserHelper("a & _");
    }

    @Test
    public void test_lastExpressionSimple() {
        Expression e = parser.parserHelper("a");
        doubleCompare(e, a);
        e = parser.parserHelper("_ | ~ _");
        doubleCompare(e, a.or(a.neg()));
    }

    @Test
    public void test_multiParenthesesNegation() {
        Expression result = parser.parserHelper("~ ( ( ~ ( a ) ) )");
        doubleCompare(result, a);
    }
}