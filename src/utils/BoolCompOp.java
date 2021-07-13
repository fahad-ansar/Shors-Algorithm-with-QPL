package utils;

public enum BoolCompOp{
    EQUAL {
        @Override
        public boolean apply(int a, int b){
            return a==b;
        }

        public String toString(){
            return "==";
        }
    },
    NOTEQUAL {
        @Override
        public boolean apply(int a, int b){
            return a!=b;
        }

        public String toString(){
            return "!=";
        }
    },
    LESSTHAN {
        @Override
        public boolean apply(int a, int b){
            return a<b;
        }

        public String toString(){
            return "<";
        }
    },
    LESSOREQUALTHAN {
        @Override
        public boolean apply(int a, int b){
            return a<=b;
        }

        public String toString(){
            return "<=";
        }
    },
    GREATERTHAN {
        @Override
        public boolean apply(int a, int b){
            return a>b;
        }

        public String toString(){
            return ">";
        }
    },
    GREATEROREQUALTHAN {
        @Override
        public boolean apply(int a, int b){
            return a>=b;
        }

        public String toString(){
            return ">=";
        }
    };
    public abstract boolean apply(int a, int b);
}
