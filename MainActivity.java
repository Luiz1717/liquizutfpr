package com.liquiz.utfprliquiz;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Vector;

import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity {



    private final static String EXP_PATH = "/Android/obb/";

    public static String nomeArqExp(Context ctx, int mainVersion) {
        String packageName = ctx.getPackageName();
        Vector<String> ret = new Vector<String>();
        if (Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED)) {
            // Build the full path to the app's expansion files
            File root = Environment.getExternalStorageDirectory();
            File expPath = new File(root.toString() + EXP_PATH + packageName);

            // Check that expansion file path exists
            //if (expPath.exists()) {
            if (mainVersion > 0) {
                String strMainPath = expPath + File.separator + "main." +
                        mainVersion + "." + packageName + ".obb";
                return strMainPath;
            }
        }
        return "erro";
    }


    private TextView countLabel;
    private FeedbackManager feedbackManager;

    private GifImageView QuestionImage;
    private Button answerBtn1;
    private Button answerBtn2;
    private Button answerBtn3;
    private Button answerBtn4;

    private String rightAnswer;
    private int rightAnswerCount = 0;
    private int quizCount = 1;
    static final private int QUIZ_COUNT = 15;

    ArrayList<ArrayList<String>> quizArray = new ArrayList<>();

    String quizData[][] = {
            // {"Image Name", "Right Answer", "Choice1", "Choice2", "Choice3"}
            {"a", "a", "c", "f", "d"},
            {"b", "b", "c", "d", "a"},
            {"c", "c", "g", "h", "k"},
            {"d", "d", "a", "b", "c"},
            {"e", "e", "h", "a", "s"},
            {"f", "f", "e", "a", "g"},
            {"g", "g", "h", "f", "a"},
            {"h", "h", "g", "f", "s"},
            {"i", "i", "h", "a", "s"},
            {"j", "j", "g", "p", "s"},
            {"k", "k", "g", "h", "m"},
            {"l", "l", "n", "m", "b"},
            {"m", "m", "s", "b", "f"},
            {"n", "n", "s", "a", "c"},
            {"aprender", "aprender", "prazer", "bom dia", "seu"},
            {"bemvindo", "bem vindo", "conhecer", "bom dia", "nome"},
            {"boanoite", "boa noite", "boa tarde", "bom dia", "seu"},
            {"boatarde", "boa tarde", "boa noite", "bom dia", "boa madrugada"},
            {"bomdia", "bom dia", "boa noite", "boa tarde", "boa madrugada"},
            {"conhecer", "conhecer", "prazer", "bom dia", "você"},
            {"ela", "ela", "prazer", "eles", "seu"},
            {"elas", "elas", "prazer", "ela", "seu"},
            {"eles", "eles", "meu", "elas", "ela"},
            {"entender", "entender", "pensar", "aprender", "oi"},
            {"estudar", "estudar", "prazer", "pensar", "oi"},
            {"eu", "eu", "meu", "você", "seu"},
            {"idade", "idade", "pensar", "parabéns", "nome"},
            {"meu", "meu", "seu", "ela", "você"},
            {"meunome", "meu nome", "prazer", "conhecer", "meu"},
            {"nada", "nada", "pensar", "bom dia", "obrigado"},
            {"adotado1", "adotado", "filho", "tio", "meu"},
            {"casado", "casado", "casada", "esposa", "meu"},
            {"cunhado", "cunhado", "homem", "pai", "vovô"},
            {"cunhada", "cunhada", "mulher", "menina", "vovó"},
            {"familia", "família", "filho", "meu", "vovó"},
            {"filha", "filha", "menina", "mulher", "vovó"},
            {"filho", "filho", "homem", "pai", "primo"},
            {"gemeos", "gêmeos", "homem", "pai", "vovô"},
            {"gravida", "grávida", "mulher", "mãe", "vovó"},
            {"irmao", "irmão", "primo", "pai", "homem"},
            {"irma", "irmã", "prima", "mulher", "sobrinha"},
            {"mae", "mãe", "homem", "mulher", "sobrinha"},
            {"mulher", "mulher", "homem", "pai", "tio"},
            {"namorada", "namorada", "mulher", "pai", "homem"},
            {"namorado", "namorado", "mulher", "sobrinha", "homem"},
            {"neta", "neta", "neto", "pai", "mulher"},
            {"neto", "neto", "neta", "vovó", "mulher"},
            {"abacate", "abacate", "abóbora", "maça", "figo"},
            {"abacaxi2", "abacaxi", "abacate", "maça", "uva"},
            {"amora", "amora", "laranja", "maça", "uva"},
            {"banana", "banana", "manga", "melancia", "figo"},
            {"caju", "caju", "amora", "cereja", "figo"},
            {"caqui", "caqui", "amora", "cereja", "caju"},
            {"carambola", "carambola", "amora", "cereja", "caju"},
            {"cereja", "cereja", "caju", "laranja", "figo"},
            {"coco", "cocô", "manga", "maça", "figo"},
            {"figo", "figo", "abobora", "maça", "melancia"},
            {"goiaba", "goiaba", "caju", "cereja", "figo"},
            {"graviola", "graviola", "amora", "goiaba", "figo"},
            {"guarana", "guaraná", "goiaba", "cereja", "uva"},
            {"jabuticada", "jabuticada", "amora", "cereja", "uva"},
            {"jaca", "jaca", "caju", "uva", "figo"},
            {"kiwi", "kiwi", "jaca", "manga", "morango"},
            {"laranja", "laranja", "abobora", "limão", "figo"},
            {"limao", "limão", "laranja", "manga", "acabaxi"},
            {"maca", "maça", "morango", "limão", "manga"},
            {"mamao", "mamão", "amora", "cereja", "melancia"},
            {"manga", "manga", "melancia", "maça", "caju"},
            {"maracuja", "maracujá", "amora", "manga", "morango"},
            {"melancia", "melancia", "manga", "morango", "kiwi"},
            {"melao", "melão", "manga", "melancia", "figo"},
            {"morango", "morango", "kiwi", "cereja", "uva"},
            {"a0", "0", "2", "1", "6"},
            {"a1", "1", "2", "3", "4"},
            {"a2", "2", "3", "1", "6"},
            {"a3", "3", "2", "8", "7"},
            {"a4", "4", "3", "1", "2"},
            {"a5", "5", "2", "1", "9"},
            {"a6", "6", "2", "7", "8"},
            {"a7", "7", "5", "4", "6"},
            {"a8", "8", "4", "1", "5"},
            {"a9", "9", "4", "5", "6"},
            {"a10", "10", "4", "5", "8"},
            {"a20", "20", "30", "50", "60"},
            {"a30", "30", "40", "50", "80"},
            {"a40", "40", "30", "90", "60"},
            {"a50", "50", "30", "20", "60"},
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        feedbackManager = new FeedbackManager(this);

        Log.v("teste", nomeArqExp(this, 9));
        countLabel = findViewById(R.id.countLabel);
        QuestionImage = findViewById(R.id.questionImage);
        answerBtn1 = findViewById(R.id.answerBtn1);
        answerBtn2 = findViewById(R.id.answerBtn2);
        answerBtn3 = findViewById(R.id.answerBtn3);
        answerBtn4 = findViewById(R.id.answerBtn4);

        int quizCategory = getIntent().getIntExtra("QUIZ_CATEGORY", 0);
        Log.v("CATEGORY", quizCategory + "");


        // Create quizArray from quizData.
        for (int i = 0; i < quizData.length; i++) {
            // Prepare array.
            ArrayList<String> tmpArray = new ArrayList<>();
            tmpArray.add(quizData[i][0]); // Image Name
            tmpArray.add(quizData[i][1]); // Right Answer
            tmpArray.add(quizData[i][2]); // Choice1
            tmpArray.add(quizData[i][3]); // Choice2
            tmpArray.add(quizData[i][4]); // Choice3


            // Add tmpArray to quizArray.
            quizArray.add(tmpArray);
        }
        showNextQuiz();
    }

    public void showNextQuiz() {


        countLabel.setText("QUESTÃO  " + quizCount);


        Random random = new Random();
        int randomNum = random.nextInt(quizArray.size());


        ArrayList<String> quiz = quizArray.get(randomNum);

        QuestionImage.setImageResource(
                getResources().getIdentifier(quiz.get(0), "drawable", getPackageName()));

        rightAnswer = quiz.get(1);

        quiz.remove(0);
        Collections.shuffle(quiz);


        answerBtn1.setText(quiz.get(0));
        answerBtn2.setText(quiz.get(1));
        answerBtn3.setText(quiz.get(2));
        answerBtn4.setText(quiz.get(3));


        quizArray.remove(randomNum);

    }

    public void checkAnswer(View view) {
        // 1. Obter elementos da UI
        Button answerBtn = (Button) view;
        GifImageView feedbackImage = new GifImageView(this);

        // 2. Verificar resposta
        boolean isCorrect = answerBtn.getText().toString().equals(rightAnswer);

        // 3. Configurar feedback visual
        if (isCorrect) {
            feedbackImage.setImageResource(R.drawable.acertou);
            rightAnswerCount++;
        } else {
            feedbackImage.setImageResource(R.drawable.errou);
            Log.d("FEEDBACK", "Resposta incorreta - Exibindo feedback");
        }

        // 4. Feedback tátil melhorado
        vibrate(isCorrect);

        // 5. Toast customizado (com tratamento de erro)
        try {
            Toast feedbackToast = new Toast(this);
            feedbackToast.setGravity(Gravity.CENTER, 0, 0);
            feedbackToast.setDuration(Toast.LENGTH_LONG);
            feedbackToast.setView(feedbackImage);
            feedbackToast.show();
        } catch (Exception e) {
            Log.e("FEEDBACK", "Erro ao exibir toast: " + e.getMessage());
            // Fallback: exibe toast simples
            Toast.makeText(this, isCorrect ? "Acertou!" : "Errou!", Toast.LENGTH_LONG).show();
        }

        // 6. Próxima questão com delay
        new Handler().postDelayed(() -> {
            if (quizCount < QUIZ_COUNT) {
                quizCount++;
                showNextQuiz();
            } else {
                showResults();
            }
        }, 2000); // 2 segundos
    }

    // Método auxiliar para vibração
    private void vibrate(boolean isSuccess) {
        try {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            if (vibrator != null && vibrator.hasVibrator()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    if (isSuccess) {
                        vibrator.vibrate(VibrationEffect.createOneShot(150, 255));
                    } else {
                        long[] pattern = {0, 100, 50, 100};
                        vibrator.vibrate(VibrationEffect.createWaveform(pattern, -1));
                    }
                } else {
                    // Compatibilidade com versões antigas
                    vibrator.vibrate(isSuccess ? 150 : 200);
                }
            }
        } catch (Exception e) {
            Log.e("FEEDBACK", "Erro na vibração: " + e.getMessage());
        }
    }
    private void showResults() {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("RIGHT_ANSWER_COUNT", rightAnswerCount);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}






