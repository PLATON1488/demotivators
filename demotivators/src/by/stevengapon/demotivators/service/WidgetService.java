package by.stevengapon.demotivators.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import by.stevengapon.demotivators.R;
import by.stevengapon.demotivators.activity.DemotivatorsViewActivity;
import by.stevengapon.demotivators.constant.Constant;
import by.stevengapon.demotivators.parser.Parser;
import by.stevengapon.demotivators.widgetprovider.Widget;
import android.app.IntentService;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.RemoteViews;

public class WidgetService extends IntentService {

  private ComponentName componentName;
  private RemoteViews remoteViews;
  private AppWidgetManager appWidgetManager ;
  private Intent activity_intent ;
  private HashMap<String, Object> data;
  
	
	public WidgetService() {
		super("WidgetService");
		Log.d("444","create" );
		
	}
	
	@Override
		public void onStart(Intent intent, int startId) {
			// TODO Auto-generated method stub
			super.onStart(intent, startId);
		}

	@Override
	protected void onHandleIntent(Intent intent) {
	         
		   
		   
		  
		   if(intent.getAction().equalsIgnoreCase("4")){
		    
		    
		    updateWidget();
			
			
		    
		  }
		   
		   if(intent.getAction().equalsIgnoreCase(Constant.COMMENTS_PREV)){
			   
			 String url_comment ;
			 url_comment = intent.getStringExtra("url_comment");
			   
			 intent = new Intent(Constant.COMMENTS_PREV);
			 ArrayList<HashMap<String, Object>>  ee ;
			 
			 ee = Parser.parserJSOn( Parser.loadJSOn(url_comment));
			 intent.putExtra("123", ee);
			 sendBroadcast(intent);
		  }
		   
		   
		  
		
	
	 
		 	
	 
	}
		public void updateWidget(){
		 
			Bitmap image ;
		    
            
		    data = new HashMap<String, Object>();
		    data = Parser.getDemotivator(); 
		    image = (Bitmap) data.get("image");
		    
		    
			componentName = new ComponentName(this,Widget.class);
		    remoteViews = new RemoteViews(this.getApplicationContext().getPackageName(),R.layout.widget);
			appWidgetManager =	AppWidgetManager.getInstance(this);
			remoteViews.setImageViewBitmap(R.id.img ,image ); 
			
			activity_intent = new Intent(this.getApplicationContext(), DemotivatorsViewActivity.class);
			activity_intent.putExtra("data", data );
			activity_intent.setAction(Constant.VIEW_DEMOTIVATOR);
            PendingIntent pendingIntent = PendingIntent.getActivity(this.getApplicationContext(), 0, activity_intent,PendingIntent.FLAG_UPDATE_CURRENT);
			remoteViews.setOnClickPendingIntent(R.id.img, pendingIntent);
			
            appWidgetManager.updateAppWidget(componentName, remoteViews);
           
			
		}
	
	@Override
		public void onDestroy() {
			// TODO Auto-generated method stub
			super.onDestroy();
			Log.d("444" , "Destroy");
		}
}
