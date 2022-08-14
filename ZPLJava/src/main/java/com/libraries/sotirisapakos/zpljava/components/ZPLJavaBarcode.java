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
import com.libraries.sotirisapakos.zpljava.superClasses.ZPLJavaShape;

/**
 * ZPLJavaBarcode is a component to add barcode128 to {@link ZPLJavaLabel}.
 * <p>Instruction template: { <code>^BC o,h,f,g,e,m</code> } where: </p>
 * <ul>
 *     <li>o: orientation</li>
 *     <li>h: height</li>
 *     <li>f: Print Interpretation Line</li>
 *     <li>g: Print Interpretation Line Above Code</li>
 *     <li>e: UCC Check Digit</li>
 *     <li>m: Mode</li>
 * </ul>
 */
public class ZPLJavaBarcode extends ZPLJavaComponent{

    /**
     * Call this interface to set barcode text placement position.
     * <p>Recommended usage:</p>
     * <code>{@link ZPLJavaBarcode#setBarcodeTextPlacement(int)}</code>
     */
    public interface BarcodeTextPlacement {
        /**
         * Barcode does not provide any text
         */
        int NO_TEXT = 0;
        /**
         * Show barcode data above barcode
         */
        int TEXT_ABOVE = 1;
        /**
         * Show barcode data below barcode
         * <h3>For below text, use marginBottom to add space at the bottom of the barcode or marginTop
         * if an item is belowOf this barcode</h3>
         */
        int TEXT_BELOW = 2;
    }

    /**
     * This is for extra Barcode field at the end that in ZEBRA documentation is referenced as (m) mode.
     * Read each mode for extra information.
     * <h3>This library version does not support different barcode modes</h3>
     * @see <a href="https://support.zebra.com/cpws/docs/zpl/code_128.htm">Official barcode128 documentation</a>
     */
    public interface BarcodeMode {
        /**
         * No mode selected
         */
        String NO_MODE = "N";
        /**
         * (^FD or ^SN statement must contain 19 numeric digits it can also contain valid alpha characters).
         * Subset C using FNC1 values is automatically selected.
         */
        String UCC_CASE_MODE = "U";
        /**
         * The Automatic Mode analyzes the data sent and automatically determines the best packing method.
         * Full ASCII Character Set can be used in the ^FD statement.
         * Printer will determine when to shift subsets.
         * A string of four or more numeric digits will cause automatic shift to subset C.
         */
        String AUTOMATIC_MODE = "A";
        /**
         * This new mode:
         * <ul>
         *      <li>Will allow dealing with UCC/EAN with and without chained application identifiers.</li>
         *      <li>Also the code will start in the appropriate subset followed
         *      by FNC1 to indicate a UCC/EAN 128 barcode.</li>
         *      <li>The printer will automatically strip out parenthesis and spaces for encoding
         *      but print them in the human-readable section.</li>
         *      <li>The printer will automatically determine if a check digit is required,
         *      calculate it, and print it.</li>
         *      <li>Automatic sizing of the human readable.</li>
         * </ul>
         */
        String NEW_MODE = "D";
    }

    /**
     * {barcodeWidth} -> Default value of barcode width
     */
    public static final int DEFAULT_BARCODE_WIDTH = 3;
    /**
     * {barcodeHeight} -> Default value of barcode height
     */
    public static final int DEFAULT_BARCODE_HEIGHT = 60;

