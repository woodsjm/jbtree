package btree.java;

import java.util.List;

public class BtreeCharacterBox {
    public List<String> box;
    public int width;
    public int start;
    public int end;
    
    public BtreeCharacterBox() {
        this.box = null;
        this.width = 0;
        this.start = 0;
        this.end = 0;
    }
    
    public BtreeCharacterBox(List<String> box, int width, int start, int end) {
        this.box = box;
        this.width = width;
        this.start = start;
        this.end = end;
    }
}
