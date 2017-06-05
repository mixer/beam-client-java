package com.mixer.api.resource;

import java.util.Map;

public class MixerValidationRuleViolation {
    public String path;
    public String type;
    public Map<String, String> context;
    public String message;
}
