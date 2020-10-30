package mr.bel.testtaskgorastudiomiakinruslan;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class AdapterReciclerView extends RecyclerView.Adapter<AdapterReciclerView.ViewHolder>{

private List<String> titles;
private ArrayList<Bitmap> imageIds;
private Context context;

public static class ViewHolder extends RecyclerView.ViewHolder{
    private CardView cardView;
    public ViewHolder(CardView v) {
        super(v);
        cardView = v;
    }
}
    public AdapterReciclerView(Context context, List<String> titles, ArrayList<Bitmap> imageIds){
        this.titles= titles;
        this.imageIds = imageIds;
        this.context = context;

    }

    @Override
    public AdapterReciclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        CardView cv2 = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_cardview, parent, false);
        return new AdapterReciclerView.ViewHolder(cv2);
    }

    public void onBindViewHolder(AdapterReciclerView.ViewHolder holder, int position){

        CardView cardView = holder.cardView;
        ImageView imageView =cardView.findViewById(R.id.image_albumid);
        BitmapDrawable drawable =  new BitmapDrawable(context.getResources(),imageIds.get(position));
        imageView.setImageDrawable(drawable);
        imageView.setContentDescription(titles.get(position));
        TextView textView = cardView.findViewById(R.id.title_id);
        textView.setText(titles.get(position));

    }

    public void setData(List<String> titles, ArrayList<Bitmap> imageIds){
        this.titles = titles;
        this.imageIds = imageIds;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){
        return titles.size();
    }}