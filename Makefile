all: clean build

clean:
	rm -rf bin/*

build:
	javac -d bin src/com/lugocorp/tempo/*.java

run:
	java -cp bin com.lugocorp.tempo.Main
