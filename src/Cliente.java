import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;


public class Cliente {

    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static InetAddress direccion;
    private static int port = 21;
    private static String ruta = "ArchivosCliente//";
    private static Socket socket;
    private static DataOutputStream dos;
    private static BufferedOutputStream bos;
    private static DataInputStream dis; 

    public static void main(String args[]) throws IOException {

        direccion = InetAddress.getByName("localhost");
        socket = new Socket( direccion, port );
        socket.setSoTimeout( 2000 );
        socket.setKeepAlive( true );
        dos = new DataOutputStream( socket.getOutputStream());
        bos = new BufferedOutputStream( socket.getOutputStream());
        dis = new DataInputStream(socket.getInputStream());
        String opcion ="";
        while (!opcion.equalsIgnoreCase("c")){
            opcion = menu();
            enviarOpcion(opcion);
            switch (opcion) {
                case "a":{
                    enviarFichero(socket);
                }
                    break;
                case "b":{
                    String nombre = seleccionarArchivo(socket);
                }
                    break;
                case "c":{
                    System.out.println("Cerrando conexión...");
                    socket.close();
                }
            }
        }

    }

    private static void enviarFichero(Socket socket) throws IOException {
        System.out.println("Escribe el nombre del fichero (sin extensión)");
        String nombre = br.readLine()+".txt";
        File archivo = new File(ruta+nombre);

        Enviar.enviarArchivo(dos,bos,archivo);               //TODO validar si existe el fichero java.io.FileNotFoundException:
        bos.close();
    }

    public static String menu() throws IOException {
        boolean correcto = false;
        String opcion="";
        while (!correcto){
            System.out.println("Elige una opción (a/b/c): \na.- Enviar \nb.- Recibir \nc.- Salir");
            opcion = br.readLine();
            if (opcion.equalsIgnoreCase("a") || opcion.equalsIgnoreCase("b" ) || opcion.equalsIgnoreCase("c")){
                correcto = true;
            }else{
                System.out.println("Opción incorrecta. Vuelve a intentarlo");
                correcto = false;
            }
        }
        return opcion;
    }

    public static void enviarOpcion(String opcion) throws IOException {
        
        dos.writeUTF(opcion);
        System.out.println("Opción enviada: "+opcion);
    }

    public static String seleccionarArchivo(Socket socket) throws IOException {
        ArrayList lista = Recibir.recibirListado(dis);
        System.out.println("\nArchivos disponibles en el servidor:");
        for (int i = 0; i < lista.size(); i++) {
            System.out.println("- "+lista.get(i));
        }
        System.out.println("Escribe el nombre del archivo que quieres descargar (sin extensión)");
        return br.readLine();
    }
}
