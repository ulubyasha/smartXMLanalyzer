# smartXMLanalyzer


*java -jar target/smart-XML-analyzer-1.0-SNAPSHOT-jar-with-dependencies.jar `sample-0-origin.html` `sample-1-evil-gemini.html`*
- html[0] > body[1] > div[0] > nav[0] > ul[1] > li[0] > ul[1] > li[6] > a[0]
- html[0] > body[1] > div[0] > div[1] > div[2] > div[0] > div[0] > div[1] > a[0]
- html[0] > body[1] > div[0] > div[1] > div[2] > div[0] > div[0] > div[1] > a[1]


*java -jar target/smart-XML-analyzer-1.0-SNAPSHOT-jar-with-dependencies.jar `sample-0-origin.html` `sample-2-container-and-clone.html`*
- html[0] > body[1] > div[0] > div[1] > div[2] > div[0] > div[0] > div[1] > div[0] > a[0]
- html[0] > body[1] > div[0] > div[1] > div[2] > div[1] > div[0] > div[1] > a[2]

*java -jar target/smart-XML-analyzer-1.0-SNAPSHOT-jar-with-dependencies.jar `sample-0-origin.html` `sample-3-the-escape.html`*
- html[0] > body[1] > div[0] > div[1] > div[2] > div[0] > div[0] > div[1] > a[0]
- html[0] > body[1] > div[0] > div[1] > div[2] > div[0] > div[0] > div[2] > a[0]


*java -jar target/smart-XML-analyzer-1.0-SNAPSHOT-jar-with-dependencies.jar `sample-0-origin.html` `sample-4-the-mash.html`*

- html[0] > body[1] > div[0] > div[1] > div[2] > div[0] > div[0] > div[1] > button[0]
- html[0] > body[1] > div[0] > div[1] > div[2] > div[0] > div[0] > div[2] > a[0]
