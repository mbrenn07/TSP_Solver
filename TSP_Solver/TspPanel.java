import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.*;
import java.awt.Color;
import java.awt.geom.Line2D;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class TspPanel extends JPanel
{
            
   private static final int DELAY=100;	//#miliseconds delay between each time the enemy moves and screen refreshes for the timer
   
   private Timer t;							//used to set the speed of the enemy that moves around the screen
   
   //define 2 queues for lanes of traffic (main & maple)
   //define values for delay and prob for each lane (mainDelay, mainProb, mapleDelay, mapleProb)
   int mainDelay, mainProb, mapleDelay, mapleProb;
   int screenXSize;
   int screenYSize;
   int nodeSize; 
   int arrayXSize;
   int arrayYSize;
   boolean caseE;
   boolean caseE2;
   ArrayList<Node> nodes;
   ArrayList<Node> nodesD = new ArrayList();
   int sizeX;	//size of cell being drawn
   int sizeY;	//size of cell being drawn
   private boolean mainGreen; //(true: green on main & red on maple, false: red on main & green on maple)
   private int frameNum;	//count of frames to keep track of time
   int cycles = 10000;
   //define counter for number of cycles completed (numCycles)
   int numCycles = 0;
   public TspPanel(int x, int y, int arrayX, int arrayY, ArrayList<Node> nodesX, boolean caseETest)
   {
      //create 2 queues for lanes of traffic
      screenXSize = x;
      screenYSize = y;
      caseE= caseETest;
      sizeX=x/arrayX;
      sizeY=y/arrayY;
      nodes=nodesX;
      copyNodes(nodes,nodesD);
      //System.out.println(nodes.get(0)+"down here :(");
      arrayXSize = arrayX;
      nodeSize = nodes.size();
      arrayYSize = arrayY;
   }
   
   public void copyNodes(ArrayList<Node> nodesX, ArrayList<Node> nodesD){
      for(int i=0;i<nodesX.size();i++){
         nodesD.add(
            new Node
            (nodesX.get(i).getValue(),nodesX.get(i).getX(),nodesX.get(i).getY()));
      }
      for(int i=0;i<nodesD.size()-1;i++){
         nodesD.get(i).setOutputNode(nodesD.get(i+1));
         nodesD.get(i+1).setInputNode(nodesD.get(i));
      }
      nodesD.get(nodesD.size()-1).setOutputNode(nodesD.get(0));
      nodesD.get(0).setInputNode(nodesD.get(nodesD.size()-1));
   
   }
   	//post:  shows different pictures on the screen in grid format depending on the values stored in the array board
   	//			0-blank, 1-white, 2-black and gives priority to drawing the player
      
      
   public void randomColor(Graphics g, int x){
      int decider = (int)(Math.random()*101);  
      if(x<1){
         g.setColor(Color.red);
      }else
         if(x<2){
            g.setColor(Color.green);
         }else
            if(x<3){
               g.setColor(Color.cyan);
            }else
               if(x<4){
                  g.setColor(Color.magenta);
               }else
                  if(x<5){
                     g.setColor(Color.yellow);
                  }else
                     if(x<6){
                        g.setColor(Color.pink);
                     }else
                        if(x<7){
                           g.setColor(Color.gray);
                        }else
                           if(x<8){
                              g.setColor(Color.orange);
                           }else
                              g.setColor(Color.white);
   }

   public void showBoard(Graphics g)	
   {
   
      // int x =SIZE, y=SIZE,x2 =SIZE, y2=SIZE;		//upper left corner location of where image will be drawn
   //       for(int i=0;i<mapleLane.getSize();i++){
   //          if(mapleLane.get(i)!=null){
   //             randomColor(g,Integer.valueOf(mapleLane.get(i).toString()));
   //             g.fillRect(x, y2, SIZE, SIZE);
   //             x+=SIZE;
   //          }
   //          //System.out.println("YUH");
   //       }
   //       //System.out.println(mainLane.getSize()+"YUH"+mapleLane.getSize());
   //       for(int i=0;i<mainLane.getSize();i++){
   //          if(mainLane.get(i)!=null){
   //             randomColor(g,Integer.valueOf(mainLane.get(i).toString()));
   //             g.fillRect(x2, y, SIZE, SIZE);
   //             y+=SIZE;
   //          }
   //          //System.out.println("AH");
   //       }
      // for(int i=0;i<nodeSize;i++){
   //          System.out.println(nodesD.get(i)+"super far down");
   //       }


	 
      // System.out.println(caseE2);
//       if(!caseE2){
//          for(int i=0;i<nodeSize;i++){
//             int totalX=0;
//             int totalY=0;
//             for(int j=0;j<nodes.get(i).getX();j++){
//                totalX += sizeX;
//             }
//             for(int j=0;j<nodes.get(i).getY();j++){
//                totalY += sizeY;
//             }
//             g.setColor(Color.white);
//             g.fillRect(totalX+5, totalY+5, sizeX-5, sizeY-5);
//             g.setColor(Color.red);
//             g.drawString(nodes.get(i).getID(),totalX+(sizeX/4)-2,totalY+(sizeY/2));
//             g.setColor(Color.blue);
//          //System.out.println(nodes.get(i).getOutputNode().getValue()+"...");
//             g.drawLine((nodes.get(i).getX()*sizeX)+sizeX/2,(nodes.get(i).getY()*sizeY)+sizeY/2,
//                (nodes.get(i).getOutputNode().getX()*sizeX)+sizeX/2,(nodes.get(i).getOutputNode().getY()*sizeY)+sizeY/2);
//          }
//       }else{
         for(int i=0;i<nodeSize;i++){
            int totalX=0;
            int totalY=0;
            for(int j=0;j<nodesD.get(i).getX();j++){
               totalX += sizeX;
            }
            for(int j=0;j<nodesD.get(i).getY();j++){
               totalY += sizeY;
            }
            g.setColor(Color.white);
            g.fillRect(totalX+5, totalY+5, sizeX-5, sizeY-5);
            g.setColor(Color.red);
            g.drawString(nodesD.get(i).getID(),totalX+(sizeX/4)-2,totalY+(sizeY/2));
            g.setColor(Color.blue);
         //System.out.println(nodesD.get(i).getOutputNode().getValue()+"...");
            g.drawLine((nodesD.get(i).getX()*sizeX)+sizeX/2,(nodesD.get(i).getY()*sizeY)+sizeY/2,
               (nodesD.get(i).getOutputNode().getX()*sizeX)+sizeX/2,(nodesD.get(i).getOutputNode().getY()*sizeY)+sizeY/2);
         }
   }
      
   public void paintComponent(Graphics g)
   {
      super.paintComponent(g);                                 
      g.setColor(Color.black);		//draw a blue boarder around the board
      g.fillRect(0, 0, screenXSize, screenYSize);
      showBoard(g);					//draw the contents of the array board on the screen
   }
      
   private class Listener implements ActionListener
   {
      public void actionPerformed(ActionEvent e)	//this is called for each timer iteration - traffic mechanics
      {
         numCycles++;
         if(numCycles >= cycles)
         {
            System.exit(1);
         }
         repaint();
      }
   }
   
}
