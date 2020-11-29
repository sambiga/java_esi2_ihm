package ci.inphb.ihm_bd;

import java.awt.Component;


public class InfoDialogBox extends javax.swing.JDialog implements java.awt.event.FocusListener{
	private static final long serialVersionUID = 1L;
	
	private javax.swing.JTextField matJTexField;
	private javax.swing.JTextField nomJTexField;
	private javax.swing.JTextField preJTexField;
	private javax.swing.JLabel statusLabel;
	
	private Boolean matReset, nomReset, preReset, matState, namState, fistState;
	private javax.swing.JRadioButton local, distant;
	private javax.swing.ButtonGroup bg;
	private final String SEPARATOR = System.getProperty("file.separator");
	private final String REGISTRATION = "."+SEPARATOR+"resources"+SEPARATOR+"working.gif", REGISTED = "."+SEPARATOR+"resources"+SEPARATOR+"green.png";
			
	public InfoDialogBox(javax.swing.JFrame parentFrame,String title, Boolean modal){
		
		super(parentFrame, title, modal);
		this.setSize(400, 320);
		//this.setResizable(false);
		this.setLocationRelativeTo(parentFrame);
		//this.pack();
		this.setDefaultCloseOperation(javax.swing.JDialog.DO_NOTHING_ON_CLOSE);
		this.getContentPane().setBackground(java.awt.Color.WHITE);
		init(parentFrame);
		//this.setVisible(true);
		
	}
	
	private void init(javax.swing.JFrame parentFrame){
		
		matReset = true; nomReset = true; preReset = true;
		
		matJTexField = new javax.swing.JTextField();
		nomJTexField = new javax.swing.JTextField();
		preJTexField = new javax.swing.JTextField();
		
		//adding focus listener
		matJTexField.addFocusListener(this);
		nomJTexField.addFocusListener(this);
		preJTexField.addFocusListener(this);
		
		/*configuration of store choice*/
		bg = new javax.swing.ButtonGroup();
		local = new javax.swing.JRadioButton("local");local.setBackground(java.awt.Color.WHITE);local.setSelected(true);
		local.setActionCommand("local");
		distant = new javax.swing.JRadioButton("distance");distant.setBackground(java.awt.Color.WHITE);
		distant.setActionCommand("distant");
		bg.add(local);
		bg.add(distant);
	
		javax.swing.JPanel panStore = new javax.swing.JPanel();
		panStore.setBorder(javax.swing.BorderFactory.createTitledBorder("Sauvegarde"));
		
		panStore.setOpaque(false);
		panStore.add(local);panStore.add(distant);
		//panStore.setBackground(java.awt.Color.white);
		
		
		/*configuration buttons*/
		javax.swing.JButton continusButton = new javax.swing.JButton("Continuer");
		javax.swing.JButton dropButton = new javax.swing.JButton("Quitter");
		
		
		//adding components to south panel  
		javax.swing.JPanel panSouth = new javax.swing.JPanel();panSouth.setOpaque(false);
		//panSouth.setAlignmentX(javax.swing.JComponent.LEFT_ALIGNMENT);
		panSouth.add(continusButton);panSouth.add(dropButton);panSouth.add(panStore);
		
		continusButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				if (matState&&namState&&fistState){
					
					//setInfo();
					((MainFrame) parentFrame).setArrayElements(matJTexField.getText(), nomJTexField.getText(), preJTexField.getText());
					
					statusLabel.setIcon(new javax.swing.ImageIcon(REGISTED));
				}
			}
		});
		dropButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				setVisible(false);
			}
		});
		this.setLayout(new java.awt.FlowLayout());
		this.add(creatField("Matricule :", matJTexField));
		
		this.add(javax.swing.Box.createVerticalStrut(15));
		this.add(creatField("Nom :", nomJTexField));
		
		this.add(javax.swing.Box.createVerticalStrut(15));
		this.add(creatField("Prenom :", preJTexField));
		
		this.add(javax.swing.Box.createVerticalStrut(15));
		this.add(panSouth);
		
		statusLabel = new javax.swing.JLabel();statusLabel.setOpaque(true);
		statusLabel.setBackground(java.awt.Color.LIGHT_GRAY);
		statusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.add(statusLabel);
		
		
		dropButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				setVisible(false);
			}
		});
		
	}
	
	private javax.swing.JPanel creatField(String name, javax.swing.JTextField jtf){
		
		javax.swing.JLabel matLabel = new javax.swing.JLabel(name);
		matLabel.setFont(new java.awt.Font("Posterama",java.awt.Font.BOLD,15));
		matLabel.setHorizontalAlignment(javax.swing.JLabel.LEFT);
		matLabel.setPreferredSize(new java.awt.Dimension(90,50));
		
		jtf.setPreferredSize(new java.awt.Dimension(180, 40));

		//jtf.setFont(new java.awt.Font("Comfortaa",java.awt.Font.BOLD,16));
		setJTextMessage(jtf,"",false);
		jtf.setBackground(new java.awt.Color(238,236,236));
		
		javax.swing.JPanel container = new javax.swing.JPanel();
		container.setOpaque(false);
		
		container.add(matLabel);
		container.add(jtf);
		return container;
	}
	
