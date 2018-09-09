package com.devraj.licenseapp;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class QuestionsAdapter extends ArrayAdapter<Questions> {

    Context context;

    public QuestionsAdapter(@NonNull Context context, ArrayList<Questions> list) {
        super(context, 0, list);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.question_list, null);

         TextView questions = view.findViewById(R.id.questions);
         final Button option1 =view.findViewById(R.id.option1);
         final Button option2 =view.findViewById(R.id.option2);
         final Button option3 =view.findViewById(R.id.option3);
         final Button option4 =view.findViewById(R.id.option4);

         final Questions questioninfo = getItem(position);

        questions.setText(questioninfo.questions);
        option1.setText(questioninfo.option1);
        option2.setText(questioninfo.option2);
        option3.setText(questioninfo.option3);
        option4.setText(questioninfo.option4);

        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(questioninfo.option1.equals(questioninfo.answers)){
                    option1.setBackgroundColor(Color.BLUE);
//                    Toast.makeText(context, "Correct Answer", Toast.LENGTH_SHORT).show();

                            Snackbar.make(v, "Correct Answer", Snackbar.LENGTH_SHORT)
                                    .setAction("Action", null).show();
                }else{
                    option1.setBackgroundColor(Color.RED);
                    Snackbar.make(v, "Incorrect Answer", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
//                    Toast.makeText(context, "Incorrect Answer", Toast.LENGTH_SHORT).show();
                }
            }
        });

        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(questioninfo.option2.equals(questioninfo.answers)){
                    option2.setBackgroundColor(Color.BLUE);

                    Snackbar.make(v, "Correct Answer", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
//                    Toast.makeText(context, "Correct Answer", Toast.LENGTH_SHORT).show();
                }else{

                    option2.setBackgroundColor(Color.RED);
                    Snackbar.make(v, "Incorrect Answer", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
//                    Toast.makeText(context, "Incorrect Answer", Toast.LENGTH_SHORT).show();
                }
            }
        });

        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(questioninfo.option3.equals(questioninfo.answers)){
                    option3.setBackgroundColor(Color.BLUE);

                    Snackbar.make(v, "Correct Answer", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
//                    Toast.makeText(context, "Correct Answer", Toast.LENGTH_SHORT).show();
                }else{
                    option3.setBackgroundColor(Color.RED);
                    Snackbar.make(v, "Incorrect Answer", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                    Toast.makeText(context, "Incorrect Answer", Toast.LENGTH_SHORT).show();
                }
            }
        });

        option4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(questioninfo.option4.equals(questioninfo.answers)){
                    option4.setBackgroundColor(Color.BLUE);

                    Snackbar.make(v, "Correct Answer", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
//                    Toast.makeText(context, "Correct Answer", Toast.LENGTH_SHORT).show();
                }else{
                    option4.setBackgroundColor(Color.RED);
                    Snackbar.make(v, "Incorrect Answer", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
//                    Toast.makeText(context, "Incorrect Answer", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
}
