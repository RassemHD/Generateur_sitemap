package projet;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLFrameHyperlinkEvent;
/**
 * Cette classe permet de lancer l'application en mode graphique
 * @author Rassim
 */
public class GUILauncher extends javax.swing.JFrame {

	// Composants graphiques principaux
	
	private javax.swing.JButton dataBut;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JLabel resultLab;
	private javax.swing.JList<String> resultList;
	private javax.swing.JButton searchBut;
	private javax.swing.JTextField searchFild;
	private javax.swing.JButton sitemapBut;
	
	
	// Représente la racine de jeux de données
	
	private String rootPath;
	
/**
 * Constructeur
 * initialise les composants graphiques, et le titre par défaut 
 * défini la position initiale de l'interface graphique
 * assure la visibilité de l'intreface graphique
 */
	public GUILauncher() {
		super("PROMENADE DANS LE SYSTEME SOLAIRE");
		initComponents();
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		this.setVisible(true);		
	}

/**
 * Initialise les différents composants de l'interface graphique et assure sa mise en forme 
 * Associe les écouteurs d'évenements aux composants graphiques
 */
    private void initComponents() {

        searchBut = new javax.swing.JButton();
        searchBut.setText("Rechercher");
        searchBut.addActionListener(new ActionSearchBut());
        
        searchFild = new javax.swing.JTextField("saisir du texte ...");
        searchFild.getFont().deriveFont(Font.ITALIC);
        searchFild.setForeground(Color.gray);
        searchFild.addMouseListener(new ActionSearchFild());
        
        resultList = new javax.swing.JList<>();
        resultList.setModel(new javax.swing.AbstractListModel<String>() {
          	String[] tab = new String[0];
            public int getSize() { return tab.length; }
            public String getElementAt(int i) { return tab[i]; }});
        resultList.setFixedCellHeight(20);
		resultList.addMouseListener(new ActionListSelect());
        
		
        dataBut = new javax.swing.JButton();
        dataBut.setText("Sélectionner le jeux de données");
        dataBut.addActionListener(new ActionSelectData());
               
        sitemapBut = new javax.swing.JButton();
        sitemapBut.setText("Afficher mon sitemap");
        sitemapBut.addActionListener(new ActionViewSitemap());
        
        jScrollPane1 = new javax.swing.JScrollPane();
        jScrollPane1.setViewportView(resultList);
        
        resultLab = new javax.swing.JLabel();
        resultLab.setText("Résultats :");

                
        setBackground(new java.awt.Color(214, 27, 37));
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(sitemapBut, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(dataBut, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(searchFild)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(searchBut, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 642, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(resultLab)))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(dataBut)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sitemapBut)
                .addGap(73, 73, 73)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchFild, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchBut))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                .addComponent(resultLab)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        
        pack();
    }               

