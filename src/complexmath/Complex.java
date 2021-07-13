package complexmath;

public class Complex {

    private double real = 0.0;
    private double imaginary = 0.0;

    //Constants
    public static final Complex I = new Complex(0.0,1.0);
    public static final Complex ONE = new Complex(1.0);
    public static final Complex NEG_ONE = new Complex(-1.0);
    public static final Complex ZERO = new Complex(0.0);
    public static final Complex NEG_I = new Complex(0.0,-1.0);

    //first constructor
    public Complex(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    //Second constructor
    public Complex(double real) {
        this(real,0.0);
    }

    //Get methods
    public double getReal() { 
        return real; 
    }

    public double getImaginary() { 
        return imaginary; 
    }

    //Magnitude of a complex number
    public double magnitude(){
        return Math.pow(imaginary,2) + Math.pow(real,2);
    }

    /*Reciprocal***/
    public Complex reciprocal(){
        double scale = real*real+ imaginary*imaginary;
        return new Complex(real/scale, -imaginary/scale);
    }

    /* REAL Arthematic Operations***/ //ac- bd //(bc+ad)i
    public Complex multiply(Complex b){
        double realT =  real*b.real - imaginary*b.imaginary;
        double imaginaryT = real*b.imaginary + imaginary*b.real;
        return new Complex(realT,imaginaryT);
    }

    //takes conjugate of complex numbers ***//(ac-bd)*-1 OR (ac+bd)*-1
    public Complex conjugate(){
        return new Complex(real,imaginary * -1);
    }

    //Addition of complex numbers ***//(5+2i)+(3-7i)
    public Complex add(Complex b){
        double realT = real+ b.real;
        double imaginaryT = imaginary+b.imaginary;
        return new Complex(realT,imaginaryT);
    }
    
    //Addition of complex numbers ***//(5+2i)+(3-7i)
    public Complex minus(Complex b){
        double realT = real - b.real;
        double imaginaryT = imaginary-b.imaginary;
        return new Complex(realT,imaginaryT);
    }

    //divides current complex number with the parameter*/
    public Complex divide(Complex b){
        return this.multiply(b.reciprocal());
    }

    //takes exponential power*/
    public Complex exponent(){
        double realT = Math.exp(real) * Math.cos(imaginary);
        double imaginaryT = Math.exp(real) * Math.sin(imaginary);
        return new Complex(realT,imaginaryT);
    }

    //pow function to use Math.pow on a Complex No.
    public Complex pow(int e) {
        Complex result = ONE;
        for(int i=0; i<e; i++){
            result = result.multiply(this);
        }
        return result;
    }

    @Override
    public String toString() {
        String result = "";
        if (real != 0.0) {
            result += real;
        }
        if (imaginary != 0.0) {
            if (imaginary < 0.0) {
                result += "-" + (-imaginary) + "i";
            } else {
                if (result.equals("")) {
                    result += imaginary + "i";
                } else {
                    result += "+" + imaginary + "i";
                }
            }
        }
        if (result.equals("")) {
            result += 0.0;
        }
        return result;
    }
}
