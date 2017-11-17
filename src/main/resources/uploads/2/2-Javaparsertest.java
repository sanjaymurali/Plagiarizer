/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.BodyDeclaration;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.Parameter;
import japa.parser.ast.body.TypeDeclaration;
import japa.parser.ast.body.VariableDeclarator;
import japa.parser.ast.expr.Expression;
import japa.parser.ast.expr.MethodCallExpr;
import japa.parser.ast.expr.NameExpr;
import japa.parser.ast.expr.VariableDeclarationExpr;
import japa.parser.ast.stmt.BlockStmt;
import japa.parser.ast.stmt.Statement;
import japa.parser.ast.type.PrimitiveType;
import japa.parser.ast.type.ReferenceType;
import japa.parser.ast.type.Type;
import japa.parser.ast.type.VoidType;
import japa.parser.ast.visitor.VoidVisitor;
import japa.parser.ast.visitor.VoidVisitorAdapter;
import java.io.FileInputStream;
import java.io.IOException;
/**
 *
 * @author cjrjh
 */
public class Javaparsertest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ParseException, IOException {
       // creates an input stream for the file to be parsed
    FileInputStream in = new FileInputStream("D:\\OneDrive\\文档\\School\\2017FALL\\CS5500\\HW4\\src\\hw4\\HW4.java");

    CompilationUnit cu;
    try {
        // parse the file
        cu = JavaParser.parse(in);
    } finally {
        in.close();
    }
    VoidVisitor<?> methodNameVisitor = new MethodNamePrinter();
 methodNameVisitor.visit(cu, null);
 

    // prints the resulting compilation unit to default system output
    System.out.println(cu.getTypes());
    }
    
    
    private static class MethodNamePrinter extends VoidVisitorAdapter<Void> {
 
     @Override
     public void visit(MethodDeclaration md, Void arg) {
        super.visit(md, arg);
        System.out.println("Method Name Printed: " + md.getName());
    }
 }
}
