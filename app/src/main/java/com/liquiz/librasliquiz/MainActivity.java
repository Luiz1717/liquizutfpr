package com.liquiz.librasliquiz;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.vending.expansion.zipfile.APKExpansionSupport;
import com.android.vending.expansion.zipfile.ZipResourceFile;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Vector;

import pl.droidsonroids.gif.GifDrawable;
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
            {"o", "o", "s", "b", "f"},
            {"p", "p", "r", "q", "s"},
            {"q", "q", "h", "a", "p"},
            {"r", "r", "a", "t", "f"},
            {"s", "s", "r", "t", "k"},
            {"t", "t", "h", "c", "m"},
            {"u", "u", "v", "b", "d"},
            {"v", "v", "u", "a", "b"},
            {"w", "w", "v", "y", "s"},
            {"x", "x", "h", "y", "s"},
            {"y", "y", "h", "m", "z"},
            {"z", "z", "x", "y", "s"},
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
            {"nos", "nós", "prazer", "todos", "meu"},
            {"obrigado", "obrigado", "prazer", "nada", "pensar"},
            {"oi", "oi", "prazer", "bom dia", "seu"},
            {"parabens", "parabéns", "prazer", "obrigado", "feliz"},
            {"pensar", "pensar", "prazer", "bom dia", "conhecer"},
            {"porfavor", "por favor", "parabéns", "pensar", "seu"},
            {"prazer", "prazer", "pensar", "parabéns", "meu"},
            {"seu", "seu", "prazer", "ela", "meu"},
            {"seunome", "seu nome", "meu", "bom dia", "seu"},
            {"sintomuito", "sinto muito", "conhecer", "pensar", "triste"},
            {"tchau", "tchau", "obrigado", "oi", "seu"},
            {"tudobem", "tudo bem", "bem", "pensar", "oi"},
            {"voce", "você", "ela", "eu", "seu"},
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
            {"noiva", "noiva", "esposa", "casado", "mulher"},
            {"nora", "nora", "neto", "neta", "sogra"},
            {"primo", "primo", "homem", "mãe", "irmão"},
            {"prima", "prima", "primo", "neta", "irmã"},
            {"sogra", "sogra", "mulher", "sogro", "sobrinha"},
            {"sogro", "sogro", "filha", "sogra", "sobrinho"},
            {"sobrinha", "sobrinha", "mulher", "sogra", "sobrinho"},
            {"sobrinho", "sobrinho", "homem", "sogro", "sobrinha"},
            {"tio", "tio", "homem", "pai", "vovô"},
            {"tia", "tia", "mulher", "filha", "vovó"},
            {"viuva", "viúva", "homem", "mulher", "neta"},
            {"vovo1", "vovó", "mulher", "pai", "filha"},
            {"vovo2", "vovô", "homem", "pai", "filho"},
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
            {"pera2", "perâ", "amora", "cereja", "morango"},
            {"pessego2", "pêssego", "morango", "cereja", "melancia"},
            {"tangerina", "tangerina", "laranja", "lima", "figo"},
            {"uva", "uva", "amora", "cereja", "kiwi"},
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
            {"a60", "60", "30", "10", "50"},
            {"a70", "70", "80", "50", "60"},
            {"a80", "80", "30", "20", "90"},
            {"a90", "90", "10", "50", "80"},
            {"a100", "100", "110", "120", "160"},
            {"a120", "120", "110", "140", "150"},
            {"a140", "140", "110", "120", "130"},
            {"a160", "160", "150", "120", "170"},
            {"a180", "180", "130", "190", "160"},
            {"a200", "200", "100", "220", "160"},
            {"a250", "250", "100", "200", "360"},
            {"a300", "300", "100", "200", "400"},
            {"a400", "400", "500", "600", "700"},
            {"a450", "450", "550", "650", "750"},
            {"a500", "500", "400", "300", "200"},
            {"a600", "600", "500", "900", "700"},
            {"a700", "700", "500", "600", "300"},
            {"a800", "800", "500", "600", "400"},
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.v("teste",nomeArqExp(this,4));
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


        countLabel.setText("Questão  " + quizCount);


        Random random = new Random();
        int randomNum = random.nextInt(quizArray.size());


        ArrayList<String> quiz = quizArray.get(randomNum);

        try {
            //acho que a versão do aplicativo que você está trabalhando é a 4
            ZipResourceFile expansionFile =
                    APKExpansionSupport.getAPKExpansionZipFile(this,
                            5,0);
            InputStream fileStream = expansionFile.getInputStream(quiz.get(0)+".gif");
            BufferedInputStream bis = new BufferedInputStream(fileStream);
            GifDrawable gifFromStream = new GifDrawable(bis);

            QuestionImage.setImageDrawable(gifFromStream);

        } catch (IOException e) {
            e.printStackTrace();
        }


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

        GifImageView questionImage =  new GifImageView(this);
        Button answerBtn = findViewById(view.getId());

        Intent intent = getIntent();

        boolean acertou = intent.getBooleanExtra("acertou", false);

        if (answerBtn.getText().equals(rightAnswer)) {
            questionImage.setImageResource(R.drawable.acertou1);
            rightAnswerCount++;

        } else {
            questionImage.setImageResource(R.drawable.errou1);
        }

        Toast t = new Toast(getApplicationContext());
        t.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        t.setDuration(Toast.LENGTH_LONG);
        t.setView(questionImage);
        t.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (quizCount == QUIZ_COUNT) {
                    // Show Result.
                    Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                    intent.putExtra("RIGHT_ANSWER_COUNT", rightAnswerCount);
                    startActivity(intent);
                } else {
                    quizCount++;
                    showNextQuiz();
                }

            }
        }, 5000); //executa método run após 5segundos

    }

}




