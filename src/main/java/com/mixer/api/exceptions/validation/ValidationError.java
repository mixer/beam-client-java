package com.mixer.api.exceptions.validation;

import com.google.common.collect.ImmutableList;
import com.mixer.api.exceptions.MixerException;
import com.mixer.api.resource.MixerValidationRuleViolation;

import java.util.List;

public class ValidationError extends MixerException {
    protected final List<MixerValidationRuleViolation> violations;

    public ValidationError(List<MixerValidationRuleViolation> violations) {
        this.violations = violations;
    }

    public List<MixerValidationRuleViolation> violations() {
        return ImmutableList.copyOf(this.violations);
    }
}
