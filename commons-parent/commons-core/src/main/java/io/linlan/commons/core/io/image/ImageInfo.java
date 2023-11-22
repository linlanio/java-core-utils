/**
 * Copyright 2015 the original author or Linlan authors.
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

import java.io.DataInput;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Vector;

/**
 * Get file format, image resolution, number of bits per pixel and optionally
 * number of images, comments and physical resolution from 
 * JPEG, GIF, BMP, PCX, PNG, IFF, RAS, PBM, PGM, PPM and PSD files 
 * (or input streams).
 * @author <a href="mailto:20400301@qq.com">linlan</a>
 * Createtime 2017/7/12 10:13 PM
 *
 * @version 1.0
 * @since 1.0
 */
public class ImageInfo {
    /**
     * Return value of {@link #getFormat()} for JPEG streams.
     * ImageInfo can extract physical resolution and comments
     * from JPEGs (only from APP0 headers).
     * Only one image can be stored in a file.
     * It is determined whether the JPEG stream is progressive
     * (see {@link #isProgressive()}).
     */
    public static final int FORMAT_JPEG = 0;

    /**
     * Return value of {@link #getFormat()} for GIF streams.
     * ImageInfo can extract comments from GIFs and count the number
     * of images (GIFs with more than one image are animations).
     * It is determined whether the GIF stream is interlaced (see {@link #isProgressive()}).
     */
    public static final int FORMAT_GIF = 1;

    /**
     * Return value of {@link #getFormat()} for PNG streams.
     * PNG only supports one image per file.
     * Both physical resolution and comments can be stored with PNG,
     * but ImageInfo is currently not able to extract those.
     * It is determined whether the PNG stream is interlaced (see {@link #isProgressive()}).
     */
    public static final int FORMAT_PNG = 2;

    /**
     * Return value of {@link #getFormat()} for BMP streams.
     * BMP only supports one image per file.
     * BMP does not allow for comments.
     * The physical resolution can be stored.
     */
    public static final int FORMAT_BMP = 3;

    /**
     * Return value of {@link #getFormat()} for PCX streams.
     * PCX does not allow for comments or more than one image per file.
     * However, the physical resolution can be stored.
     */
    public static final int FORMAT_PCX = 4;

    /**
     * Return value of {@link #getFormat()} for IFF streams.
     */
    public static final int FORMAT_IFF = 5;

    /**
     * Return value of {@link #getFormat()} for RAS streams.
     * Sun Raster allows for one image per file only and is not able to
     * store physical resolution or comments.
     */
    public static final int FORMAT_RAS = 6;

    /** Return value of {@link #getFormat()} for PBM streams. */
    public static final int FORMAT_PBM = 7;

    /** Return value of {@link #getFormat()} for PGM streams. */
    public static final int FORMAT_PGM = 8;

    /** Return value of {@link #getFormat()} for PPM streams. */
    public static final int FORMAT_PPM = 9;

    /** Return value of {@link #getFormat()} for PSD streams. */
    public static final int FORMAT_PSD = 10;

/*    public static final int COLOR_TYPE_UNKNOWN = -1;
    public static final int COLOR_TYPE_TRUECOLOR_RGB = 0;
    public static final int COLOR_TYPE_PALETTED = 1;
    public static final int COLOR_TYPE_GRAYSCALE= 2;
    public static final int COLOR_TYPE_BLACK_AND_WHITE = 3;*/

    /**
     * The names of all supported file formats.
     * The FORMAT_xyz int constants can be used as index values for
     * this array.
     */
    private static final String[] FORMAT_NAMES =
        {"JPEG", "GIF", "PNG", "BMP", "PCX",
         "IFF", "RAS", "PBM", "PGM", "PPM",
         "PSD"};

    /**
     * The names of the MIME types for all supported file formats.
     * The FORMAT_xyz int constants can be used as index values for
     * this array.
     */
    private static final String[] MIME_TYPE_STRINGS =
        {"image/jpeg", "image/gif", "image/png", "image/bmp", "image/pcx",
         "image/iff", "image/ras", "image/x-portable-bitmap", "image/x-portable-graymap", "image/x-portable-pixmap",
         "image/psd"};

    /** The width. */
    private int width;

    /** The height. */
    private int height;

    /** The bits per pixel. */
    private int bitsPerPixel;
    //private int colorType = COLOR_TYPE_UNKNOWN;
    /** The progressive. */
    private boolean progressive;

