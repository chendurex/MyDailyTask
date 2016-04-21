package com.design.patterns.prototype_patterns;

/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.prototype_patterns
 * @date 2016/4/7 9:07
 */
public abstract class IShape implements Cloneable{
    public Integer id;
    protected String type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public abstract void draw();

    @Override
    protected Object clone() {
        Object obj = null;
        try {
            obj = super.clone();
        }catch (CloneNotSupportedException exception) {
            exception.printStackTrace();
        }
        return obj;
    }
}
