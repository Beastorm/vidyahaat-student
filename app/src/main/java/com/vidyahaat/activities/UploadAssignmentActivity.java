package com.vidyahaat.activities;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.vidyahaat.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.vidyahaat.api.ApiClient;
import com.vidyahaat.api.ApiInterface;
import com.vidyahaat.model.uploadassignment.UploadAssignmentModel;
import com.vidyahaat.model.viewassignment.Data;
import com.vidyahaat.model.viewassignment.ViewAssignmentModel;
import com.vidyahaat.utilities.MyPreference;

import java.io.File;
import java.util.List;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadAssignmentActivity extends AppCompatActivity {

    private static final int REQUEST_STORAGE = 112;
    TextInputEditText assignmentIdEt;
    TextInputLayout assignmentIdTil;
    Button chooseDocBtn, submitBtn;
    public static final int REQUEST_CODE_DOC = 123;
    TextView fileNameTv;
    ProgressBar progressBar;
    File file;

    MyPreference myPreference;
    private boolean isFileSelected = false, isAssignmentId = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_assignment);
        assignmentIdEt = findViewById(R.id.assignment_id_et);
        assignmentIdTil = findViewById(R.id.assignment_id_til);
        chooseDocBtn = findViewById(R.id.upload_btn);
        submitBtn = findViewById(R.id.submitBtn);
        fileNameTv = findViewById(R.id.file_name);
        progressBar = findViewById(R.id.progressBar);
        submitBtn.setEnabled(false);
        submitBtn.setAlpha((float) 0.3);
        myPreference = new MyPreference(this);
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
                    if ((Objects.requireNonNull(assignmentIdEt.getText()).toString().length() > 0)) {
                        submitBtn.setEnabled(true);
                        submitBtn.setAlpha(1);
                        assignmentIdTil.setError(null);
                        isAssignmentId = true;
                    } else {
                        submitBtn.setEnabled(false);
                        submitBtn.setAlpha((float) 0.3);
                        assignmentIdTil.setError("Please Provide Assignment ID");
                        isAssignmentId = false;
                    }
                    performEnableDisable();

                } catch (Exception e) {
                    submitBtn.setEnabled(false);
                    submitBtn.setAlpha((float) 0.3);
                    isAssignmentId = false;
                    assignmentIdTil.setError("Please Provide Assignment ID");
                    performEnableDisable();
                }
            }
        });


        chooseDocBtn.setOnClickListener(view -> {
            if (Build.VERSION.SDK_INT >= 23) {
                String[] PERMISSIONS = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE};
                if (!hasPermissions(getApplicationContext(), PERMISSIONS)) {
                    ActivityCompat.requestPermissions(UploadAssignmentActivity.this, PERMISSIONS, REQUEST_STORAGE);
                } else {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    intent.setType("application/pdf");

                    startActivityForResult(Intent.createChooser(intent, "Select Document"), REQUEST_CODE_DOC);
                }
            } else {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");

                startActivityForResult(Intent.createChooser(intent, "Select Document"), REQUEST_CODE_DOC);
            }
        });

        submitBtn.setOnClickListener(view -> {
            if (isAssignmentId && isFileSelected) {

                getAllSubmittedAssignment();

            } else {
                Toast.makeText(UploadAssignmentActivity.this, "Please Upload Complete Data", Toast.LENGTH_SHORT).show();
            }


        });


    }

    private static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    /*get Permissions Result*/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


