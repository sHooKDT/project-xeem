package shook.xeem.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
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

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.Objects;

import shook.xeem.BlankList;
import shook.xeem.R;
import shook.xeem.UserList;
import shook.xeem.fragments.TestResultFragment;
import shook.xeem.interfaces.BlankListHolder;
import shook.xeem.interfaces.BlankUpdateListener;
import shook.xeem.list_adapters.BlankListRecyclerAdapter;
import shook.xeem.objects.BlankObject;
import shook.xeem.objects.TestResult;
import shook.xeem.services.XeemApiService;
import shook.xeem.services.XeemAuthService;


public class MainActivity extends AppCompatActivity implements BlankListHolder {

    static final int EDIT_BLANK_REQUEST = 27;
    static final int ADD_BLANK_REQUEST = 28;
    static final int PASS_BLANK_REQUEST = 29;

    static final String BLANKS_CACHE_FILE_NAME = "blanks_cache";

    ProgressDialog loadingIndicator;

    static private BlankList loadedBlankList = new BlankList();
    static private BlankListRecyclerAdapter blankListAdapter;

    private XeemApiService apiService = new XeemApiService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadingIndicator = new ProgressDialog(this);

        if (XeemAuthService.isLogged())
            Toast.makeText(MainActivity.this, "Привет, " + XeemAuthService.getCachedUsername(), Toast.LENGTH_SHORT).show();
        else Toast.makeText(MainActivity.this, "Вы не авторизированы", Toast.LENGTH_SHORT).show();

        apiService.registerBlankUpdateListener(updateListener);
        apiService.loadBlanks();
        apiService.loadUsers();

        if (!XeemAuthService.isOnline()) {
            Log.d("XEEMDBG", "[CACHE] Not online, trying to load cache");
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(openFileInput(BLANKS_CACHE_FILE_NAME)));
                String line;
                if ((line = reader.readLine()) != null) {
                    Log.d("XEEMDBG", "[CACHE] Loaded: " + line);
                    loadedBlankList = (new Gson()).fromJson(line, BlankList.class);
                    blankListAdapter.reload();
                } else Log.d("XEEMDBG", "[CACHE] No cache");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (!hasBlanks() || !hasUsers()) {
            loadingIndicator.setMessage("Loading some blanks");
            loadingIndicator.setCancelable(false);
            loadingIndicator.show();
        }

        // If no internet, ask user to continue
        if (!XeemAuthService.isOnline()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Уведомление");
            builder.setMessage("Вы не подключены к интернету. Без интернета нельзя редактировать и удалять бланки, а также недоступно сохранение результатов.");
            builder.setPositiveButton("Ок", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            builder.create().show();
        }

        RecyclerView blankListView = (RecyclerView) findViewById(R.id.blankListView);
        blankListAdapter = new BlankListRecyclerAdapter(this);
        if (blankListView != null) {
            blankListView.setAdapter(blankListAdapter);
            blankListView.setLayoutManager(new LinearLayoutManager(this));
        }

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
                apiService.loadBlanks();
                return true;
            }
        });
        return true;
    }


    private BlankUpdateListener updateListener = new BlankUpdateListener() {
        @Override
        public void onUpdate(BlankList _blanks) {
            if (_blanks != null) {
                try {
                    Log.d("XEEMDBG", "[CACHE] Caching blanks");
                    FileOutputStream fos = openFileOutput(BLANKS_CACHE_FILE_NAME, Context.MODE_PRIVATE);
                    fos.write(_blanks.toJSON().getBytes());
                    Log.d("XEEMDBG", "[CACHE] Written: " + _blanks.toJSON());
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                loadedBlankList = _blanks;
            }
            if (hasUsers() && hasBlanks()) blankListAdapter.reload();
        }
    };

    public void addBlankClick(@Nullable View v) {
        Intent editIntent = new Intent(this, BlankEditActivity.class);
        editIntent.setAction("ADD");
        startActivityForResult(editIntent, ADD_BLANK_REQUEST);
    }

    public void deleteBlankClick(int position) {
        if (Objects.equals(loadedBlankList.get(position).getAuthor(), XeemAuthService.getUserId())) {
            apiService.deleteBlank(loadedBlankList.get(position));
        } else {
            Toast.makeText(this, "Вы не можете удалить этот тест", Toast.LENGTH_SHORT).show();
        }
        apiService.loadBlanks();
    }

    public void editBlankClick(int position) {
        Intent editBlankIntent = new Intent(this, BlankEditActivity.class);
        editBlankIntent.setAction("EDIT");
        editBlankIntent.putExtra("blank_to_edit", blankListAdapter.getItem(position).toJSON());
        startActivityForResult(editBlankIntent, EDIT_BLANK_REQUEST);
    }

    public void passBlankClick(int position) {
        Intent passBlankIntent = new Intent(this, PassTestActivity.class);
        passBlankIntent.setAction("PASS");
        passBlankIntent.putExtra("blank_to_pass", blankListAdapter.getItem(position).toJSON());
        startActivityForResult(passBlankIntent, PASS_BLANK_REQUEST);
    }

    public void publishResult(TestResult _result) {
        apiService.postResult(_result);
    }

    public BlankList getBlankList() {
        return loadedBlankList;
    }

    public UserList getUserList() {
        return (UserList) apiService.getUsers();
    }

    @Override
    public boolean hasBlanks() {
        return loadedBlankList != null;
    }

    @Override
    public boolean hasUsers() {
        return getUserList() != null;
    }

    @Override
    public void hideLoading() {
        loadingIndicator.hide();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        // Edited blank callback
        if (requestCode == ADD_BLANK_REQUEST && resultCode != RESULT_CANCELED) {
            if (XeemAuthService.isOnline()) {
                final BlankObject _blank = BlankObject.fromJSON(result.getStringExtra("edited_blank"));
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Опубликовать бланк?");
                builder.setNegativeButton("Не надо", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d("XEEMDBG", "[POSTING] Declined by user");
                    }
                });
                builder.setPositiveButton("Опубликовать", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d("XEEMDBG", "[POSTING] Requested");
                        apiService.postBlank(_blank);
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            } else Toast.makeText(this, "Нет подключения к интернету", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_BLANK_REQUEST && resultCode != RESULT_CANCELED) {
            if (XeemAuthService.isOnline()) {
                final BlankObject _blank = BlankObject.fromJSON(result.getStringExtra("edited_blank"));
                Log.d("XEEMDBG", "tried to send: " + _blank);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Сохранить изменения?");
                builder.setNegativeButton("Не надо", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d("XEEMDBG", "[POSTING] Declined by user");
                    }
                });
                builder.setPositiveButton("Сохранить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d("XEEMDBG", "[PATCH] Blank sent to api class");
                        apiService.editBlank(_blank);
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            } else Toast.makeText(this, "Нет подключения к интернету", Toast.LENGTH_SHORT).show();
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
            if (fragment_frame != null) fragment_frame.setVisibility(View.VISIBLE);
        }
    }

}
