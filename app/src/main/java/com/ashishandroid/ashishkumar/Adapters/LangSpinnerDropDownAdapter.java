package com.ashishandroid.ashishkumar.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.ashishandroid.ashishkumar.LangSpinnerDropDownInterface;
import com.ashishandroid.ashishkumar.try1.R;

import java.util.List;
import java.util.Set;

import androidx.annotation.NonNull;

public class LangSpinnerDropDownAdapter<T> extends BaseAdapter {

    private LangSpinnerDropDownInterface callback;


    class SpinnerItem<String> {
        private String txt;
        private T item;

        SpinnerItem(T t, String s) {
            item = t;
            txt = s;
        }
    }

    private Context context;
    private Set<String> languageSelected;
    private List<String> all_items;
    private String headerText;



    public LangSpinnerDropDownAdapter(Context context, int resource,
                                      String headerText,
                                      List<String> all_items,Set<String> languageSelected, LangSpinnerDropDownInterface abc) {
        this.context = context;
        this.headerText = headerText;
        this.all_items = all_items;
        this.languageSelected = languageSelected;
        this.callback= abc;
    }

    @Override
    public int getCount() {
        return all_items.size() + 1;
    }

    @Override
    public String getItem(int position) {
        if( position < 1 ) {
            return null;
        }
        else {
            return all_items.get(position -1);
        }
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    @SuppressWarnings({"unchecked"})
    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
//        Log.d("TAG", "geview   1");
        final ViewHolder holder;
        if (convertView == null ) {
            LayoutInflater layoutInflator = LayoutInflater.from(context);
            convertView = layoutInflator.inflate(R.layout.spinner_item, parent, false);

            holder = new ViewHolder();
            holder.mTextView = convertView.findViewById(R.id.text);
            holder.mCheckBox = convertView.findViewById(R.id.checkbox);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

//        Log.d("TAG", "geview   2");
        if( position < 1 ) {
//            holder.mCheckBox.setVisibility(View.GONE);
//            holder.mTextView.setText(headerText);
            header(holder,headerText,position);

//            Log.d("TAG", "geview   3");
        }
        else {
            final int listPos = position - 1;
            holder.mCheckBox.setVisibility(View.VISIBLE);
            holder.mTextView.setText(all_items.get(listPos));
            final String item = (String) all_items.get(listPos);
            boolean isSel = languageSelected.contains(item);

            holder.mCheckBox.setOnCheckedChangeListener(null);
            holder.mCheckBox.setChecked(isSel);

//            if (all_items.get(listPos).equals("OTHERS")) {
//                holder.mCheckBox.setVisibility(View.INVISIBLE);
//            }

//            Log.d("TAG", "geview   5");
            holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        if (!all_items.get(listPos).equals("OTHERS")) {
                            languageSelected.add(item);
                            headerText = languageSelected.toString();
//                            notifyDataSetChanged();
                            Log.d("TAG", "Adapterselected_items: " + languageSelected);
                        }
                        else if (all_items.get(listPos).equals("OTHERS")) {
                            Log.d("TAG", "Others");
                            String signal = "ClickedOthers";
                            languageSelected.add(item);
                            Log.d("TAG", "callback" + callback + signal);
                            callback.visibleOtherLanguageEditText(signal);
                        }
                        notifyDataSetChanged();
                    }
                    else {
                        if(all_items.get(listPos).equals("OTHERS")){
                            Log.d("TAG", "Others");
                            String signal = "UnClickedOthers";
                            languageSelected.remove(item);
                            if (languageSelected.isEmpty()) {
                                headerText = "Select Language";
//                                notifyDataSetChanged();
                            }
                            Log.d("TAG", "callback"+callback+signal);
                            callback.visibleOtherLanguageEditText(signal);
                        }
                        else {
                            languageSelected.remove(item);
                            headerText = languageSelected.toString();
//                            notifyDataSetChanged();
                            if (languageSelected.isEmpty()) {
                                headerText = "Select Language";
//                                notifyDataSetChanged();
                            }
                        }
                        Log.d("TAG", "AdapterUnselected_items: "+ languageSelected);
                        notifyDataSetChanged();
                    }

                    callback.getValueFromLangSpinnerAdapter(languageSelected);
                }
            });

            holder.mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.mCheckBox.toggle();
                }
            });

        }

        return convertView;
    }

    void header(ViewHolder holder, String string,int position){
        holder.mCheckBox.setVisibility(View.GONE);
        holder.mTextView.setText(string);
    }

    private class ViewHolder {
        private TextView mTextView;
        private CheckBox mCheckBox;
    }
}