    /** The format. */
    private int format;

    /** The in. */
    private InputStream in;

    /** The din. */
    private DataInput din;

    /** The collect comments. */
    private boolean collectComments = true;

    /** The comments. */
    @SuppressWarnings("rawtypes")
    private Vector comments;

    /** The determine number of images. */
    private boolean determineNumberOfImages;

    /** The number of images. */
    private int numberOfImages;

    /** The physical height dpi. */
    private int physicalHeightDpi;

    /** The physical width dpi. */
    private int physicalWidthDpi;

    /**
     * Adds the comment.
     *
     * @param s
     *            the s
     */
    @SuppressWarnings("rawtypes")
    private void addComment(String s) {
        if (comments == null) {
            comments = new Vector();
        }
        comments.addElement(s);
    }

    /**
     * Call this method after you have provided an input stream or file
     * using {@link #setInput(InputStream)} or {@link #setInput(DataInput)}.
     * If true is returned, the file format was known and information
     * on the file's content can be retrieved using the various getXyz methods.
     * @return if information could be retrieved from input
     */
    public boolean check() {
        format = -1;
        width = -1;
        height = -1;
        bitsPerPixel = -1;
        numberOfImages = 1;
        physicalHeightDpi = -1;
        physicalWidthDpi = -1;
        comments = null;
        try {
            int b1 = read() & 0xff;
            int b2 = read() & 0xff;
            if (b1 == 0x47 && b2 == 0x49) {
                return checkGif();
            }
            else
            if (b1 == 0x89 && b2 == 0x50) {
                return checkPng();
            }
            else
            if (b1 == 0xff && b2 == 0xd8) {
                return checkJpeg();
            }
            else
            if (b1 == 0x42 && b2 == 0x4d) {
                return checkBmp();
            }
            else
            if (b1 == 0x0a && b2 < 0x06) {
                return checkPcx();
            }
            else
            if (b1 == 0x46 && b2 == 0x4f) {
                return checkIff();
            }
            else
            if (b1 == 0x59 && b2 == 0xa6) {
                return checkRas();
            }
            else
            if (b1 == 0x50 && b2 >= 0x31 && b2 <= 0x36) {
                return checkPnm(b2 - '0');
            }
            else
            if (b1 == 0x38 && b2 == 0x42) {
                return checkPsd();
            }
            else {
                return false;
            }
        } catch (IOException ioe) {
            return false;
        }
    }

    /**
     * Check bmp.
     *
     * @return true, if successful
     * @throws IOException
     *             Signals that an I/O exceptions has occurred.
     */
    private boolean checkBmp() throws IOException {
        byte[] a = new byte[44];
        if (read(a) != a.length) {
            return false;
        }
        width = getIntLittleEndian(a, 16);
        height = getIntLittleEndian(a, 20);
        if (width < 1 || height < 1) {
            return false;
        }
        bitsPerPixel = getShortLittleEndian(a, 26);
        if (bitsPerPixel != 1 && bitsPerPixel != 4 &&
            bitsPerPixel != 8 && bitsPerPixel != 16 &&
            bitsPerPixel != 24 && bitsPerPixel != 32) {
            return false;
        }
        int x = (int)(getIntLittleEndian(a, 36) * 0.0254);
        if (x > 0) {
            setPhysicalWidthDpi(x);
        }
        int y = (int)(getIntLittleEndian(a, 40) * 0.0254);
        if (y > 0) {
            setPhysicalHeightDpi(y);
        }
        format = FORMAT_BMP;
        return true;
    }

