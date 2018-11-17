import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class Servidor {

    private static ServerSocket servidor = null;
    private static String ruta = "ArchivosServidor//";
    private static String carpeta = "ArchivosServidor";
    private static Socket socket;
    private static DataInputStream dataOpcion;
    private static DataInputStream dis; 
    private static DataOutputStream dos; 
    private static BufferedInputStream in; 

    public static void main(String args[]) throws IOException {
        servidor = new ServerSocket(21);    
        while (true){
            socket = servidor.accept();
            System.out.println("Cliente "+socket.getInetAddress().getHostName()+" conectado.");
            
            dataOpcion = new DataInputStream(socket.getInputStream());
            dis= new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            in = new BufferedInputStream(socket.getInputStream());
            
            String opcion="";
            while (!opcion.equalsIgnoreCase("c")) {
                opcion = dataOpcion.readUTF();
                System.out.println("Opción recibida: "+opcion);
                switch (opcion) {
                    case "a":
                        Recibir.recibirArchivo(dis,in,ruta);
                        break;
                    case "b":
                        Enviar.enviarListado(dos, new File(carpeta));
                        //String nombre = Recibir.recibirNombre(socket);
                        //Enviar.enviarArchivo();enviarFichero(socket,nombre);
                        break;
                    case "c":

                        socket.close();
                        System.out.println("Conexión cliente " + socket.getInetAddress().getHostName() + " cerrada");
                }
            }
        }
    }

}
