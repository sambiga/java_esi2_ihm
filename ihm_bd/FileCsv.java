package ci.inphb.ihm_bd;

import java.io.IOException;

public class FileCsv {
	
	java.nio.file.Path path;
	java.util.List<String> lines;
	public FileCsv(String url) throws IOException{
		
		path = java.nio.file.Paths.get(url);
		
		if(!java.nio.file.Files.exists(path)){
			makeFile("matricule,nom,prenom%n");
		}
		else{
			
		}
		
	}
	public void write(java.util.ArrayList<String> list){
		
		String matricule, name, firstName;
		
		for (int i = 0; i < list.size()/3; i++) {
			matricule = list.get(i);
			name = list.get(i+1);
			firstName = list.get(i+2);
			write(matricule, name, firstName);
		}
	}
	public void write(String mat, String nom, String prenom){
			
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(!elementExist(mat)){			
					try {
						java.nio.file.Files.write(path, String.format(mat+","+nom+","+prenom+"%n").getBytes(), java.nio.file.StandardOpenOption.APPEND);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				//JOptionPan
				else javax.swing.JOptionPane.showMessageDialog(null, "Ce matricule existe dans le fichier : "+path.getFileName(),"Erreur", javax.swing.JOptionPane.ERROR_MESSAGE);;
				
			}
			});
		t.start();
		}
	
	public void makeFile(String name) throws IOException{
		
			java.nio.file.Files.createFile(path);
			java.nio.file.Files.write(path, String.format(name).getBytes());
	}

	public void read(){
		
		try {
			
			lines = java.nio.file.Files.readAllLines(path);
			
		} catch (IOException e) {
			
			System.out.println("Impossible de lire le fichier des commandes");
			
		}

	    if (lines.size() < 2) {

	        System.out.println("Il n'y a pas de commande dans le fichier");

	        return;

	    }


	    for (int i = 1; i < lines.size(); i++) {

	        String[] split = lines.get(i).split(",");

	        String matValue = split[0];//matricule

	        String nameValue = split[1];//nom

	        String fistNameValue = split[2];//prenom
	        
	        System.out.println(matValue+" "+nameValue+" "+fistNameValue);

	    }
	    
	}
	
	private Boolean elementExist(String matricule){
		
		try {
			
			lines = java.nio.file.Files.readAllLines(path);
			
		} catch (IOException e) {
			
			System.out.println("Impossible de lire le fichier des commandes");
		}
		 for (int i = 1; i < lines.size(); i++) {

		        String mat = lines.get(i).split(",")[0];
		        if(mat.equals(matricule)){
		        	return true;
		        }
		    }
		
		return false;
		
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.path.toString();
	}


}
