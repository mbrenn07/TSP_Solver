public class Node<anyType> {

   private anyType value;
   private int x;
   private int y;
   private Node inputNode;
   private Node outputNode;

   public Node (anyType value, int x, int y) {
      this.value = value;
      this.x = x;
      this.y = y;
      inputNode = null;
      outputNode = null;
   }

   public anyType getValue() {
      return value;
   }
   
   public String getID() {
      return value.toString();
   }

   public int getX() {
      return x;
   
   }
   public int getY() {
      return y;
   }

   public Node getInputNode() {
      return inputNode;
   }

   public Node getOutputNode() {
      return outputNode;
   }

   public void setValue(anyType value) {
      this.value = value;
   }

   public void setX(int x) {
      this.x = x;
   }

   public void setY(int y) {
      this.y = y;
   }

   public void setInputNode(Node o) {
      inputNode = o;
   }

   public void setOutputNode(Node o) {
      outputNode = o;
   }

   public double dist(Node o) {//sqr (x1-x2)^2+(y1-y2)^2
      return Math.sqrt(Math.pow(x - o.getX(), 2) + Math.pow(y - o.getY(), 2));
   }
   
   public Node clone() {
      return new Node(value, x, y);
   }

   public String toString() {
      String res = value.toString(); 
      if (inputNode != null) {
         res += " Input: " + inputNode.getValue().toString();
      } else {
         res += " Input: null ";
      } 
      if (outputNode != null) {
         res += " Output: " + outputNode.getValue().toString();;
      } else {
         res += " Output: null";
      }
      return res;
   }
}