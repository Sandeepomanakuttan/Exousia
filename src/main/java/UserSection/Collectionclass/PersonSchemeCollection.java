package UserSection.Collectionclass;

public class PersonSchemeCollection {

    String Scheme_id,Scheme_Name,Amount,Person_id,Status,id,department,Authority_type,Authority_Place,Income;
    long timeStamp;

    public String getIncome() {
        return Income;
    }

    @Override
    public String toString() {
        return "PersonSchemeCollection{" +
                "Scheme_id='" + Scheme_id + '\'' +
                ", Scheme_Name='" + Scheme_Name + '\'' +
                ", Amount='" + Amount + '\'' +
                ", Person_id='" + Person_id + '\'' +
                ", Status='" + Status + '\'' +
                ", id='" + id + '\'' +
                ", department='" + department + '\'' +
                ", Authority_type='" + Authority_type + '\'' +
                ", Authority_Place='" + Authority_Place + '\'' +
                ", Income='" + Income + '\'' +
                ", timeStamp=" + timeStamp +
                ", Date=" + Date +
                '}';
    }

    public void setIncome(String income) {
        Income = income;
    }

    public String getAuthority_type() {
        return Authority_type;
    }

    public void setAuthority_type(String authority_type) {
        Authority_type = authority_type;
    }

    public String getAuthority_Place() {
        return Authority_Place;
    }

    public void setAuthority_Place(String authority_Place) {
        Authority_Place = authority_Place;
    }

    java.util.Date Date;

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public java.util.Date getDate() {
        return Date;
    }

    public void setDate(java.util.Date date) {
        Date = date;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScheme_id() {
        return Scheme_id;
    }

    public void setScheme_id(String scheme_id) {
        Scheme_id = scheme_id;
    }

    public String getScheme_Name() {
        return Scheme_Name;
    }

    public void setScheme_Name(String scheme_Name) {
        Scheme_Name = scheme_Name;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getPerson_id() {
        return Person_id;
    }

    public void setPerson_id(String person_id) {
        Person_id = person_id;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public PersonSchemeCollection() {
    }

}
