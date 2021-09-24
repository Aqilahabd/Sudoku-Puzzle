package cosc592.sudokupuzzle;


import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    private final int SIZE=9;
    private AppInterface appInterface;
    private Game game;
    private int[][] nums=new int[9][9];
    private final String FILE_NAME="Game";
    Button newBtn, saveBtn, resumeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newGame();

    }
    private void newGame()
    {
        Point screenSize=new Point();
        getWindowManager().getDefaultDisplay().getSize(screenSize);
        int width = screenSize.x/SIZE;
        appInterface=new AppInterface(this,SIZE,width);
        nums=appInterface.drawInitialBoard();

        game = new Game();
        game.setInitialNumbers(nums);
        game.setNumbers(nums);
        for(int i = 0; i < 9; i++)
            for(int j = 0; j < 9; j++) {
                TextChangeHandler temp = new TextChangeHandler(i,j);
                appInterface.setTextChangeHandler(temp, i, j);
            }
        setContentView(appInterface);

        ButtonHandler buttonHandler=new ButtonHandler();
        newBtn=findViewById(appInterface.getIdBtnNew());
        newBtn.setOnClickListener(buttonHandler);
        saveBtn=findViewById(appInterface.getIdBtnave());
        saveBtn.setOnClickListener(buttonHandler);
        resumeBtn=findViewById(appInterface.getIdBtnResume());
        resumeBtn.setOnClickListener(buttonHandler);
    }
    private void resumeGame(int[][] nums, int[][] initialNums)
    {
        Point screenSize=new Point();
        getWindowManager().getDefaultDisplay().getSize(screenSize);
        int width = screenSize.x/SIZE;
        appInterface=new AppInterface(this,SIZE,width);
        appInterface.drawInitalBoard(nums,initialNums);
        for(int i = 0; i < 9; i++)
            for(int j = 0; j < 9; j++) {
                TextChangeHandler temp = new TextChangeHandler(i,j);
                appInterface.setTextChangeHandler(temp, i, j);
            }
        setContentView(appInterface);

        ButtonHandler buttonHandler=new ButtonHandler();
        newBtn=findViewById(appInterface.getIdBtnNew());
        newBtn.setOnClickListener(buttonHandler);
        saveBtn=findViewById(appInterface.getIdBtnave());
        saveBtn.setOnClickListener(buttonHandler);
        resumeBtn=findViewById(appInterface.getIdBtnResume());
        resumeBtn.setOnClickListener(buttonHandler);
    }
    private class ButtonHandler implements View.OnClickListener
    {
        public void onClick(View v)
        {
            Button b=(Button)v;
            if(b.getId()==appInterface.getIdBtnNew())
            {
                newGame();
            }
            else if(b.getId()==appInterface.getIdBtnave())
            {
                try
                {
                    FileOutputStream fout = openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
                    String sudoku="";
                    int[][] nums = game.getNumbers();
                    int[][] initialNums = game.getInitialNumbers();
                    for(int i=0;i<9;i++)
                    {
                        for(int j=0;j<9;j++)
                        {
                            sudoku=sudoku+nums[i][j]+"";
                            if(initialNums[i][j]!=0)
                                sudoku=sudoku+"F";
                            sudoku=sudoku+" ";
                        }
                        sudoku=sudoku+"\n";
                    }
                    fout.write(sudoku.getBytes());
                    fout.close();
                }
                catch(Exception e){}
            }
            else if(b.getId()==appInterface.getIdBtnResume())
            {
                try
                {
                    FileInputStream fin=openFileInput(FILE_NAME);
                    InputStreamReader inputStreamReader=new InputStreamReader(fin);
                    BufferedReader bufferedReader=new BufferedReader(inputStreamReader);

                    String sudokuLine="";
                    String token="";
                    int[][] nums=new int[9][9];
                    int[][] initialNums=new int[9][9];
                    int i=0,j=0;
                    while((sudokuLine=bufferedReader.readLine())!=null)
                    {
                        StringTokenizer st=new StringTokenizer(sudokuLine);
                        j=0;
                        while(st.hasMoreTokens()) {
                            token = st.nextToken();
                            if((token.charAt(token.length()-1))=='F') {
                                initialNums[i][j] = Character.getNumericValue(token.charAt(0));
                            }
                            else {
                                initialNums[i][j] = 0;
                            }
                            nums[i][j]=Character.getNumericValue(token.charAt(0));
                            j++;
                        }
                        i++;
                    }

                    game.setInitialNumbers(initialNums);
                    game.setNumbers(nums);
                    resumeGame(nums,initialNums);

                }
                catch(Exception e){}
            }
        }
    }
    private class TextChangeHandler implements TextWatcher{
        private int x;
        private int y;

        public TextChangeHandler(int x, int y){
            this.x=x;
            this.y=y;
        }
        public void afterTextChanged(Editable e){
            int number;
            String numString=e.toString();
            try{
                number=Integer.parseInt(numString);
            }catch(Exception ex){
                number=0;
            }

            boolean isPresent= game.isValid(x,y,number);
            if(!isPresent)
            {
                if(number>0 && number<=9)
                {
                    game.setNum(x,y,number);
                    appInterface.removeTextchangehandler(this, x, y);
                    appInterface.setText(x, y, number);
                    appInterface.setTextchangehandler(this, x, y);
                }
                else{
                    game.removeNum(x,y);
                    appInterface.removeTextchangehandler(this,x,y);
                    appInterface.removeText(x, y);
                    appInterface.setTextchangehandler(this,x,y);
                }
            }
            else{
                game.removeNum(x,y);
                appInterface.removeTextchangehandler(this,x,y);
                appInterface.removeText(x,y);
                appInterface.setTextChangeHandler(this,x,y);
            }
            if(game.solved()){
                displayMessage("Game won");
            }
        }
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2){

        }
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2){

        }
    }
    private void displayMessage(String message){

        Toast toast = Toast.makeText(this,message, Toast.LENGTH_SHORT);
        toast.show();
    }
}
