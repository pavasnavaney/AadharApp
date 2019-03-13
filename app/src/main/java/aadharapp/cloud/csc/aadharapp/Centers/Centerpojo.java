package aadharapp.cloud.csc.aadharapp.Centers;

/**
 * Created by PAVAS NAVANEY on 30-06-2017.
 */

public class Centerpojo {

    public String id;
    public String center_name;
    public String user_name;
    public String address;
    public String service_provided;
    public String email;
    public String date;
    public String todate;
    public String state_code;
    public String district_code;
    public String service_code;

    public String getState_code() {
        return state_code;
    }

    public String getTodate() {
        return todate;
    }

    public void setTodate(String todate) {
        this.todate = todate;
    }

    public void setState_code(String state_code) {
        this.state_code = state_code;
    }

    public String getDistrict_code() {
        return district_code;
    }

    public void setDistrict_code(String district_code) {
        this.district_code = district_code;
    }

    public String getService_code() {
        return service_code;
    }

    public void setService_code(String service_code) {
        this.service_code = service_code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCenter_name() {
        return center_name;
    }

    public void setCenter_name(String center_name) {
        this.center_name = center_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getService_provided() {
        return service_provided;
    }

    public void setService_provided(String service_provided) {
        this.service_provided = service_provided;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
