package shook.xeem;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import shook.xeem.list_adapters.BlankListAdapter;


public class MainActivity extends Activity {

    static final int EDIT_BLANK_REQUEST = 27;

    static private List<BlankObject> loadedBlankList = new ArrayList<BlankObject>();
    static private BlankListAdapter blankListAdapter;
    static private ListView blankListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Log.d("MYTAG",String.format("Main activity started, loadedBlankList: %d, blankListAdapter: %d, blankListView: %d", loadedBlankList.size(), blankListAdapter.getCount(), blankListView.getCount()));
        XeemApiService.updateBlanks();
//        Log.d("MYTAG",String.format("updateBlank() requested, loadedBlankList: %d, blankListAdapter: %d, blankListView: %d", loadedBlankList.size(), blankListAdapter.getCount(), blankListView.getCount()));
        blankListView = (ListView) findViewById(R.id.blankListView);
        blankListAdapter = new BlankListAdapter(this, loadedBlankList);
        blankListView.setAdapter(blankListAdapter);
        Log.d("MYTAG",String.format("Adapter initialised, loadedBlankList: %d, blankListAdapter: %d, blankListView: %d", loadedBlankList.size(), blankListAdapter.getCount(), blankListView.getCount()));

    }

    public static void setBlankList(List<BlankObject> _blanks) {
        blankListAdapter.reload(_blanks);
        blankListAdapter.notifyDataSetChanged();
        Log.d("MYTAG",String.format("Response success and data loaded (?), loadedBlankList: %d, blankListAdapter: %d, blankListView: %d", loadedBlankList.size(), blankListAdapter.getCount(), blankListView.getCount()));
    }

    public void addBlankClick (View v) {

//        BlankObject blankToEdit = BlankObject.generateSome();
        Intent editIntent = new Intent(this, BlankEditActivity.class);
        editIntent.setAction("ADD");
        startActivityForResult(editIntent, EDIT_BLANK_REQUEST);
    }

    protected void onActivityResult (int requestCode, int resultCode, Intent result) {
        // Edited blank callback
        if (requestCode == EDIT_BLANK_REQUEST) {
            BlankObject _blank = BlankObject.fromJSON(result.getStringExtra("edited_blank"));
            try {
                Log.d("MYTAG", String.format("Blank edited: %s", _blank.toJSON()));
            } catch (Exception e) {e.printStackTrace();}
        }
    }

}
