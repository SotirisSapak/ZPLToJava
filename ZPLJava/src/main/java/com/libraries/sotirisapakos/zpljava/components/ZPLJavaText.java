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

import com.libraries.sotirisapakos.zpljava.superClasses.ZPLJavaComponent;

/**
 * ZPLJavaText is a component to add simple text to {@link ZPLJavaLabel}.
 */
public class ZPLJavaText extends ZPLJavaComponent {

    /**
     * Instead of giving a simple value to font size, give a specific style instead
     */
    public interface FontStyle{
        int FONT_SMALL      = 30;
        int FONT_NORMAL     = 40;
        int FONT_LARGE      = 50;
        int FONT_XLARGE     = 70;
        int FONT_XXLARGE    = 90;
    }

    public static final int     DEFAULT_FONT_SIZE = FontStyle.FONT_SMALL;
    public static final boolean DEFAULT_SPECIAL_CHAR_SUPPORT = true;

    /**
     * Text size parameter
     */
    private int fontSize = DEFAULT_FONT_SIZE;
    /**
     * Component parameter to add support for special characters (hex values in string)
     * <p>Example: If you want to add the EURO(€) symbol</p>
     */
    private boolean specialCharacterSupport = DEFAULT_SPECIAL_CHAR_SUPPORT;
    /**
     * Text block will support...text value!
     */
    private String text;

    public ZPLJavaText(){}
    /**
     * Create a simple text with custom [x,y] position and a text. Will use default values for other
     * parameters. If you call this construction, you will not allowed to change any parameter after
     * call.
     * @param x position
     * @param y position
     * @param text text to add
     * @param labelWidth the width of the label
     */
    public ZPLJavaText(int x, int y, String text, int labelWidth){
        if(x>=0) super.setX(x);
        if(y>=0) super.setY(y);
        if(!text.isEmpty()) this.text = text;
        if(labelWidth >= 0) super.setLabelWidth(labelWidth);
    }

    public void setFontSize(int fontSize) {
        if(fontSize >= 0) this.fontSize = fontSize;
    }
    public void setSpecialCharacterSupport(boolean specialCharacterSupport) {
        this.specialCharacterSupport = specialCharacterSupport;
    }

    /**
     * Method to create the instruction based on given parameters.
     */
    @Override
    public void generateInstruction(){
        //instruction example:
        // ^FO0,50^A0,80^FB812,1,1,c,0^FH_^FDText with euro symbol at the end_15\&^FS
        StringBuilder instruction = new StringBuilder();
        // add first part "^FO{x},{y}"
        instruction.append("^FO")
                .append(super.getX())
                .append(",")
                .append(super.getY());
        // add second part "^A{fontStyle},{fontSize}"
        instruction.append("^A")
                .append("0")
                .append(",")
                .append(fontSize);
        // add third part "^FB{labelWidth},{numberOfLines: 1},{addOrDeleteSpaces: 0},{textAlignment},{0}"
        instruction.append("^FB")
                .append(super.getLabelWidth())
                .append(",1,")
                .append("0,")
                .append(super.getAlignment())
                .append(",0");
        // if special character support is enabled, then add the below text inside instruction
        if(specialCharacterSupport) instruction.append("^FH_");
        // add text abject "^FD{text}\&^FS"
        instruction.append("^FD")
                .append(text)
                .append("\\&")
                .append("^FS");
        super.setInstruction(instruction.toString());
    }

    public void setText(String text) {
        if(!text.isEmpty()) this.text = text;
    }
    public String getText() {
        return text;
    }

    /**
     * Must override this {@link ZPLJavaComponent#getComponentSize()} instruction
     */
    @Override
    public int getComponentSize() {
        int componentSize = 0;
        componentSize += fontSize;
        return componentSize;
    }

    /**
     * Return instruction instead of parameters
     * @return component {@link ZPLJavaComponent#getInstruction() instruction}
     */
    @Override
    public String toString() {
        return super.getInstruction();
    }
}
