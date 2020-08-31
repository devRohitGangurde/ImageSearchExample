package com.example.imageSearchApp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.example.imageSearchApp.R;
import com.example.imageSearchApp.database.ImageDatabase;
import com.example.imageSearchApp.databinding.ActivityImageDetailsBinding;
import com.example.imageSearchApp.model.Data;
import com.example.imageSearchApp.constants.AppConstants;
import com.squareup.picasso.Picasso;

import com.example.imageSearchApp.utility.AppComman;

public class ImageDetailActivity extends AppCompatActivity {
    private Data selectedImageData;
    private ActivityImageDetailsBinding activityImageDetailsBinding;
    private  ImageDatabase imageDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityImageDetailsBinding =  DataBindingUtil.setContentView(this,R.layout.activity_image_details);

        initSupportClasses();
        setToolbar();
        setImageDetailsData();


    }

    /**
     * on Back press of activity animate and finish
     */
    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        super.onBackPressed();
    }

    /**
     * On Click actions for View
     * @param v- the Click View
     */
    private void onClick(View v) {
        saveCommentInDatabase();
    }

    /**
     * Method for set Toolbar
     */
    private void setToolbar() {
        activityImageDetailsBinding.toolbar.setTitle(selectedImageData.getTitle());
        setSupportActionBar(activityImageDetailsBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    /**
     * Initialise the other class component
     */
    private void initSupportClasses() {

        //Register Listeners
        activityImageDetailsBinding.btnSaveComment.setOnClickListener(this::onClick);

        // Get Data from Bundle
        Intent intent = getIntent();
        selectedImageData = intent.getParcelableExtra(AppConstants.INSTANCE.getINTENT_VALUE());

        // Get Database instance
        imageDatabase = ImageDatabase.Companion.getInstance(this);
    }


    /**
     * Set data to Screen
     */
    private void setImageDetailsData() {
        if(selectedImageData!=null && selectedImageData.getLink()!=null){
            Picasso.get()
                    .load(selectedImageData.getInnerImages().get(0).getLink())
                    .placeholder(R.drawable.ic_person)
                    .into(activityImageDetailsBinding.imageViewUserDetails);
        }

        if(selectedImageData.getLink()!=null){
            Data savedImage = imageDatabase.imageDao().getImageDataByLink(selectedImageData.getLink());
            if(savedImage == null){
                activityImageDetailsBinding.edtTxtComment.setText("");
            }else {
                activityImageDetailsBinding.edtTxtComment.setText(savedImage.getComment());
            }

        }
    }


    /**
     * Save comments in Database
     */
    private void saveCommentInDatabase() {
        if(activityImageDetailsBinding.edtTxtComment.getText().toString().isEmpty()){
            Toast.makeText(this, "Please enter comment", Toast.LENGTH_SHORT).show();

        }else {
            selectedImageData.setComment(activityImageDetailsBinding.edtTxtComment.getText().toString());
            imageDatabase.imageDao().insertImageData(selectedImageData);
            Toast.makeText(this, "Comment Added", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == android.R.id.home)
        {
            onBackPressed();
            return true;
        }
        else
        {
            return super.onOptionsItemSelected(item);
        }
    }


}