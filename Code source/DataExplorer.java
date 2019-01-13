package projet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Cette classe est la base de l'application
 * Elle permet la g�n�ration de sitemap,l'indexation et la recherche
 * @author Rassem
 */
public class DataExplorer
{
	
/**
 * Collecte les fichiers filtr�s
 */
	private ArrayList<File> alFiles;
/**
 * Collecte les informations relatives � chaque page de site
 */
	private ArrayList<WebPage> listWebPages;
/**
 * 	Repr�sente la racine de jeux de donn�es
 */
	private String rootPath;
/**
 * Constructeur
 * initialise les deux ArrayList � vide
 * initialise la racine par la valeur entr�e par l'utilisateur
 * @param rootPath racine de jeux de donn�es � traiter
 */
	public DataExplorer(String rootPath) {
		listWebPages = new ArrayList<WebPage>();
		alFiles = new ArrayList<File>();
		this.rootPath = rootPath;
	}
	
/**
 * Parcours r�cursivement l'arbor�scence de fichiers
 * Filtre les fichiers et r�cup�re uniqument ceux qui remplissent des condition bien pr�cises (extension html,contient title ou h1...)
 * Stocke les fichies obtenus dans l' ArrayList alFile d�finie comme attribut de cette classe
 * @param root racine de jeux de donn�es � traiter
 */
	public void scanFiles(String root) {
	    File f = new File(root);
	    File[] listFiles = f.listFiles();
	    for (int i = 0; i < listFiles.length; i++) {
	    	if(listFiles[i].isFile()){
	    		if(listFiles[i].getName().endsWith("html")) {
	    			if(!listFiles[i].getName().equals("promenade-fr.html") && !listFiles[i].getName().equals("index.html")) {
		    			if(hasTitleOrH1(listFiles[i])) {
		    				if(!alFiles.contains(listFiles[i])){
		    					alFiles.add(listFiles[i]);
		    				}
		    			}
		    		}
	    		}
	    	}
	    	if(listFiles[i].isDirectory()) scanFiles(listFiles[i].getAbsolutePath());
	    }
	}
/**
 * Teste si un fichier html contient au moins une balise title ou h1
 * @param f fichier html
 * @return vrai si le fichier contient au moins une balise title ou h1 , faux sinon
 */
	public boolean hasTitleOrH1(File f) {
		boolean find = false;
		try {
				BufferedReader br = new BufferedReader(
										new FileReader(f));
				String line = br.readLine();

				Pattern pat_TITLE = Pattern.compile(".*<TITLE>(.*)\\</TITLE>.*");
				Pattern pat_title = Pattern.compile(".*<title>(.*)\\</title>.*");
				Pattern pat_H1 = Pattern.compile(".*<H1>(.*)\\</H1>.*");
				Pattern pat_h1 = Pattern.compile(".*<h1>(.*)\\</h1>.*");

				while(line!= null && !find) {
	
					Matcher mat_TITLE = pat_TITLE.matcher(line);
					Matcher mat_title = pat_title.matcher(line);
					Matcher mat_H1 = pat_H1.matcher(line);
					Matcher mat_h1 = pat_h1.matcher(line);
	
					if(mat_TITLE.find()||mat_title.find()||mat_H1.find()||mat_h1.find()) find=true;
		
					line = br.readLine();
				}
				br.close();			
				return find;
			
		} catch (FileNotFoundException e) {
			System.out.println("html file not found");
			e.printStackTrace();
			return find;
		} catch (IOException e) {
			System.out.println("error while reading html file");
			e.printStackTrace();
			return find;
		}
	}
/**
 * Permet l'indexation des pages de site
 * Extrait le contenu des balises title et h1 de chaque fichier et elle lui associe une instance de la classe WebPage
 * et elle la stocke par la suite dans l'Arraylist listWebPage d�finie ci dessus comme attribut	
 * @param f fichier html
 */
	public  void  indexWebPages(File f) {		
		try {
			
			BufferedReader br = new BufferedReader(
									new FileReader(f));
			String line = br.readLine();
			StringBuffer tagContent = new StringBuffer();
						
			Pattern pat_TITLE = Pattern.compile(".*<TITLE>(.*)\\</TITLE>.*");
			Pattern pat_title = Pattern.compile(".*<title>(.*)\\</title>.*");
			Pattern pat_H1 = Pattern.compile(".*<H1>(.*)\\</H1>.*");
			Pattern pat_h1 = Pattern.compile(".*<h1>(.*)\\</h1>.*");

			while(line!= null) {
				
				Matcher mat_TITLE = pat_TITLE.matcher(line);
				Matcher mat_title = pat_title.matcher(line);
				Matcher mat_H1 = pat_H1.matcher(line);
				Matcher mat_h1 = pat_h1.matcher(line);
	
				if(mat_TITLE.find()) tagContent.append(mat_TITLE.group(1));
				if(mat_title.find()) tagContent.append(mat_title.group(1));
				if(mat_H1.find()) tagContent.append(mat_H1.group(1));
				if(mat_h1.find()) tagContent.append(mat_h1.group(1));
	
				line = br.readLine();
			}
			
				String tag = checkHTMLEntities(tagContent.toString());
				tag = adjustText(tag);
				String link = f.getAbsolutePath().replace(this.rootPath,"https://promenade.imcce.fr/fr").replace("\\", "/");
				Date lastmod = new Date(f.lastModified());
				listWebPages.add(new WebPage(link,tag,lastmod));
				
			
			br.close();						
		} catch (FileNotFoundException e) {
			System.out.println("html file not found");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("error while reading html file");
			e.printStackTrace();
		}
	}
/**
 * Remplace les entit�es html des balises extraites, par les caract�res correspondants
 * @param tag balise initiale  
 * @return tag balise sans entit�es html
 */
	public String checkHTMLEntities(String tag) {
	
		tag = Pattern.compile("&agrave;").matcher(tag).replaceAll("�");
		tag = Pattern.compile("&Agrave;").matcher(tag).replaceAll("�");		
		tag = Pattern.compile("&acirc;").matcher(tag).replaceAll("�");
		tag = Pattern.compile("&Acirc;").matcher(tag).replaceAll("�");		
		tag = Pattern.compile("&eacute;").matcher(tag).replaceAll("�");		
		tag = Pattern.compile("&Eacute;").matcher(tag).replaceAll("�");		
		tag = Pattern.compile("&egrave;").matcher(tag).replaceAll("�");		
		tag = Pattern.compile("&Egrave;").matcher(tag).replaceAll("�");		
		tag = Pattern.compile("&ecirc;").matcher(tag).replaceAll("�");		
		tag = Pattern.compile("&Ecirc;").matcher(tag).replaceAll("�");
		tag = Pattern.compile("&euml;").matcher(tag).replaceAll("�");
        tag = Pattern.compile("&Euml;").matcher(tag).replaceAll("�");       
        tag = Pattern.compile("&icirc;").matcher(tag).replaceAll("�");
		tag = Pattern.compile("&Icirc;").matcher(tag).replaceAll("�");		
		tag = Pattern.compile("&iuml;").matcher(tag).replaceAll("�");
		tag = Pattern.compile("&Iuml;").matcher(tag).replaceAll("�");        
		tag = Pattern.compile("&ocirc").matcher(tag).replaceAll("�");
		tag = Pattern.compile("&Ocirc").matcher(tag).replaceAll("�");		
		tag = Pattern.compile("&ucirc;").matcher(tag).replaceAll("�");		
		tag = Pattern.compile("&Ucirc;").matcher(tag).replaceAll("�");		
		tag = Pattern.compile("&oelig;").matcher(tag).replaceAll("�");        
        tag = Pattern.compile("&OElig;").matcher(tag).replaceAll("�");		
		tag = Pattern.compile("&ccedil;").matcher(tag).replaceAll("�");		 
        tag = Pattern.compile("&Ccedil;").matcher(tag).replaceAll("�");
        tag = Pattern.compile("é").matcher(tag).replaceAll("�");
        tag = Pattern.compile("è").matcher(tag).replaceAll("�");
        tag = Pattern.compile("ê").matcher(tag).replaceAll("�");
        tag = Pattern.compile("ô").matcher(tag).replaceAll("�");
        tag = Pattern.compile("ç").matcher(tag).replaceAll("�");
        tag = Pattern.compile("ï").matcher(tag).replaceAll("�");
        tag = Pattern.compile("&nbsp").matcher(tag).replaceAll(" ");  
		tag = tag.replaceAll("<[^>]*>", "");
		tag =tag.replace("."," ");
		tag =tag.replace(","," ");
		tag =tag.replace(";"," ");
		tag =tag.replace(":"," ");
		tag =tag.replace("-"," ");
		tag =tag.replace("\""," ");
		tag =tag.replace("\\("," ");
		tag =tag.replace("\\)"," ");
		tag =tag.replace("\\*"," ");
		
		return tag;
	}
/**
 * Permet de faciliter la recherche en la rendant insensible au majuscules/miniscules , au accents ...
 * @param text chaine de caract�re initiale saisie par l'utilisateur
 * @return text chaine de caract�res sans majuscules , sans accents ...
 */
	public String adjustText(String text) {
		
		text = text.toLowerCase();
		
		text = text.replace("�","a");
		text = text.replace("�","a");
		text = text.replace("�","a");
		text = text.replace("�","e");
		text = text.replace("�","e");
		text = text.replace("�","e");
		text = text.replace("�","i");
		text = text.replace("�","i");
		text = text.replace("�","o");
		text = text.replace("�","u");
		
		text = text.replace("�","c");
		text =text.replace("'"," ");
		
		if(text.endsWith(" ") || text.indexOf(" ")==0) text = text.replace(" ","");

		return text;
	}
/**
 * G�n�re le fichier sitemap 
 * @param fileNameXML nom de fichier sitemap
 */
	public void generatSiteMapXML(String fileNameXML) {
		try {
			scanFiles(rootPath);
			BufferedWriter bw = new BufferedWriter(
										new FileWriter(
												new File(fileNameXML)));
			
			bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");bw.newLine();
			bw.write("<urlset xmlns="+"\"http://www.sitemaps.org/schemas/sitemap/0.9\""+">");bw.newLine();
			
			for(int i=0;i<alFiles.size();i++) {
				String link = alFiles.get(i).getAbsolutePath().replace(this.rootPath,"https://promenade.imcce.fr/fr").replace("\\", "/");
				bw.write("<url>"); bw.newLine();
				bw.write("<loc>"+link+"</loc>");bw.newLine();
				bw.write("<lastmod>"+new Date(alFiles.get(i).lastModified())+"</lastmod>");bw.newLine();
				bw.write("<changefreq>"+"Monthly"+"</changefreq>");bw.newLine();
				bw.write("<priority>"+"0.5"+"</priority>");bw.newLine();
				bw.write("</url>");bw.newLine();			
			}
			
			bw.write("</urlset>");
			bw.close();
			System.out.println("<< Sitmap created successfully! >>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
/**
 * G�n�re le fichier d'indexation par le m�canisme de s�rialisation
 * @param fileNameSer nom de fichier index
 */
	public void generatIndexFileSer(String fileNameSer) {
		try {
			scanFiles(rootPath);
			ObjectOutputStream oos = new ObjectOutputStream(
										new FileOutputStream(
											new File(fileNameSer)));
			for(int i=0;i<alFiles.size();i++) {
				indexWebPages(alFiles.get(i));
			}
			oos.writeObject(listWebPages);
			oos.close();				
			System.out.println("<< Index file created successfully! >>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
/**
 * R�staure les donn�es sauvegard�es
 * Permet de lancer la recherche au d�marage de programme, sans refaire par les deux phases pr�c�dentes   
 * @param serialName nom de fichiers r�sultat d'indexation
 * @throws FileNotFoundException si le fichier index est introuvable (supprim�,d�plac� ...)
 * @throws IOException si une erreur est survenue lors de la lecture de fichier index
 * @throws ClassNotFoundException si la classe WebPage est introuvable (supprim�e, modifi�e...)
 */
	public  void restorDataSer(String serialName) throws FileNotFoundException, IOException, ClassNotFoundException{

			ObjectInputStream ois = new ObjectInputStream(
										new FileInputStream(
											new File(serialName)));
			
			listWebPages = (ArrayList<WebPage>) ois.readObject();
			ois.close();
			
	}
/**
 * Permet la recherche par mot clef � partir de l�indexation pr�alable du contenu du jeu de donn�es
 * @param text chaine de caract�re � rechercher, saisie par l'utilisateur
 * @return result liste des pages correspondant au crit�re recherch�
 */
	public ArrayList<WebPage> search(String text) {
		ArrayList<WebPage> result = new ArrayList<WebPage>();
		text = adjustText(text);
		for(int i=0;i<listWebPages.size();i++) {
			if(listWebPages.get(i).getTagContent().contains(text)) {
				result.add(listWebPages.get(i));
			}
		}
		return result;
	}
	
}
