package ro.mdc_software.basenumber;

/**
 * TODO description
 *
 * @author Cosmin Mihu
 * @date 21-March-2017
 */

public class BaseNumber extends InternalBaseNumber {
    //region Constructors

    /**
     * Create a number in base 10.
     *
     * @throws BaseNumberException
     */
    public BaseNumber(Object value) throws BaseNumberException {
        super(value);
    }

    /**
     * Create ZERO in base 10.
     *
     * @throws BaseNumberException
     */
    public BaseNumber() throws BaseNumberException {
    }

    /**
     * Clone a number.
     *
     * @throws BaseNumberException
     */
    public BaseNumber(BaseNumber number) {
        super(number);
    }

    /**
     * /////////////////////////////////////// OPERATIONS ////////////////////////////////////////
     */

    /**
     * Create a new number.
     *
     * @throws BaseNumberException
     */
    public BaseNumber(Object value, int base) throws BaseNumberException {
        super(value, base);
    }
    //endregion

    /**
     * Create a new BaseNumber as addition between <code>this</code> and <code>that</code>.
     *
     * @return new = this + that
     * @throws BaseNumberException
     */
    public BaseNumber add(BaseNumber that) throws BaseNumberException {
        BaseNumber clone = (BaseNumber) clone();
        clone.addition(that);
        return clone;
    }

    /**
     * Create a new BaseNumber as subtraction between <code>this</code> and <code>that</code>.
     *
     * @return new = this - that
     * @throws BaseNumberException
     */
    public BaseNumber sub(BaseNumber that) throws BaseNumberException {
        BaseNumber clone = (BaseNumber) clone();
        clone.subtract(that);
        return clone;
    }

    /**
     * Create a new BaseNumber as multiply between <code>this</code> and <code>that</code>.
     *
     * @return new = this * that
     * @throws BaseNumberException
     */
    public BaseNumber mul(BaseNumber that) throws BaseNumberException {
        BaseNumber clone = (BaseNumber) clone();
        clone.multiply(that);
        return clone;
    }

    /**
     * Create a new BaseNumber as division between <code>this</code> and <code>that</code>.
     *
     * @return new = this / that
     * @throws BaseNumberException
     */
    public BaseNumber div(BaseNumber that) throws BaseNumberException {
        BaseNumber clone = (BaseNumber) clone();
        clone.divide(that);
        return clone;
    }

    /**
     * Create a new BaseNumber as modulo between <code>this</code> and <code>that</code>.
     *
     * @return new = this % that
     * @throws BaseNumberException
     */
    public BaseNumber modulo(BaseNumber that) throws BaseNumberException {
        BaseNumber clone = (BaseNumber) clone();
        clone.modulo((InternalBaseNumber) that);
        return clone;
    }

    /**
     * /////////////////////////////////////// AUX METHODS ////////////////////////////////////////
     */

    /**
     * Create a new BaseNumber as increment <code>this</code> with 1.
     *
     * @return new = this + 1
     * @throws BaseNumberException
     */
    public BaseNumber increment() throws BaseNumberException {
        BaseNumber clone = (BaseNumber) clone();
        clone.addition(new InternalBaseNumber(1, this.getBase()));
        return clone;
    }

    /**
     * Create a new BaseNumber as decrement <code>this</code> with 1.
     *
     * @return new = this - 1
     * @throws BaseNumberException
     */
    public void decrement() throws BaseNumberException {
        this.subtract(new InternalBaseNumber(1, this.getBase()));
    }

    /**
     * Create a new BaseNumber as unaryNegation ( <code>this</code> ).
     *
     * @return new = unaryNegation ( this )
     * @throws BaseNumberException
     */
    public BaseNumber unaryNegation() throws BaseNumberException {
        BaseNumber clone = (BaseNumber) clone();
        if (clone.greaterThan(ZERO)) {
            clone.setNumber("-" + clone.getNumber());
        } else if (clone.lessThan(ZERO)) {
            clone.setNumber(clone.getNumber().substring(1));
        }
        return clone;
    }

    /**
     * Create a new BaseNumber as orExclusive between <code>this</code> and <code>that</code>.
     *
     * @return new = orExclusive( this , that )
     * @throws BaseNumberException
     */
    public BaseNumber orExclusive() throws BaseNumberException {
        BaseNumber clone = (BaseNumber) clone();

        if (clone.lessThan(new InternalBaseNumber(0, clone.getBase()))) {
            throw new RuntimeException("// Pre: daca nr2<0 atunci return 0");
        }

        BaseNumber aux = new BaseNumber(1, clone.getBase());
        BaseNumber contor = new BaseNumber(0, clone.getBase());
        while (contor.notEquals(clone)) {
            aux.multiply(clone);
            contor = contor.increment();
        }
        clone.setNumber(aux.getNumber());
        clone.setBase(aux.getBase());

        return clone;
    }

    /**
     * Create a new BaseNumber which is the left shifted <code>this</code>.
     * Internal that means <code>this</code> converted in new base <code>newBase</code>.
     *
     * @return new = <code>this</code> left shifted in <code>newBase</code>
     * @throws BaseNumberException
     */
    public final BaseNumber shiftLeft(int newBase) throws BaseNumberException {
        BaseNumber clone = (BaseNumber) clone();
        clone.convert(newBase);
        return clone;
    }

    /**
     * Create a new BaseNumber which is the right shifted <code>this</code>.
     * Internal that means <code>this</code> DOUBLE converted in new base <code>newBase</code>.
     *
     * @return new = <code>this</code> right shifted in <code>newBase</code>
     * @throws BaseNumberException
     */
    public final BaseNumber shiftRight(int newBase) throws BaseNumberException {
        BaseNumber clone = (BaseNumber) clone();
        clone.shiftLeft(newBase);
        return clone;
    }

    /**
     * /////////////////////////////////////// STATICS ////////////////////////////////////////
     */

    /**
     * Create a new BaseNumber in a new base.
     *
     * @return new = that converted in <code>newBase</code>
     * @throws BaseNumberException
     */
    public static final BaseNumber convert(BaseNumber that, int newBase) throws BaseNumberException {
        BaseNumber clone = (BaseNumber) that.clone();
        clone.convert(newBase);
        return clone;
    }

    //region Range Functions

    /**
     * Generates a list of BaseNumbers (in base {base}) starting from {start} and ending with {end}.
     *
     * @throws BaseNumberException
     */
    public static final java.util.List<BaseNumber> range(int start, int end, int base) throws BaseNumberException {
        java.util.ArrayList<BaseNumber> list = new java.util.ArrayList<>();
        if (0 <= end) {
            for (int i = start; i <= end; i++) {
                list.add(new BaseNumber(i, base));
            }
        }
        return list;
    }

    /**
     * Generates a list of BaseNumbers (in base {base}) starting from 0 and ending with {end}.
     *
     * @throws BaseNumberException
     */
    public static final java.util.List<BaseNumber> range(int end, int base) throws BaseNumberException {
        return range(0, end, base);
    }

    /**
     * Generates a list of BaseNumbers (in base 10) starting from {start} and ending with {end}.
     *
     * @throws BaseNumberException
     */
    public static final java.util.List<BaseNumber> rangeBase10(int start, int end) throws BaseNumberException {
        return range(start, end, 10);
    }

    /**
     * Generates a list of BaseNumbers (in base 10) starting from 0 and ending with {end}.
     *
     * @throws BaseNumberException
     */
    public static final java.util.List<BaseNumber> rangeBase10(int end) throws BaseNumberException {
        return range(0, end, 10);
    }
    //endregion
}
