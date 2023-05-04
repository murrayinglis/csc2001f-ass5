JAVAC=/usr/bin/javac
JAVA=/usr/bin/java
.SUFFIXES: .java .class

SRCDIR=src/main
BINDIR=bin
LIBDIR=lib

$(BINDIR)/main/%.class: $(SRCDIR)/%.java 
	$(JAVAC) -d $(BINDIR) -cp $(BINDIR):$(LIBDIR)/* -sourcepath $(SRCDIR) $<

CLASSES=Instrumentation.class GraphException.class Graph.class RandomGraphGenerator.class GraphExperiment.class
CLASS_FILES=$(CLASSES:%.class=$(BINDIR)/main/%.class)

default: $(CLASS_FILES)
clean:
	rm $(BINDIR)/main/*.class data/graphs/* data/*.jpeg data/*.txt
run: $(CLASS_FILES)
	java -cp $(BINDIR):$(LIBDIR)/* main/GraphExperiment
javadoc:
	javadoc -d docs -cp $(BINDIR):$(LIBDIR)/* -sourcepath src/ main