    /**
     * Check gif.
     *
     * @return true, if successful
     * @throws IOException
     *             Signals that an I/O exceptions has occurred.
     */
    private boolean checkGif() throws IOException {
        final byte[] GIF_MAGIC_87A = {0x46, 0x38, 0x37, 0x61};
        final byte[] GIF_MAGIC_89A = {0x46, 0x38, 0x39, 0x61};
        byte[] a = new byte[11]; // 4 from the GIF signature + 7 from the global header
        if (read(a) != 11) {
            return false;
        }
        if ((!equals(a, 0, GIF_MAGIC_89A, 0, 4)) &&
            (!equals(a, 0, GIF_MAGIC_87A, 0, 4))) {
            return false;
        }
        format = FORMAT_GIF;
        width = getShortLittleEndian(a, 4);
        height = getShortLittleEndian(a, 6);
        int flags = a[8] & 0xff;
        bitsPerPixel = ((flags >> 4) & 0x07) + 1;
        //progressive = (flags & 0x02) != 0;
        if (!determineNumberOfImages) {
            return true;
        }
        // skip global color palette
        if ((flags & 0x80) != 0) {
            int tableSize = (1 << ((flags & 7) + 1)) * 3;
            skip(tableSize);
        }
        numberOfImages = 0;
        int blockType;
        do
        {
            blockType = read();
            switch(blockType)
            {
                case(0x2c): // image separator
                {
                    if (read(a, 0, 9) != 9) {
                        return false;
                    }
                    flags = a[8] & 0xff;
                    progressive = (flags & 0x40) != 0;
                    /*int locWidth = getShortLittleEndian(a, 4);
                    int locHeight = getShortLittleEndian(a, 6);
                    System.out.println("LOCAL: " + locWidth + " x " + locHeight);*/
                    int localBitsPerPixel = (flags & 0x07) + 1;
                    if (localBitsPerPixel > bitsPerPixel) {
                        bitsPerPixel = localBitsPerPixel;
                    }
                    if ((flags & 0x80) != 0) {
                        skip((1 << localBitsPerPixel) * 3);
                    }
                    skip(1); // initial code length
                    int n;
                    do
                    {
                        n = read();
                        if (n > 0) {
                            skip(n);
                        }
                        else
                        if (n == -1) {
                            return false;
                        }
                    }
                    while (n > 0);
                    numberOfImages++;
                    break;
                }
                case(0x21): // extension
                {
                    int extensionType = read();
                    if (collectComments && extensionType == 0xfe) {
                        StringBuffer sb = new StringBuffer();
                        int n;
                        do
                        {
                            n = read();
                            if (n == -1) {
                                return false;
                            }
                            if (n > 0) {
                                for (int i = 0; i < n; i++) {
                                    int ch = read();
                                    if (ch == -1) {
                                        return false;
                                    }
                                    sb.append((char)ch);
                                }
                            }
                        }
                        while (n > 0);
                    } else {
                        int n;
                        do
                        {
                            n = read();
                            if (n > 0) {
                                skip(n);
                            }
                            else
                            if (n == -1) {
                                return false;
                            }
                        }
                        while (n > 0);
                    }
                    break;
                }
                case(0x3b): // end of file
                {
                    break;
                }
                default:
                {
                    return false;
                }
            }
        }
        while (blockType != 0x3b);
        return true;
    }

    /**
     * Check iff.
     *
     * @return true, if successful
     * @throws IOException
     *             Signals that an I/O exceptions has occurred.
     */
    private boolean checkIff() throws IOException {
        byte[] a = new byte[10];
        // read remaining 2 bytes of file id, 4 bytes file size
        // and 4 bytes IFF subformat
        if (read(a, 0, 10) != 10) {
            return false;
        }
        final byte[] IFF_RM = {0x52, 0x4d};
        if (!equals(a, 0, IFF_RM, 0, 2)) {
            return false;
        }
        int type = getIntBigEndian(a, 6);
        if (type != 0x494c424d && // type must be ILBM...
            type != 0x50424d20) { // ...or PBM
            return false;
        }
        // loop chunks to find BMHD chunk
        do {
            if (read(a, 0, 8) != 8) {
                return false;
            }
            int chunkId = getIntBigEndian(a, 0);
            int size = getIntBigEndian(a, 4);
            if ((size & 1) == 1) {
                size++;
            }
            if (chunkId == 0x424d4844) { // BMHD chunk
                if (read(a, 0, 9) != 9) {
                    return false;
                }
                format = FORMAT_IFF;
                width = getShortBigEndian(a, 0);
                height = getShortBigEndian(a, 2);
                bitsPerPixel = a[8] & 0xff;
                return (width > 0 && height > 0 && bitsPerPixel > 0 && bitsPerPixel < 33);
            } else {
                skip(size);
            }
        } while (true);
    }

