package pro.beam.api.resource.user.validation;

public class UnknownValidationError extends UserValidationException {
    protected final Throwable cause;

    public UnknownValidationError(Throwable cause) {
        this.cause = cause;
    }

    public Throwable getCause() {
        return this.cause;
    }
}
