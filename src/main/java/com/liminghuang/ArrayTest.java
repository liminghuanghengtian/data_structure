package com.liminghuang;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ProjectName: example
 * PackageName: com.liminghuang
 * Description:
 * <p>
 * CreateTime: 2017/8/20 17:03
 * Modifier: Adaministrator
 * ModifyTime: 2017/8/20 17:03
 * Comment:
 *
 * @author Adaministrator
 */
public class ArrayTest {
    static class Location {
        private int row;
        private int col;
        
        public Location(int row, int col) {
            this.row = row;
            this.col = col;
        }
        
        public int getRow() {
            return row;
        }
        
        public void setRow(int row) {
            this.row = row;
        }
        
        public int getCol() {
            return col;
        }
        
        public void setCol(int col) {
            this.col = col;
        }
    }
    
    public static void main(String[] args) {
        final int[][] array = {
                {1, 0, 3, 4},
                {0, 2, 9, 0},
                {3, 4, 5, 5},
                {3, 4, 5, 5}
        };
        List<Location> locations = new ArrayList<Location>();
        
        int rowNo = 0;
        for (int[] row : array) {
            int colNo = 0;
            for (int element : row) {
                if (element == 0) {
                    locations.add(new Location(rowNo, colNo));
                }
                colNo++;
            }
            rowNo++;
        }
        
        int[][] result = array;
        for (int row = 0; row < result.length; row++) {
            for (int col = 0; col < result[row].length; col++) {
                boolean isExist = false;
                for (Location location : locations) {
                    if (location.getRow() == row || location.getCol() == col) {
                        isExist = true;
                        break;
                    }
                }
                
                if (isExist) {
                    result[row][col] = 0;
                }
            }
        }
        
        System.out.println(Arrays.deepToString(result));
    }
}
