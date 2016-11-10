all: compile cleanup
	java Main

compile:
	javac src/*.java

cleanup:
	mv src/*.class bin
	rm src/*.java~

