import javax.print.attribute.standard.PresentationDirection;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.URL;
import java.util.logging.Level;


public class creatorMain {

    private static JButton selectedButton = null;
    private JContentArea currentArea;

    public creatorMain() {

        JFrame frame = new JFrame();
        frame.setFocusable(true);
        Dimension screenDimensions = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(screenDimensions.width / 2 - 660, screenDimensions.height / 2 - 410);
        //frame.setSize(1300,800);
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
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem openItem = new JMenuItem("Open");
        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(quitItem);
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
        JButton aicdButton = createButton("Images/acid.png");
        JButton nodeButton = createButton("Images/Node.png");
        JButton vacuumButton = createButton("Images/vacuumBlast1RC.png");
        JButton tinyMouseButton = createButton("Images/tinyMouse1_RC.png");
        JButton yarnballButton = createButton("Images/yarnBall-2C.png");
        JButton squirrelButton = createButton("Images/squirrelC.png");
        JButton rsquirrelButton = createButton("Images/r_squirrelC.png");
        JButton deleteButton = createButton("Images/X.png");

        toolbar.add(floorButton);
        toolbar.add(wallButton);
        toolbar.add(aicdButton);
        toolbar.add(nodeButton);
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
            if (selectedButton != null) {
                selectedButton.setSelected(false);
            }
            selectButton.setSelected(true);
            currentArea.SetTool(Tools.SELECT);
            selectedButton = selectButton;
        });
        lineButton.addActionListener(e -> {
            if (selectedButton != null) {
                selectedButton.setSelected(false);
            }
            lineButton.setSelected(true);
            currentArea.SetTool(Tools.LINE);
            selectedButton = lineButton;
        });
        floorButton.addActionListener(e -> {
            if (selectedButton != null) {
                selectedButton.setSelected(false);
            }
            floorButton.setSelected(true);
            currentArea.SetTool(Tools.FLOOR);
            selectedButton = floorButton;
        });
        wallButton.addActionListener(e -> {
            if (selectedButton != null) {
                selectedButton.setSelected(false);
            }
            wallButton.setSelected(true);
            currentArea.SetTool(Tools.WALL);
            selectedButton = wallButton;
        });
        nodeButton.addActionListener(e -> {
            if (selectedButton != null) {
                selectedButton.setSelected(false);
            }
            nodeButton.setSelected(true);
            currentArea.SetTool(Tools.NODE);
            selectedButton = nodeButton;
        });
        vacuumButton.addActionListener(e -> {
            if (selectedButton != null) {
                selectedButton.setSelected(false);
            }
            vacuumButton.setSelected(true);
            currentArea.SetTool(Tools.VACUUM);
            selectedButton = vacuumButton;
        });
        tinyMouseButton.addActionListener(e -> {
            if (selectedButton != null) {
                selectedButton.setSelected(false);
            }
            tinyMouseButton.setSelected(true);
            currentArea.SetTool(Tools.TINYMOUSE);
            selectedButton = tinyMouseButton;
        });
        yarnballButton.addActionListener(e -> {
            if (selectedButton != null) {
                selectedButton.setSelected(false);
            }
            yarnballButton.setSelected(true);
            currentArea.SetTool(Tools.YARNBALL);
            selectedButton = yarnballButton;
        });
        squirrelButton.addActionListener(e -> {
            if (selectedButton != null) {
                selectedButton.setSelected(false);
            }
            squirrelButton.setSelected(true);
            currentArea.SetTool(Tools.SQUIRREL);
            selectedButton = squirrelButton;
        });
        rsquirrelButton.addActionListener(e -> {
            if (selectedButton != null) {
                selectedButton.setSelected(false);
            }
            rsquirrelButton.setSelected(true);
            currentArea.SetTool(Tools.RSQUIRREL);
            selectedButton = rsquirrelButton;
        });
        deleteButton.addActionListener(e -> {
            if (selectedButton == selectButton) {
                currentArea.delete();
            }
        });
        aicdButton.addActionListener(e -> {
            if (selectedButton != null) {
                selectedButton.setSelected(false);
            }
            aicdButton.setSelected(true);
            currentArea.SetTool(Tools.ACID);
            selectedButton = aicdButton;
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

        saveItem.addActionListener(e -> {
            LevelConfigurationObject configObj = currentArea.displayList.getConfigObj();
            try {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showSaveDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    FileOutputStream fStream = new FileOutputStream(fileChooser.getSelectedFile());
                    ObjectOutputStream oStream = new ObjectOutputStream(fStream);
                    oStream.writeObject(configObj);
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        });

        openItem.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            //fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("lvl"));
            //fileChooser.setCurrentDirectory(new File("../../"));
            int result = fileChooser.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    FileInputStream fStream = new FileInputStream(fileChooser.getSelectedFile());
                    ObjectInputStream oStream = new ObjectInputStream(fStream);
                    LevelConfigurationObject configObj = (LevelConfigurationObject) oStream.readObject();

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

                    currentArea.loadConfigObj(configObj);

                } catch (IOException | ClassNotFoundException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });


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
