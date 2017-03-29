package yufang;

import com.intellij.util.ui.UIUtil;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.dnd.DropTarget;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

public class EncryptMainUi extends JFrame implements ActionListener {

  private final static String TITLE = "Encrypt File";
  //make reference for Main class
  private static EncryptMainUi m;
  public BufferedImage originalImage, copyImage;
  int image_Xpos, image_Ypos;
  private JTextField resPathJTF;
  private JLabel imagePreviewJL;

  public EncryptMainUi() {
    m = this;
    setLayout(null);
    setVisible(true);
    setSize(900, 500);
    setTitle(TITLE);
    setResizable(false);
    //setLocation(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width - this.getWidth(), 100);
    setLocationRelativeTo(null);
    // creating border
    Border border = BorderFactory.createEtchedBorder();

    JMenu fileMenu = new JMenu("File");
    JMenuBar menuBar = new JMenuBar();
    String[] fileItems = {"Open", "Save"};
    for (String fileItem : fileItems) {
      JMenuItem item = new JMenuItem(fileItem);
      item.addActionListener(this);
      fileMenu.add(item);
    }
    menuBar.add(fileMenu);

    resPathJTF = new JTextField();

    JLabel resPathJL = new JLabel("Browse your image file here");
    JButton addBtn = new JButton("Add");

    imagePreviewJL = new JLabel();

    imagePreviewJL.setBorder(border);
    imagePreviewJL.setHorizontalAlignment(JLabel.CENTER);
    imagePreviewJL.setVerticalAlignment(JLabel.CENTER);

    add(resPathJL).setBounds(20, 50, 170, 30);
    add(resPathJTF).setBounds(190, 50, 150, 30);
    add(addBtn).setBounds(350, 50, 100, 30);

    add(imagePreviewJL).setBounds(600, 0, 300, 500);

    setJMenuBar(menuBar);
    revalidate();

    resPathJTF.setEditable(false);

    //register listener
    addBtn.addActionListener(this);

    setDragAndDropListener();
    setMouseListener();
    setResDocumentListener();



  }

  public static EncryptMainUi getInstance() {
    return m;
  }

  // main() method
  public static void main(String args[]) {
    m = new EncryptMainUi();
  }

//		################   Listeners ##############################

  @Override
  public void actionPerformed(ActionEvent e) {
    // TODO Auto-generated method stub
    switch (e.getActionCommand()) {
      case "Add":
      case "Open":
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setFileFilter(new FileNameExtensionFilter("Image files only", "png", "jpg"));
        int result = jFileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
          File file = jFileChooser.getSelectedFile();
          setImageIcon(file);
        }

        break;
      case "Save":
        if (resPathJTF.getText() != null && !resPathJTF.getText().equalsIgnoreCase("")) {

          File file = new File(resPathJTF.getText());
          jFileChooser = new JFileChooser();
          jFileChooser.setSelectedFile(file);
          int choose_option = jFileChooser.showSaveDialog(this);

          if (choose_option == JFileChooser.APPROVE_OPTION) {
            File file1 = jFileChooser.getSelectedFile();
            try {
              if (copyImage != null)
                ImageIO.write(copyImage, "png", file1);
              else
                ImageIO.write(originalImage, "png", file1);
              JOptionPane.showMessageDialog(this, "File saved successfully");
            } catch (IOException e1) {
              // TODO Auto-generated catch block
              e1.printStackTrace();
              JOptionPane.showMessageDialog(this, "File not saved", "Warning", JOptionPane.WARNING_MESSAGE);
            }
          } else {
            JOptionPane.showMessageDialog(this, "File not saved", "Warning", JOptionPane.WARNING_MESSAGE);
          }
        } else {
          JOptionPane.showMessageDialog(this, "You should select image first");
        }
//               new SearchAndSave(requiredColor, image, file.getName(), file.getParentFile().getName(), file.getParentFile().getParentFile().getPath());
        break;
      default:
        break;

    }

  }

  private void setDragAndDropListener() {
    EncryptDragAndDropListener listener = new EncryptDragAndDropListener();
    new DropTarget(imagePreviewJL, listener);
  }

  private void setMouseListener() {
    imagePreviewJL.addMouseListener(new MouseListener() {

      @Override
      public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

      }

      @Override
      public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

      }

      @Override
      public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

      }

      @Override
      public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

      }

      @Override
      public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

      }
    });
  }

  private void setResDocumentListener() {
    resPathJTF.getDocument().addDocumentListener(new DocumentListener() {

      @Override
      public void removeUpdate(DocumentEvent arg0) {
        // TODO Auto-generated method stub

      }

      @Override
      public void insertUpdate(DocumentEvent arg0) {
        // TODO Auto-generated method stub
      }

      @Override
      public void changedUpdate(DocumentEvent arg0) {
        // TODO Auto-generated method stub
      }
    });
  }


  private void setUp() {
  }

  public void setImageIcon(File file) {
    try {
      if (file.getAbsoluteFile().toString().contains(".png") ||
          file.getAbsoluteFile().toString().contains(".jpg")) {

        resPathJTF.setText(file.getAbsolutePath());

        originalImage = ImageIO.read(new File(resPathJTF.getText()));
        imagePreviewJL.setIcon(new ImageIcon(originalImage));

      } else {
        JOptionPane.showMessageDialog(this, "Selected file is not an image file");
      }
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

}


