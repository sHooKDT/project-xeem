package shook.xeem.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import shook.xeem.BlankListHolder;
import shook.xeem.R;
import shook.xeem.TestResultFragment;
import shook.xeem.XeemApiService;
import shook.xeem.XeemAuthService;
import shook.xeem.list_adapters.BlankListRecyclerAdapter;
import shook.xeem.objects.BlankObject;
import shook.xeem.objects.TestResult;


public class MainActivity extends AppCompatActivity implements BlankListHolder {

    static final int EDIT_BLANK_REQUEST = 27;
    static final int ADD_BLANK_REQUEST = 28;
    static final int PASS_BLANK_REQUEST = 29;

    static private LinkedList<BlankObject> loadedBlankList = new LinkedList<>();
    static private BlankListRecyclerAdapter blankListAdapter;

    private XeemApiService apiService = new XeemApiService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(MainActivity.this, "Hello, " + XeemAuthService.getAccount().getDisplayName() , Toast.LENGTH_SHORT).show();


        RecyclerView blankListView = (RecyclerView) findViewById(R.id.blankListView);
        blankListAdapter = new BlankListRecyclerAdapter(this);
        if (blankListView != null) {
            blankListView.setAdapter(blankListAdapter);
            blankListView.setLayoutManager(new LinearLayoutManager(this));
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_action_bar, menu);
        menu.findItem(R.id.addBlankButton).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                addBlankClick(null);
                return true;
            }
        });
        menu.findItem(R.id.updateButton).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                apiService.updateBlanks(updateCallback);
                return true;
            }
        });
        return true;
    }

    @Override
    protected void onResume() {
        apiService.updateBlanks(updateCallback);
        super.onResume();
    }

    private Callback<XeemApiService.blankListResponse> updateCallback = new Callback<XeemApiService.blankListResponse>() {
        @Override
        public void onResponse(Call<XeemApiService.blankListResponse> call, Response<XeemApiService.blankListResponse> response) {
            if (response.code() == 200) {
                Log.d("XEEMDBG", "Blanks updated");
                loadedBlankList = response.body()._items;
                blankListAdapter.reload();
            } else {
                Log.d("XEEMDBG", "Blanks update failed");
            }
        }

        @Override
        public void onFailure(Call<XeemApiService.blankListResponse> call, Throwable t) {

        }
    };

    public void addBlankClick (@Nullable View v) {
        Intent editIntent = new Intent(this, BlankEditActivity.class);
        editIntent.setAction("ADD");
        startActivityForResult(editIntent, ADD_BLANK_REQUEST);
    }

    public void deleteBlankClick (int position) {
        if (Objects.equals(loadedBlankList.get(position).getAuthor(), XeemAuthService.getAccount().getId())) {
            apiService.deleteBlank(loadedBlankList.get(position));
        } else {
            Toast.makeText(this, "Вы не можете удалить этот тест", Toast.LENGTH_SHORT).show();
        }
        apiService.updateBlanks(updateCallback);
    }

    public void editBlankClick (int position) {
        Intent editBlankIntent = new Intent(this, BlankEditActivity.class);
        editBlankIntent.setAction("EDIT");
        editBlankIntent.putExtra("blank_to_edit", blankListAdapter.getItem(position).toJSON());
        startActivityForResult(editBlankIntent, EDIT_BLANK_REQUEST);
    }

    public void passBlankClick (int position) {
        Intent passBlankIntent = new Intent(this, PassTestActivity.class);
        passBlankIntent.setAction("PASS");
        passBlankIntent.putExtra("blank_to_pass", blankListAdapter.getItem(position).toJSON());
        startActivityForResult(passBlankIntent, PASS_BLANK_REQUEST);
    }

    public LinkedList<BlankObject> getBlankList() {
        return loadedBlankList;
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
                    Log.d("XEEMDBG", "[POSTING] Declined by user");
                }
            });
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    apiService.postBlank(_blank);
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        } else if (requestCode == EDIT_BLANK_REQUEST) {
            final BlankObject _blank = BlankObject.fromJSON(result.getStringExtra("edited_blank"));
            Log.d("XEEMDBG", "tried to send: " + _blank);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Replace this blank?");
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Log.d("XEEMDBG", "[POSTING] Declined by user");
                }
            });
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Log.d("XEEMDBG", "[PATCH] Blank sent to api class");
                    apiService.editBlank(_blank);
                    apiService.updateBlanks(updateCallback);
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        } else if (requestCode == PASS_BLANK_REQUEST) {
            final TestResult _result = TestResult.fromJSON(result.getStringExtra("result"));
            TestResultFragment resultFragment = new TestResultFragment();
            Bundle data = new Bundle();
            data.putString("result", _result.toJSON());
            resultFragment.setArguments(data);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.result_fragment_container, resultFragment)
                    .addToBackStack("result")
                    .commitAllowingStateLoss();
            FrameLayout fragment_frame = (FrameLayout) findViewById(R.id.result_fragment_container);
            fragment_frame.setVisibility(View.VISIBLE);
        }
    }

}
