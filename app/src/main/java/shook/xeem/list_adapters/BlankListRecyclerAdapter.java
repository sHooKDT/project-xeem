package shook.xeem.list_adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.LinkedList;

import shook.xeem.R;
import shook.xeem.interfaces.BlankListHolder;
import shook.xeem.objects.BlankObject;

public class BlankListRecyclerAdapter extends RecyclerView.Adapter<BlankListRecyclerAdapter.ViewHolder> {

    LinkedList<BlankObject> blankList;
    BlankListHolder blankListHost;

    public BlankListRecyclerAdapter(BlankListHolder _activity) {
        blankListHost = _activity;
        this.blankList = blankListHost.getBlankList();
    }

    public void reload() {
        this.blankList = blankListHost.getBlankList();
        notifyDataSetChanged();
    }

    @Override
    public BlankListRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.blank_list_item, parent, false);
        return new BlankListRecyclerAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int _position) {
        final int position = holder.getAdapterPosition();
        BlankObject blank = blankList.get(position);

        holder.author.setText(blank.getAuthor());
        holder.title.setText(blank.getTitle());

        holder.delBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                blankListHost.deleteBlankClick(position);
            }
        });

        holder.editBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                blankListHost.editBlankClick(position);
            }
        });

        holder.passBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                blankListHost.passBlankClick(position);
            }
        });
    }

    public BlankObject getItem(int position) {
        return blankList.get(position);
    }

    @Override
    public int getItemCount() {
        return blankList.size();
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
