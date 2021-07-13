package utils;

public abstract class Printable {
    
    @Override
    public String toString() {
        return toStringIndent(0);
    }

    public abstract String toStringIndent(int indent);    
}
