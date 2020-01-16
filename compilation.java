import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.FileNotFoundException;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;

public class compilation extends JFrame {
	
	static int xx,xy;
	static ArrayList<String> mots       = new ArrayList<String>();
	static ArrayList<String> mots_ligne = new ArrayList<String>();
	static ArrayList<String> mots_lexic = new ArrayList<String>();
	
	/* L'analyse Lexicale */

	public void lexicale(List<String> liste) {
	int i = 0;

	while (i < mots.size()) {
		if (mots_reserver(mots.get(i)) != null) {
			mots_lexic.add(mots_reserver(mots.get(i)));
		} else if (identificateur(mots.get(i)) != null) {
			mots_lexic.add(identificateur(mots.get(i)));
		} else if (numero(mots.get(i)) != null) {
			mots_lexic.add(numero(mots.get(i)));
		}
		else mots_lexic.add("Erreur");

		i++;
	}

}

	/* L'analyse Syntaxique */

	public String syntaxique(String code){
	
		if(code.equals("Start_Program")) return "Début du programme";
		else if(code.equals("Else")) return "SINON";
		else if(code.equals("Start")) return "Début d'un bloc";
		else if(code.equals("Finish")) return "Fin d'un bloc";
		else if(code.startsWith("//.")) return "Exemple d'un commentaire";
		else if(code.equals("End_Program")) return "Fin du programme";
		else if(code.startsWith("ShowMes : \" ") && code.endsWith(" \" ;;")) return "Affichage d'un message à l'ecran";
		else if(code.contains(" ")) {
			String[] mot;
			mot =code.split(" ");
			int i=0,k=1;
			if(mot[i].equals("Int_Number")) {
				i++;
				if(mot[i].equals(":"))i++;
					if(identificateur(mot[i])!=null) {
						i++;
						while(mot[i].equals(",")) {
							i++;
							k++;
							if(identificateur(mot[i])!=null)i++;
						}
						if(mot[i].equals(";;")) return "Declaration de "+k+" variables d'entier";
					}
				}
			else if(mot[i].equals("Give")) {
				i++;
				if(identificateur(mot[i]) != null){
					i++;
					if(mot[i].equals(":"))i++;
					if(numero(mot[i]) == "Nombre entier") {
						i++;
						if(mot[i].equals(";;")) return "affectation dune valeur entiere à "+mot[i-3];
					}
					else if(numero(mot[i]) == "Nombre reel"){
						i++;
						if(mot[i].equals(";;")) return "affectation dune valeur reel à "+mot[i-3];
					}
			}
		}
			else if(mot[i].equals("Real_Number")) {
				i++;
				if(mot[i].equals(":"))i++;
				if(identificateur(mot[i]) != null)i++;
					if(mot[i].equals(";;")) return "Déclaration de  variable reel";
				}
			else if(mot[i].equals("If")) {
				i++;
				if(mot[i].equals("--")) {
					i++;
					if(identificateur(mot[i])!=null) {
						i++;
						if(mot[i].equals("<") || mot[i].equals(">") || mot[i].equals("==")) {
							i++;
							if(identificateur(mot[i])!=null) {
								i++;
								if(mot[i].equals("--")) return "condition";
							}
						}
					}
				}
			}
			else if(mot[i].equals("Affect")) {
				i++;
				if(identificateur(mot[i])!=null) {
					i++;
					if(mot[i].equals("to")) {
						i++;
						if(identificateur(mot[i])!=null) {
							i++;
							if(mot[i].equals(";;"))return "affectetaion de "+mot[i-3]+" a "+mot[i-1];
						}
					}
				}
			}
			else if(mot[i].equals("ShowVal")) {
				i++;
				if(mot[i].equals(":"))i++;
				if(identificateur(mot[i])!=null)i++;
				if(mot[i].equals(";;"))return "affichage de la valeur "+mot [i-1];
			}
			
		}
		return "erreur syntaxique";
	}
	
	/* L'analyse Semantique */
	
