package example.codeclan.com.hellofrog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by user on 27/04/2016.
 */
public class JSONAdapter extends BaseAdapter {

    private JSONArray mJSONArray;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private AmphibianList mFavourites;

    public JSONAdapter(LayoutInflater inflater, Context context)
    {
        mJSONArray = new JSONArray();
        mLayoutInflater = inflater;
        mContext = context;
    }

    public int getCount(){
        return mJSONArray.length();
    }

    public Object getItem(int position){
        return mJSONArray.optJSONObject(position);
    }

    public long getItemId(int position){
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent){

        ViewHolder holder;

        JSONObject jsonObject = (JSONObject) getItem(position);

        if(convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.row_listitem, null);
            holder = new ViewHolder();
            holder.thumbnailImageView = (ImageView) convertView.findViewById(R.id.img_thumbnail);
            holder.nameTextView = (TextView) convertView.findViewById(R.id.text_name);
            holder.speciesTextView = (TextView) convertView.findViewById(R.id.text_species);
            holder.favouritedImageView = (ImageView) convertView.findViewById(R.id.image_favourited);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        // checks if the contentview is empty(null) and if so it populates it with what it needs and saves this for the next time it is called. so if it has been created before then it wont be null and all the info for inside it is loaded in the else part.

        if (jsonObject.has("imageUrl")){
            String imageUrl = jsonObject.optString("imageUrl");

            Picasso picasso = Picasso.with(mContext);
            RequestCreator image = picasso.load(imageUrl);
            image.into(holder.thumbnailImageView);
        }

        if(jsonObject.has("name")){
            String name = jsonObject.optString("name");
            holder.nameTextView.setText(name);

            if(mFavourites.get(name) != null){
                holder.favouritedImageView.setVisibility(View.VISIBLE);
            } else {
                holder.favouritedImageView.setVisibility(View.INVISIBLE);
            }
        }

        if(jsonObject.has("species")){
            String species = jsonObject.optString("species");
            holder.speciesTextView.setText(species);
        }

        return  convertView;
    }

    public void updateData(JSONArray jsonArray, AmphibianList favourites){
        mJSONArray = jsonArray;
        mFavourites = favourites;
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        public ImageView thumbnailImageView;
        public TextView nameTextView;
        public TextView speciesTextView;
        public ImageView favouritedImageView;
    }
}
