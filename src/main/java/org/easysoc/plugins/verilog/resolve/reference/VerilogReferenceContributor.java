package org.easysoc.plugins.verilog.resolve.reference;

import com.intellij.openapi.util.TextRange;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.*;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

public class VerilogReferenceContributor extends PsiReferenceContributor {
    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
//      PlatformPatterns.psiElement(PsiComment.class)
        registrar.registerReferenceProvider(PlatformPatterns.psiComment(),
                new PsiReferenceProvider() {
                    @NotNull
                    @Override
                    public PsiReference[] getReferencesByElement(@NotNull PsiElement element,
                                                                 @NotNull ProcessingContext context) {
                        PsiComment comment = (PsiComment) element;
                        String value = comment.getText();
                        if ((value.startsWith("// @["))) {
                            // 这里只影响鼠标点击时的提示范围，不影响 getText() 内容
                            TextRange textRange = new TextRange(5, value.length() - 1);
                            return new PsiReference[]{new FileInfoRef(element, textRange)};
                        }
                        return PsiReference.EMPTY_ARRAY;
                    }
                });
    }
}
