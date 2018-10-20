package pay.environment;

import org.h2.tools.Server;
import pay.camel.processor.TransferProc;
import pay.service.TransferService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Environment {

    public Environment(TransferService transferService, TransferProc transferProc) {
        this.transferService = transferService;
        transferService.setEnvironment(this);
        this.transferProc = transferProc;
        transferProc.setEnv(this);
    }

    public final TransferService transferService;
    public final TransferProc transferProc;
    private Connection connection;

    public Connection getDbConnection() throws SQLException {
        if(connection == null) {
            connection =  DriverManager.getConnection("jdbc:h2:mem:test;INIT=create schema if not exists test\\;runscript from 'classpath:create.sql'", "sa", "");
            connection.commit();
        }
        return connection;
    }

}
