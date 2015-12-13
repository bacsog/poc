voomer
==============

Proof of Concept webapp using Vaadin 7.5.

Workflow
========

To see in action:

 - git clone https://github.com/bacsog/poc.git

 - cd poc

 - mvn verify //Requires Java8. It runs for more than a full minute, but needs to be done only once per project

 - mvn jetty:run

 - open http://localhost:8080/

 - To experiment with the project, just change the code. Context will be refreshed & reloaded in around 5 sec in your browser.
 
Source
========

This project is using the basic Vaadin archetype:
https://vaadin.com/maven

Features
========

 - Hiearchical table with:
	- Context menu on right click
	- Add/remove feature
	- Collapsible columns
	- Resizable columns
	- Ordered columns
	- Reorderable columns
	
 - Grid with:
	- Ordering
	- Grand Total row
	- Text search filter
	- Number search filter
	- Progress bar renderer
	- Live push (1200 elements every 0.5 sec)
