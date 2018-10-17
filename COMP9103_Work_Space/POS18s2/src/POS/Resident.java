package POS;

import java.util.HashMap;

public class Resident {
    private String name ;
    private String postcode ;
    private String phone ;
    private String pet ;
    private String address ;
    private String birthday ;

    public String getName() {
        return name;
    }
    public String getPostcode() {
        return postcode;
    }
    public String getPhone() {
        return phone;
    }
    public String getPet() {
        return pet;
    }
    public String getAddress() {
        return address;
    }
    public String getBirthday() {
        return birthday;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public void setPet(String pet) {
        this.pet = pet;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    double calcAge(){
        int length = this.birthday.length();
        int bornYear = Integer.parseInt(this.birthday.substring(length-2));
        if (bornYear >= 18)
            return 18-bornYear+100;
        else
            return 18-bornYear;
    }

    //普通常规构造函数
    Resident(){
        name = null ;
        postcode = null ;
        phone = null ;
        pet = null ;
        address = null ;
        birthday = null ;
    }

    //用HashMap对对象进行初始化
    Resident(HashMap<String,String> map){
        this.name = map.get("name");
        this.phone = map.get("phone");
        this.postcode = map.get("postcode");
        this.pet = map.get("pet");
        this.birthday = map.get("birthday");
        this.address = map.get("address");
    }
}
