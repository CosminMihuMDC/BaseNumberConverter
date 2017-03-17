package ro.mdc_software.basenumber;

public class BaseNumber {
	private String value;
	private int baseNumber;
	private static String digits = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	// Properties
	public final String getValue() {
		return this.value;
	}

	public final void setValue(String value) {
		this.value = value;
	}

	public final int getBase() {
		return this.baseNumber;
	}

	public final void setBase(int value) {
		this.baseNumber = value;
	}

	public static String getDigits() {
		return BaseNumber.digits;
	}

	public static void setDigits(String value) {
		BaseNumber.digits = value;
	}

	static {
		try {
			BaseNumber zero = new BaseNumber(0);
			BaseNumber one = new BaseNumber(1);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// Contructors
	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#region Contructors
	public BaseNumber(Object value, int baseNumber) throws BaseNumberException {
		BaseNumber.valide(value, baseNumber);

		this.setValue(value.toString().toUpperCase());
		this.setBase(baseNumber);

		this.normalize();
	}

	public BaseNumber(Object value) throws BaseNumberException {
		this(value, 10);
	}

	public BaseNumber() throws BaseNumberException {
		this("0", 10);
	}

	public BaseNumber(BaseNumber nr) {
		this.setValue(nr.getValue());
		this.setBase(nr.getBase());
	}

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#endregion

	// overload operators
	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#region overload operators
	// compare
	public static boolean OpLessThan(BaseNumber nr1, BaseNumber nr2)
			throws BaseNumberException {
		if (nr1.getBase() == nr2.getBase()) {
			String a = nr1.getValue(), b = nr2.getValue();
			if (a.charAt(0) == '-' && b.charAt(0) == '-') {
				return (OpLessThan(new BaseNumber(a.substring(1), nr1.getBase()),
						new BaseNumber(b.substring(1), nr2.getBase())));
			}
			if (a.charAt(0) != '-' && b.charAt(0) != '-') {

				if (nr1.getValue().length() > nr2.getValue().length()) {
					return false;
				}
				if (nr1.getValue().length() < nr2.getValue().length()) {
					return true;
				}
				if (nr1.getValue().length() == nr2.getValue().length()) {
					int i = 0;
					while (i < nr1.getValue().length()
							&& nr1.getValue().charAt(i) == nr2.getValue()
									.charAt(i)) {
						i++;
					}
					if (i < nr1.getValue().length()
							&& BaseNumber.getDigits().indexOf(
									nr1.getValue().charAt(i)) > BaseNumber
									.getDigits().indexOf(
											nr2.getValue().charAt(i))) {
						return false;
					}
					if (i < nr1.getValue().length()
							&& BaseNumber.getDigits().indexOf(
									nr1.getValue().charAt(i)) < BaseNumber
									.getDigits().indexOf(
											nr2.getValue().charAt(i))) {
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
				this.setValue(BaseNumber.add(this.getValue(), nr.getValue(),
						this.getBase()));
			} else {
				if (BaseNumber.OpLessThan(this, zero)
						&& BaseNumber.OpLessThan(nr, zero)) {
					this.setValue("-"
							+ BaseNumber.add(this.getValue().substring(1), nr
									.getValue().substring(1), this.getBase()));
				} else {
					if (BaseNumber.OpGreaterThan(this, zero)
							&& BaseNumber.OpLessThan(nr, zero)) {
						if (OpGreaterThan(this, new BaseNumber(nr.getValue()
								.substring(1), nr.getBase()))) {
							this.setValue(BaseNumber.sub(this.getValue(), nr
									.getValue().substring(1), this.getBase()));
						} else {
							this.setValue("-"
									+ BaseNumber.sub(nr.getValue().substring(1),
											this.getValue(), this.getBase()));
						}
					} else {
						if (BaseNumber.OpGreaterThan(new BaseNumber(this.getValue()
								.substring(1), this.getBase()), nr)) {
							this.setValue("-"
									+ BaseNumber.sub(this.getValue().substring(1),
											nr.getValue(), this.getBase()));
						} else {
							this.setValue(BaseNumber.sub(nr.getValue(), this
									.getValue().substring(1), this.getBase()));
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
				this.setValue(BaseNumber.add(this.getValue(), nr.getValue()
						.substring(1), this.getBase()));
			} else {
				if (BaseNumber.OpLessThan(this, zero)
						&& BaseNumber.OpGreaterThan(nr, zero)) {
					this.setValue("-"
							+ BaseNumber.add(this.getValue().substring(1),
									nr.getValue(), this.getBase()));
				} else {
					if (BaseNumber.OpGreaterThan(this, zero)
							&& BaseNumber.OpGreaterThan(nr, zero)) {
						if (BaseNumber.OpGreaterThanOrEqual(this, nr)) {
							this.setValue(BaseNumber.sub(this.getValue(),
									nr.getValue(), this.getBase()));
						} else {
							this.setValue("-"
									+ BaseNumber.sub(nr.getValue(),
											this.getValue(), this.getBase()));
						}
					} else {
						if (OpLessThan(
								new BaseNumber(this.getValue().substring(1),
										this.getBase()),
								new BaseNumber(nr.getValue().substring(1), nr
										.getBase()))) {
							this.setValue(BaseNumber.sub(nr.getValue()
									.substring(1),
									this.getValue().substring(1), this
											.getBase()));
						} else {
							this.setValue("-"
									+ BaseNumber.sub(this.getValue().substring(1),
											nr.getValue().substring(1),
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
			nr.setValue("-" + nr.getValue());
		} else if (BaseNumber.OpLessThan(nr, zero)) {
			nr.setValue(nr.getValue().substring(1));
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
				this.setValue(BaseNumber.mul(this.getValue(), nr.getValue(),
						this.getBase()));
			} else if (BaseNumber.OpLessThan(this, zero)
					&& BaseNumber.OpLessThan(nr, zero)) {
				this.setValue(BaseNumber.mul(this.getValue().substring(1), nr
						.getValue().substring(1), this.getBase()));
			} else if (BaseNumber.OpLessThan(this, zero)
					&& BaseNumber.OpGreaterThan(nr, zero)) {
				this.setValue("-"
						+ BaseNumber.mul(this.getValue().substring(1),
								nr.getValue(), this.getBase()));
			} else if (BaseNumber.OpGreaterThan(this, zero)
					&& BaseNumber.OpLessThan(nr, zero)) {
				this.setValue("-"
						+ BaseNumber.mul(this.getValue(),
								nr.getValue().substring(1), this.getBase()));
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
		if (nr.getValue().equals("0")) {
			throw new BaseNumberException("It's not possible to divide by 0!");
		}
		if (this.getBase() == nr.getBase()) {
			BaseNumber zero = new BaseNumber("0", nr.getBase());
			if (BaseNumber.OpGreaterThanOrEqual(this, zero)
					&& BaseNumber.OpGreaterThanOrEqual(nr, zero)) {
				this.setValue(BaseNumber.div(this.getValue(), nr.getValue(),
						this.getBase()).get(0));
			} else if (BaseNumber.OpLessThan(this, zero)
					&& BaseNumber.OpLessThan(nr, zero)) {
				this.setValue(BaseNumber.div(this.getValue().substring(1),
						nr.getValue().substring(1), this.getBase()).get(0));
			} else if (BaseNumber.OpLessThan(this, zero)
					&& BaseNumber.OpGreaterThan(nr, zero)) {
				this.setValue("-"
						+ BaseNumber.div(this.getValue().substring(1),
								nr.getValue(), this.getBase()).get(0));
			} else if (BaseNumber.OpGreaterThan(this, zero)
					&& BaseNumber.OpLessThan(nr, zero)) {
				this.setValue("-"
						+ BaseNumber.div(this.getValue(),
								nr.getValue().substring(1), this.getBase())
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
		if (nr.getValue().equals("0")) {
			throw new BaseNumberException("It's not possible to divide by 0!");
		}
		if (this.getBase() == nr.getBase()) {
			BaseNumber zero = new BaseNumber("0", nr.getBase());
			if (BaseNumber.OpGreaterThanOrEqual(this, zero)
					&& BaseNumber.OpGreaterThanOrEqual(nr, zero)) {
				this.setValue(BaseNumber.div(this.getValue(), nr.getValue(),
						this.getBase()).get(1));
			} else if (BaseNumber.OpLessThan(this, zero)
					&& BaseNumber.OpLessThan(nr, zero)) {
				this.setValue(BaseNumber.div(this.getValue().substring(1),
						nr.getValue().substring(1), this.getBase()).get(1));
			} else if (BaseNumber.OpLessThan(this, zero)
					&& BaseNumber.OpGreaterThan(nr, zero)) {
				this.setValue(BaseNumber.div(this.getValue().substring(1),
						nr.getValue(), this.getBase()).get(1));
			} else if (BaseNumber.OpGreaterThan(this, zero)
					&& BaseNumber.OpLessThan(nr, zero)) {
				this.setValue(BaseNumber.div(this.getValue(),
						nr.getValue().substring(1), this.getBase()).get(1));
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
				if (h > BaseNumber.getDigits().length()) {
					throw new BaseNumberException("Base is not correct!"); // ("Baza in care se converteste nu corespunde intervalului de baze!");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (BaseNumber.OpLessThan(nr, new BaseNumber(0, nr.getBase()))) {
				nr.setValue("-"
						+ BaseNumber.Convert(nr.getValue().substring(1),
								nr.getBase(), h));
			} else {
				nr.setValue(BaseNumber.Convert(nr.getValue(), nr.getBase(), h));
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

	// egalitate
	/**
	 * Verifica egaliatate a 2 KNumbers.
	 */
	public final boolean RawEquals(BaseNumber nr) {
		if (this.getBase() == nr.getBase()) {
			if (this.getValue().equals(nr.getValue())) {
				return true;
			}
		}
		return false;
	}

	public final boolean equals(BaseNumber nr) throws BaseNumberException {
		if (this.getBase() == nr.getBase() && this.RawEquals(nr) == true) {
			return true;
		} else {
			BaseNumber aux = new BaseNumber(nr);
			aux.Convert(this.getBase());
			if (this.RawEquals(aux) == true) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean equals(Object obj) {
		try {
			BaseNumber aux = new BaseNumber(obj);
			return BaseNumber.OpEquality(this, aux);
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * alte functii
	 */
	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public String toString() {
		return this.getValue() + "(" + (new Integer(this.getBase())).toString()
				+ ")";
	}

	private void normalize() {
		int ok;
		if (this.getValue().charAt(0) == '-' && this.getValue().length() > 1) {
			ok = 1;
			this.setValue(this.getValue().substring(1));
		} else {
			ok = 0;
		}

		int i = ok;
		while (this.getValue().length() > 1 && this.getValue().charAt(0) == '0') {
			i++;
			this.setValue(this.getValue().substring(1));
		}
		if (ok == 1 && !this.getValue().equals("0")) {
			this.setValue("-" + this.getValue());
		}

	}

	private static void valide(Object value, int baseNumber) throws BaseNumberException {
		if (!(value instanceof String || value instanceof Integer)) {
			throw new BaseNumberException("BaseNumber must be integer or string.");
		}
		if (value.toString().length() <= 0) {
			throw new BaseNumberException("BaseNumber must be integer or string, not null.");
		}
		if (baseNumber > BaseNumber.getDigits().length()) {
			throw new BaseNumberException("BaseNumber's base can not be greater than "
					+ BaseNumber.getDigits().length() + ".");
		}

		String aux = value.toString().toUpperCase();
		int i = (aux.charAt(0) != '-') ? 0 : 1;
		for (; i < aux.length(); i++) {
			if (BaseNumber.getDigits().indexOf(aux.charAt(i)) == -1
					|| BaseNumber.getDigits().indexOf(aux.charAt(i)) >= baseNumber) {
				throw new BaseNumberException("The digit '" + aux.charAt(i)
						+ "' does not exist in base "
						+ (new Integer(baseNumber)).toString() + ".");
			}
		}
	}

	/**
	 * Cloneaza acest BaseNumber, creand o copie a sa.
	 * 
	 * @throws BaseNumberException
	 */
	public final BaseNumber Clone() throws BaseNumberException {
		return new BaseNumber(this.getValue(), this.getBase());
	}

	// ------------------------functii
	// ajutatoare---------------------------------
	/**
	 * Genereaza o lista de KNumbers in baza [Base] de la [start] la [end].
	 * 
	 * @throws BaseNumberException
	 */
	public static java.util.ArrayList<BaseNumber> range(int start, int end,
														int Base) throws BaseNumberException {
		java.util.ArrayList<BaseNumber> lista = new java.util.ArrayList<BaseNumber>();
		if (0 <= end) {
			for (int i = start; i <= end; i++) {
				lista.add(new BaseNumber(i, Base));
			}
		}
		return lista;
	}

	public static java.util.ArrayList<BaseNumber> range(int end, int Base)
			throws BaseNumberException {
		return BaseNumber.range(0, end, Base);
	}

	/**
	 * Genereaza o lista de KNumbers in baza 10 de la [start] la [end].
	 * 
	 * @throws BaseNumberException
	 */
	public static java.util.ArrayList<BaseNumber> rangeBase10(int start, int end)
			throws BaseNumberException {
		return BaseNumber.range(start, end, 10);
	}

	public static java.util.ArrayList<BaseNumber> rangeBase10(int end)
			throws BaseNumberException {
		return BaseNumber.range(0, end, 10);
	}

	// ------------------------raw methods---------------------------------
	private static String add(String nr1, String nr2, int baseNumber) { // Pre:
																		// nr1,nr2
																		// > 0
																		// numere
																		// in
																		// baza
																		// baseNumber
		String result = "";

		while (nr1.length() < nr2.length()) {
			nr1 = "0" + nr1;
		}
		while (nr2.length() < nr1.length()) {
			nr2 = "0" + nr2;
		}

		int t = 0, suma;
		for (int c = nr1.length() - 1; c >= 0; c--) {
			suma = BaseNumber.getDigits().indexOf(nr1.charAt(c))
					+ BaseNumber.getDigits().indexOf(nr2.charAt(c)) + t;
			result = BaseNumber.getDigits().charAt(suma % baseNumber) + result;
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

	private static String sub(String nr1, String nr2, int baseNumber) { // Pre:nr1>=nr2,
																		// //Pre:
																		// nr1,nr2
																		// > 0
																		// numere
																		// in
																		// baza
																		// baseNumber
		while (nr1.length() != nr2.length()) {
			nr2 = "0" + nr2;
		}

		int t = 0, s, d;
		String result = "";
		for (int i = nr1.length() - 1; i >= 0; i--) {
			s = BaseNumber.getDigits().indexOf(nr1.charAt(i)) - t;
			d = BaseNumber.getDigits().indexOf(nr2.charAt(i));
			t = 0;

			if (s < d) {
				s = s + baseNumber;
				t = 1;
			}
			result = BaseNumber.getDigits().charAt(s - d) + result;
		}
		while (result.length() > 1 && result.charAt(0) == '0') {
			result = result.substring(1);
		}
		return result;
	}

	private static String mulOne(String nr1, char nr2, int baseNumber) { // Pre:
																			// nr1,nr2
																			// >
																			// 0
																			// numere
																			// in
																			// baza
																			// baseNumber
		if (nr2 == '0') {
			return "0";
		}
		if (nr2 == '1') {
			return nr1;
		}

		String result = "";
		int c = BaseNumber.getDigits().indexOf(nr2);
		int t = 0, produs;

		for (int i = nr1.length() - 1; i >= 0; i--) {
			produs = BaseNumber.getDigits().indexOf(nr1.charAt(i)) * c + t;
			result = BaseNumber.getDigits().charAt(produs % baseNumber) + result;
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

	private static String mul(String nr1, String nr2, int baseNumber) {
		String result = "0";
		for (int i = 0; i < nr2.length(); i++) {
			result = BaseNumber.add(
					BaseNumber.mulOne(nr1, nr2.charAt(i), baseNumber), result
							+ "0", baseNumber);
		}
		while (result.length() > 1 && result.charAt(0) == '0') {
			result = result.substring(1);
		}
		return result;
	}

	public static java.util.ArrayList<String> divOne(String nr1, char nr2,
			int baseNumber) {
		String result = "";
		int imp = BaseNumber.getDigits().indexOf(nr2);
		int rest = BaseNumber.getDigits().indexOf(nr1.charAt(0));
		for (int i = 0; i < nr1.length() - 1; i++) {
			result += BaseNumber.getDigits().charAt(rest / imp);
			rest = baseNumber * (rest % imp)
					+ BaseNumber.getDigits().indexOf(nr1.charAt(i + 1));
		}
		result += BaseNumber.getDigits().charAt(rest / imp);
		String Rest = String.valueOf(BaseNumber.getDigits().charAt(rest % imp));

		while (result.length() > 1 && result.charAt(0) == '0') {
			result = result.substring(1);
		}

		java.util.ArrayList<String> x = new java.util.ArrayList<String>();
		x.add(result);
		x.add(Rest);

		return x;
	}

	public static java.util.ArrayList<String> div(String nr1, String nr2,
			int baseNumber) {
		String result = "";

		String aux = BaseNumber.Convert(nr2, baseNumber, 10);
		int imp = Integer.parseInt(aux);
		int rest = BaseNumber.getDigits().indexOf(nr1.charAt(0));
		for (int i = 0; i < nr1.length() - 1; i++) {
			result += BaseNumber.getDigits().charAt(rest / imp);
			rest = baseNumber * (rest % imp)
					+ BaseNumber.getDigits().indexOf(nr1.charAt(i + 1));
		}
		result += BaseNumber.getDigits().charAt(rest / imp);
		rest = rest % imp;

		while (result.length() > 1 && result.charAt(0) == '0') {
			result = result.substring(1);
		}
		String Rest = BaseNumber.Convert((new Integer(rest)).toString(), 10,
				baseNumber);

		java.util.ArrayList<String> x = new java.util.ArrayList<String>();
		x.add(result);
		x.add(Rest);

		return x;
	}

	private static String convertMul(String nr, int b, int h) { // Pre: b<h
		String result = "0";
		String pow = "1", termen;
		for (int i = nr.length() - 1; i >= 0; i--) {
			termen = BaseNumber.mul(pow, String.valueOf(nr.charAt(i)), h);
			result = add(result, termen, h);

			pow = BaseNumber.mul(pow,
					String.valueOf(BaseNumber.getDigits().charAt(b)), h);
		}
		return result;
	}

	private static String convertDiv(String nr, int b, int h) { // Pre: b>h
		String result = "", cat = "";
		java.util.ArrayList<String> aux;

		while (!nr.equals("0")) {
			cat = "";
			String cifra = String.valueOf(nr.charAt(0));
			for (int c = 1; c < nr.length(); c++) {
				aux = BaseNumber.divOne(cifra, BaseNumber.getDigits().charAt(h), b);
				cat += aux.get(0);

				cifra = BaseNumber.add(
						BaseNumber.mul("10", aux.get(1).toString(), b),
						String.valueOf(nr.charAt(c)), b);
			}
			aux = BaseNumber.divOne(cifra, BaseNumber.getDigits().charAt(h), b);

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

	private static String Convert(String nr, int b, int h) {
		if (b == h) {
			return nr;
		} else if (b < h) {
			return BaseNumber.convertMul(nr, b, h);
		} else {
			return BaseNumber.convertDiv(nr, b, h);
		}
	}
}