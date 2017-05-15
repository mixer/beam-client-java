package pro.beam.api.resource.interactive;

import java.io.Serializable;

/**
 * Describes an interactive version.
 */
public class InteractiveVersion implements Serializable {
    public int id;
    public int gameId;
    public String version;
    public int versionOrder;
    public String changelog;
    public VersionState state;
    public String installation;
    public String download;
    public InteractiveControls controls;
    public ControlVersion controlVersion;
}
