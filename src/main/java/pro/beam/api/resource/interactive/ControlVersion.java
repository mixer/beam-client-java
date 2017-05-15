package pro.beam.api.resource.interactive;

import com.google.gson.annotations.SerializedName;

/**
 * Describes the interactive controls version.
 */
public enum ControlVersion {
    @SerializedName("1.0")
    ONE,
    @SerializedName("2.0")
    TWO,
}
