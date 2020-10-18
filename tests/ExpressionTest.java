import static org.junit.Assert.*;

import logicalOperations.Expression;
import logicalOperations.False;
import logicalOperations.True;
import logicalOperations.Variable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;


public class ExpressionTest {

    private Expression x0 = Variable.give("x0");
    private Expression x1 = Variable.give("x1");
    private Expression x2 = Variable.give("x2");
    private Expression T = True.getInstance();
    private Expression F = False.getInstance();

    private HashMap<String, Boolean> e = new HashMap<>();
    private HashMap<String, Boolean> t = new HashMap<>();
    private HashMap<String, Boolean> f = new HashMap<>();
    private HashMap<String, Boolean> tt = new HashMap<>();
    private HashMap<String, Boolean> tf = new HashMap<>();
    private HashMap<String, Boolean> ft = new HashMap<>();
    private HashMap<String, Boolean> ff = new HashMap<>();
    private HashMap<String, Boolean> ttt = new HashMap<>();
    private HashMap<String, Boolean> ttf = new HashMap<>();
    private HashMap<String, Boolean> tft = new HashMap<>();
    private HashMap<String, Boolean> tff = new HashMap<>();
    private HashMap<String, Boolean> ftt = new HashMap<>();
    private HashMap<String, Boolean> ftf = new HashMap<>();
    private HashMap<String, Boolean> fft = new HashMap<>();
    private HashMap<String, Boolean> fff = new HashMap<>();

