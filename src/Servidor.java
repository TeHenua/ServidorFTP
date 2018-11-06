import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class Servidor {

    private static ServerSocket servidor = null;

    public static void main(String args[]) throws IOException {
        servidor = new ServerSocket(21);
        while (true){
            Socket cliente = servidor.accept();
            DataInputStream dataOpcion = new DataInputStream( cliente.getInputStream() );
            String opcion = dataOpcion.readUTF();
            if (opcion.equalsIgnoreCase("a")){  //Enviar desde el cliente
                recibirFichero(cliente);
            }else{                                          //Recibir desde el cliente
                enviarListado(cliente);
                enviarFichero(cliente);
            }
        }
    }



    private static void enviarListado(Socket cliente) {

    }

    private static void enviarFichero(Socket cliente) {

    }

    public static void recibirFichero(Socket cliente) throws IOException {
        DataInputStream dis = new DataInputStream( cliente.getInputStream() );
        // Obtenemos el nombre del archivo
        String nombreArchivo = dis.readUTF().toString();
        //Obtenemos el tamaÃ±o del archivo
        int tam = dis.readInt();
        System.out.println( "Recibiendo archivo "+nombreArchivo);
        // Creamos flujo de salida, este flujo nos sirve para
        // indicar donde guardaremos el archivo
        FileOutputStream fos = new FileOutputStream("Archivos//"+nombreArchivo);
        //Ruta donde se va a guardar el archivo
        BufferedOutputStream out = new BufferedOutputStream(fos);
        BufferedInputStream in = new BufferedInputStream( cliente.getInputStream() );
        // Creamos el array de bytes para leer los datos del archivo
        byte[] buffer = new byte[ tam ];
        // Obtenemos el archivo mediante la lectura de bytes enviados
        for( int i = 0; i < buffer.length; i++ ){
            buffer[i] = (byte)in.read();
        }
        // Escribimos el archivo
        out.write(buffer);
        // Cerramos flujos
        out.flush();
        in.close();
        out.close();
        System.out.println( "Archivo Recibido "+nombreArchivo);
    }
}
