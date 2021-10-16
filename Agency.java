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
        System.out.println("Agência Bancária. ");  
        Scanner sc1 = new Scanner(System.in);
        Scanner sc2 = new Scanner(System.in);           
        Scanner sc3 = new Scanner(System.in);           
        Scanner sc4 = new Scanner(System.in);           
        Scanner sc5 = new Scanner(System.in);           
        Scanner sc6 = new Scanner(System.in);           
 
        
            
        System.out.println("DIGITE: ");
        System.out.println("1 - Solicitar abertura de conta");
        System.out.println("2 - Solicitar fechamento de conta");
        System.out.println("3 - Autenticar conta");
        System.out.println("4 - Consultar Saldo");
        System.out.println("5 - Solicitar depósito");
        System.out.println("6 - Solicitar saque");
        System.out.println("7 - Contas cadastradas");
        System.out.println("8 - Movimentos realizados na conta");    
        System.out.println("9 - Simulação erro abertura conta");
        System.out.println("10 - Simulação erro depósito");
        System.out.println("11 - Simulação erro saque");
        System.out.println("0 - Sair");


        try{
            int choose = sc1.nextInt();            
            if(choose == 1 || choose == 2 || choose == 3 || choose == 4 || choose == 5 || choose == 6 || choose == 7 || choose == 8 || choose == 9 || choose == 10 || choose == 11 || choose == 0){
                switch (choose){
                    case 1: {                
                        System.out.println("Conta aberta.");
                        c.openAccount();                        
                        menu(c);
                        break;
                    }
                    case 2: {
                        System.out.println("Informe o número da conta a ser fechada:");
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
                        System.out.println("Informe o número da conta para autenticacao");
                        int op2 = sc2.nextInt();
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
                        System.out.println("Informe o número da conta para checagem do saldo:");
                        int op3 = sc2.nextInt();
                        System.out.println("Saldo atual: " + c.balance(op3));
                        menu(c);
                        break;
                    }
                    case 5: {
                        System.out.println("Informe o número da conta e o valor a ser depositado:");
                        int op4 = sc2.nextInt();
                        double op5 = sc3.nextDouble();
                        System.out.println("Saldo atual: " + c.deposit(op4, op5));
                        menu(c); 
                        break;
                    }
                    case 6: {
                        System.out.println("Informe o número da conta e o valor a ser sacado:");
                        int op6 = sc2.nextInt();
                        double op7 = sc3.nextDouble();
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
                        System.out.println("Informe o número da conta para busca");
                        int op8 = sc2.nextInt();     
                        c.acountMoviments(op8);
                        for(int i = 0; i<=c.acountMoviments(op8).size()-1;i++){
                            System.out.println(c.acountMoviments(op8).get(i).toString());
                            System.out.println("\n");
                        }                         
                        menu(c);                
                        break;
                    }
                    case 9:{
                        Thread t = new Thread();
                        c.openAccount();
                        System.out.println("Dados da conta a ser aberta: \n" + c.database().get(c.database().size()-1));
                        String parameter = c.database().get(c.database().size()-1);
                        int indexStart = parameter.indexOf("Hash");
                        String r = parameter.substring(indexStart+6, indexStart+18);                    
                        System.out.println("Solicitando abertura da conta....");
                        try {
                            t.sleep(7000);
                            System.out.println("Servidor não respondeu.");
                            System.out.println("Tentar novamente? - digite Y para sim ou N para não.");
                            String option = sc2.nextLine();
                            if(option.equalsIgnoreCase("Y")){  
                                System.out.println("Solicitando a abertura da conta " + c.database().get(c.database().size()-1) + "novamente ...");
                                t.sleep(3000);                              
                                for (int i = 0; i<=c.bankHashes().size()-1;i++){
                                    if(c.bankHashes().get(i).equals(r)){
                                        System.out.println("Erro: conta com o hash " + r + " já está aberta.");
                                        System.out.println("Retornando ao menu anterior...");
                                        t.sleep(5000);
                                    }
                                }
                          
                            }
                            else{
                                System.out.println("Retornando ao menu anterior...");
                                t.sleep(3000);                                
                                menu(c);
                            }

                            
                        } catch(InterruptedException e) {
                            e.printStackTrace();
                        }
                                               
                        menu(c);                        
                        break;

                    }
                    case 10:{
                        Thread t = new Thread();
                        System.out.println("Informe o número da conta e o valor a ser depositado:");
                        int op4 = sc5.nextInt();
                        double op5 = sc6.nextDouble();
                        c.deposit(op4, op5);
                        String parameter = c.acountMoviments(op4).get(c.acountMoviments(op4).size()-1);
                        int indexStart = parameter.indexOf("Hash");
                        String r = parameter.substring(indexStart+6, indexStart+18);    
                        System.out.println("Solicitando depósito de R$ " + op5 + " na conta de número: " + op4 + " ...");
                        System.out.println("Hash da operação: " + r);
                        try {
                            t.sleep(7000);
                            System.out.println("Servidor não respondeu.");
                            System.out.println("Tentar novamente? - digite Y para sim ou N para não.");
                            String option = sc2.nextLine();                            
                            if(option.equalsIgnoreCase("Y")){  
                                System.out.println("Solicitando depósito de R$ " + op5 + " na conta de número: " + op4 + " novamente...");
                                t.sleep(3000);                           
                                for (int i = 0; i<=c.bankHashes().size()-1;i++){
                                    if(c.bankHashes().get(i).equals(r)){
                                        System.out.println("Erro: movimento já realizado. " + "Hash do movimento: " + r);
                                        System.out.println("Retornando ao menu anterior...");
                                        t.sleep(5000);
                                    }
                                }
                            }
                            else{
                                System.out.println("Retornando ao menu anterior...");
                                t.sleep(3000);                               
                                menu(c);
                            }
                        
                        } catch(InterruptedException e) {
                            e.printStackTrace();
                        }                       
                                                
                        menu(c);
                        break;
                    }
                    case 11:{
                        Thread t = new Thread();
                        System.out.println("Informe o número da conta e o valor a ser sacado:");
                        int op4 = sc5.nextInt();
                        double op5 = sc6.nextDouble();
                        c.withdraw(op4, op5);
                        String parameter = c.acountMoviments(op4).get(c.acountMoviments(op4).size()-1);
                        int indexStart = parameter.indexOf("Hash");
                        String r = parameter.substring(indexStart+6, indexStart+18);    
                        System.out.println("Solicitando saque de R$ " + op5 + " na conta de número: " + op4 + " ...");
                        System.out.println("Hash da operação: " + r);
                        try {
                            t.sleep(7000);
                            System.out.println("Servidor não respondeu.");
                            System.out.println("Tentar novamente? - digite Y para sim ou N para não.");
                            String option = sc2.nextLine();                            
                            if(option.equalsIgnoreCase("Y")){  
                                System.out.println("Solicitando depósito de R$ " + op5 + " na conta de número: " + op4 + " novamente...");
                                t.sleep(3000);                           
                                for (int i = 0; i<=c.bankHashes().size()-1;i++){
                                    if(c.bankHashes().get(i).equals(r)){
                                        System.out.println("Erro: movimento já realizado. " + "Hash do movimento: " + r);
                                        System.out.println("Retornando ao menu anterior...");
                                        t.sleep(5000);
                                    }
                                }
                            }
                            else{
                                System.out.println("Retornando ao menu anterior...");
                                t.sleep(3000);                                
                                menu(c);
                            }
                        
                        } catch(InterruptedException e) {
                            e.printStackTrace();
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
        System.out.println("Opção inválida Por favor digite um nÃºmero entre 1 e 6, ou 0 para sair.");
        menu(c); 
        }
    }
}
