package com.vidyahaat.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.vidyahaat.BuildConfig;
import com.vidyahaat.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.vidyahaat.api.ApiClient;
import com.vidyahaat.api.ApiInterface;
import com.vidyahaat.fragments.AddAssignmentFragment;
import com.vidyahaat.model.uploadassignment.UploadAssignmentModel;
import com.vidyahaat.utilities.MyPreference;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements AddAssignmentFragment.OnCommunicator {
    private static final int REQUEST_STORAGE = 112;
    NavController navController;
    BottomNavigationView bottomNavigationView;
    DrawerLayout drawerLayout;
    TextView nameTv, emailTv;
    MyPreference myPreference;
    public static final int REQUEST_CODE_DOC = 123;
    boolean isFileSelected = false;
    File file;
    String mediaPath;
    TextView myProfileBtn, uploadAssignmentBtn, shareAppBtn, contactUsBtn, logOutBtn, testPerformanceBtn, aboutUsBtn;
    View divider;
    AddAssignmentFragment addAssignmentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        drawerLayout = findViewById(R.id.drawerLayout);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationView navView = findViewById(R.id.nav_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        nameTv = findViewById(R.id.full_name_tv);
        emailTv = findViewById(R.id.mobile_no_tv);
        myPreference = new MyPreference(this);
        nameTv.setText(myPreference.readFullUserName());
        emailTv.setText(myPreference.readEmailId());
        myProfileBtn = findViewById(R.id.my_profile_tv);
        uploadAssignmentBtn = findViewById(R.id.upload_assignment_btn);
        shareAppBtn = findViewById(R.id.share_our_app_btn);
        contactUsBtn = findViewById(R.id.contact_us);
        logOutBtn = findViewById(R.id.log_out_btn);
        testPerformanceBtn = findViewById(R.id.test_performance);
        divider = findViewById(R.id.divider);
        aboutUsBtn = findViewById(R.id.about_us_link);
        addAssignmentFragment = new AddAssignmentFragment();
        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph())
                        .setOpenableLayout(drawerLayout)
                        .build();
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        NavigationUI.setupWithNavController(navView, navController);
        NavigationUI.setupWithNavController(
                toolbar, navController, appBarConfiguration);

        myProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.profileFragment);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        uploadAssignmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, UploadAssignmentActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        shareAppBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Vidyhaat");
                    String shareMessage = "\nLet me recommend you this application\n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch (Exception e) {
                    Log.i("TAG", "onClick: " + e.getMessage());
                }
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        contactUsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ContactUsActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        aboutUsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, AboutUsActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        testPerformanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.testPerformanceFragment);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        if (myPreference.readStudentType().equals("outsider")) {
            myProfileBtn.setVisibility(View.GONE);
            uploadAssignmentBtn.setVisibility(View.GONE);
            testPerformanceBtn.setVisibility(View.GONE);
            divider.setVisibility(View.GONE);
        } else {

            myProfileBtn.setVisibility(View.VISIBLE);
            uploadAssignmentBtn.setVisibility(View.VISIBLE);
            testPerformanceBtn.setVisibility(View.VISIBLE);
            divider.setVisibility(View.VISIBLE);
        }

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if (destination.getId() == R.id.homeFragment || destination.getId() == R.id.profileFragment || destination.getId() == R.id.messageFragment) {

                    bottomNavigationView.setVisibility(View.VISIBLE);
                } else {
                    bottomNavigationView.setVisibility(View.GONE);

                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myPreference.LogOut();
                Intent intent = new Intent(HomeActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        Fragment fragment = (Fragment) getSupportFragmentManager().findFragmentByTag(childTag);
//        if (fragment != null) {
//            fragment.onActivityResult(requestCode, resultCode, intent);
//        }

        Log.i("TAG", "onActivityResult: " + requestCode + " ," + data + " ," + resultCode);

        if (requestCode == REQUEST_CODE_DOC && resultCode == RESULT_OK && data != null) {

            Toast.makeText(this, "selected", Toast.LENGTH_SHORT).show();
            Uri path = data.getData();
            String[] projection = {MediaStore.Images.Media.DATA};
            isFileSelected = true;
            Cursor cursor = getContentResolver().query(path, projection, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(projection[0]);
            mediaPath = cursor.getString(columnIndex);

            file = new File(mediaPath);
            //file = new File(Objects.requireNonNull(FileUtil.getPath(path, getApplicationContext())));
            Bundle bundle = new Bundle();
            bundle.putString("file", file.getName());
            navController.navigate(R.id.addAssignmentFragment, bundle);
            cursor.close();
        }


    }


    private Intent getFileChooserIntent() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        // Only the system receives the ACTION_OPEN_DOCUMENT, so no need to test.
        return intent;
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

    /*get Permissions Result*/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, REQUEST_CODE_DOC);
                } else {
                    Toast.makeText(getApplicationContext(), "The app was not allowed to write to your storage.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    /*check permissions  for marshmallow*/

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


    @Override
    public void onBtnClick() {
        if (Build.VERSION.SDK_INT >= 23) {
            String[] PERMISSIONS = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE};
            if (!hasPermissions(getApplicationContext(), PERMISSIONS)) {
                ActivityCompat.requestPermissions((Activity) this, PERMISSIONS, REQUEST_STORAGE);
            } else {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, REQUEST_CODE_DOC);
            }
        } else {
            Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, REQUEST_CODE_DOC);
        }
    }

    @Override
    public String uploadAssignment(String id) {

        uploadAssignmentData(id);

        return "s";
    }

    private void uploadAssignmentData(String id) {
        RequestBody assignmentId = RequestBody.create(MediaType.parse("multipart/form-data"), id);
        RequestBody studentId = RequestBody.create(MediaType.parse("multipart/form-data"), myPreference.readStudentId());
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part doc = MultipartBody.Part.createFormData("file", file.getName(), requestBody);


        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<UploadAssignmentModel> call = apiInterface.submitAssignment(assignmentId, studentId, doc);

        call.enqueue(new Callback<UploadAssignmentModel>() {
            @Override
            public void onResponse(@NonNull Call<UploadAssignmentModel> call, @NonNull Response<UploadAssignmentModel> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(HomeActivity.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(@NonNull Call<UploadAssignmentModel> call, @NonNull Throwable t) {
                Toast.makeText(HomeActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}