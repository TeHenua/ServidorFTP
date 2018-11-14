import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class Servidor {

    private static ServerSocket servidor = null;
    private static String ruta = "ArchivosServidor//";
    private static String carpeta = "ArchivosServidor";

    public static void main(String args[]) throws IOException {
        servidor = new ServerSocket(21);
        while (true){
            Socket socket = servidor.accept();
            System.out.println("Cliente "+socket.getInetAddress().getHostName()+" conectado.");
            DataInputStream dataOpcion = new DataInputStream( socket.getInputStream() );
            String opcion="";
            while (!opcion.equalsIgnoreCase("c")) {
                opcion = dataOpcion.readUTF();
                System.out.println("Opción recibida: "+opcion);
                switch (opcion) {
                    case "a":
                        Recibir.recibirArchivo(socket, ruta);
                        break;
                    case "b":
                        Enviar.enviarListado(socket, new File(carpeta));
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
