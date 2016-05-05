package example.codeclan.com.hellofrog;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by sandy on 25/04/2016.
 */
public class HelloFrog extends AppCompatActivity {

    private static final String API_URL = "http://cc-amphibian-api.herokuapp.com/";
    private static final int REQUEST_CODE_FAVOURITES = 0;
    //above REQUEST_CODE_FAVOURITES is an ID, its not a counter (the 0 cannot change as we have set this to 'final'), its so we can reference it to get info back.

//    EditText mNameEditText;
//    EditText mSpeciesEditText;
//    Button mSubmitButton;
    ListView mListView;

    JSONAdapter mJSONAdapter;
    AmphibianList mFavourites;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("HelloFrog:", "onCreate called");
        super.onCreate(savedInstanceState);

        mFavourites = new AmphibianList();

        setContentView(R.layout.activity_main);


//        mNameEditText = (EditText) findViewById(R.id.name_input);
//        mSpeciesEditText = (EditText) findViewById(R.id.species_input);
//        mSubmitButton = (Button) findViewById(R.id.submit_button);
        mListView =  (ListView) findViewById(R.id.amphibian_list_view);

        mJSONAdapter = new JSONAdapter(getLayoutInflater(), this);
        fetchAmphibians();
        mListView.setAdapter(mJSONAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                JSONObject jsonObject = (JSONObject) mJSONAdapter.getItem(position);
                Log.d("HelloFrog: ", jsonObject.toString());

                Intent intent = new Intent(HelloFrog.this, AmphibianDetails.class);

                intent.putExtra("name", jsonObject.optString("name"));
                intent.putExtra("species", jsonObject.optString("species"));
                intent.putExtra("legs", jsonObject.optString("numberOfLegs"));
                intent.putExtra("media", jsonObject.optString("media"));
                intent.putExtra("url", jsonObject.optString("imageUrl"));
                intent.putExtra("favourites", mFavourites.getList());

                startActivityForResult(intent, REQUEST_CODE_FAVOURITES);
                //Use startActivityForResult rather than startActivity when we need to remember the outcome of the activity. eg. when we go back a page we still want the outcome to be remembered.
            }
        });


//        mSubmitButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d("HelloFrog:", "submit button clicked!");
//                Intent intent = new Intent(HelloFrog.this, AmphibianDetails.class);
//
//                intent.putExtra("name", mNameEditText.getText().toString());
//                intent.putExtra("species", mSpeciesEditText.getText().toString());
//
//                startActivity(intent);
//            }
//        });
    }

    private void fetchAmphibians(){
        AsyncHttpClient client = new AsyncHttpClient();

        client.get(API_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject jsonObject){
                Log.d("HelloFrog: ", jsonObject.toString());
                JSONArray data = jsonObject.optJSONArray("Amphibians");
                if (data != null){
                    mJSONAdapter.updateData(data, mFavourites);
                } else {
                    Log.e("HelloFrog:", "No data found :-( ");
                }
            }
            @Override
            public void onFailure(int statusCode, Throwable throwable, JSONObject error){
                Log.e("Hello Frog: ", "Failure: " + statusCode + " " + throwable.getMessage());
            }
            //// the comma above in Log.e is because it is expecting 2 parameters. If used a + instead then it would think it only has been passed 1 parameter.
        });
    }

    @Override
    protected void onActivityResult( int requestCode, int resultCode, Intent data){
        if (resultCode != RESULT_OK){
            return;
        }
        if (requestCode == REQUEST_CODE_FAVOURITES){
            if ( data == null) {
                return;
            }

            mFavourites = AmphibianDetails.getFavourites(data);
        }
        fetchAmphibians();
    }
}





















