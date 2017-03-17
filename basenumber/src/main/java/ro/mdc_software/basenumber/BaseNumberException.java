package ro.mdc_software.basenumber;

public class BaseNumberException extends Exception {
	public BaseNumberException() {
		super("Error!");
	}

	public BaseNumberException(String message) {
		super(message);
	}

}