package com.devraj.licenseapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class QuestionsFragment extends Fragment {

    AQuery aQuery;
    TextView questions;
    Button option1, option2, option3, option4, next;
    String fetchurl = "http://192.168.0.106/license/select.php";

    ListView listview;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_questions, null);

        aQuery = new AQuery(getActivity());
        listview = view.findViewById(R.id.listview);
//        questions = view.findViewById(R.id.questions);
//        option1 = view.findViewById(R.id.option1);
//        option2 = view.findViewById(R.id.option2);
//        option3 = view.findViewById(R.id.option3);
//        option4 = view.findViewById(R.id.option4);
//        next = view.findViewById(R.id.next);
//
//        next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        fetchData();
        return view;
    }

    public void fetchData() {
        aQuery.ajax(fetchurl, JSONArray.class, new AjaxCallback<JSONArray>() {
            @Override
            public void callback(String url, JSONArray array, AjaxStatus status) {
                super.callback(url, array, status);
                Log.i("response", "response" + array);

                if (LaUtils.checkNetworkAvailability(getActivity())) {
                    ArrayList<Questions> list = new ArrayList<>();
                    for (int i = 0; i < array.length(); i++) {
                        try {
                            JSONObject jsonObject = array.getJSONObject(i);
                            Questions questioninfo = new Questions();

                            questioninfo.id = jsonObject.getString("id");
                            questioninfo.questions = jsonObject.getString("question");
                            questioninfo.option1 = jsonObject.getString("option1");
                            questioninfo.option2 = jsonObject.getString("option2");
                            questioninfo.option3 = jsonObject.getString("option3");
                            questioninfo.option4 = jsonObject.getString("option4");
                            questioninfo.answers = jsonObject.getString("answer");

                            list.add(questioninfo);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    listview.setAdapter(new QuestionsAdapter(getActivity(), list));
                } else {
                    Snackbar.make(getView(), "Please connect to the Internet", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Action", null).show();
                }
            }
        });
    }
}





