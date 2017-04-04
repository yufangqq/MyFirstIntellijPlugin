package yufang;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.dnd.DropTarget;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class EncryptMainUi extends JFrame implements ActionListener {

  private final static String TITLE = "Encrypt File";
  //make reference for Main class
  private static EncryptMainUi m;
  public BufferedImage originalImage, copyImage;
  private JTextField resPathJTF;
  private JLabel filePreviewJL;

  byte[] originalFile;


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
    String[] fileItems = {"Open", "Save", "Encrypt", "Decrypt"};
    for (String fileItem : fileItems) {
      JMenuItem item = new JMenuItem(fileItem);
      item.addActionListener(this);
      fileMenu.add(item);
    }
    menuBar.add(fileMenu);

    resPathJTF = new JTextField();

    JLabel resPathJL = new JLabel("Browse your file here");
    JButton addBtn = new JButton("Add");

    filePreviewJL = new JLabel();

    filePreviewJL.setBorder(border);
    filePreviewJL.setHorizontalAlignment(JLabel.CENTER);
    filePreviewJL.setVerticalAlignment(JLabel.CENTER);

    add(resPathJL).setBounds(20, 50, 170, 30);
    add(resPathJTF).setBounds(190, 50, 150, 30);
    add(addBtn).setBounds(350, 50, 100, 30);

    add(filePreviewJL).setBounds(600, 0, 300, 500);

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
        // jFileChooser.setFileFilter(new FileNameExtensionFilter("Image files only", "png", "jpg"));
        int result = jFileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
          File file = jFileChooser.getSelectedFile();
          setSelectedFile(file);
        }

        break;
      case "Save":
        if (resPathJTF.getText() != null && !resPathJTF.getText().equalsIgnoreCase("")) {

          File currentSelectedFile = new File(resPathJTF.getText());
          jFileChooser = new JFileChooser();
          jFileChooser.setSelectedFile(currentSelectedFile);
          int choose_option = jFileChooser.showSaveDialog(this);

          if (choose_option == JFileChooser.APPROVE_OPTION) {
            File newSelectedFile = jFileChooser.getSelectedFile();
            try {
              Path path = Paths.get(newSelectedFile.getPath());
              Files.write(path, originalFile); //creates, overwrites
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
          JOptionPane.showMessageDialog(this, "You should select file first");
        }
//               new SearchAndSave(requiredColor, image, file.getName(), file.getParentFile().getName(), file.getParentFile().getParentFile().getPath());
        break;
      case "Encrypt":
        if (resPathJTF.getText() != null && !resPathJTF.getText().equalsIgnoreCase("")) {

          File currentSelectedFile = new File(resPathJTF.getText());
          jFileChooser = new JFileChooser();
          jFileChooser.setSelectedFile(currentSelectedFile);
          int choose_option = jFileChooser.showSaveDialog(this);

          if (choose_option == JFileChooser.APPROVE_OPTION) {
            File newSelectedFile = jFileChooser.getSelectedFile();
            try {
              Path path = Paths.get(newSelectedFile.getPath());
              Files.write(path, EncryptAesCryptor.encrypt(originalFile)); //creates, overwrites
              JOptionPane.showMessageDialog(this, "File encrypted then saved successfully");
            } catch (IOException e1) {
              // TODO Auto-generated catch block
              e1.printStackTrace();
              JOptionPane.showMessageDialog(this, "File not saved", "Warning", JOptionPane.WARNING_MESSAGE);
            } catch (Exception e2) {
              e2.printStackTrace();
              JOptionPane.showMessageDialog(this, "File not encrypted", "Warning", JOptionPane.WARNING_MESSAGE);
            }
          } else {
            JOptionPane.showMessageDialog(this, "File not saved", "Warning", JOptionPane.WARNING_MESSAGE);
          }
        } else {
          JOptionPane.showMessageDialog(this, "You should select file first");
        }
//               new SearchAndSave(requiredColor, image, file.getName(), file.getParentFile().getName(), file.getParentFile().getParentFile().getPath());
        break;
      case "Decrypt":
        if (resPathJTF.getText() != null && !resPathJTF.getText().equalsIgnoreCase("")) {

          File currentSelectedFile = new File(resPathJTF.getText());
          jFileChooser = new JFileChooser();
          jFileChooser.setSelectedFile(currentSelectedFile);
          int choose_option = jFileChooser.showSaveDialog(this);

          if (choose_option == JFileChooser.APPROVE_OPTION) {
            File newSelectedFile = jFileChooser.getSelectedFile();
            try {
              Path path = Paths.get(newSelectedFile.getPath());
              Files.write(path, EncryptAesCryptor.decrypt(originalFile)); //creates, overwrites
              JOptionPane.showMessageDialog(this, "File decrypted then saved successfully");
            } catch (IOException e1) {
              // TODO Auto-generated catch block
              e1.printStackTrace();
              JOptionPane.showMessageDialog(this, "File not saved", "Warning", JOptionPane.WARNING_MESSAGE);
            } catch (Exception e2) {
              e2.printStackTrace();
              JOptionPane.showMessageDialog(this, "File not decrypted", "Warning", JOptionPane.WARNING_MESSAGE);
            }
          } else {
            JOptionPane.showMessageDialog(this, "File not saved", "Warning", JOptionPane.WARNING_MESSAGE);
          }
        } else {
          JOptionPane.showMessageDialog(this, "You should select file first");
        }
//               new SearchAndSave(requiredColor, image, file.getName(), file.getParentFile().getName(), file.getParentFile().getParentFile().getPath());
        break;
      default:
        break;

    }

  }

  private void setDragAndDropListener() {
    EncryptDragAndDropListener listener = new EncryptDragAndDropListener();
    new DropTarget(filePreviewJL, listener);
  }

  private void setMouseListener() {
    filePreviewJL.addMouseListener(new MouseListener() {

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

  public void setSelectedFile(File file) {
    try {
      resPathJTF.setText(file.getAbsolutePath());

      // originalImage = ImageIO.read(new File(resPathJTF.getText()));

      Path path = Paths.get(resPathJTF.getText());
      originalFile = Files.readAllBytes(path);
      filePreviewJL.setIcon(new ImageIcon(originalFile));

    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

}


