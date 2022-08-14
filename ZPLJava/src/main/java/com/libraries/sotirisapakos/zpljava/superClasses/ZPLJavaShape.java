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
package com.libraries.sotirisapakos.zpljava.superClasses;

import com.libraries.sotirisapakos.zpljava.components.ZPLJavaRectangle;
import com.libraries.sotirisapakos.zpljava.components.ZPLJavaEllipse;
import com.libraries.sotirisapakos.zpljava.components.ZPLJavaDiagonalLine;

/**
 * Parent class for {@link ZPLJavaRectangle}, {@link ZPLJavaEllipse}, {@link ZPLJavaDiagonalLine}.
 * <h3>Do not use this class as a {@link ZPLJavaComponent}
 * because it's usage is to reduce child classes their code size by extracting common code</h3>
 * <h3>Use one of these {@link ZPLJavaRectangle}, {@link ZPLJavaEllipse}, {@link ZPLJavaDiagonalLine} </h3>
 * <h2>Reason to exist: </h2>
 * They have similar structure -> common code.
 */
public class ZPLJavaShape extends ZPLJavaComponent{

    /**
     * Set color for rectangle line
     */
    public interface Color {
        String COLOR_BLACK = "B";
        String COLOR_WHITE = "W";
    }

    /**
     * Rectangle width
     */
    private int width = 0;
    /**
     * Rectangle height
     */
    private int height = 0;
    /**
     * Rectangle Thickness
     */
    private int thickness = 1;
    /**
     * Rectangle line color
     */
    private String color = Color.COLOR_BLACK;

    public ZPLJavaShape(){}
    public ZPLJavaShape(int width, int height){
        super.setLabelWidth(width);
        super.setLabelHeight(height);
    }
    public ZPLJavaShape(int x, int y, int width, int height){
        super.setX(x);
        super.setY(y);
        super.setLabelWidth(width);
        super.setLabelHeight(height);
    }
    public ZPLJavaShape(int x, int y, int width, int height, int thickness){
        super.setX(x);
        super.setY(y);
        super.setLabelWidth(width);
        super.setLabelHeight(height);
        setThickness(thickness);
    }

    /**
     * fill with color, the inside layer of border using thickness parameter. Do not change thickness
     * after calling this function or will remove background color.
     */
    public void fillBackground(){
        if(getWidth() > getHeight()) setThickness(getHeight() / 2);
        if(getWidth() < getHeight()) setThickness(getWidth() / 2);
        if(getWidth() == getHeight()) setThickness(getHeight() / 2);
    }

    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        if(width >= 0) this.width = width;
    }

    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        if(height >= 0) this.height = height;
    }

    public int getThickness() {
        return thickness;
    }
    public void setThickness(int thickness) {
        if(thickness > 0) {
            this.thickness = thickness;
        }
        if(thickness == 0){
            this.thickness = 1;
        }
    }

    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        if(color.equalsIgnoreCase(Color.COLOR_BLACK) || color.equalsIgnoreCase(Color.COLOR_WHITE)) this.color = color;
        else System.out.println("Choose one value between ZPLJavaRectangle.Color interface");
    }


    /**
     * Margin right is basically reducing the label length for this component. So, to implement margin
     * right, we reduce the label length visible to this component by marginRight dots.
     * <p>Formula implemented: { {@code labelWidth = labelWidth - marginRight} }</p>
     * @param marginRight the left margin of component.
     */
    @Override
    public void setMarginRight(int marginRight) {
        this.width -= marginRight;
    }
    /**
     * Margin bottom is basically reducing the label height visible to this component.
     * <p>Formula implemented: { {@code labelHeight = labelHeight - marginBottom} }</p>
     * @param marginBottom the bottom margin of component.
     */
    @Override
    public void setMarginBottom(int marginBottom){
        this.height -= marginBottom;
    }
    @Override
    public void setMargins(int left, int top, int right, int bottom){
        setMarginBottom(bottom + top);
        setMarginRight(right + left);
        super.setMarginLeft(left);
        super.setMarginTop(top);
    }
    @Override
    public void setMargins(int margin){
        super.setMarginLeft(margin);
        super.setMarginTop(margin);
        setMarginRight(margin * 2);
        setMarginBottom(margin * 2);
    }

    @Override
    public void setAlignment(String alignment) {
        if(!alignment.equals(POSITION_LEFT)
                && !alignment.equals(POSITION_CENTER)
                && !alignment.equals(POSITION_RIGHT)
                && !alignment.equalsIgnoreCase(POSITION_BOTTOM)) return;
        if(alignment.equalsIgnoreCase(POSITION_LEFT)) super.setX(0);
        if(alignment.equalsIgnoreCase(POSITION_CENTER)) setShapeAtCenterHorizontalPosition();
        if(alignment.equalsIgnoreCase(POSITION_RIGHT)) setShapeAtRightHorizontalPosition();
        if(alignment.equalsIgnoreCase(POSITION_BOTTOM)) setShapeAtBottomPosition();
    }
    /**
     * <h2>Warning: Center position work with Normal orientation only</h2>
     * There is no ^FB functionality for shape to work with...so we need to implement a formula
     * to change x value dynamically.
     */
    private void setShapeAtCenterHorizontalPosition(){
        int marginLeft;
        int whiteSpace;
        whiteSpace = super.getLabelWidth() - getWidth();
        marginLeft = whiteSpace / 2;
        super.setX(marginLeft);
    }
    /**
     * <h2>Warning: Right position work with Normal orientation only</h2>
     * There is no ^FB functionality for shape to work with...so we need to implement a formula
     * to change x value dynamically.
     */
    private void setShapeAtRightHorizontalPosition(){
        int marginLeft;
        int whiteSpace;

        whiteSpace = super.getLabelWidth() - width;
        marginLeft = whiteSpace;
        super.setX(marginLeft);
    }
    /**
     * <h2>Warning: bottom position work with Normal orientation only</h2>
     * There is no ^FB functionality for shape to work with...so we need to implement a formula
     * to change y value dynamically.
     * To set shape at the vertical bottom of the label, we have to execute 1 calculation:
     * <p>Find empty space: <code>WhiteSpace = labelHeight - height</code></p>
     * Now, set y value the white space we found!
     */
    private void setShapeAtBottomPosition(){
        super.setY(super.getLabelHeight());
        setMarginBottom(height);
    }

    /**
     * Must override this {@link ZPLJavaComponent#getComponentSize()} instruction
     */
    @Override
    public int getComponentSize() {
        int componentSize = 0;
        componentSize += height + thickness;
        return componentSize;
    }

    @Override
    public void generateInstruction() {}

    /**
     * Return instruction instead of parameters
     * @return component {@link ZPLJavaComponent#getInstruction() instruction}
     */
    @Override
    public String toString() {
        return super.getInstruction();
    }

}
