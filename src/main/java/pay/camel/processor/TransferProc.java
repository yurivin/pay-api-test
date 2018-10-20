package pay.camel.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import pay.environment.Environment;
import pay.model.Transfer;
import pay.model.TransferAck;

public class TransferProc implements Processor {

    Environment env;

    @Override
    public void process(Exchange exchange) throws Exception {
        Transfer transfer = exchange.getIn().getBody(Transfer.class);
        TransferAck ack = env.transferService.processTransfer(transfer);
        exchange.getOut().setBody(ack);
    }

    public void setEnv(Environment env) {
        this.env = env;
    }
}
