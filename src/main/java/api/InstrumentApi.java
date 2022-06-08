package api;

import model.Instrument;
import spark.Request;
import spark.Response;

import java.util.List;

public class InstrumentApi {
    public List<Instrument> getAll(Request request, Response response) {
        return List.of(Instrument.values());
    }
}
