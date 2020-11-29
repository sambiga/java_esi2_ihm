package ci.inphb.ihm_bd;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;

public class Test {
	
	public static void main(String[] args) {
			
			//serveur
//			Test t = new Test();
//			new Thread(new Runnable() {
//				
//				@Override
//				public void run() {
//					// TODO Auto-generated method stub
//					try {
//						t.server();
//						System.out.println("server");
//						
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//					
//				}
//			}).start();
//			//client
//			try {
//				t.client();
//				System.out.println("client");
//				//t.socketClient.close();t.server.close();t.socketServer.close();
//				
//			} catch (Exception e) {
//				// TODO: handle exception
//			}
			
	}
	private void server() throws IOException, ClassNotFoundException{
		
			ServerSocket s = new ServerSocket(9000);
	        System.out.println("Socket serveur: " + s);
	 
	        Socket soc = s.accept();
	 
	        System.out.println("Serveur a accepte connexion: " + soc);
	 
	        ObjectOutputStream out = new ObjectOutputStream(soc.getOutputStream());
	        out.flush();
	 
	        ObjectInputStream in = new ObjectInputStream(soc.getInputStream());
	 
	 
	        System.out.println("Serveur a cree les flux");
	 
	        int[] tableauAEmettre = {7, 8, 9};
	 
	        out.writeObject(tableauAEmettre);
	        out.flush();
	 
	        System.out.println("Serveur: donnees emises");
	 
	        Object objetRecu = in.readObject();
	        int[] tableauRecu = (int[]) objetRecu;
	 
	        System.out.println("Serveur recoit: " + Arrays.toString(tableauRecu));
	 
	        in.close();
	        out.close();
	        soc.close();
	}
	private void client() throws UnknownHostException, IOException, ClassNotFoundException{

		Socket socket = new Socket("localhost", 9000);
        System.out.println("Socket client: " + socket);
 
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.flush();
 
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
 
        System.out.println("Client a cree les flux");
 
        int[] tableauAEmettre = {1, 2, 3};
 
        out.writeObject(tableauAEmettre);
        out.flush();
 
        System.out.println("Client: donnees emises");
 
        Object objetRecu = in.readObject();
        int[] tableauRecu = (int[]) objetRecu;
 
        System.out.println("Client recoit: " + Arrays.toString(tableauRecu));
 
        in.close();
        out.close();
        socket.close();
	}
}