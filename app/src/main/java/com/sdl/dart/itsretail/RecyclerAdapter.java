package com.sdl.dart.itsretail;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class RecyclerAdapter extends
        RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{
    private String[] titles={"Wheat Flour","Rice","Potato","Onion"};

    private String[] details = {"Get the best quality flour",
            "Choose your favorite type of Rice", "Plump, affordable potato",
            "Best quality affordable onions"};
    private int[] images = { R.drawable.wheat_flour,
            R.drawable.rice,
            R.drawable.potatoes,
            R.drawable.onions,
     };
    class ViewHolder extends RecyclerView.ViewHolder {
        public int currentItem;
        public ImageView itemImage;
        public TextView itemTitle;
        public TextView itemDetail;
        public ViewHolder(View itemView) {
            super(itemView);
            itemImage =
                    (ImageView)itemView.findViewById(R.id.item_image);
            itemTitle =
                    (TextView)itemView.findViewById(R.id.item_title);
            itemDetail =
                    (TextView)itemView.findViewById(R.id.item_detail);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    int position = getAdapterPosition();
                    Intent intent;
                   // Log.d("xyzr22",position+"touched");
                    switch(position)
                    {
                        case 0:
                            intent = new Intent(v.getContext(), WheatActivity.class);
                            v.getContext().startActivity(intent);
                            break;
                        case 1:intent = new Intent(v.getContext(), RiceActivity.class);
                            v.getContext().startActivity(intent);
                            break;
                        case 2:intent = new Intent(v.getContext(), PotatoActivity.class);
                            v.getContext().startActivity(intent);
                            break;
                        case 3:intent = new Intent(v.getContext(), OnionActivity.class);
                            v.getContext().startActivity(intent);
                            break;
                    }
                }
            });
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.itemTitle.setText(titles[i]);
        viewHolder.itemDetail.setText(details[i]);
        viewHolder.itemImage.setImageResource(images[i]);
    }
    @Override
    public int getItemCount() {
        return titles.length;
    }
}
