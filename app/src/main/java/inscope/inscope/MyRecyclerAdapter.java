package inscope.inscope;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

/**
 * Created by tirthrami on 3/30/16.
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder>{

    List<Scopeinfo> data = Collections.emptyList();

    public MyRecyclerAdapter(Context context, List<Scopeinfo> data){
        this.data = data;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       final View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_scope_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Scopeinfo current = data.get(position);
        holder.title.setText(current.title);
        holder.subtitle.setText(current.subTitle);
        holder.mood.setText(current.mood);
        holder.phoneNumber.setText(current.phoneNumber);
        holder.icon.setImageResource(current.iconId);
    }

    @Override
    public int getItemCount() {
        if(data != null) return data.size();
        return 0;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView){
        super.onAttachedToRecyclerView(recyclerView);
    }


    static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView title;
        public TextView subtitle;
        public TextView mood;
        public TextView phoneNumber;
        public ImageView icon;
        public LinearLayout row;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.scope_data_title);
            subtitle = (TextView) itemView.findViewById(R.id.scope_data_subtitle);
            mood = (TextView) itemView.findViewById(R.id.mood);
            phoneNumber = (TextView) itemView.findViewById(R.id.phoneNumber);
            icon = (ImageView) itemView.findViewById(R.id.scope_data_icon);
            row = (LinearLayout) itemView.findViewById(R.id.custom_row);
            row.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), "Debug: Item Click", Toast.LENGTH_SHORT).show();

            //mListener.details();
        }

    }
}
