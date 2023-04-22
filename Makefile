JAVAC=/usr/bin/javac
.SUFFIXES: .java .class

SRCDIR=src
BINDIR=bin
LIBDIR=lib

$(BINDIR)//%.class:$(SRCDIR)/%.java
	$(JAVAC) -d $(BINDIR)/ -cp $(BINDIR) $<

CLASSES=Post.class Account.class BST.class Frame.class
CLASS_FILES=$(CLASSES:%.class=$(BINDIR)//%.class)

default: $(CLASS_FILES)
clean:
	rm $(BINDIR)//*.class
run: $(CLASS_FILES)
	java -cp $(BINDIR)
javadoc:
	javadoc -d docs -cp bin -sourcepath src/ 