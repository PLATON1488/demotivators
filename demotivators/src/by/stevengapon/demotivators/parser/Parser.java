package by.stevengapon.demotivators.parser;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class Parser {

	  private static  final String CONSTATNT_URL = "http://demotivators.to/random/";
	  private static TagNode rootNode;
	  
	public Parser() {
		
	}
	
	
	public static Bitmap getImageRandom() {
		
	   Log.d("666", "666");
		     String imgUrl ="";
		    //Создаём объект HtmlCleaner
		    HtmlCleaner cleaner = new HtmlCleaner();
		    //Загружаем html код сайта
		    try {
				rootNode = cleaner.clean(new URL(CONSTATNT_URL));
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    Log.d("666", "666");
		    //Выбираем все ссылки
		    TagNode linkElements[] = rootNode.getElementsByName("a", true);
		    Log.d("666", "666");
		    
		    for (int i = 0; linkElements != null && i < linkElements.length; i++)
		    {
		    	String classType = linkElements[i].getAttributeByName("target");
		        //если атрибут есть и он эквивалентен искомому, то добавляем в список
		        if (classType != null && classType.equals("_blank"))
		        {
		          Log.d("555","http://demotivators.to" +linkElements[i].getAttributeByName("href").toString());
		        }
		        
		    	
		    	TagNode  imgElements[] =	linkElements[i].getElementsByName("img",  true);
		    	
		    	if(imgElements.length==1){
		    		imgUrl ="http://demotivators.to" + imgElements[0].getAttributeByName("src");
		    		return loadImage(imgUrl);
		    		
		    	}
	 
		    }
		    return null;
		}
	
	  public static Bitmap loadImage(String url){
		  
		  if(url!=""){
			  
	          InputStream input = null;
              try {
                     URL urlConn = new URL(url);
                    input = urlConn.openStream(); 
              }
              catch (MalformedURLException e) {
             	e.printStackTrace();
              }
                  catch (IOException e) {
          	e.printStackTrace();
                  }
                  
              Bitmap image = BitmapFactory.decodeStream(input);
              
               return image; 	 
		  
		  }
		  
		  return null;
	  }
}