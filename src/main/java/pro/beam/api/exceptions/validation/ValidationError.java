package pro.beam.api.exceptions.validation;

import com.google.common.collect.ImmutableList;
import pro.beam.api.exceptions.BeamException;
import pro.beam.api.resource.BeamValidationRuleViolation;

import java.util.List;

public class ValidationError extends BeamException {
    protected final List<BeamValidationRuleViolation> violations;

    public ValidationError(List<BeamValidationRuleViolation> violations) {
        this.violations = violations;
    }

    public List<BeamValidationRuleViolation> violations() {
        return ImmutableList.copyOf(this.violations);
    }
}
