public class PingObject {

    private String message;

    public PingObject() {
        super();
        this.message = "Get pinged on!";
    }

    public PingObject(String message) {
        super();
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}