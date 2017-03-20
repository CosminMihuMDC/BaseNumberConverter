package ro.mdc_software.basenumber;

/**
 * Primitive operations with math numbers as Strings.
 *
 * @author Cosmin Mihu
 * @date 20-March-2017
 */
/* package */ abstract class MathOperations extends Number {

    private static String BASE_DIGITS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    protected static String getBaseDigits() {
        return BASE_DIGITS;
    }

    // Pre:
    // nr1,nr2
    // > 0
    // numere
    // in
    // baza
    // base
    protected static String add(String nr1, String nr2, int baseNumber) {
        String result = "";

        while (nr1.length() < nr2.length()) {
            nr1 = "0" + nr1;
        }
        while (nr2.length() < nr1.length()) {
            nr2 = "0" + nr2;
        }

        int t = 0, suma;
        for (int c = nr1.length() - 1; c >= 0; c--) {
            suma = getBaseDigits().indexOf(nr1.charAt(c))
                    + getBaseDigits().indexOf(nr2.charAt(c)) + t;
            result = getBaseDigits().charAt(suma % baseNumber) + result;
            t = suma / baseNumber;
        }
        if (t != 0) {
            result = (new Integer(t)).toString() + result;
        }
        while (result.length() > 1 && result.charAt(0) == '0') {
            result = result.substring(1);
        }
        return result;
    }

    // Pre:nr1>=nr2,
    // //Pre:
    // nr1,nr2
    // > 0
    // numere
    // in
    // baza
    // base
    protected static String sub(String nr1, String nr2, int baseNumber) {
        while (nr1.length() != nr2.length()) {
            nr2 = "0" + nr2;
        }

        int t = 0, s, d;
        String result = "";
        for (int i = nr1.length() - 1; i >= 0; i--) {
            s = getBaseDigits().indexOf(nr1.charAt(i)) - t;
            d = getBaseDigits().indexOf(nr2.charAt(i));
            t = 0;

            if (s < d) {
                s = s + baseNumber;
                t = 1;
            }
            result = getBaseDigits().charAt(s - d) + result;
        }
        while (result.length() > 1 && result.charAt(0) == '0') {
            result = result.substring(1);
        }
        return result;
    }

    // Pre:
    // nr1,nr2
    // >
    // 0
    // numere
    // in
    // baza
    // base
    protected static String mulOne(String nr1, char nr2, int baseNumber) {
        if (nr2 == '0') {
            return "0";
        }
        if (nr2 == '1') {
            return nr1;
        }

        String result = "";
        int c = getBaseDigits().indexOf(nr2);
        int t = 0, produs;

        for (int i = nr1.length() - 1; i >= 0; i--) {
            produs = getBaseDigits().indexOf(nr1.charAt(i)) * c + t;
            result = getBaseDigits().charAt(produs % baseNumber) + result;
            t = produs / baseNumber;
        }
        if (t != 0) {
            result = (new Integer(t)).toString() + result;
        }
        while (result.length() > 1 && result.charAt(0) == '0') {
            result = result.substring(1);
        }
        return result;
    }

    protected static String mul(String nr1, String nr2, int baseNumber) {
        String result = "0";
        for (int i = 0; i < nr2.length(); i++) {
            result = add(
                    mulOne(nr1, nr2.charAt(i), baseNumber), result
                            + "0", baseNumber);
        }
        while (result.length() > 1 && result.charAt(0) == '0') {
            result = result.substring(1);
        }
        return result;
    }

    protected static java.util.ArrayList<String> divOne(String nr1, char nr2,
                                                        int baseNumber) {
        String result = "";
        int imp = getBaseDigits().indexOf(nr2);
        int rest = getBaseDigits().indexOf(nr1.charAt(0));
        for (int i = 0; i < nr1.length() - 1; i++) {
            result += getBaseDigits().charAt(rest / imp);
            rest = baseNumber * (rest % imp)
                    + getBaseDigits().indexOf(nr1.charAt(i + 1));
        }
        result += getBaseDigits().charAt(rest / imp);
        String Rest = String.valueOf(getBaseDigits().charAt(rest % imp));

        while (result.length() > 1 && result.charAt(0) == '0') {
            result = result.substring(1);
        }

        java.util.ArrayList<String> x = new java.util.ArrayList<>();
        x.add(result);
        x.add(Rest);

        return x;
    }

    protected static java.util.ArrayList<String> div(String nr1, String nr2,
                                                     int baseNumber) {
        String result = "";

        String aux = Convert(nr2, baseNumber, 10);
        int imp = Integer.parseInt(aux);
        int rest = getBaseDigits().indexOf(nr1.charAt(0));
        for (int i = 0; i < nr1.length() - 1; i++) {
            result += getBaseDigits().charAt(rest / imp);
            rest = baseNumber * (rest % imp)
                    + getBaseDigits().indexOf(nr1.charAt(i + 1));
        }
        result += getBaseDigits().charAt(rest / imp);
        rest = rest % imp;

        while (result.length() > 1 && result.charAt(0) == '0') {
            result = result.substring(1);
        }
        String Rest = Convert((new Integer(rest)).toString(), 10,
                baseNumber);

        java.util.ArrayList<String> x = new java.util.ArrayList<>();
        x.add(result);
        x.add(Rest);

        return x;
    }

    // Pre: b<h
    protected static String convertMul(String nr, int b, int h) {
        String result = "0";
        String pow = "1", termen;
        for (int i = nr.length() - 1; i >= 0; i--) {
            termen = mul(pow, String.valueOf(nr.charAt(i)), h);
            result = add(result, termen, h);

            pow = mul(pow,
                    String.valueOf(getBaseDigits().charAt(b)), h);
        }
        return result;
    }

    // Pre: b>h
    protected static String convertDiv(String nr, int b, int h) {
        String result = "", cat = "";
        java.util.ArrayList<String> aux;

        while (!nr.equals("0")) {
            cat = "";
            String cifra = String.valueOf(nr.charAt(0));
            for (int c = 1; c < nr.length(); c++) {
                aux = divOne(cifra, getBaseDigits().charAt(h), b);
                cat += aux.get(0);

                cifra = add(
                        mul("10", aux.get(1).toString(), b),
                        String.valueOf(nr.charAt(c)), b);
            }
            aux = divOne(cifra, getBaseDigits().charAt(h), b);

            cat += aux.get(0);
            cifra = aux.get(1).toString();

            result = cifra + result;
            nr = cat;

            while (nr.length() > 1 && nr.charAt(0) == '0') {
                nr = nr.substring(1);
            }
        }
        return (result.equals("")) ? "0" : result;
    }

    protected static String Convert(String nr, int b, int h) {
        if (b == h) {
            return nr;
        } else if (b < h) {
            return convertMul(nr, b, h);
        } else {
            return convertDiv(nr, b, h);
        }
    }
}
