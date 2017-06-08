package com.mixer.api.response.jojen;

import com.mixer.api.resource.MixerValidationRuleViolation;

import java.util.List;

public final class ValidationErrorResponse {
    public List<MixerValidationRuleViolation> details;
    public boolean isJoi;
    public String name;
}
