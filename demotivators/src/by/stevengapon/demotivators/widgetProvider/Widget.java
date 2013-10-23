package by.stevengapon.demotivators.widgetProvider;

import by.stevengapon.demotivators.service.UpdateWidgetService;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class Widget extends AppWidgetProvider {
	final String UPDATE_ALL_WIDGETS = "update_all_widgets";
 
	@Override
	public void onEnabled(Context context) {
		// TODO Auto-generated method stub
		
		super.onEnabled(context);
		
		Intent intent = new Intent(context, Widget.class);
	    intent.setAction(UPDATE_ALL_WIDGETS);
	    PendingIntent pIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
	    AlarmManager alarmManager = (AlarmManager) context
	        .getSystemService(Context.ALARM_SERVICE);
	    alarmManager.setRepeating(AlarmManager.RTC, System.currentTimeMillis(),
	        10000, pIntent);
		
		
		Log.d("555", "enab");
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// TODO Auto-generated method stub
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		
		
		context.startService(new Intent(context,UpdateWidgetService.class));
	}
	
	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		// TODO Auto-generated method stub
		super.onDeleted(context, appWidgetIds);
	}
	
	@Override
	public void onDisabled(Context context) {
		// TODO Auto-generated method stub
		super.onDisabled(context);
		
		Intent intent = new Intent(context, Widget.class);
	    intent.setAction(UPDATE_ALL_WIDGETS);
	    PendingIntent pIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
	    AlarmManager alarmManager = (AlarmManager) context
	        .getSystemService(Context.ALARM_SERVICE);
	    alarmManager.cancel(pIntent);
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		super.onReceive(context, intent);
		
		
		 if (intent.getAction().equalsIgnoreCase(UPDATE_ALL_WIDGETS)) {
		      ComponentName thisAppWidget = new ComponentName(
		          context.getPackageName(), getClass().getName());
		      AppWidgetManager appWidgetManager = AppWidgetManager
		          .getInstance(context);
		      int ids[] = appWidgetManager.getAppWidgetIds(thisAppWidget);
		      
		        onUpdate(context, appWidgetManager, ids);
		      }
		    
	}
}
