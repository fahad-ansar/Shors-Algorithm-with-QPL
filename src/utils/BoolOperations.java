package utils;

public enum BoolOperations {
    AND {
        @Override
        public boolean apply(boolean a, boolean b){
            return a && b;
        }

        public String toString(){
            return " && ";
        }

        @Override
        public int precedence() {
            return 0;
        }
    },
    OR {
        @Override
        public boolean apply(boolean a, boolean b){
            return a || b;
        }

        public String toString(){
            return " || ";
        }

        @Override
        public int precedence() {
            return 0;
        }
    };
    public abstract boolean apply(boolean a, boolean b);
    public abstract int precedence();
}
