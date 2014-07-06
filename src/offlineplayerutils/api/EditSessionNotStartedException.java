package offlineplayerutils.api;

public class EditSessionNotStartedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EditSessionNotStartedException() {
		super("Edit session is not started");
	}

}
