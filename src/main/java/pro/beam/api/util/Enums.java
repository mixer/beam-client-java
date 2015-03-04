package pro.beam.api.util;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Field;

public class Enums {
    public static String serializedName(Enum e) {
        try {
            Field f = e.getClass().getField(e.name());
            if (f != null) {
                return f.getAnnotation(SerializedName.class).value();
            }
        } catch (NoSuchFieldException e1) {
            e1.printStackTrace();
        }

        return null;
    }
}
