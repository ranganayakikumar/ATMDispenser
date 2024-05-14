import java.util.*
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class test {
    private static Scanner in;
    private static Scanner in;
    private static float balance = 200;

    public static void main(String args[]) {
        in = new Scanner(System.in);


        transaction();
    }

    private static void transaction() {
        String connectionUrl =
                "jdbc:sqlserver://localhost:1433;"
                        + "database=insite_2019_30_supplier_insite;"
                        + "user=SilverUserLogin;"
                        + "password=agpeople0112;"
                        + "encrypt=false;"
                        + "trustServerCertificate=true;"
                        + "loginTimeout=30;";

        int choice;

        System.out.println("Please select an option");
        System.out.println("1. Withdraw");
        System.out.println("2. Balance");
        System.out.printLn("3. Credit")
        System.out.println("4. Exit");

        ResultSet resultSet = null;

        try (Connection connection = DriverManager.getConnection(connectionUrl);
             Statement statement = connection.createStatement();) {

            // Create and execute a SELECT SQL statement.
            String selectSql = "SELECT SUM(amount) from atmDispenser";
            resultSet = statement.executeQuery(selectSql);
            int balance = 0;

            // Print results from select statement
            if (ObjectUtil.isUsable(resultSet.getString(1))) {
                balance = resultSet.getString(1);
            }

    choice =in.nextInt();

        switch(choice)

    {
        case 1:
            int amount;
            HashMap<int, int> denominationMap = new HashMap<>();
            System.out.println("Please enter amount to withdraw: ");
            amount = in.nextFloat();
            int note = 0 ;
            int count = 0 ;
            int count20 = 0 ;
            int count50 = 0 ;
            String selectSql = "SELECT denomination, count from atmDispenser ";
            resultSet = statement.executeQuery(selectSql);

            while (resultSet.next()) {
                int note = resultSet.getInt("denomination");
                int count =  resultSet.getInt("count");
                denominationMap.put(note,count);
            }


            if((amount % 20 != 0 && amount % 50 != 0) && ((amount % 50) % 20 != 0) ){
                System.out.println("Incorrect Amount \n\n");
            } else {
                if (amount > balance || amount == 0) {
                    System.out.println("You have insufficient funds\n\n");
                    transaction();
                } else {
                    if(amount % 50 == 0 && ((amount / 50) > count50 || (amount / 50) == count50 )){
                        if((amount / 50) > denominationMap.get(50) || (amount / 50) == denominationMap.get(50) ){
                            int amount50 = amount / 50;
                            balance = balance - amount;
                            count50 = denominationMap.get(50) - amount50;
                            System.out.println("You have withdrawn " + amount + " with 50 notes : " + amount50 + " and your new balance is " + balance + "\n");

                        }
                    }

                    int dbAmount20 = count20 * 20;
                    int dbAmount50 = count50 * 50;
                    PreparedStatement pstmt;

                    pstmt = connection.prepareStatement(
                            "UPDATE atmDispenser SET count=?, amount=? WHERE denomination=?");
                    // Create a PreparedStatement object
                    pstmt.setString(1,String.ValueOf(count20));
                    pstmt.setString(2,String.ValueOf(dbAmount20));
                    pstmt.setString(3,"20");
                    numUpd = pstmt.executeUpdate();   // Perform first update
                    pstmt.setString(1,String.ValueOf(count50));
                    pstmt.setString(2,String.ValueOf(dbAmount50));
                    pstmt.setString(3,"50");
                    numUpd = pstmt.executeUpdate();
                    pstmt.close();

                    balance = balance - amount;
                    System.out.println("You have withdrawn " + amount + " and your new balance is " + balance + "\n");
                    transaction();
                }
            }

            break;


        case 2:

            System.out.println("Your balance is " + balance + "\n");
            transaction();
            break;

        default:
            System.out.println("Invalid option:\n\n");
            transaction();
            break;
    }
}
catch(SQLException e){
        e.printStackTrace();
        }

    }
}



