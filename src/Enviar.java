import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Enviar {


    public static void enviarArchivo(DataOutputStream dos, BufferedOutputStream bos,File archivo ) throws IOException {
        
        
    }

    public static void enviarListado(DataOutputStream dos, File carpeta) throws IOException {
        System.out.println("Conectando...");

        dos.writeInt(carpeta.listFiles().length);
        for (File archivo : carpeta.listFiles()){
            dos.writeUTF(archivo.getName());
        }
        System.out.println("Listado enviado.");
    }
}
