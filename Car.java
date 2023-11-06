package carsharing;

public class Car {
    private String name;
    private int id;
    private Company company;

    public Car(int id, String name, Company company){
        this.id = id;
        this.name = name;
        this.company = company;
    }

    public int getId() {
        return id;
    }

    public String getName(){
        return name;
    }

    public Company getCompany() {
        return company;
    }
}