    /**
     * Barcode component placement
     */
    public String barcodePlacement = POSITION_LEFT;
    /**
     * Barcode orientation. One value from
     * {@link ZPLJavaComponent.Orientation Orientation}
     * interface that is placed in {@link ZPLJavaComponent}
     */
    private String orientation = Orientation.ORIENTATION_NORMAL;
    /**
     * Barcode height value
     */
    private int barcodeHeight = DEFAULT_BARCODE_HEIGHT;
    /**
     * Set barcode to check UCC digit
     */
    private boolean UCCCheckDigit = false;
    /**
     * Barcode mode
     * @see BarcodeMode
     */
    private String barcodeMode = BarcodeMode.NO_MODE;
    /**
     * Barcode width
     */
    private int barcodeWidth = DEFAULT_BARCODE_WIDTH;
    /**
     * Set interpretation line for barcode text.
     * <p>3 current states:</p>
     * <ul>
     *      <li>{@link BarcodeTextPlacement#NO_TEXT}</li>
     *      <li>{@link BarcodeTextPlacement#TEXT_ABOVE}</li>
     *      <li>{@link BarcodeTextPlacement#TEXT_BELOW}</li>
     * </ul>
     */
    private int barcodeTextPlacement = BarcodeTextPlacement.NO_TEXT;
    /**
     * Text that barcode will preview
     */
    private String barcodeData = "";
    /**
     * Enable or disable the rectangle at the back of the barcode
     */
    private boolean applyBackground = false;
    /**
     * If user enable background, then this rectangle will be created before the barcode
     */
    private ZPLJavaRectangle backgroundRectangle;

    /**
     * Constructor to create a barcode by giving only the printable data
     * @param data data to print
     */
    public ZPLJavaBarcode(String data){ setBarcodeData(data); }
    /**
     * Create a simple barcode128 with basic data
     * @param x position
     * @param y position
     * @param data barcode data
     * @param labelWidth the total label width
     */
    public ZPLJavaBarcode(int x, int y, String data, int labelWidth){
        if(x>=0) super.setX(x);
        if(y>=0) super.setY(y);
        if(!data.isEmpty()) setBarcodeData(data);
        if(labelWidth >= 0) super.setLabelWidth(labelWidth);
    }

    /**
     * Method to create the instruction based on given parameters.
     */
    @Override
    public void generateInstruction(){
        StringBuilder instruction = new StringBuilder();
        if(applyBackground){
            backgroundRectangle.generateInstruction();
            instruction.append(backgroundRectangle.toString());
            instruction.append("\n");
            instruction.append("\t");
        }
        // add next part "^FO{x},{y}"
        instruction.append("^FO")
                .append(super.getX())
                .append(",")
                .append(super.getY());
        // add next part "^BY {barWidth}"
        instruction.append("^BY ")
                .append(barcodeWidth);
        // add next part "^BC {orientation} ,{height}, {textBelow}, {textAbove}, {addUccCheckDigit}, {mode}"
        instruction.append("^BC")
                .append(orientation)
                .append(",")
                .append(barcodeHeight)
                .append(",");
        if(barcodeTextPlacement == BarcodeTextPlacement.NO_TEXT) instruction.append("N,N,");
        if(barcodeTextPlacement == BarcodeTextPlacement.TEXT_ABOVE) instruction.append("Y,Y,");
        if(barcodeTextPlacement == BarcodeTextPlacement.TEXT_BELOW) instruction.append("Y,N,");
        if(UCCCheckDigit) instruction.append("Y,"); else instruction.append("N,");
        instruction.append(barcodeMode);
        // add next part "^FD{text}^FS"
        instruction.append("^FD")
                .append(barcodeData)
                .append("^FS");
        super.setInstruction(instruction.toString());
    }

    /**
     * Set barcode position based on below values:
     * <ul>
     *      <li>{@link #POSITION_LEFT}</li>
     *      <li>{@link #POSITION_CENTER}</li>
     *      <li>{@link #POSITION_RIGHT}</li>
     * </ul>
     * @param alignment one of the above permitted values
     */
    @Override
    public void setAlignment(String alignment) {
        if(!alignment.equals(POSITION_LEFT)
                && !alignment.equals(POSITION_CENTER)
                && !alignment.equals(POSITION_RIGHT)
                && !alignment.equalsIgnoreCase(POSITION_BOTTOM)) return;
        if(alignment.equalsIgnoreCase(POSITION_LEFT)) super.setX(0);
        if(alignment.equalsIgnoreCase(POSITION_CENTER)) setBarcodeAtCenterHorizontalPosition();
        if(alignment.equalsIgnoreCase(POSITION_RIGHT)) setBarcodeAtRightHorizontalPosition();
        if(alignment.equalsIgnoreCase(POSITION_BOTTOM)) setBarcodeAtBottomPosition();
    }

