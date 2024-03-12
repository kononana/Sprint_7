import java.util.ArrayList;

public class OrderCreate {
    private String firstName;
    private String lastNAme;
    private String address;
    private int metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private ArrayList<String> color;

    public OrderCreate(String firstName, String lastNAme, String address, int metroStation, String phone, int rentTime, String deliveryDate, String comment) {
        this.firstName = firstName;
        this.lastNAme = lastNAme;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
    }

    public OrderCreate() {

    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastNAme() {
        return lastNAme;
    }

    public String getAddress() {
        return address;
    }

    public int getMetroStation() {
        return metroStation;
    }

    public String getPhone() {
        return phone;
    }

    public int getRentTime() {
        return rentTime;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public String getComment() {
        return comment;
    }

    public ArrayList<String> getColor() {
        return color;
    }



    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastNAme(String lastNAme) {
        this.lastNAme = lastNAme;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setMetroStation(int metroStation) {
        this.metroStation = metroStation;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setRentTime(int rentTime) {
        this.rentTime = rentTime;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setColor(ArrayList<String> color) {
        this.color = color;
    }
}
