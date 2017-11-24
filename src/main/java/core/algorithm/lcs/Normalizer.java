package core.algorithm.lcs;

import core.IO.Reader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Normalizer to get files ready for comparison.
 */
public class Normalizer {
    private String normalized;

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
        /*File file = new File(filePath);
        Scanner scanner = new Scanner(file);
        StringBuilder string = new StringBuilder((int) file.length());
        while (scanner.hasNextLine()) {
            string.append(scanner.nextLine());
            string.append("\n");
        }
        this.normalized = string.toString();
        scanner.close();*/

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
        System.out.println("1In here!");
        this.removeComments();
        System.out.println("2In here!");
        this.removeExtraSpacesAndLines();
        System.out.println("3In here!");
        this.removePackagesAndImports();
        System.out.println("4In here!");
        this.organize();
        System.out.println("5In here!");
        this.replaceVariables();
        System.out.println("6In here!");
        this.replaceMethods();
        System.out.println("7In here!");
        this.normalized = this.normalized.trim();
    }

    /**
     * Finds header, constructor, and variable declarations and restructures string in a standard order:
     * 1. Header
     * 2. Variable Declarations
     * 3. Constructor
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
            variableList = "\n";
            for (int i = 0; i < variableDeclarations.size(); ++i) {
                String dec = (String) variableDeclarations.get(i);
                variableList = variableList.concat(dec).concat("\n");
            }
            this.normalized = this.normalized.replace(variableList, "").trim();
        }

        /* Reorganize string representation of file in standard order */

        organized = organized.concat(header);
        organized = organized.concat(variableList);
        organized = organized.concat(constructor);
        organized = organized.concat("\n" + this.getNormalized()); //TODO fix weirdness with spacing

        // System.out.print("AFTER REMOVAL:\n" + this.normalized); //DEBUG
        this.normalized = organized;
        // System.out.print("\n\nAFTER RE-ORGANIZATION:" + this.normalized); //DEBUG
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
        //this.normalized = this.normalized.replaceAll("\\s{2,}", " ").trim(); // removes all spaces/new lines
        this.normalized = this.normalized.replaceAll("( ){2,}", " ");
        this.normalized = this.normalized.replaceAll("(\n )", "\n");
        this.normalized = this.normalized.replaceAll("(\n{2,})", "\n");
        this.normalized = this.normalized.replaceAll("(; )", ";").trim();
    }

    /**
     * Isolates variable names from variable declarations, and puts them in an ArrayList
     *
     * @return array list with all variables in the program
     * @throws IOException //TODO HANDLE EXCEPTION
     */
    private ArrayList<String> findVariables() {
        //TODO doesn't work yet with variables like ArrayList<string> b/c of characters
        ArrayList<String> variables = new ArrayList<String>();
        ArrayList<String> variableDeclarations = this.findVariableDeclarations();
        for (int i = 0; i < variableDeclarations.size(); ++i) {
            String declaration = variableDeclarations.get(i);
            String variableName = declaration.replaceAll("(public|protected|private|static|)", "").trim();
            variableName = variableName.replaceAll(("([a-zA-Z][\\w]*.\\s)"), "").trim();
            variableName = variableName.replaceAll("(;)", "");
            variables.add(variableName);
        }
        return variables;

    }

    /**
     * Replaces all variables in the program with a standard name.
     */
    private void replaceVariables() {
        String replacementVariableName = "variable";
        ArrayList<String> variables = this.findVariables();
        for (int i = 0; i < variables.size(); ++i) {
            String variableName = variables.get(i);
            this.normalized = this.normalized.replaceAll(variableName, replacementVariableName);

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
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(this.getNormalized());
        if (m.find()) {
            header = this.normalized.substring(0, m.end());
        }
        return header;
    }

    /**
     * Finds the variable declarations for the class.
     *
     * @return variable declarations for the class
     */
    private ArrayList<String> findVariableDeclarations() {
        String declaration;
        ArrayList declarations = new ArrayList<String>();
        //TODO doesn't work yet with variables like ArrayList<string> b/c of characters
        String pattern = "(public|protected|private|static|_)+(\\s)+([a-zA-Z][\\w]*\\s)+([a-zA-Z_$][\\w$]*)+(;)";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(this.getNormalized());
        while (m.find()) {
            declaration = this.normalized.substring(m.start(), m.end());
            declarations.add(declaration);
        }
        return declarations;

    }

    /**
     * Finds the name of the class.
     *
     * @return name of the class
     */
    private String findClassName() {  //TODO make this tolerate less standard class names (i.e. extends... implements...) through regex (started w/ commented out lines)
        String className = null;
        String header = this.findHeader();
        //String pattern = "([a-zA-Z_$][\\w$]*)";
        //Pattern p = Pattern.compile(pattern);
        //Matcher m = p.matcher(header);
        if (header != null) {
            className = header.replace("(public|protected|private|static|)", "").trim();
            className = className.replace("class", "").trim();
            className = className.replace("{", "").trim();
            //className = className.substring(0, m.end());
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
        Pattern p = null;
        Matcher m = null;
        if (className != null) {
            pattern = "(public)+(\\s)" + className; //TODO - may be able to replace do while loop by using regex to find up to last } ?
            p = Pattern.compile(pattern);
            m = p.matcher(this.getNormalized());


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
        String pattern = "(public|protected|private|static|public static|private static|protected static_)" +
                "+(\\s)+([a-zA-Z][\\w]*\\s)+([a-zA-Z_$][\\w$]*)+(\\()";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(this.getNormalized());
        while (m.find()) {
            methodDeclaration = this.normalized.substring(m.start(), m.end() - 1);
            String methodName = methodDeclaration.replace("(public|protected|private|static|public static|" +
                    "private static|protected static_)", "").trim();
            methodName = methodName.replaceAll(("([a-zA-Z][\\w]*.\\s)"), "").trim();
            methods.add(methodName);
        }

        return methods;
    }

    /**
     * Replaces all methods in the program with a standard name.
     */
    public void replaceMethods() {
        String replacementVariableName = "method";
        ArrayList<String> methods = this.findMethods();
        for (int i = 0; i < methods.size(); ++i) {
            String methodName = methods.get(i);
            this.normalized = this.normalized.replaceAll(methodName, replacementVariableName);

        }

    }

}