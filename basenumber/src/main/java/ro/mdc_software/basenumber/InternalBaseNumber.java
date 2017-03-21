package ro.mdc_software.basenumber;

/**
 * todo
 *
 * @author Cosmin Mihu
 */
public class InternalBaseNumber extends StringNumber {

    //region Default numbers (zero and one) in base <code>10</code>.
    public static InternalBaseNumber ZERO;
    public static InternalBaseNumber ONE;

    static {
        try {
            ZERO = new InternalBaseNumber(0);
            ONE = new InternalBaseNumber(1);
        } catch (Exception ignore) {
        }
    }
    //endregion

    //region Constructors

    /**
     * Create a number in base 10.
     *
     * @throws BaseNumberException
     */
    protected InternalBaseNumber(Object value) throws BaseNumberException {
        super(value, 10);
    }

    /**
     * Create ZERO in base 10.
     *
     * @throws BaseNumberException
     */
    protected InternalBaseNumber() throws BaseNumberException {
        super();
    }

    /**
     * Clone a number.
     *
     * @throws BaseNumberException
     */
    protected InternalBaseNumber(InternalBaseNumber number) {
        super(number);
    }

    /**
     * Create a new number.
     *
     * @throws BaseNumberException
     */
    protected InternalBaseNumber(Object value, int base) throws BaseNumberException {
        super(value, base);
    }
    //endregion

    //region addition | increment

    /**
     * Addition between <code>this</code> and <code>that</code>.
     * this = this + that
     *
     * @throws BaseNumberException
     */
    protected final void addition(InternalBaseNumber that) throws BaseNumberException {
        if (this.getBase() == that.getBase()) {
            InternalBaseNumber zero = new InternalBaseNumber("0", that.getBase());
            if (this.greaterThan(zero) && that.greaterThan(zero)) {
                this.setNumber(add(this.getNumber(), that.getNumber(), this.getBase()));
            } else {
                if (this.lessThan(zero) && that.lessThan(zero)) {
                    this.setNumber("-" + InternalBaseNumber.add(this.getNumber().substring(1), that.getNumber().substring(1), this.getBase()));
                } else {
                    if (this.greaterThan(zero) && that.lessThan(zero)) {
                        if (this.greaterThan(new InternalBaseNumber(that.getNumber().substring(1), that.getBase()))) {
                            this.setNumber(InternalBaseNumber.sub(this.getNumber(), that.getNumber().substring(1), this.getBase()));
                        } else {
                            this.setNumber("-" + InternalBaseNumber.sub(that.getNumber().substring(1), this.getNumber(), this.getBase()));
                        }
                    } else {
                        if (new InternalBaseNumber(this.getNumber().substring(1), this.getBase()).greaterThan(that)) {
                            this.setNumber("-" + InternalBaseNumber.sub(this.getNumber().substring(1), that.getNumber(), this.getBase()));
                        } else {
                            this.setNumber(InternalBaseNumber.sub(that.getNumber(), this.getNumber().substring(1), this.getBase()));
                        }
                    }
                }
            }

            this.normalize();
        } else {
            throw new BaseNumberException(
                    "It's not possible to addition 2 BaseNumbers in different bases: "
                            + (new Integer(this.getBase())).toString() + "!="
                            + (new Integer(that.getBase())).toString() + ".");
        }

    }

    //endregion

    //region subtract

    /**
     * Subtraction between <code>this</code> and <code>that</code>.
     * this = this - that
     *
     * @throws BaseNumberException
     */
    protected final void subtract(InternalBaseNumber that) throws BaseNumberException {
        if (this.getBase() == that.getBase()) {
            InternalBaseNumber zero = new InternalBaseNumber("0", that.getBase());
            if (this.greaterThan(zero) && that.lessThan(zero)) {
                this.setNumber(InternalBaseNumber.add(this.getNumber(), that.getNumber().substring(1), this.getBase()));
            } else {
                if (this.lessThan(zero) && that.greaterThan(zero)) {
                    this.setNumber("-" + InternalBaseNumber.add(this.getNumber().substring(1), that.getNumber(), this.getBase()));
                } else {
                    if (this.greaterThan(zero) && that.greaterThan(zero)) {
                        if (this.greaterThanOrEqual(that)) {
                            this.setNumber(InternalBaseNumber.sub(this.getNumber(), that.getNumber(), this.getBase()));
                        } else {
                            this.setNumber("-" + InternalBaseNumber.sub(that.getNumber(), this.getNumber(), this.getBase()));
                        }
                    } else {
                        if (new InternalBaseNumber(this.getNumber().substring(1), this.getBase()).lessThan(new InternalBaseNumber(that.getNumber().substring(1), that.getBase()))) {
                            this.setNumber(InternalBaseNumber.sub(that.getNumber().substring(1), this.getNumber().substring(1), this.getBase()));
                        } else {
                            this.setNumber("-" + InternalBaseNumber.sub(this.getNumber().substring(1), that.getNumber().substring(1), this.getBase()));
                        }
                    }
                }
            }
            this.normalize();
        } else {
            throw new BaseNumberException(
                    "It's not possible to substract 2 BaseNumbers in different bases: "
                            + (new Integer(this.getBase())).toString() + "!="
                            + (new Integer(that.getBase())).toString() + ".");
        }
    }
    //endregion

