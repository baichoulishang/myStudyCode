//: annotations/InterfaceExtractorProcessor.java
// APT-based annotation processing.
// {Exec: apt -factory
// annotations.InterfaceExtractorProcessorFactory
// Multiplier.java -s ../annotations}
//Exec: apt -factory annotations.InterfaceExtractorProcessorFactory Multiplier.java -s ../annotations
package annotations;

import com.sun.mirror.apt.AnnotationProcessor;
import com.sun.mirror.apt.AnnotationProcessorEnvironment;
import com.sun.mirror.apt.Filer;
import com.sun.mirror.apt.Messager;
import com.sun.mirror.declaration.MethodDeclaration;
import com.sun.mirror.declaration.Modifier;
import com.sun.mirror.declaration.ParameterDeclaration;
import com.sun.mirror.declaration.TypeDeclaration;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class InterfaceExtractorProcessor implements AnnotationProcessor {
    // 要处理的注解所处的环境
    private final AnnotationProcessorEnvironment env;
    private ArrayList<MethodDeclaration> interfaceMethods = new ArrayList<MethodDeclaration>();

    public InterfaceExtractorProcessor(AnnotationProcessorEnvironment env) {
        this.env = env;
    }

    public void process() {
        for (TypeDeclaration typeDecl : env.getSpecifiedTypeDeclarations()) {
            // 取得ExtractInterface的注解对象
            ExtractInterface extractInterface = typeDecl.getAnnotation(ExtractInterface.class);
            if (extractInterface == null) {
                break;
            }
            for (MethodDeclaration m : typeDecl.getMethods())
                // 如果是public方法,而且不是static,则加入到集合中
                if (m.getModifiers().contains(Modifier.PUBLIC) && !(m.getModifiers().contains(Modifier.STATIC))) {
                    interfaceMethods.add(m);
                }
            if (interfaceMethods.size() > 0) {
                try {
                    Messager messager = env.getMessager();
                    Filer filer = env.getFiler();
                    // 创建接口的文件,等会儿往里面写入信息
                    PrintWriter writer = filer.createSourceFile(extractInterface.value());
                    // 写入包信息
                    writer.println("package " + typeDecl.getPackage().getQualifiedName() + ";");
                    // 写入接口名称
                    writer.println("public interface " + extractInterface.value() + " {");
                    // 写入接口列表们
                    for (MethodDeclaration m : interfaceMethods) {
                        writer.print("  public ");
                        writer.print(m.getReturnType() + " ");
                        writer.print(m.getSimpleName() + " (");
                        int i = 0;
                        for (ParameterDeclaration parm : m.getParameters()) {
                            writer.print(parm.getType() + " " + parm.getSimpleName());
                            if (++i < m.getParameters().size()) {
                                writer.print(", ");
                            }
                        }
                        writer.println(");");
                    }
                    writer.println("}");
                    writer.close();
                } catch (IOException ioe) {
                    throw new RuntimeException(ioe);
                }
            }
        }
    }
} ///:~
