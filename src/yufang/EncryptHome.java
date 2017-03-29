package yufang;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

public class EncryptHome extends AnAction {
  @Override
  public void actionPerformed(AnActionEvent anActionEvent) {
    new EncryptMainUi();
  }
}