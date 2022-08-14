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

/**
 * Use this class to create custom ZPL components by passing only the ZPL command using
 * {@link #setInstruction(String)} method.
 */
public class ZPLJavaGenericObject {

    private String instruction;

    public String getInstruction()
    {
        return instruction;
    }
    public void setInstruction(String instruction)
    {
        this.instruction = instruction;
    }

}
