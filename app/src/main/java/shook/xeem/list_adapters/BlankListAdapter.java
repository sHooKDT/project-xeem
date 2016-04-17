package shook.xeem.list_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import shook.xeem.XeemApiService;
import shook.xeem.objects.BlankObject;
import shook.xeem.R;

public class BlankListAdapter extends BaseAdapter{

    Context context;
    LayoutInflater lInflater;
    List<BlankObject> blanksList;

    public BlankListAdapter(Context _context, List<BlankObject> _blanksList) {
        this.context = _context;
        this.blanksList = _blanksList;
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
        BlankObject curBlank = (BlankObject) getItem(position);

        if (view == null) {
            view = lInflater.inflate(R.layout.blank_list_item, parent, false);
        }

        ((TextView) view.findViewById(R.id.blanktitle)).setText(curBlank.getTitle());
        ((TextView) view.findViewById(R.id.blankauthor)).setText(curBlank.getAuthor());

        ((Button) view.findViewById(R.id.delButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                XeemApiService.deleteBlank(blanksList.get(position));
            }
        });

        return view;
    }
}
