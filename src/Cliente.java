import java.io.*;
import java.net.InetAddress;
import java.net.Socket;


public class Cliente {

    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static InetAddress direccion;
    private static int port = 21;

    public static void main(String args[]) throws IOException {
        direccion = InetAddress.getByName("localhost");
        Socket socket = new Socket( direccion, port );
        socket.setSoTimeout( 2000 );
        socket.setKeepAlive( true );
        String opcion = menu();
        enviarOpcion(opcion);
        if (opcion.equalsIgnoreCase("a")){ //Enviar
            enviarFichero();
        }
    }

    private static void enviarFichero() throws IOException {
        Socket socket = new Socket(direccion,port);
        System.out.println("Escribe el nombre del fichero (sin extensión)");
        String nombre = br.readLine();
        File archivo = new File(nombre+".txt");// Obtenemos el tamaÃ±o del archivo
        int tamArchivo = ( int )archivo.length();
        // Creamos el flujo de salida, este tipo de flujo nos permite
        // hacer la escritura de diferentes tipos de datos tales como
        // Strings, boolean, caracteres y la familia de enteros,etc.
        DataOutputStream dos = new DataOutputStream( socket.getOutputStream() );
        System.out.println( "Enviando Archivo: "+archivo.getName() );
        // Enviamos el nombre del archivo
        dos.writeUTF( archivo.getName() );
        // Enviamos el tamaÃ±o del archivo
        dos.writeInt( tamArchivo );
        // Creamos flujo de entrada para realizar la lectura del archivo en bytes
        FileInputStream fis = new FileInputStream(EnviarArchivo.Archivo);
        BufferedInputStream bis = new BufferedInputStream( fis );
        // Creamos el flujo de salida para enviar los datos del archivo en
        BufferedOutputStream bos = new BufferedOutputStream( socket.getOutputStream());
        // Creamos un array de tipo byte con el tamaÃ±o del archivo
        byte[] buffer = new byte[ tamArchivo ];
        // Leemos el archivo y lo introducimos en el array de bytes
        bis.read( buffer );
        // Realizamos el envio de los bytes que conforman el archivo
        for( int i = 0; i < buffer.length; i++ ){
            bos.write( buffer[ i ] );
        }
        System.out.println( "Archivo Enviado: "+archivo.getName() );
        // Cerramos socket y flujos
        bis.close();
        bos.close();
        socket.close();
    }

    public static String menu() throws IOException {
        boolean correcto = false;
        String opcion="";
        while (!correcto){
            System.out.println("Elige una opción (a/b): \na.- Enviar \nb.- Recibir");
            opcion = br.readLine();
            if (opcion.equalsIgnoreCase("a") || opcion.equalsIgnoreCase("b" )){
                correcto = true;
            }else{
                System.out.println("Opción incorrecta. Vuelve a intentarlo");
                correcto = false;
            }
        }
        return opcion;
    }

    public static void enviarOpcion(String opcion) throws IOException {
        DataOutputStream dos = new DataOutputStream( socket.getOutputStream());
        dos.writeUTF(opcion);
        //dos.close();
    }
}
