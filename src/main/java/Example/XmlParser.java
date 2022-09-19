package Example;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.IOException;
import java.io.StringReader;

public class XmlParser {
    public static void main(String[] args) {
        String xml = "<HEADER>dddd</HEADER><ITEMS><ITEM><R>http://aaa.com</R></ITEM></ITEMS>";

        StringBuilder sb = new StringBuilder();
        sb.append("<xml>").append(xml).append("</xml>");

        try {
            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(new StringReader(sb.toString()));
            Element element = document.getRootElement();
            Element item = element.getChild("ITEMS").getChild("ITEM");
            System.out.println(item.getChildText("R"));

        } catch (JDOMException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
