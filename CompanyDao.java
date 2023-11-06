package carsharing;

import java.util.List;

public interface CompanyDao {
    List<Company> findAll();
    void add(String company);
    Company findById(int id);
}
