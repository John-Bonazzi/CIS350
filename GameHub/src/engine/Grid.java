package engine;
import javax.swing.*;
import java.awt.*;
public class Grid{

  /*
  * defines the amount of rows in the grid.
  */
  private int rows;
  /*
  * defines the amount of columns in the grid.
  */
  private int cols;
  /*
  * This variable is the bounding box in which the grid will reside.
  * Everything else is based on this.
  */
  private Dimension size;


  /*
  * A list of endpoints for drawing lines to create the rows.
  * Listed as such: [ line1:y ,line2:y, line3:y ... ]
  */
  private double[] rowEndpoints;
  /*
  * A list of endpoints for drawing lines to create the columns.
  * Listed as such: [ line1:x ,line2:x, line3:x ... ]
  */
  private double[] colEndpoints;




  /*
  * Alternate constructor for grid.
  * @param rowNum number of rows desired
  * @param colNum number of columns desired
  * @param windowWidth available width for grid
  * @param windowHeight available height for grid
  */
  public Grid(int rowNum, int colNum, int windowWidth, int windowHeight){
    //calls other constructor
    this(rowNum,colNum,new Dimension(windowWidth,windowHeight));
  }

  /*
  * Constructor for grid.
  * @param rowNum number of rows desired
  * @param colNum number of columns desired
  * @param windowSize Dimension object indicating the size of the available space.
  */
  public Grid(int rowNum, int colNum, Dimension windowSize){
    this.rows=rowNum;
    this.cols=colNum;
    this.size=windowSize;

    rowEndpoints=this.getRowPoints();
    colEndpoints=this.getColPoints();
  }

  private double[] getRowPoints(){
    double height=this.size.height;
    int numPoints= ( this.rows - 1 );

    double[] output=new double[numPoints];

    double c=0;
    double squareHeight = height / this.rows;

    for(int i=0; i<numPoints; i++){
      output[i]= c;
      c += squareHeight;
    }


    return output;





  }
  private double[] getColPoints(){
    double wid = this.size.width;
    int numPoints= ( this.cols - 1 );
    double[] output=new double[numPoints];

    double c=0;
    double squareWidth = wid / this.cols;

    for(int i=0; i<numPoints; i++){
      output[i]= c;
      c += squareWidth;
    }

    return output;
  }

}
