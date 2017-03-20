package ro.mdc_software.basenumber;

/**
 * String numbers that implement {@link Number} and also expose actually the parts of the base number.
 *
 * @author Cosmin Mihu
 * @date 20-March-2017
 */
/* package */ class StringNumber extends MathOperations {

    /**
     * Number representation stored as string. The current base number is {@link #base}.
     * It is used this approach to handle large numbers that cannot fit into <code>int</code> or <code>long</code>.
     */
    private String number;

    /**
     * The base of the {@link #number}.
     */
    private int base;

    //region Getters

    /**
     * Getters.
     */
    public final String getNumber() {
        return this.number;
    }

    public final int getBase() {
        return this.base;
    }
    //endregion

    //region Setters
    public void setNumber(String number) {
        this.number = number;
    }

    public void setBase(int value) {
        this.base = value;
    }
    //endregion

    //region Constructors
    protected StringNumber(Object value) throws BaseNumberException {
        this(value, 10);
    }

    protected StringNumber() throws BaseNumberException {
        this("0", 10);
    }

    protected StringNumber(StringNumber number) {
        this.setNumber(number.getNumber());
        this.setBase(number.getBase());
    }

    protected StringNumber(Object value, int base) throws BaseNumberException {
        validate(value, base);

        setNumber(value.toString().toUpperCase());
        setBase(base);

        normalize();
    }
    //endregion


    @Override
    public int intValue() {
        return Integer.parseInt(number, base);
    }

    @Override
    public long longValue() {
        return Long.parseLong(number, base);
    }

    @Override
    public float floatValue() {
        throw new NumberFormatException("The number cannot be converted to float.");
    }

    @Override
    public double doubleValue() {
        throw new NumberFormatException("The number cannot be converted to double.");
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new StringNumber(this);
    }

    @Override
    public String toString() {
        return this.getNumber() + "(" + (new Integer(this.getBase())).toString()
                + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StringNumber)) {
            return false;
        }

        StringNumber that = (StringNumber) o;
        return getBase() == that.getBase() && (getNumber() != null ? getNumber().equals(that.getNumber()) : that.getNumber() == null);
    }

    @Override
    public int hashCode() {
        int result = getNumber() != null ? getNumber().hashCode() : 0;
        result = 31 * result + getBase();
        return result;
    }

    private static void validate(Object value, int base) throws BaseNumberException {
        if (!(value instanceof String || value instanceof Integer || value instanceof Long)) {
            throw new BaseNumberException("Number must be string or integer or long.");
        }
        if (value.toString().length() <= 0) {
            throw new BaseNumberException("Number must be string or integer or long, not null.");
        }
        if (base > getBaseDigits().length()) {
            throw new BaseNumberException("Number's base can not be greater than " + getBaseDigits().length() + ".");
        }

        String aux = value.toString().toUpperCase();
        int i = (aux.charAt(0) != '-') ? 0 : 1;
        for (; i < aux.length(); i++) {
            if (getBaseDigits().indexOf(aux.charAt(i)) == -1 || getBaseDigits().indexOf(aux.charAt(i)) >= base) {
                throw new BaseNumberException("The digit '" + aux.charAt(i) + "' does not exist in base " + (new Integer(base)).toString() + ".");
            }
        }
    }

    private void normalize() {
        int ok;
        String number = this.getNumber();
        if (number.charAt(0) == '-' && number.length() > 1) {
            ok = 1;
            this.setNumber(number.substring(1));
        } else {
            ok = 0;
        }

        while (number.length() > 1 && number.charAt(0) == '0') {
            this.setNumber(number.substring(1));
        }
        if (ok == 1 && !number.equals("0")) {
            this.setNumber("-" + number);
        }
    }
}
