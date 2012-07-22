import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;


public class MainActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		SQLFunctions entry = new SQLFunctions(this); // start class
		entry.open(); //open database to read/write/update etc
		entry.createEntry("test", "test"); //insert entry.
		String name = entry.getName(1); // get Name where database table ID = 1
		setName(1, "New Name"); // update name where ID = 1
		Integer highestID = entry.getHighestId(); //get highest ID of database
		entry.close(); //close database
	}

}