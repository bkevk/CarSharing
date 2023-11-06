package carsharing;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DbCarDao implements CarDao{
    private DbClient client;

    private static final String INSERT_DATA = "INSERT INTO CAR(NAME, COMPANY_ID) VALUES ('%s', %d)";

    public DbCarDao(DbClient client){
        this.client = client;


        client.run("CREATE TABLE CAR" +
                "(ID INTEGER AUTO_INCREMENT, " +
                " NAME VARCHAR(255) UNIQUE NOT NULL, " +
                " COMPANY_ID INTEGER NOT NULL, " +
                " FOREIGN KEY (COMPANY_ID) REFERENCES COMPANY(ID), " +
                " PRIMARY KEY ( ID ))");

    }

    @Override
    public List<Car> findAll(Company company) {
        ResultSet result = client.find("SELECT * FROM CAR WHERE COMPANY_ID = " + company.getId());
        List<Car> list = new ArrayList<>();
        try {
            while(result.next()) {
                int id  = result.getInt("ID");
                String name = result.getString("NAME");
                list.add(new Car(id, name, company));
            }

        } catch(Exception e){

        }
        return list;
    }

    public boolean isAvailable(int carId) {
        ResultSet result = client.find("SELECT * FROM CUSTOMER WHERE RENTED_CAR_ID = " + carId);
        List<Car> list = new ArrayList<>();
        try {
            while(result.next()) {
                return false;
            }

        } catch(Exception e){

        }
        return true;
    }



    @Override
    public void add(String name, Company company) {
        client.run(String.format(INSERT_DATA, name, company.getId()));
    }

}
