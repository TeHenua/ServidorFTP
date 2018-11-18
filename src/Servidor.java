import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class Servidor {

    private static ServerSocket servidor = null;
    private static String rutaSt = "ArchivosServidor/";
    private static String carpetaSt = "ArchivosServidor";
    private static Socket socket;
    private static DataInputStream dis; 
    private static DataOutputStream dos; 
    private static BufferedInputStream in;
    private static FileOutputStream fos; 
    private static InputStream inputStream;
    private static OutputStream outputStream;
    private static BufferedOutputStream out;

    public static void main(String args[]) throws IOException {
        servidor = new ServerSocket(21);    
        while (true){
            socket = servidor.accept();
            System.out.println("Cliente "+socket.getInetAddress().getHostName()+" conectado.");
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            dos = new DataOutputStream(outputStream);
            dis= new DataInputStream(inputStream);
            in = new BufferedInputStream(inputStream);
            
            out  = new BufferedOutputStream(fos);   
            String opcion="";
            while (!opcion.equalsIgnoreCase("c")) {
                opcion = dis.readUTF();
                System.out.println("Opción recibida: "+opcion);
                switch (opcion) {
                    case "a":
                    	System.out.println("Conectando...");
                        String nombreArchivo = dis.readUTF().toString();
                        int tam = dis.readInt();
                        fos = new FileOutputStream(new File(rutaSt+nombreArchivo)); 
                        System.out.println("Recibiendo archivo "+nombreArchivo);
                        byte[] buffer = new byte[ tam ];
                        for( int i = 0; i < buffer.length; i++ ){
                            System.out.println("vuelta recibir "+i);
                            buffer[i] = (byte)in.read();
                        }
                        System.out.println("Archivo recibido.");
                        out.write(buffer);
                        break;
                        
                    case "b":
                    	System.out.println("Conectando...");
                    	File carpeta = new File(carpetaSt);
                        dos.writeInt(carpeta.listFiles().length);
                        for (File archivo : carpeta.listFiles()){
                            dos.writeUTF(archivo.getName());
                        }
                        System.out.println("Listado enviado.");
                        //String nombre = Recibir.recibirNombre(socket);
                        //Enviar.enviarArchivo();enviarFichero(socket,nombre);
                        break;
                        
                    case "c":
                        socket.close();
                        System.out.println("ConexiÃ³n cliente " + socket.getInetAddress().getHostName() + " cerrada");
                        break;
                }
            }
        }
    }

}
