JAVAC=/usr/bin/javac
.SUFFIXES: .java .class

SRCDIR=src/main
BINDIR=bin/main

$(BINDIR)/%.class:$(SRCDIR)/%.java
	$(JAVAC) -d $(BINDIR) -cp $(BINDIR) $<

CLASSES=Vertex.class Edge.class Graph.class GraphException.class GraphExperiment.class Instrumentation.class Path.class RandomGraphGenerator.class
CLASS_FILES=$(CLASSES:%.class=$(BINDIR)/%.class)

default: $(CLASS_FILES)
clean:
	rm $(BINDIR)/*.class
run: $(CLASS_FILES)
	java -cp $(BINDIR) GraphExperiment
javadoc:
	javadoc -d docs -cp $(BINDIR) -sourcepath src/main main
