package shook.xeem.activities;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
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
import shook.xeem.list_adapters.BlankListRecyclerAdapter;
import shook.xeem.objects.BlankObject;


public class MainActivity extends AppCompatActivity {

    static final int EDIT_BLANK_REQUEST = 27;
    static final int ADD_BLANK_REQUEST = 28;
    static final int PASS_BLANK_REQUEST = 29;

    static private List<BlankObject> loadedBlankList = new ArrayList<BlankObject>();
//    static private BlankListAdapter blankListAdapter;
    static private BlankListRecyclerAdapter myadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(MainActivity.this, "Hello, " + XeemAuthService.getAccount().getDisplayName() , Toast.LENGTH_SHORT).show();

//        ListView blankListView = (ListView) findViewById(R.id.blankListView);
//        blankListAdapter = new BlankListAdapter(this, loadedBlankList);
//        blankListView.setAdapter(blankListAdapter);

        RecyclerView blankListView = (RecyclerView) findViewById(R.id.blankListView);
        myadapter = new BlankListRecyclerAdapter(this, loadedBlankList);
        blankListView.setAdapter(myadapter);
        blankListView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.somemenu, menu);
        menu.findItem(R.id.addBlankButton).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                addBlankClick(null);
                return true;
            }
        });
        return true;
    }

    @Override
    protected void onResume() {
        XeemApiService.updateBlanks();
        super.onResume();
        Log.d("MYTAG", "[Activity] Main.onResume()");
    }

    public static void setBlankList(List<BlankObject> _blanks) {
//        blankListAdapter.reload(_blanks);
//        blankListAdapter.notifyDataSetChanged();
        myadapter.reload(_blanks);
        myadapter.notifyDataSetChanged();
     }

    public void addBlankClick (@Nullable View v) {
        Intent editIntent = new Intent(this, BlankEditActivity.class);
        editIntent.setAction("ADD");
        startActivityForResult(editIntent, ADD_BLANK_REQUEST);
    }

    public void deleteBlankClick (int position) {
        XeemApiService.deleteBlank(myadapter.getItem(position));
        XeemApiService.updateBlanks();
    }

    public void editBlankClick (int position) {
        Intent editBlankIntent = new Intent(this, BlankEditActivity.class);
        editBlankIntent.setAction("EDIT");
        editBlankIntent.putExtra("blank_to_edit", myadapter.getItem(position).toJSON());
        startActivityForResult(editBlankIntent, EDIT_BLANK_REQUEST);
    }

    public void passBlankClick (int position) {
        Intent passBlankIntent = new Intent(this, PassTestActivity.class);
        passBlankIntent.setAction("PASS");
        passBlankIntent.putExtra("blank_to_pass", myadapter.getItem(position).toJSON());
        startActivityForResult(passBlankIntent, PASS_BLANK_REQUEST);
    }

    protected void onActivityResult (int requestCode, int resultCode, Intent result) {
        // Edited blank callback
        if (requestCode == ADD_BLANK_REQUEST) {
            final BlankObject _blank = BlankObject.fromJSON(result.getStringExtra("edited_blank"));
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Post this blank?");
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Log.d("MYTAG", "[POSTING] Declined by user");
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
        } else if (requestCode == EDIT_BLANK_REQUEST) {
            final BlankObject _blank = BlankObject.fromJSON(result.getStringExtra("edited_blank"));
            Log.d("MYTAG", "tried to send: " + _blank);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Replace this blank?");
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Log.d("MYTAG", "[POSTING] Declined by user");
                }
            });
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    XeemApiService.editBlank(_blank);
                    XeemApiService.updateBlanks();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        } else if (requestCode == PASS_BLANK_REQUEST) {
            Log.d("MYTAG", "You passed the test, Nice!");
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }

}
