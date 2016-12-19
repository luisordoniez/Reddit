package com.reddit.luisordoniez.reddit.models;

/**
 * Created by luisordoniez on 14/12/16.
 * <p/>
 * Esta clase recoje todos los campos del JSON con key 'children'
 */

public class Child {

    /**
     * Atributos
     */

    private String kind;
    private Data data;

    /**
     * Metodos Get y Set
     */

    public String getKind() {
        return kind;
    }
    public void setKind(String kind) {
        this.kind = kind;
    }

    public Data getData() {
        return data;
    }
    public void setData(Data data) {
        this.data = data;
    }
}
