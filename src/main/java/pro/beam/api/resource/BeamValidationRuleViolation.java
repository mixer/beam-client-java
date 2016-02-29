package pro.beam.api.resource;

import java.util.Map;

public class BeamValidationRuleViolation {
    public String path;
    public String type;
    public Map<String, String> context;
    public String message;
}
