import java.util.ArrayList;
import javax.swing.JFrame;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.*;
import java.awt.geom.Line2D;


public class Main {

   public static TspPanel screen;
   public static TspPanel screen2;
   public static TspPanel screen3;
   public static TspPanel screen4;
   public static TspPanel screen5;

   static int arraySizeX;
   static int arraySizeY;

   public static double findDistance(ArrayList<Node> nodesX) {
      double distance =0;
      for(int i=0;i<nodesX.size()-1;i++){
         distance += nodesX.get(i).dist(nodesX.get(i+1));
      }
      distance += nodesX.get(nodesX.size()-1).dist(nodesX.get(0));
      return distance;
   }
   
   //nodes sizeXsize
   public static ArrayList<Node> addRandomNodes(int sizeX, int sizeY, int number) {
      ArrayList<Node> nodesX = new ArrayList();
      arraySizeX=sizeX;
      arraySizeY=sizeY;
      for(int i=0;i<number;i++){
         nodesX.add(new Node("N"+i,(int)((Math.random()*sizeX)),(int)((Math.random()*sizeY))));
         boolean fail=false;
         do{
            if(fail){
               nodesX.get(i).setX((int)((Math.random()*sizeX)));
               nodesX.get(i).setY((int)((Math.random()*sizeY)));
            }
            fail=false;
            for(int j=0;j<nodesX.size();j++){
               if(nodesX.get(i).getX()==nodesX.get(j).getX()||nodesX.get(i).getY()==nodesX.get(j).getY()){
                  fail=true;
               }
            }
         }while(!fail);
         //System.out.println(nodesX.get(i).getX()+","+nodesX.get(i).getY());
      }
      return nodesX;
   }


