package btree.java;


public class BtreeException {
  public static class NodeValueException extends Exception {
    public NodeValueException(String message) {
      super(message);
    }
  }
}