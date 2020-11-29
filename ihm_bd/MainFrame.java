package ci.inphb.ihm_bd;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.UnknownHostException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JFrame;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;

public class MainFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	private javax.swing.JButton displayDataBase, saveData, send, exit, view, back;
	private javax.swing.JPanel registPanel, viewPanel, comboPanel;
	private javax.swing.ButtonGroup bg;
	private FileCsv csvClass;
	private java.util.ArrayList<String> myArray;
	private InfoDialogBox registerDialog;
	
	private Boolean rightLocation;
	private String dataBaseName, tableName;
	private buttonListener listener;
	private final int PORT_NUMBER = 8060;
	
	private String user = "sam",pw = "sam00";
	
	private String connectionString = "jdbc:mysql://localhost/";
	
	public  MainFrame(String title) {
		
		this.setTitle(title);
		//System.out.println(SEPARATOR);
		csvClass = null;
		myArray = new java.util.ArrayList<>();
		saveData = new javax.swing.JButton("Enregistrer");
		displayDataBase = new javax.swing.JButton("Consulter");
		initFrameContent();
	}
	
	private void initFrameContent(){		
		
		//registPanel.setLayout(mgr);
		registerDialog = new InfoDialogBox(this, "Enregistrer Etudiant", true);
		listener = new buttonListener();
		javax.swing.JPanel topPan = new javax.swing.JPanel();
		topPan.add(saveData);topPan.add(displayDataBase);
		
		saveData.addActionListener(listener);
		saveData.setActionCommand("save");
		
		displayDataBase.addActionListener(listener);
		displayDataBase.setActionCommand("view");
		
		this.setLayout(new javax.swing.BoxLayout(this.getContentPane(), javax.swing.BoxLayout.Y_AXIS));
		this.add(topPan);//this.getContentPane().add(displayDataBase);
		
		viewField();
		registerField();
	}

	
	private void registerField() {
		/*choose file
		 * we ask user whatever new or old file
		 * */
		registPanel = new javax.swing.JPanel();
		registPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Crée un fichier/utiliser un ancien ?"));

		send = new javax.swing.JButton("Valider");send.setActionCommand("send");
		send.addActionListener(listener);
		exit = new javax.swing.JButton("Quitter");exit.setActionCommand("exit");
		exit.addActionListener(listener);
		
		
		javax.swing.JRadioButton newFile = new javax.swing.JRadioButton("nouveau fichier");
		newFile.setBackground(java.awt.Color.WHITE);newFile.setSelected(true);
		newFile.setActionCommand("new");
		
		javax.swing.JRadioButton oldFile = new javax.swing.JRadioButton("Ancien fichier");
		oldFile.setBackground(java.awt.Color.WHITE);
		oldFile.setActionCommand("old");
		oldFile.setSelected(true);
		
		bg = new javax.swing.ButtonGroup();		
		bg.add(newFile);
		bg.add(oldFile);
		
		javax.swing.JPanel choicePanel =  new javax.swing.JPanel();choicePanel.setOpaque(false);
		choicePanel.setAlignmentX(javax.swing.JComponent.CENTER_ALIGNMENT);
		choicePanel.add(newFile); choicePanel.add(oldFile);
		
		javax.swing.JPanel buttonPanel =  new javax.swing.JPanel();buttonPanel.setOpaque(false);
		buttonPanel.setAlignmentX(javax.swing.JComponent.CENTER_ALIGNMENT);
		buttonPanel.add(send); buttonPanel.add(exit);
		
		//registPanel.setLayout(new java.awt.FlowLayout());//new BoxLayout(panel, BoxLayout.Y_AXIS
		registPanel.setLayout(new javax.swing.BoxLayout(registPanel, javax.swing.BoxLayout.Y_AXIS));
		registPanel.add(choicePanel);
		registPanel.add(javax.swing.Box.createVerticalStrut(5));
		registPanel.add(buttonPanel);
		//
		this.add(javax.swing.Box.createVerticalStrut(8));
	}

	
	private void viewField() {
		/*view field*/
		viewPanel = new javax.swing.JPanel();
		viewPanel.setBorder(
				javax.swing.BorderFactory.createTitledBorder("reference my sql"));
		

		view = new javax.swing.JButton("consulter");
		view.setActionCommand("view");
		
		back = new javax.swing.JButton("revenir");
		back.setActionCommand("back");
		
		javax.swing.JTextField inputField = 
				new javax.swing.JTextField();
		inputField.setBackground(new java.awt.Color(238,236,236));
		inputField.setPreferredSize(new java.awt.Dimension(140, 30));
		javax.swing.JLabel lab = new javax.swing.JLabel("Nom base de donnée");
		lab.setFont(new java.awt.Font("Posterama",java.awt.Font.BOLD,12));
		lab.setHorizontalAlignment(javax.swing.JLabel.LEFT);
		lab.setPreferredSize(new java.awt.Dimension(85,40));
		
		
		javax.swing.JTextField inputField1 = 
				new javax.swing.JTextField();
		inputField1.setBackground(new java.awt.Color(238,236,236));
		inputField1.setPreferredSize(new java.awt.Dimension(140, 30));
		javax.swing.JLabel lab1 = new javax.swing.JLabel("Nom table");
		lab1.setFont(new java.awt.Font("Posterama",java.awt.Font.BOLD,12));
		lab1.setHorizontalAlignment(javax.swing.JLabel.LEFT);
		lab1.setPreferredSize(new java.awt.Dimension(85,40));
		
		
		viewPanel.setLayout(new java.awt.FlowLayout());
		javax.swing.JPanel pan1 = new javax.swing.JPanel();pan1.setLayout(new java.awt.FlowLayout());
		pan1.add(lab);pan1.add(inputField);
		
		javax.swing.JPanel pan3 = new javax.swing.JPanel();pan3.setLayout(new java.awt.FlowLayout());
		pan3.add(lab1);pan3.add(inputField1);
		
		javax.swing.JPanel pan2 = new javax.swing.JPanel();
		pan2.add(back);pan2.add(view);
		viewPanel.setLayout(new javax.swing.BoxLayout(viewPanel, javax.swing.BoxLayout.Y_AXIS));
		viewPanel.add(pan1);viewPanel.add(pan3);viewPanel.add(pan2);
		
		back.addActionListener(new java.awt.event.ActionListener(){
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				
				MainFrame.this.remove(viewPanel);
				saveData.setEnabled(true);displayDataBase.setEnabled(true);
				//displayDataBase.setEnabled(true);
				MainFrame.this.pack();
			}
			
		});
		view.addActionListener(new java.awt.event.ActionListener(){
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				
				if(rightLocation){
					
					dataBaseName = inputField.getText();
					tableName = inputField1.getText();
					MainFrame.this.remove(viewPanel);
					comboView();
					displayDataBase.setEnabled(true);
					MainFrame.this.pack();
					//System.out.println(rightLocation);
				}
			}
			
		});
		/*
		inputField.addFocusListener(new java.awt.event.FocusListener(){

			@Override
			public void focusGained(FocusEvent e) {
				setJTextMessage(inputField, serverLocation, false);
				
			}

			@Override
			public void focusLost(FocusEvent e) {
				serverLocation = inputField.getText();
				if(!inputField.getText().matches("(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])")){

					setJTextMessage(inputField, "*(Ex: 10.182.2.254)", true);
					rightLocation = false;
				}
				else rightLocation = true;
				
			}
			
		});
		 */
	}
	@SuppressWarnings("unused")
	private void setJTextMessage(javax.swing.JTextField field, String message, Boolean small){
		field.setFont(new java.awt.Font(small?"roboto":"Comfortaa",small?java.awt.Font.ITALIC:java.awt.Font.PLAIN,small?13:14));
		field.setText(message);
		//not small "Comfortaa",java.awt.Font.BOLD,16
	}
	
	private String fileDirectChooser(Boolean isFile){
		
		javax.swing.JFileChooser jfc = new javax.swing.JFileChooser(
				javax.swing.filechooser.FileSystemView.getFileSystemView().getHomeDirectory());
		
		jfc.setDialogTitle(isFile?"Sélection du fichier Excel":"choissiez le repertoire d'enregistrement");
		jfc.setAcceptAllFileFilterUsed(false);
		jfc.setFileSelectionMode(isFile?javax.swing.JFileChooser.FILES_ONLY:javax.swing.JFileChooser.DIRECTORIES_ONLY);
		javax.swing.filechooser.FileNameExtensionFilter filter = 
				new javax.swing.filechooser.FileNameExtensionFilter("*.csv", "csv");
		jfc.addChoosableFileFilter(filter);
		
		int returnValue =  isFile?jfc.showOpenDialog(null):jfc.showSaveDialog(null);//jfc.showDialog(null, "validé")
		if (returnValue == javax.swing.JFileChooser.APPROVE_OPTION) {
			return jfc.getSelectedFile().getPath();
		}
		return "";
	}
	
	private void reSetCsvClass(String path){
		try {
			csvClass = new FileCsv(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void comboView() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					serverSendData();
				} catch (ClassNotFoundException | IOException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}).start();
		try {
			clientGet();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		javax.swing.JPanel panel = new javax.swing.JPanel();
		javax.swing.JButton back = new javax.swing.JButton("revenir");
		panel.setBorder(
				javax.swing.BorderFactory.createTitledBorder("liste des etudiants"));
		javax.swing.JComboBox<String> combo = new javax.swing.JComboBox<>();
		//javax.swing.JLabel label = new javax.swing.JLabel("Etudiant");
		comboPanel = new javax.swing.JPanel();
		
		for (int i = 0; i < myArray.size()/3; i++) {
			combo.addItem(myArray.get(i+1)+" "+myArray.get(i+1)+" "+myArray.get(i));
		}
		panel.setLayout(new java.awt.FlowLayout());
		panel.add(back);panel.add(combo);
		comboPanel.add(panel);
		MainFrame.this.add(comboPanel);
		back.addActionListener(new java.awt.event.ActionListener() {
			
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				
				MainFrame.this.remove(comboPanel);
				saveData.setEnabled(true);displayDataBase.setEnabled(true);
				MainFrame.this.pack();
			}
		});
	}
	
	private class buttonListener implements java.awt.event.ActionListener{

		@Override
		public void actionPerformed(java.awt.event.ActionEvent e) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					
					switch (e.getActionCommand()) {
					
					case "save":
						
						//crée un nouveau fichier 
						MainFrame.this.remove(viewPanel);
						//MainFrame.this.add(registPanel);
						//MainFrame.this.getContentPane().revalidate();
						registerDialog.setVisible(true);
						if(!myArray.isEmpty()){
							
							if(registerDialog.getRadioSelected().equals("local")){
								
							}
							MainFrame.this.remove(registPanel);
							store(registerDialog.getRadioSelected());
							}
						
						break;
						
					case "view":
						MainFrame.this.remove(registPanel);
						MainFrame.this.add(viewPanel);
						//MainFrame.this.add(viewPanel);
						
						displayDataBase.setEnabled(false);saveData.setEnabled(true);
						break;
						
					case "send":
						//if new
						Boolean canStore = false;
						if(bg.getSelection().getActionCommand().equals("new")){
							
							String fileName = javax.swing.JOptionPane.showInputDialog(MainFrame.this, "Entrez le nom du fichier :");
							fileName+="";
							
							if(!fileName.equals("null")){
								
								String directoryName = fileDirectChooser(false);
								fileName = directoryName+"/"+fileName+".csv";
								
								if(!directoryName.equals("")) {reSetCsvClass(fileName); canStore = true; }
							}
							
							
						} else{//if it is old then we ask file
							String file = fileDirectChooser(true);
							if(!file.equals("")){
								//System.out.println("none");
								reSetCsvClass(file);canStore = true;
								}
						}
						
						MainFrame.this.remove(registPanel);//MainFrame.this.pack();
						saveData.setEnabled(false);saveData.setEnabled(true);displayDataBase.setEnabled(true);
						if(canStore) {
							
							csvClass.write(myArray);//
							myArray.clear();
						}
						//System.out.println(myArray);
						break;
						
					case "exit":
						MainFrame.this.remove(registPanel);
//						showRegisterDailog(true);
//						//if array is set 
//						if(!myArray.isEmpty()){
//							/*sauvegarder a distance methode*/
//							myArray.clear();//wonder if it necessary
//						}
						saveData.setEnabled(true);displayDataBase.setEnabled(true);
						break;
					}
					MainFrame.this.pack();

				}
			}).start();	
		}
		
	}
	
	
	public void store(String storeType){
		
		if(storeType.equals("local")){
			
			saveData.setEnabled(false);displayDataBase.setEnabled(false);
			this.add(registPanel);this.pack();
		}
		
		else{
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						serverSetData();
					} catch (ClassNotFoundException | IOException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}).start();
			try {
				clientSet();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	 void setArrayElements(String matricule, String name,String firstName){
		//myArray = new java.util.ArrayList<>();
		//myArray.clear();
		myArray.add(matricule); myArray.add(name);myArray.add(firstName);
	
	}
	
	/*getters and setters*/
	public java.util.ArrayList<String> getMyArray() {
		return myArray;
	}
	
	public void setMyArray(java.util.ArrayList<String> myArray) {
		this.myArray = myArray;
	}
	
	private void serverSetData() throws IOException, ClassNotFoundException, SQLException{
		
		
		Connection connection = (Connection) DriverManager.getConnection(connectionString+dataBaseName, user, pw);
		
		@SuppressWarnings("resource")
		java.net.ServerSocket socketServer = new java.net.ServerSocket(PORT_NUMBER);
        //System.out.println("Socket serveur: " + s);
 
        java.net.Socket socket = socketServer.accept();
 
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
 
        Object objetRecu = in.readObject();
        @SuppressWarnings("unchecked")
		java.util.ArrayList<String> tableauRecu = (java.util.ArrayList<String>) objetRecu;
 
        System.out.println("Serveur recoit: " + tableauRecu);
        
        String request = "INSERT INTO "+tableName+" (matricule, nom, prenom) ";
        PreparedStatement statement  = connection.prepareStatement(request
			+ "VALUES (?,?,?)");
	
		for (int i = 0; i < tableauRecu.size()/3; i++) {
			
			statement.setString(1, tableauRecu.get(i));
			statement.setString(2, tableauRecu.get(i+1));
			statement.setString(3, tableauRecu.get(i+2));
			statement.executeUpdate();
		}
        in.close();
        //out.close();
        socket.close();
}
	
	private void serverSendData() throws IOException, ClassNotFoundException, SQLException{
		
		@SuppressWarnings("resource")
		java.net.ServerSocket socketServer = new java.net.ServerSocket(PORT_NUMBER);
        //System.out.println("Socket serveur: " + s);
 
        java.net.Socket socket = socketServer.accept();
 
        System.out.println("Serveur a accepte connexion: " + socket);
 
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.flush();
 
        //ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
 
 
        System.out.println("Serveur a cree les flux");
 
        java.util.ArrayList<String> tableauAEmettre = new java.util.ArrayList<String>();
 
        Connection connection = (Connection) DriverManager.getConnection(connectionString+dataBaseName, user, pw);
        Statement statement = (Statement) connection.createStatement();
		//statement.execute("");
        ResultSet data = (ResultSet) statement.executeQuery("SELECT * FROM "+tableName);
		
		while (data.next()) {
			tableauAEmettre.add(data.getString("matricule"));
			tableauAEmettre.add(data.getString("nom"));
			tableauAEmettre.add(data.getString("prenom"));
			
		}
        
		out.writeObject(tableauAEmettre);
		out.flush();
        System.out.println("Serveur: donnees emises :" + tableauAEmettre);
 
        //in.close();
        out.close();
        socket.close();

}
	
	private void clientGet() throws UnknownHostException, IOException, ClassNotFoundException{

		java.net.Socket socket = new java.net.Socket("localhost", PORT_NUMBER);
        System.out.println("Socket client: " + socket);
 
        //java.io.ObjectOutputStream out = new java.io.ObjectOutputStream(socket.getOutputStream());
        //out.flush();
 
        java.io.ObjectInputStream in = new java.io.ObjectInputStream(socket.getInputStream());
 
        System.out.println("Client a cree les flux");
 
       // java.util.ArrayList<String> tableauAEmettre = null;
 
//        out.writeObject(tableauAEmettre);
//        out.flush();
 
        System.out.println("Client: donnees emises");
 
        Object objetRecu = in.readObject();
        @SuppressWarnings("unchecked")
		java.util.ArrayList<String> tableauRecu = (java.util.ArrayList<String>) objetRecu;
        //System.out.println("Client recoit: " + tableauRecu);
        myArray = tableauRecu;
 
        in.close();
        //out.close();
        socket.close();
	}
	
	private void clientSet() throws UnknownHostException, IOException, ClassNotFoundException{

		java.net.Socket socket = new java.net.Socket("localhost", PORT_NUMBER);
        System.out.println("Socket client: " + socket);
 
        java.io.ObjectOutputStream out = new java.io.ObjectOutputStream(socket.getOutputStream());
        out.flush();
 
       // java.io.ObjectInputStream in = new java.io.ObjectInputStream(socket.getInputStream());
 
        System.out.println("Client a cree les flux");
 
        //java.util.ArrayList<String> tableauAEmettre = null;
 
        out.writeObject(myArray);
        out.flush();
// 
//        System.out.println("Client: donnees emises");
// 
//        Object objetRecu = in.readObject();
//        int[] tableauRecu = (int[]) objetRecu;
 
       // System.out.println("Client recoit: " + Arrays.toString(tableauRecu));
 
       // in.close();
        out.close();
        socket.close();
	}

	
}
