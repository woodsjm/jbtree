package btree.java;


public class BtreeException {

  public static class NodeIndexException extends Exception {
    public NodeIndexException(String message) {
      super(message);
    }
  }

  public static class NodeModifyException extends Exception {
    public NodeModifyException(String message) {
      super(message);
    }
  }

  public static class NodeNotFoundException extends Exception {
    public NodeNotFoundException(String message) {
      super(message);
    }
  }

  public static class NodeReferenceException extends Exception {
    public NodeReferenceException(String message) {
      super(message);
    }
  }

  public static class NodeTypeException extends Exception {
    public NodeTypeException(String message) {
      super(message);
    }
  }

  public static class NodeValueException extends Exception {
    public NodeValueException(String message) {
      super(message);
    }
  }

  public static class TreeHeightException extends Exception {
    public TreeHeightException(String message) {
      super(message);
    }
  }
}