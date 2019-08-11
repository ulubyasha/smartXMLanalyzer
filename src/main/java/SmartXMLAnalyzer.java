import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class SmartXMLAnalyzer {

    private static final String CHARSET = "UTF-8";
    private static final String ELEMENT_ID = "make-everything-ok-button";

    private static Document getDocumentFromFile(File inputFile) {
        try {
            return Jsoup.parse(inputFile, CHARSET, inputFile.getAbsolutePath());
        } catch (IOException e) {
            throw new IllegalArgumentException("File " + inputFile.getName() + "is not valid.");
        }
    }

    private static Element getTargetElement(Document originDocument) {
        return originDocument.getElementById(SmartXMLAnalyzer.ELEMENT_ID);
    }

    private static Map<String, String> getAttributes(Element targetElement) {
        return targetElement.attributes()
                .asList()
                .stream()
                .collect(Collectors.toMap(Attribute::getKey, Attribute::getValue));
    }

    private static String getPath(Element element) {
        Elements parents = element.parents();
        Collections.reverse(parents);
        parents.add(element);
        return parents.stream()
                .map(el -> el.nodeName() + "[" + el.elementSiblingIndex() + "]")
                .collect(Collectors.joining(" > "));
    }

    private static void getSimilarElement(File originFile, File otherFile) {
        Document originDocument = getDocumentFromFile(originFile);
        Document otherDocument = getDocumentFromFile(otherFile);

        Element targetElement = getTargetElement(originDocument);
        Map<String, String> targetElementAttributes = getAttributes(targetElement);

        for (Element element : otherDocument.getAllElements()) {
            for (Attribute attribute : element.attributes()) {
                if (targetElementAttributes.containsValue(attribute.getValue())) {
                    System.out.println(getPath(element));
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        File originFile = new File(args[0]);
        File sampleFile = new File(args[1]);

        getSimilarElement(originFile, sampleFile);
    }
}
