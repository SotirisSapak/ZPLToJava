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
package com.libraries.sotirisapakos.zpljava.config;

/**
 * Based on: <a href="URL#value">https://support.zebra.com/cpws/docs/zpl/LL_Command.pdf</a>
 * <p>Can change the label length using ^LL command.</p>
 * <ul>
 *     <li>{@code For 6 dot/mm printheads...  Label length in inches x 152.4 (dots/inch) = y}</li>
 *     <li>{@code For 8 dot/mm printheads...  Label length in inches x 203.2 (dots/inch) = y}</li>
 *     <li>{@code For 12 dot/mm printheads... Label length in inches x 304.8 (dots/inch) = y}</li>
 *     <li>{@code For 24 dot/mm printheads... Label length in inches x 609.6 (dots/inch) = y}</li>
 * </ul>
 * Extract above values to static parameters...
 */
public interface LabelSize {

    int DEFAULT_WIDTH_INCHES = 4;
    int DEFAULT_HEIGHT_INCHES = 6;

    int _6DPMM  = 152;
    int _8DPMM  = 203;
    int _12DPMM = 305;
    int _24DPMM = 610;

}
