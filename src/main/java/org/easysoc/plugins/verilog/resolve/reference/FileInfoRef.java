package org.easysoc.plugins.verilog.resolve.reference;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiReferenceBase;
import com.intellij.psi.SyntheticElement;
import com.intellij.psi.impl.FakePsiElement;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FileInfoRef extends PsiReferenceBase<PsiElement> {

  public FileInfoRef(@NotNull PsiElement element, TextRange textRange) {
    super(element, textRange);
  }
  
  @Nullable
  @Override
  public PsiElement resolve() {
    return new MyFakePsiElement();
  }

  public class MyFakePsiElement extends FakePsiElement implements SyntheticElement {
    @Override
    public PsiElement getParent() {
      return myElement;
    }

    @Override
    public void navigate(boolean requestFocus) {

      String[] token = myElement.getText().split(" ");
      String filename = token[1].substring(2);
      String[] pos = token[2].split(":");
      int line = Integer.valueOf(pos[0]) - 1;
      int column = Integer.valueOf(pos[1].replace("]", "")) - 1;

      Project p = myElement.getProject();
      PsiFile[] files = FilenameIndex.getFilesByName(p,filename, GlobalSearchScope.projectScope(p));
      int length = files.length;
      if (length != 0){
        if (length == 1) {
          new OpenFileDescriptor(p, files[0].getVirtualFile(),line,column).navigate(true);
        } else {
          Notifications.Bus.notify(new Notification("","",String.valueOf(length) + " "
                  + filename + "files found!", NotificationType.INFORMATION));
        }
      } else {
        Notifications.Bus.notify(new Notification("","",filename + " not found!",
                NotificationType.ERROR));
      }
    }
  }
}