   public static void main(String[] args) {
   
      ArrayList<Node> nodes = new ArrayList();
      ArrayList<Node> OGnodes = new ArrayList();
   
      Scanner in = new Scanner(System.in);
      int screenXsize=1000;
      int screenYSize=1000;   
      int dimensions=0;
      int numOfnodes=0;
      int numOfScreens=0;
      String discardVar="";
      boolean running =true;
      System.out.println("Press 1 to use default settings. press 2 to set custom ones, or press 3 to choose from a list of presets.");
      String beginAns = in.next();
      if(beginAns.equals("1")){
         dimensions=500;
         numOfnodes=150;
      }else if(beginAns.equals("2")){
         System.out.println("Please enter the dimensions for the node grid.");
         if(in.hasNextInt()) {
            dimensions=in.nextInt();
         }else{
            System.out.println("ERROR: Unexpected input type");
            discardVar=in.next();
            running =false;
         }            
         System.out.println("Please enter the number of nodes. This number cannot be larger than the dimensions squared.");
         if(in.hasNextInt()) {
            numOfnodes=in.nextInt();
         }else{
            System.out.println("ERROR: Unexpected input type");
            running =false;
            discardVar=in.next();
         }
         if((dimensions*dimensions)<numOfnodes){
            System.out.println("ERROR: The number of nodes is larger than the grid allows for.");
            running =false;
         }         
      }else if(beginAns.equals("3")){
         System.out.println("Press 1 for a small grid, press 2 for a medium grid, press 3 for a large grid, or press 4 for a massive grid");
         int newAns=0;
         boolean triggered=false;
         if(in.hasNextInt()) {
            newAns=in.nextInt();
         }else{
            System.out.println("ERROR: Unexpected input type");
            discardVar=in.next();
            running =false;
            triggered=true;;
         }
         if(newAns==1){
            dimensions=10;
            numOfnodes=7;
         }else
            if(newAns==2){
               dimensions=100;
               numOfnodes=50;
            }else
               if(newAns==3){
                  dimensions=1000;
                  numOfnodes=1000;
               }else
                  if(newAns==4){
                     dimensions=1000;
                     numOfnodes=50000;
                  }else{
                     if(!triggered){
                        System.out.println("ERROR: Unexpected input type");
                        running =false;   
                     }
                  }    
      }else{
         System.out.println("ERROR: Unexpected input type");
         running =false;   
         discardVar=in.next();     
      }
   
            
      nodes = addRandomNodes(dimensions, dimensions, numOfnodes);  
      //nodes = sortB(nodes);      
      String sortType="";
      boolean caseE = true;
      for(int i=0;i<nodes.size();i++){
         OGnodes.add(nodes.get(i));
      }
      while(running){
         for(int i=0;i<nodes.size();i++){
            nodes.set(i,OGnodes.get(i));
            nodes.get(i).setInputNode(null);
            nodes.get(i).setOutputNode(null);
         }
         System.out.println("Please enter 'A', 'B', 'C', 'D', or 'E' for your sort type");
         String ans = in.next();
         if(ans.equals("A")){
            caseE = false;
            sortType = "Sort A";
            long start = System.currentTimeMillis();
            nodes = sortA(nodes);
            System.out.println("The best distance from Sort A is "+findDistance(nodes)+".");
            long finish = System.currentTimeMillis();
            long timeElapsed = finish - start;
            System.out.println(timeElapsed/1000.0+" seconds to finish Sort A.");
         }else if(ans.equals("B")){
            caseE = false;
            sortType = "Sort B";
            long start = System.currentTimeMillis();
            nodes = sortB(nodes);
            System.out.println("\nThe best distance after Sort B is "+findDistance(nodes)+".");
            long finish = System.currentTimeMillis();
            long timeElapsed = finish - start;
            System.out.println(timeElapsed/1000.0+" seconds to finish Sort B.");
         }else if(ans.equals("C")){
            caseE = false;
            sortType = "Sort C";
            long start = System.currentTimeMillis();
            nodes = sortC(nodes);
            System.out.println("The best distance after Sort C is "+findDistance(nodes)+".");
            long finish = System.currentTimeMillis();
            long timeElapsed = finish - start;
            System.out.println(timeElapsed/1000.0+" seconds to finish Sort C.");
         }else if(ans.equals("D")){
            caseE = false;
            sortType = "Sort D";
            long start = System.currentTimeMillis();
            nodes = sortD(nodes);
            System.out.println("The best distance after Sort D is "+findDistance(nodes)+".");
            long finish = System.currentTimeMillis();
            long timeElapsed = finish - start;
            System.out.println(timeElapsed/1000.0+" seconds to finish Sort D.");
         }else if(ans.equals("E")){
            for(int i=0;i<nodes.size();i++){
               nodes.get(i).setInputNode(null);
               nodes.get(i).setOutputNode(null);
            }
            caseE = true;
            sortType = "Sort E";
            long start = System.currentTimeMillis();
            nodes = sortE(nodes);
            //System.out.println(nodes.get(0)+"up here!");
            System.out.println("The best distance after Sort E is "+findDistance(nodes)+".");
            long finish = System.currentTimeMillis();
            long timeElapsed = finish - start;
            System.out.println(timeElapsed/1000.0+" seconds to finish Sort E.");
         }else{
            System.out.println("ERROR: Unexpected input type");
            running =false;
            break;
         }
         
         //NOTE: all new screens just replacing old screens
         numOfScreens++;
         if(numOfScreens==1){
            screen = new TspPanel(screenXsize,screenYSize,arraySizeY,arraySizeX,nodes,caseE);
            JFrame frame = new JFrame("Traveling Salesmen Solver "+sortType);	//window title
            frame.setSize(screenXsize, screenYSize);					//Size of game window
            frame.setLocation(100, 50);				//location of game window on the screen
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(screen);		
            frame.setVisible(true);
         }else if(numOfScreens==2){
            screen2 = new TspPanel(screenXsize,screenYSize,arraySizeY,arraySizeX,nodes,caseE);
            JFrame frame2 = new JFrame("Traveling Salesmen Solver "+sortType);	//window title
            frame2.setSize(screenXsize, screenYSize);					//Size of game window
            frame2.setLocation(100, 50);				//location of game window on the screen
            frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame2.setContentPane(screen);		
            frame2.setVisible(true);
         }else if(numOfScreens==3){
            screen3 = new TspPanel(screenXsize,screenYSize,arraySizeY,arraySizeX,nodes,caseE);
            JFrame frame3 = new JFrame("Traveling Salesmen Solver "+sortType);	//window title
            frame3.setSize(screenXsize, screenYSize);					//Size of game window
            frame3.setLocation(100, 50);				//location of game window on the screen
            frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame3.setContentPane(screen);		
            frame3.setVisible(true);
         }else if(numOfScreens==4){
            screen4 = new TspPanel(screenXsize,screenYSize,arraySizeY,arraySizeX,nodes,caseE);
            JFrame frame4 = new JFrame("Traveling Salesmen Solver "+sortType);	//window title
            frame4.setSize(screenXsize, screenYSize);					//Size of game window
            frame4.setLocation(100, 50);				//location of game window on the screen
            frame4.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame4.setContentPane(screen);		
            frame4.setVisible(true);  
         }else if(numOfScreens==5){
            screen5 = new TspPanel(screenXsize,screenYSize,arraySizeY,arraySizeX,nodes,caseE);
            JFrame frame5 = new JFrame("Traveling Salesmen Solver "+sortType);	//window title
            frame5.setSize(screenXsize, screenYSize);					//Size of game window
            frame5.setLocation(100, 50);				//location of game window on the screen
            frame5.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame5.setContentPane(screen);		
            frame5.setVisible(true);  
         }
         	 
         	 
         else{
            System.out.println("ERROR: Too many screens");
            System. exit(0);
         }
      
         System.out.println("Press 1 to exit the program, or 2 to begin again using the same coordinates");
         String ansNum = in.next();
         if(ansNum.equals("1")){
            System. exit(0);
         }else if(ansNum.equals("2")){
            running = true;  
         }else{
            System.out.println("ERROR: Unexpected input type");
            System. exit(0);
         }
      } 
   }
      
      
   public static ArrayList<Node> sortA(ArrayList<Node> nodes){
      //TODO: sortA breaks if coords are already sorted;
      Node firstNode = nodes.get(0);
      for (int i = 0; i < nodes.size(); i++) {
         Node shortest = null;
         int shortestIndex = -1;
         for (int j = i+1; j < nodes.size(); j++) {
            if (j != i && nodes.get(j).getInputNode() == null && nodes.get(j).getOutputNode()== null) {
               if (shortest == null) {
                  shortest = nodes.get(j);
                  shortestIndex = j;
                  continue;
               }         
               if (nodes.get(i).dist(shortest) > nodes.get(i).dist(nodes.get(j))) {
                  shortest = nodes.get(j);
                  shortestIndex = j;
               }
            }
         }
         
         if(shortest==null){
            shortest= firstNode;
         }
         nodes.get(i).setOutputNode(shortest);
         shortest.setInputNode(nodes.get(i));
         if(i!=nodes.size()-1){
            swap(nodes, i+1, shortestIndex);
         }
      }
      // for(int i=0;i<nodes.size();i++){
   //          System.out.println(nodes.get(i));
   //       }
      return nodes;
   }
   
