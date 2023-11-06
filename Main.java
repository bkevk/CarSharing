package carsharing;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // write your code here
        Menu menu = new Menu();
        Scanner scanner = new Scanner(System.in);
        DbClient db = new DbClient();
        DbCompanyDao client = new DbCompanyDao(db);

        DbCustomerDao customerDb = new DbCustomerDao(db);
        DbCarDao carDb = new DbCarDao(db);
        login(menu, scanner, client, carDb, customerDb);

    }

    public static void login(Menu menu, Scanner scanner,DbCompanyDao client, DbCarDao carDb, DbCustomerDao customerDb){
        menu.level0();
        int input = scanner.nextInt();
        if(input == 0){
            return;
        }
        if(input == 1){
            companyMenu(menu, scanner, client, carDb, customerDb);
        }
        if(input == 2){
            if(customerDb.findAll().isEmpty()){
                System.out.println("The customer list is empty!");
                login(menu, scanner, client, carDb, customerDb);

            }else{
                System.out.println("Customer list:");
                customerDb.findAll().stream().forEach(customer -> System.out.println(customer.getId() + ". " + customer.getName()));
                System.out.println("0. Back");
                input = scanner.nextInt();
                customerMenu(menu, scanner, client, carDb, customerDb, input);

            }
        }
        if(input == 3){
            System.out.println("Enter the customer name:");
            String customer = scanner.nextLine();
            customer = scanner.nextLine();
            customerDb.add(customer);
            System.out.println("The customer was added!");
            login(menu, scanner, client, carDb, customerDb);
        }
    }

    public static void companyMenu(Menu menu, Scanner scanner, DbCompanyDao client, DbCarDao carDb, DbCustomerDao customerDb){
        menu.level1();
        int input = scanner.nextInt();
        if(input == 0){
            login(menu, scanner, client, carDb, customerDb);
        }
        if(input == 1){
            List<Company> list = client.findAll();
            if(list.isEmpty()){
                System.out.println("The company list is empty!");
                companyMenu(menu, scanner, client, carDb, customerDb);
            }else {
                System.out.println("Choose the company:");
                list.stream().forEach(company -> System.out.println(company.getId() + ". " + company.getName()));

            }
            System.out.println("0. Back");
            int choose = scanner.nextInt();
            if(choose == 0){
                companyMenu(menu, scanner, client, carDb, customerDb);
            }else{
                carMenu(menu, scanner, client, carDb, customerDb, choose);
            }

        }
        if(input == 2){
            System.out.println("Enter the company name:");
            String company = scanner.nextLine();
            company = scanner.nextLine();
            client.add(company);
            System.out.println("The company was created!");
            companyMenu(menu, scanner, client, carDb, customerDb);
        }
    }

    public static void carMenu(Menu menu, Scanner scanner, DbCompanyDao client, DbCarDao carDb, DbCustomerDao customerDb, int choose){

        Company company =  client.findById(choose);

        System.out.println("'" + company.getName() + "' company");
        menu.level2();
        int input = scanner.nextInt();
        if(input == 0){
            companyMenu(menu, scanner, client, carDb, customerDb);
        }
        if(input == 1){
            List<Car> list = carDb.findAll(company);
            if(list.isEmpty()){
                System.out.println("The car list is empty!");
                carMenu(menu, scanner, client, carDb, customerDb, choose);
            }else {
                for(int i = 0; i<list.size(); i++){
                    System.out.println(i + 1 + ". " + list.get(i).getName());
                }

            }
            carMenu(menu, scanner, client, carDb, customerDb, choose);

        }
        if(input == 2){
            System.out.println("Enter the car name:");
            String car = scanner.nextLine();
            car = scanner.nextLine();
            carDb.add(car, company);
            System.out.println("The car was added!");
            carMenu(menu, scanner, client, carDb, customerDb, choose);
        }

    }

    public static void customerMenu(Menu menu, Scanner scanner, DbCompanyDao client, DbCarDao carDb, DbCustomerDao customerDb, int choose){
        menu.level2_0();
        int input = scanner.nextInt();
        if(input == 0){
            login(menu, scanner, client, carDb, customerDb);
        }
        if(input == 3){
            if(customerDb.getRentedCar(choose) == null){
                System.out.println("You didn't rent a car!");
                customerMenu(menu, scanner, client, carDb, customerDb, input);
            } else {
                System.out.println("Your rented car:");
                System.out.println(customerDb.getRentedCar(choose).getName());
                System.out.println("Company:");
                System.out.println(customerDb.getRentedCar(choose).getCompany().getName());
                customerMenu(menu, scanner, client, carDb, customerDb, choose);
            }

        }
        if(input == 2){
            if(customerDb.getRentedCar(choose) == null){
                System.out.println("You didn't rent a car!");
                customerMenu(menu, scanner, client, carDb, customerDb, input);
            } else {
                System.out.println("You've returned a rented car!");
                customerDb.update(choose, -1);
                customerMenu(menu, scanner, client, carDb, customerDb, choose);
            }
        }

        if(input == 1){
            if(customerDb.getRentedCar(choose) == null){
                List<Company> list = client.findAll();
                System.out.println("Choose the company:");
                list.stream().forEach(company -> System.out.println(company.getId() + ". " + company.getName()));
                System.out.println("0. Back");
                int companyInput = scanner.nextInt();

                List<Car> carList = carDb.findAll(client.findById(companyInput));
                if(list.isEmpty()){
                    System.out.println("The car list is empty!");
                    //customerMenu(menu, scanner, client, carDb, customerDb, input);
                }else {
                    System.out.println("Choose a car:");
                    int counter = 0;
                    for(int i = 0; i<carList.size(); i++){
                        if(carDb.isAvailable(carList.get(i).getId())){
                            System.out.println(counter + 1 + ". " + carList.get(i).getName());
                            counter++;
                        }


                    }
                    System.out.println("0. Back");

                    int carInput = scanner.nextInt();
                    if(carInput == 0){
                        customerMenu(menu, scanner, client, carDb, customerDb, choose);
                    }else{
                        customerDb.update(choose, carInput);
                        System.out.println("You rented '" + carList.get(carInput - 1).getName() + "'");
                        customerMenu(menu, scanner, client, carDb, customerDb, input);
                    }

                }

            } else {
                System.out.println("You've already rented a car!");
                customerMenu(menu, scanner, client, carDb, customerDb, choose);
            }
        }
    }

}
