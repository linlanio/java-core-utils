/**
 * Copyright 2020-2023 the original author or Linlan authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.linlan.commons.core.io.image;

import java.awt.Color;
import java.io.File;

/**
 *  
 * Filename:IImageScale.java
 * Desc:
 *
 * CreateTime:2020-07-29 10:33 PM
 *
 * @version 1.0
 * @since 1.0
 *
 */
public interface IImageScale {

    /**
     * 缩小图片.
     *
     * @param srcFile
     *            原图片
     * @param destFile
     *            目标图片
     * @param boxWidth
     *            缩略图最大宽度
     * @param boxHeight
     *            缩略图最大高度
     * @throws Exception
     *             the exceptions
     */
    public void resizeFix(File srcFile, File destFile, int boxWidth,
            int boxHeight) throws Exception;

    /**
     * 缩小并裁剪图片.
     *
     * @param srcFile
     *            原文件
     * @param destFile
     *            目标文件
     * @param boxWidth
     *            缩略图最大宽度
     * @param boxHeight
     *            缩略图最大高度
     * @param cutTop
     *            裁剪TOP
     * @param cutLeft
     *            裁剪LEFT
     * @param cutWidth
     *            裁剪宽度
     * @param catHeight
     *            裁剪高度
     * @throws Exception
     *             the exceptions
     */
    public void resizeFix(File srcFile, File destFile, int boxWidth,
            int boxHeight, int cutTop, int cutLeft, int cutWidth, int catHeight)
            throws Exception;

    /**
     * Image mark.
     *
     * @param srcFile
     *            the src file
     * @param destFile
     *            the dest file
     * @param minWidth
     *            the min width
     * @param minHeight
     *            the min height
     * @param pos
     *            the pos
     * @param offsetX
     *            the offset x
     * @param offsetY
     *            the offset y
     * @param text
     *            the text
     * @param color
     *            the color
     * @param size
     *            the size
     * @param alpha
     *            the alpha
     * @throws Exception
     *             the exceptions
     */
    public void imageMark(File srcFile, File destFile, int minWidth,
            int minHeight, int pos, int offsetX, int offsetY, String text,
            Color color, int size, int alpha) throws Exception;

    /**
     * Image mark.
     *
     * @param srcFile
     *            the src file
     * @param destFile
     *            the dest file
     * @param minWidth
     *            the min width
     * @param minHeight
     *            the min height
     * @param pos
     *            the pos
     * @param offsetX
     *            the offset x
     * @param offsetY
     *            the offset y
     * @param markFile
     *            the mark file
     * @throws Exception
     *             the exceptions
     */
    public void imageMark(File srcFile, File destFile, int minWidth,
            int minHeight, int pos, int offsetX, int offsetY, File markFile)
            throws Exception;
}
