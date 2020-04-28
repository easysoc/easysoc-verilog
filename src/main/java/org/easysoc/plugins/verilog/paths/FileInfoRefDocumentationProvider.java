package org.easysoc.plugins.verilog.paths;

import com.intellij.lang.documentation.AbstractDocumentationProvider;
import com.intellij.psi.PsiElement;
import org.easysoc.plugins.verilog.resolve.reference.FileInfoRef;

public class FileInfoRefDocumentationProvider extends AbstractDocumentationProvider {
    @Override
    public String getQuickNavigateInfo(PsiElement element, PsiElement originalElement) {
        if (element instanceof FileInfoRef.MyFakePsiElement) {
            return "Jump to Chisel code";
        }
        return null;
    }
}
