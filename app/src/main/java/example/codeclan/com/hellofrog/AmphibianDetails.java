package example.codeclan.com.hellofrog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by user on 26/04/2016.
 */
public class AmphibianDetails extends AppCompatActivity{

    TextView mNameText;
    TextView mSpeciesText;
    TextView mNumberOfLegsText;
    TextView mMediaText;
    ImageView mImageView;
    Button mAddFavouriteButton;
    AmphibianList mFavourites;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate( savedInstanceState);
        setContentView(R.layout.activity_details);
        Log.d("HelloFrog:", "AmphibianDetails.onCreate called");

        mFavourites = new AmphibianList();

        mNameText = (TextView) findViewById(R.id.text_name);
        mSpeciesText = (TextView)findViewById(R.id.text_species);
        mNumberOfLegsText = (TextView) findViewById(R.id.text_number_of_legs);
        mMediaText = (TextView) findViewById(R.id.text_media);
        mImageView = (ImageView) findViewById(R.id.img_imageUrl);
        mAddFavouriteButton = (Button) findViewById(R.id.add_favourite_button);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        String name = extras.getString("name");
        final String species = extras.getString("species");
        String numberOfLegs = extras.getString("legs");
        String media = extras.getString("media");
        String url = extras.getString("url");

        ArrayList<Amphibian> favourites = (ArrayList<Amphibian>) intent.getSerializableExtra("favourites");
        mFavourites = new AmphibianList(favourites);

        Picasso picasso = Picasso.with(this);
        RequestCreator image = picasso.load(url);
        image.into(mImageView);

        mNameText.setText(name);
        mSpeciesText.setText("Species: "+species);
        mNumberOfLegsText.setText("Number of Legs: " + numberOfLegs);
        mMediaText.setText("Stars in: " + media);

        mAddFavouriteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String name = mNameText.getText().toString();
                String species = mSpeciesText.getText().toString();

                Log.d("HelloFrog: ", name + " is to be added to favourites");

                Amphibian newFavourite = new Amphibian(name, species);
                mFavourites.add(newFavourite);

                Log.d("HelloFrog:", "Favourites contains " + mFavourites.length());
                for( Amphibian a: mFavourites.getList()){
                    Log.d("HelloFrog: ", a.toString());
                }

                Intent intent = new Intent();
                intent.putExtra("favourites", mFavourites.getList());
                setResult(RESULT_OK, intent);
            }
        });
    }

    public static AmphibianList getFavourites(Intent intent){
        ArrayList<Amphibian> favourites = (ArrayList<Amphibian>) intent.getSerializableExtra("favourites");
        return new AmphibianList(favourites);
    }
}

