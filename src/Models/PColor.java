package Models;

public enum PColor {
    W, B;
    private static PColor[] vals = values();

    public PColor next(){
        return vals[(this.ordinal() + 1) % vals.length];
    }

    public String toString(){
        switch (this){
            case W: return "w";
            case B: return "b";
            default: throw new IllegalArgumentException();
        }
    }
}