	public String semantique(String chaine){
		
		if(chaine.equals("Start_Program")) return "public static void main(String[] args) {";
		else if(chaine.equals("Else")) return "else";
		else if(chaine.equals("Start")) return "{";
		else if(chaine.equals("Finish")) return "}";
		else if(chaine.equals("End_Program")) return "}";
		else if(chaine.contains(" ")) {
			String[] mot;
			mot = chaine.split(" ");
			int i=0;

				if(mot[i].equals("Int_Number")){
					i++;
					if(mot[i].equals(":"))
						i++;
						if(identificateur(mot[i]) != null){
							i++;
							while(mot[i].equals(",")){
								i++;
								if(identificateur(mot[i]) != null) i++;
							}
							if(mot[i].equals(";;")) {
								String s=mot[2];
								int j=4;
								while (j<i) {
									s=s+(", "+mot[j]);
									j=j+2;
								}
								s="int "+s+";";
								return s;
							}
						}
					

				}
				
				else if(mot[i].equals("Give")){
					i++;
					if(identificateur(mot[i]) != null){
						i++;
					if(mot[i].equals(":"))i++;
						if(numero(mot[i]) == "Nombre entier") {
							i++;
							if(mot[i].equals(";;")) return mot[i-3]+" = "+mot[i-1]+" ;";
						}
						else if(numero(mot[i]) == "Nombre reel"){
							i++;
							if(mot[i].equals(";;")) return mot[i-3]+" = "+mot[i-1]+" ;";
						}

					
				}

				}
				
				if(mot[i].equals("Real_Number")){
					i++;
					if(mot[i].equals(":"))
						i++;
						if(identificateur(mot[i]) != null){
							i++;
							while(mot[i].equals(",")){
								i++;
								if(identificateur(mot[i]) != null) i++;
							}
							if(mot[i].equals(";;")) {
								String s=mot[2];
								int j=4;
								while (j<i) {
									s=s+(", "+mot[j]);
									j=j+2;
								}
								s="double "+s+";";
								return s;
							}
						}
					

				}
				
				else if(mot[i].equals("If")){
					i++;
					if(mot[i].equals("--")){
						i++;
						if(identificateur(mot[i]) != null){
							i++;
							if(mot[i].equals("<") || mot[i].equals(">") || mot[i].equals("==")){
								i++;
								if(identificateur(mot[i]) != null){
									i++;
									if(mot[i].equals("--")) return "if"+"( "+mot[i-3]+" "+mot[i-2]+" "+mot[i-1]+" )"; 
								}
							}
						}
					}
				}
				
				
				
				else if(mot[i].equals("Affect")){
					i++;
					if(identificateur(mot[i]) != null){
						i++;
						if(mot[i].equals("to")){
							i++;
							if(identificateur(mot[i]) != null) {
								i++;
								if(mot[i].equals(";;")) return  mot[i-3]+" = "+mot[i-1]+" ;";
							}
						}
					}
				}
				
				
				else if(mot[i].equals("ShowVal")){
					i++;
					if(mot[i].equals(":"))i++;
					if(identificateur(mot[i]) != null)i++;
					if(mot[i].equals(";;")) return "System.out.println( "+mot[i-1]+" ) ;";
				}
				
				else if(mot[i].equals("ShowMes")) {
					String s="System.out.println( \"";
					i++;
					if(mot[i].equals(":"))i++;
					if(mot[i].equals("\""))i++;
					while(!mot[i].equals("\"")) {
						s=s+mot[i]+" ";
						i++;
					}
					s=s+"\");";
					return s;
				}
				//. ceci est un commentaire
				else if(mot[i].equals("//.")) {
					String s="// ";
					i++;
					while(i<mot.length) {
						s=s+mot[i]+" ";
						i++;
					}
					return s;
				}
		}
		return "Erreur symantique";
		
	}
	
	/* Un Chiffre */
	