    //region multiply | orExclusive

    /**
     * Multiplication of <code>this</code> with <code>that</code>.
     * this = this * that
     *
     * @throws BaseNumberException
     */
    protected final void multiply(InternalBaseNumber that) throws BaseNumberException {
        if (this.getBase() == that.getBase()) {
            InternalBaseNumber zero = new InternalBaseNumber("0", that.getBase());
            if (this.greaterThanOrEqual(zero) && that.greaterThanOrEqual(zero)) {
                this.setNumber(InternalBaseNumber.mul(this.getNumber(), that.getNumber(), this.getBase()));
            } else if (this.lessThan(zero) && that.lessThan(zero)) {
                this.setNumber(InternalBaseNumber.mul(this.getNumber().substring(1), that.getNumber().substring(1), this.getBase()));
            } else if (this.lessThan(zero) && that.greaterThan(zero)) {
                this.setNumber("-" + InternalBaseNumber.mul(this.getNumber().substring(1), that.getNumber(), this.getBase()));
            } else if (this.greaterThan(zero) && that.lessThan(zero)) {
                this.setNumber("-" + InternalBaseNumber.mul(this.getNumber(), that.getNumber().substring(1), this.getBase()));
            }
        } else {
            throw new BaseNumberException(
                    "It's not possible to multiply 2 BaseNumbers in different basese: "
                            + (new Integer(this.getBase())).toString() + "!="
                            + (new Integer(that.getBase())).toString() + ".");
        }

    }

    //endregion

    //region divide | modulo

    /**
     * Division of <code>this</code> with <code>that</code>.
     * this = this / that
     *
     * @throws BaseNumberException
     */
    protected final void divide(InternalBaseNumber that) throws BaseNumberException {
        if (that.getNumber().equals("0")) {
            throw new BaseNumberException("It's not possible to divide by 0!");
        }
        if (this.getBase() == that.getBase()) {
            InternalBaseNumber zero = new InternalBaseNumber("0", that.getBase());
            if (this.greaterThanOrEqual(zero) && that.greaterThanOrEqual(zero)) {
                this.setNumber(InternalBaseNumber.div(this.getNumber(), that.getNumber(), this.getBase()).get(0));
            } else if (this.lessThan(zero) && that.lessThan(zero)) {
                this.setNumber(InternalBaseNumber.div(this.getNumber().substring(1), that.getNumber().substring(1), this.getBase()).get(0));
            } else if (this.lessThan(zero) && that.greaterThan(zero)) {
                this.setNumber("-" + InternalBaseNumber.div(this.getNumber().substring(1), that.getNumber(), this.getBase()).get(0));
            } else if (this.greaterThan(zero) && that.lessThan(zero)) {
                this.setNumber("-" + InternalBaseNumber.div(this.getNumber(), that.getNumber().substring(1), this.getBase()).get(0));
            }
        } else {
            throw new BaseNumberException(
                    "It's not possible to divide 2 BaseNumbers in different bases: "
                            + (new Integer(this.getBase())).toString() + "!="
                            + (new Integer(that.getBase())).toString() + ".");
        }
    }

    /**
     * Modulo of <code>this</code> with <code>that</code>.
     * this = this % that
     *
     * @throws BaseNumberException
     */
    protected final void modulo(InternalBaseNumber that) throws BaseNumberException {
        if (that.getNumber().equals("0")) {
            throw new BaseNumberException("It's not possible to divide by 0!");
        }
        if (this.getBase() == that.getBase()) {
            InternalBaseNumber zero = new InternalBaseNumber("0", that.getBase());
            if (this.greaterThanOrEqual(zero) && that.greaterThanOrEqual(zero)) {
                this.setNumber(InternalBaseNumber.div(this.getNumber(), that.getNumber(), this.getBase()).get(1));
            } else if (this.lessThan(zero) && that.lessThan(zero)) {
                this.setNumber(InternalBaseNumber.div(this.getNumber().substring(1), that.getNumber().substring(1), this.getBase()).get(1));
            } else if (this.lessThan(zero) && that.greaterThan(zero)) {
                this.setNumber(InternalBaseNumber.div(this.getNumber().substring(1), that.getNumber(), this.getBase()).get(1));
            } else if (this.greaterThan(zero) && that.lessThan(zero)) {
                this.setNumber(InternalBaseNumber.div(this.getNumber(), that.getNumber().substring(1), this.getBase()).get(1));
            }
        } else {
            throw new BaseNumberException(
                    "It's not possible to divide 2 BaseNumbers in different bases: "
                            + (new Integer(this.getBase())).toString() + "!="
                            + (new Integer(that.getBase())).toString() + ".");
        }
    }
    //endregion

