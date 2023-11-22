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
package io.linlan.commons.script.xml;

import io.linlan.commons.core.CharsetUtils;
import io.linlan.commons.core.StringUtils;
import io.linlan.commons.core.io.FileUtils;
import io.linlan.commons.core.io.IoUtils;
import io.linlan.commons.script.ScriptException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * this is a utils of XML to provide many methods
 * Filename:XmlUtils.java
 * Desc:the XML utils in script operations
 * this utils include readXML, parseXML, toString, toFile etc. methods
 *
 * @author <a href="mailto:20400301@qq.com">linlan</a>
 * CreateTime:2017-07-10 10:25 PM
 *
 * @version 1.0
 * @since 1.0
 *
 */
public final class XmlUtils {

    /**
     * the invalid regex string in XML
     */
    public final static String INVALID_REGEX = "[\\x00-\\x08\\x0b-\\x0c\\x0e-\\x1f]";

    private XmlUtils() {

    }

    /**
     * read XML from file
     *
     * @param file File file
     * @return XML Document
     */
    public static Document readXML(File file) {
        if (file == null) {
            throw new NullPointerException("Xml file is null !");
        }
        if (false == file.exists()) {
            throw new ScriptException("File [{}] not a exist!", file.getAbsolutePath());
        }
        if (false == file.isFile()) {
            throw new ScriptException("[{}] not a file!", file.getAbsolutePath());
        }

        try {
            file = file.getCanonicalFile();
        } catch (IOException e) {
        }

        final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            final DocumentBuilder builder = dbf.newDocumentBuilder();
            return builder.parse(file);
        } catch (Exception e) {
            throw new ScriptException("Parse xml file [" + file.getAbsolutePath() + "] error!", e);
        }
    }

    /**
     * read XML from file in absolute path
     *
     * @param absolutePath XML file in absolute path
     * @return XML Document
     */
    public static Document readXML(String absolutePath) {
        return readXML(new File(absolutePath));
    }

    /**
     * parse String source to XML
     *
     * @param source string
     * @return XML Document
     */
    public static Document parseXml(String source) {
        if (StringUtils.isBlank(source)) {
            throw new ScriptException("Xml content string is empty !");
        }
        source = cleanInvalid(source);

        final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            final DocumentBuilder builder = dbf.newDocumentBuilder();
            return builder.parse(new InputSource(StringUtils.getReader(source)));
        } catch (Exception e) {
            throw new ScriptException("Parse xml file [" + source + "] error!", e);
        }
    }

    /**
     * trans XML Document to string<br>
     *
     * @param doc XML Document
     * @return String of XML
     */
    public static String toString(Document doc) {
        return toString(doc, CharsetUtils.UTF_8);
    }

    /**
     * trans XML Document to string<br>
     * this may change the Document's charset
     *
     * @param doc XML Document
     * @param charset the input charset name
     * @return String of XML
     */
    public static String toString(Document doc, String charset) {
        try {
            StringWriter writer = StringUtils.getWriter();

            final Transformer tf = TransformerFactory.newInstance().newTransformer();
            tf.setOutputProperty(OutputKeys.ENCODING, charset);
            tf.setOutputProperty(OutputKeys.INDENT, "yes");
            tf.transform(new DOMSource(doc), new StreamResult(writer));

            return writer.toString();
        } catch (Exception e) {
            throw new ScriptException("Trans xml document to string error!", e);
        }
    }

    /**
     * save XML Document to a file in absolute path<br>
     * use the Document's charset
     *
     * @param doc XML Document
     * @param absolutePath absolute path, if path or file is null, then file
     */
    public static void toFile(Document doc, String absolutePath) {
        toFile(doc, absolutePath, null);
    }

    /**
     * save XML Document to a file in absolute path<br>
     *
     * @param doc XML Document
     * @param absolutePath absolute path, if path or file is null, then file
     * @param charset the input charset name
     */
    public static void toFile(Document doc, String absolutePath, String charset) {
        if (StringUtils.isBlank(charset)) {
            charset = doc.getXmlEncoding();
        }
        if (StringUtils.isBlank(charset)) {
            charset = CharsetUtils.UTF_8;
        }

        BufferedWriter writer = null;
        try {
            writer = FileUtils.getWriter(absolutePath, Charset.forName(charset), false);
            Source source = new DOMSource(doc);
            final Transformer tf = TransformerFactory.newInstance().newTransformer();
            tf.setOutputProperty(OutputKeys.ENCODING, charset);
            tf.setOutputProperty(OutputKeys.INDENT, "yes");
            tf.transform(source, new StreamResult(writer));
        } catch (Exception e) {
            throw new ScriptException("Trans xml document to string error!", e);
        } finally {
            IoUtils.close(writer);
        }
    }

    /**
     * create XML Document<br>
     * the default charset is utf-8, in the method of toString or toFile, can define the charset
     *
     * @param rootElementName XML root element name
     * @return XML Document
     */
    public static Document createXml(String rootElementName) {
        final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = dbf.newDocumentBuilder();
        } catch (Exception e) {
            throw new ScriptException("Create xml document error!", e);
        }
        Document doc = builder.newDocument();
        doc.appendChild(doc.createElement(rootElementName));

        return doc;
    }

    /**
     * clean the invalid XML content, replace the invalid regex string
     *
     * @param xmlContent XML content
     * @return String ,if input is null, return null
     */
    public static String cleanInvalid(String xmlContent) {
        if (xmlContent == null) return null;
        return xmlContent.replaceAll(INVALID_REGEX, "");
    }

    /**
     * get the element list from element's tag name
     *
     * @param element element
     * @param tagName tag name
     * @return List<Element>
     */
    public static List<Element> getElements(Element element, String tagName) {
        final NodeList nodeList = element.getElementsByTagName(tagName);
        return transElements(element, nodeList);
    }

    /**
     * get the first element from element's tag name
     *
     * @param element element
     * @param tagName tag name
     * @return Element
     */
    public static Element getElement(Element element, String tagName) {
        final NodeList nodeList = element.getElementsByTagName(tagName);
        if (nodeList == null || nodeList.getLength() < 1) {
            return null;
        }
        int length = nodeList.getLength();
        for (int i = 0; i < length; i++) {
            Element childEle = (Element) nodeList.item(i);
            if (childEle == null || childEle.getParentNode() == element) {
                return childEle;
            }
        }
        return null;
    }

    /**
     * get the first element's text content from element's tag name
     *
     * @param element element
     * @param tagName tag name
     * @return String Element's text content
     */
    public static String elementText(Element element, String tagName) {
        Element child = getElement(element, tagName);
        return child == null ? null : child.getTextContent();
    }

    /**
     * get the first element's text content from element's tag name
     *
     * @param element element
     * @param tagName tag name
     * @param defaultValue if the Element's text content is null, then use default
     * @return String Element's text content
     */
    public static String elementText(Element element, String tagName, String defaultValue) {
        Element child = getElement(element, tagName);
        return child == null ? defaultValue : child.getTextContent();
    }

    /**
     * to trans NodeList to Element List
     *
     * @param nodeList NodeList
     * @return List<Element>
     */
    public static List<Element> transElements(NodeList nodeList) {
        return transElements(null, nodeList);
    }

    /**
     * to trans NodeList to Element List
     *
     * @param parent parent element, if not null, then return this's children, else return all
     * @param nodeList NodeList
     * @return List<Element>
     */
    public static List<Element> transElements(Element parent, NodeList nodeList) {
        final ArrayList<Element> elements = new ArrayList<Element>();
        int length = nodeList.getLength();
        for (int i = 0; i < length; i++) {
            Element element = (Element) nodeList.item(i);
            if (parent == null || element.getParentNode() == parent) {
                elements.add(element);
            }
        }

        return elements;
    }

    /**
     * write T to XML file, if the file exists, then override
     * Writes serializable object to a XML file. Existing file will be overwritten <br>
     * @param dest dest file
     * @param t source object
     * @param <T> serializable object
     * @throws IOException IO exception
     */
    public static <T> void writeAsXml(File dest, T t) throws IOException {
        XMLEncoder xml = null;
        try {
            xml = new XMLEncoder(FileUtils.getOutputStream(dest, false));
            xml.writeObject(t);
        } finally {
            //关闭XMLEncoder会相应关闭OutputStream
            IoUtils.close(xml);
        }
    }

    /**
     * read XML object from file source
     * Reads serialized object from the XML file.
     * @param source XML file
     * @return T Object
     * @throws IOException IO exception
     */
    public static <T> T readFromXml(File source) throws IOException {
        Object result = null;
        XMLDecoder xml = null;
        try {
            xml = new XMLDecoder(FileUtils.getInputStream(source));
            result = xml.readObject();
        } finally {
            IoUtils.close(xml);
        }
        return (T) result;
    }

}
