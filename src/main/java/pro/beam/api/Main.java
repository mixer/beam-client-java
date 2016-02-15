package pro.beam.api;

import pro.beam.api.services.impl.UsersService;

import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        BeamAPI beam = new BeamAPI();
        beam.use(UsersService.class).findOne(139).get();
    }
}