    /**
     * Check jpeg.
     *
     * @return true, if successful
     * @throws IOException
     *             Signals that an I/O exceptions has occurred.
     */
    private boolean checkJpeg() throws IOException {
        byte[] data = new byte[12];
        while (true) {
            if (read(data, 0, 4) != 4) {
                return false;
            }
            int marker = getShortBigEndian(data, 0);
            int size = getShortBigEndian(data, 2);
            if ((marker & 0xff00) != 0xff00) {
                return false; // not a valid marker
            }
            if (marker == 0xffe0) { // APPx
                if (size < 14) {
                    // not an APPx header as we know it, skip
                    skip(size - 2);
                    continue;
                }
                if (read(data, 0, 12) != 12) {
                    return false;
                }
                final byte[] APP0_ID = {0x4a, 0x46, 0x49, 0x46, 0x00};
                if (equals(APP0_ID, 0, data, 0, 5)) {
                    //System.out.println("data 7=" + data[7]);
                    if (data[7] == 1) {
                        setPhysicalWidthDpi(getShortBigEndian(data, 8));
                        setPhysicalHeightDpi(getShortBigEndian(data, 10));
                    }
                    else
                    if (data[7] == 2) {
                        int x = getShortBigEndian(data, 8);
                        int y = getShortBigEndian(data, 10);
                        setPhysicalWidthDpi((int)(x * 2.54f));
                        setPhysicalHeightDpi((int)(y * 2.54f));
                    }
                }
                skip(size - 14);
            }
            else
            if (collectComments && size > 2 && marker == 0xfffe) { // comment
                size -= 2;
                byte[] chars = new byte[size];
                if (read(chars, 0, size) != size) {
                    return false;
                }
                String comment = new String(chars, "iso-8859-1");
                comment = comment.trim();
                addComment(comment);
            }
            else
            if (marker >= 0xffc0 && marker <= 0xffcf && marker != 0xffc4 && marker != 0xffc8) {
                if (read(data, 0, 6) != 6) {
                    return false;
                }
                format = FORMAT_JPEG;
                bitsPerPixel = (data[0] & 0xff) * (data[5] & 0xff);
                progressive = marker == 0xffc2 || marker == 0xffc6 ||
                    marker == 0xffca || marker == 0xffce;
                width = getShortBigEndian(data, 3);
                height = getShortBigEndian(data, 1);
                return true;
            } else {
                skip(size - 2);
            }
        }
    }

    /**
     * Check pcx.
     *
     * @return true, if successful
     * @throws IOException
     *             Signals that an I/O exceptions has occurred.
     */
    private boolean checkPcx() throws IOException {
        byte[] a = new byte[64];
        if (read(a) != a.length) {
            return false;
        }
        if (a[0] != 1) { // encoding, 1=RLE is only valid value
            return false;
        }
        // width / height
        int x1 = getShortLittleEndian(a, 2);
        int y1 = getShortLittleEndian(a, 4);
        int x2 = getShortLittleEndian(a, 6);
        int y2 = getShortLittleEndian(a, 8);
        if (x1 < 0 || x2 < x1 || y1 < 0 || y2 < y1) {
            return false;
        }
        width = x2 - x1 + 1;
        height = y2 - y1 + 1;
        // color depth
        int bits = a[1];
        int planes = a[63];
        if (planes == 1 &&
            (bits == 1 || bits == 2 || bits == 4 || bits == 8)) {
            // paletted
            bitsPerPixel = bits;
        } else
        if (planes == 3 && bits == 8) {
            // RGB truecolor
            bitsPerPixel = 24;
        } else {
            return false;
        }
        setPhysicalWidthDpi(getShortLittleEndian(a, 10));
        setPhysicalHeightDpi(getShortLittleEndian(a, 10));
        format = FORMAT_PCX;
        return true;
    }

    /**
     * Check png.
     *
     * @return true, if successful
     * @throws IOException
     *             Signals that an I/O exceptions has occurred.
     */
    private boolean checkPng() throws IOException {
        final byte[] PNG_MAGIC = {0x4e, 0x47, 0x0d, 0x0a, 0x1a, 0x0a};
        byte[] a = new byte[27];
        if (read(a) != 27) {
            return false;
        }
        if (!equals(a, 0, PNG_MAGIC, 0, 6)) {
            return false;
        }
        format = FORMAT_PNG;
        width = getIntBigEndian(a, 14);
        height = getIntBigEndian(a, 18);
        bitsPerPixel = a[22] & 0xff;
        int colorType = a[23] & 0xff;
        if (colorType == 2 || colorType == 6) {
            bitsPerPixel *= 3;
        }
        progressive = (a[26] & 0xff) != 0;
        return true;
    }

