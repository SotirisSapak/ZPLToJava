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
 * ZPLJavaDiagonalLine is a component to add a diagonal line to {@link ZPLJavaLabel}.
 * <p>Instruction template: { <code>^GD w,h,t,c,o</code> } where: </p>
 * <ul>
 *     <li>w: width</li>
 *     <li>h: height</li>
 *     <li>t: thickness</li>
 *     <li>c: color</li>
 *     <li>o: orientation</li>
 * </ul>
 */
public class ZPLJavaDiagonalLine extends ZPLJavaShape {

    /**
     * Choose one of these at {@code orientation} parameter
     */
    public interface DiagonalLineOrientation{
        String DIAGONAL_RIGHT = "R";
        String DIAGONAL_LEFT = "L";
    }

    private String orientation = DiagonalLineOrientation.DIAGONAL_RIGHT;

    public ZPLJavaDiagonalLine(){}
    public ZPLJavaDiagonalLine(int width, int height){
        super.setLabelWidth(width);
        super.setLabelHeight(height);
    }
    public ZPLJavaDiagonalLine(int x, int y, int width, int height){
        super.setX(x);
        super.setY(y);
        super.setLabelWidth(width);
        super.setLabelHeight(height);
    }
    public ZPLJavaDiagonalLine(int x, int y, int width, int height, int thickness){
        super.setX(x);
        super.setY(y);
        super.setLabelWidth(width);
        super.setLabelHeight(height);
        super.setThickness(thickness);
    }
    public ZPLJavaDiagonalLine(int x, int y, int width, int height, int thickness, String orientation){
        super.setX(x);
        super.setY(y);
        super.setLabelWidth(width);
        super.setLabelHeight(height);
        super.setThickness(thickness);
        setOrientation(orientation);
    }
    public ZPLJavaDiagonalLine(int x, int y, int width, int height, int thickness,  String orientation,
                            String color){
        super.setX(x);
        super.setY(y);
        super.setLabelWidth(width);
        super.setLabelHeight(height);
        super.setThickness(thickness);
        setOrientation(orientation);
        super.setColor(color);
    }

    /**
     * Method to create the instruction based on given parameters.
     */
    @Override
    public void generateInstruction(){
        //instruction example:
        // ^FO0,0^GD812,1218,3,B,R^FS -> all over the label rectangle
        StringBuilder instruction = new StringBuilder();
        // add first part "^FO {x},{y}"
        instruction.append("^FO")
                .append(super.getX())
                .append(",")
                .append(super.getY());
        // add next part "^GD {width},{height}, {thickness}, {color}, {orientation} ^FS"
        instruction.append("^GD")
                .append(super.getWidth())
                .append(",")
                .append(super.getHeight())
                .append(",")
                .append(super.getThickness())
                .append(",")
                .append(super.getColor())
                .append(",")
                .append(orientation)
                .append("^FS");
        super.setInstruction(instruction.toString());
    }

    public String getOrientation() {
        return orientation;
    }

    /**
     * Choose one of the below values:
     * <ul>
     *     <li>{@link DiagonalLineOrientation#DIAGONAL_LEFT}</li>
     *     <li>{@link DiagonalLineOrientation#DIAGONAL_RIGHT}</li>
     * </ul>
     * @param orientation give diagonal line the orientation
     */
    public void setOrientation(String orientation) {
        if(!orientation.equalsIgnoreCase(DiagonalLineOrientation.DIAGONAL_LEFT) &&
                !orientation.equalsIgnoreCase(DiagonalLineOrientation.DIAGONAL_RIGHT)) return;
        this.orientation = orientation;
    }
}
