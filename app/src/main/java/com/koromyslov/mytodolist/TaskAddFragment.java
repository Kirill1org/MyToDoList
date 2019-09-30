package com.koromyslov.mytodolist;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class TaskAddFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";

    private String titleTask = "";
    private String descriptionTask = "";
    private int indicatorTask = 1;
    private boolean isCreated = false;
    private int index = 0;


    private Button createBtn;
    private EditText editTitleTask;
    private EditText editDescriptionTask;
    private View redEllipse;
    private View yellowEllipse;
    private View greenEllipse;
    private View redVectorCheck;
    private View yellowVectorCheck;
    private View greenVectorCheck;


    private OnFragmentInteractionListener mListener;

    public TaskAddFragment() {
        // Required empty public constructor
    }


    public static TaskAddFragment newInstance(String titleTask, String descriptionTask, int indicatorTask, int index) {
        TaskAddFragment fragment = new TaskAddFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, titleTask);
        args.putString(ARG_PARAM2, descriptionTask);
        args.putInt(ARG_PARAM3, indicatorTask);
        args.putInt(ARG_PARAM4, index);
        fragment.setArguments(args);
        return fragment;
    }

    public static TaskAddFragment newInstance() {

        TaskAddFragment fragment = new TaskAddFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.task_add, container, false);

        editTitleTask = rootView.findViewById(R.id.editTextTitle);
        editDescriptionTask = rootView.findViewById(R.id.editTextDescription);
        createBtn = rootView.findViewById(R.id.button_create);


        redEllipse = rootView.findViewById(R.id.red_ellipse);
        yellowEllipse = rootView.findViewById(R.id.yellow_ellipse);
        greenEllipse = rootView.findViewById(R.id.green_ellipse);
        redVectorCheck = rootView.findViewById(R.id.red_vector_check);
        yellowVectorCheck = rootView.findViewById(R.id.yellow_vector_check);
        greenVectorCheck = rootView.findViewById(R.id.green_vector_check);


        if (getArguments() != null) {
            titleTask = getArguments().getString(ARG_PARAM1);
            descriptionTask = getArguments().getString(ARG_PARAM2);
            indicatorTask = getArguments().getInt(ARG_PARAM3);
            index = getArguments().getInt(ARG_PARAM4);

            isCreated = true;
        }
        addFragmentInit();

        redEllipse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRedIndicator();


            }
        });

        yellowEllipse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setYellowIndicator();


            }
        });

        greenEllipse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setGreenIndicator();

            }
        });
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTitleTask.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "You should add a title!", Toast.LENGTH_LONG).show();
                } else if (editTitleTask.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "You should add a description!", Toast.LENGTH_LONG).show();
                } else {
                    mListener.onFragmentInteraction(editTitleTask.getText().toString(), editDescriptionTask.getText().toString(), indicatorTask, index, isCreated);
                }
            }
        });

        return rootView;
    }

    private void addFragmentInit() {

        editTitleTask.setText(titleTask);
        editDescriptionTask.setText(descriptionTask);
        if (indicatorTask == 1) setRedIndicator();
        if (indicatorTask == 2) setYellowIndicator();
        if (indicatorTask == 3) setGreenIndicator();

    }

    private void setGreenIndicator() {
        indicatorTask = 3;
        redVectorCheck.setVisibility(View.INVISIBLE);
        yellowVectorCheck.setVisibility(View.INVISIBLE);
        greenVectorCheck.setVisibility(View.VISIBLE);
    }

    private void setYellowIndicator() {
        indicatorTask = 2;
        redVectorCheck.setVisibility(View.INVISIBLE);
        yellowVectorCheck.setVisibility(View.VISIBLE);
        greenVectorCheck.setVisibility(View.INVISIBLE);
    }

    private void setRedIndicator() {
        indicatorTask = 1;
        redVectorCheck.setVisibility(View.VISIBLE);
        yellowVectorCheck.setVisibility(View.INVISIBLE);
        greenVectorCheck.setVisibility(View.INVISIBLE);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String titleTask, String descriptionTask, int indicatorPriorityTask, int index, boolean isCreated);

    }
}
