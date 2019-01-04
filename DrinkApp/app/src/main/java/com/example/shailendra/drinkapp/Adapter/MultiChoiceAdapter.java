package com.example.shailendra.drinkapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.shailendra.drinkapp.Model.Drink;
import com.example.shailendra.drinkapp.R;
import com.example.shailendra.drinkapp.Utils.Common;

import java.util.List;

/**
 * Created by shailendra on 5/26/2018.
 */

public class MultiChoiceAdapter extends RecyclerView.Adapter<MultiChoiceAdapter.MultiChoiceViewHolder>{

    Context context;
    List<Drink> optionList;

    public MultiChoiceAdapter(Context context, List<Drink> optionList) {
        this.context = context;
        this.optionList = optionList;
    }

    @Override
    public MultiChoiceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View itemview = LayoutInflater.from(context).inflate(R.layout.multi_check_layout , null);
        return new MultiChoiceViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(MultiChoiceViewHolder holder, final int position) {

        holder.checkBox.setText(optionList.get(position).Name);
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked)
                {
                    Common.toppingAdded.add(buttonView.getText().toString());
                    Common.toppingPrice+=Double.parseDouble(optionList.get(position).Price);

                }
                else
                {
                    Common.toppingAdded.remove(buttonView.getText().toString());
                    Common.toppingPrice-=Double.parseDouble(optionList.get(position).Price);

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return optionList.size();
    }

    //MultichoiceViewHolder in same
    class MultiChoiceViewHolder extends RecyclerView.ViewHolder
    {
      CheckBox checkBox;

        public MultiChoiceViewHolder(View itemView) {
            super(itemView);

            checkBox = (CheckBox)itemView.findViewById(R.id.ckb_topping);

        }
    }
}
