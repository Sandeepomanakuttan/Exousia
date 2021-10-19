package Admin.datacollectionClass;

public class ProfileData {

    String imageUri, Athority_Type, Authority_Place, userName, password, Name, user, id,Department,WardNo,HouseNo;

    public String getDepartment() {
        return Department;
    }

    @Override
    public String toString() {
        return "ProfileData{" +
                "imageUri='" + imageUri + '\'' +
                ", Athority_Type='" + Athority_Type + '\'' +
                ", Authority_Place='" + Authority_Place + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", Name='" + Name + '\'' +
                ", user='" + user + '\'' +
                ", id='" + id + '\'' +
                ", Department='" + Department + '\'' +
                '}';
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public ProfileData() {

    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }


    public String getAthority_Type() {
        return Athority_Type;
    }

    public void setAthority_Type(String athority_Type) {
        Athority_Type = athority_Type;
    }

    public String getAuthority_Place() {
        return Authority_Place;
    }

    public void setAuthority_Place(String authority_Place) {
        Authority_Place = authority_Place;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}

