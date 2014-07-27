package com.kokonote.irkitcontrol.fragment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.kokonote.irkitcontrol.R;
import com.kokonote.irkitcontrol.ds.entity.PostHistory;

import java.util.List;

/**
 * Created by kuriyama on 2014/07/27.
 */
public class PostHistoryAdapter extends ArrayAdapter<PostHistory>{

    private LayoutInflater inflater;

    public PostHistoryAdapter(Context context, int resource, List<PostHistory> objects) {
        super(context, resource, objects);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        View view = convertView;
        if(view == null){
            view = inflater.inflate(R.layout.item_post_history,null);

            holder = new ViewHolder();
            holder.title = (TextView) view.findViewById(R.id.item_post_history_title_text);
            holder.date = (TextView) view.findViewById(R.id.item_post_history_date_text);
            view.setTag(holder);
        }else{
           holder = (ViewHolder) view.getTag();
        }
        PostHistory item = getItem(position);
        holder.title.setText(item.title);
        holder.date.setText(item.datetime);

        return view;
    }


    private static class ViewHolder{
        TextView title;
        TextView date;
    }
}
