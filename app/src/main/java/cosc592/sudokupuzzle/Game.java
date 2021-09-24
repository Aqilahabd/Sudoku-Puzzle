package cosc592.sudokupuzzle;


public class Game {
    private int[][] numbers=new int[9][9];
    private int[][] initNumbers=new int[9][9];

    public void setNumbers(int[][] numbers){
        for(int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                this.numbers[i][j]=numbers[i][j];
            }
        }
    }

    public void setNum(int x, int y, int number)
    {
        this.numbers[x][y]=number;
    }
    public int[][] getNumbers(){ return numbers; }
    public void setInitialNumbers(int[][] numbers)
    {
        for(int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                this.initNumbers[i][j] = numbers[i][j];
            }
        }
    }
    public int[][] getInitialNumbers()
    {
        return initNumbers;
    }
    public void removeNum(int x, int y)
    {
        this.numbers[x][y]=0;
    }
    public boolean isValid(int x, int y, int number)
    {
        if(isPresentRow(x,number) || isPresentCol(y,number) || isPresentGrid(x,y,number))
            return true;
        else
            return false;
    }
    public boolean isPresentRow(int row,int number){
        boolean isPresent=false;
        for(int i=0;i<9;i++){
            if(numbers[row][i]==number) {
                isPresent = true;
            }
        }
        return isPresent;
    }
    public boolean isPresentCol(int col, int number){
        boolean isPresent=false;
        for(int i=0;i<9;i++){
            if(numbers[i][col]==number) {
                isPresent = true;
            }
        }
        return isPresent;
    }
    public boolean isPresentGrid(int row, int col, int number){
        boolean isPresent=false;
        int x1 = 3*(row/3);
        int y1 = 3*(col/3);
        int x2 = x1+2;
        int y2 = y1+2;
        for(int k=x1;k<=x2;k++) {
            for (int l = y1; l <= y2; l++) {
                if (numbers[k][l] == number) {
                    isPresent = true;
                }
            }
        }
        return isPresent;
    }
    public boolean solved(){
        boolean isBoardFull = true;
        for(int i = 0; i < 9; i++)
            for(int j = 0; j < 9; j++) {
                if(numbers[i][j] == 0){
                    isBoardFull = false;
                }
            }
        return isBoardFull;
    }
}
