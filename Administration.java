import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.rmi.Remote;


public class Administration extends UnicastRemoteObject implements Functions {
    
    protected Administration() throws RemoteException {
        super();
    }

    private static int numbers = 0;    
    private static int numbersMov = 0;    
    
    ArrayList<String> hashes = new ArrayList();
    ListAccounts ListOfAccounts = new ListAccounts();
    

    public String rndString () {
            String rndString = "";
            for(int i = 0; i < 12; i++) {
                int rnd = (int) (Math.random() * 52);
                char base = (rnd < 26) ? 'A' : 'a';
                char rndChar = (char) (base + rnd % 26);
                rndString += rndChar;
            }   
            return rndString;         
    }

    public boolean keyCheck(String r){
            boolean B = false;
            for(int i=0; i<=hashes.size()-1;i++){
                if(hashes.get(i).equals(r)){
                    B = true;
                };
            }
            return B;
    }

       

    public class Account extends UnicastRemoteObject implements Remote {
        private int Num;
        private String Signature;
        private double Balance;
        private ListOperations Movimentos;

        public Account (int oneNum, String oneID, double oneBalance, ListOperations Movimentos) throws RemoteException{
            this.Num = oneNum;
            this.Signature = oneID;
            this.Balance = oneBalance;
                        
        }
      
        public int getNum() throws RemoteException{
            return Num;
        }
    
        public String getSignature() throws RemoteException{
            return Signature;
        }
    
        public double getBalance() throws RemoteException{
            return Balance;
        }

        public ListOperations getListOperations() throws RemoteException{
            return Movimentos;
        }

        public void insertMove(Operation M) {
            Movimentos.insert(M);
        }
    
        public void setNum(int oneNum) throws RemoteException{
            Num = oneNum;
        }
    
        public void setSign(String oneSign) throws RemoteException{
            Signature = oneSign;
        }

        public void setListOP(ListOperations LO2) throws RemoteException{
            Movimentos = LO2;
        }
    
        public void setBalancePlus(double oneBalance) throws RemoteException{
            Balance = Balance + oneBalance;
        }

        public void setBalanceMinus(double oneBalance) throws RemoteException{
            Balance = Balance - oneBalance;
        }
    
        @Override
        public String toString() {
            try {
                return "Numero da conta: " + getNum()  + " - " + " Hash: " + getSignature() + " Saldo: " + getBalance();
            } catch (RemoteException e) {                
                e.printStackTrace();
            }
            return null;
        } 
    }

    public class ListAccounts extends UnicastRemoteObject {

        private ArrayList<Account> Accounts;
       
        public ListAccounts() throws RemoteException {
            Accounts = new ArrayList<>();
        }
    
        public ArrayList<Account> getList() throws RemoteException{
            return Accounts;
        }
    
        public void insert(Account oneAccount) throws RemoteException {
            Accounts.add(oneAccount);      
                 
        }

        public boolean remove (int k) throws RemoteException {
            boolean found = false;
            for (int i = 0; i <= Accounts.size()-1; i++){             
                if (Accounts.get(i).getNum() == k){
                    Accounts.remove(i);                    
                    System.out.println("Conta removida.");
                    found = true;
                }               
           
            }

            if (found == false){
                System.out.println("Conta nao encontrada.");
            }
            return found;
            
        }

        public boolean aut (int k) throws RemoteException{
            boolean found = false;
            for (int i = 0; i <= Accounts.size()-1; i++){             
                if (Accounts.get(i).getNum() == k){                   
                    System.out.println("Conta Autenticada.");
                    found = true;
                }
                
            }
            if (found == false) {
                System.out.println("Conta nao encontrada.");
            }
       
            return found;
        }

        public String searchBalance(int k) throws RemoteException{
            boolean found = false;
            String res ="";
            for (int i = 0; i <= Accounts.size()-1; i++){             
                if (Accounts.get(i).getNum() == k){                   
                    System.out.println("Saldo: " + Accounts.get(i).getBalance());
                    found = true;
                    res = Double.toString(Accounts.get(i).getBalance());
                }
                
            }
            if (found == false) {
                System.out.println("Conta nao encontrada.");
            }
            return res;            
        }

        public ArrayList<String> listAccs() throws RemoteException{
        ArrayList<String> JK = new ArrayList<>();
        System.out.println("Lista de Contas");
        for (Account ACC : Accounts) {
            JK.add(ACC.toString());            
            }
            return JK;
        }     

        public String makeADeposit(int acc, double value) throws RemoteException{
            boolean found = false;
            String res = "";
            
            for(int i = 0; i<=Accounts.size()-1;i++){
                if(Accounts.get(i).getNum() == acc){
                    Accounts.get(i).setBalancePlus(value);
                    Operation M = new Operation(0, "", "Deposito: "+ value);
                    M.setNum(Administration.numbersMov = Administration.numbersMov+1);
                    String key = rndString();        
                    while(keyCheck(key)==true){
                        key = rndString();            
                    }
                    hashes.add(key);
                    M.setSign(key);
                    Accounts.get(i).getListOperations().insert(M);              
                    found = true;
                    res = Double.toString(Accounts.get(i).getBalance());
                }

            }
            if (found == false){
                res = "Conta nao encontrada.";
            }

            return res;

        }

