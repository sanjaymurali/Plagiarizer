package core.algorithm.lcs;

import core.IO.Reader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//TODO support declarations where variable is assigned.

/**
 * Normalizer to get files ready for comparison.
 */
public class Normalizer {
    private String normalized;
    private static String replacementMethodName = "method";
    private static String replacementVariableName = "variable";

    /**
     * Constructs a normalizer by parsing a file into a string.
     *
     * @param filePath path to the file to normalize
     * @throws FileNotFoundException if file cannot be found or read
     */
    public Normalizer(String filePath) throws IOException {
        Reader r = new Reader();
        if (r.getFile(filePath) == null) {
            throw new IOException("Cannot find file");
        } else {
            this.normalized = r.getFile(filePath);
        }
    }

    /**
     * Gets the normalized string representation of the file.
     *
     * @return normalized string representation of the file
     */
    public String getNormalized() {
        return this.normalized;
    }

    /**
     * Runs normalization functions on string representation of file
     */
    public void runNormalization() {
        this.removeComments();
        this.removeExtraSpacesAndLines();
        this.removePackagesAndImports();
        this.replaceVariables();
        this.replaceMethods();
        this.organize();
        this.removeExtraSpacesAndLines();
        this.normalized = this.normalized.trim();
    }

    /**
     * Runs only variable and method normalization on string representation of file
     */
    public void runPartialNormalization() {
        this.replaceVariables();
        this.replaceMethods();
    }

    /**
     * Finds header, constructor, and variable declarations and restructures string in a standard order:
     * 1. Header
     * 2. Variable Declarations
     * 3. Constructor
     * 4. Private Methods
     * 4. Other Methods
     */
    private void organize() {
        String organized = "";

        /* Find and remove CONSTRUCTOR */ //TODO - why only works before header and variable declarations?
        String constructor = this.findConstructor();
        if (constructor == null) {
            constructor = "";
        } else {
            this.normalized = this.normalized.replace(constructor, "");
        }

       /* Find and remove HEADER */
        String header = this.findHeader();
        if (header == null) {
            header = "";
        } else {
            this.normalized = this.normalized.replace(header, "");
        }

        /* Find and remove VARIABLE DECLARATIONS */
        ArrayList variableDeclarations = this.findVariableDeclarations();
        String variableList = null;
        if (variableDeclarations.isEmpty()) {
            variableList = "";
        } else {
            variableList = "";
            for (int i = 0; i < variableDeclarations.size(); ++i) {
                String dec = (String) variableDeclarations.get(i);
                this.normalized = this.normalized.replace(dec.trim(), ""); //NEW
                variableList = variableList.concat(dec).concat(" ");

            }

        }

        /* Find and remove PRIVATE CLASSES */
        ArrayList privateClasses = this.findPrivateClasses();
        String privateClassNames = null;
        if (privateClasses.isEmpty()) {
            privateClassNames = "";
        } else {
            privateClassNames = "";
            for (int i = 0; i < privateClasses.size(); ++i) {
                String pc = (String) privateClasses.get(i);
                this.normalized = this.normalized.replace(pc.trim(), "");
                privateClassNames = privateClassNames.concat(pc).concat(" ");

            }

        }

        /* Reorganize string representation of file in standard order */

        organized = organized.concat(header);
        organized = organized.concat(variableList);
        organized = organized.concat(constructor);
        organized = organized.concat(privateClassNames);
        organized = organized.concat(this.getNormalized());// ^ add back if we want to keep new lines

        this.normalized = organized;
    }

    private void removePackagesAndImports() {
        this.normalized = this.normalized.replaceAll("(import|package).+?;", "").trim();
    }

    /**
     * Deals with "//" and "/*" comments
     **/
    private void removeComments() {
        this.normalized = this.normalized.replaceAll("(//.*\n)|(/\\*(.|[\\n\\r])*?\\*/)", "");

    }

    /**
     * Replaces 2+ white spaces or lines with one white space or line
     */
    private void removeExtraSpacesAndLines() {
        this.normalized = this.normalized.replaceAll("\n", "");
        this.normalized = this.normalized.replaceAll("( ){2,}", " ");
        this.normalized = this.normalized.replaceAll("(\\{ )", "{").trim();
    }

    /**
     * Isolates variable names from variable declarations, and puts them in an ArrayList
     *
     * @return array list with all variables in the program
     */
    private ArrayList<String> findVariables() {
        //TODO doesn't work yet with variables like ArrayList<string> b/c of characters
        ArrayList<String> variables = new ArrayList<String>();
        ArrayList<String> variableDeclarations = this.findVariableDeclarations();
        if (variableDeclarations.isEmpty()) {
            return variables;
        } else {
            for (int i = 0; i < variableDeclarations.size(); ++i) {
                String declaration = variableDeclarations.get(i);
                String variableName = declaration.replaceAll("(public|protected|private|static|)", "").trim();
                String remove = findEnclosed(variableName);
                if (remove != null) {
                    variableName = variableName.replace(remove, "").trim();
                }
                variableName = variableName.replaceAll(("([a-zA-Z][\\w]*.\\s)"), "").trim();
                variableName = variableName.replaceAll("(;)", "");
                if (!variableName.equals(replacementVariableName)) {
                    variables.add(variableName);
                }
            }
        }
        return variables;

    }

    /**
     * Replaces all variables in the program with a standard name.
     */
    private void replaceVariables() {
        ArrayList<String> variables = this.findVariables();
        if (!variables.isEmpty()) {
            this.replace(replacementVariableName, variables);
        }
    }