    /**
     * Check pnm.
     *
     * @param id
     *            the id
     * @return true, if successful
     * @throws IOException
     *             Signals that an I/O exceptions has occurred.
     */
    private boolean checkPnm(int id) throws IOException {
        if (id < 1 || id > 6) {
            return false;
        }
        final int[] PNM_FORMATS = {FORMAT_PBM, FORMAT_PGM, FORMAT_PPM};
        format = PNM_FORMATS[(id - 1) % 3];
        boolean hasPixelResolution = false;
        String s;
        while (true)
        {
            s = readLine();
            if (s != null) {
                s = s.trim();
            }
            if (s == null || s.length() < 1) {
                continue;
            }
            if (s.charAt(0) == '#') { // comment
                if (collectComments && s.length() > 1) {
                    addComment(s.substring(1));
                }
                continue;
            }
            if (!hasPixelResolution) { // split "343 966" into width=343, height=966
                int spaceIndex = s.indexOf(' ');
                if (spaceIndex == -1) {
                    return false;
                }
                String widthString = s.substring(0, spaceIndex);
                spaceIndex = s.lastIndexOf(' ');
                if (spaceIndex == -1) {
                    return false;
                }
                String heightString = s.substring(spaceIndex + 1);
                try {
                    width = Integer.parseInt(widthString);
                    height = Integer.parseInt(heightString);
                } catch (NumberFormatException nfe) {
                    return false;
                }
                if (width < 1 || height < 1) {
                    return false;
                }
                if (format == FORMAT_PBM) {
                    bitsPerPixel = 1;
                    return true;
                }
                hasPixelResolution = true;
            }
            else
            {
                int maxSample;
                try {
                    maxSample = Integer.parseInt(s);
                } catch (NumberFormatException nfe) {
                    return false;
                }
                if (maxSample < 0) {
                    return false;
                }
                for (int i = 0; i < 25; i++) {
                    if (maxSample < (1 << (i + 1))) {
                        bitsPerPixel = i + 1;
                        if (format == FORMAT_PPM) {
                            bitsPerPixel *= 3;
                        }
                        return true;
                    }
                }
                return false;
            }
        }
    }

    /**
     * Check psd.
     *
     * @return true, if successful
     * @throws IOException
     *             Signals that an I/O exceptions has occurred.
     */
    private boolean checkPsd() throws IOException {
        byte[] a = new byte[24];
        if (read(a) != a.length) {
            return false;
        }
        final byte[] PSD_MAGIC = {0x50, 0x53};
        if (!equals(a, 0, PSD_MAGIC, 0, 2)) {
            return false;
        }
        format = FORMAT_PSD;
        width = getIntBigEndian(a, 16);
        height = getIntBigEndian(a, 12);
        int channels = getShortBigEndian(a, 10);
        int depth = getShortBigEndian(a, 20);
        bitsPerPixel = channels * depth;
        return (width > 0 && height > 0 && bitsPerPixel > 0 && bitsPerPixel <= 64);
    }

    /**
     * Check ras.
     *
     * @return true, if successful
     * @throws IOException
     *             Signals that an I/O exceptions has occurred.
     */
    private boolean checkRas() throws IOException {
        byte[] a = new byte[14];
        if (read(a) != a.length) {
            return false;
        }
        final byte[] RAS_MAGIC = {0x6a, (byte)0x95};
        if (!equals(a, 0, RAS_MAGIC, 0, 2)) {
            return false;
        }
        format = FORMAT_RAS;
        width = getIntBigEndian(a, 2);
        height = getIntBigEndian(a, 6);
        bitsPerPixel = getIntBigEndian(a, 10);
        return (width > 0 && height > 0 && bitsPerPixel > 0 && bitsPerPixel <= 24);
    }

