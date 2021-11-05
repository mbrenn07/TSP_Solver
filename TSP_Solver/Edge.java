import java.util.ArrayList;

public class Edge {
   
   private ArrayList<Node> nodes;
   private int deposits;
   
   public Edge (Node n1, Node n2) {
      nodes = new ArrayList();
      nodes.add(n1);
      nodes.add(n2);
      deposits = 0;
   }
   
   public ArrayList<Node> getNodes() {
      return nodes;
   }
   
   public int getDeposits() {
      return deposits;
   }
   
   public void addDeposit() {
      deposits += 1/nodes.get(0).dist(nodes.get(1));
   }
   
   public void swap() {
      nodes.add(nodes.remove(0));
   }
   
   public void reset() {
      for (int i = 0; i < nodes.size(); i++) {
         nodes.get(i).setInputNode(null);
         nodes.get(i).setOutputNode(null);
      }
   }
   
   public static double score(Edge e) { // higher score = better
      double distanceWeight = 1; // multiplied by -1 in calcs since -dist is +score
      double depositWeight = 1;
      double result = 0;
      
      result = (distanceWeight*-1)*(e.getNodes().get(0).dist(e.getNodes().get(1))) + (depositWeight)*(e.getDeposits()); // could distance be an overpowering factor?
      
      return result;
   }
   
}