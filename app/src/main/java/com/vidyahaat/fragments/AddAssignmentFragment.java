package com.vidyahaat.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.vidyahaat.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


public class AddAssignmentFragment extends Fragment {

    TextInputEditText assignmentIdEt;
    TextInputLayout assignmentIdTil;
    Button uploadBtn, submitBtn;
    public static final int REQUEST_CODE_DOC = 123;
    TextView fileNameTv;
    OnCommunicator onCommunicator;
    Context context;
    private String status;
    boolean isDocSelected = false, isAssignmentId = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_assignment, container, false);
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        this.context = context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        assignmentIdEt = view.findViewById(R.id.assignment_id_et);
        assignmentIdTil = view.findViewById(R.id.assignment_id_til);
        uploadBtn = view.findViewById(R.id.upload_btn);
        submitBtn = view.findViewById(R.id.submitBtn);
        fileNameTv = view.findViewById(R.id.file_name);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        submitBtn.setEnabled(false);
        submitBtn.setAlpha((float) 0.5);
        if (getArguments() != null)
            if (getArguments().getString("file") != null) {
                isDocSelected = true;
                fileNameTv.setText(getArguments().getString("file"));
                uploadBtn.setVisibility(View.INVISIBLE);
            } else {
                isDocSelected = false;
            }

        assignmentIdEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {


                try {
                    if ((assignmentIdEt.getText().toString().length() > 0)) {
                        submitBtn.setEnabled(true);
                        submitBtn.setAlpha(1);
                        assignmentIdTil.setError(null);
                        isAssignmentId = true;
                    } else {
                        submitBtn.setEnabled(false);
                        submitBtn.setAlpha((float) 0.5);
                        assignmentIdTil.setError("Please Provide Assignment ID");
                        isAssignmentId = false;
                    }

                } catch (Exception e) {
                    submitBtn.setEnabled(false);
                    submitBtn.setAlpha((float) 0.5);
                    isAssignmentId = false;
                    assignmentIdTil.setError("Please Provide Assignment ID");
                }
            }
        });


        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCommunicator = (OnCommunicator) context;
                onCommunicator.onBtnClick();
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAssignmentId && isDocSelected) {
                    if (onCommunicator == null)
                        onCommunicator = (OnCommunicator) context;
                    status = onCommunicator.uploadAssignment(assignmentIdEt.getText().toString().trim());
                    if (status.equals("s"))
                        Toast.makeText(context, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Please upload complete data", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }

    public void getFileFromActivity(Uri file) {
        if (file != null)
            Toast.makeText(requireContext(), "file", Toast.LENGTH_SHORT).show();
        fileNameTv.setText(file.getPath());

    }

    public interface OnCommunicator {
        public void onBtnClick();

        public String uploadAssignment(String id);
    }


}