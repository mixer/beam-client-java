package pro.beam.api.resource.interactive;

import pro.beam.api.resource.Timeable;

/**
 * Describes an interactive game.
 */
public class InteractiveGame extends Timeable {
    public int id;
    public int ownerId;
    public String name;
    public String coverUrl;
    public String description;
    public boolean hasPublishedVersions;
    public String installation;
}
