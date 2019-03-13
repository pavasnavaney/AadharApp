package aadharapp.cloud.csc.aadharapp.Main;

/**
 * Created by PAVAS NAVANEY on 13-06-2017.
 */

public class State {

    private String state;
    private String code;

    public State(String state , String code)
    {
        this.state=state;
        this.code=code;
    }
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
