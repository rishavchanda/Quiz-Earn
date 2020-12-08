package com.rishav.quizearn;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.rishav.quizearn.databinding.ActivityQuizBinding;

import java.util.ArrayList;
import java.util.Random;

public class Quiz extends AppCompatActivity {

    ActivityQuizBinding binding;

    ArrayList<Question> questions;
    int index=0;
    Question question;
    CountDownTimer timer;
    int correctAnswers = 0;
    ProgressDialog dialog;

    FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dialog = new ProgressDialog(this);
        dialog.setCanceledOnTouchOutside(false);

        dialog.show();

        database = FirebaseFirestore.getInstance();
        questions=new ArrayList<>();
        final String catId = getIntent().getStringExtra("catId");
        Random random = new Random();
        final int rand = random.nextInt(5);
        database.collection("categories")
                .document(catId)
                .collection("questions")
                .whereGreaterThanOrEqualTo("index",rand)
                .orderBy("index")
                .limit(10)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots.getDocuments().size() < 5){
                    database.collection("categories")
                            .document(catId)
                            .collection("questions")
                            .whereLessThanOrEqualTo("index",rand)
                            .orderBy("index")
                            .limit(10)
                            .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                for(DocumentSnapshot snapshot : queryDocumentSnapshots){
                                    Question question= snapshot.toObject(Question.class);
                                    questions.add(question);
                                 }
                            setNextQuestion();
                            dialog.dismiss();
                            System.out.println(rand);
                        }
                    });
                }else{
                           for(DocumentSnapshot snapshot : queryDocumentSnapshots){
                               Question question= snapshot.toObject(Question.class);
                               questions.add(question);
                           }
                    setNextQuestion();
                    dialog.dismiss();
                    System.out.println(rand);

                    }
                }
        });
        resetTimer();

        binding.quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                finish();
            }
        });


    }
    void resetTimer(){
        timer = new CountDownTimer(30000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                binding.timer.setText(String.valueOf(millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {
                reset();
                if((index+1) < questions.size()){
                    index++;
                    timer.cancel();
                    setNextQuestion();
                }else{
                    timer.cancel();
                    Intent intent = new Intent(Quiz.this,Result.class);
                    intent.putExtra("correct",correctAnswers);
                    intent.putExtra("total",questions.size());
                    startActivity(intent);
                    finish();
                }

            }
        };
    }

    void showAnswer(){
        if(question.getAnswer().equals(binding.option1.getText().toString()))
            binding.option1.setBackground(getResources().getDrawable(R.drawable.right_opt));
        else if(question.getAnswer().equals(binding.option2.getText().toString()))
            binding.option2.setBackground(getResources().getDrawable(R.drawable.right_opt));
        else if(question.getAnswer().equals(binding.option3.getText().toString()))
            binding.option3.setBackground(getResources().getDrawable(R.drawable.right_opt));
        else if(question.getAnswer().equals(binding.option4.getText().toString()))
        binding.option4.setBackground(getResources().getDrawable(R.drawable.right_opt));
    }

    void setNextQuestion(){
        if(timer!=null){
            timer.cancel();
        }
        timer.start();
        if(index < questions.size()){
            binding.quixcounter.setText(String.format("%d/%d",(index+1),questions.size()));
            question = questions.get(index);
            binding.question.setText(question.getQuestion());
            binding.option1.setText(question.getOption1());
            binding.option2.setText(question.getOption2());
            binding.option3.setText(question.getOption3());
            binding.option4.setText(question.getOption4());

        }
    }
    void checkAnswer(TextView textview){
        String selectedAnswer = textview.getText().toString();
        if(selectedAnswer.equals(question.getAnswer())){
            correctAnswers++;
            textview.setBackground(getResources().getDrawable(R.drawable.right_opt));
        }else {
            showAnswer();
            textview.setBackground(getResources().getDrawable(R.drawable.wrong_opt));
        }
    }

    void reset(){
        binding.option1.setBackground(getResources().getDrawable(R.drawable.textbox));
        binding.option2.setBackground(getResources().getDrawable(R.drawable.textbox));
        binding.option3.setBackground(getResources().getDrawable(R.drawable.textbox));
        binding.option4.setBackground(getResources().getDrawable(R.drawable.textbox));
    }

    public void onClick (View view){
              switch (view.getId()){
                  case R.id.option1:
                  case R.id.option2:
                  case R.id.option3:
                  case R.id.option4:
                      if (timer!=null){
                          timer.cancel();
                      }
                      TextView selected = (TextView)view;
                      checkAnswer(selected);
                      break;
                  case R.id.next:
                      reset();
                      timer.cancel();
                      if((index+1) < questions.size()){
                          index++;
                          resetTimer();
                          setNextQuestion();
                      }else{
                          Intent intent = new Intent(Quiz.this,Result.class);
                          intent.putExtra("correct",correctAnswers);
                          intent.putExtra("total",questions.size());
                          startActivity(intent);
                          finish();
                      }
                      break;
              }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        timer.cancel();
        finish();
    }
}