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
     * Create a new BaseNumber as orExclusive between <code>this</code> and <code>that</code>.
     *
     * @return new = orExclusive( this , that )
     * @throws BaseNumberException
     */
    public BaseNumber orExclusive(BaseNumber that) throws BaseNumberException {
        BaseNumber clone = (BaseNumber) clone();
        clone.orExclusive(that);
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

    /**
     * Create a new BaseNumber which is the left shifted <code>that</code>.
     * Internal that means <code>that</code> converted in new base <code>newBase</code>.
     *
     * @return new = that left shifted in <code>newBase</code>
     * @throws BaseNumberException
     */
    public static final BaseNumber shiftLeft(BaseNumber that, int newBase) throws BaseNumberException {
        BaseNumber clone = (BaseNumber) that.clone();
        clone.shiftLeft(newBase);
        return clone;
    }

    /**
     * Create a new BaseNumber which is the right shifted <code>that</code>.
     * Internal that means <code>that</code> DOUBLE converted in new base <code>newBase</code>.
     *
     * @return new = that right shifted in <code>newBase</code>
     * @throws BaseNumberException
     */
    public static final BaseNumber shiftRight(BaseNumber that, int newBase) throws BaseNumberException {
        BaseNumber clone = (BaseNumber) that.clone();
        clone.shiftRight(newBase);
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
