import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;


public class Cliente {

    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static InetAddress direccion;
    private static int port = 21;
    private static String ruta = "ArchivosCliente//";

    public static void main(String args[]) throws IOException {
        direccion = InetAddress.getByName("localhost");
        Socket socket = new Socket( direccion, port );
        socket.setSoTimeout( 2000 );
        socket.setKeepAlive( true );
        String opcion ="";
        //while (!opcion.equalsIgnoreCase("c")){
            opcion = menu();
            enviarOpcion(opcion,socket);
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
        //}

    }

    private static void enviarFichero(Socket socket) throws IOException {
        System.out.println("Escribe el nombre del fichero (sin extensión)");
        String nombre = br.readLine()+".txt";
        File archivo = new File(ruta+nombre);

        Enviar.enviarArchivo(socket,archivo);               //TODO validar si existe el fichero java.io.FileNotFoundException:
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

    public static void enviarOpcion(String opcion, Socket socket) throws IOException {
        DataOutputStream dos = new DataOutputStream( socket.getOutputStream());
        dos.writeUTF(opcion);
    }

    public static String seleccionarArchivo(Socket socket) throws IOException {
        ArrayList lista = Recibir.recibirListado(socket);
        System.out.println("\nArchivos disponibles en el servidor:");
        for (int i = 0; i < lista.size(); i++) {
            System.out.println("- "+lista.get(i));
        }
        System.out.println("Escribe el nombre del archivo que quieres descargar (sin extensión)");
        return br.readLine();
    }
}