/**
 * Sous interface graphique permettant la visualisation des pages web dans une fenetre graphique et d'afficher leurs principales caractéristiques
 * @author Rassim
 */
    	
    private class PageViewer extends JFrame implements HyperlinkListener, ActionListener{

    	// Composants graphiques principaux
    	
    	private JEditorPane viewerPane;
    	private JTextField  urlField;
    	private JButton infoBut;
    	private JPanel inputPanel;
    	private JScrollPane scrollPane;
    	
    	// Constructeur
    	
    	public PageViewer () {
    		super("Web Page Viewer");
    		this.initComponents();
			this.setSize(1200,700);
			this.setLocationRelativeTo(null);
			this.setVisible(true);			
		}
    	
    	
    	// initialisation des composants et association des écouteurs d'évenements
    	
    	private void initComponents() {
    		
    		viewerPane = new JEditorPane ();
    		viewerPane.setEditable (false);
			viewerPane.addHyperlinkListener (this);
    		
    		urlField = new JTextField ();
    		urlField.setEditable(false);
    		
    		infoBut = new JButton ("Info");
    		infoBut.addActionListener(this);
    		
    		inputPanel = new JPanel (new BorderLayout ());
    		inputPanel.add (infoBut, BorderLayout.WEST);
    		inputPanel.add (urlField, BorderLayout.CENTER);
    		getContentPane ().add (inputPanel, BorderLayout.NORTH);
    		
    		
    		scrollPane = new JScrollPane (viewerPane);
    		getContentPane ().add (scrollPane, BorderLayout.CENTER);
    		
    	}
    	
    	
    	// méthode permettant le chargement des pages dans l'éditeur
    	
    	
    	public void loadPage (String urlText){
   		 try{
   			 viewerPane.setPage (new URL (urlText));
   		 } 
   		 catch (IOException ex) {
   			new JOptionPane().showMessageDialog(null, "Accès impossible!", "Info", JOptionPane.ERROR_MESSAGE);
   		}
   	}
    	
    	// écouteurs d'évenements
    	
    	
    	public void hyperlinkUpdate (HyperlinkEvent event) {
    		if (event.getEventType() == HyperlinkEvent.EventType.ACTIVATED){

    			urlField.setText (event.getURL ().toString ());
    			if (event instanceof HTMLFrameHyperlinkEvent) {
    				HTMLDocument doc = (HTMLDocument)viewerPane.getDocument ();
    				doc.processHTMLFrameHyperlinkEvent ((HTMLFrameHyperlinkEvent)event);
    			}else loadPage (urlField.getText ());
    		}
    	}

    	public void actionPerformed (ActionEvent event){
    		DataExplorer DExp = new DataExplorer(rootPath);
    		try {
    			DExp.restorDataSer("data.ser");
				} catch (FileNotFoundException e1) {
					new JOptionPane().showMessageDialog(null,"fichier index introuvable!"+"\n"+" Veuillez sélectionner le jeux de données pour le génerer." ,"Info",JOptionPane.INFORMATION_MESSAGE);
					e1.printStackTrace();
				} catch (ClassNotFoundException e2) {
					new JOptionPane().showMessageDialog(null,"la classe des objets sérialisés est introuvable." ,"Error",JOptionPane.ERROR_MESSAGE);
					e2.printStackTrace();
				} catch (IOException e3) {
					new JOptionPane().showMessageDialog(null,"Erreur de lecture de fichier index." ,"Error",JOptionPane.ERROR_MESSAGE);
					e3.printStackTrace();
				}
    	       	ArrayList<WebPage> result = DExp.search(searchFild.getText());
    	       	for(int i=0;i<result.size();i++) {
    	       		if(result.get(i).getLink().equals(urlField.getText ())) {
            			new JOptionPane().showMessageDialog(null,result.get(i).toString(), "Info Web Page",JOptionPane.INFORMATION_MESSAGE);
            			break;
            		}
            	}	
    	}
    	
}

    
    // écouteurs d'évenements
    
    
    
    private class ActionSearchBut implements ActionListener{

		
		public void actionPerformed(ActionEvent e) {
			if(!searchFild.getText().equals("saisir du texte ...") && !searchFild.getText().equals("")) {
				DataExplorer DExp = new DataExplorer(rootPath);
				try {
					DExp.restorDataSer("data.ser");
				} catch (FileNotFoundException e1) {
					new JOptionPane().showMessageDialog(null,"fichier index introuvable!"+"\n"+" Veuillez sélectionner le jeux de données pour le génerer." ,"Info",JOptionPane.INFORMATION_MESSAGE);
					e1.printStackTrace();
				} catch (ClassNotFoundException e2) {
					new JOptionPane().showMessageDialog(null,"la classe des objets sérialisés est introuvable." ,"Error",JOptionPane.ERROR_MESSAGE);
					e2.printStackTrace();
				} catch (IOException e3) {
					new JOptionPane().showMessageDialog(null,"Erreur de lecture de fichier index." ,"Error",JOptionPane.ERROR_MESSAGE);
					e3.printStackTrace();
				}
	       		ArrayList<WebPage> result = DExp.search(searchFild.getText());
	       		DefaultListModel jModel = new DefaultListModel();
	       		if(result.isEmpty()) jModel.addElement("Aucune page ne correspond à votre recherche!");
	       		else {
	       			for(int i=0;i<result.size();i++) {
	        			jModel.addElement(result.get(i).getLink());
	        		}
	       		}
	       		resultList.setModel(jModel);
			}
			
		}
    	
    }
    
    private class ActionViewSitemap implements ActionListener{

		public void actionPerformed(ActionEvent e) {
	
			try {
				BufferedReader br = new BufferedReader(
										new FileReader(
											new File("sitemap.xml")));
				JTextArea textAr = new JTextArea();
				textAr.read(br,null);
				br.close();
				JFrame JtextViewer = new JFrame();
				JScrollPane sPan = new JScrollPane(textAr);
				JtextViewer.getContentPane().add(sPan);
				JtextViewer.setSize (450,600);
				JtextViewer.setResizable(false);
				JtextViewer.setVisible(true);
				textAr.setEditable(false);
				
			} catch (FileNotFoundException e1) {
				new JOptionPane().showMessageDialog(null,"sitemap introuvable!"+"\n"+" Veuillez sélectionner le jeux de données pour le génerer." ,"Info",JOptionPane.INFORMATION_MESSAGE);
				e1.printStackTrace();
			} catch (IOException e1) {
				new JOptionPane().showMessageDialog(null,"Erreur de lecture de fichier sitemap." ,"Error",JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
			}
		}
    	
    }
    
    private class ActionSelectData implements ActionListener{
    	
		public void actionPerformed(ActionEvent e) {
		
			JFileChooser fc = new JFileChooser ();
			JOptionPane jop = new JOptionPane();
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			fc.showOpenDialog(null);
			rootPath = fc.getSelectedFile().getAbsolutePath();
			DataExplorer DExp = new DataExplorer(rootPath);
			try {
				DExp.generatSiteMapXML("sitemap.xml");
				DExp.generatIndexFileSer("data.ser");
				jop.showMessageDialog(null, "Sitemap et index générés avec succès", "Info", JOptionPane.INFORMATION_MESSAGE);
			}catch(NullPointerException e1) {
				jop.showMessageDialog(null, "Repertoire inaccessible.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}   	
    }
    
    private class ActionListSelect implements MouseListener {

	
		public void mouseClicked(MouseEvent arg0) {
			String selectedValue = resultList.getSelectedValue();
			boolean isNotEmpty = (selectedValue!=null) ;
			if(isNotEmpty) {
				if(!selectedValue.equals("Aucune page ne correspond à votre recherche!")) {
					PageViewer  pv = new PageViewer();
					pv.urlField.setText(selectedValue);
					pv.loadPage(selectedValue);
				}
			}
			
		}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
    	
    }
    
    private class ActionSearchFild implements MouseListener{
    	
        public void mouseClicked(MouseEvent e) {
            JTextField texteField = ((JTextField)e.getSource());
            texteField.setText("");
            texteField.getFont().deriveFont(Font.PLAIN);
            texteField.setForeground(Color.black);
            texteField.removeMouseListener(this);
        }
        
        public void mouseReleased(MouseEvent e) {}         
        public void mousePressed(MouseEvent e) {}          
        public void mouseExited(MouseEvent e) {}           
        public void mouseEntered(MouseEvent e) {}   
    	
    }
    
    
  
    public static void main(String args[]) {
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUILauncher.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUILauncher.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUILauncher.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUILauncher.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUILauncher();
            }
        });
    }  	
    
}
