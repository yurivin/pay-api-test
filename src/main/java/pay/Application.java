package pay;

import org.apache.camel.main.Main;
import pay.camel.RestApiRoutes;
import pay.environment.Configurator;
import pay.environment.Environment;

public class Application {


    public static void main(String [] args) throws Exception {
        Environment env = Configurator.configure();
        startCamel(args, env);
    }

    private static void startCamel(String[] args, Environment env) throws Exception {
        Main main = new Main();
        main.addRouteBuilder(new RestApiRoutes(env));
        main.run(args);
    }

}
