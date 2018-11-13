import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Recibir {


    public static void recibirArchivo(Socket socket,String ruta) throws IOException {
        DataInputStream dis = new DataInputStream( socket.getInputStream() );
        System.out.println("Conectando...");

        String nombreArchivo = dis.readUTF().toString();
        int tam = dis.readInt();

        FileOutputStream fos = new FileOutputStream(new File(ruta+nombreArchivo));
        BufferedOutputStream out = new BufferedOutputStream(fos);
        BufferedInputStream in = new BufferedInputStream( socket.getInputStream() );

        System.out.println("Recibiendo archivo "+nombreArchivo);

        byte[] buffer = new byte[ tam ];
        for( int i = 0; i < buffer.length; i++ ){
            buffer[i] = (byte)in.read();
        }

        System.out.println("Archivo recibido.");
        out.write(buffer);
        // Cerramos flujos
        out.flush();
        in.close();
        out.close();
    }

    public static ArrayList recibirListado(Socket socket) throws IOException {
        ArrayList<String> lista = new ArrayList<>();
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        System.out.println("Conectando...");
        int numero = dis.readInt();
        for (int i = 0; i < numero; i++) {
            lista.add(dis.readUTF());
        }
        System.out.println("Listado recibido.");
        return lista;
    }
}
