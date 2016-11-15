all: compile cleanup
	java Main

compile:
	javac src/*.java

cleanup:
	mv src/*.class bin
	rm src/*.java~

clean: cleanup
	rm bin/*.class
	
mrproper: clean
	rm Main
