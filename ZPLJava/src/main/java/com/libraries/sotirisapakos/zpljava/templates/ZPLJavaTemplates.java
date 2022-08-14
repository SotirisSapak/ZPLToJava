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
package com.libraries.sotirisapakos.zpljava.templates;

import com.libraries.sotirisapakos.zpljava.components.ZPLJavaEllipse;
import com.libraries.sotirisapakos.zpljava.components.ZPLJavaLabel;
import com.libraries.sotirisapakos.zpljava.components.ZPLJavaRectangle;
import com.libraries.sotirisapakos.zpljava.components.ZPLJavaText;
import com.libraries.sotirisapakos.zpljava.components.ZPLJavaBarcode;
import com.libraries.sotirisapakos.zpljava.superClasses.ZPLJavaComponent;
import com.libraries.sotirisapakos.zpljava.superClasses.ZPLJavaShape;

/**
 * Some ready-to-go labels to use.
 */
public class ZPLJavaTemplates {

    /**
     * <h2>Template #1</h2>
     * <h3>Components:</h3>
     * <ul>
     *     <li> {@link ZPLJavaText}      -> Text #1: title</li>
     *     <li> {@link ZPLJavaText}      -> Text #2: subtitle</li>
     *     <li> {@link ZPLJavaBarcode}   -> Barcode</li>
     * </ul>
     * A small label template that has a rounded border around the label with 20dot inset, two basic
     * text, one for primary role and the other for information purposes. At the bottom, there is a
     * barcode128 with a solid background to cut the border near it to avoid component conflict!
     * <h3>RECOMMENDED LABEL PARAMETERS (for better user experience): </h3>
     * <ul>
     *     <li>{@link ZPLJavaLabel#labelWidth inchesWidth:} 2</li>
     *     <li>{@link ZPLJavaLabel#labelHeight inchesHeight:} 1</li>
     *     <li>dpmmValue: {@link com.libraries.sotirisapakos.zpljava.config.LabelSize#_8DPMM
     *     LabelSize._8DPMM}</li>
     * </ul>
     * @param label label to create the template
     * @param centralizedText true if you want to set the texts at center of label
     * @param titleText the title's text
     * @param subtitleText the subtitle's text
     * @param barcodeText the barcode data
     * @return the generated label of Template #1
     */
    public static String getTemplate1(ZPLJavaLabel label,
                                      boolean centralizedText, String titleText,
                                      String subtitleText, String barcodeText){

        // check label before creating template
        if(label == null || label.getLabelHeight() <= 0 || label.getLabelWidth() <= 0) {
            System.out.println("Please create first a valid label!... return empty string!");
            return "";
        }
        // First of all, create title
        ZPLJavaText title = new ZPLJavaText();
        title.setId("title");
        title.setLabelSize(label.getLabelWidth(), label.getLabelHeight());
        title.setText(titleText);
        title.setMarginTop(40);
        if(centralizedText) title.setAlignment(ZPLJavaComponent.POSITION_CENTER);
        else title.setMarginLeft(40);
        // create subtitle
        ZPLJavaText subtitle = new ZPLJavaText();
        subtitle.setId("subtitle");
        subtitle.setLabelSize(label.getLabelWidth(), label.getLabelHeight());
        subtitle.setText(subtitleText);
        subtitle.belowOf(title);
        subtitle.setFontSize(20);
        if(centralizedText) subtitle.setAlignment(ZPLJavaComponent.POSITION_CENTER);
        else subtitle.setMarginLeft(40);
        // Create barcode
        ZPLJavaBarcode barcode = new ZPLJavaBarcode(barcodeText);
        barcode.setId("barcode");
        barcode.setLabelSize(label.getLabelWidth(), label.getLabelHeight());
        barcode.setBarcodeWidth(2);
        barcode.setAlignment(ZPLJavaComponent.POSITION_BOTTOM);
        barcode.setAlignment(ZPLJavaComponent.POSITION_CENTER);
        barcode.setMarginBottom(20);
        barcode.applyBackground(ZPLJavaRectangle.Color.COLOR_WHITE, 10, 10);
        // add border
        ZPLJavaRectangle borderBox = new ZPLJavaRectangle();
        borderBox.setId("borderBox");
        borderBox.setLabelSize(label.getLabelWidth(), label.getLabelHeight());
        borderBox.setWidth(label.getLabelWidth());
        borderBox.setHeight(label.getLabelHeight());
        borderBox.setThickness(2);
        borderBox.setMargins(20, 20, 20, barcode.getComponentSize() - 10);
        borderBox.setCornerRadius(1);

        label.addAllComponents(borderBox, title, subtitle, barcode);
        return label.getLabelCode();
    }

