package Admin;

public class PersonDataCollection {

    String imageUri,House_OwnerName,District,Panchayath,WardNo,HouseNo,id,userType;


    public PersonDataCollection(){

    }

    public PersonDataCollection(String stringName, String stringDistrict, String stringPanchayath, String stringWardNo, String stringHouseNo, String stringuser) {

        House_OwnerName = stringName;
        District = stringDistrict;
        Panchayath = stringPanchayath;
        WardNo = stringWardNo;
        HouseNo = stringHouseNo;
        userType = stringuser;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "PersonDataCollection{" +
                "imageUri='" + imageUri + '\'' +
                ", House_OwnerName='" + House_OwnerName + '\'' +
                ", District='" + District + '\'' +
                ", Panchayath='" + Panchayath + '\'' +
                ", WardNo='" + WardNo + '\'' +
                ", HouseNo='" + HouseNo + '\'' +
                ", id='" + id + '\'' +
                ", userType='" + userType + '\'' +
                '}';
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHouse_OwnerName() {
        return House_OwnerName;
    }

    public void setHouse_OwnerName(String house_OwnerName) {
        House_OwnerName = house_OwnerName;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public String getPanchayath() {
        return Panchayath;
    }

    public void setPanchayath(String panchayath) {
        Panchayath = panchayath;
    }

    public String getWardNo() {
        return WardNo;
    }

    public void setWardNo(String wardNo) {
        WardNo = wardNo;
    }

    public String getHouseNo() {
        return HouseNo;
    }

    public void setHouseNo(String houseNo) {
        HouseNo = houseNo;
    }

}