//                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/example.pdf");
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            } else {

//                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/example.pdf");
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "The app was not allowed to write to your storage.", Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        Log.i("TAG", "onActivityResult: " + requestCode + " ," + data + " ," + resultCode);

        if (requestCode == REQUEST_CODE_DOC && resultCode == RESULT_OK && data != null) {
            Uri path = data.getData();
            isFileSelected = true;
            performEnableDisable();
            chooseDocBtn.setVisibility(View.GONE);
            file = new File(Objects.requireNonNull(FileUtil.getPath(path, this)));
            fileNameTv.setText(file.getName());
        }


    }

    private void uploadAssignmentData(String id) {
        submitBtn.setVisibility(View.GONE);
        RequestBody assignmentId = RequestBody.create(MediaType.parse("multipart/form-data"), id);
        RequestBody studentId = RequestBody.create(MediaType.parse("multipart/form-data"), myPreference.readStudentId());
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/pdf"), file);
        MultipartBody.Part doc = MultipartBody.Part.createFormData("item_doc", file.getName(), requestBody);

        Log.i("TAG", "uploadAssignmentData: " + file);
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        progressBar.setVisibility(View.VISIBLE);
        Call<UploadAssignmentModel> call = apiInterface.submitAssignment(assignmentId, studentId, doc);

        call.enqueue(new Callback<UploadAssignmentModel>() {
            @Override
            public void onResponse(@NonNull Call<UploadAssignmentModel> call, @NonNull Response<UploadAssignmentModel> response) {
                progressBar.setVisibility(View.GONE);
                Log.i("uploadAssignmentData", "onResponse: " + response.code());
                if (response.isSuccessful()) {
                    if (response.body().getResponse().equals("Assignment Submission Added Successfully")) {
                        Toast.makeText(UploadAssignmentActivity.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    } else if (response.body().getResponse().equals("Assignment Not Found")) {
                        Toast.makeText(UploadAssignmentActivity.this, "Wrong Assignment ID", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(UploadAssignmentActivity.this, "Already Submitted", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(UploadAssignmentActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(@NonNull Call<UploadAssignmentModel> call, @NonNull Throwable t) {
                Toast.makeText(UploadAssignmentActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }


    public static class FileUtil {
        /*
         * Gets the file path of the given Uri.
         */
        @SuppressLint("NewApi")
        public static String getPath(Uri uri, Context context) {
            // final boolean needToCheckUri = true;
            String selection = null;
            String[] selectionArgs = null;
            // Uri is different in versions after KITKAT (Android 4.4), we need to
            // deal with different Uris.
            if (DocumentsContract.isDocumentUri(context, uri)) {
                if (isExternalStorageDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                } else if (isDownloadsDocument(uri)) {
                    final String id = DocumentsContract.getDocumentId(uri);
                    if (id.startsWith("raw:")) {
                        return id.replaceFirst("raw:", "");
                    }
                    uri = ContentUris.withAppendedId(
                            Uri.parse("content://downloads/public_downloads"), Long.parseLong(id));
                } else if (isMediaDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];
                    switch (type) {
                        case "image":
                            uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                            break;
                        case "video":
                            uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                            break;
                        case "audio":
                            uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                            break;
                    }
                    selection = "_id=?";
                    selectionArgs = new String[]{
                            split[1]
                    };
                }
            }
            if ("content".equalsIgnoreCase(uri.getScheme())) {
                String[] projection = {
                        MediaStore.Images.Media.DATA
                };
                try (Cursor cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null)) {
                    if (cursor != null && cursor.moveToFirst()) {
                        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        return cursor.getString(columnIndex);
                    }
                } catch (Exception e) {
                    Log.e("on getPath", "Exception", e);
                }
            } else if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }
            return null;
        }

        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is ExternalStorageProvider.
         */
        private static boolean isExternalStorageDocument(Uri uri) {
            return "com.android.externalstorage.documents".equals(uri.getAuthority());
        }

        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is DownloadsProvider.
         */
        private static boolean isDownloadsDocument(Uri uri) {
            return "com.android.providers.downloads.documents".equals(uri.getAuthority());
        }

        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is MediaProvider.
         */
        private static boolean isMediaDocument(Uri uri) {
            return "com.android.providers.media.documents".equals(uri.getAuthority());
        }
    }

    public void performEnableDisable() {
        if (isAssignmentId) {

            if (isFileSelected) {
                submitBtn.setEnabled(true);
                submitBtn.setAlpha((float) 1);
            } else {
                submitBtn.setEnabled(false);
                submitBtn.setAlpha((float) 0.3);

            }

        } else {
            submitBtn.setEnabled(false);
            submitBtn.setAlpha((float) 0.3);


        }
    }


    private void getAllSubmittedAssignment() {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        progressBar.setVisibility(View.VISIBLE);
        Call<ViewAssignmentModel> call = apiInterface.getAllSubmittedAssignments(myPreference.readStudentId());

        call.enqueue(new Callback<ViewAssignmentModel>() {
            @Override
            public void onResponse(@NonNull Call<ViewAssignmentModel> call, @NonNull Response<ViewAssignmentModel> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getResponse().equals("Successful")) {
                        if (response.body().getData().size() > 0) {
                            if (isAssignmentAlreadySubmit(response.body().getData())) {
                                Toast.makeText(UploadAssignmentActivity.this, "Already Assignment Submitted", Toast.LENGTH_SHORT).show();
                            } else {
                                uploadAssignmentData(Objects.requireNonNull(assignmentIdEt.getText()).toString().trim());
                            }
                        }

                    }

                }

            }

            @Override
            public void onFailure(@NonNull Call<ViewAssignmentModel> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(UploadAssignmentActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean isAssignmentAlreadySubmit(List<Data> data) {
        String id = assignmentIdEt.getText().toString().trim();
        boolean flag = false;

        for (Data item : data) {

            if (item.getId().equals(id)) {
                flag = true;
                break;
            }

        }
        return flag;
    }
}