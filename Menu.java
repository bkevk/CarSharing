package carsharing;

public class Menu {
    private int level;


    public Menu(){
        this.level = 0;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void level0(){
        System.out.println("1. Log in as a manager\n" +
                "2. Log in as a customer\n" +
                "3. Create a customer\n" +
                "0. Exit");
        this.setLevel(0);
    }

    public void level1(){
        System.out.println("1. Company list\n" +
                "2. Create a company\n" +
                "0. Back");
        this.setLevel(1);
    }

    public void level2(){
        System.out.println("1. Car list\n" +
                "2. Create a car\n" +
                "0. Back");
        this.setLevel(2);
    }

    public void level2_0(){
        System.out.println("1. Rent a car\n" +
                "2. Return a rented car\n" +
                "3. My rented car\n" +
                "0. Back");
    }
}
