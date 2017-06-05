package com.mixer.api.exceptions.validation;

import com.google.common.collect.ImmutableList;
import com.mixer.api.exceptions.BeamException;
import com.mixer.api.resource.BeamValidationRuleViolation;

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
