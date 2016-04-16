package shook.xeem.list_adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import shook.xeem.objects.BlankObject;
import shook.xeem.objects.QuestionObject;
import shook.xeem.R;

public class BlankEditAdapter extends BaseAdapter{

    static final int EDIT_QUESTION_REQUEST = 29;

    Context context;
    LayoutInflater lInflater;
    BlankObject.Factory loadedFactory;

    public BlankEditAdapter(Context _context, BlankObject.Factory _factory) {
        this.context = _context;
        this.loadedFactory = _factory;
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if (loadedFactory.getPreview() == null) {
            return 0;
        } else {
            return loadedFactory.getPreview().getQuestions().size();
        }
    }

    @Override
    public Object getItem(int position) {
        return loadedFactory.getPreview().getQuestions().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final int pos = position;
        View view = convertView;

        if (view == null) {
            view = lInflater.inflate(R.layout.edit_question_list_item, parent, false);
        }

        QuestionObject q = (QuestionObject) getItem(position);

        ((TextView) view.findViewById(R.id.questionText)).setText(q.getText());

        // Click handler for REMOVE button
        Button removeBut = (Button) view.findViewById(R.id.removeButton);
        removeBut.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                loadedFactory.rmQuestion(pos);
                notifyDataSetChanged();
            }
        });

        // Click handler for EDIT button
        Button editBut = (Button) view.findViewById(R.id.editButton);
        editBut.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                editQuestion(pos);
                notifyDataSetChanged();
            }
        });


        return view;
    }

    public void editQuestion (int position) {
        final QuestionObject editable = loadedFactory.getPreview().getQuestions().get(position);
        AnswerAdapter myadapter = new AnswerAdapter(context, editable);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(editable.getText());
        builder.setAdapter(myadapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d("MYTAG", "You clicked something? WTF??");
            }
        });
        builder.setNeutralButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // TODO: FAST DELETE THIS SHIT
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
                builder.setTitle("Type the answer");
                final EditText input = new EditText(context);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editable.putAns(input.getText().toString());
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });
        builder.setPositiveButton("Nice!", new DialogInterface.OnClickListener() {
            public void onClick (DialogInterface dialog, int id) {
                dialog.cancel();
            };
        });
        AlertDialog alert = builder.create();
        alert.show();

    }

}
