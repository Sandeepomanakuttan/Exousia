package Admin.datacollectionClass;

public class SchemedataCollection {
    String strschName;
    String strcategory;
    String strtype;
    String strequitment;
    String stramount;
    String strbelow;
    String strabove;
    String strcriteria;
    String id;
    String StrStatus;
    String authority;
    String authority_Place;

    public SchemedataCollection(){

    }

    public SchemedataCollection(String strschName, String strcategory, String strtype, String strequitment, String stramount, String strbelow, String strabove, String strcriteria,String authority,String authority_Place,String StrStatus) {
        this.strschName = strschName;
        this.strcategory = strcategory;
        this.strtype = strtype;
        this.strequitment = strequitment;
        this.stramount = stramount;
        this.strbelow = strbelow;
        this.strabove = strabove;
        this.strcriteria = strcriteria;
        this.authority=authority;
        this.authority_Place=authority_Place;
        this.StrStatus=StrStatus;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getAuthority_Place() {
        return authority_Place;
    }

    public void setAuthority_Place(String authority_Place) {
        this.authority_Place = authority_Place;
    }

    public String getStrStatus() {
        return StrStatus;
    }

    public void setStrStatus(String strStatus) {
        StrStatus = strStatus;
    }


    public String getStrschName() {
        return strschName;
    }

    public void setStrschName(String strschName) {
        this.strschName = strschName;
    }

    public String getStrcategory() {
        return strcategory;
    }

    public void setStrcategory(String strcategory) {
        this.strcategory = strcategory;
    }

    public String getStrtype() {
        return strtype;
    }

    public void setStrtype(String strtype) {
        this.strtype = strtype;
    }

    public String getStrequitment() {
        return strequitment;
    }

    public void setStrequitment(String strequitment) {
        this.strequitment = strequitment;
    }

    public String getStramount() {
        return stramount;
    }

    public void setStramount(String stramount) {
        this.stramount = stramount;
    }

    public String getStrbelow() {
        return strbelow;
    }

    public void setStrbelow(String strbelow) {
        this.strbelow = strbelow;
    }

    public String getStrabove() {
        return strabove;
    }

    public void setStrabove(String strabove) {
        this.strabove = strabove;
    }

    public String getStrcriteria() {
        return strcriteria;
    }

    public void setStrcriteria(String strcriteria) {
        this.strcriteria = strcriteria;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "SchemedataCollection{" +
                "strschName='" + strschName + '\'' +
                ", strcategory='" + strcategory + '\'' +
                ", strtype='" + strtype + '\'' +
                ", strequitment='" + strequitment + '\'' +
                ", stramount='" + stramount + '\'' +
                ", strbelow='" + strbelow + '\'' +
                ", strabove='" + strabove + '\'' +
                ", strcriteria='" + strcriteria + '\'' +
                ", id='" + id + '\'' +
                ", StrStatus='" + StrStatus + '\'' +
                ", authority='" + authority + '\'' +
                ", authority_Place='" + authority_Place + '\'' +
                '}';
    }

}