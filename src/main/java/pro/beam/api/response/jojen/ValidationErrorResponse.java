package pro.beam.api.response.jojen;

import pro.beam.api.resource.BeamValidationRuleViolation;

import java.util.ArrayList;
import java.util.List;

public final class ValidationErrorResponse {
    public List<BeamValidationRuleViolation> details;
    public boolean isJoi;
    public String name;
}
