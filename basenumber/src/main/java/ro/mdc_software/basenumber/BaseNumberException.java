package ro.mdc_software.basenumber;

public class BaseNumberException extends Exception {
	public BaseNumberException() {
		super("Eroare!");
	}

	public BaseNumberException(String message) {
		super(message);
	}

}