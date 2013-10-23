package by.stevengapon.demotivators.service;

import by.stevengapon.demotivators.R;
import by.stevengapon.demotivators.parser.Parser;
import by.stevengapon.demotivators.widgetProvider.Widget;
import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.widget.RemoteViews;

public class UpdateWidgetService extends IntentService {

	
  private ComponentName componentName;
  private RemoteViews remoteViews;
  private AppWidgetManager appWidgetManager ;
  private Intent serviceIntent ;
	
	public UpdateWidgetService() {
		super("UpdateWidgetService");
		
	}

	@Override
	protected void onHandleIntent(Intent intent) {
	
		
	 componentName = new ComponentName(this,Widget.class);
     remoteViews = new RemoteViews(this.getApplicationContext().getPackageName(),R.layout.widget);
	 appWidgetManager =	AppWidgetManager.getInstance(this);

	 remoteViews.setImageViewBitmap(R.id.img , Parser.getImageRandom());
	 
	appWidgetManager.updateAppWidget(componentName, remoteViews);

	 
	}

}
