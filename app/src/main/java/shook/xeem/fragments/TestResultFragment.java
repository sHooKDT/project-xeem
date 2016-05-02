package shook.xeem.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import shook.xeem.R;
import shook.xeem.objects.TestResult;


public class TestResultFragment extends Fragment {


    public TestResultFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("XEEMDBG", "Fragment shown");
        View view = inflater.inflate(R.layout.fragment_test_result, null);

        TestResult result = TestResult.fromJSON(getArguments().getString("result"));

        ((TextView) view.findViewById(R.id.test_complete_name)).setText(String.format(getString(R.string.test_complete_name), result.getBlankName()));
        ((TextView) view.findViewById(R.id.test_complete_points)).setText(String.format(getString(R.string.test_complete_points), result.getPoints(), result.getMaxPoints(), (result.getPoints() * 100 / result.getMaxPoints()), "%"));
        ((TextView) view.findViewById(R.id.test_complete_questions)).setText(String.format(getString(R.string.test_complete_questions), result.getQRight(), result.getQCount(), (result.getQRight() * 100 / result.getQCount()), "%"));
        ((TextView) view.findViewById(R.id.test_complete_mark)).setText(getString(R.string.test_complete_mark));

        view.findViewById(R.id.okButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
                getActivity().findViewById(R.id.result_fragment_container).setVisibility(View.GONE);
            }
        });

        return view;
    }

}
