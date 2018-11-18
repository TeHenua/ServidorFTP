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
    private static OutputStream outputStream;
    private static InputStream inputStream;
    private static BufferedInputStream bis;
    

    public static void main(String args[]) throws IOException {

        direccion = InetAddress.getByName("localhost");
        socket = new Socket( direccion, port );
        socket.setSoTimeout( 2000 );
        socket.setKeepAlive( true );
        outputStream = socket.getOutputStream();
        inputStream = socket.getInputStream();
        dos = new DataOutputStream(outputStream);
        bos = new BufferedOutputStream(outputStream);
        
        String opcion ="";
        while (!opcion.equalsIgnoreCase("c")){
        	 boolean correcto = false;
             while (!correcto){
                 System.out.println("Elige una opci髇 (a/b/c): \na.- Enviar \nb.- Recibir \nc.- Salir");
                 opcion = br.readLine();
                 if (opcion.equalsIgnoreCase("a") || opcion.equalsIgnoreCase("b" ) || opcion.equalsIgnoreCase("c")){
                     correcto = true;
                 }else{
                     System.out.println("Opci髇 incorrecta. Vuelve a intentarlo");
                     correcto = false;
                 }
             }

         	dos = new DataOutputStream(outputStream);
            dos.writeUTF(opcion);
            dos.close();
            System.out.println("Opci髇 enviada: "+opcion);
            
            switch (opcion) {
                case "a":
                	System.out.println("Escribe el nombre del fichero (sin extensi贸n)");
                    String nombre = br.readLine()+".txt";
                    File archivo = new File(ruta+nombre);
                    System.out.println("Conectando...");

                    String nombreArchivo = archivo.getName();
                    int tamanoArchivo = ( int )archivo.length();

                    dos.writeUTF(nombreArchivo);
                    dos.writeInt(tamanoArchivo);
                    
                    FileInputStream fis = new FileInputStream(archivo);
                    bis = new BufferedInputStream(fis);
                    System.out.println("Enviando archivo "+nombreArchivo);
                    byte[] buffer = new byte[tamanoArchivo];
                    bis.read(buffer);
                    for(int i = 0; i < buffer.length; i++){
                        System.out.println("vuelta enviar "+i);
                        bos.write(buffer[i]);
                    }

                    System.out.println("Archivo enviado.");
                    break;
                case "b":
                	dis = new DataInputStream(inputStream);
                    ArrayList<String> lista = Recibir.recibirListado(dis);
                    dis.close();
                    System.out.println("\nArchivos disponibles en el servidor:");
                    for (int i = 0; i < lista.size(); i++) {
                        System.out.println("- "+lista.get(i));
                    }
                    System.out.println("Escribe el nombre del archivo que quieres descargar (sin extensi贸n)");
                    String nombreArch =  br.readLine();
                    break;
                case "c":
                    System.out.println("Cerrando conexi贸n...");
                    socket.close();
                    break;
                
            }
        }

    }

    private static void enviarFichero() throws IOException {
        
    }

    public static String menu() throws IOException {
        boolean correcto = false;
        String opcion="";
        while (!correcto){
            System.out.println("Elige una opci贸n (a/b/c): \na.- Enviar \nb.- Recibir \nc.- Salir");
            opcion = br.readLine();
            if (opcion.equalsIgnoreCase("a") || opcion.equalsIgnoreCase("b" ) || opcion.equalsIgnoreCase("c")){
                correcto = true;
            }else{
                System.out.println("Opci贸n incorrecta. Vuelve a intentarlo");
                correcto = false;
            }
        }
        return opcion;
    }

    public static void enviarOpcion(String opcion) throws IOException {
    	dos = new DataOutputStream(outputStream);
        dos.writeUTF(opcion);
        dos.close();
        System.out.println("Opci贸n enviada: "+opcion);
    }

    public static String seleccionarArchivo() throws IOException {
        dis = new DataInputStream(inputStream);
        ArrayList<String> lista = Recibir.recibirListado(dis);
        dis.close();
        System.out.println("\nArchivos disponibles en el servidor:");
        for (int i = 0; i < lista.size(); i++) {
            System.out.println("- "+lista.get(i));
        }
        System.out.println("Escribe el nombre del archivo que quieres descargar (sin extensi贸n)");
        return br.readLine();
    }
}
