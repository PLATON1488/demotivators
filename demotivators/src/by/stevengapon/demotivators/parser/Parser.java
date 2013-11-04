package by.stevengapon.demotivators.parser;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.json.JSONArray;
import org.json.JSONException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class Parser {

	  private static  final String CONSTATNT_URL = "http://demotivators.to/random/";
	  private static TagNode rootNode;
	  
	public Parser() {
		
	}
	
	
	public static HashMap<String, Object> getDemotivator()  {
		
		    HashMap<String, Object> data = new HashMap<String, Object>();
		
			TagNode rootNode = null;
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
			
		    
		    TagNode[]  linkElements = null ;
		    linkElements  = rootNode.getElementsByName("div", true);
		    
		    for (int i = 0; linkElements != null && i < linkElements.length; i++)
		    {
		    	String classType = linkElements[i].getAttributeByName("class");
		    	
		        //если атрибут есть и он эквивалентен искомому, то добавляем в список
		        if (classType != null && classType.equals("poster thumb share"))
		        {
		        	
		        	
		          data.put("id",linkElements[i].getAttributeByName("data-poster-id"));
		          data.put("name",linkElements[i].getAttributeByName("data-sharer-title")
		        		  + linkElements[i].getAttributeByName("data-sharer-text"));
		      
		          Log.d("777", "111" + linkElements[i].getAttributeByName("data-sharer-title"));
		          data.put("url",linkElements[i].getAttributeByName("data-sharer-url"));
		          data.put("image",loadImage(linkElements[i].getAttributeByName("data-sharer-image")));
		          
		          break;       
		          }    
		  }
		    linkElements  = rootNode.getElementsByName("span", true);
		    
		    for (int i = 0; linkElements != null && i < linkElements.length; i++)
		    {
		    	String classType = linkElements[i].getAttributeByName("class");
		    
		        //если атрибут есть и он эквивалентен искомому, то добавляем в список
		        if (classType != null && classType.equals("label"))
		        {
		         data.put("count_comments",linkElements[i].getText());
		        }   
		        
		        if (classType != null && classType.equals("label rating"))
		        {
		          data.put("rating",linkElements[i].getText());
		          
		          break;
		        } 
		        
		       
		  }
		    
		    
		    return data;
		}
		
	
	  public static Bitmap loadImage(String url) {
		  
		  	if(url!="") 
		  {
			  
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
              
              byte[] arraybyte_image = null;
			try {
				arraybyte_image = IOUtils.toByteArray(input);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
             
			return BitmapFactory.decodeByteArray(arraybyte_image , 0, arraybyte_image.length);	   
		    
		  }
		  	
		  return null;
	  }
	  
	  
	  public static String   loadJSOn (String url){
	  
		  String result = "";
		  HttpClient  httpclient =  new DefaultHttpClient();
	      //HttpGet httpget = new HttpGet(url);
		  HttpGet httpget = new HttpGet(url);
	      String Tag = "666";
	      try {
	    	     Log.d(Tag, "запрос отправлен");
	    	     HttpResponse response = httpclient.execute(httpget);
	    	     HttpEntity httpEntity =response.getEntity();
	    	     result = EntityUtils.toString(httpEntity, "UTF-8");
	    	     Log.d(Tag, result);     
	    	}
	    	 catch (ClientProtocolException e) {
	    	Log.d(Tag, "ошибочка");
	    	} 
	    	catch (IOException e) {
	    	Log.d(Tag, "запрос не отправлен");
	    	}
	    
	      
	        return result;
	      
	      }
	      
	      
	 	  
	  
	public static  ArrayList<HashMap<String, Object>>  parserJSOn (String json){
		
		ArrayList<HashMap<String, Object>>  data  = new  ArrayList<HashMap<String,Object>>() ;
		HashMap<String, Object>  dataHM; 
		JSONObjectWrapper  jsonObj =  new JSONObjectWrapper(json);
		JSONArray commentsArray = jsonObj.getJSONArray("comments");
		JSONObjectWrapper  jsonObjCom , jsonObjUser;
		
		try {
		
		for(int i = 0 ; i < commentsArray.length() ; i++ ){
		     
			dataHM = new HashMap<String, Object>();
			
			jsonObjCom = new JSONObjectWrapper(commentsArray.getJSONObject(i));
			dataHM.put("text", jsonObjCom.getString("t"));
			dataHM.put("to", jsonObjCom.getString("to"));
			dataHM.put("slug", jsonObjCom.getString("slug"));
			dataHM.put("fslug", jsonObjCom.getString("fslug"));
			
			jsonObjUser = jsonObjCom.getJSONObject("u");
			dataHM.put("name", jsonObjUser.getString("name"));
			dataHM.put("image", loadImage("http://demotivators.to" + jsonObjUser.getString("a")));
			
			
			
			
			 data.add(dataHM);
			
		}
		
		 dataHM = new HashMap<String, Object>();
		 dataHM.put("hasPrev", jsonObj.getJSONObject("pager").getString("hasPrev"));
		 dataHM.put("hasNext", jsonObj.getJSONObject("pager").getString("hasNext"));
		 data.add(dataHM);  
		
		
		}
		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return data;
	}
	  
	  
	  
	    public static int count_symbol (String  slug_string){
	    	
	    	int count = 0 ; 
	    	for(int i = 0 ; i <slug_string.length() ; i++){
	    		
	    		if (slug_string.charAt(i) == '/' ){
	    			count++;
	    		}
	    		
	    	}
	    	
	    	return count;
	    	
	    }
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
}