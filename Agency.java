import java.rmi.Naming;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.rmi.Remote;
import java.rmi.RemoteException;

public class Agency{
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
        System.out.println("Agencia Bancaria. ");  
        Scanner sc1 = new Scanner(System.in);
        Scanner sc2 = new Scanner(System.in);           
        Scanner sc3 = new Scanner(System.in);           
        Scanner sc4 = new Scanner(System.in);           
        Scanner sc5 = new Scanner(System.in);           
        Scanner sc6 = new Scanner(System.in);           
        Scanner sc7 = new Scanner(System.in);                      
        Scanner sc8 = new Scanner(System.in);
        Scanner sc9 = new Scanner(System.in);
        
            
        System.out.println("DIGITE: ");
        System.out.println("1 - Solicitar abertura de conta");
        System.out.println("2 - Solicitar fechamento de conta");
        System.out.println("3 - Autenticar conta");
        System.out.println("4 - Consultar Saldo");
        System.out.println("5 - Solicitar deposito");
        System.out.println("6 - Solicitar saque");
        System.out.println("7 - Contas cadastradas");
        System.out.println("8 - Movimentos realizados na conta");    
        System.out.println("0 - Sair");


        try{
            int choose = sc1.nextInt();            
            if(choose == 1 || choose == 2 || choose == 3 || choose == 4 || choose == 5 || choose == 6 || choose == 7 || choose == 8 || choose == 0){
                switch (choose){
                    case 1: {                
                        System.out.println("Conta aberta.");
                        c.openAccount();                        
                        menu(c);
                        break;
                    }
                    case 2: {
                        System.out.println("Informe o numero da conta a ser fechada:");
                        int op = sc2.nextInt();
                        if(c.closeAccount(op) == true){
                            System.out.println("Conta encerrada.");
                        }
                        else{
                            System.out.println("Conta inexistente.");
                        };
                        menu(c);
                        break;
                    }
                    case 3: {
                        System.out.println("Informe o numero da conta para autenticacao");
                        int op2 = sc3.nextInt();
                        if(c.autentication(op2)==true){
                            System.out.println("Conta Autenticada.");
                        }
                        else{
                            System.out.println("Conta inexistente.");
                        };
                        menu(c);
                        break;
                    }
                    case 4: {
                        System.out.println("Informe o numero da conta para checagem do saldo:");
                        int op3 = sc4.nextInt();
                        System.out.println("Saldo atual: " + c.balance(op3));
                        menu(c);
                        break;
                    }
                    case 5: {
                        System.out.println("Informe o numero da conta e o valor a ser depositado:");
                        int op4 = sc5.nextInt();
                        double op5 = sc6.nextDouble();
                        System.out.println("Saldo atual: " + c.deposit(op4, op5));
                        menu(c); 
                        break;
                    }
                    case 6: {
                        System.out.println("Informe o numero da conta e o valor a ser sacado:");
                        int op6 = sc7.nextInt();
                        double op7 = sc8.nextDouble();
                        System.out.println("Saldo atual: "+ c.withdraw(op6, op7));
                        menu(c);
                        break;
                    }

                    case 7:{            
                        for (int i = 0; i<=c.database().size()-1;i++){
                            System.out.println(c.database().get(i).toString());
                            System.out.println("\n");
                        }                        
                        menu(c);
                        break;
                    }
                    case 8:{
                        System.out.println("Informe o numero da conta para busca");
                        int op8 = sc9.nextInt();     
                        c.acountMoviments(op8);
                        for(int i = 0; i<=c.acountMoviments(op8).size()-1;i++){
                            System.out.println(c.acountMoviments(op8).get(i).toString());
                            System.out.println("\n");
                        }                         
                        menu(c);                
                        break;
                    }
                    case 0: {
                        System.out.println("Fim");
                        break;
                    }               
                }
            }
        } catch (InputMismatchException e) {
        System.out.println("Opção inválida. Por favor digite um número entre 1 e 6, ou 0 para sair.");
        menu(c); 
        }
    }
}
