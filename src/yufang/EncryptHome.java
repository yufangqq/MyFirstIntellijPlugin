package yufang;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

public class EncryptHome extends AnAction {
  @Override
  public void actionPerformed(AnActionEvent anActionEvent) {
    VirtualFile vFile = anActionEvent.getData(PlatformDataKeys.VIRTUAL_FILE);
    String filePath = vFile != null ? vFile.getPath() : null;

    new EncryptMainUi(filePath);
  }
}