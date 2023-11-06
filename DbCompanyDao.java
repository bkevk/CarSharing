package carsharing;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DbCompanyDao implements CompanyDao{
    private DbClient client;
    private static final String INSERT_DATA = "INSERT INTO COMPANY(NAME) VALUES ('%s')";

    public DbCompanyDao(DbClient client){
        this.client = client;

        client.run("CREATE TABLE COMPANY" +
                "(ID INTEGER AUTO_INCREMENT, " +
                " NAME VARCHAR(255) UNIQUE NOT NULL, " +
                " PRIMARY KEY ( ID ))");

    }

    @Override
    public List<Company> findAll() {
        ResultSet result = client.find("SELECT * FROM COMPANY");
        List<Company> list = new ArrayList<>();
        try {
            while(result.next()) {
                int id  = result.getInt("ID");
                String name = result.getString("NAME");
                list.add(new Company(id, name));
            }

        } catch(Exception e){

        }
        return list;
    }

    @Override
    public void add(String company) {
        client.run(String.format(INSERT_DATA, company));
    }

    @Override
    public Company findById(int id) {
        ResultSet result = client.find("SELECT * FROM COMPANY WHERE ID = " + id);
        try {
            while(result.next()) {

                String name = result.getString("NAME");
                return new Company(id, name);
            }

        } catch(Exception e){

        }
        return null;
    }
}
