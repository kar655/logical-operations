import static org.junit.Assert.*;

import logicalOperations.Expression;
import logicalOperations.False;
import logicalOperations.True;
import logicalOperations.Variable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Polecenie:
 * zaimplementuj projekt dotyczący Wyrażeń logicznych zawierający następujace klasy:
 *
 * Wyrazenie
 *    public boolean wartosc(boolean... wartosciowanie_zmiennych
 *          wyliczba wartość wyrażanie, przy założeniu, że:
 *             x_0 := wartosciowanie_zmiennych[0]
 *             x_1 := wartosciowanie_zmiennych[1]
 *             ...
 *    public String toString()
 *    public Wyrazenie neg()
 *    public Wyrazenie and(Wyrazenie arg)
 *    public Wyrazenie or(Wyrazenie arg)
 *    public Wyrazenie xor(Wyrazenie arg)
 * True
 *    public static True daj()
 * False
 *    public static False daj()
 * Zmienna
 *    public static Zmienna daj(int i) -> generuje zmienną x_i
 */


public class ExpressionTest {

    private Expression x0 = Variable.give(0);
    private Expression x1 = Variable.give(1);
    private Expression x2 = Variable.give(2);
    private Expression t = True.getInstance();
    private Expression f = False.getInstance();

    @Before
    public void setUp() throws Exception {}

    @After
    public void tearDown() throws Exception {}

    @Test
    public void test_zmienna() {
        assertEquals(true, x0.evaluate(true));
        assertEquals(false, x0.evaluate(false));
        assertEquals("x0", x0.toString());
        assertEquals(true, x1.evaluate(false, true));
        assertEquals(false, x2.evaluate(true, true, false));
    }

    @Test
    public void test_stale() {
        assertEquals(true, t.evaluate());
        assertEquals(true, t.evaluate(false, true));
        assertEquals("T", t.toString());

        assertEquals(false, f.evaluate());
        assertEquals(false, f.evaluate(true, false));
        assertEquals("F", f.toString());
    }

    @Test
    public void test_or() {
        assertEquals(true, x0.or(t).evaluate(false));
        assertEquals(true, x0.or(t).evaluate(true));
        assertEquals("T", x0.or(t).toString());
        assertEquals(true, t.or(x0).evaluate(false));
        assertEquals(true, t.or(x0).evaluate(true));
        assertEquals("T", t.or(x0).toString());

        assertEquals(false, x0.or(f).evaluate(false));
        assertEquals(true, x0.or(f).evaluate(true));
        assertEquals("x0", x0.or(f).toString());
        assertEquals(false, f.or(x0).evaluate(false));
        assertEquals(true, f.or(x0).evaluate(true));
        assertEquals("x0", f.or(x0).toString());

        assertEquals(true, t.or(f).evaluate());
        assertEquals(true, f.or(t).evaluate());
        assertEquals("T", f.or(t).toString());
        assertEquals("T", f.or(t).toString());

    }

    @Test
    public void test_and() {
        assertEquals(true, x0.and(t).evaluate(true));
        assertEquals(false, x0.and(t).evaluate(false));
        assertEquals("x0", x0.and(t).toString());
        assertEquals(true, t.and(x0).evaluate(true));
        assertEquals(false, t.and(x0).evaluate(false));
        assertEquals("x0", t.and(x0).toString());

        assertEquals(false, x0.and(f).evaluate(true));
        assertEquals(false, x0.and(f).evaluate(false));
        assertEquals("F", x0.and(f).toString());
        assertEquals(false, f.and(x0).evaluate(true));
        assertEquals(false, f.and(x0).evaluate(false));
        assertEquals("F", f.and(x0).toString());

        assertEquals(false, t.and(f).evaluate());
        assertEquals(false, f.and(t).evaluate());
        assertEquals("F", t.and(f).toString());
        assertEquals("F", f.and(t).toString());
    }

    @Test
    public void test_xor() {
        assertEquals(false, x0.xor(t).evaluate(true));
        assertEquals(true, x0.xor(t).evaluate(false));
        assertEquals("x0^T", x0.xor(t).toString());
        assertEquals(false, t.xor(x0).evaluate(true));
        assertEquals(true, t.xor(x0).evaluate(false));
        assertEquals("T^x0", t.xor(x0).toString());

        assertEquals(true, x0.xor(f).evaluate(true));
        assertEquals(false, x0.xor(f).evaluate(false));
        assertEquals("x0^F", x0.xor(f).toString());
        assertEquals(true, f.xor(x0).evaluate(true));
        assertEquals(false, f.xor(x0).evaluate(false));
        assertEquals("F^x0", f.xor(x0).toString());

        assertEquals("F", f.xor(f).toString());
        assertEquals(false, f.xor(f).evaluate());
        assertEquals("F", t.xor(t).toString());
        assertEquals(false, t.xor(t).evaluate());
        assertEquals("T", t.xor(f).toString());
        assertEquals("T", f.xor(t).toString());


        assertEquals(true, t.xor(f).evaluate());
        assertEquals(true, f.xor(t).evaluate());
        assertEquals("T", t.xor(f).toString());
        assertEquals("T", f.xor(t).toString());
    }

    @Test
    public void test_neg() {
        assertEquals(false, x0.neg().evaluate(true));
        assertEquals(true, x0.neg().evaluate(false));
        assertEquals("~x0", x0.neg().toString());

        assertEquals(false, t.neg().evaluate());
        assertEquals(true, f.neg().evaluate());
        assertEquals("T", f.neg().toString());
        assertEquals("F", t.neg().toString());

        assertEquals("x0", x0.neg().neg().toString());
    }

    @Test
    public void test_proste_wyrazenia(){
        Expression w1 = x0.or(x1);
        assertEquals("x0|x1", w1.toString());
        assertEquals(true, w1.evaluate(true,false));
        assertEquals(true, w1.evaluate(false,true));
        assertEquals(false, w1.evaluate(false,false));

        Expression w2 = x0.xor(x1);
        assertEquals("x0^x1", w2.toString());

        Expression tautologia = x0.or(x0.neg());
        Expression sprzecznosc = x0.and(x0.neg());
        assertEquals("x0|~x0", tautologia.toString());
        assertEquals("x0&~x0", sprzecznosc.toString());
    }

    @Test
    public void test_priorytety(){
        Expression w1 = x0.or(x1);
        Expression w2 = w1.and(x2);
        assertEquals("(x0|x1)&x2", w2.toString());

        assertEquals(true, w1.neg().evaluate(false, false));
        assertEquals(false, w1.neg().evaluate(true, false));
        assertEquals(false, w1.neg().evaluate(false, true));
        assertEquals(false, w1.neg().evaluate(true, true));
        assertEquals("~(x0|x1)", w1.neg().toString());


        Expression p1 = x2.or(x1.neg());
        assertEquals("x2|~x1", p1.toString());
        Expression p2 = x2.xor(x0.neg());
        assertEquals("x2^~x0", p2.toString());
        Expression w3 = p1.and(p2);
        assertEquals("(x2|~x1)&(x2^~x0)", w3.toString());
        assertEquals(true, w3.evaluate(true, true, true));
        assertEquals(false, w3.evaluate(false, true, true));

        assertEquals(false, w3.neg().evaluate(true, true, true));
        assertEquals("~((x2|~x1)&(x2^~x0))", w3.neg().toString());
    }

}