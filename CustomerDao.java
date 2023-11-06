package carsharing;

import java.util.List;

public interface CustomerDao {
    List<Customer> findAll();
    void add(String name);
    Car getRentedCar(int customerId);
    void update(int customer, int car);
}
