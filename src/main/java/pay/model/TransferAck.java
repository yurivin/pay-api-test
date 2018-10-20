package pay.model;

public class TransferAck {

    private Boolean success;
    private String error;

    public TransferAck(Boolean success, String error) {
        this.success = success;
        this.error = error;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getError() {
        return error;
    }
}
