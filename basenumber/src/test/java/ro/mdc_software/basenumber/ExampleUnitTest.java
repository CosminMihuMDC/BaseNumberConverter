package ro.mdc_software.basenumber;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void test_equals() {

        BaseNumber nbr_234_10 = new BaseNumber(234, 10);
        BaseNumber nbr_EA_16 = new BaseNumber("EA", 16);
        assertEquals(nbr_234_10, nbr_EA_16);
        assertEquals(nbr_EA_16, nbr_234_10);

        BaseNumber nbr_234_10_clone = new BaseNumber("234", 10);
        assertEquals(nbr_234_10_clone, nbr_234_10);
        assertEquals(nbr_234_10, nbr_234_10_clone);

        BaseNumber nbr_261BKPIL9_26 = new BaseNumber("261BKPIL9", 26);
        BaseNumber nbr_466294192763_10 = new BaseNumber("466294192763", 10);
        assertTrue(nbr_466294192763_10.equals(nbr_261BKPIL9_26));

        // TODO error convert
//        BaseNumber nbr_minus_261BKPIL9_26 = new BaseNumber("-261BKPIL9", 26);
//        BaseNumber nbr_minus__466294192763_10 = new BaseNumber("-466294192763", 10);
//        assertEquals(nbr_minus_261BKPIL9_26, nbr_minus__466294192763_10);

        // TODO error convert
//        BaseNumber nbr_6C914CBA7B_16 = new BaseNumber("6C914CBA7B", 16);
//        assertEquals(nbr_261BKPIL9_26, nbr_6C914CBA7B_16);
//        assertEquals(nbr_466294192763_10, nbr_6C914CBA7B_16);
    }

    @Test
    public void test_add() {
        BaseNumber nbr_234_10 = new BaseNumber(234, 10);
        BaseNumber nbr_23_10 = new BaseNumber(23, 10);
        BaseNumber total = new BaseNumber(257, 10);
        assertEquals(total, nbr_234_10.add(nbr_23_10));

        BaseNumber nbr_A102F_16 = new BaseNumber("A102F", 16);
        BaseNumber nbr_23_16 = new BaseNumber(23, 16);
        total = new BaseNumber("a1052", 16);
        assertEquals(total, nbr_A102F_16.add(nbr_23_16));

        // TODO error
//        BaseNumber minus_nbr_A102F_16 = new BaseNumber("-A102F", 16);
//        total = new BaseNumber("a100c", 16);
//        assertEquals(total, nbr_23_16.add(minus_nbr_A102F_16));
    }

    @Test
    public void test_sub() {
        BaseNumber nbr_234_10 = new BaseNumber(234, 10);
        BaseNumber nbr_23_10 = new BaseNumber(23, 10);
        BaseNumber total = new BaseNumber(211, 10);
        assertEquals(total, nbr_234_10.sub(nbr_23_10));

        BaseNumber nbr_A102F_16 = new BaseNumber("A102F", 16);
        BaseNumber nbr_23_16 = new BaseNumber(23, 16);
        total = new BaseNumber("A100C", 16);
        assertEquals(total, nbr_A102F_16.sub(nbr_23_16));
    }

    @Test
    public void test_mul() {
        BaseNumber nbr_234_10 = new BaseNumber(234, 10);
        BaseNumber nbr_23_10 = new BaseNumber(23, 10);
        BaseNumber total = new BaseNumber(5382, 10);
        assertEquals(total, nbr_234_10.mul(nbr_23_10));

        BaseNumber nbr_EA_16 = new BaseNumber("EA", 16);
        BaseNumber nbr_17_16 = new BaseNumber(17, 16);
        total = new BaseNumber(1506, 16);
        assertEquals(total, nbr_EA_16.mul(nbr_17_16));
    }

    @Test
    public void test_div() {
        BaseNumber nbr_5382_10 = new BaseNumber(5382, 10);
        BaseNumber nbr_23_10 = new BaseNumber(23, 10);
        BaseNumber total = new BaseNumber(234, 10);
        assertEquals(total, nbr_5382_10.div(nbr_23_10));

        BaseNumber nbr_1506_16 = new BaseNumber(1506, 16);
        BaseNumber nbr_17_16 = new BaseNumber(17, 16);
        total = new BaseNumber("EA", 16);
        assertEquals(total, nbr_1506_16.div(nbr_17_16));
    }

    @Test
    public void test_modulo() {
        BaseNumber nbr_5382_10 = new BaseNumber(53822, 10);
        BaseNumber nbr_23_10 = new BaseNumber(23, 10);
        BaseNumber total = new BaseNumber(2, 10);
        assertEquals(total, nbr_5382_10.modulo(nbr_23_10));

        BaseNumber nbr_EA4F_16 = new BaseNumber("EA4F", 16);
        BaseNumber nbr_17_16 = new BaseNumber(17, 16);
        total = new BaseNumber("16", 16);
        assertEquals(total, nbr_EA4F_16.modulo(nbr_17_16));

    }

}