package interpreter.bytecode;

public abstract class AddressLabelCode extends ByteCode{

    protected int address;

    protected String label;

    public String getLabel(){
        return label;
    }

    public void setAddress(int address){
        this.address = address;
    }
}
