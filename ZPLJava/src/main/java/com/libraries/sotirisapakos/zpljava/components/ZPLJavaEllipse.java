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
 * ZPLJavaEllipse is a component to add an ellipse shape to {@link ZPLJavaLabel}. To add a circle just
 * set {@link ZPLJavaShape#width} & {@link ZPLJavaShape#height} values the same.
 * <p>Instruction template: { <code>^GE w,h,t,c </code> } where: </p>
 * <ul>
 *     <li>w: width</li>
 *     <li>h: height</li>
 *     <li>t: thickness</li>
 *     <li>c: color</li>
 * </ul>
 */
public class ZPLJavaEllipse extends ZPLJavaShape {

    public ZPLJavaEllipse(){}
    public ZPLJavaEllipse(int width, int height){
        super.setLabelWidth(width);
        super.setLabelHeight(height);
    }
    public ZPLJavaEllipse(int x, int y, int width, int height){
        super.setX(x);
        super.setY(y);
        super.setLabelWidth(width);
        super.setLabelHeight(height);
    }
    public ZPLJavaEllipse(int x, int y, int width, int height, int thickness){
        super.setX(x);
        super.setY(y);
        super.setLabelWidth(width);
        super.setLabelHeight(height);
        super.setThickness(thickness);
    }
    public ZPLJavaEllipse(int x, int y, int width, int height, int thickness, String color){
        super.setX(x);
        super.setY(y);
        super.setLabelWidth(width);
        super.setLabelHeight(height);
        super.setThickness(thickness);
        super.setColor(color);
    }

    /**
     * Margin right is basically reducing the component's x attribute by given value.
     * <p>Formula implemented: { {@code x = x - marginRight} }</p>
     * @param marginRight the right margin of component.
     */
    @Override
    public void setMarginRight(int marginRight) { super.setX(super.getX() - marginRight); }
    /**
     * Margin bottom is basically reducing the component's y attribute by given value.
     * <p>Formula implemented: { {@code y = y - marginBottom} }</p>
     * @param marginBottom the bottom margin of component.
     */
    @Override
    public void setMarginBottom(int marginBottom){
        super.setY(super.getY() - marginBottom);
    }

    /**
     * Apply margin to every aspect of component...clockwise.
     * @param left left margin
     * @param top top margin
     * @param right right margin
     * @param bottom bottom margin
     */
    @Override
    public void setMargins(int left, int top, int right, int bottom){
        setMarginBottom(bottom + top);
        setMarginRight(right + left);
        setMarginLeft(left);
        setMarginTop(top);
    }

    /**
     * Similar with the {@link ZPLJavaShape#setMargins(int, int, int, int)} method but instead of giving
     * different values to left, top, right and bottom margin, will set one value to all margins.
     * @param margin one margin for {left}, {top}, {right}, {bottom}
     */
    @Override
    public void setMargins(int margin){
        setMargins(margin, margin, margin, margin);
    }

    /**
     * Method to create the instruction based on given parameters.
     */
    @Override
    public void generateInstruction(){
        //instruction example:
        // ^FO0,0^GE812,1218,3,B^FS -> all over the label rectangle
        StringBuilder instruction = new StringBuilder();
        // add first part "^FO {x},{y}"
        instruction.append("^FO")
                .append(super.getX())
                .append(",")
                .append(super.getY());
        // add next part "^GE {width},{height}, {thickness}, {color}^FS"
        instruction.append("^GE")
                .append(super.getWidth())
                .append(",")
                .append(super.getHeight())
                .append(",")
                .append(super.getThickness())
                .append(",")
                .append(super.getColor())
                .append("^FS");
        super.setInstruction(instruction.toString());
    }

}
