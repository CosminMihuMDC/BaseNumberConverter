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
     */
    public BaseNumber(Object value) {
        super(value);
    }

    /**
     * Create ZERO in base 10.
     */
    public BaseNumber() {
    }

    /**
     * Clone a number.
     */
    public BaseNumber(BaseNumber number) {
        super(number);
    }

    /**
     * Clone a number.
     */
    /* package */ BaseNumber(InternalBaseNumber number) {
        super(number);
    }

    /**
     * /////////////////////////////////////// OPERATIONS ////////////////////////////////////////
     */

    /**
     * Create a new number.
     */
    public BaseNumber(Object value, int base) {
        super(value, base);
    }
    //endregion

    /**
     * Create a new BaseNumber as addition between <code>this</code> and <code>that</code>.
     *
     * @return new = this + that
     */
    public BaseNumber add(BaseNumber that) {
        BaseNumber clone = (BaseNumber) clone();
        clone.addition(that);
        return clone;
    }

    /**
     * Create a new BaseNumber as subtraction between <code>this</code> and <code>that</code>.
     *
     * @return new = this - that
     */
    public BaseNumber sub(BaseNumber that) {
        BaseNumber clone = (BaseNumber) clone();
        clone.subtract(that);
        return clone;
    }

    /**
     * Create a new BaseNumber as multiply between <code>this</code> and <code>that</code>.
     *
     * @return new = this * that
     */
    public BaseNumber mul(BaseNumber that) {
        BaseNumber clone = (BaseNumber) clone();
        clone.multiply(that);
        return clone;
    }

    /**
     * Create a new BaseNumber as division between <code>this</code> and <code>that</code>.
     *
     * @return new = this / that
     */
    public BaseNumber div(BaseNumber that) {
        BaseNumber clone = (BaseNumber) clone();
        clone.divide(that);
        return clone;
    }

    /**
     * Create a new BaseNumber as modulo between <code>this</code> and <code>that</code>.
     *
     * @return new = this % that
     */
    public BaseNumber modulo(BaseNumber that) {
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
     */
    public BaseNumber increment() {
        BaseNumber clone = (BaseNumber) clone();
        clone.addition(new InternalBaseNumber(1, this.getBase()));
        return clone;
    }

    /**
     * Create a new BaseNumber as decrement <code>this</code> with 1.
     *
     * @return new = this - 1
     */
    public void decrement() {
        this.subtract(new InternalBaseNumber(1, this.getBase()));
    }

    /**
     * Create a new BaseNumber as unaryNegation ( <code>this</code> ).
     *
     * @return new = unaryNegation ( this )
     */
    public BaseNumber unaryNegation() {
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
     * <code>that</code> should be > 0 (that.getBase).
     *
     * @return new = orExclusive( this , that )
     */
    public BaseNumber orExclusive(BaseNumber that) {
        BaseNumber clone = (BaseNumber) clone();

        if (that.lessThan(new BaseNumber(0, that.getBase()))) {
            throw new RuntimeException("<code>that</code> should be > 0(that.getBase)");
        }

        BaseNumber aux = new BaseNumber(1, clone.getBase());
        BaseNumber count = new BaseNumber(0, that.getBase());
        while (count.notEquals(that)) {
            aux = aux.mul(clone);
            count = count.increment();
        }
        return aux;
    }

    /**
     * Create a new BaseNumber which is the left shifted <code>this</code>.
     * Internal that means <code>this</code> converted in new base <code>newBase</code>.
     *
     * @return new = <code>this</code> left shifted in <code>newBase</code>
     */
    public final BaseNumber shiftLeft(int newBase) {
        BaseNumber clone = (BaseNumber) clone();
        clone.convert(newBase);
        return clone;
    }

    /**
     * Create a new BaseNumber which is the right shifted <code>this</code>.
     * Internal that means <code>this</code> DOUBLE converted in new base <code>newBase</code>.
     *
     * @return new = <code>this</code> right shifted in <code>newBase</code>
     */
    public final BaseNumber shiftRight(int newBase) {
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
     */
    public static BaseNumber convert(BaseNumber that, int newBase) {
        BaseNumber clone = (BaseNumber) that.clone();
        clone.convert(newBase);
        return clone;
    }

    //region Range Functions

    /**
     * Generates a list of BaseNumbers (in base {base}) starting from {start} and ending with {end}.
     */
    public static java.util.List<BaseNumber> range(int start, int end, int base) {
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
     */
    public static java.util.List<BaseNumber> range(int end, int base) {
        return range(0, end, base);
    }

    /**
     * Generates a list of BaseNumbers (in base 10) starting from {start} and ending with {end}.
     */
    public static java.util.List<BaseNumber> rangeBase10(int start, int end) {
        return range(start, end, 10);
    }

    /**
     * Generates a list of BaseNumbers (in base 10) starting from 0 and ending with {end}.
     */
    public static java.util.List<BaseNumber> rangeBase10(int end) {
        return range(0, end, 10);
    }
    //endregion

    @Override
    protected BaseNumber clone() {
        return new BaseNumber(super.clone());
    }
}