   public static ArrayList<Node> sortB(ArrayList<Node> nodes){
      int ants = nodes.size();
      double bestAntDistance = Double.MAX_VALUE;
      int bestAntIndex = -1;
      ArrayList<Edge>[] antPaths = new ArrayList[ants];
      Edges edges = new Edges(nodes);
      for (int ant = 0; ant < ants; ant++) {
         System.out.print(ant+"...");
         Node firstNode = nodes.get(ant);
         swap(nodes, ant, 0);
         double distance = 0;
         antPaths[ant] = new ArrayList();
         edges.reset();
         for (int i = 0; i < nodes.size(); i++) {
            Node choice = null;
            int choiceIndex = -1;
            for (int j = i+1; j < nodes.size(); j++) {
               if (j != i && nodes.get(j).getInputNode() == null && nodes.get(j).getOutputNode()== null) {
                  if (choice == null) {
                     choice = nodes.get(j);
                     choiceIndex = j;
                     continue;
                  }         
                  if (Edge.score(edges.find(nodes.get(i), nodes.get(j))) > Edge.score(edges.find(nodes.get(i), choice))) {
                     choice = nodes.get(j);
                     choiceIndex = j;
                  }
               }
            }
            if(choice==null){
               choice = firstNode;
            }
            nodes.get(i).setOutputNode(choice);
            choice.setInputNode(nodes.get(i));
            edges.find(nodes.get(i), choice).addDeposit();
            distance += nodes.get(i).dist(choice);
            antPaths[ant].add(edges.find(nodes.get(i), choice));
            if(i!=nodes.size()-1){
               swap(nodes, i+1, choiceIndex);
            }
         }
         if (distance < bestAntDistance) {
            bestAntDistance = distance;
            bestAntIndex = ant;
         }
      }
      return Edges.format(antPaths[bestAntIndex]);
   }
	  

