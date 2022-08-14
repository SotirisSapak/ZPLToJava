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

import com.libraries.sotirisapakos.zpljava.components.ZPLJavaText;
import com.libraries.sotirisapakos.zpljava.components.ZPLJavaBarcode;

/**
 * A parent class for all ZPLJava components like {@link ZPLJavaText} or {@link ZPLJavaBarcode}.
 * Can use this object to create ZPL code by using {@link ZPLJavaComponent#setInstruction(String)} method
 * but it is not recommended.
 */
public abstract class ZPLJavaComponent {

    /**
     * Any component can have an orientation. To apply orientation to component, just call
     * <p><code>setOrientation(Orientation.ORIENTATION_NORMAL)</code></p>
     */
    public interface Orientation {
        /**
         * Normal orientation
         */
        String ORIENTATION_NORMAL = "N";
        /**
         * Rotated orientation (90 degrees clockwise)
         */
        String ORIENTATION_ROTATED = "R";
        /**
         * Inverted orientation (180 degrees)
         */
        String ORIENTATION_INVERTED = "I";
        /**
         * Read from Bottom Up orientation (270 degrees)
         */
        String ORIENTATION_BOTTOM_UP = "B";
    }

    /**
     * Use this static values to set the position of any component. By default,
     * any {@link ZPLJavaComponent} has left alignment.
     * <p>This is for left alignment (maybe unnecessary value but ok!)</p>
     */
    public static final String POSITION_LEFT        = "L";
    /**
     * Use this static values to set the position of any component. By default,
     * any {@link ZPLJavaComponent} has left alignment.
     * <p>This is for right alignment</p>
     */
    public static final String POSITION_RIGHT       = "R";
    /**
     * Use this static values to set the position of any component. By default,
     * any {@link ZPLJavaComponent} has left alignment.
     * <p>This is for center alignment</p>
     */
    public static final String POSITION_CENTER      = "C";
    /**
     * Use this static values to set the position of any component. By default,
     * any {@link ZPLJavaComponent} has left alignment.
     * <p>This is for justified value</p>
     */
    public static final String POSITION_JUSTIFIED   = "J";
    /**
     * Beta implementation of component bottom placement
     **/
    public static final String POSITION_BOTTOM      = "B";

    /**
     * Component id
     */
    private String id = "";
    /**
     * Component placement position at x axis (left)
     */
    private int x = 0;
    /**
     * Component placement position at y axis (top)
     */
    private int y = 0;

    /**
     * Component alignment
     */
    private String alignment = POSITION_LEFT;
    /**
     * Component size. Must override
     */
    private int componentSize = 0;

    private int labelWidth = 0;
    private int labelHeight = 0;

    /**
     * Every component will converted to an instruction at zpl language
     */
    private String instruction = "";

    /**
     * <b>Must</b> include an empty constructor.
     */
    public ZPLJavaComponent(){}

    /**
     * Simple constructor if you want to init component with only label canvas size.
     * @param labelWidth label total width
     * @param labelHeight label total height
     */
    public ZPLJavaComponent(int labelWidth, int labelHeight){
        setLabelWidth(labelWidth);
        setLabelHeight(labelHeight);
    }
    /**
     * Simple constructor if you want to add a custom instruction in {@link ZPLJavaText} object.
     * No need to set anything else.
     * @param instruction instruction to create a {@link ZPLJavaComponent}
     */
    public ZPLJavaComponent(String instruction){
        if(!instruction.isEmpty()) this.instruction = instruction;
    }

    /**
     * Confirm label size to component to calculate the inner values.
     * @param labelWidth label total width
     * @param labelHeight label total height
     */
    public void setLabelSize(int labelWidth, int labelHeight){
        setLabelWidth(labelWidth);
        setLabelHeight(labelHeight);
    }
    /**
     * Set the alignment of the component inside the label.
     * @param alignment use one of these:
     *                  <ul>
     *                      <li>{@link #POSITION_LEFT}</li>
     *                      <li>{@link #POSITION_RIGHT}</li>
     *                      <li>{@link #POSITION_CENTER}</li>
     *                      <li>{@link #POSITION_JUSTIFIED}</li>
     *                  </ul>
     */
    public void setAlignment(String alignment) {
        this.alignment = alignment;
    }
    /**
     * Margin left is basically adding marginLeft value to x.
     * Formula implemented: { {@code x = x + marginLeft} }
     * @param marginLeft the left margin of component.
     */
    public void setMarginLeft(int marginLeft) {
        x += marginLeft;
    }
    /**
     * <h3>Not working on every Component - override this method if any component has different method
     * implementation</h3>
     * Margin right is basically reducing the label length for this component. So, to implement margin
     * right, we reduce the label length visible to this component by marginRight dots.
     * <p>Formula implemented: { {@code labelWidth = labelWidth - marginRight} }</p>
     * @param marginRight the left margin of component.
     */
    public void setMarginRight(int marginRight) {
        this.labelWidth -= marginRight;
    }
    /**
     * Margin left is basically adding marginTop value to y.
     * <p>Formula implemented: { {@code y = y + marginTop} }</p>
     * @param marginTop the top margin of component.
     */
    public void setMarginTop(int marginTop){
        y += marginTop;
    }
    /**
     * <h3>Not working on every Component - override this method if any component has different method
     * implementation</h3>
     * Margin bottom is basically reducing the label height visible to this component.
     * <p>Formula implemented: { {@code labelHeight = labelHeight - marginBottom} }</p>
     * @param marginBottom the bottom margin of component.
     */
    public void setMarginBottom(int marginBottom){
        labelHeight -= marginBottom;
    }
    public void setMargins(int left, int top, int right, int bottom){
        setMarginBottom(bottom + top);
        setMarginRight(right + left);
        setMarginLeft(left);
        setMarginTop(top);
    }
    public void setMargins(int margin){
        setMarginLeft(margin);
        setMarginTop(margin);
        setMarginRight(margin * 2);
        setMarginBottom(margin * 2);
    }

    /**
     * Function to set component below of another component.
     * Basically, add y value the top position of the other component and add the size of it.
     * <p>Formula implemented: { {@code y = y + other.y + other.size} }</p>
     * @param otherComponent Any {@link ZPLJavaComponent} to set this component below of.
     */
    public void belowOf(ZPLJavaComponent otherComponent){
        if(otherComponent != null) y += otherComponent.getY() + otherComponent.getComponentSize();
    }

    public String getAlignment() {
        return alignment;
    }
    public void setLabelWidth(int labelWidth) {
        this.labelWidth = labelWidth;
    }
    public int getLabelWidth() {
        return labelWidth;
    }
    public void setLabelHeight(int labelHeight) {
        this.labelHeight = labelHeight;
    }
    public int getLabelHeight() {
        return labelHeight;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }

    /**
     * This function must override every {@link ZPLJavaComponent} component
     */
    public void setComponentSize(int componentSize) {this.componentSize = componentSize;}
    public int getComponentSize() {
        return componentSize;
    }

    /**
     * Override this method at child classes. No need to call this function if you create a
     * custom {@link ZPLJavaComponent} object with custom instruction.
     */
    public abstract void generateInstruction();

    public String getInstruction() {
        return instruction;
    }
    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    @Override
    public String toString() {
        return getInstruction();
    }
}