    //region convert

    /**
     * Convert of <code>this</code> into another <code>newBase</code>.
     * this = Convert (this) in newBase
     *
     * @throws BaseNumberException
     */
    protected final void convert(int newBase) throws BaseNumberException {
        try {
            if (newBase > InternalBaseNumber.getBaseDigits().length()) {
                throw new BaseNumberException("Base is not correct!"); // ("Baza in care se converteste nu corespunde intervalului de baze!");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (this.lessThan(new InternalBaseNumber(0, this.getBase()))) {
            this.setNumber("-" + InternalBaseNumber.Convert(this.getNumber().substring(1), this.getBase(), newBase));
        } else {
            this.setNumber(InternalBaseNumber.Convert(this.getNumber(), this.getBase(), newBase));
        }
        this.setBase(newBase);
    }
    //endregion

    //region compare

    /**
     * Is this InternalBaseNumber less than {that}.
     *
     * @return <code>this</code> < <code>that</code>.
     * @throws BaseNumberException
     */
    public final boolean lessThan(InternalBaseNumber that)
            throws BaseNumberException {
        if (this.getBase() == that.getBase()) {
            String a = this.getNumber(), b = that.getNumber();
            if (a.charAt(0) == '-' && b.charAt(0) == '-') {
                return new InternalBaseNumber(a.substring(1), this.getBase()).lessThan(new InternalBaseNumber(b.substring(1), that.getBase()));
            }
            if (a.charAt(0) != '-' && b.charAt(0) != '-') {

                if (this.getNumber().length() > that.getNumber().length()) {
                    return false;
                }
                if (this.getNumber().length() < that.getNumber().length()) {
                    return true;
                }
                if (this.getNumber().length() == that.getNumber().length()) {
                    int i = 0;
                    while (i < this.getNumber().length()
                            && this.getNumber().charAt(i) == that.getNumber()
                            .charAt(i)) {
                        i++;
                    }
                    if (i < this.getNumber().length()
                            && InternalBaseNumber.getBaseDigits().indexOf(
                            this.getNumber().charAt(i)) > InternalBaseNumber
                            .getBaseDigits().indexOf(
                                    that.getNumber().charAt(i))) {
                        return false;
                    }
                    if (i < this.getNumber().length()
                            && InternalBaseNumber.getBaseDigits().indexOf(
                            this.getNumber().charAt(i)) < InternalBaseNumber
                            .getBaseDigits().indexOf(
                                    that.getNumber().charAt(i))) {
                        return true;
                    }
                }
            } else if (a.charAt(0) == '-' && b.charAt(0) != '-') {
                return true;
            } else {
                return false;
            }
        } else {
            throw new BaseNumberException(
                    "It's not possible to compare 2 InternalBaseNumber in different bases: "
                            + (new Integer(this.getBase())).toString() + "!="
                            + (new Integer(that.getBase())).toString() + ".");
        }
        return false;
    }

    /**
     * Is this InternalBaseNumber greater than {that}.
     *
     * @return <code>this</code> > <code>that</code>.
     * @throws BaseNumberException
     */
    public final boolean greaterThan(InternalBaseNumber that) throws BaseNumberException {
        if (lessThan(that) || equals(that)) {
            return false;
        }
        return true;
    }

    /**
     * Is this InternalBaseNumber less or equal than {that}.
     *
     * @return <code>this</code> <= <code>that</code>.
     * @throws BaseNumberException
     */
    public final boolean lessThanOrEqual(InternalBaseNumber that) throws BaseNumberException {
        if (lessThan(that) || equals(that)) {
            return true;
        }
        return false;
    }

    /**
     * Is this InternalBaseNumber greater or equal than {that}.
     *
     * @return <code>this</code> >= <code>that</code>.
     * @throws BaseNumberException
     */
    public final boolean greaterThanOrEqual(InternalBaseNumber that) throws BaseNumberException {
        if (greaterThan(that) || equals(that)) {
            return true;
        }
        return false;
    }
    //endregion

    //region equals

    /**
     * Base of baseNumber could be different of the current one's base.
     *
     * @throws BaseNumberException
     */
    private boolean equalsInternal(InternalBaseNumber baseNumber) throws BaseNumberException {
        // Convert into my base
        InternalBaseNumber aux = (InternalBaseNumber) baseNumber.clone();
        aux.convert(this.getBase());

        // Check equals now
        return super.equals(aux);
    }

    @Override
    public boolean equals(Object obj) {
        boolean same = super.equals(obj);
        if (same) {
            return true;
        }
        try {
            return equalsInternal((InternalBaseNumber) obj);
        } catch (BaseNumberException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Indicates whether baseNumber object is "not equal to" this one.
     *
     * @return this != that.
     */
    public boolean notEquals(InternalBaseNumber that) {
        return !equals(that);
    }

    //endregion
}