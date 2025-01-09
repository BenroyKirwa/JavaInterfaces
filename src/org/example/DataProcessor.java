package org.example;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public interface DataProcessor{
    void process(String data);
}

class JsonProcessor implements DataProcessor{
    @Override
    public void process(String data) {
        try {
            // Remove the curly braces and split by commas
            String jsonContent = data.substring(1, data.length() - 1);
            String[] pairs = jsonContent.split(",");

            // Process each key-value pair
            for (String pair : pairs) {
                // Split by : and remove quotes
                String[] keyValue = pair.split(":");
                String key = keyValue[0].trim().replace("\"", "");
                String value = keyValue[1].trim().replace("\"", "");

                System.out.println("Key: " + key + ", Value: " + value);
            }
        } catch (Exception e) {
            System.err.println("Error processing JSON: " + e.getMessage());
        }
    }
}

class XMLProcessor implements DataProcessor{
    @Override
    public void process(String data) {
        try {
            // Create XML document
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            ByteArrayInputStream input = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
            Document doc = builder.parse(input);

            // Get root element
            Element root = doc.getDocumentElement();

            // Get all elements
            NodeList nodes = root.getElementsByTagName("*");

            // Print each element's name and value
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    String elementName = node.getNodeName();
                    String value = node.getTextContent();
                    System.out.println("Element: " + elementName + ", Value: " + value);
                }
            }
        } catch (Exception e) {
            System.err.println("Error processing XML: " + e.getMessage());
        }
    }
}

class Main {
    public static void main(String[] args) {
        // Test JSON processing
        String jsonData = "{\"name\":\"John\",\"age\":30,\"city\":\"New York\"}";
        DataProcessor jsonProcessor = new JsonProcessor();
        System.out.println("Processing JSON:");
        jsonProcessor.process(jsonData);

        System.out.println("\n-------------------\n");

        // Test XML processing
        String xmlData =
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                        "<person>" +
                        "    <name>John</name>" +
                        "    <age>30</age>" +
                        "    <city>New York</city>" +
                        "</person>";
        DataProcessor xmlProcessor = new XMLProcessor();
        xmlProcessor.process(xmlData);
    }
}