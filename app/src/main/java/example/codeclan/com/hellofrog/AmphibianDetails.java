package example.codeclan.com.hellofrog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.net.URL;

/**
 * Created by user on 26/04/2016.
 */
public class AmphibianDetails extends AppCompatActivity{

    TextView mNameText;
    TextView mSpeciesText;
    TextView mNumberOfLegsText;
    TextView mMediaText;
    ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate( savedInstanceState);
        setContentView(R.layout.activity_details);
        Log.d("HelloFrog:", "AmphibianDetails.onCreate called");

        mNameText = (TextView) findViewById(R.id.text_name);
        mSpeciesText = (TextView)findViewById(R.id.text_species);
        mNumberOfLegsText = (TextView) findViewById(R.id.text_number_of_legs);
        mMediaText = (TextView) findViewById(R.id.text_media);
        mImageView = (ImageView) findViewById(R.id.img_imageUrl);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        String name = extras.getString("name");
        String species = extras.getString("species");
        String numberOfLegs = extras.getString("legs");
        String media = extras.getString("media");
        String url = extras.getString("url");

        Picasso picasso = Picasso.with(this);
        RequestCreator image = picasso.load(url);
        image.into(mImageView);

        mNameText.setText("Name: "+name);
        mSpeciesText.setText("Species: "+species);
        mNumberOfLegsText.setText("Number of Legs: " + numberOfLegs);
        mMediaText.setText("Stars in: " + media);


    }
}

