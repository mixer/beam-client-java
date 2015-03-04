package pro.beam.api.resource;

import pro.beam.api.resource.channel.BeamChannel;

import java.util.Date;

public class BeamUser {
    public Date created_at;
    public String display_name;
    public String email;
    public int id;
    public BeamChannel channel;
    public int points;
    public Date reset_time;
    public String social_facebook;
    public String social_twitter;
    public String social_youtube;
    public Date updated_at;
    public String username;
    public boolean verified;
}
