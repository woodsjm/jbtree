package btree.java;

import java.util.ArrayList;
import java.util.List;
import java.text.NumberFormat;


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

    protected static <T extends Comparable<T>> BtreeCharacterBox buildTreeString(Node<T> root, int currentIdx, boolean includeIdx, String delimiter) {
        if (root == null) {
            return new BtreeCharacterBox();
        }

        List<String> line1 = new ArrayList<String>();
        List<String> line2 = new ArrayList<String>();
        String nodeRepr = "";
        nodeRepr = includeIdx ? String.format("%s%s%s", currentIdx, delimiter, root.getVal()) : String.valueOf(root.getVal());

        int newRootWidth = nodeRepr.length();
        int gapSize = nodeRepr.length();
        int newRootStart;

        BtreeCharacterBox l = buildTreeString(root.getLeft(), 2 * currentIdx + 1, includeIdx, delimiter);
        BtreeCharacterBox r = buildTreeString(root.getRight(), 2 * currentIdx + 2, includeIdx, delimiter);

        if (l.width > 0) {
            int lRoot = Math.floorDiv((l.start + l.end), 2) + 1;
            line1.add(String.valueOf(new char[lRoot + 1]).replace("\0", " "));
            line1.add(String.valueOf(new char[l.width - lRoot]).replace("\0", "_"));
            line2.add(String.valueOf(new char[lRoot]).replace("\0", " ") + "/");
            line2.add(String.valueOf(new char[l.width - lRoot]).replace("\0", " "));
            newRootStart = l.width + 1;
            gapSize++;
        } else {
            newRootStart = 0;
        }
                
        line1.add(nodeRepr);
        line2.add(String.valueOf(new char[newRootWidth]).replace("\0", " "));
        

        if (r.width > 0) {
            int rRoot = Math.floorDiv((r.start + r.end), 2);
            line1.add(String.valueOf(new char[rRoot]).replace("\0", "_"));
            line1.add(String.valueOf(new char[r.width - rRoot + 1]).replace("\0", " "));
            line2.add(String.valueOf(new char[rRoot]).replace("\0", " ") + "\\");
            line2.add(String.valueOf(new char[r.width - rRoot]).replace("\0", " "));
            gapSize++;
        }

        int newRootEnd = newRootStart + newRootWidth - 1;

        String gap = String.valueOf(new char[gapSize]).replace("\0", " ");
        List<String> newBox = new ArrayList<>();
        newBox.add(String.join("", line1));
        newBox.add(String.join("", line2));

        for (int i = 0; i < Math.max(l.box == null ? 0 : l.box.size(), r.box == null ? 0 : r.box.size()); i++) {			
            String lLine = "";																							
            String rLine = "";																				
            if (l.box != null) {
                lLine = i < l.box.size() ? l.box.get(i) : String.valueOf(new char[l.width]).replace("\0", " ");
            }
            if (r.box != null) {
                rLine = i < r.box.size() ? r.box.get(i) : String.valueOf(new char[r.width]).replace("\0", " ");
            }
            
            newBox.add(lLine + gap + rLine);
        } 

        return new BtreeCharacterBox(newBox, newBox.get(0).length(), newRootStart, newRootEnd);
    }
}