   public static ArrayList<Node> sortC(ArrayList<Node> nodes){
      nodes = sortA(nodes);
      double currentDist=findDistance(nodes);
      System.out.println("The best distance from Sort A is "+currentDist+".");
      boolean improved = true;
      while(improved){
         improved = false;
         for(int i=0;i<nodes.size()-1;i++){
            for(int j=i+1;j<nodes.size();j++){
               ArrayList<Node> tempNodes =new ArrayList();
               for(int u=0;u<nodes.size();u++){
                  tempNodes.add(nodes.get(u));
               }
               int counter=0;
               for(int k=0;k<nodes.size();k++){
                  if(k>=i&&k<=j){
                     tempNodes.set(k,nodes.get(j-counter)); 
                     counter++;
                  }
               }
               double newDist=findDistance(tempNodes);
               if(newDist<currentDist){
                  for(int u=0;u<tempNodes.size();u++){
                     nodes.set(u,tempNodes.get(u));
                  }
                  currentDist = newDist;
                  improved = true;
               }
            }
         }
      }
      for(int i=0;i<nodes.size()-1;i++){
         nodes.get(i).setOutputNode(nodes.get(i+1));
         nodes.get(i+1).setInputNode(nodes.get(i));
      }
      nodes.get(nodes.size()-1).setOutputNode(nodes.get(0));
      nodes.get(0).setInputNode(nodes.get(nodes.size()-1));
       // for(int i=0;i<nodes.size();i++){
   //              System.out.println(nodes.get(i));
   //           }
      return nodes;
   }   
   
   public static ArrayList<Node> sortD(ArrayList<Node> nodes){
      double currentDist=findDistance(nodes);
      boolean improved = true;
      while(improved){
         improved = false;
         for(int i=0;i<nodes.size()-1;i++){
            for(int j=i+1;j<nodes.size();j++){
               ArrayList<Node> tempNodes =new ArrayList();
               for(int u=0;u<nodes.size();u++){
                  tempNodes.add(nodes.get(u));
               }
               int counter=0;
               for(int k=0;k<nodes.size();k++){
                  if(k>=i&&k<=j){
                     tempNodes.set(k,nodes.get(j-counter)); 
                     counter++;
                  }
               }
               double newDist=findDistance(tempNodes);
               if(newDist<currentDist){
                  for(int u=0;u<tempNodes.size();u++){
                     nodes.set(u,tempNodes.get(u));
                  }
                  currentDist = newDist;
                  improved = true;
               }
            }
         }
      }
      for(int i=0;i<nodes.size()-1;i++){
         nodes.get(i).setOutputNode(nodes.get(i+1));
         nodes.get(i+1).setInputNode(nodes.get(i));
      }
      nodes.get(nodes.size()-1).setOutputNode(nodes.get(0));
      nodes.get(0).setInputNode(nodes.get(nodes.size()-1));
      //  for(int i=0;i<nodes.size();i++){
   //              System.out.println(nodes.get(i));
   //           }
      return nodes;
   }   

   public static ArrayList<Node> sortE(ArrayList<Node> nodes) {
      //TODO:fix this method crashing when it is run second after any other method
      ArrayList<Node> shortest = new ArrayList();
      for (int i = 0; i < nodes.size(); i++) {
         shortest.add(new Node(nodes.get(i).getValue(),nodes.get(i).getX(),nodes.get(i).getY()));
      }
      heapPermutation(nodes, nodes.size(), shortest);
      for(int i=0;i<shortest.size();i++){
         nodes.set(i,shortest.get(i));
      }
      for(int i=0;i<nodes.size()-1;i++){
         nodes.get(i).setOutputNode(nodes.get(i+1));
         nodes.get(i+1).setInputNode(nodes.get(i));
      }
      nodes.get(nodes.size()-1).setOutputNode(nodes.get(0));
      nodes.get(0).setInputNode(nodes.get(nodes.size()-1));
      //System.out.println(nodes.get(0)+"hiding in sortC ^^");
      return nodes;
   }
   
   public static void heapPermutation(ArrayList<Node> nodes, int size, ArrayList<Node> shortest) 
   { 
        // if size becomes 1 then prints the obtained 
        // permutation 
      if (size == 1) {
         if (findDistance(nodes) < findDistance(shortest)) {
            for (int i = 0; i < nodes.size(); i++) {
               shortest.set(i,new Node(nodes.get(i).getValue(),nodes.get(i).getX(),nodes.get(i).getY()));
            }
         }
         return;
      } 
   
      for (int i=0; i<size; i++) 
      { 
         heapPermutation(nodes, size-1, shortest); 
      
            // if size is odd, swap first and last 
            // element 
         if (size % 2 == 1) 
         { 
            Node temp = nodes.get(0); 
            nodes.set(0, nodes.get(size-1));
            nodes.set(size-1, temp);
         } 
         
            // If size is even, swap with and last 
            // element 
         else
         { 
            Node temp = nodes.get(i); 
            nodes.set(i, nodes.get(size-1));
            nodes.set(size-1, temp);
         } 
      } 
   }   
   public static void swap(ArrayList<Node> list, int i1, int i2) {
      Node temp = list.get(i2);
      list.set(i2, list.get(i1));
      list.set(i1, temp);
   }

}