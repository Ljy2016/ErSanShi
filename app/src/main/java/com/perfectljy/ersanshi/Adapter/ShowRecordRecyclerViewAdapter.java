package com.perfectljy.ersanshi.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.perfectljy.ersanshi.R;
import com.perfectljy.ersanshi.Widget.slideitem.ItemSlideHelper;
import com.perfectljy.ersanshi.db.model.RecordModel;

import java.util.List;

/**
 * Created by PerfectLjy on 2016/3/17.
 */
public class ShowRecordRecyclerViewAdapter extends RecyclerView.Adapter<ShowRecordRecyclerViewAdapter.MyViewHolder> implements ItemSlideHelper.Callback {
    public void setRecordModelList(List<RecordModel> recordModelList) {
        this.recordModelList = recordModelList;
    }

    private List<RecordModel> recordModelList;
    private LayoutInflater layoutInflater;
    private RecordModel recordModel;
    private RecyclerView mRecyclerView;

    public ShowRecordRecyclerViewAdapter(Context context, List<RecordModel> recordModelList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.recordModelList = recordModelList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder viewHolder = new MyViewHolder(layoutInflater.inflate(R.layout.record_recyclerview_item, parent, false));
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        if (onClickListene != null) {

            holder.showLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    onClickListene.onShowClick(holder.itemView, pos);
                }
            });
            holder.deleteLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos=holder.getLayoutPosition();
                    onClickListene.onDeleteClick(holder.itemView,pos);
                }
            });
            holder.showLL.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    onClickListene.onItemLongClick(holder.itemView, pos);
                    return true;

                }
            });
        }
        recordModel = recordModelList.get(position);
        holder.title.setText(recordModel.getTitle());
        holder.date.setText(recordModel.getDate());
        holder.wether.setText(recordModel.getWeather());
    }

    @Override
    public int getItemCount() {
        return recordModelList.size();
    }

    //得到item的宽度
    @Override
    public int getHorizontalRange(RecyclerView.ViewHolder holder) {


        if (holder.itemView instanceof LinearLayout) {
            ViewGroup viewGroup = (ViewGroup) holder.itemView;
            if (viewGroup.getChildCount() == 2) {
                return viewGroup.getChildAt(1).getLayoutParams().width;
            }
        }


        return 0;

    }

    @Override
    public RecyclerView.ViewHolder getChildViewHolder(View childView) {
        return mRecyclerView.getChildViewHolder(childView);

    }

    @Override
    public View findTargetView(float x, float y) {

        return mRecyclerView.findChildViewUnder(x, y);

    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
        mRecyclerView.addOnItemTouchListener(new ItemSlideHelper(mRecyclerView.getContext(), this));
    }

    //item监听
    public interface OnClickListener {
        void onShowClick(View view, int position);

        void onDeleteClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    private OnClickListener onClickListene;

    public void setOnItemClickListene(OnClickListener onItemClickListene) {
        this.onClickListene = onItemClickListene;
    }

    //自定义viewholder
    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView date;
        public TextView wether;
        private LinearLayout showLL;
        private LinearLayout deleteLL;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.record_title_tv);
            wether = (TextView) view.findViewById(R.id.record_weather_tv);
            date = (TextView) view.findViewById(R.id.record_date_tv);
            showLL = (LinearLayout) view.findViewById(R.id.record_show_ll);
            deleteLL = (LinearLayout) view.findViewById(R.id.record_delete_ll);
        }
    }
}
