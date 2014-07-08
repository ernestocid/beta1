package exceptions;

public class AnimationErrorException extends Exception {

	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "An error occurred during the animation step.";
	}

}
