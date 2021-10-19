package Admin.datacollectionClass;


import android.net.Uri;

public class UserDataCollection {
    String Id,HouseOwnerName,PersonName,District,Panchayath,WardNo,houseNo,Dob,uid,status,FatherName,MotherName,Address,RitNo,AnualIncime,mobile,Password,UserType;
    Uri Image;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    @Override
    public String toString() {
        return "UserDataCollection{" +
                "Id='" + Id + '\'' +
                ", HouseOwnerName='" + HouseOwnerName + '\'' +
                ", PersonName='" + PersonName + '\'' +
                ", District='" + District + '\'' +
                ", Panchayath='" + Panchayath + '\'' +
                ", WardNo='" + WardNo + '\'' +
                ", houseNo='" + houseNo + '\'' +
                ", Dob='" + Dob + '\'' +
                ", uid='" + uid + '\'' +
                ", status='" + status + '\'' +
                ", FatherName='" + FatherName + '\'' +
                ", MotherName='" + MotherName + '\'' +
                ", Address='" + Address + '\'' +
                ", RitNo='" + RitNo + '\'' +
                ", AnualIncime='" + AnualIncime + '\'' +
                ", mobile='" + mobile + '\'' +
                ", Image=" + Image +
                '}';
    }

    public UserDataCollection() {
    }

    public UserDataCollection(String houseOwnerName, String personName, String district, String panchayath, String wardNo, String houseNo, String dob, String uid, String status, String fatherName, String motherName, String address, String ritNo, String anualIncime, String mobile) {
        HouseOwnerName = houseOwnerName;
        PersonName = personName;
        District = district;
        Panchayath = panchayath;
        WardNo = wardNo;
        this.houseNo = houseNo;
        Dob = dob;
        this.uid = uid;
        this.status = status;
        FatherName = fatherName;
        MotherName = motherName;
        Address = address;
        RitNo = ritNo;
        AnualIncime = anualIncime;
        this.mobile = mobile;
    }

    public String getHouseOwnerName() {
        return HouseOwnerName;
    }

    public void setHouseOwnerName(String houseOwnerName) {
        HouseOwnerName = houseOwnerName;
    }

    public String getPersonName() {
        return PersonName;
    }

    public void setPersonName(String personName) {
        PersonName = personName;
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
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    public String getDob() {
        return Dob;
    }

    public void setDob(String dob) {
        Dob = dob;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFatherName() {
        return FatherName;
    }

    public void setFatherName(String fatherName) {
        FatherName = fatherName;
    }

    public String getMotherName() {
        return MotherName;
    }

    public void setMotherName(String motherName) {
        MotherName = motherName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getRitNo() {
        return RitNo;
    }

    public void setRitNo(String ritNo) {
        RitNo = ritNo;
    }

    public String getAnualIncime() {
        return AnualIncime;
    }

    public void setAnualIncime(String anualIncime) {
        AnualIncime = anualIncime;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Uri getImage() {
        return Image;
    }

    public void setImage(Uri image) {
        Image = image;
    }

}
