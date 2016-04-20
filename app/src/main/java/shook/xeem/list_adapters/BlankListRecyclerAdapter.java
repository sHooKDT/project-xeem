package shook.xeem.list_adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import shook.xeem.R;
import shook.xeem.activities.MainActivity;
import shook.xeem.objects.BlankObject;

public class BlankListRecyclerAdapter extends RecyclerView.Adapter<BlankListRecyclerAdapter.ViewHolder> {

    List<BlankObject> blanksList;
    MainActivity activity;

    public BlankListRecyclerAdapter(MainActivity _activity, List<BlankObject> _blanksLists) {
        activity = _activity;
        this.blanksList = _blanksLists;
    }

    public void reload(List<BlankObject> _blankset) {
        this.blanksList = _blankset;
    }

    @Override
    public BlankListRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.blank_list_item, parent, false);
        return new BlankListRecyclerAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int _position) {
        final int position = holder.getAdapterPosition();
        BlankObject blank = blanksList.get(position);
        holder.author.setText(blank.getAuthor());
        holder.title.setText(blank.getTitle());

        holder.delBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.deleteBlankClick(position);
//                XeemApiService.deleteBlank(blanksList.get(position));
//                XeemApiService.updateBlanks();
            }
        });

        holder.editBut.setOnClickListener(new View.OnClickListener() {
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

        holder.passBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.passBlankClick(position);
            }
        });
    }

    public BlankObject getItem(int position) {
        return blanksList.get(position);
    }

    @Override
    public int getItemCount() {
        return blanksList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView author;
        private TextView title;
        private Button delBut;
        private Button editBut;
        private Button passBut;

        public ViewHolder(View itemView) {
            super(itemView);
            this.author = (TextView) itemView.findViewById(R.id.blankauthor);
            this.title = (TextView) itemView.findViewById(R.id.blanktitle);
            this.delBut = (Button) itemView.findViewById(R.id.delButton);
            this.editBut = (Button) itemView.findViewById(R.id.editButton);
            this.passBut = (Button) itemView.findViewById(R.id.passButton);
        }

    }
}
