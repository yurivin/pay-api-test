package pay.service;

import pay.environment.Environment;
import pay.model.Transfer;
import pay.model.TransferAck;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransferService {

    private Environment env;
    private PreparedStatement updateAccount;
    private PreparedStatement selectAccount;
    String updateString = "update `test`.`account` set balance = ? where id = ?";
    String selectString = "SELECT * FROM `test`.`account` WHERE id = ?";

    public TransferAck processTransfer(Transfer transfer) {
        TransferAck ack = new TransferAck(false, "Server Error");
        try{
            Connection con = env.getDbConnection();
            con.setAutoCommit(false);

            selectAccount = selectAccount == null ? con.prepareStatement(selectString) : selectAccount;
            selectAccount.setLong(1, transfer.getSender());

            ResultSet rs = selectAccount.executeQuery();
            if(rs.next()) {
                BigDecimal senderBalance = rs.getBigDecimal(2);
                if(senderBalance.compareTo(transfer.getAmount()) >=  0) {
                    updateAccount(con, senderBalance.subtract(transfer.getAmount()), transfer.getSender());
                    updateAccount(con, transfer.getAmount(), transfer.getRecipient());
                    con.commit();
                    ack = new TransferAck(true, null);
                } else {
                    ack = new TransferAck(false, "Not enough balance");
                }
            } else {
                ack = new TransferAck(false, "Sender not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ack;
    }

    private void updateAccount(Connection con, BigDecimal amount, long recipient) throws SQLException {
        updateAccount = updateAccount == null ? con.prepareStatement(updateString) : updateAccount;
        updateAccount.setBigDecimal(1, amount);
        updateAccount.setLong(2, recipient);
        updateAccount.execute();
    }

    public void setEnvironment(Environment env) {
        if(this.env == null) {
            this.env = env;
        }
    }

}
