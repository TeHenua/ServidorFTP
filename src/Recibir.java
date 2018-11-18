import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Recibir {


    public static void recibirArchivo(DataInputStream dis,BufferedInputStream in, String ruta) throws IOException {
        
        System.out.println("Conectando...");

        String nombreArchivo = dis.readUTF().toString();
        int tam = dis.readInt();

        FileOutputStream fos = new FileOutputStream(new File(ruta+nombreArchivo));
        BufferedOutputStream out = new BufferedOutputStream(fos);
        

        System.out.println("Recibiendo archivo "+nombreArchivo);

        byte[] buffer = new byte[ tam ];
        for( int i = 0; i < buffer.length; i++ ){
            System.out.println("vuelta recibir "+i);
            buffer[i] = (byte)in.read();
        }

        System.out.println("Archivo recibido.");
        out.write(buffer);
        // Cerramos flujos
        out.flush();
        in.close();
        out.close();
    }

    public static ArrayList<String> recibirListado(DataInputStream dis) throws IOException {
        ArrayList<String> lista = new ArrayList<>();
        
        System.out.println("Conectando...");
        int numero = dis.readInt();
        for (int i = 0; i < numero; i++) {
            lista.add(dis.readUTF());
        }
        System.out.println("Listado recibido.");
        return lista;
    }
}
