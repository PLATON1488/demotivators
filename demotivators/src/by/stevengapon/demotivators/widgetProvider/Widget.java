package by.stevengapon.demotivators.widgetprovider;

import by.stevengapon.demotivators.R;
import by.stevengapon.demotivators.activity.DemotivatorsViewActivity;
import by.stevengapon.demotivators.constant.Constant;
import by.stevengapon.demotivators.service.WidgetService;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

public class Widget extends AppWidgetProvider {
	
 
	@Override
	public void onEnabled(Context context) {
		// TODO Auto-generated method stub
		
		super.onEnabled(context);
		
		
		
		Log.d("555", "enab");
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// TODO Auto-generated method stub
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		
		Log.d("777", "UUUPPPDDDAAATTTEE");
		
		for (int appWidgetId : appWidgetIds)
	        {

			 ComponentName thisWidget = new ComponentName(context,
				        Widget.class);
				    int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
				    for (int widgetId : allWidgetIds) {
				      // create some random data
				      

				      RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
				          R.layout.widget);
				  
				      // Set the text
				      
				      // Register an onClickListener
				      Intent intent = new Intent(context, WidgetService.class);

				      intent.setAction("4");
				      
				      PendingIntent pendingIntent = PendingIntent.getService(context,
				          0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
				      remoteViews.setOnClickPendingIntent(R.id.button1, pendingIntent);
				      appWidgetManager.updateAppWidget(widgetId, remoteViews);
				    }
				  
	            
	        }
		
		
		
		
		
		
	}
	
	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		// TODO Auto-generated method stub
		super.onDeleted(context, appWidgetIds);
		Log.d("444", "onDeleted");
	}
	
	@Override
	public void onDisabled(Context context) {
		// TODO Auto-generated method stub
		super.onDisabled(context);
		
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		super.onReceive(context, intent);
		 
		
		 
		 
		
		
		    
	}
}
