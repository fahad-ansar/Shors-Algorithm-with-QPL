package states;

import complexmath.Complex;
import complexmath.UnitaryMatrix;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import qpl.FuncDecl;

import static complexmath.Complex.*;

public class State {

    private final Map<String,Integer> store;
    private Complex[] quantRegister;
    private Map<String,FuncDecl> funcDecls;
    private int numQBits;
    
    public State() {
        this.store = new HashMap();
        this.funcDecls = new HashMap();
    }
    
    public State getNew() {
        State result = new State();
        result.funcDecls = funcDecls;
        return result;
    }

    public void initQRegister(int numQBits){
        this.numQBits = numQBits;
        this.quantRegister = new Complex[(int) Math.pow(2,numQBits)];
        quantRegister[0] = new Complex(1);
        for (int i = 1; i < quantRegister.length; i++) {
            quantRegister[i] = ZERO;
        }
    }

    public void applyToQRegister(UnitaryMatrix g){
       quantRegister = g.apply(quantRegister);
    }

    public int measureQRegister(int lowB, int uppB) {
        double[] probs = new double[(int) Math.pow(2,uppB-lowB+1)];
        for(int i=0; i<quantRegister.length; i++) {
            int j = (i >>> lowB) % (1 << uppB+1);
            probs[j] += quantRegister[i].magnitude();
        }
        double rand = Math.random();
        Integer measured = null;
        int i=0;
        boolean cond = true;
        while (cond) {
            rand = rand - probs[i];
            if (rand <= 0) {
                measured = i;
            }
            i++;
            cond = (i < probs.length) && (measured == null);
        }
        if (measured == null) {
            measured = probs.length-1;
        }
        for(int j=0; j<quantRegister.length; j++) {
            int k = (j >>> lowB) % (1 << uppB+1);
            quantRegister[j] = (measured==k)? quantRegister[j].divide(new Complex(Math.sqrt(probs[measured]))) : ZERO;
        }
        return measured;
    }
    
    public Integer getStore(String s){
        return store.get(s);
    }

    public void putStore(String var, int value) {
        store.put(var,value);
    }
    
    public void removeStore(String var) {
        store.remove(var);
    }
    
    public FuncDecl getDecl(String s){
        return funcDecls.get(s);
    }

    public void putDecl(String name, FuncDecl fun) {
        funcDecls.put(name,fun);
    }

    public int getNumQBits() { 
        return numQBits;
    }

    @Override
    public String toString() {
        return "State[" +
                "store=" + store +
                ", quantRegister=" + Arrays.toString(quantRegister) +
                ']';
    }
}
