package projet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
/**
 * Cette classe permet de lancer l'application en mode console
 * Elle a q'une seul méthode: main
 * @author Rassim
 */
public class CMLauncher {

	
	public static void main(String[] args) {
		
		DataExplorer DExp;
		String sitemapName = "sitemap.xml";
        String serialName = "data.ser";
        String rootPath = null;
        String text = null;
        StringBuffer mainArgs = new StringBuffer();
     
        for (int i = 0; i < args.length; i++) {
        	
            switch (args[i]) {
            
                case "-d": {
                    File f = new File(args[i+1].substring(1));
                    try{
                    	if(f.exists() && f.isDirectory()){
                    		rootPath=args[i+1].substring(1);
                            mainArgs.append("-d");
                    	}else throw new FileNotFoundException("Error!Invalid rootPath,data not found.");
                    	
                    }catch (FileNotFoundException e) {
            			System.out.println(e.getMessage());            			
            		}
                }
                break;
                
                case "-o": {
                    sitemapName = args[i+1];
                    mainArgs.append("-o");
                }
                break;
                
                case "-c": {
                    serialName = args[i+1];
                    mainArgs.append("-c");
                }
                break;
                
                case "-s": {
                	 StringBuffer textBuffer = new StringBuffer();
                     for(int k=i+1;k<args.length;k++){
                         textBuffer.append(args[k]);
                     }
                     text = textBuffer.toString();
                     mainArgs.append("-s");
                }
                break;
            }
        }
        
        DExp = new DataExplorer(rootPath);
        
        if (mainArgs.toString().contains("-d") && mainArgs.toString().contains("-o")) {
        	DExp.generatSiteMapXML(sitemapName);
        }
        if (mainArgs.toString().contains("-d") && mainArgs.toString().contains("-c")) {
        	DExp.generatIndexFileSer(serialName);
        }
        if (mainArgs.toString().contains("-s")) {
       		try {
				DExp.restorDataSer(serialName);
			} catch (FileNotFoundException e) {
				System.out.println("index file not found!");
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				System.out.println("The class of serialized objects is not Found");
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("error while reading index file");
				e.printStackTrace();
			}
       		
       		ArrayList<WebPage> result = DExp.search(text);
       		if(result.isEmpty()) System.out.println("Sorry! but we can't find any pages that contain the text you entered.");
       		else {
       			for(int i=0;i<result.size();i++) {
        			System.out.println(result.get(i).getLink());
        		}
       		}
        }		
	}
}