    /**
     * <h2>Warning: Center position work with Normal orientation barcode only</h2>
     * There is no ^FB functionality for barcode to work with...so we need to implement a formula
     * to change x value dynamically. A code 128 barcode (excluding the two 10x quiet zones)
     * is made up of:
     * <ul>
     *     <li>a start character (11x wide)</li>
     *     <li>n encoded character of width 11x</li>
     *     <li>a CRC (11x wide)</li>
     *     <li>a stop character (12x wide)</li>
     * </ul>
     * Using above hints, the formula to calculate the barcode length in dots is:
     * <p><code> (34 + (n * 11)) * x </code></p>
     * where:
     * <ul>
     *     <li>x: barcode width</li>
     *     <li>n: number of encoded characters</li>
     * </ul>
     * To set barcode at the horizontal center of the label, we have to execute 2 more calculations:
     * <p>Find empty space: <code>WhiteSpace = labelWidth - barcodeLength</code></p>
     * <p>Find Margin left: <code>MarginLeft = WhiteSpace / 2
     * //2 because we have marginLeft and marginRight</code></p>
     */
    private void setBarcodeAtCenterHorizontalPosition(){
        int marginLeft;
        int whiteSpace;
        int barcodeLength = getBarcodeLength();
        whiteSpace = super.getLabelWidth() - barcodeLength;
        marginLeft = whiteSpace / 2;
        super.setX(marginLeft);
    }
    /**
     * <h2>Warning: Right position work with Normal orientation barcode only</h2>
     * There is no ^FB functionality for barcode to work with...so we need to implement a formula
     * to change x value dynamically. A code 128 barcode (excluding the two 10x quiet zones)
     * is made up of:
     * <ul>
     *     <li>a start character (11x wide)</li>
     *     <li>n encoded character of width 11x</li>
     *     <li>a CRC (11x wide)</li>
     *     <li>a stop character (12x wide)</li>
     * </ul>
     * Using above hints, the formula to calculate the barcode length in dots is:
     * <p><code> (34 + (n * 11)) * x </code></p>
     * where:
     * <ul>
     *     <li>n: number of encoded characters</li>
     *     <li>x: barcode width</li>
     * </ul>
     * To set barcode at the horizontal right of the label, we have to execute 1 more calculation:
     * <p>Find empty space: <code>WhiteSpace = labelWidth - barcodeLength</code></p>
     * Now, set x value the white space we found!
     */
    private void setBarcodeAtRightHorizontalPosition(){
        if(!orientation.equals(Orientation.ORIENTATION_NORMAL)) return;
        int marginLeft;
        int whiteSpace;
        int barcodeLength;

        barcodeLength = getBarcodeLength();
        whiteSpace = super.getLabelWidth() - barcodeLength;
        marginLeft = whiteSpace;
        super.setX(marginLeft);
    }
    /**
     * <h2>Warning: bottom position work with Normal orientation barcode only</h2>
     * There is no ^FB functionality for barcode to work with...so we need to implement a formula
     * to change y value dynamically.
     * To set barcode at the vertical bottom of the label, we have to execute 1 calculation:
     * <p>Find empty space: <code>WhiteSpace = labelHeight - componentSize</code></p>
     * Now, set y value the white space we found!
     */
    private void setBarcodeAtBottomPosition(){
        if(!orientation.equals(Orientation.ORIENTATION_NORMAL)) return;
        int whiteSpace;

        whiteSpace = super.getLabelHeight() - getComponentSize();
        super.setY(whiteSpace);
    }
    /**
     * Calculate barcode length based on this formula:
     * <p>{@code (34 + (n * 11)) * x}</p>
     * where:
     * <li>n encoded character of width 11x</li>
     * <ul>
     *     <li>34: </li>
     *      <ul>
     *          <li>a start character (11x wide)</li>
     *          <li>a CRC (11x wide)</li>
     *          <li>a stop character (12x wide)</li>
     *      </ul>
     *      <li>n * 11: </li>
     *      <ul>
     *          <li>n encoded character of width 11x</li>
     *      </ul>
     *      <li>(...) * x: </li>
     *      <ul>
     *          <li>x: barcode width</li>
     *      </ul>
     * </ul>
     * @return the barcode length
     */
    public int getBarcodeLength(){
        if(!orientation.equals(Orientation.ORIENTATION_NORMAL)) return 0;
        int numberOfChars = barcodeData.toCharArray().length;
        return (34 + (numberOfChars * 11)) * barcodeWidth;
    }

