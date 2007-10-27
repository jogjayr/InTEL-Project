/*
 * Matrix.java
 * 
 * Created on Sep 27, 2007, 1:09:27 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.modes.equation.solver;

/**
 *
 * @author Calvin Ashmore
 */
public class Matrix {
    // A[row][column]
    private float[][] A;
    
    public int getSize() {return A.length;}

    public String toString() {
        String r = "";
        for(int i=0;i<getSize(); i++) {
            r += "[ ";
            for(int j=0; j<getSize(); j++) {
                r += A[i][j]+ " ";
            }
            r += "]\n";
        }
        return r;
    }
    
    public Matrix(float[][] A) {
        this.A = A;
    }
    
    public Matrix(int size) {
        A = new float[size][size];
    }
    
    public Matrix(Matrix M) {
        this(M.getSize());
        for(int i=0; i<getSize(); i++)
        for(int j=0; j<getSize(); j++) {
            A[i][j] = M.A[i][j];
        }
    }
    
    public Matrix createMinor(int removeRow, int removeCol) {
        Matrix M = new Matrix(getSize()-1);
        
        for (int i = 0; i < A.length; i++) {
            float[] column = A[i];
            
            if(i == removeRow)
                continue;
            int ii = (i<removeRow) ? i : i-1;
            
            for (int j = 0; j < column.length; j++) {
                float a = column[j];
                
                if(j == removeCol)
                    continue;
                int jj = (j<removeCol) ? j : j-1;
                
                M.A[ii][jj] = a;
            }
        }
        return M;
    }
    
    public float calculateDeterminant() {
        if(getSize() == 1) {
            return A[0][0];
        } else if(getSize() == 2) {
            return A[0][0]*A[1][1] - A[1][0]*A[0][1];
        } else {
            double r = 0;
            for(int i=0; i<getSize(); i++)
                r += (i%2 == 0 ? 1 : -1) * A[0][i] * createMinor(0, i).calculateDeterminant();
            return (float)r;
        }
    }
    
    private String format(double B[][]) {
        String r = "";
        for(int i=0;i<B.length; i++) {
            r += "[ ";
            for(int k=0; k<2*B.length; k++) {
                r += (float)B[i][k]+ " ";
            }
            r+= "]\n";
        }
        return r;
    }
    
    public Matrix calculateInverse() {
        
        // use Gauss-Jordan method to invert matrix
        
        int n = getSize();
        double B[][] = new double[n][2*n];
        
        // build our double matrix
        for(int i=0; i<n; i++)
        for(int j=0; j<n; j++) {
            B[i][j] = A[i][j];
            B[i][j+n] = (i==j ? 1 : 0);
        }
        
        for(int i=0; i<n; i++) {
            
            // locate maximum pivot
            int pivot = i;
            for(int j=i+1; j<n; j++)
                if( Math.abs(B[j][i]) > Math.abs(B[pivot][i]) )
                    pivot = j;
                
            // swap rows
            for(int k=0; k<n*2; k++) {
                double temp = B[i][k];
                B[i][k] = B[pivot][k];
                B[pivot][k] = temp;
            }
            
            // should not have this problem, but check anyway
            if(B[i][i] == 0)
                return null;
            
            // eliminate columns via row subtraction
            for(int j=0; j<n; j++) {
                if(i != j) {
                    double c = B[j][i] / B[i][i];
                    for(int k=0; k<2*n; k++)
                        B[j][k] -= B[i][k] * c;
                } else {
                    // normalize if we are on the target row
                    double c = 1/B[i][i];
                    for(int k=0; k<2*n; k++)
                        B[i][k] *= c;
                }
            }
        }
        
        Matrix M = new Matrix(n);
        for(int i=0; i<n; i++)
        for(int j=0; j<n; j++)
            M.A[i][j] = (float)B[i][j+n];
        
        return M;
    }
    
    public Matrix mult(Matrix M) {
        int n = getSize();
        Matrix R = new Matrix(n);
        
        for(int i=0; i<n; i++)
        for(int j=0; j<n; j++) {
            float v = 0;
            for(int k=0; k<n; k++)
                v += A[i][k]*M.A[k][j];
            R.A[i][j] = v;
        }
        return R;
    }
    
    public float[] apply(float[] v) {
        int n = getSize();
        float[] r = new float[n];
        for(int i=0; i<n; i++) {
            for(int j=0; j<n; j++)
                r[i] += v[j]*A[i][j];
        }
        return r;
    }
}