//	private void setInfo(String ...arg0){
//		for (String string : arg0) {
//			myArray.add(string);
//		}
//	}
	
	
	private void setJTextMessage(javax.swing.JTextField field, String message, Boolean small){
		field.setFont(new java.awt.Font(small?"roboto":"Comfortaa",small?java.awt.Font.ITALIC:java.awt.Font.PLAIN,small?14:18));
		field.setText(message);
		//not small "Comfortaa",java.awt.Font.BOLD,16
	}
	
	public String getRadioSelected(){
		return bg.getSelection().getActionCommand();
		
	}
	@Override
	public void focusGained(java.awt.event.FocusEvent e) {
		statusLabel.setIcon(new javax.swing.ImageIcon(REGISTRATION));
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				if(((javax.swing.JTextField)e.getSource()).equals(matJTexField) && matReset){
					setJTextMessage(matJTexField, "", false);
					//System.out.println("focus : réinitialisé");
				}
				else if(((javax.swing.JTextField)e.getSource()).equals(nomJTexField) && nomReset){
					
					setJTextMessage(nomJTexField, "", false);
					//System.out.println("focus : réinitialisé");
				}
				else if(((javax.swing.JTextField)e.getSource()).equals(preJTexField) && preReset){
					setJTextMessage(preJTexField, "", false);
					//System.out.println("focus : réinitialisé");
				}
				
			}
		});
		t.start();
	}

	@Override
	public void focusLost(java.awt.event.FocusEvent e) {
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				if(((javax.swing.JTextField)e.getSource()).equals(matJTexField)){
					if(!matJTexField.getText().matches("^(INP2019)(\\d){4}")) {
						setJTextMessage(matJTexField, "*(Ex: INP2019xxxx)", true);//matJTexField.setText("*(Ex: INP2019xxxx)");
						matReset = true;matState = false;
					}
					else {matState = true;matReset = false;}
				}
				else if(((javax.swing.JTextField)e.getSource()).equals(nomJTexField) ){
					if(!nomJTexField.getText().matches("[a-zA-Z]+")){
						namState = false;//System.out.println("alpha erroe");
						setJTextMessage(nomJTexField, "*(Ex :kouassi)", true);
						nomReset = true;//System.out.println("focus : réinitialisé");
					}
					else {namState = true;nomReset = false;}
				}
				else if(((javax.swing.JTextField)e.getSource()).equals(preJTexField)){
					if(!preJTexField.getText().matches("[a-z A-Z]+")){
						
						setJTextMessage(preJTexField, "*(Ex : jean)", true);
						preReset = true;//System.out.println("focus : réinitialisé");
						fistState = false;
					}
					else {fistState = true;preReset = false;}
					
				}
				
			}
		});
		t.start();
		
	}

}

/*
 * javax.swing.JPanel panCenter = new javax.swing.JPanel();
		panCenter.setLayout(new java.awt.FlowLayout());
		panCenter.add(creatField("Matricule :", matJTexField));
		
		panCenter.add(javax.swing.Box.createVerticalStrut(15));
		panCenter.add(creatField("Nom :", nomJTexField));
		
		panCenter.add(javax.swing.Box.createVerticalStrut(15));
		panCenter.add(creatField("Prenom :", preJTexField));
		
		panCenter.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.GRAY));
		
		this.setLayout(new java.awt.BorderLayout());
		this.add(panCenter,java.awt.BorderLayout.CENTER);
		this.add(panSouth, java.awt.BorderLayout.SOUTH);
		*/