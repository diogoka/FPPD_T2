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
    Scanner sc2 = new Scanner(System.in);           
    Scanner sc3 = new Scanner(System.in);            
        
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
                        System.out.println("Informe o número da conta para checagem do saldo:");
                        int op3 = sc2.nextInt();
                        System.out.println("Saldo atual: R$ " + c.balance(op3));
                        menu(c);
                        break;
                    }
                    case 2: {
                        System.out.println("Informe o número da conta e o valor a ser depositado:");
                        int op4 = sc2.nextInt();
                        double op5 = sc3.nextDouble();
                        System.out.println("Saldo atual: R$ " + c.deposit(op4, op5));
                        menu(c);     

                        break;
                    }
            
                    case 3: {
                        System.out.println("Informe o número da conta e o valor a ser sacado:");
                        int op6 = sc2.nextInt();
                        double op7 = sc3.nextDouble();
                        System.out.println("Saldo atual: R$ "+ c.withdraw(op6, op7));
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