	public boolean chiffre(String chaine, int i) {
		char[] nombre = { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' };
		int j = 0;
		while (j < nombre.length) {
			if (chaine.charAt(i) == nombre[j]) {
				return true;
			}
			j++;
		}

		return false;
	}
	
	/* Un Numero */
	
	public String numero(String chaine) {
		int i = 0;
		int token_pos = 0;
		boolean point_unique = true;
		while (i < chaine.length()) {
			if (chiffre(chaine, i)) token_pos++;
			else if(point_unique & chaine.charAt(token_pos)=='.') {
				token_pos++;
				point_unique = false;
			}
			i++;
		}

		if (token_pos == chaine.length() && !chaine.contains(".")) return "Nombre entier";
		else if (token_pos == chaine.length() && !point_unique) return "Nombre reel";
		return null;

	}

	/* Un Caractere */

	public boolean caractere(String chaine, int i) {
		char[] alphabet = { 'A', 'a', 'B', 'b', 'C', 'c', 'D', 'd', 'E', 'e', 'F', 'f', 'G', 'g', 'H', 'h', 'I', 'i',
				'J', 'j', 'K', 'k', 'L', 'l', 'M', 'm', 'N', 'n', 'O', 'o', 'P', 'p', 'Q', 'q', 'R', 'r', 'S', 's', 'T',
				't', 'U', 'u', 'V', 'v', 'W', 'w', 'X', 'x', 'Y', 'y', 'Z', 'z' };
		int k = 0;
		while (k < alphabet.length) {
			if (chaine.charAt(i) == alphabet[k]) {
				return true;
			}
			k++;
		}
		return false;

	}
	
	/* Un Identificateur */
	
	public String identificateur(String chaine) {
		boolean verifier_Premier = false;
		boolean tiret_unique = true;
		int token_pos = 0;
		int i = 0;
		if (caractere(chaine, 0)) {
			token_pos++;
			verifier_Premier = true;
		}
		if (verifier_Premier == true && chaine.length() == 1)
			return "identificateur";

		else if (chaine.length() > 1) {
			i = 1;
			while (i < chaine.length()) {

				if (caractere(chaine, i))
					{token_pos++;
					tiret_unique=true;
					}
				else if (chiffre(chaine, i))
					{token_pos++;
					tiret_unique=true;
					}
				else if (chaine.charAt(i) == '_' && tiret_unique) {
					tiret_unique=true;
					token_pos++;
				}
				i++;
			}
			if (token_pos == chaine.length())
				return "Identificateur";
		}
		return null;
	}

	/* Les Mots Reserver */

	public String mots_reserver(String chaine) {
		String[] mot_reserve = {":", "\"", "<", ">", ",", "Start_Program", "Int_Number", ";;", "Affect", "Real_Number", "If", "--", "Else",
				"Start", "Give","to", "Finish", "ShowMes", "ShowVal", "//.", "End_Program" };
		String[] Affichage = {"symbol deux points", "mot reserve pour guillemets",
				"symbol inferieur", "symbol superieur", "caractere reservé virgule",
				"Mot reserve debut du programme", " Mot reserve debut declaration d'un entier",
				"Mot reserve fin instruction", "Mot reserve pour affectation entre variables", " Mot reserve debut declaration d'un Real",
				" Mot reserve pour condition SI", "Mot reserve pour condition", "Mot reserve pour condition SINON", "Debut d'un sous programme",
				"Mot reserve pour affectation", "Mot reserve pour affectation", "Fin d'un sous programme",
				"Mot reservé pour afficher un message", "Mot reservé pour afficher une valeur", "Mot reservé pour un commentaire", "Mot reserve Fin du programme" };
		int i = 0;
		while (i < mot_reserve.length) {
			if (chaine.equals(mot_reserve[i])) {
				return Affichage[i];
			}
			i++;
		}
		return null;
	}
	
	/* Lance L'application */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					compilation frame = new compilation();
					frame.setUndecorated(true);
					frame.setVisible(true);
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/* Creation De La Fenetre */
	
	public compilation() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1224, 637);
		JPanel contentPane = new JPanel();
		contentPane.setBackground(SystemColor.controlHighlight);
		contentPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				xx = e.getX();
		        xy = e.getY();
			}
		});
		contentPane.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen();

		        int y = e.getYOnScreen();
		        setLocation(x - xx, y - xy);
			}
		});
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVisible(false);
		scrollPane.setBorder(null);
		scrollPane.setBounds(274, 76, 936, 547);
		contentPane.add(scrollPane);
		
		JTextArea textArea = new JTextArea();
		textArea.setBorder(null);
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		textArea.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 15));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setVisible(false);
		scrollPane_1.setBorder(null);
		scrollPane_1.setBounds(274, 76, 936, 547);
		contentPane.add(scrollPane_1);
		
		String[] obj = { "Code", "Descreption" };
		
		DefaultTableModel model = new DefaultTableModel(null, obj)
		{
			public boolean isCellEditable(int row, int column)
		    {
				return false;
		    }
		};
		JTable table = new JTable();
		table.setBorder(null);
		table.setGridColor(SystemColor.controlShadow);
		table.setSelectionForeground(SystemColor.textHighlightText);
		table.setSelectionBackground(new Color(102, 102, 255));
		table.setFont(new Font("Tahoma", Font.BOLD, 12));
		table.setModel(model);
		table.setRowHeight(30);
		scrollPane_1.setViewportView(table);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(90,80,242));
		panel.setBounds(0, 0, 257, 645);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton btn_fichier = new JButton("Charger Un Fichier");
		btn_fichier.setBounds(0, 126, 257, 69);
		panel.add(btn_fichier);
		btn_fichier.setFont(new Font("Tahoma", Font.BOLD, 14));
		btn_fichier.setForeground(Color.WHITE);
		btn_fichier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fc.setAcceptAllFileFilterUsed(false);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files","compila");
				fc.setFileFilter(filter);
				fc.showOpenDialog(null);
				String returnVal = fc.getSelectedFile().getAbsolutePath();
				File file = new File(returnVal);
				scrollPane.setVisible(true);
				scrollPane_1.setVisible(false);
				try {
					Scanner sc_lignes = new Scanner(file);
					Scanner sc_mots = new Scanner(file);
					mots.clear();
					mots_lexic.clear();
					mots_ligne.clear();
					while(sc_lignes.hasNextLine()){
						mots_ligne.add(sc_lignes.nextLine());
					}
					while(sc_mots.hasNext()){
						mots.add(sc_mots.next());
					}
					sc_mots.close();
					sc_lignes.close();
					int i = 0;
					while (i < mots_ligne.size()) {
						textArea.setText(textArea.getText()+mots_ligne.get(i)+"\n");
						i++;
					}
					
				} 
				catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		});
		btn_fichier.setBackground(new Color(80,50,250));
		btn_fichier.setFocusPainted(false);
		
		JButton btn_lexical = new JButton("Analyse Lexical");
		btn_lexical.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");
				model.setRowCount(0);
				scrollPane.setVisible(false);
				scrollPane_1.setVisible(true);
				lexicale(mots);
				int i = 0;
				while (i < mots.size()) {
					Object[] row = {" "+textArea.getText()+mots.get(i)," "+mots_lexic.get(i)};
				    model.addRow(row);
					i++;
				}
			}
		});
		btn_lexical.setForeground(Color.WHITE);
		btn_lexical.setFont(new Font("Tahoma", Font.BOLD, 14));
		btn_lexical.setBackground(new Color(80, 50, 250));
		btn_lexical.setBounds(0, 217, 257, 69);
		btn_lexical.setFocusPainted(false);
		panel.add(btn_lexical);
		
		JButton btn_syntaxique = new JButton("Analyse Syntaxique");
		btn_syntaxique.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");
				model.setRowCount(0);
				scrollPane.setVisible(false);
				scrollPane_1.setVisible(true);
				int i = 0;
				while (i < mots_ligne.size()) {
					//textArea.setText(textArea.getText()+lignes.get(i) + "  =>  " +syntax(lignes.get(i))+"\n");
					Object[] row = {" "+textArea.getText()+mots_ligne.get(i)," "+syntaxique(mots_ligne.get(i))};
				    model.addRow(row);
					i++;
				}
			}
		});
		btn_syntaxique.setForeground(Color.WHITE);
		btn_syntaxique.setFont(new Font("Tahoma", Font.BOLD, 14));
		btn_syntaxique.setBackground(new Color(80, 50, 250));
		btn_syntaxique.setBounds(0, 308, 257, 69);
		btn_syntaxique.setFocusPainted(false);
		panel.add(btn_syntaxique);
		
		JButton btn_symantique = new JButton("Analyse Symantique");
		btn_symantique.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");
				model.setRowCount(0);
				scrollPane.setVisible(false);
				scrollPane_1.setVisible(true);
				int i = 0;
				while (i < mots_ligne.size()) {
					//textArea.setText(textArea.getText()+lignes.get(i) + " -:- " +semantique(lignes.get(i))+"\n");
					Object[] row = {" "+textArea.getText()+mots_ligne.get(i)," "+semantique(mots_ligne.get(i))};
				    model.addRow(row);
					i++;
				}
			}
		});
		btn_symantique.setForeground(Color.WHITE);
		btn_symantique.setFont(new Font("Tahoma", Font.BOLD, 14));
		btn_symantique.setBackground(new Color(80, 50, 250));
		btn_symantique.setBounds(0, 403, 257, 69);
		btn_symantique.setFocusPainted(false);
		panel.add(btn_symantique);
		
		JButton btn_quitter = new JButton("Quitter Application");
		btn_quitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btn_quitter.setForeground(Color.WHITE);
		btn_quitter.setFont(new Font("Tahoma", Font.BOLD, 14));
		btn_quitter.setBackground(new Color(80, 50, 250));
		btn_quitter.setBounds(0, 497, 257, 69);
		btn_quitter.setFocusPainted(false);
		panel.add(btn_quitter);
		
		JLabel lblNewLabel = new JLabel("PROJET");
		lblNewLabel.setBounds(23, 25, 75, 27);
		panel.add(lblNewLabel);
		lblNewLabel.setForeground(new Color(204, 204, 255));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		
		JLabel lblCompilation = new JLabel("COMPILATION");
		lblCompilation.setForeground(Color.WHITE);
		lblCompilation.setFont(new Font("Algerian", Font.BOLD | Font.ITALIC, 18));
		lblCompilation.setBounds(61, 62, 162, 42);
		panel.add(lblCompilation);
		
		JLabel lblAnalyseurLexicalSymantique = new JLabel("Analyseur Lexical, Symantique, Syntaxique");
		lblAnalyseurLexicalSymantique.setFont(new Font("Segoe Print", Font.PLAIN, 18));
		lblAnalyseurLexicalSymantique.setBounds(554, 18, 419, 40);
		contentPane.add(lblAnalyseurLexicalSymantique);
	}
}
