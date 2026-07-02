# java-academic-genealogy-tree
Java command-line academic genealogy tree with professor/advisor relationships, tree queries, CSV input, and JUnit tests.

# Java PhD Genealogy Tree

This is a Java command-line program for storing and querying an academic genealogy tree.

The program reads professor/advisor relationships from a CSV file and builds a tree where each node represents a professor and each edge represents a PhD advisor-advisee relationship. Users can query the tree to print professors, check whether someone appears in the tree, count descendants, find advisors, find academic lineage, and find common ancestors.

## Features

- Reads academic genealogy data from a CSV file
- Represents professors as nodes in a tree
- Stores advisor-advisee relationships
- Prints professors in the tree
- Checks whether a professor exists in the tree
- Counts the size of the full tree or a subtree
- Finds a professor’s direct advisor
- Finds a professor’s academic lineage from the root
- Finds the common ancestor of two professors
- Includes JUnit tests for core tree behavior
- Includes input/output test files for command-line behavior

## Tech Stack

- Java
- JUnit 5

## Project Structure

```text
java-phd-genealogy-tree/
├── README.md
├── .gitignore
├── src/
│   └── cs2110/
│       ├── PhDApp.java
│       ├── PhDTree.java
│       ├── Professor.java
│       ├── NotFound.java
│       └── InputFormatException.java
├── tests/
│   └── cs2110/
│       └── PhDTreeTest.java
├── input-tests/
│   ├── test1/
│   ├── test2/
│   ├── test3/
│   ├── test4/
│   ├── test5/
│   ├── test6/
│   └── test7/
├── professors.csv
├── professors-shortened.csv
└── summary.txt
```

## How to Run

Make sure Java is installed.

Compile the source files:

```bash
javac -d out src/cs2110/*.java
```

Run the program with the default professor data file:

```bash
java -cp out cs2110.PhDApp professors.csv
```

Run the program with the shortened professor data file:

```bash
java -cp out cs2110.PhDApp professors-shortened.csv
```

You can then type commands into the terminal.

## Run with an Input Script

The program can also read commands from a file using the `-i` flag.

Example:

```bash
java -cp out cs2110.PhDApp -i input-tests/test1/input.txt professors-shortened.csv
```

This runs the commands from the input file against the given CSV file.

## Commands

Show the help menu:

```text
help
```

Print the full tree:

```text
print
```

Print a subtree starting from a professor:

```text
print <advisor name>
```

Check whether a professor is in the tree:

```text
contains <professor name>
```

Get the size of the full tree:

```text
size
```

Get the size of a professor’s subtree:

```text
size <professor name>
```

Find a professor’s direct advisor:

```text
advisor <advisee name>
```

Find the common ancestor of two professors:

```text
ancestor <professor 1>, <professor 2>
```

Find a professor’s academic lineage from the root:

```text
lineage <professor name>
```

Exit the program:

```text
exit
```

## Example Session

```text
help
contains David Patterson
size
advisor David Patterson
lineage David Patterson
exit
```

## CSV Format

The input CSV file should use this header:

```csv
advisee,year,advisor
```

Each row represents one advisor-advisee relationship.

Example:

```csv
advisee,year,advisor
Student Name,2020,Advisor Name
```

## Tests

This project includes JUnit tests in:

```text
tests/cs2110/PhDTreeTest.java
```

The tests cover core tree behavior such as:

- creating professor nodes
- inserting advisees
- checking whether professors exist in the tree
- finding subtree size
- finding advisors
- finding academic lineage
- finding common ancestors

If using IntelliJ, mark `src/` as the source root and `tests/` as the test root, then run the test class from the `tests/cs2110/` folder.
