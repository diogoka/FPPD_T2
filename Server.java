import java.rmi.Naming;

public class Server{
    Server() {
        try {
            Functions c = new Administration();
            Naming.rebind("rmi://127.0.0.1:1099/BankService", c);
            System.out.println("Servidor pronto.");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public static void main(String[] args){
        new Server();
    }
}
