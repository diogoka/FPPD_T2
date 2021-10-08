import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface Functions extends Remote
{
    public String withdraw(int a, double b) throws RemoteException; 
    public String deposit (int a, double b) throws RemoteException; 
    public void openAccount() throws RemoteException; 
    public boolean closeAccount(int k) throws RemoteException;
    public boolean autentication(int k) throws RemoteException;
    public String balance(int k) throws RemoteException;
    public ArrayList<String> database() throws RemoteException; 
    public ArrayList<String> acountMoviments (int y) throws RemoteException;

}
