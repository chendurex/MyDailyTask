package com.design.patterns.prototype_patterns;

/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.prototype_patterns
 * @date 2016/4/7 9:11
 */
public class SquareImpl extends IShape {
   public SquareImpl(Integer id) {
       setId(id);
   }

    @Override
    public void draw() {
        System.out.println("draw square");
    }
}
