package com.example.goodhabits;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    TextView totalQuestionsTextView;
    TextView questionTextView;
    Button ansA, ansB, ansC, ansD;
    Button submitBtn;

    int score = 0;
    // Max number of questions is 5 rn
    int totalQuestions = 5;
    int currentQuestionIndex = 0;
    int randomQ = 0;
    String selectedAnswer = "";
    List<Integer> questions_taken = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        totalQuestionsTextView = findViewById(R.id.total_questions);
        questionTextView = findViewById(R.id.question);

        ansA = findViewById(R.id.ans_A);
        ansB = findViewById(R.id.ans_B);
        ansC = findViewById(R.id.ans_C);
        ansD = findViewById(R.id.ans_D);

        submitBtn = findViewById(R.id.submit);

        ansA.setOnClickListener(this);
        ansB.setOnClickListener(this);
        ansC.setOnClickListener(this);
        ansD.setOnClickListener(this);
        submitBtn.setOnClickListener(this);

        totalQuestionsTextView.setText("Total Questions: " + (totalQuestions));

        loadNewQuestion();
    }

    @Override
    public void onClick(View v) {

        ansA.setBackgroundColor(Color.WHITE);
        ansB.setBackgroundColor(Color.WHITE);
        ansC.setBackgroundColor(Color.WHITE);
        ansD.setBackgroundColor(Color.WHITE);

        Button clickedButton = (Button) v;
        if(clickedButton.getId()==R.id.submit){
            if(selectedAnswer.equals(QuestionAnswer.correctAnswers[randomQ])) {
                score++;
            }
            currentQuestionIndex++;
            loadNewQuestion();

        } else{
            selectedAnswer = clickedButton.getText().toString();
            clickedButton.setBackgroundColor(Color.GRAY);
        }
    }

    void loadNewQuestion(){
        randomQ = getRandomIndex(QuestionAnswer.question.length);
        if (currentQuestionIndex == totalQuestions){
            finishQuiz();
            return;
        }

        if(questions_taken.contains(randomQ)){
            loadNewQuestion();
        } else {
            questions_taken.add(randomQ);
            questionTextView.setText(QuestionAnswer.question[randomQ]);
            ansA.setText(QuestionAnswer.choices[randomQ][0]);
            ansB.setText(QuestionAnswer.choices[randomQ][1]);
            ansC.setText(QuestionAnswer.choices[randomQ][2]);
            ansD.setText(QuestionAnswer.choices[randomQ][3]);
        }
    }

    void finishQuiz(){
        String passStatus = "";
        if(score > totalQuestions*0.50){
            passStatus = "Passed!";

        } else {
            passStatus = "Try harder!";
        }

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userID = user.getUid();
        Query usersReference = FirebaseDatabase.getInstance().getReference("Users");

        usersReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    // update user points
                    String current_points = snapshot.child(userID).child("points").getValue().toString();
                    int new_points = Integer.parseInt(current_points) + score;
                    snapshot.getRef().child(userID).child("points").setValue(Integer.toString(new_points));


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(QuizActivity.this, "ERROR: QuizActivity", Toast.LENGTH_LONG).show();
            }
        });


        new AlertDialog.Builder(this).setTitle(passStatus)
                .setMessage("Score: " + score + "/" + totalQuestions)
                .setPositiveButton("Try again", (dialogInterface, i) -> restartQuiz())
                .setNegativeButton("Exit", (dialogInterface, i) -> goHome())
                .setCancelable(false)
                .show();
    }

    void restartQuiz(){
        score = 0;
        currentQuestionIndex = 0;
        loadNewQuestion();
    }

    void goHome(){
        startActivity(new Intent(QuizActivity.this, HomepageActivity.class));
    }

    private int getRandomIndex(int arrayLength) {
        Random r = new Random();
        return r.nextInt(arrayLength);
    }
}

