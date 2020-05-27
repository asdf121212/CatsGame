import javax.print.attribute.standard.PresentationDirection;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.logging.Level;


public class creatorMain {

    private static JButton selectedButton = null;
    private JContentArea currentArea;
    private JCheckBox checkBox;

    private String parentDirectory = "";

    public creatorMain() {

        JFrame frame = new JFrame();
        frame.setFocusable(true);
        Dimension screenDimensions = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(screenDimensions.width / 2 - 660, screenDimensions.height / 2 - 410);
        currentArea = new JContentArea();
        currentArea.setVisible(true);
        currentArea.setPreferredSize(new Dimension(1300, 800));
        frame.add(currentArea, BorderLayout.CENTER);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        frame.add(menuBar, BorderLayout.NORTH);
        JMenu fileMenu = new JMenu("File");
        JMenuItem newItem = new JMenuItem("New");
        JMenuItem quitItem = new JMenuItem("Quit");
        //JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem saveNewFormat = new JMenuItem("Save");
        JMenuItem openItem = new JMenuItem("Open");
        //JMenuItem convertFolderItem = new JMenuItem("Add 50 to folder sp's");
        fileMenu.add(newItem);
        fileMenu.add(openItem);
        //fileMenu.add(saveItem);
        fileMenu.add(saveNewFormat);
        fileMenu.add(quitItem);
        //fileMenu.add(convertFolderItem);
        menuBar.add(fileMenu);

        JToolBar toolbar = new JToolBar(JToolBar.VERTICAL);
        frame.add(toolbar, BorderLayout.WEST);
        toolbar.setFloatable(false);
        //toolbar.setPreferredSize(new Dimension(60, toolbar.getHeight()));

        JButton selectButton = createButton("Images/select.png");
        JButton lineButton = createButton("Images/lineDraw.png");
        //JButton rectangleButton = createButton("Images/rectangle.png");

        toolbar.add(selectButton);

        selectedButton = selectButton;
        selectButton.setSelected(true);

        JButton floorButton = createButton("Images/floor.png");
        JButton wallButton = createButton("Images/wall.png");
        JButton acidButton = createButton("Images/acid.png");
        JButton nodeButton = createButton("Images/Node.png");
        JButton vacuumButton = createButton("Images/vacuumBlast1RC.png");
        JButton tinyMouseButton = createButton("Images/tinyMouse1_RC.png");
        JButton yarnballButton = createButton("Images/yarnBall-2C.png");
        JButton squirrelButton = createButton("Images/squirrelC.png");
        JButton rsquirrelButton = createButton("Images/r_squirrelC.png");
        JButton deleteButton = createButton("Images/X.png");
        JButton spawnButton = createButton("Images/spawnPoint.png");

        checkBox = new JCheckBox("Trap", false);
        checkBox.setVisible(false);

        toolbar.add(floorButton);
        toolbar.add(wallButton);
        toolbar.add(acidButton);
        toolbar.add(nodeButton);
        toolbar.add(spawnButton);
        toolbar.add(checkBox);
        toolbar.add(lineButton);
        toolbar.add(vacuumButton);
        toolbar.add(tinyMouseButton);
        toolbar.add(yarnballButton);
        toolbar.add(squirrelButton);
        toolbar.add(rsquirrelButton);
        toolbar.add(deleteButton);

        toolbar.revalidate();
        frame.pack();

        selectButton.addActionListener(e -> {
            buttonSelect();
            selectButton.setSelected(true);
            currentArea.SetTool(Tools.SELECT);
            selectedButton = selectButton;
        });
        lineButton.addActionListener(e -> {
            buttonSelect();
            lineButton.setSelected(true);
            currentArea.SetTool(Tools.LINE);
            selectedButton = lineButton;
        });
        floorButton.addActionListener(e -> {
            buttonSelect();
            floorButton.setSelected(true);
            currentArea.SetTool(Tools.FLOOR);
            selectedButton = floorButton;
        });
        wallButton.addActionListener(e -> {
            buttonSelect();
            wallButton.setSelected(true);
            currentArea.SetTool(Tools.WALL);
            selectedButton = wallButton;
        });
        nodeButton.addActionListener(e -> {
            buttonSelect();
            nodeButton.setSelected(true);
            currentArea.SetTool(Tools.NODE);
            selectedButton = nodeButton;
        });
        vacuumButton.addActionListener(e -> {
            buttonSelect();
            vacuumButton.setSelected(true);
            currentArea.SetTool(Tools.VACUUM);
            selectedButton = vacuumButton;
        });
        tinyMouseButton.addActionListener(e -> {
            buttonSelect();
            tinyMouseButton.setSelected(true);
            currentArea.SetTool(Tools.TINYMOUSE);
            selectedButton = tinyMouseButton;
        });
        yarnballButton.addActionListener(e -> {
            buttonSelect();
            yarnballButton.setSelected(true);
            currentArea.SetTool(Tools.YARNBALL);
            selectedButton = yarnballButton;
        });
        squirrelButton.addActionListener(e -> {
            buttonSelect();
            squirrelButton.setSelected(true);
            currentArea.SetTool(Tools.SQUIRREL);
            selectedButton = squirrelButton;
        });
        rsquirrelButton.addActionListener(e -> {
            buttonSelect();
            rsquirrelButton.setSelected(true);
            currentArea.SetTool(Tools.RSQUIRREL);
            selectedButton = rsquirrelButton;
        });
        deleteButton.addActionListener(e -> {
            if (selectedButton == selectButton) {
                currentArea.delete();
            }
        });
        acidButton.addActionListener(e -> {
            buttonSelect();
            acidButton.setSelected(true);
            currentArea.SetTool(Tools.ACID);
            selectedButton = acidButton;
        });
        spawnButton.addActionListener(e -> {
            buttonSelect();
            checkBox.setVisible(true);
            spawnButton.setSelected(true);
            if (checkBox.isSelected()) {
                currentArea.SetTool(Tools.TRAP_SPAWN);
            } else {
                currentArea.SetTool(Tools.SPAWN);
            }
            selectedButton = spawnButton;
        });

        checkBox.addActionListener(e -> {
            if (selectedButton == spawnButton) {
                if (checkBox.isSelected()) {
                    currentArea.SetTool(Tools.TRAP_SPAWN);
                } else {
                    currentArea.SetTool(Tools.SPAWN);
                }
            }
        });

        newItem.addActionListener(e -> {
            JContentArea newArea = new JContentArea();
            newArea.setPreferredSize(currentArea.getPreferredSize());
            frame.remove(currentArea);
            frame.add(newArea, BorderLayout.CENTER);
            newArea.setVisible(true);
            selectedButton.setSelected(false);
            selectedButton = selectButton;
            selectButton.setSelected(true);
            newArea.SetTool(Tools.SELECT);
            currentArea = newArea;
            frame.pack();
            frame.repaint();
        });

        quitItem.addActionListener(e -> {
            frame.dispose();
            System.exit(0);
        });

//        saveItem.addActionListener(e -> {
//            LevelConfigurationObject configObj = currentArea.displayList.getConfigObj();
//            try {
//                JFileChooser fileChooser = new JFileChooser();
//                int result = fileChooser.showSaveDialog(frame);
//                if (result == JFileChooser.APPROVE_OPTION) {
//                    FileOutputStream fStream = new FileOutputStream(fileChooser.getSelectedFile());
//                    ObjectOutputStream oStream = new ObjectOutputStream(fStream);
//                    oStream.writeObject(configObj);
//                }
//            } catch (IOException ex) {
//                System.out.println(ex.getMessage());
//            }
//        });

        saveNewFormat.addActionListener(e -> {
            LevelConfigObj2 configObj = currentArea.displayList.getConfigObj2();
            try {
                JFileChooser fileChooser = new JFileChooser();
                if (!parentDirectory.equals(""))  {
                    fileChooser.setCurrentDirectory(new File(parentDirectory));
                }
                int result = fileChooser.showSaveDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    FileOutputStream fStream = new FileOutputStream(fileChooser.getSelectedFile());
                    ObjectOutputStream oStream = new ObjectOutputStream(fStream);
                    oStream.writeObject(configObj);
                    parentDirectory = fileChooser.getSelectedFile().getParent();
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        });

        openItem.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            //fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("lvl"));
            //fileChooser.setCurrentDirectory(new File("../../"));
            if (!parentDirectory.equals(""))  {
                fileChooser.setCurrentDirectory(new File(parentDirectory));
            }
            int result = fileChooser.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    parentDirectory = fileChooser.getSelectedFile().getParent();
                    FileInputStream fStream = new FileInputStream(fileChooser.getSelectedFile());
                    ObjectInputStream oStream = new ObjectInputStream(fStream);
                    LevelConfigObj2 configObj = (LevelConfigObj2) oStream.readObject();

                    JContentArea newArea = new JContentArea();
                    newArea.setPreferredSize(currentArea.getPreferredSize());
                    frame.remove(currentArea);
                    frame.add(newArea, BorderLayout.CENTER);
                    newArea.setVisible(true);
                    selectedButton.setSelected(false);
                    selectedButton = selectButton;
                    selectButton.setSelected(true);
                    newArea.SetTool(Tools.SELECT);
                    currentArea = newArea;
                    frame.setTitle(fileChooser.getSelectedFile().getName());
                    frame.pack();
                    frame.repaint();

                    currentArea.loadConfigObj2(configObj);

                } catch (IOException | ClassNotFoundException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });

//        convertFolderItem.addActionListener(e -> {
//            JFileChooser fileChooser = new JFileChooser();
//            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//            if (!parentDirectory.equals(""))  {
//                fileChooser.setCurrentDirectory(new File(parentDirectory));
//            }
//            int result = fileChooser.showOpenDialog(frame);
//            if (result == JFileChooser.APPROVE_OPTION) {
//                try {
//                    for (File file : fileChooser.getSelectedFile().listFiles()) {
//                        System.out.println(file.getName());
//                        FileInputStream fStream = new FileInputStream(file);
//                        ObjectInputStream oStream = new ObjectInputStream(fStream);
//                        LevelConfigObj2 configObj = (LevelConfigObj2) oStream.readObject();
//                        configObj.leftSpawnPoint.x -= 50;
//                        configObj.leftSpawnPoint.y -= 50;
//                        configObj.rightSpawnPoint.x -= 50;
//                        configObj.rightSpawnPoint.y -= 50;
//                        configObj.topSpawnPoint.x -= 50;
//                        configObj.topSpawnPoint.y -= 50;
//                        configObj.bottomSpawnPoint.x -= 50;
//                        configObj.bottomSpawnPoint.y -= 50;
//                        FileOutputStream fOutStream = new FileOutputStream(file);
//                        ObjectOutputStream oOutStream = new ObjectOutputStream(fOutStream);
//                        oOutStream.writeObject(configObj);
//                        fStream.close();
//                        oStream.close();
//                        fOutStream.close();
//                        oOutStream.close();
//                    }
//
//                } catch (IOException | ClassNotFoundException ex) {
//                    System.out.println(ex.getMessage());
//                }
//            }
//        });


    }

    private void buttonSelect() {
        if (selectedButton != null) {
            selectedButton.setSelected(false);
        }
        checkBox.setVisible(false);
    }

    public JButton createButton(String pathToImage) {
        URL imageIcon = creatorMain.class.getResource(pathToImage);
        ImageIcon icon = new ImageIcon(imageIcon);
        icon.setImage(icon.getImage().getScaledInstance(40, 30, Image.SCALE_SMOOTH));
        JButton button = new JButton();
        //button.setPreferredSize(new Dimension(80, 50));
        button.setIcon(icon);
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new creatorMain());
    }
}
