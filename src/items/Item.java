package items;

abstract public class Item {

    private String name;

    Item(String n){
        name = n;
        
    }

    public String getName(){
        return name;
    }

    abstract public void use();

}
