package spider.task_3;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class ListAppWidget extends AppWidgetProvider {
    static Context sContext = null;
    public static String WIDGET_BUTTON = "WIDGET_BUTTON";
    static AppWidgetManager sappWidgetManager = null;
    static int[] sappWidgetIds ;
    static void updateAppWidget(){
        if(sContext!=null && sappWidgetManager!=null){
            for (int sappWidgetId : sappWidgetIds) {
                //updateAppWidget(sContext, sappWidgetManager, sappWidgetId);
                Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, sappWidgetId);
                sContext.sendBroadcast(intent);

                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(sContext);
                int appWidgetIds[] = appWidgetManager.getAppWidgetIds(
                        new ComponentName(sContext, ListAppWidget.class));
                appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.lv_widget);
            }
        }else{
            System.out.println("sContext is null!!!");
        }
    }


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        if(sContext==null){
            sContext = context;
            sappWidgetManager = appWidgetManager;
        }



        Config.database = new Database(context);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.list_app_widget);


        /*Intent refreshIntent = new Intent(WIDGET_BUTTON);
        PendingIntent pendingIntentRefresh = PendingIntent.getBroadcast(context, 0, refreshIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.btn_widget_refresh, pendingIntentRefresh );
        if(pendingIntentRefresh.equals(WIDGET_BUTTON)) {
            updateAppWidget();
        }*/
        //appWidgetManager.updateAppWidget(appWidgetId, views);



        Intent intentSync = new Intent(context, ListAppWidget.class);
        intentSync.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE); //You need to specify the action for the intent. Right now that intent is doing nothing for there is no action to be broadcasted.
        PendingIntent pendingSync = PendingIntent.getBroadcast(context,0, intentSync, PendingIntent.FLAG_UPDATE_CURRENT); //You need to specify a proper flag for the intent. Or else the intent will become deleted.
        views.setOnClickPendingIntent(R.id.btn_widget_refresh,pendingSync);


        Intent configIntent = new Intent(context, Add.class);
        PendingIntent configPendingIntent = PendingIntent.getActivity(context, 0, configIntent, 0);
        views.setOnClickPendingIntent(R.id.btn_widget_add, configPendingIntent);

        //appWidgetManager.updateAppWidget(appWidgetId, views);

        Intent intent = new Intent(context, MyWidgetRemoteViewsService.class);
        //views.setRemoteAdapter(R.id.lv_widget, intent);
        views.setRemoteAdapter(appWidgetId, R.id.lv_widget,intent);
        appWidgetManager.updateAppWidget(appWidgetId, views);

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {


        //if(sContext==null){
            sappWidgetIds = appWidgetIds;
        //}
        Config.database = new Database(context);
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);


        }
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.lv_widget);
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.list_app_widget);


        Intent refreshIntent = new Intent(WIDGET_BUTTON);
        PendingIntent pendingIntentRefresh = PendingIntent.getBroadcast(context, 0, refreshIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.btn_widget_refresh, pendingIntentRefresh );
        if(pendingIntentRefresh.equals(WIDGET_BUTTON)) {
            updateAppWidget();
        }
        super.onEnabled(context);
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        super.onReceive(context, intent);

        Bundle extras = intent.getExtras();
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName thisAppWidget = new ComponentName(context.getPackageName(), ListAppWidget.class.getName());
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidget);
        if(sContext!=null){
            appWidgetIds = sappWidgetIds;
        }
        this.onUpdate(context, AppWidgetManager.getInstance(context), appWidgetIds);

    }
}

