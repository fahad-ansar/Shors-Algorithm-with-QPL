package complexmath;

import static complexmath.Complex.*;

public class UnitaryMatrix {

    private final Complex[][] matrix;

    private UnitaryMatrix(Complex[][] matrix){
        this.matrix = matrix;
    }
    
    //generate identity matrix ***/
    public static UnitaryMatrix IDENTITY(int numBits){
        int size = (int) Math.pow(2,numBits);
        Complex[][] matrix = new Complex[size][size];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = (i==j) ? ONE : ZERO;
            }
        }
        return new UnitaryMatrix(matrix);
    }

    //Hadamrd generator ***/
    public static UnitaryMatrix HADAMARD(int numBits) {
        int size = (int) Math.pow(2,numBits);
        Complex factor = new Complex(1/Math.sqrt(size));
        Complex[][] matrix = new Complex[size][size];
        for(int i=0; i<size; i++) {
            for(int j=0; j<size; j++) {
                int no = i&j;
                no ^= (no >>> 16);
                no ^= (no >>> 8);
                no ^= (no >>> 4);
                no ^= (no >>> 2);
                no ^= (no >>> 1);
                matrix[i][j] = ((no & 1)==0? ONE : NEG_ONE).multiply(factor);
            }
        }
        return new UnitaryMatrix(matrix);
    }

    //QFT***/
    public static UnitaryMatrix QFT (int numBits) {
        int size = (int) Math.pow(2,numBits);
        Complex omega = new Complex(0.0,2*Math.PI).divide(new Complex(size)).exponent();
        Complex factor = new Complex(1/Math.sqrt(size));
        Complex[][] matrix = new Complex[size][size];
        for(int i=0; i<size; i++) {
            for(int j=0; j<size; j++) {
                matrix[i][j] = omega.pow(i*j % size).multiply(factor);
            }
        }
        return new UnitaryMatrix(matrix);
    }
    
    public static UnitaryMatrix FROMCLASSICAL(int inBits, int outBits, int[] operation, boolean in_out) {
        int in_size = (int) Math.pow(2,inBits);
        int out_size = (int) Math.pow(2,outBits);
        int size = in_size * out_size;
        Complex[][] matrix  = new Complex[size][size];
        for (int i=0; i<size; i++) {
            int in_value;
            int out_value;
            int result;
            if (in_out) {
                in_value = i % in_size;
                out_value = i / in_size;
                result = ((operation[in_value] ^ out_value) << inBits) + in_value;
            } else {
                in_value = i / out_size;
                out_value = i % out_size;
                result = (in_value << outBits) + (operation[in_value] ^ out_value);
            }
            for(int j=0; j<size; j++) {
                matrix[i][j] = (j==result)? ONE : ZERO;
            }
        }
        return new UnitaryMatrix(matrix);
    }

    //Tensor product ***/
    public UnitaryMatrix tensor(UnitaryMatrix g){
        Complex[][] gMatrix = g.matrix;
        int thisSize = matrix.length;
        int gSize = gMatrix.length;
        Complex[][] result = new Complex[thisSize*gSize][thisSize*gSize];
        for (int i = 0; i < thisSize; i++) {
            for (int j = 0; j < thisSize; j++) {
                for (int k = 0; k < gSize; k++) {
                    for (int l = 0; l < gSize; l++) {
                        result[i*gSize+k][j*gSize+l] = matrix[i][j].multiply(gMatrix[k][l]);
                    }
                }
            }
        }
        return new UnitaryMatrix(result);
    }

    //Inverse matrix ***/
    public UnitaryMatrix inverse(){
        int size = matrix.length;
        Complex[][] result = new Complex[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                result[i][j] = matrix[j][i].conjugate();
            }
        }
        return new UnitaryMatrix(result);
    }

    //for applying a vector the current matric
    public Complex[] apply(Complex[] vec){
        Complex[] result = new Complex[vec.length];
        for(int i = 0; i<vec.length; i++) {
            for(int j = 0; j<vec.length; j++) {
                result[i] = ((result[i]==null) ? ZERO : result[i]).add(matrix[i][j].multiply(vec[j]));
            }
        }
        return result;
    }
}
