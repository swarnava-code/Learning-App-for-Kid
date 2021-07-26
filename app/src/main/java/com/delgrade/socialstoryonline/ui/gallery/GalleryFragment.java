package com.delgrade.socialstoryonline.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.delgrade.socialstoryonline.R;

public class GalleryFragment extends Fragment {
    ListView listView;
    TextView textView;
    String[] listItem;
    View root;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_gallery, container, false);

        listView=(ListView) root.findViewById(R.id.listView);
        textView=(TextView) root.findViewById(R.id.textView);
        listItem = getResources().getStringArray(R.array.array_courtesy);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, android.R.id.text1, listItem);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // TODO Auto-generated method stub
                String value=adapter.getItem(position);
                Toast.makeText(getContext(),value,Toast.LENGTH_SHORT).show();

            }
        });

        return root;
    }
}