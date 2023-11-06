package carsharing;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DbCustomerDao implements CustomerDao{
    private DbClient client;
    private static final String INSERT_DATA = "INSERT INTO CUSTOMER(NAME) VALUES ('%s')";
    private static final String RENT_CAR = "UPDATE CUSTOMER SET RENTED_CAR_ID = %d WHERE ID = %d;";
    private static final String RETURN_CAR = "UPDATE CUSTOMER SET RENTED_CAR_ID = NULL WHERE ID = %d;";

    public DbCustomerDao(DbClient client){
        this.client = client;

        client.run("CREATE TABLE CUSTOMER" +
                "(ID INTEGER AUTO_INCREMENT, " +
                " NAME VARCHAR(255) UNIQUE NOT NULL, " +
                " RENTED_CAR_ID INTEGER, " +
                " FOREIGN KEY (RENTED_CAR_ID) REFERENCES CAR(ID), " +
                " PRIMARY KEY ( ID ))");
    }

    @Override
    public List<Customer> findAll() {
        ResultSet result = client.find("SELECT * FROM CUSTOMER");
        List<Customer> list = new ArrayList<>();
        try {
            while(result.next()) {
                int id  = result.getInt("ID");
                String name = result.getString("NAME");
                int rentedCarId  = result.getInt("RENTED_CAR_ID");
                list.add(new Customer(id, name, rentedCarId));
            }

        } catch(Exception e){

        }
        return list;
    }

    @Override
    public void add(String name) {
        client.run(String.format(INSERT_DATA, name));
    }

    @Override
    public Car getRentedCar(int customerId) {
        ResultSet result = client.find("SELECT * FROM CAR JOIN COMPANY ON CAR.COMPANY_ID = COMPANY.ID JOIN CUSTOMER ON CAR.ID = CUSTOMER.RENTED_CAR_ID WHERE CUSTOMER.ID = " + customerId);
        try {
            while(result.next()) {


                return new Car(result.getInt("CAR.ID"), result.getString("CAR.NAME"), new Company(result.getInt("COMPANY.ID"),result.getString("COMPANY.NAME")));
            }

        } catch(Exception e){

        }

        return null;
    }

    @Override
    public void update(int customerId, int carId) {
        if(carId == -1){
            client.run(String.format(RETURN_CAR, customerId));
        } else {
            client.run(String.format(RENT_CAR, carId, customerId));
        }

    }
}