    @Before
    public void setUp() throws Exception {
        t.put("x0", true);
        f.put("x0", false);

        tt.putAll(t);
        tt.put("x1", true);

        tf.putAll(t);
        tf.put("x1", false);

        ft.putAll(f);
        ft.put("x1", true);

        ff.putAll(f);
        ff.put("x1", false);

        ttt.putAll(tt);
        ttt.put("x2", true);

        ttf.putAll(tt);
        ttf.put("x2", false);

        tft.putAll(tf);
        tft.put("x2", true);

        tff.putAll(tf);
        tff.put("x2", false);

        ftt.putAll(ft);
        ftt.put("x2", true);

        ftf.putAll(ft);
        ftf.put("x2", false);

        fft.putAll(ff);
        fft.put("x2", false);

        fff.putAll(ff);
        fff.put("x2", false);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test_variables_simple() {
        assertEquals(true, x0.evaluate(t));
        assertEquals(false, x0.evaluate(f));
        assertEquals("x0", x0.toString());
        assertEquals(true, x1.evaluate(ft));
        assertEquals(false, x2.evaluate(ttf));
    }

    @Test
    public void test_stale() {
        assertEquals(true, T.evaluate(e));
        assertEquals(true, T.evaluate(ft));
        assertEquals("T", T.toString());

        assertEquals(false, F.evaluate(e));
        assertEquals(false, F.evaluate(tf));
        assertEquals("F", F.toString());
    }

    @Test
    public void test_or() {
        assertEquals(true, x0.or(T).evaluate(f));
        assertEquals(true, x0.or(T).evaluate(t));
        assertEquals("T", x0.or(T).toString());
        assertEquals(true, T.or(x0).evaluate(f));
        assertEquals(true, T.or(x0).evaluate(t));
        assertEquals("T", T.or(x0).toString());

        assertEquals(false, x0.or(F).evaluate(f));
        assertEquals(true, x0.or(F).evaluate(t));
        assertEquals("x0", x0.or(F).toString());
        assertEquals(false, F.or(x0).evaluate(f));
        assertEquals(true, F.or(x0).evaluate(t));
        assertEquals("x0", F.or(x0).toString());

        assertEquals(true, T.or(F).evaluate(e));
        assertEquals(true, F.or(T).evaluate(e));
        assertEquals("T", F.or(T).toString());
        assertEquals("T", F.or(T).toString());

    }

    @Test
    public void test_and() {
        assertEquals(true, x0.and(T).evaluate(t));
        assertEquals(false, x0.and(T).evaluate(f));
        assertEquals("x0", x0.and(T).toString());
        assertEquals(true, T.and(x0).evaluate(t));
        assertEquals(false, T.and(x0).evaluate(f));
        assertEquals("x0", T.and(x0).toString());

        assertEquals(false, x0.and(F).evaluate(t));
        assertEquals(false, x0.and(F).evaluate(f));
        assertEquals("F", x0.and(F).toString());
        assertEquals(false, F.and(x0).evaluate(t));
        assertEquals(false, F.and(x0).evaluate(f));
        assertEquals("F", F.and(x0).toString());

        assertEquals(false, T.and(F).evaluate(e));
        assertEquals(false, F.and(T).evaluate(e));
        assertEquals("F", T.and(F).toString());
        assertEquals("F", F.and(T).toString());
    }

    @Test
    public void test_xor() {
        assertEquals(false, x0.xor(T).evaluate(t));
        assertEquals(true, x0.xor(T).evaluate(f));
        assertEquals("x0^T", x0.xor(T).toString());
        assertEquals(false, T.xor(x0).evaluate(t));
        assertEquals(true, T.xor(x0).evaluate(f));
        assertEquals("T^x0", T.xor(x0).toString());

        assertEquals(true, x0.xor(F).evaluate(t));
        assertEquals(false, x0.xor(F).evaluate(f));
        assertEquals("x0^F", x0.xor(F).toString());
        assertEquals(true, F.xor(x0).evaluate(t));
        assertEquals(false, F.xor(x0).evaluate(f));
        assertEquals("F^x0", F.xor(x0).toString());

        assertEquals("F", F.xor(F).toString());
        assertEquals(false, F.xor(F).evaluate(e));
        assertEquals("F", T.xor(T).toString());
        assertEquals(false, T.xor(T).evaluate(e));
        assertEquals("T", T.xor(F).toString());
        assertEquals("T", F.xor(T).toString());


        assertEquals(true, T.xor(F).evaluate(e));
        assertEquals(true, F.xor(T).evaluate(e));
        assertEquals("T", T.xor(F).toString());
        assertEquals("T", F.xor(T).toString());
    }

    @Test
    public void test_neg() {
        assertEquals(false, x0.neg().evaluate(t));
        assertEquals(true, x0.neg().evaluate(f));
        assertEquals("~x0", x0.neg().toString());

        assertEquals(false, T.neg().evaluate(e));
        assertEquals(true, F.neg().evaluate(e));
        assertEquals("T", F.neg().toString());
        assertEquals("F", T.neg().toString());

        assertEquals("x0", x0.neg().neg().toString());
    }

    @Test
    public void test_simple_expressions() {
        Expression w1 = x0.or(x1);
        assertEquals("x0|x1", w1.toString());
        assertEquals(true, w1.evaluate(tf));
        assertEquals(true, w1.evaluate(ft));
        assertEquals(false, w1.evaluate(ff));

        Expression w2 = x0.xor(x1);
        assertEquals("x0^x1", w2.toString());

        Expression tautologia = x0.or(x0.neg());
        Expression sprzecznosc = x0.and(x0.neg());
        assertEquals("x0|~x0", tautologia.toString());
        assertEquals("x0&~x0", sprzecznosc.toString());
    }

    @Test
    public void test_priorities() {
        Expression w1 = x0.or(x1);
        Expression w2 = w1.and(x2);
        assertEquals("(x0|x1)&x2", w2.toString());

        assertEquals(true, w1.neg().evaluate(ff));
        assertEquals(false, w1.neg().evaluate(tf));
        assertEquals(false, w1.neg().evaluate(ft));
        assertEquals(false, w1.neg().evaluate(tt));
        assertEquals("~(x0|x1)", w1.neg().toString());


        Expression p1 = x2.or(x1.neg());
        assertEquals("x2|~x1", p1.toString());
        Expression p2 = x2.xor(x0.neg());
        assertEquals("x2^~x0", p2.toString());
        Expression w3 = p1.and(p2);
        assertEquals("(x2|~x1)&(x2^~x0)", w3.toString());
        assertEquals(true, w3.evaluate(ttt));
        assertEquals(false, w3.evaluate(ftt));

        assertEquals(false, w3.neg().evaluate(ttt));
        assertEquals("~((x2|~x1)&(x2^~x0))", w3.neg().toString());
    }

    @Test
    public void test_variables_names() {

    }


    @Test
    public void test_tautology_simple() {
        Expression w1 = x0.or(x0.neg());
        assertEquals("x0|~x0", w1.toString());
        assertEquals(true, w1.isTautology());

        Expression w2 = x0.and(x0.neg());
        assertEquals("x0&~x0", w2.toString());
        assertEquals(false, w2.isTautology());
    }

    @Test
    public void test_tautology() {
        Expression pierce = x0.imply(x1).imply(x0).imply(x0);

        //assertEquals("((x0->x1)->x0)->x0", pierce.toString());
        assertEquals(true, pierce.isTautology());

        Expression clavius = x0.imply(x0.neg()).imply(x0.neg());
        assertEquals("x0->~x0->~x0", clavius.toString());
        assertEquals(true, clavius.isTautology());

        Expression e1 = x0.imply(x1.and(x2))
                .imply((x0.imply(x1)).and(x0.imply(x2)));
        assertEquals("x0->x1&x2->(x0->x1)&(x0->x2)", e1.toString());
        assertEquals(true, e1.isTautology());
    }

    @Test
    public void test_equals() {
        assertEquals(true, x0.equals(x0));
        assertEquals(false, x0.equals(x1));
        assertEquals(false, x0.equals(null));

        Expression e1 = x0.or(x1);

        assertEquals(true, e1.equals(x1.or(x0)));
        assertEquals(false, e1.equals(x0.and(x1)));
    }

    @Test
    public void test_equals_plus_tautology() {
        Expression e1 = x0.or(x1);
        Expression e2 = (x0.or(x1)).and(x0.or(x0.neg()));

        assertEquals(true, e1.equals(e2));
    }

    @Test
    public void test_equiv() {
        assertEquals(true, x0.equiv(x0).isTautology());
        assertEquals(false, x0.equiv(x1).isTautology());
    }
}