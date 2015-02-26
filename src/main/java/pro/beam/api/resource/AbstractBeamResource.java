package pro.beam.api.resource;

import com.google.common.base.Objects;

import java.lang.reflect.Field;
import java.util.Arrays;

public abstract class AbstractBeamResource {
    @Override
    public String toString() {
        Objects.ToStringHelper helper = Objects.toStringHelper(this);
        for (Field field : this.getClass().getFields()) {
            helper.add(field.getName(), this.fieldValue(field));
        }

        return helper.toString();
    }

    private String fieldValue(Field field) {
        try {
            Object rawValue = field.get(this);
            if (rawValue instanceof Object[]) {
                return Arrays.toString((Object[]) rawValue);
            } else {
                return String.valueOf(rawValue);
            }
        } catch (IllegalAccessException e) {
            return null;
        }
    }
}