    /**
     * Replaces all names in the list with the provided new name
     *
     * @param newName new name to change all names to
     * @param list    list requiring name changes
     */
    private void replace(String newName, ArrayList<String> list) {
        if (list.isEmpty()) {
            return;
        } else {
            for (int i = 0; i < list.size(); ++i) {
                String singleName = list.get(i);
                this.normalized = this.normalized.replaceAll(singleName, newName);


            }
        }
    }

    /**
     * Finds the header for the class.
     *
     * @return header for the class.
     */
    private String findHeader() {
        String header = null;
        String pattern = "\\{";
        Matcher m = Pattern.compile(pattern).matcher(this.getNormalized());
        if (m.find()) {
            header = this.normalized.substring(0, m.end());
        }
        return header;
    }

    private ArrayList findPrivateClasses() {
        ArrayList<String> privateClasses = new ArrayList<String>();
        String pc = null;
        String pattern = "(private class )[^{]*(\\{)";
        Matcher m = Pattern.compile(pattern).matcher(this.getNormalized());
        while (m.find()) {
            int i = m.end();
            int openBraces = 1;
            int closedBraces = 0;
            do {
                Character c = this.getNormalized().charAt(i);
                if (c == '{') {
                    openBraces++;

                } else if (c == '}') {
                    closedBraces++;

                }
                ++i;
            } while (openBraces == 0 || openBraces != closedBraces);
            pc = this.normalized.substring(m.start(), i);
            privateClasses.add(pc);
        }
        return privateClasses;
    }


    /**
     * Finds the variable declarations for the class.
     *
     * @return variable declarations for the class
     */
    private ArrayList<String> findVariableDeclarations() { //TODO STILL DOESN'T WORK FOR [] VARIABLES
        ArrayList declarations = new ArrayList<String>();
        String pattern = "(public|protected|private|static)(\\s)(([a-zA-Z][\\w]*)(<[a-zA-Z][\\w]*>)|([a-zA-Z][\\w]*)([a-zA-Z][\\w]*))( [a-zA-Z][\\w]*)(;)";
        Matcher m = Pattern.compile(pattern).matcher(this.getNormalized());
        while (m.find()) {
            declarations.add(m.group());
        }
        return declarations;

    }

    /**
     * Finds the substring within provided string enclosed in '<' '>' symbols
     *
     * @param s String to check within
     * @return substring enclosed in '<' '>' symbols (including the symbols)
     */
    private static String findEnclosed(String s) {
        String result = null;
        int start = s.indexOf("<");
        if (start > -1) {
            int i = start;
            int open = 0;
            int closed = 0;
            do {
                Character c = s.charAt(i);
                if (c == '<') {
                    open++;

                } else if (c == '>') {
                    closed++;

                }
                ++i;
            } while (open == 0 || open != closed);
            result = s.substring(start, i);
        }
        return result;
    }

    /**
     * Finds the name of the class.
     *
     * @return name of the class
     */
    private String findClassName() {
        String className = null;
        String header = this.findHeader();
        if (header != null) {
            className = header;
            className = className.replaceAll("(public|protected|private|static)", "").trim();
            className = className.replace("class", "").trim();
            className = className.replace("{", "").trim();
            className = className.replaceAll("implements *([a-zA-Z][\\w]*)", "");
            className = className.replaceAll("extends *([a-zA-Z][\\w]*)", "");
            String remove = findEnclosed(className);
            if (remove != null) {
                className = className.replace(remove, "").trim();
            }
        }
        return className;
    }

    /**
     * Finds the constructor of the class.
     *
     * @return the constructor of the class
     */
    private String findConstructor() {
        String constructor = null;
        String className = null;
        className = this.findClassName();
        String pattern = null;
        Matcher m = null;
        if (className != null) {
            pattern = "(public)+(\\s)" + className;
            m = Pattern.compile(pattern).matcher(this.getNormalized());


            if (m.find()) {
                int i = m.start();
                int openBraces = 0;
                int closedBraces = 0;
                do {
                    Character c = this.getNormalized().charAt(i);
                    if (c == '{') {
                        openBraces++;

                    } else if (c == '}') {
                        closedBraces++;

                    }
                    ++i;
                } while (openBraces == 0 || openBraces != closedBraces);
                constructor = this.normalized.substring(m.start(), i);
            }
        }

        return constructor;
    }

    /**
     * Finds all method declarations, isolates method names, and puts them in an ArrayList
     *
     * @return array list with all method names in the program
     */
    public ArrayList<String> findMethods() {
        String methodDeclaration;
        ArrayList methods = new ArrayList<String>();
        String pattern = "(public|protected|private|static|public static|private static|protected static)(\\s)+([a-zA-Z][\\w]*\\s)+([a-zA-Z_$][\\w$]*)(\\()";
        Matcher m = Pattern.compile(pattern).matcher(this.getNormalized());
        while (m.find()) {
            methodDeclaration = this.normalized.substring(m.start(), m.end() - 1);
            String methodName = methodDeclaration.replace("(public|protected|private|static|public static|" +
                    "private static|protected static_)", "").trim();
            methodName = methodName.replaceAll(("([a-zA-Z][\\w]*.\\s)"), "").trim();
            if (!methodName.equals(replacementMethodName) && !methodName.equals(replacementVariableName)){
                methods.add(methodName);
            }
        }

        return methods;
    }

    /**
     * Replaces all methods in the program with a standard name.
     */
    public void replaceMethods() {
        ArrayList<String> methods = this.findMethods();
        this.replace(replacementMethodName, methods);
    }

}