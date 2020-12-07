package com.rishav.quizearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.rishav.quizearn.databinding.ActivityQuizBinding;

import java.util.ArrayList;

public class Quiz extends AppCompatActivity {

    ActivityQuizBinding binding;

    ArrayList<Question> questions;
    int index=0;
    Question question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

       questions=new ArrayList<>();

       questions.add(new Question("What is earth?"
       ,"Planet","Sun","Human","Car","Planet"));
        questions.add(new Question("What is you?"
                ,"Planet","Sun","Human","Car","Human"));
        questions.add(new Question("What is light?"
                ,"Planet","Sun","Human","Car","Sun"));
        questions.add(new Question("What is earth?"
                ,"Planet","Sun","Human","Car","Planet"));

        setNextQuestion();
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
        if(index < questions.size()){
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
                      TextView selected = (TextView)view;
                      checkAnswer(selected);
                      break;
                  case R.id.next:
                      reset();
                      if(index < questions.size()){
                          index++;
                          setNextQuestion();
                      }else{
                          startActivity(new Intent(this,Result.class));
                      }
                      break;
              }
    }
}