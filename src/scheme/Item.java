package scheme;

import java.util.*;


interface Item {
    public String getValue();
    public Item evaluate(Bindings bindings, Stack<Binding> temporaryBindings);
    public Number getNumber();
    public boolean isNumber();
    public boolean isString();
    public boolean isSymbol();
    public boolean isName();
    public boolean isList();
    public boolean isBoolean();
    public Item clone();
}