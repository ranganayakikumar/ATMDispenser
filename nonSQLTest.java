import java.util.Scanner;

public class nonSQLTest {
    private static Scanner in;
    private static int balance = 200;
    private static int count20 = 5;
    private static int count50 = 2;

    public static void main(String args[]) {
        in = new Scanner(System.in);


        transaction();
    }
    private static void transaction() {

        int choice;

        System.out.println("Please select an option");
        System.out.println("1. Withdraw");
        System.out.println("2. Balance");
        System.out.println("3. Credit");
        System.out.println("4. Exit");


        choice =in.nextInt();

        switch(choice)

        {
            case 1:
                int amount;
                System.out.println("Please enter amount to withdraw: ");
                amount = in.nextInt();
                if((amount % 20 != 0 && amount % 50 != 0) && ((amount % 50) % 20 != 0) ){
                    System.out.println("Incorrect Amount \n\n");
                } else {
                    if (amount > balance || amount == 0) {
                        System.out.println("You have insufficient funds\n\n");
                        transaction();
                    } else {
                        if(amount % 50 == 0 && ((amount / 50) < count50 || (amount / 50) == count50 )){
                            int amount50 = amount / 50;
                            balance = balance - amount;
                            count50 = count50 - (amount/50);
                            System.out.println("You have withdrawn! " + amount + " with 50 notes : " + amount50 + " and your new balance is " + balance + "\n");
                            transaction();
                        } else if(amount % 20 == 0 && ((amount / 20) < count20 || (amount / 20) == count20 )){
                            int amount20 = amount / 20;
                            balance = balance - amount;
                            count20 = count20 - (amount/20);
                            System.out.println("You have withdrawn " + amount + " with 20 notes : " + amount20 + " and your new balance is " + balance + "\n");
                            transaction();
                        } else {
                            int balance50 = 0 ;
                            int amountWithdrwan = amount;
                            int note20 = 0 ;
                            int note50 = 0 ;
                            for(int i = 0; i <= count50 && count50 >= 1; i++ ) {
                                if(amount >= 50) {
                                    note50 = note50 + 1;
                                    amount = amount - 50;
                                    balance50 = balance50 + 1;
                                    count50 = count50 - 1 ;
                                } else {
                                    break;
                                }
                            }
                            int balance20 = 0;
                            for(int i = 0; i <= count20 && count20 >= 1; i++ ) {
                                if(amount >= 20) {
                                    note20 = note20 + 1;
                                    amount = amount - 20;
                                    balance20 = balance20 +1;
                                    count20 = count20 - 1 ;
                                } else {
                                    break;
                                }
                            }
                            balance = (count20 * 20) + (count50 *50);

                            System.out.println("You have withdrawn " + amountWithdrwan + " with 20$ note : " + note20 +" and 50$ note : "+ note50 + " and your new balance is " + balance + "\n");
                            transaction();
                        }
                    }
                }
                break;


            case 2:

                System.out.println("Your balance is " + balance + "\n");
                transaction();
                break;

            case 3:

                System.out.println("Please enter the number of notes ");
                System.out.println("Please enter the number of 20$ notes ");
                int note20 = 0 ;
                note20 = in.nextInt();
                System.out.println("Please enter the number of 50$ notes ");
                int note50 = 0 ;
                note50 = in.nextInt();

                count20 = count20 + note20;
                count50 = count50 + note50;

                balance = (count20 * 20) + (count50 *50);
                System.out.println("Your new balance is " + balance + "\n");
                transaction();
                break;
            case 4: System.exit(0);
                break;
            default:
                System.out.println("Invalid option:\n\n");
                transaction();
                break;
        }


    }
}
