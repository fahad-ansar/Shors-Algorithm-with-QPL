package utils;

import intterm.IntTerm;

public enum IntOperations {
     PLUS {
        @Override
        public int apply(int a, int b){
            return a+b;
        }

        public String toString(){
            return "+";
        }

        public int precedence(){
            return 0;
        }
    },
    MINUS {
        @Override
        public int apply(int a, int b){
            return a-b;
        }

        public String toString(){
            return "-";
        }

        public  int  precedence(){
            return 0;
        }
    },
    MULT {
        @Override
        public int apply(int a, int b){
            return a*b;
        }

        public String toString(){
            return "*";
        }

        public  int  precedence(){
            return 1;
        }
    },
    DIV {
        @Override
        public int apply(int a, int b){
            return a/b;
        }

        public String toString(){
            return "/";
        }

        public  int  precedence(){
            return 1;
        }
    },
    MOD {
        @Override
        public int apply(int a, int b){
            return a%b;
        }

        public String toString(){
            return "%";
        }

        public  int  precedence(){
            return 1;
        }
    };
    public abstract int apply(int a, int b);
    public abstract int precedence();
}
