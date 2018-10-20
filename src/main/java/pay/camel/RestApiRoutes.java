package pay.camel;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import pay.camel.constants.ROUTES;
import pay.environment.Environment;
import pay.model.Transfer;
import pay.model.TransferAck;

public class RestApiRoutes extends RouteBuilder {

    private Environment env;

    public RestApiRoutes(Environment env) {
        this.env = env;
    }

    @Override
    public void configure() {

        restConfiguration()
                .component("jetty").port(8080)
                .contextPath("/broker").apiContextPath("/api-doc")
                .apiProperty("api.title", "Broker REST API")
                .apiProperty("api.version", "1.0")
                .apiContextRouteId("doc-api")
                .componentProperty("handlers", "#LoggingHandler")

                .bindingMode(RestBindingMode.json);

        rest("/v1/").description("Money transfer REST service")
                .produces("application/json; charset=UTF-8")
                .consumes("application/json")
                .post("/transfer")
                .description("Create money transfer")
                .type(Transfer.class).description("Money Transfer request")
                .outType(TransferAck.class).description("Acknowledgement of money transfer")
                .route().routeId(ROUTES.TRANSFER)
                .log(LoggingLevel.INFO, "Transfer body: ${body}")
                .process(env.transferProc);
    }
}