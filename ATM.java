import java.rmi.Naming;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.rmi.Remote;
import java.rmi.RemoteException;

public class ATM{
    public static void main(String[] args){
        try {
            Functions c = (Functions) Naming.lookup("rmi://127.0.0.1:1099/BankService");
            menu(c);
                                   
        }
        catch (Exception e){
            System.out.println(e);
        }
    }


    public static void menu(Functions aux) throws RemoteException {
    Functions c = aux;
    System.out.println("Caixa Automatico:");  
    Scanner sc1 = new Scanner(System.in);          
    Scanner sc4 = new Scanner(System.in);           
    Scanner sc5 = new Scanner(System.in);           
    Scanner sc6 = new Scanner(System.in);           
    Scanner sc7 = new Scanner(System.in);                      
    Scanner sc8 = new Scanner(System.in);    
        
    System.out.println("DIGITE: ");
    System.out.println("1 - Consultar Saldo");
    System.out.println("2 - Solicitar deposito");
    System.out.println("3 - Solicitar saque");
    System.out.println("0 - Sair");

        try{
            int choose = sc1.nextInt();            
            if(choose == 1 || choose == 2 || choose == 3 || choose == 0){
                switch (choose){
                    case 1: {
                        System.out.println("Informe o numero da conta para checagem do saldo:");
                        int op3 = sc4.nextInt();
                        System.out.println("Saldo atual: " + c.balance(op3));
                        menu(c);
                        break;
                    }
                    case 2: {
                        System.out.println("Informe o numero da conta e o valor a ser depositado:");
                        int op4 = sc5.nextInt();
                        double op5 = sc6.nextDouble();
                        System.out.println("Saldo atual: " + c.deposit(op4, op5));
                        menu(c);     

                        break;
                    }
            
                    case 3: {
                        System.out.println("Informe o numero da conta e o valor a ser sacado:");
                        int op6 = sc7.nextInt();
                        double op7 = sc8.nextDouble();
                        System.out.println("Saldo atual: "+ c.withdraw(op6, op7));
                        menu(c);

                        break;
                    }            
            
                    case 0: {
                        System.out.println("Fim");
                        break;
                    }
                }
            }
        }catch (InputMismatchException e) {
            System.out.println("Opção inválida. Por favor digite um número entre 1 e 6, ou 0 para sair.");
            menu(c);          

        }
    }
    
}
