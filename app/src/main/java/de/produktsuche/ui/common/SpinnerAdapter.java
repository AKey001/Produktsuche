package de.produktsuche.ui.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import de.produktsuche.R;

public class SpinnerAdapter extends ArrayAdapter<String> {
    LayoutInflater inflater;

    public SpinnerAdapter(@NonNull Context context, int resource, int textView, List<String> list) {
        super(context, resource, textView, list);
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String item = getItem(position);
        View root = inflater.inflate(R.layout.spinner_item, null, true);

        TextView text = (TextView) root.findViewById(R.id.item);
        text.setText(item);

        return root;
    }
}
