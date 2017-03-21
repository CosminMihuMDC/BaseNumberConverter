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
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void addition_das() throws Exception {

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
}