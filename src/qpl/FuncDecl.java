package qpl;

import utils.Printable;
import intterm.IntTerm;
import java.util.List;

import static utils.StringUtil.replicate;

public class FuncDecl extends Printable {

    private final String name;
    private final List<String> varNames;
    private final QPLCommand body;
    private final IntTerm returnVal;

    public FuncDecl(String name, List<String> varNames, QPLCommand body, IntTerm returnVal) {
        this.name = name;
        this.varNames = varNames;
        this.body = body;
        this.returnVal = returnVal;
    }
    
    public String getName() {
        return name;
    }
    
    public List<String> getParams() {
        return varNames;
    }
    
    public QPLCommand getBody() {
        return body;
    }
    
    public IntTerm getRetTerm() {
        return returnVal;
    }

    @Override
    public String toStringIndent(int indent) {
        String tabs = replicate('\t',indent);
        String result = tabs + name + "(";
        for (int i=0; i<varNames.size(); i++) {
            result += varNames.get(i);
            if (i<varNames.size()-1) {
                result += ",";
            }
        }
        result += ") {\n";
        if (body instanceof BlockStatement) {
            result += ((BlockStatement) body).toStringBody(indent);
        } else {
            result += body.toStringIndent(indent);
        }
        result += tabs + "\treturn " + returnVal + "\n";
        result += tabs + "}\n";
        return result;
    }
}
