package com.mixer.api.response.jojen;

import com.mixer.api.resource.BeamValidationRuleViolation;

import java.util.List;

public final class ValidationErrorResponse {
    public List<BeamValidationRuleViolation> details;
    public boolean isJoi;
    public String name;
}
