package cosc592.sudokupuzzle;

import android.content.Context;
import android.graphics.Color;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.RelativeLayout;

public class AppInterface extends RelativeLayout {
    private int size;
    private EditText[][] board;
    private Button btnNew, btnSave, btnResume;

    public AppInterface(Context context, int size, int width)
    {
        super(context);
        final int D = (int) (getResources().getDisplayMetrics().density);
        this.size = size;
        board = new EditText[size][size];
        RelativeLayout.LayoutParams paramsRelative=new RelativeLayout.LayoutParams(0,0);
        GridLayout gridOne=new GridLayout(context);
        gridOne.setId(GridLayout.generateViewId());
        gridOne.setRowCount(9);
        gridOne.setColumnCount(9);
        paramsRelative.width=LayoutParams.WRAP_CONTENT;
        paramsRelative.height=LayoutParams.WRAP_CONTENT;
        gridOne.setLayoutParams(paramsRelative);
        for(int i=0;i<9;i++)
        {
            for(int j=0;j<9;j++)
            {
                board[i][j]=new EditText(context);
                board[i][j].setBackgroundColor(Color.parseColor("#9d8b8a"));
                board[i][j].setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                board[i][j].setTextColor(Color.parseColor("#000000"));
                board[i][j].setInputType(InputType.TYPE_CLASS_NUMBER);
                board[i][j].setGravity(Gravity.CENTER);
                GridLayout.LayoutParams params=new GridLayout.LayoutParams();
                params.width=width;
                params.height=width;
                params.rowSpec = GridLayout.spec(i,1);
                params.columnSpec = GridLayout.spec(j,1);
                if(i==0)
                {
                    params.topMargin=30*D;
                    params.bottomMargin=1;
                }
                else
                    params.topMargin = params.bottomMargin = 1;
                params.leftMargin = params.rightMargin = 1;
                board[i][j].setLayoutParams(params);
                gridOne.addView(board[i][j]);
            }
        }
        addView(gridOne);

        RelativeLayout.LayoutParams paramsRelBtn=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        btnNew=new Button(context);
        btnNew.setTextColor(Color.parseColor("#000000"));
        btnNew.setBackgroundColor(Color.parseColor("#4DD0E1"));
        btnNew.setPadding(10*D,10*D,10*D,10*D);
        btnNew.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
        btnNew.setText("New");
        btnNew.setId(Button.generateViewId());
        paramsRelBtn.addRule(RelativeLayout.BELOW,gridOne.getId());
        paramsRelBtn.leftMargin=40*D;
        paramsRelBtn.topMargin=50*D;
        btnNew.setLayoutParams(paramsRelBtn);
        addView(btnNew);

        paramsRelBtn=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        btnSave=new Button(context);
        btnSave.setTextColor(Color.parseColor("#000000"));
        btnSave.setBackgroundColor(Color.parseColor("#4DD0E1"));
        btnSave.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
        btnSave.setText("Save");
        btnSave.setPadding(10*D,10*D,10*D,10*D);
        btnSave.setId(Button.generateViewId());
        paramsRelBtn.leftMargin=40*D;
        paramsRelBtn.topMargin=50*D;
        paramsRelBtn.addRule(RelativeLayout.RIGHT_OF,btnNew.getId());
        paramsRelBtn.addRule(RelativeLayout.ALIGN_BOTTOM, btnNew.getId());
        btnSave.setLayoutParams(paramsRelBtn);
        addView(btnSave);

        paramsRelBtn=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        btnResume=new Button(context);
        btnResume.setTextColor(Color.parseColor("#000000"));
        btnResume.setBackgroundColor(Color.parseColor("#4DD0E1"));
        btnResume.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
        btnResume.setText("Resume");
        btnResume.setPadding(10*D,10*D,10*D,10*D);
        btnResume.setId(Button.generateViewId());
        paramsRelBtn.leftMargin=40*D;
        paramsRelBtn.topMargin=50*D;
        paramsRelBtn.addRule(RelativeLayout.RIGHT_OF,btnSave.getId());
        paramsRelBtn.addRule(RelativeLayout.ALIGN_BOTTOM,btnSave.getId());
        btnResume.setLayoutParams(paramsRelBtn);
        addView(btnResume);

    }
    public int[][] drawInitialBoard()
    {
        Sudoku s=new Sudoku();
        int[][] nums=s.generate();
        for(int i=0;i<9;i++) {
            for (int j = 0; j < 9; j++) {
                if(nums[i][j]==0)
                    board[i][j].setText("");
                else {
                    board[i][j].setText(nums[i][j] + "");
                    board[i][j].setBackgroundColor(Color.parseColor("#A4C1EE"));
                    board[i][j].setEnabled(false);
                }
            }
        }
        return nums;
    }
    public void drawInitalBoard(int[][] nums, int[][] initialNums)
    {
        for(int i=0;i<9;i++) {
            for (int j = 0; j < 9; j++) {
                if(nums[i][j]==0)
                    board[i][j].setText("");
                else if(nums[i][j]!=0 && initialNums[i][j]==0)
                    board[i][j].setText(nums[i][j]+"");
                else {
                    board[i][j].setText(nums[i][j] + "");
                    board[i][j].setBackgroundColor(Color.parseColor("#A4C1EE"));
                    board[i][j].setEnabled(false);
                }
            }
        }
    }
    public void setTextChangeHandler(TextWatcher temp, int i, int j)
    {
        board[i][j].addTextChangedListener(temp);
    }
    public void setText(int i, int j, int num){
        board[i][j].setText(num+"");
    }
    public void removeText(int i, int j){
        board[i][j].setText("");
    }
    public void setTextchangehandler(TextWatcher textwatcher, int x, int y){
        board[x][y].addTextChangedListener(textwatcher);
    }
    public void removeTextchangehandler(TextWatcher textwatcher, int x, int y){
        board[x][y].removeTextChangedListener(textwatcher);
    }
    public int getIdBtnNew()
    {
        return btnNew.getId();
    }
    public int getIdBtnave()
    {
        return btnSave.getId();
    }
    public int getIdBtnResume()
    {
        return btnResume.getId();
    }
}

