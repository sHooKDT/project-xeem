package shook.xeem.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import shook.xeem.R;
import shook.xeem.activities.MainActivity;
import shook.xeem.interfaces.BlankListHolder;
import shook.xeem.objects.BlankObject;
import shook.xeem.objects.TestResult;
import shook.xeem.services.XeemAuthService;


public class TestResultFragment extends Fragment {


    public TestResultFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("XEEMDBG", "[RESULT] Fragment shown");
        final View view = inflater.inflate(R.layout.fragment_test_result, null);

        final TestResult result = TestResult.fromJSON(getArguments().getString("result"));
        final BlankObject blank = ((BlankListHolder) getActivity()).getBlankList().findBlankById(result.testid);
        String title;
        if (blank != null) title = blank.getTitle();
        else title = "unknown";

        ((TextView) view.findViewById(R.id.test_complete_name)).setText(String.format(getString(R.string.test_complete_name), title));
        ((TextView) view.findViewById(R.id.test_complete_points)).setText(String.format(getString(R.string.test_complete_points), result.points, result.max_points, (result.points * 100 / result.max_points), "%"));
        ((TextView) view.findViewById(R.id.test_complete_questions)).setText(String.format(getString(R.string.test_complete_questions), result.right_questions, result.max_questions, (result.right_questions * 100 / result.max_questions), "%"));
        ((TextView) view.findViewById(R.id.test_complete_mark)).setText(getString(R.string.test_complete_mark));

        ((CheckBox) view.findViewById(R.id.postCheckBox)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!XeemAuthService.isOnline()) {
                    Toast.makeText(getActivity(), "Нет подключения к интернету, отправка недоступна", Toast.LENGTH_SHORT).show();
                    ((CheckBox) view.findViewById(R.id.postCheckBox)).setChecked(false);
                }
            }
        });

        view.findViewById(R.id.okButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View clickView) {

                if (((CheckBox) view.findViewById(R.id.postCheckBox)).isChecked()) {
                    ((MainActivity) getActivity()).publishResult(result);
                }

                getActivity().getSupportFragmentManager().popBackStack();
                getActivity().findViewById(R.id.result_fragment_container).setVisibility(View.GONE);
            }
        });

        return view;
    }

}
