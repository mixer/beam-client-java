package pro.beam.api.util;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Field;

public class Enums {
    public static String serializedName(Enum e) {
        Field f = getEnumField(e);
        if (f != null && f.isAnnotationPresent(SerializedName.class)) {
            return f.getAnnotation(SerializedName.class).value();
        }

        return null;
    }

    private static Field getEnumField(Enum e) {
        try {
            return e.getClass().getField(e.name());
        } catch (NoSuchFieldException ignored) {
            return null;
        }
    }
}