    /**
     * <h2>Template #2</h2>
     * <h3>Components:</h3>
     * <ul>
     *     <li> {@link ZPLJavaText}      -> Text #1: title</li>
     *     <li> {@link ZPLJavaText}      -> Text #2: subtitle</li>
     *     <li> {@link ZPLJavaText}      -> Text #3: info</li>
     *     <li> {@link ZPLJavaBarcode}   -> Barcode</li>
     * </ul>
     * A small label template that has a rounded border around the label with 20dot inset, three
     * texts, one for primary role, the other for secondary text and the last one for information
     * purposes. At the bottom, there is a barcode128 with a solid background to cut the
     * border near it to avoid component conflict!
     * <h3>RECOMMENDED LABEL PARAMETERS (for better user experience): </h3>
     * <ul>
     *     <li>{@link ZPLJavaLabel#labelWidth inchesWidth:} 3</li>
     *     <li>{@link ZPLJavaLabel#labelHeight inchesHeight:} 2</li>
     *     <li>dpmmValue: {@link com.libraries.sotirisapakos.zpljava.config.LabelSize#_8DPMM
     *     LabelSize._8DPMM}</li>
     * </ul>
     * @param label label to create the template
     * @param titleText the title's text
     * @param subtitleText the subtitle's text
     * @param infoText the info's text
     * @param barcodeText the barcode data
     * @return the generated label of Template #1
     */
    public static String getTemplate2(ZPLJavaLabel label, String titleText,
                                      String subtitleText, String infoText, String barcodeText){

        // check label before creating template
        if(label == null || label.getLabelHeight() <= 0 || label.getLabelWidth() <= 0) {
            System.out.println("Please create first a valid label!... return empty string!");
            return "";
        }
        // First of all, create title
        ZPLJavaText title = new ZPLJavaText();
        title.setId("title");
        title.setLabelSize(label.getLabelWidth(), label.getLabelHeight());
        title.setText(titleText);
        title.setMarginTop(50);
        title.setFontSize(ZPLJavaText.FontStyle.FONT_XLARGE);
        title.setAlignment(ZPLJavaComponent.POSITION_CENTER);

        ZPLJavaRectangle line = new ZPLJavaRectangle();
        line.setId("line below title");
        line.setLabelSize(label.getLabelWidth(), label.getLabelHeight());
        line.setWidth(140);
        line.setHeight(4);
        line.belowOf(title);
        line.setAlignment(ZPLJavaComponent.POSITION_CENTER);
        line.setMarginTop(24);
        line.fillBackground();

        ZPLJavaRectangle lineBack = new ZPLJavaRectangle();
        lineBack.setId("center dot padding");
        lineBack.setLabelSize(label.getLabelWidth(), label.getLabelHeight());
        lineBack.setWidth(36);
        lineBack.setHeight(8);
        lineBack.setMarginTop(24);
        lineBack.belowOf(title);
        lineBack.setAlignment(ZPLJavaComponent.POSITION_CENTER);
        lineBack.setColor(ZPLJavaShape.Color.COLOR_WHITE);
        lineBack.fillBackground();

        ZPLJavaEllipse centerEllipse = new ZPLJavaEllipse(label.getLabelWidth(),label.getLabelHeight());
        centerEllipse.setId("ellipse below title");
        centerEllipse.setWidth(18);
        centerEllipse.setHeight(18);
        centerEllipse.setMarginTop(17);
        centerEllipse.belowOf(title);
        centerEllipse.setAlignment(ZPLJavaComponent.POSITION_CENTER);
        centerEllipse.fillBackground();

        // create subtitle
        ZPLJavaText subtitle = new ZPLJavaText();
        subtitle.setId("subtitle");
        subtitle.setLabelSize(label.getLabelWidth(), label.getLabelHeight());
        subtitle.setText(subtitleText);
        subtitle.belowOf(line);
        subtitle.setFontSize(ZPLJavaText.FontStyle.FONT_NORMAL);
        subtitle.setMarginTop(40);
        subtitle.setAlignment(ZPLJavaComponent.POSITION_CENTER);

        // create info text
        ZPLJavaText info = new ZPLJavaText();
        info.setId("info");
        info.setLabelSize(label.getLabelWidth(), label.getLabelHeight());
        info.setText(infoText);
        info.belowOf(subtitle);
        info.setFontSize(ZPLJavaText.FontStyle.FONT_SMALL);
        info.setAlignment(ZPLJavaComponent.POSITION_CENTER);

        // Create barcode
        ZPLJavaBarcode barcode = new ZPLJavaBarcode(barcodeText);
        barcode.setId("barcode");
        barcode.setLabelSize(label.getLabelWidth(), label.getLabelHeight());
        barcode.setBarcodeWidth(2);
        barcode.setAlignment(ZPLJavaComponent.POSITION_BOTTOM);
        barcode.setAlignment(ZPLJavaComponent.POSITION_CENTER);
        barcode.setMarginBottom(20);
        barcode.applyBackground(ZPLJavaShape.Color.COLOR_WHITE,
                10, 0);

        // add border
        ZPLJavaRectangle borderBox = new ZPLJavaRectangle();
        borderBox.setId("Border");
        borderBox.setLabelSize(label.getLabelWidth(), label.getLabelHeight());
        borderBox.setWidth(label.getLabelWidth());
        borderBox.setHeight(label.getLabelHeight());
        borderBox.setThickness(3);
        // formula: { BarcodeMarginBottom + (barcodeHeight / 2) + (borderThickness / 2) }
        int marginCalc = 20 + (barcode.getComponentSize() / 2) + (int)(borderBox.getThickness() / 2);
        borderBox.setMargins(20, 20, 20, marginCalc);
        borderBox.setCornerRadius(1);

        label.addAllComponents(borderBox, title, line, lineBack, centerEllipse , subtitle,
                info, barcode);
        return label.getLabelCode();
    }

}
