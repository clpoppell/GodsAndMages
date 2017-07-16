package gods_and_mages_engine;

import android.app.Application;
import android.content.Context;

/**
 * Convenience class for providing a {@code Context} object.
 */
public class App extends Application{
	public static Context context;
	
	@Override public void onCreate(){
		super.onCreate();
		context= getApplicationContext();
	}
}
