#LIBS := lib/jsr80-1.0.1.jar:lib/usb4java-1.3.0/lib/*.jar

all: clean build

clean:
	rm -rf bin/*

build:
	javac -d bin src/com/lugocorp/tempo/*.java
