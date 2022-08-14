/*
 * Copyright 2022 SotirisSapak
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.libraries.sotirisapakos.zpljava.components;

import com.libraries.sotirisapakos.zpljava.superClasses.ZPLJavaShape;

/**
 * Draw rectangle object in a {@link ZPLJavaLabel} object
 */
public class ZPLJavaRectangle extends ZPLJavaShape {

    private int cornerRadius = 0;

    public ZPLJavaRectangle(){}
    public ZPLJavaRectangle(int labelWidth, int labelHeight){
        super.setLabelWidth(labelWidth);
        super.setLabelHeight(labelHeight);
    }
    public ZPLJavaRectangle(int x, int y, int width, int height){
        super.setX(x);
        super.setY(y);
        super.setLabelWidth(width);
        super.setLabelHeight(height);
    }
    public ZPLJavaRectangle(int x, int y, int width, int height, int thickness){
        super.setX(x);
        super.setY(y);
        super.setLabelWidth(width);
        super.setLabelHeight(height);
        setThickness(thickness);
    }
    public ZPLJavaRectangle(int x, int y, int width, int height, int thickness, int cornerRadius){
        super.setX(x);
        super.setY(y);
        super.setLabelWidth(width);
        super.setLabelHeight(height);
        setThickness(thickness);
        setCornerRadius(cornerRadius);
    }
    public ZPLJavaRectangle(int x, int y, int width, int height, int thickness, int cornerRadius,
                            String color){
        super.setX(x);
        super.setY(y);
        super.setLabelWidth(width);
        super.setLabelHeight(height);
        setThickness(thickness);
        setCornerRadius(cornerRadius);
        setColor(color);
    }

    /**
     * Method to create the instruction based on given parameters.
     */
    @Override
    public void generateInstruction(){
        //instruction example:
        // ^FO0,0^GB812,1218,3,B,1^FS -> all over the label rectangle
        StringBuilder instruction = new StringBuilder();
        // add first part "^FO {x},{y}"
        instruction.append("^FO")
                .append(super.getX())
                .append(",")
                .append(super.getY());
        // add next part "^GB {width}, {height}, {thickness}, {color}, {cornerRadius} ^FS"
        instruction.append("^GB")
                .append(super.getWidth())
                .append(",")
                .append(super.getHeight())
                .append(",")
                .append(super.getThickness())
                .append(",")
                .append(super.getColor())
                .append(",")
                .append(cornerRadius)
                .append("^FS");
        super.setInstruction(instruction.toString());
    }

    public int getCornerRadius() {
        return cornerRadius;
    }
    public void setCornerRadius(int cornerRadius) {
        if(cornerRadius >=0 && cornerRadius<=8) this.cornerRadius = cornerRadius;
        else System.out.println("Enter value between 0 and 8");
    }


}
