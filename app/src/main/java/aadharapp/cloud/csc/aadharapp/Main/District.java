package aadharapp.cloud.csc.aadharapp.Main;

/**
 * Created by PAVAS NAVANEY on 13-06-2017.
 */

public class District {

    private String district;
    private String code;

    public District(String district , String code)
    {
        this.district=district;
        this.code=code;
    }
    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
