package ro.mdc_software.basenumber;

/**
 * todo
 *
 * @author Cosmin Mihu
 */
public class BaseNumber extends StringNumber {


    //endregion

    // C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
    // /#endregion

    // overload operators
    // C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
    // /#region overload operators
    // compare
    public static boolean OpLessThan(BaseNumber nr1, BaseNumber nr2)
            throws BaseNumberException {
        if (nr1.getBase() == nr2.getBase()) {
            String a = nr1.getNumber(), b = nr2.getNumber();
            if (a.charAt(0) == '-' && b.charAt(0) == '-') {
                return (OpLessThan(new BaseNumber(a.substring(1), nr1.getBase()),
                        new BaseNumber(b.substring(1), nr2.getBase())));
            }
            if (a.charAt(0) != '-' && b.charAt(0) != '-') {

                if (nr1.getNumber().length() > nr2.getNumber().length()) {
                    return false;
                }
                if (nr1.getNumber().length() < nr2.getNumber().length()) {
                    return true;
                }
                if (nr1.getNumber().length() == nr2.getNumber().length()) {
                    int i = 0;
                    while (i < nr1.getNumber().length()
                            && nr1.getNumber().charAt(i) == nr2.getNumber()
                            .charAt(i)) {
                        i++;
                    }
                    if (i < nr1.getNumber().length()
                            && BaseNumber.getBaseDigits().indexOf(
                            nr1.getNumber().charAt(i)) > BaseNumber
                            .getBaseDigits().indexOf(
                                    nr2.getNumber().charAt(i))) {
                        return false;
                    }
                    if (i < nr1.getNumber().length()
                            && BaseNumber.getBaseDigits().indexOf(
                            nr1.getNumber().charAt(i)) < BaseNumber
                            .getBaseDigits().indexOf(
                                    nr2.getNumber().charAt(i))) {
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
                    "It's not possible to compare 2 KNumbers in different bases: "
                            + (new Integer(nr1.getBase())).toString() + "!="
                            + (new Integer(nr2.getBase())).toString() + ".");
        }
        return false;

    }

    public static boolean OpGreaterThan(BaseNumber nr1, BaseNumber nr2)
            throws BaseNumberException {
        if (BaseNumber.OpLessThan(nr1, nr2)) {
            return false;
        }
        return true;
    }

    public static boolean OpEquality(BaseNumber nr1, BaseNumber nr2)
            throws BaseNumberException {
        return nr1.equals(nr2);
    }

    public static boolean OpInequality(BaseNumber nr1, BaseNumber nr2)
            throws BaseNumberException {
        if (BaseNumber.OpEquality(nr1, nr2)) {
            return false;
        }
        return true;
    }

    public static boolean OpLessThanOrEqual(BaseNumber nr1, BaseNumber nr2)
            throws BaseNumberException {
        if (BaseNumber.OpLessThan(nr1, nr2) || BaseNumber.OpEquality(nr1, nr2)) {
            return true;
        }
        return false;
    }

    public static boolean OpGreaterThanOrEqual(BaseNumber nr1, BaseNumber nr2)
            throws BaseNumberException {
        if (BaseNumber.OpGreaterThan(nr1, nr2) || BaseNumber.OpEquality(nr1, nr2)) {
            return true;
        }
        return false;
    }

    // add

    /**
     * Aduna la acest BaseNumber un alt BaseNumber.
     *
     * @throws BaseNumberException
     */
    public final void Add(BaseNumber nr) throws BaseNumberException {
        if (this.getBase() == nr.getBase()) {
            BaseNumber zero = new BaseNumber("0", nr.getBase());
            if (BaseNumber.OpGreaterThan(this, zero)
                    && BaseNumber.OpGreaterThan(nr, zero)) {
                this.setNumber(BaseNumber.add(this.getNumber(), nr.getNumber(),
                        this.getBase()));
            } else {
                if (BaseNumber.OpLessThan(this, zero)
                        && BaseNumber.OpLessThan(nr, zero)) {
                    this.setNumber("-"
                            + BaseNumber.add(this.getNumber().substring(1), nr
                            .getNumber().substring(1), this.getBase()));
                } else {
                    if (BaseNumber.OpGreaterThan(this, zero)
                            && BaseNumber.OpLessThan(nr, zero)) {
                        if (OpGreaterThan(this, new BaseNumber(nr.getNumber()
                                .substring(1), nr.getBase()))) {
                            this.setNumber(BaseNumber.sub(this.getNumber(), nr
                                    .getNumber().substring(1), this.getBase()));
                        } else {
                            this.setNumber("-"
                                    + BaseNumber.sub(nr.getNumber().substring(1),
                                    this.getNumber(), this.getBase()));
                        }
                    } else {
                        if (BaseNumber.OpGreaterThan(new BaseNumber(this.getNumber()
                                .substring(1), this.getBase()), nr)) {
                            this.setNumber("-"
                                    + BaseNumber.sub(this.getNumber().substring(1),
                                    nr.getNumber(), this.getBase()));
                        } else {
                            this.setNumber(BaseNumber.sub(nr.getNumber(), this
                                    .getNumber().substring(1), this.getBase()));
                        }
                    }
                }
            }

            this.normalize();
        } else {
            throw new BaseNumberException(
                    "It's not possible to add 2 KNumbers in different bases: "
                            + (new Integer(this.getBase())).toString() + "!="
                            + (new Integer(nr.getBase())).toString() + ".");
        }

    }

    public static BaseNumber OpAddition(BaseNumber nr1, BaseNumber nr2)
            throws BaseNumberException {
        BaseNumber aux = nr1.Clone();
        aux.Add(nr2);
        return aux;
    }

    public static BaseNumber OpIncrement(BaseNumber nr) throws BaseNumberException {
        nr.Add(new BaseNumber(1, nr.getBase()));
        return nr;
    }

    // sub

    /**
     * Scade din acest BaseNumber un alt BaseNumber.
     */
    public final void Substract(BaseNumber nr) throws BaseNumberException {
        if (this.getBase() == nr.getBase()) {
            BaseNumber zero = new BaseNumber("0", nr.getBase());
            if (BaseNumber.OpGreaterThan(this, zero)
                    && BaseNumber.OpLessThan(nr, zero)) {
                this.setNumber(BaseNumber.add(this.getNumber(), nr.getNumber()
                        .substring(1), this.getBase()));
            } else {
                if (BaseNumber.OpLessThan(this, zero)
                        && BaseNumber.OpGreaterThan(nr, zero)) {
                    this.setNumber("-"
                            + BaseNumber.add(this.getNumber().substring(1),
                            nr.getNumber(), this.getBase()));
                } else {
                    if (BaseNumber.OpGreaterThan(this, zero)
                            && BaseNumber.OpGreaterThan(nr, zero)) {
                        if (BaseNumber.OpGreaterThanOrEqual(this, nr)) {
                            this.setNumber(BaseNumber.sub(this.getNumber(),
                                    nr.getNumber(), this.getBase()));
                        } else {
                            this.setNumber("-"
                                    + BaseNumber.sub(nr.getNumber(),
                                    this.getNumber(), this.getBase()));
                        }
                    } else {
                        if (OpLessThan(
                                new BaseNumber(this.getNumber().substring(1),
                                        this.getBase()),
                                new BaseNumber(nr.getNumber().substring(1), nr
                                        .getBase()))) {
                            this.setNumber(BaseNumber.sub(nr.getNumber()
                                            .substring(1),
                                    this.getNumber().substring(1), this
                                            .getBase()));
                        } else {
                            this.setNumber("-"
                                    + BaseNumber.sub(this.getNumber().substring(1),
                                    nr.getNumber().substring(1),
                                    this.getBase()));
                        }
                    }
                }
            }
            this.normalize();
        } else {
            throw new BaseNumberException(
                    "It's not possible to substract 2 KNumbers in different bases: "
                            + (new Integer(this.getBase())).toString() + "!="
                            + (new Integer(nr.getBase())).toString() + ".");
        }
    }

    public static BaseNumber OpSubtraction(BaseNumber nr1, BaseNumber nr2)
            throws BaseNumberException {
        BaseNumber aux = nr1.Clone();
        aux.Substract(nr2);
        return aux;
    }

    public static BaseNumber OpDecrement(BaseNumber nr) throws BaseNumberException {
        nr.Substract(new BaseNumber(1, nr.getBase()));
        return nr;
    }

    public static BaseNumber OpUnaryNegation(BaseNumber nr) throws BaseNumberException {
        BaseNumber zero = new BaseNumber(0);
        if (BaseNumber.OpGreaterThan(nr, zero)) {
            nr.setNumber("-" + nr.getNumber());
        } else if (BaseNumber.OpLessThan(nr, zero)) {
            nr.setNumber(nr.getNumber().substring(1));
        }
        return nr;
    }

    // mul

    /**
     * Inmulteste acest BaseNumber un alt BaseNumber.
     */
    public final void Multiply(BaseNumber nr) throws BaseNumberException {
        if (this.getBase() == nr.getBase()) {
            BaseNumber zero = new BaseNumber("0", nr.getBase());
            if (BaseNumber.OpGreaterThanOrEqual(this, zero)
                    && BaseNumber.OpGreaterThanOrEqual(nr, zero)) {
                this.setNumber(BaseNumber.mul(this.getNumber(), nr.getNumber(),
                        this.getBase()));
            } else if (BaseNumber.OpLessThan(this, zero)
                    && BaseNumber.OpLessThan(nr, zero)) {
                this.setNumber(BaseNumber.mul(this.getNumber().substring(1), nr
                        .getNumber().substring(1), this.getBase()));
            } else if (BaseNumber.OpLessThan(this, zero)
                    && BaseNumber.OpGreaterThan(nr, zero)) {
                this.setNumber("-"
                        + BaseNumber.mul(this.getNumber().substring(1),
                        nr.getNumber(), this.getBase()));
            } else if (BaseNumber.OpGreaterThan(this, zero)
                    && BaseNumber.OpLessThan(nr, zero)) {
                this.setNumber("-"
                        + BaseNumber.mul(this.getNumber(),
                        nr.getNumber().substring(1), this.getBase()));
            }
        } else {
            throw new BaseNumberException(
                    "It's not possible to multiply 2 KNumbers in different basese: "
                            + (new Integer(this.getBase())).toString() + "!="
                            + (new Integer(nr.getBase())).toString() + ".");
        }

    }

    public static BaseNumber OpMultiply(BaseNumber nr1, BaseNumber nr2)
            throws BaseNumberException {
        BaseNumber aux = nr1.Clone();
        aux.Multiply(nr2);
        return aux;
    }

    public static BaseNumber OpExclusiveOr(BaseNumber nr1, BaseNumber nr2)
            throws BaseNumberException { // Pre: daca nr2<0 atunci return 0
        if (BaseNumber.OpLessThan(nr2, new BaseNumber(0, nr2.getBase()))) {
            return new BaseNumber(0, nr2.getBase());
        }

        BaseNumber aux = new BaseNumber(1, nr1.getBase());
        BaseNumber contor = new BaseNumber(0, nr2.getBase());
        while (BaseNumber.OpInequality(contor, nr2)) {
            aux = BaseNumber.OpMultiply(aux, nr1);
            BaseNumber.OpIncrement(contor);
        }
        return aux;
    }

    // div & mod

    /**
     * Imparte acest BaseNumber la un alt BaseNumber.
     */
    public final void Divide(BaseNumber nr) throws BaseNumberException {
        if (nr.getNumber().equals("0")) {
            throw new BaseNumberException("It's not possible to divide by 0!");
        }
        if (this.getBase() == nr.getBase()) {
            BaseNumber zero = new BaseNumber("0", nr.getBase());
            if (BaseNumber.OpGreaterThanOrEqual(this, zero)
                    && BaseNumber.OpGreaterThanOrEqual(nr, zero)) {
                this.setNumber(BaseNumber.div(this.getNumber(), nr.getNumber(),
                        this.getBase()).get(0));
            } else if (BaseNumber.OpLessThan(this, zero)
                    && BaseNumber.OpLessThan(nr, zero)) {
                this.setNumber(BaseNumber.div(this.getNumber().substring(1),
                        nr.getNumber().substring(1), this.getBase()).get(0));
            } else if (BaseNumber.OpLessThan(this, zero)
                    && BaseNumber.OpGreaterThan(nr, zero)) {
                this.setNumber("-"
                        + BaseNumber.div(this.getNumber().substring(1),
                        nr.getNumber(), this.getBase()).get(0));
            } else if (BaseNumber.OpGreaterThan(this, zero)
                    && BaseNumber.OpLessThan(nr, zero)) {
                this.setNumber("-"
                        + BaseNumber.div(this.getNumber(),
                        nr.getNumber().substring(1), this.getBase())
                        .get(0));
            }
        } else {
            throw new BaseNumberException(
                    "It's not possible to divide 2 KNumbers in different bases: "
                            + (new Integer(this.getBase())).toString() + "!="
                            + (new Integer(nr.getBase())).toString() + ".");
        }
    }

    public final void Modulo(BaseNumber nr) throws BaseNumberException {
        if (nr.getNumber().equals("0")) {
            throw new BaseNumberException("It's not possible to divide by 0!");
        }
        if (this.getBase() == nr.getBase()) {
            BaseNumber zero = new BaseNumber("0", nr.getBase());
            if (BaseNumber.OpGreaterThanOrEqual(this, zero)
                    && BaseNumber.OpGreaterThanOrEqual(nr, zero)) {
                this.setNumber(BaseNumber.div(this.getNumber(), nr.getNumber(),
                        this.getBase()).get(1));
            } else if (BaseNumber.OpLessThan(this, zero)
                    && BaseNumber.OpLessThan(nr, zero)) {
                this.setNumber(BaseNumber.div(this.getNumber().substring(1),
                        nr.getNumber().substring(1), this.getBase()).get(1));
            } else if (BaseNumber.OpLessThan(this, zero)
                    && BaseNumber.OpGreaterThan(nr, zero)) {
                this.setNumber(BaseNumber.div(this.getNumber().substring(1),
                        nr.getNumber(), this.getBase()).get(1));
            } else if (BaseNumber.OpGreaterThan(this, zero)
                    && BaseNumber.OpLessThan(nr, zero)) {
                this.setNumber(BaseNumber.div(this.getNumber(),
                        nr.getNumber().substring(1), this.getBase()).get(1));
            }
        } else {
            throw new BaseNumberException(
                    "It's not possible to divide 2 KNumbers in different bases: "
                            + (new Integer(this.getBase())).toString() + "!="
                            + (new Integer(nr.getBase())).toString() + ".");
        }
    }

    public static BaseNumber OpDivision(BaseNumber nr1, BaseNumber nr2)
            throws BaseNumberException {
        BaseNumber aux = nr1.Clone();
        aux.Divide(nr2);
        return aux;
    }

    public static BaseNumber OpModulus(BaseNumber nr1, BaseNumber nr2) throws BaseNumberException {
        try {
            BaseNumber aux = nr1.Clone();
            aux.Modulo(nr2);
            return aux;
        } catch (Exception e) {
            throw new BaseNumberException("Error at division!");
        }
    }

    // conversie

    /**
     * Conversia unui BaseNumber din baza lui in alta baza.
     *
     * @return
     */
    public static void Convert(BaseNumber nr, int h) throws BaseNumberException {
        {
            try {
                if (h > BaseNumber.getBaseDigits().length()) {
                    throw new BaseNumberException("Base is not correct!"); // ("Baza in care se converteste nu corespunde intervalului de baze!");
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            if (BaseNumber.OpLessThan(nr, new BaseNumber(0, nr.getBase()))) {
                nr.setNumber("-"
                        + BaseNumber.Convert(nr.getNumber().substring(1),
                        nr.getBase(), h));
            } else {
                nr.setNumber(BaseNumber.Convert(nr.getNumber(), nr.getBase(), h));
            }
            nr.setBase(h);
        }

    }

    public final void Convert(int h) throws BaseNumberException {
        BaseNumber.Convert(this, h);
    }

    public static BaseNumber OpLeftShift(BaseNumber nr, int baseNumber)
            throws BaseNumberException {
        nr.Convert(baseNumber);
        return nr;
    }

    public static BaseNumber OpRightShift(BaseNumber nr, int baseNumber)
            throws BaseNumberException { // acelasi ca si <<
        return BaseNumber.OpLeftShift(nr, baseNumber);
    }

    /**
     * =======================================================================================================================
     */

    //region Default numbers (zero and one) in base <code>10</code>.
    static {
        try {
            BaseNumber ZERO = new BaseNumber(0);
            BaseNumber ONE = new BaseNumber(1);
        } catch (Exception ignore) {
        }
    }
    //endregion

    //region Constructors
    public BaseNumber(Object value) throws BaseNumberException {
        super(value, 10);
    }

    public BaseNumber() throws BaseNumberException {
        super();
    }

    public BaseNumber(BaseNumber number) {
        super(number);
    }

    public BaseNumber(Object value, int base) throws BaseNumberException {
        super(value, base);
    }
    //endregion

    //region Range Functions

    /**
     * Generates a list of BaseNumbers (in base {base}) starting from {start} and ending with {end}.
     *
     * @throws BaseNumberException
     */
    public static java.util.List<BaseNumber> range(int start, int end, int base) throws BaseNumberException {
        java.util.ArrayList<BaseNumber> list = new java.util.ArrayList<BaseNumber>();
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
    public static java.util.List<BaseNumber> range(int end, int base) throws BaseNumberException {
        return BaseNumber.range(0, end, base);
    }

    /**
     * Generates a list of BaseNumbers (in base 10) starting from {start} and ending with {end}.
     *
     * @throws BaseNumberException
     */
    public static java.util.List<BaseNumber> rangeBase10(int start, int end) throws BaseNumberException {
        return BaseNumber.range(start, end, 10);
    }

    /**
     * Generates a list of BaseNumbers (in base 10) starting from 0 and ending with {end}.
     *
     * @throws BaseNumberException
     */
    public static java.util.List<BaseNumber> rangeBase10(int end) throws BaseNumberException {
        return BaseNumber.range(0, end, 10);
    }
    //endregion

    //region Equals

    /**
     * Base of baseNumber could be different of the current one's base.
     *
     * @throws BaseNumberException
     */
    private boolean equalsInternal(BaseNumber baseNumber) throws BaseNumberException {
        try {
            // Convert into my base
            BaseNumber aux = (BaseNumber) baseNumber.clone();
            aux.Convert(this.getBase());

            // Check equals now
            return super.equals(aux);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        boolean same = super.equals(obj);
        if (same) {
            return true;
        }
        try {
            return equalsInternal((BaseNumber) obj);
        } catch (BaseNumberException e) {
            e.printStackTrace();
        }
        return false;
    }
    //endregion
}