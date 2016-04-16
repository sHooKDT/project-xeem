package shook.xeem.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import shook.xeem.R;
import shook.xeem.XeemApiService;
import shook.xeem.XeemAuthService;
import shook.xeem.activities.BlankEditActivity;
import shook.xeem.list_adapters.BlankListAdapter;
import shook.xeem.objects.BlankObject;


public class MainActivity extends Activity {

    static final int EDIT_BLANK_REQUEST = 27;

    static private List<BlankObject> loadedBlankList = new ArrayList<BlankObject>();
    static private BlankListAdapter blankListAdapter;
    static private ListView blankListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(MainActivity.this, "Hello, " + XeemAuthService.getAccount().getDisplayName() , Toast.LENGTH_SHORT).show();

        XeemApiService.updateBlanks();
        blankListView = (ListView) findViewById(R.id.blankListView);
        blankListAdapter = new BlankListAdapter(this, loadedBlankList);
        blankListView.setAdapter(blankListAdapter);
    }

    @Override
    protected void onResume() {
        XeemApiService.updateBlanks();
        super.onResume();
        Log.d("MYTAG", "Main activity resumed");
    }

    public static void setBlankList(List<BlankObject> _blanks) {
        blankListAdapter.reload(_blanks);
        blankListAdapter.notifyDataSetChanged();}

    public void addBlankClick (View v) {
        Intent editIntent = new Intent(this, BlankEditActivity.class);
        editIntent.setAction("ADD");
        startActivityForResult(editIntent, EDIT_BLANK_REQUEST);
    }

    protected void onActivityResult (int requestCode, int resultCode, Intent result) {
        // Edited blank callback
        if (requestCode == EDIT_BLANK_REQUEST) {
            final BlankObject _blank = BlankObject.fromJSON(result.getStringExtra("edited_blank"));
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Post this blank?");
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Log.d("MYTAG", "Blank posting declined");
                }
            });
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    XeemApiService.postBlank(_blank);
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

}
