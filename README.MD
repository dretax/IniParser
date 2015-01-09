==IniParser==


Java Command line app. I fully tested It on windows, and It worked. I hope It will be the same on Linux.

Help:

java -jar IniParser.jar -h

Parameters:

*-h Show help.
*-m Specify mode: del/set/add (Delete Required Section and Key)
*-i Write the given ID to the inifile
*-p Direct path to the ini file
*-s Specify the Section if necessary
*-v Specify the Value if necessary
*-f File name

Parameter Explanations:

The program always defines the default values.

*-i = Must be defined.
*-m = Must be defined.
*-s = Section is the [Whitelist] by default. You can give something else if you want.
*-v = Value is the 1 after the = mark by default. You can change this also if you want. (Not really recommending if you use my whitelist plugin though, so you shouldn't edit the -s or the -v)
*-p = The directory where you are running the program by default. You are able to change this too.
*-f = File's name. It's Whitelist.ini by default.

Examples:

java -jar IniParser.jar -m set -i 525525414

java -jar IniParser.jar -m set -i 525525414 -v "This shouldn't equal with 1"

java -jar IniParser.jar -m set -i 525525414 -v "This shouldn't equal with 1" -s "Another Section"

java -jar IniParser.jar -m set -i 525525414 -v "This shouldn't equal with 1" -s "Another Section" -f "NotWhitelist.ini"

Output:

Well, no code changes, you just changed the file's name.

java -jar IniParser.jar -m set -i 525525414 -v "This shouldn't equal with 1" -s "Another Section" -p "D:\Rust\RustServer\Save\PyPlugins\Whitelist\"

Output:

No code changes again, you just changed path of the ini file. The file will be located at the given path, and not at the location of the jar file.

Basically all the parameters have a default value, except the -i. I hope It will work, I remember I had problems with PHP  when I wanted to write in the Magma's inifile, and Magma couldn't read the file.