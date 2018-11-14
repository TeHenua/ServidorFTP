import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Enviar {


    public static void enviarArchivo(Socket socket,File archivo ) throws IOException {
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        System.out.println("Conectando...");

        String nombreArchivo = archivo.getName();
        int tamanoArchivo = ( int )archivo.length();

        dos.writeUTF(nombreArchivo);
        dos.writeInt(tamanoArchivo);

        FileInputStream fis = new FileInputStream(archivo);
        BufferedInputStream bis = new BufferedInputStream( fis );
        BufferedOutputStream bos = new BufferedOutputStream( socket.getOutputStream());

        System.out.println("Enviando archivo "+nombreArchivo);
        byte[] buffer = new byte[tamanoArchivo];
        bis.read(buffer);
        for(int i = 0; i < buffer.length; i++){
            System.out.println("vuelta enviar "+i);
            bos.write(buffer[i]);
        }

        System.out.println("Archivo enviado.");
        bis.close();
        bos.close();
    }

    public static void enviarListado(Socket socket, File carpeta) throws IOException {
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        System.out.println("Conectando...");

        dos.writeInt(carpeta.listFiles().length);
        for (File archivo : carpeta.listFiles()){
            dos.writeUTF(archivo.getName());
        }
        System.out.println("Listado enviado.");
    }
}
