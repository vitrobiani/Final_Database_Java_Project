public class Contact extends Person{
    public int EmployeeNumber;
    public Contact(String name, String phoneNumber, int EmployeeNumber){
        super(name, phoneNumber);
        this.EmployeeNumber = EmployeeNumber;
    }
}