    /**
     * Run over String list, return false iff at least one of the arguments
     * equals <code>-c</code>.
     *
     * @param args
     *            string list to check
     * @return true, if successful
     */
    private static boolean determineVerbosity(String[] args) {
        if (args != null && args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                if ("-c".equals(args[i])) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Equals.
     *
     * @param a1
     *            the a1
     * @param offs1
     *            the offs1
     * @param a2
     *            the a2
     * @param offs2
     *            the offs2
     * @param num
     *            the num
     * @return true, if successful
     */
    private static boolean equals(byte[] a1, int offs1, byte[] a2, int offs2, int num) {
        while (num-- > 0) {
            if (a1[offs1++] != a2[offs2++]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Gets the，获取方法 the bits per pixel.
     *
     * @return the bits per pixel
     */
    public int getBitsPerPixel() {
        return bitsPerPixel;
    }

    /**
     * Returns the index'th comment retrieved from the file.
     *
     * @param index
     *            int index of comment to return
     * @return the comment
     * @see #getNumberOfComments
     */
    public String getComment(int index) {
        if (comments == null || index < 0 || index >= comments.size()) {
            throw new IllegalArgumentException("Not a valid comment index: " + index);
        }
        return (String)comments.elementAt(index);
    }

    /**
     * Gets the，获取方法 the format.
     *
     * @return the format
     */
    public int getFormat() {
        return format;
    }

    /**
     * Gets the format name.
     *
     * @return the format name
     */
    public String getFormatName() {
        if (format >= 0 && format < FORMAT_NAMES.length) {
            return FORMAT_NAMES[format];
        } else {
            return "?";
        }
    }

    /**
     * Gets the，获取方法 the height.
     *
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Gets the int big endian.
     *
     * @param a
     *            the a
     * @param offs
     *            the offs
     * @return the int big endian
     */
    private static int getIntBigEndian(byte[] a, int offs) {
        return
            (a[offs] & 0xff) << 24 |
            (a[offs + 1] & 0xff) << 16 |
            (a[offs + 2] & 0xff) << 8 |
            a[offs + 3] & 0xff;
    }

    /**
     * Gets the int little endian.
     *
     * @param a
     *            the a
     * @param offs
     *            the offs
     * @return the int little endian
     */
    private static int getIntLittleEndian(byte[] a, int offs) {
        return
            (a[offs + 3] & 0xff) << 24 |
            (a[offs + 2] & 0xff) << 16 |
            (a[offs + 1] & 0xff) << 8 |
            a[offs] & 0xff;
    }

    /**
     * Gets the mime type.
     *
     * @return the mime type
     */
    public String getMimeType() {
        if (format >= 0 && format < MIME_TYPE_STRINGS.length) {
            if (format == FORMAT_JPEG && progressive)
            {
                return "image/pjpeg";
            }
            return MIME_TYPE_STRINGS[format];
        } else {
            return null;
        }
    }

    /**
     * Gets the number of comments.
     *
     * @return the number of comments
     */
    public int getNumberOfComments()
    {
        if (comments == null) {
            return 0;
        } else {
            return comments.size();
        }
    }

    /**
     * Gets the，获取方法 the number of images.
     *
     * @return the number of images
     */
    public int getNumberOfImages()
    {
        return numberOfImages;
    }

    /**
     * Gets the，获取方法 the physical height dpi.
     *
     * @return the physical height dpi
     */
    public int getPhysicalHeightDpi() {
        return physicalHeightDpi;
    }

    /**
     * Gets the physical height inch.
     *
     * @return the physical height inch
     */
    public float getPhysicalHeightInch() {
        int h = getHeight();
        int ph = getPhysicalHeightDpi();
        if (h > 0 && ph > 0) {
            return ((float)h) / ((float)ph);
        } else {
            return -1.0f;
        }
    }

    /**
     * Gets the，获取方法 the physical width dpi.
     *
     * @return the physical width dpi
     */
    public int getPhysicalWidthDpi() {
        return physicalWidthDpi;
    }

    /**
     * Gets the physical width inch.
     *
     * @return the physical width inch
     */
    public float getPhysicalWidthInch() {
        int w = getWidth();
        int pw = getPhysicalWidthDpi();
        if (w > 0 && pw > 0) {
            return ((float)w) / ((float)pw);
        } else {
            return -1.0f;
        }
    }

    /**
     * Gets the short big endian.
     *
     * @param a
     *            the a
     * @param offs
     *            the offs
     * @return the short big endian
     */
    private static int getShortBigEndian(byte[] a, int offs) {
        return
            (a[offs] & 0xff) << 8 |
            (a[offs + 1] & 0xff);
    }

    /**
     * Gets the short little endian.
     *
     * @param a
     *            the a
     * @param offs
     *            the offs
     * @return the short little endian
     */
    private static int getShortLittleEndian(byte[] a, int offs) {
        return (a[offs] & 0xff) | (a[offs + 1] & 0xff) << 8;
    }

    /**
     * Gets the，获取方法 the width.
     *
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Checks if is the progressive.
     *
     * @return the progressive
     */
    public boolean isProgressive()
    {
        return progressive;
    }

    /**
     * To use this class as a command line application, give it either
     * some file names as parameters (information on them will be
     * printed to standard output, one line per file) or call
     * it with no parameters. It will then check data given to it
     * via standard input.
     * @param args the program arguments which must be file names
     */
    public static void main(String[] args) {
        ImageInfo imageInfo = new ImageInfo();
        imageInfo.setDetermineImageNumber(true);
        boolean verbose = determineVerbosity(args);
        if (args.length == 0) {
            run(null, System.in, imageInfo, verbose);
        } else {
            int index = 0;
            while (index < args.length) {
                InputStream in = null;
                try {
                    String name = args[index++];
                    System.out.print(name + ";");
                    if (name.startsWith("http://")) {
                        in = new URL(name).openConnection().getInputStream();
                    } else {
                        in = new FileInputStream(name);
                    }
                    run(name, in, imageInfo, verbose);
                    in.close();
                } catch (IOException e) {
                    System.out.println(e);
                    try {
                        if (in != null) {
                            in.close();
                        }
                    } catch (IOException ee) {
                    }
                }
            }
        }
    }

    /**
     * Prints the.
     *
     * @param sourceName
     *            the source name
     * @param ii
     *            the ii
     * @param verbose
     *            the verbose
     */
    private static void print(String sourceName, ImageInfo ii, boolean verbose) {
        if (verbose) {
            printVerbose(sourceName, ii);
        } else {
            printCompact(sourceName, ii);
        }
    }

    /**
     * Prints the compact.
     *
     * @param sourceName
     *            the source name
     * @param imageInfo
     *            the image info
     */
    private static void printCompact(String sourceName, ImageInfo imageInfo) {
        final String SEP = "\t";
        System.out.println(
            sourceName + SEP +
            imageInfo.getFormatName() + SEP +
            imageInfo.getMimeType() + SEP +
            imageInfo.getWidth() + SEP +
            imageInfo.getHeight() + SEP +
            imageInfo.getBitsPerPixel() + SEP +
            imageInfo.getNumberOfImages() + SEP +
            imageInfo.getPhysicalWidthDpi() + SEP +
            imageInfo.getPhysicalHeightDpi() + SEP +
            imageInfo.getPhysicalWidthInch() + SEP +
            imageInfo.getPhysicalHeightInch() + SEP +
            imageInfo.isProgressive()
        );
    }

    /**
     * Prints the line.
     *
     * @param indentLevels
     *            the indent levels
     * @param text
     *            the text
     * @param value
     *            the value
     * @param minValidValue
     *            the min valid value
     */
    private static void printLine(int indentLevels, String text, float value, float minValidValue) {
        if (value < minValidValue) {
            return;
        }
        printLine(indentLevels, text, Float.toString(value));
    }

    /**
     * Prints the line.
     *
     * @param indentLevels
     *            the indent levels
     * @param text
     *            the text
     * @param value
     *            the value
     * @param minValidValue
     *            the min valid value
     */
    private static void printLine(int indentLevels, String text, int value, int minValidValue) {
        if (value >= minValidValue) {
            printLine(indentLevels, text, Integer.toString(value));
        }
    }

    /**
     * Prints the line.
     *
     * @param indentLevels
     *            the indent levels
     * @param text
     *            the text
     * @param value
     *            the value
     */
    private static void printLine(int indentLevels, String text, String value) {
        if (value == null || value.length() == 0) {
            return;
        }
        while (indentLevels-- > 0) {
            System.out.print("\t");
        }
        if (text != null && text.length() > 0) {
            System.out.print(text);
            System.out.print(" ");
        }
        System.out.println(value);
    }

    /**
     * Prints the verbose.
     *
     * @param sourceName
     *            the source name
     * @param ii
     *            the ii
     */
    private static void printVerbose(String sourceName, ImageInfo ii) {
        printLine(0, null, sourceName);
        printLine(1, "File format: ", ii.getFormatName());
        printLine(1, "MIME type: ", ii.getMimeType());
        printLine(1, "Width (pixels): ", ii.getWidth(), 1);
        printLine(1, "Height (pixels): ", ii.getHeight(), 1);
        printLine(1, "Bits per pixel: ", ii.getBitsPerPixel(), 1);
        printLine(1, "Progressive: ", ii.isProgressive() ? "yes" : "no");
        printLine(1, "Number of images: ", ii.getNumberOfImages(), 1);
        printLine(1, "Physical width (dpi): ", ii.getPhysicalWidthDpi(), 1);
        printLine(1, "Physical height (dpi): ", ii.getPhysicalHeightDpi(), 1);
        printLine(1, "Physical width (inches): ", ii.getPhysicalWidthInch(), 1.0f);
        printLine(1, "Physical height (inches): ", ii.getPhysicalHeightInch(), 1.0f);
        int numComments = ii.getNumberOfComments();
        printLine(1, "Number of textual comments: ", numComments, 1);
        if (numComments > 0) {
            for (int i = 0; i < numComments; i++) {
                printLine(2, null, ii.getComment(i));
            }
        }
    }

    /**
     * Read.
     *
     * @return the int
     * @throws IOException
     *             Signals that an I/O exceptions has occurred.
     */
    private int read() throws IOException {
        if (in != null) {
            return in.read();
        } else {
            return din.readByte();
        }
    }

    /**
     * Read.
     *
     * @param a
     *            the a
     * @return the int
     * @throws IOException
     *             Signals that an I/O exceptions has occurred.
     */
    private int read(byte[] a) throws IOException {
        if (in != null) {
            return in.read(a);
        } else {
            din.readFully(a);
            return a.length;
        }
    }

    /**
     * Read.
     *
     * @param a
     *            the a
     * @param offset
     *            the offset
     * @param num
     *            the num
     * @return the int
     * @throws IOException
     *             Signals that an I/O exceptions has occurred.
     */
    private int read(byte[] a, int offset, int num) throws IOException {
        if (in != null) {
            return in.read(a, offset, num);
        } else {
            din.readFully(a, offset, num);
            return num;
        }
    }

    /**
     * Read line.
     *
     * @return the string
     * @throws IOException
     *             Signals that an I/O exceptions has occurred.
     */
    private String readLine() throws IOException {
        return readLine(new StringBuffer());
    }

    /**
     * Read line.
     *
     * @param sb
     *            the sb
     * @return the string
     * @throws IOException
     *             Signals that an I/O exceptions has occurred.
     */
    private String readLine(StringBuffer sb) throws IOException {
        boolean finished;
        do {
            int value = read();
            finished = (value == -1 || value == 10);
            if (!finished) {
                sb.append((char)value);
            }
        } while (!finished);
        return sb.toString();
    }

    /**
     * Run.
     *
     * @param sourceName
     *            the source name
     * @param in
     *            the in
     * @param imageInfo
     *            the image info
     * @param verbose
     *            the verbose
     */
    private static void run(String sourceName, InputStream in, ImageInfo imageInfo, boolean verbose) {
        imageInfo.setInput(in);
        imageInfo.setDetermineImageNumber(true);
        imageInfo.setCollectComments(verbose);
        if (imageInfo.check()) {
            print(sourceName, imageInfo, verbose);
        }
    }

    /**
     * Sets the，设置方法 the collect comments.
     *
     * @param newValue
     *            the new collect comments
     */
    public void setCollectComments(boolean newValue)
    {
        collectComments = newValue;
    }

    /**
     * Sets the determine image number.
     *
     * @param newValue
     *            the new determine image number
     */
    public void setDetermineImageNumber(boolean newValue)
    {
        determineNumberOfImages = newValue;
    }

    /**
     * Sets the input.
     *
     * @param dataInput
     *            the new input
     */
    public void setInput(DataInput dataInput) {
        din = dataInput;
        in = null;
    }

    /**
     * Sets the input.
     *
     * @param inputStream
     *            the new input
     */
    public void setInput(InputStream inputStream) {
        in = inputStream;
        din = null;
    }

    /**
     * Sets the，设置方法 the physical height dpi.
     *
     * @param newValue
     *            the new physical height dpi
     */
    private void setPhysicalHeightDpi(int newValue) {
        physicalWidthDpi = newValue;
    }

    /**
     * Sets the，设置方法 the physical width dpi.
     *
     * @param newValue
     *            the new physical width dpi
     */
    private void setPhysicalWidthDpi(int newValue) {
        physicalHeightDpi = newValue;
    }

    /**
     * Skip.
     *
     * @param num
     *            the num
     * @throws IOException
     *             Signals that an I/O exceptions has occurred.
     */
    private void skip(int num) throws IOException {
        while (num > 0) {
            long result;
            if (in != null) {
                result = in.skip(num);
            } else {
                result = din.skipBytes(num);
            }
            if (result > 0) {
                num -= result;
            } else {
                if (in != null) {
                    result = in.read();
                } else {
                    result = din.readByte();
                }
                if (result == -1) {
                    throw new IOException("Premature end of input.");
                } else {
                    num--;
                }
            }
        }
    }
}
