package clases;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
	
	private static ServerSocket servidor = null;
	private static Socket socket;
	private static DataOutputStream dos;
	private static DataInputStream dis;
	private static DataInputStream disOp;
	private static BufferedInputStream bis;
	private static BufferedOutputStream out;
	private static FileOutputStream fos; 
	private static String ruta= "ArchivosServidor/";

	public static void main(String[] args) throws IOException {
		System.out.println("Servidor iniciado");
		servidor = new ServerSocket(21);
		while(true) {
			socket = servidor.accept();
			System.out.println("Cliente "+socket.getInetAddress().getHostName()+" conectado.");
			
			dos = new DataOutputStream(socket.getOutputStream());
			dis = new DataInputStream(socket.getInputStream());
			disOp = new DataInputStream(socket.getInputStream());
			
			String opcion="";
            while (!opcion.equalsIgnoreCase("c")) {
            	opcion = disOp.readUTF();
            	System.out.println("Opción recibida: "+opcion);
                switch (opcion) {
                	case "a":{
                		System.out.println("Conectando...");
                		//recibir nombre y tamaño
                        String nombreArchivo = dis.readUTF().toString();
                        int tam = dis.readInt(); 
                        out = new BufferedOutputStream(new FileOutputStream(ruta+nombreArchivo));
                        bis = new BufferedInputStream(socket.getInputStream());
                        System.out.println("Recibiendo archivo "+nombreArchivo);
                        byte[] buffer = new byte[tam];
                        for( int i = 0; i < buffer.length; i++ ){
                            System.out.println("vuelta recibir "+i);
                            //recibir archivo
                            buffer[i] = (byte)bis.read();
                        }
                        System.out.println("Archivo recibido.");
                        out.write(buffer);
                        break;
                	}
                	case "b":{
                		System.out.println("Conectando...");
                    	File carpeta = new File(ruta);
                        dos.writeInt(carpeta.listFiles().length);
                        for (File archivo : carpeta.listFiles()){
                            dos.writeUTF(archivo.getName());
                        }
                        System.out.println("Listado enviado.");
                	
                		break;
                	}
                	case "c":{
                		
                	
                		break;
                	}
                		
                }
            }
		}

	}

}
