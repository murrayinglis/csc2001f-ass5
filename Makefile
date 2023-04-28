JAVAC=/usr/bin/javac
JAVA=/usr/bin/java
.SUFFIXES: .java .class

SRCDIR=src/main
BINDIR=bin

$(BINDIR)/main/%.class: $(SRCDIR)/%.java 
	$(JAVAC) -d $(BINDIR) -cp $(BINDIR) -sourcepath $(SRCDIR) $<

CLASSES=Instrumentation.class GraphException.class Graph.class RandomGraphGenerator.class GraphExperiment.class
CLASS_FILES=$(CLASSES:%.class=$(BINDIR)/main/%.class)

default: $(CLASS_FILES)
clean:
	rm $(BINDIR)/main/*.class
run: $(CLASS_FILES)
	java -cp $(BINDIR) main/GraphExperiment
javadoc:
	javadoc -d docs -cp $(BINDIR) -sourcepath src/ main
