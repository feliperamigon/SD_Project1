import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.logging.*;

// Clase encargada de enviar y recibir los mensajes que vienen y van del servidor.
class Persona extends Thread {
    protected Socket sk;
    protected DataOutputStream dos;
    protected DataInputStream dis;
    private int id;
    
// Constructor
    
    public Persona(int id) {
        this.id = id;
    }
    
    @Override
    public void run() {
        try {
            sk = new Socket("127.0.0.1", 10578);
            dos = new DataOutputStream(sk.getOutputStream());
            dis = new DataInputStream(sk.getInputStream());
            System.out.println(id + " envía saludo");
            dos.writeUTF("hola");
            String respuesta="";
            respuesta = dis.readUTF();
            System.out.println(id + " Servidor devuelve saludo: " + respuesta);
            dis.close();
            dos.close();
            sk.close();
        } catch (IOException ex) {
            Logger.getLogger(Persona.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}


public class Cliente {
    public static void main(String[] args) {
        ArrayList< Thread> clients = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            clients.add(new Persona(i));
        }
        for (Thread thread : clients) {
            thread.start();
        }
    }
}