        public String makeAWithdraw(int acc, double value) throws RemoteException{
            boolean found = false;
            String res = "";     
            for(int i = 0; i<=Accounts.size()-1;i++){
                if(Accounts.get(i).getNum() == acc){
                    Accounts.get(i).setBalanceMinus(value);
                    Operation M = new Operation(0, "", "Saque: "+ value);
                    M.setNum(Administration.numbersMov = Administration.numbersMov+1);
                    String key = rndString();        
                    while(keyCheck(key)==true){
                        key = rndString();            
                    }
                    hashes.add(key);
                    M.setSign(key);
                    Accounts.get(i).getListOperations().insert(M);   
                    found = true;
                    res = Double.toString(Accounts.get(i).getBalance());
                }
            }
            if (found == false){
                res = "Conta nao encontrada.";
            }
            return res;
        }

        public ArrayList<String> movements(int c) throws RemoteException{            
            ArrayList<String> aux = new ArrayList<>();  
            boolean found = false;  
            for(int i=0; i<=Accounts.size()-1;i++){
                if(Accounts.get(i).getNum() == c){
                    aux = Accounts.get(i).getListOperations().getOperationString();
                    found = true;                   
                }
            }
            if(found == false){
                aux.add("Conta nao encontrada");
                aux.toString();                
            }
            return aux;
        }
       
    }
    
    public class Operation extends UnicastRemoteObject implements Remote{

        private String realizado;
        private int numberMov;
        private String sigMov;
    
        
        public Operation (int oneNum, String oneS, String oneDone) throws RemoteException{
            this.numberMov = oneNum;
            this.sigMov = oneS;
            this.realizado = oneDone;
        }
      
        public int getNum(){
            return numberMov;
        }
    
        public String getSignature(){
            return sigMov;
        }
    
        public String getRealizado(){
            return realizado;
        }

        public void setNum(int oneNum) throws RemoteException{
            numberMov = oneNum;
        }
    
        public void setSign(String oneSign) throws RemoteException{
            sigMov = oneSign;
        }

        @Override
        public String toString(){
            return "Numero do movimento " + getNum() + "-" + " Hash: " + getSignature() + " Movimento: " + getRealizado();
        } 
    }

    private class ListOperations extends UnicastRemoteObject{
    
        private ArrayList<Operation> Movements;
       
        public ListOperations() throws RemoteException {
            Movements = new ArrayList<>();
        }
    
        public ArrayList<Operation> getList(){
            return Movements;
        }
    
        public void insert(Operation oneMovement) {
            Movements.add(oneMovement);      
                 
        }
     
        public void listOPS() {
            System.out.println("Lista de Operaçoes");
            for (Operation OPP : Movements) {
                System.out.println(OPP.toString());
            }
        }

        public ArrayList<String> getOperationString(){
            ArrayList<String> aux = new ArrayList<>();
            for(int i = 0; i<=Movements.size()-1;i++){
                String S1 = "Numero do movimento: " + Integer.toString(Movements.get(i).getNum()) + " - ";
                String S2 = "Hash: " + Movements.get(i).getSignature() + " - ";
                String S3 = "Movimento: " + Movements.get(i).getRealizado();
                String Full = S1.concat(S2).concat(S3);
                aux.add(Full);                                
            }          
            return aux;           
        }
        
    }
        
    public void openAccount() throws RemoteException{
        ListOperations LO = new ListOperations();        
        Account A1 = new Account(0,"",00, null);
        A1.setNum(Administration.numbers = Administration.numbers +1);
        String key = rndString();        
        while(keyCheck(key)==true){
            key = rndString();
        }
        hashes.add(key);
        A1.setSign(key);
        A1.setListOP(LO);        
        ListOfAccounts.insert(A1);
        A1.toString();    
        System.out.println("Conta aberta:");
        System.out.println("Numero da conta: " + A1.getNum() + " - Hash: "+ A1.getSignature() + " - Saldo :" + A1.getBalance());
    }; 

    public boolean closeAccount(int k) throws RemoteException{
        return ListOfAccounts.remove(k);       
    };

    public boolean autentication(int k) throws RemoteException{
        return ListOfAccounts.aut(k);
    };

    public String balance(int k) throws RemoteException{
        return ListOfAccounts.searchBalance(k);
    };


    public String deposit(int acc, double value) throws RemoteException{        
        return ListOfAccounts.makeADeposit(acc, value);
        
    };
    
    public String withdraw(int a, double b) throws RemoteException{ 
        return ListOfAccounts.makeAWithdraw(a, b);    
    }

    public ArrayList<String> database () throws RemoteException{
        return ListOfAccounts.listAccs();
        
    };

    public ArrayList<String> acountMoviments(int x) throws RemoteException{      
        return ListOfAccounts.movements(x);

    }


}