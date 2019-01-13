package projet;

import java.io.Serializable;
import java.util.Date;
/**
 * Cette classe permet de repr�senter les �l�ments principaux d'un fichier html, qui sont n�cessaires pour r�aliser l'application  
 * @author Rassem
 */

public class WebPage implements Serializable
{
/**
 * repr�sente le lien de la page web	
 */
	private String link;
/**
 * repr�sente le contenu des balises title et h1 de fichier html
 */
	private String tagContent;
/**
 *repr�sente la date de derni�re modification  
 */
	private Date lastmod;
/**
 * Constructeur
 * initialise les 3 attributs par les valeurs entr�es par l'utilisarteur
 * @param link lien de la page web
 * @param tagContent contenu des balises title et h1
 * @param lastmod date de derni�re modification
 */
	public WebPage(String link,String tagContent,Date lastmod) {
		this.link = link;
		this.lastmod = lastmod;
		this.tagContent = tagContent;
	}
/**	
 * @return link le lien de la page web
 */
	public String getLink() {
		return link;
	}
/**
 * @return tagContent le contenu des balises title et h1 
 */
	public String getTagContent() {
		return tagContent;
	}
/**
 * @return la date de derni�re modification
 */
	public Date getLastmod() {
		return lastmod;
	}
/**
 * @return une chaine de caract�res repr�sentant une instance de cette classe
 */
	public String toString() {
		return "link : " + link + "\nlastmod : " + lastmod + "\npriority : 0.5"
				+ "\nchangfreq : Monthly "+"\n";
	}
	
}	
