package ro.mdc_software.basenumber;

public class BaseNumberException extends RuntimeException {
	public BaseNumberException() {
		super("Error!");
	}

	public BaseNumberException(String message) {
		super(message);
	}
}