    @Override
    public void setMarginRight(int marginRight) {
        super.setX(super.getX() - marginRight);
    }
    @Override
    public void setMarginBottom(int marginBottom){
        super.setY(super.getY() - marginBottom);
    }

    public void setBarcodeData(String barcodeData) {
        this.barcodeData = barcodeData;
    }
    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }
    public void setUCCCheckDigit(boolean UCCCheckDigit) {
        this.UCCCheckDigit = UCCCheckDigit;
    }
    public void setBarcodeMode(String barcodeMode) {
        this.barcodeMode = barcodeMode;
    }
    public void setBarcodeWidth(int barcodeWidth) {
        this.barcodeWidth = barcodeWidth;
    }
    public void setBarcodeHeight(int barcodeHeight) {
        this.barcodeHeight = barcodeHeight;
    }
    public void setBarcodeTextPlacement(int barcodeTextPlacement) {
        this.barcodeTextPlacement = barcodeTextPlacement;
    }

    /**
     * This function will create a solid background rectangle that will overlay the other components
     * below the barcode. Think of non-transparent background barcode! Call this method after setting
     * up the barcode component structure to work properly.
     * @param color choose the color. Available input:
     *              <p>{@link ZPLJavaRectangle.Color#COLOR_BLACK}</p>
     *                 {@link ZPLJavaRectangle.Color#COLOR_WHITE}
     *
     *
     * @param paddingLeftAndRight the padding at the left and right side of barcode...if you want to
     *                            extend the background. (only >= 0)
     * @param paddingTopAndBottom the padding at the top and bottom side of barcode...(only >= 0)
     */
    public void applyBackground(String color,
                                int paddingLeftAndRight, int paddingTopAndBottom){
        this.applyBackground = true;
        if(paddingLeftAndRight < 0) paddingLeftAndRight = 0;
        if(paddingTopAndBottom < 0) paddingTopAndBottom = 0;
        if(!color.equalsIgnoreCase(ZPLJavaShape.Color.COLOR_WHITE) &&
                !color.equalsIgnoreCase(ZPLJavaShape.Color.COLOR_BLACK)) {
            color = ZPLJavaRectangle.Color.COLOR_WHITE;
        }
        backgroundRectangle = new ZPLJavaRectangle(getLabelWidth(), getLabelHeight());
        // Margins include both left and right
        backgroundRectangle.setWidth(getBarcodeLength() + (paddingLeftAndRight * 2));
        backgroundRectangle.setHeight(getComponentSize() + (paddingTopAndBottom * 2));
        backgroundRectangle.setX(super.getX() - paddingLeftAndRight);
        backgroundRectangle.setY(super.getY() - paddingTopAndBottom);
        backgroundRectangle.setColor(color);
        backgroundRectangle.fillBackground();
    }

    public String getBarcodeData() {
        return barcodeData;
    }
    public String getOrientation() {
        return orientation;
    }
    public int getBarcodeHeight() {
        return barcodeHeight;
    }
    public boolean isUCCCheckDigit() {
        return UCCCheckDigit;
    }
    public String getBarcodeMode() {
        return barcodeMode;
    }
    public int getBarcodeWidth() {
        return barcodeWidth;
    }
    public int getBarcodeTextPlacement() {
        return barcodeTextPlacement;
    }

    /**
     * Must override this {@link ZPLJavaComponent#getComponentSize()} instruction
     */
    @Override
    public int getComponentSize() {
        int size = 0;
        size += barcodeHeight;
        if(barcodeTextPlacement == BarcodeTextPlacement.TEXT_BELOW) size += 30;
        // if(barcodeTextPlacement == BarcodeTextPlacement.TEXT_ABOVE) size += 60;
        return size;
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
