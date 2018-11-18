package clases;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class Cliente {

    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private static Socket socket;
	private static BufferedOutputStream bos;
	private static BufferedInputStream bis;
	private static DataOutputStream dos;
	private static DataInputStream dis;
	private static String ruta = "ArchivosCliente/";
	
	public static void main(String[] args) throws IOException {
		socket = new Socket(InetAddress.getLocalHost(),21);
		bos = new BufferedOutputStream(socket.getOutputStream());
		bis = new BufferedInputStream(socket.getInputStream());
		dos = new DataOutputStream(socket.getOutputStream());
		dis = new DataInputStream(socket.getInputStream());
		
		String opcion ="";
        while (!opcion.equalsIgnoreCase("c")){
        	boolean correcto = false;
            while (!correcto){
                System.out.println("Elige una opción (a/b/c): \na.- Enviar \nb.- Recibir \nc.- Salir");
                opcion = br.readLine();
                if (opcion.equalsIgnoreCase("a") || opcion.equalsIgnoreCase("b" ) || opcion.equalsIgnoreCase("c")){
                    correcto = true;
             
                    dos.writeUTF(opcion);
                    System.out.println("Opción enviada: "+opcion);
                    
                    switch (opcion) {
                    	case "a":{
                    		System.out.println("Escribe el nombre del fichero (sin extensiÃ³n)");
                            String nombre = br.readLine()+".txt";
                            File archivo = new File(ruta+nombre);
                            System.out.println("Conectando...");
                            
                            String nombreArchivo = archivo.getName();
                            int tamanoArchivo = ( int )archivo.length();
                            //enviar nombre y tamaño
                            dos.writeUTF(nombreArchivo);
                            dos.writeInt(tamanoArchivo);
                            //leer archivo
                            bis = new BufferedInputStream(new FileInputStream(archivo));
                            System.out.println("Enviando archivo "+nombreArchivo);
                            byte[] buffer = new byte[tamanoArchivo];
                            bis.read(buffer);
                            for(int i = 0; i < buffer.length; i++){
                                System.out.println("vuelta enviar "+i);
                                //enviar archivo
                                bos.write(buffer[i]);
                            }

                            System.out.println("Archivo enviado.");
                    	}
                    		break;
                    	case "b":{
                    		ArrayList<String> lista = new ArrayList<>();
                            
                            System.out.println("Conectando...");
                            int numero = dis.readInt();
                            for (int i = 0; i < numero; i++) {
                                lista.add(dis.readUTF());
                            }
                            System.out.println("Listado recibido.");
                            System.out.println("\nArchivos disponibles en el servidor:");
                            for (int i = 0; i < lista.size(); i++) {
                                System.out.println("- "+lista.get(i));
                            }
                            System.out.println("Escribe el nombre del archivo que quieres descargar (sin extensiÃ³n)");
                            String nombreArch =  br.readLine();
                    	}
                    	break;
                    }
                }else{
                    System.out.println("Opción incorrecta. Vuelve a intentarlo");
                    correcto = false;
                }
            }
        }
	}
}
