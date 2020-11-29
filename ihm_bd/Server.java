package ci.inphb.ihm_bd;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	private final int PORT_NUMBER = 8060;
	public Server() throws IOException {
	
		ServerSocket service = new ServerSocket(1234);
		System.out.println("Service (1234) en fonction ...");
		while (true) {
			System.out.println("Attente d'un nouveau client ...");
			Socket client = service.accept(); // attend la connexion d'un client
			new Thread(new Connexion(client)).start();
		}
	}
		// TODO Auto-generated constructor stub
		

		class Connexion implements Runnable {
		   private Socket client;
		   private final double TAUX = 6.55957;
		   private double €uro, franc;

		   public Connexion(Socket client) { this.client = client; }

		   public void run() {
		      String adresse = client.getInetAddress().getHostAddress();
		       try {
		         DataInputStream requête = new DataInputStream(client.getInputStream());
		         DataOutputStream réponse = new DataOutputStream(client.getOutputStream());
		         System.out.println("Client ("+adresse+") connecté ...");
		         while (true) {
		            String demande = requête.readUTF();
		            if (demande.equals("EuroFranc")) {
		               €uro = requête.readDouble();
		               franc = €uro * TAUX;
		               réponse.writeDouble(franc);
		            }
		            if (demande.equals("FrancEuro")) {
		               franc = requête.readDouble();
		               €uro = franc / TAUX;
		               réponse.writeDouble(€uro);
		            }
		         }
		      } 
		      catch (Exception ex) { System.err.println("Fin de la connexion avec le client ("+adresse+")");    }
		   }   

}
}
		
