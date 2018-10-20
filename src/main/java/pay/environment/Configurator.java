package pay.environment;

import pay.camel.processor.TransferProc;
import pay.service.TransferService;

import java.sql.SQLException;

public class Configurator {

    public static Environment configure() throws SQLException {
        Environment env = new Environment(new TransferService(), new TransferProc());
        return env;
    }
}
