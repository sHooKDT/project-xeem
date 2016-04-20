package shook.xeem.list_adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import shook.xeem.XeemApiService;
import shook.xeem.activities.BlankEditActivity;
import shook.xeem.activities.MainActivity;
import shook.xeem.objects.BlankObject;
import shook.xeem.R;

public class BlankListAdapter extends BaseAdapter{

    Context context;
    LayoutInflater lInflater;
    List<BlankObject> blanksList;
    MainActivity activity;

    public BlankListAdapter(Context _context, List<BlankObject> _blanksList) {
        this.context = _context;
        this.blanksList = _blanksList;
        activity = (MainActivity) _context;
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void reload(List<BlankObject> _blankset) {
        this.blanksList = _blankset;
    }

    @Override
    public int getCount() {
        return blanksList.size();
    }

    @Override
    public Object getItem(int i) {
        return blanksList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int _position, View convertView, ViewGroup parent) {

        final int position = _position;
        View view =  convertView;
        final BlankObject curBlank = (BlankObject) getItem(position);

        if (view == null) {
            view = lInflater.inflate(R.layout.blank_list_item, parent, false);
        }

        ((TextView) view.findViewById(R.id.blanktitle)).setText(curBlank.getTitle());
        ((TextView) view.findViewById(R.id.blankauthor)).setText(curBlank.getAuthor());

        ((Button) view.findViewById(R.id.delButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.deleteBlankClick(position);
//                XeemApiService.deleteBlank(blanksList.get(position));
//                XeemApiService.updateBlanks();
            }
        });

        ((Button) view.findViewById(R.id.editButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.editBlankClick(position);
//                Log.d("MYTAG", "[BLANKADAPTER] Requesting edit");
//                Intent editBlankIntent = new Intent(context, BlankEditActivity.class);
//                editBlankIntent.setAction("EDIT");
//                editBlankIntent.putExtra("blank_to_edit", curBlank.toJSON());
//                ((Activity) context).startActivityForResult(editBlankIntent, MainActivity.EDIT_BLANK_REQUEST);
            }
        });

        ((Button) view.findViewById(R.id.passButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.passBlankClick(position);
            }
        });

        return view;
    }
}
