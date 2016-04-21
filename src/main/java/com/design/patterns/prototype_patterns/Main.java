package com.design.patterns.prototype_patterns;

/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.prototype_patterns
 * @date 2016/4/7 9:25
 */
public class Main {
    public static void main(String[] args) {
        IShape square = new SquareImpl(1);
        IShape rectangle = new RectangleImpl(2);
        IShape circle = new CircleImpl(3);
        CacheShape.loadCache(1,square);
        CacheShape.loadCache(2,rectangle);
        CacheShape.loadCache(3,circle);

        IShape cloneOne = (IShape)CacheShape.getCache(1).clone();
        System.out.println("origin cloneOne:"+cloneOne.getId());
        System.out.println("origin square:"+square.getId());
        square.setId(5);
        System.out.println("recent cloneOne:"+cloneOne.getId());
        System.out.println("recent square:"+square.getId());
    }
}
