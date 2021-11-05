import java.util.*;

public class Edges {
   
   private ArrayList<Edge> edges;
   
   public Edges(ArrayList<Node> nodes) {
      edges = new ArrayList();
      for (int i = 0; i < nodes.size(); i++) {
         for (int j = i+1; j < nodes.size(); j++) {
            edges.add(new Edge(nodes.get(i), nodes.get(j)));
         }
      }
   }
   
   public Edge find (Node n1, Node n2) {
      Edge result = null;
      for (int i = 0; i < edges.size(); i++) {
         if (edges.get(i).getNodes().contains(n1) && edges.get(i).getNodes().contains(n2)) {
            result = edges.get(i);
            break;
         }
      }
      return result;
   }
   
   public static Edge findOther (ArrayList<Edge> e, Node n1, Node skip) {
      Edge result = null;
      for (int i = 0; i < e.size(); i++) {
         if (e.get(i).getNodes().contains(n1) && !e.get(i).getNodes().contains(skip) ) {
            result = e.get(i);
            if (result.getNodes().get(0) != n1) {
               result.swap();
            }
            break;
         }
      }
      return result;
   }
   
   public void reset() {
      for (int i = 0; i < edges.size(); i++) {
         edges.get(i).reset();
      }
   }
   
   public static ArrayList<Node> format (ArrayList<Edge> e) {
      ArrayList<Node> result = new ArrayList();
      for (int i = 0; i < e.size(); i++) {
         result.add(e.get(i).getNodes().get(0));
         //result.add(e.get(i).getNodes().get(1));
         Edge nextEdge = Edges.findOther(e, e.get(i).getNodes().get(1), e.get(i).getNodes().get(0));
         int nextEdgeIndex = -1;
         for (int j = 0; j < e.size(); j++) {
            if (e.get(j) == nextEdge) {
               nextEdgeIndex = j;
               break;
            }
         }
         if (i != e.size()-1) {
            swap(e, i+1, nextEdgeIndex);
         }
      }
      return result;
   }
   
   private static void swap(ArrayList<Edge> list, int i1, int i2) {
      Edge temp = list.get(i2);
      list.set(i2, list.get(i1));
      list.set(i1, temp);
   }
   
}