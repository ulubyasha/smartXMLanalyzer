import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

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
                .collect(toMap(Attribute::getKey, Attribute::getValue));
    }

    private static String getPath(Element element) {
        Elements parents = element.parents();
        Collections.reverse(parents);
        parents.add(element);
        return parents.stream()
                .map(el -> el.nodeName() + "[" + el.elementSiblingIndex() + "]")
                .collect(Collectors.joining(" > "));
    }

    private static Element getFirstElementFromDescOrder(Map<Element, Integer> matchedElements) {
        return matchedElements.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new))
                .entrySet().iterator().next().getKey();
    }

    private static void getSimilarElement(File originFile, File otherFile) {
        Document originDocument = getDocumentFromFile(originFile);
        Document otherDocument = getDocumentFromFile(otherFile);

        Element targetElement = getTargetElement(originDocument);
        Map<String, String> targetElementAttributes = getAttributes(targetElement);

        Map<Element, Integer> matchedElements = new HashMap<>();
        for (Element element : otherDocument.getAllElements()) {
            int count = 0;
            for (Attribute attribute : element.attributes()) {
                if (targetElementAttributes.containsValue(attribute.getValue())) {
                    matchedElements.put(element, ++count);
                }
            }
        }
        Element similarElement = getFirstElementFromDescOrder(matchedElements);
        System.out.println(getPath(similarElement));
    }

    public static void main(String[] args) {
        File originFile = new File(args[0]);
        File sampleFile = new File(args[1]);

        getSimilarElement(originFile, sampleFile);
    }
}
