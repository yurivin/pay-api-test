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
    String updateString = "update `test`.`account` set balance = ? where id = ? AND e_version = ?";
    String selectString = "SELECT * FROM `test`.`account` WHERE id = ?";

    public TransferAck processTransfer(Transfer transfer) {
        TransferAck ack = new TransferAck(false, "Server Error");
        try{
            Connection con = env.getDbConnection();
            con.setAutoCommit(false);

            selectAccount = selectAccount == null ? con.prepareStatement(selectString) : selectAccount;
            selectAccount.setLong(1, transfer.getSender());

            ResultSet rs = selectAccount.executeQuery();
            ack = updateAccounts(transfer, con, rs);
        } catch (SQLException e) {
            e.printStackTrace();
            ack = new TransferAck(false, "Error writing in to Databse. Maybe because of cuncurrent balance changes");
        }
        return ack;
    }

    private TransferAck updateAccounts(Transfer transfer, Connection con, ResultSet rs)
        throws SQLException {
        TransferAck ack;
        if(rs.next()) {
            BigDecimal senderBalance = rs.getBigDecimal(2);
            int versionSender = rs.getInt(3);
            if(senderBalance.compareTo(transfer.getAmount()) >=  0) {
                updateAccount(con, senderBalance.subtract(transfer.getAmount()), transfer.getSender(), versionSender++);
                selectAccount.setLong(1, transfer.getRecipient());
                updateRecipient(transfer, con);
                con.commit();
                ack = new TransferAck(true, null);
            } else {
                ack = new TransferAck(false, "Not enough balance");
            }
        } else {
            ack = new TransferAck(false, "Sender not found");
        }
        return ack;
    }

    private void updateRecipient(Transfer transfer, Connection con) throws SQLException {
        ResultSet rs;
        rs = selectAccount.executeQuery();
        if(rs.next()) {
            BigDecimal receiverBalance = rs.getBigDecimal(2);
            int versionRecipient = rs.getInt(3);
            updateAccount(con, receiverBalance.add(transfer.getAmount()), transfer.getRecipient(), versionRecipient++);
        }
    }

    private void updateAccount(Connection con, BigDecimal amount, long recipient, int version) throws SQLException {
        updateAccount = updateAccount == null ? con.prepareStatement(updateString) : updateAccount;
        updateAccount.setBigDecimal(1, amount);
        updateAccount.setLong(2, recipient);
        updateAccount.setInt(3, version);
        updateAccount.execute();
    }

    public void setEnvironment(Environment env) {
        if(this.env == null) {
            this.env = env;
        }
    }

}
