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
 * Elle permet la génération de sitemap,l'indexation et la recherche
 * @author Rassem
 */
public class DataExplorer
{
	
/**
 * Collecte les fichiers filtrés
 */
	private ArrayList<File> alFiles;
/**
 * Collecte les informations relatives à chaque page de site
 */
	private ArrayList<WebPage> listWebPages;
/**
 * 	Représente la racine de jeux de données
 */
	private String rootPath;
/**
 * Constructeur
 * initialise les deux ArrayList à vide
 * initialise la racine par la valeur entrée par l'utilisateur
 * @param rootPath racine de jeux de données à traiter
 */
	public DataExplorer(String rootPath) {
		listWebPages = new ArrayList<WebPage>();
		alFiles = new ArrayList<File>();
		this.rootPath = rootPath;
	}
	
/**
 * Parcours récursivement l'arboréscence de fichiers
 * Filtre les fichiers et récupère uniqument ceux qui remplissent des condition bien précises (extension html,contient title ou h1...)
 * Stocke les fichies obtenus dans l' ArrayList alFile définie comme attribut de cette classe
 * @param root racine de jeux de données à traiter
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
 * et elle la stocke par la suite dans l'Arraylist listWebPage définie ci dessus comme attribut	
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
 * Remplace les entitées html des balises extraites, par les caractères correspondants
 * @param tag balise initiale  
 * @return tag balise sans entitées html
 */
	public String checkHTMLEntities(String tag) {
	
		tag = Pattern.compile("&agrave;").matcher(tag).replaceAll("à");
		tag = Pattern.compile("&Agrave;").matcher(tag).replaceAll("À");		
		tag = Pattern.compile("&acirc;").matcher(tag).replaceAll("â");
		tag = Pattern.compile("&Acirc;").matcher(tag).replaceAll("Â");		
		tag = Pattern.compile("&eacute;").matcher(tag).replaceAll("é");		
		tag = Pattern.compile("&Eacute;").matcher(tag).replaceAll("É");		
		tag = Pattern.compile("&egrave;").matcher(tag).replaceAll("è");		
		tag = Pattern.compile("&Egrave;").matcher(tag).replaceAll("È");		
		tag = Pattern.compile("&ecirc;").matcher(tag).replaceAll("ê");		
		tag = Pattern.compile("&Ecirc;").matcher(tag).replaceAll("Ê");
		tag = Pattern.compile("&euml;").matcher(tag).replaceAll("ë");
        tag = Pattern.compile("&Euml;").matcher(tag).replaceAll("Ë");       
        tag = Pattern.compile("&icirc;").matcher(tag).replaceAll("î");
		tag = Pattern.compile("&Icirc;").matcher(tag).replaceAll("Î");		
		tag = Pattern.compile("&iuml;").matcher(tag).replaceAll("ï");
		tag = Pattern.compile("&Iuml;").matcher(tag).replaceAll("Ï");        
		tag = Pattern.compile("&ocirc").matcher(tag).replaceAll("ô");
		tag = Pattern.compile("&Ocirc").matcher(tag).replaceAll("Ô");		
		tag = Pattern.compile("&ucirc;").matcher(tag).replaceAll("û");		
		tag = Pattern.compile("&Ucirc;").matcher(tag).replaceAll("Û");		
		tag = Pattern.compile("&oelig;").matcher(tag).replaceAll("œ");        
        tag = Pattern.compile("&OElig;").matcher(tag).replaceAll("Œ");		
		tag = Pattern.compile("&ccedil;").matcher(tag).replaceAll("ç");		 
        tag = Pattern.compile("&Ccedil;").matcher(tag).replaceAll("Ç");
        tag = Pattern.compile("Ã©").matcher(tag).replaceAll("é");
        tag = Pattern.compile("Ã¨").matcher(tag).replaceAll("è");
        tag = Pattern.compile("Ãª").matcher(tag).replaceAll("ê");
        tag = Pattern.compile("Ã´").matcher(tag).replaceAll("ô");
        tag = Pattern.compile("Ã§").matcher(tag).replaceAll("ç");
        tag = Pattern.compile("Ã¯").matcher(tag).replaceAll("ï");
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
 * @param text chaine de caractère initiale saisie par l'utilisateur
 * @return text chaine de caractères sans majuscules , sans accents ...
 */
	public String adjustText(String text) {
		
		text = text.toLowerCase();
		
		text = text.replace("à","a");
		text = text.replace("ã","a");
		text = text.replace("â","a");
		text = text.replace("é","e");
		text = text.replace("è","e");
		text = text.replace("ë","e");
		text = text.replace("î","i");
		text = text.replace("ï","i");
		text = text.replace("ô","o");
		text = text.replace("û","u");
		
		text = text.replace("ç","c");
		text =text.replace("'"," ");
		
		if(text.endsWith(" ") || text.indexOf(" ")==0) text = text.replace(" ","");

		return text;
	}
/**
 * Génère le fichier sitemap 
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
 * Génère le fichier d'indexation par le mécanisme de sérialisation
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
 * Réstaure les données sauvegardées
 * Permet de lancer la recherche au démarage de programme, sans refaire par les deux phases précédentes   
 * @param serialName nom de fichiers résultat d'indexation
 * @throws FileNotFoundException si le fichier index est introuvable (supprimé,déplacé ...)
 * @throws IOException si une erreur est survenue lors de la lecture de fichier index
 * @throws ClassNotFoundException si la classe WebPage est introuvable (supprimée, modifiée...)
 */
	public  void restorDataSer(String serialName) throws FileNotFoundException, IOException, ClassNotFoundException{

			ObjectInputStream ois = new ObjectInputStream(
										new FileInputStream(
											new File(serialName)));
			
			listWebPages = (ArrayList<WebPage>) ois.readObject();
			ois.close();
			
	}
/**
 * Permet la recherche par mot clef à partir de l’indexation préalable du contenu du jeu de données
 * @param text chaine de caractère à rechercher, saisie par l'utilisateur
 * @return result liste des pages correspondant au critère recherché
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
