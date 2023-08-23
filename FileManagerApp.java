import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileManagerApp extends JFrame implements ActionListener {

    private JTextArea outputText;
    private String clipboardFile;
    private String clipboardFolder;

    public FileManagerApp() {
        setTitle("File Manager");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.BLACK);

        outputText = new JTextArea(15, 90);
        outputText.setFont(new Font("Arial", Font.PLAIN, 12));
        outputText.setBackground(Color.BLACK);
        outputText.setForeground(Color.GREEN);
        JScrollPane scrollPane = new JScrollPane(outputText);
        add(scrollPane, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);
        add(buttonPanel, BorderLayout.CENTER);
        buttonPanel.setLayout(new FlowLayout());

        createButton("Create File", buttonPanel);
        createButton("Copy File", buttonPanel);
        createButton("Paste File", buttonPanel);
        createButton("Rename File", buttonPanel);
        createButton("Delete File", buttonPanel);

        JPanel secondLinePanel = new JPanel();
        secondLinePanel.setBackground(Color.BLACK);
        add(secondLinePanel, BorderLayout.SOUTH);
        secondLinePanel.setLayout(new FlowLayout());

        createButton("Create Folder", secondLinePanel);
        createButton("Rename Folder", secondLinePanel);
        createButton("Delete Folder", secondLinePanel);

        setVisible(true);
    }

    private void createButton(String text, JPanel panel) {
        JButton button = new JButton(text);
        button.setFont(new Font("ROG", Font.PLAIN, 12));
        button.setBackground(Color.GREEN);
        button.setForeground(Color.WHITE);
        button.addActionListener(this);
        panel.add(button);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Create File":
                createFile();
                break;
            case "Copy File":
                copyFile();
                break;
            case "Paste File":
                pasteFile();
                break;
            case "Rename File":
                renameFile();
                break;
            case "Delete File":
                deleteFile();
                break;
            case "Create Folder":
                createDirectory();
                break;
            case "Rename Folder":
                renameFolder();
                break;
            case "Delete Folder":
                deleteFolder();
                break;
            default:
                break;
        }
    }

    private void createFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose Directory");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int option = fileChooser.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            File directoryPath = fileChooser.getSelectedFile();
            String newFileName = JOptionPane.showInputDialog(this, "Enter the name for the new file:");
            if (newFileName != null && !newFileName.isEmpty()) {
                File newFile = new File(directoryPath, newFileName);
                try {
                    if (newFile.createNewFile()) {
                        outputText.append("New file Successfully created: " + newFile.getAbsolutePath() + "\n");
                    } else {
                        outputText.append("File already exists: " + newFile.getAbsolutePath() + "\n");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                    outputText.append("Error creating the file.\n");
                }
                outputText.setCaretPosition(outputText.getDocument().getLength());
            }
        }
    }

    private void copyFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose File to Copy");
        int option = fileChooser.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            File fileToCopy = fileChooser.getSelectedFile();
            clipboardFile = fileToCopy.getAbsolutePath();
            outputText.append("File Successfully copied: " + clipboardFile + "\n");
            outputText.setCaretPosition(outputText.getDocument().getLength());
        }
    }

    private void pasteFile() {
        if (clipboardFile != null && !clipboardFile.isEmpty()) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Choose Destination Folder");
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int option = fileChooser.showOpenDialog(this);
            if (option == JFileChooser.APPROVE_OPTION) {
                File destinationPath = fileChooser.getSelectedFile();
                File copiedFile = new File(clipboardFile);
                File destinationFile = new File(destinationPath, copiedFile.getName());
                try {
                    Files.copy(Paths.get(clipboardFile), destinationFile.toPath());
                    outputText.append("File Successfully pasted to: " + destinationFile.getAbsolutePath() + "\n");
                } catch (IOException ex) {
                    ex.printStackTrace();
                    outputText.append("Error pasting the file.\n");
                }
                outputText.setCaretPosition(outputText.getDocument().getLength());
            }
        } else {
            outputText.append("No file copied. Please copy a file first.\n");
            outputText.setCaretPosition(outputText.getDocument().getLength());
        }
    }

    private void renameFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose File to Rename");
        int option = fileChooser.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            File fileToRename = fileChooser.getSelectedFile();
            String newFileName = JOptionPane.showInputDialog(this, "Enter the new name for the file:");
            if (newFileName != null && !newFileName.isEmpty()) {
                File newFile = new File(fileToRename.getParent(), newFileName);
                if (fileToRename.renameTo(newFile)) {
                    outputText.append("File Successfully renamed to: " + newFile.getAbsolutePath() + "\n");
                } else {
                    outputText.append("Error renaming the file.\n");
                }
                outputText.setCaretPosition(outputText.getDocument().getLength());
            }
        }
    }

    private void deleteFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose File to Delete");
        int option = fileChooser.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            File fileToDelete = fileChooser.getSelectedFile();
            int confirmResult = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete '"
                    + fileToDelete.getAbsolutePath() + "'?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirmResult == JOptionPane.YES_OPTION) {
                if (fileToDelete.delete()) {
                    outputText.append("File Successfully deleted: " + fileToDelete.getAbsolutePath() + "\n");
                } else {
                    outputText.append("Error deleting the file.\n");
                }
                outputText.setCaretPosition(outputText.getDocument().getLength());
            }
        }
    }

    private void createDirectory() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose Directory");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int option = fileChooser.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            File directoryPath = fileChooser.getSelectedFile();
            String newDirectoryName = JOptionPane.showInputDialog(this, "Enter the name for the new directory:");
            if (newDirectoryName != null && !newDirectoryName.isEmpty()) {
                File newDirectory = new File(directoryPath, newDirectoryName);
                if (newDirectory.mkdirs()) {
                    outputText.append("New directory created Successfully: " + newDirectory.getAbsolutePath() + "\n");
                } else {
                    outputText.append("Error creating the directory.\n");
                }
                outputText.setCaretPosition(outputText.getDocument().getLength());
            }
        }
    }

    private void renameFolder() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose Folder to Rename");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int option = fileChooser.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            File folderToRename = fileChooser.getSelectedFile();
            String newFolderName = JOptionPane.showInputDialog(this, "Enter the new name for the folder:");
            if (newFolderName != null && !newFolderName.isEmpty()) {
                File newFolder = new File(folderToRename.getParent(), newFolderName);
                if (folderToRename.renameTo(newFolder)) {
                    outputText.append("Folder Successfully renamed to: " + newFolder.getAbsolutePath() + "\n");
                } else {
                    outputText.append("Error renaming the folder.\n");
                }
                outputText.setCaretPosition(outputText.getDocument().getLength());
            }
        }
    }

    private void deleteFolder() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose Folder to Delete");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int option = fileChooser.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            File folderToDelete = fileChooser.getSelectedFile();
            int confirmResult = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete '"
                    + folderToDelete.getAbsolutePath() + "'?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirmResult == JOptionPane.YES_OPTION) {
                if (deleteRecursive(folderToDelete)) {
                    outputText.append("Folder deleted: " + folderToDelete.getAbsolutePath() + "\n");
                } else {
                    outputText.append("Error deleting the folder.\n");
                }
                outputText.setCaretPosition(outputText.getDocument().getLength());
            }
        }
    }

    private boolean deleteRecursive(File file) {
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                if (!deleteRecursive(child)) {
                    return false;
                }
            }
        }
        return file.delete();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FileManagerApp::new);
    }
}