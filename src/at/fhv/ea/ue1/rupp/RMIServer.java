package at.fhv.ea.ue1.rupp;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class RMIServer implements IRMIServer {

    public static void main(String[] args) {
        System.setProperty("java.rmi.server.codebaseOnly", String.valueOf(false));
        System.setProperty("java.security.policy", "./client.policy");

        SecurityManager sm = new MySecurityManager();
        System.setSecurityManager(sm);
        RMIServer serv = new RMIServer();
        Registry registry;
        try {
            registry = LocateRegistry.createRegistry(2345); // Creates the Registry necessary for RMI
            String[] list = registry.list();
            System.out.println(Arrays.toString(list));
            IRMIServer stub = (IRMIServer) UnicastRemoteObject.exportObject(serv, 2345); // Creates the
            // code stub
            // necessary for
            // RMI
            registry.bind("IRMIServer", stub); // binds the stub to the registry, allowing others to call the
            System.out.println(Arrays.toString(list));
            // methods of the FileServerInterface-interface remotely.
        } catch (RemoteException | AlreadyBoundException e1) {

            e1.printStackTrace();
        }
        System.out.println("Started");
    }

    @Override
    public double pow(double operand, double toPower) {
        return Math.pow(operand, toPower);
    }

    @Override
    public List<TestEntity> getList() {
        System.out.println("Triggered");
        TestEntity ent1 = new TestEntity(0, "Bob");
        TestEntity ent2 = new TestEntity(1, "Klaus");
        TestEntity ent3 = new TestEntity(2, "Anna");
        LinkedList<TestEntity> list = new LinkedList<>();
        list.add(ent1);
        list.add(ent2);
        list.add(ent3);
        return list;

    }
}
