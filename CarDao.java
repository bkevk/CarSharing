package carsharing;

import java.util.List;

public interface CarDao {
    List<Car> findAll(Company company);
    void add(String name, Company company);
}
