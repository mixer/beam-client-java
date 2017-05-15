package pro.beam.api.resource.interactive;

import com.google.gson.annotations.SerializedName;

/**
 * Describes the state of an interactive game.
 */
public enum VersionState {
    @SerializedName("draft")
    DRAFT,
    @SerializedName("pending")
    PENDING,
    @SerializedName("published")
    PUBLISHED,
}
