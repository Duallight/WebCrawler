package crawler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class WebCrawler extends JFrame {
    private JTextField urlTextField;
    private JButton runButton;
    private JTextField fileSave;
    private JTextField workerField;
    private JTextField depthField;
    private JTextField timeField;
    private JCheckBox timeEnabled;
    private JCheckBox depthEnabled;
    private final Font font = new Font("Courier",Font.BOLD,12);
    private final Color darkBlue = new Color(4,6,15);
    private final Color slate = new Color(3,53,62);
    private final Color skyBlue = new Color(2,148,165);
    private final Color grayBrown = new Color(167,156,147);
    private final Color mintIsh = new Color(88,255,194);

    public WebCrawler() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setTitle("Web Crawler");
        setLayout(new BorderLayout(2, 2));
        getContentPane().setBackground(darkBlue);
        setResizable(false);
        initUI();
    }
    private void initUI(){
        ImageIcon unchecked = new ImageIcon("/home/duallight/IdeaProjects/Web Crawler/Web Crawler/task/src/crawler/unchecked.png");
        ImageIcon checked = new ImageIcon("/home/duallight/IdeaProjects/Web Crawler/Web Crawler/task/src/crawler/checked.png");
        JPanel mainPanel = new JPanel(new BorderLayout(0, 2));
        mainPanel.setVisible(true);
        mainPanel.setBackground(darkBlue);

        JPanel topPanel = new JPanel(new BorderLayout(2, 2));
        topPanel.setVisible(true);
        topPanel.setBackground(darkBlue);

        JPanel urlPanel = new JPanel(new BorderLayout(2, 2));
        urlPanel.setVisible(true);
        urlPanel.setBackground(darkBlue);

        JLabel urlPrompt = new JLabel("Start URL:");
        urlPrompt.setForeground(grayBrown);
        urlPrompt.setVisible(true);
        urlPrompt.setPreferredSize(new Dimension(150, 25));
        urlPanel.add(urlPrompt, BorderLayout.LINE_START);

        urlTextField = new JTextField("https://www.example.com");
        urlTextField.setVisible(true);
        urlTextField.setName("UrlTextField");
        urlTextField.setPreferredSize(new Dimension(500, 25));
        urlTextField.setBackground(slate);
        urlTextField.setForeground(grayBrown);
        urlTextField.setFont(font);
        urlTextField.setBorder(BorderFactory.createLineBorder(skyBlue));
        urlTextField.setCaretColor(mintIsh);

        runButton = new JButton("Parse");
        runButton.setVisible(true);
        runButton.setName("RunButton");
        runButton.setPreferredSize(new Dimension(90, 25));
        runButton.setBackground(slate);
        runButton.setForeground(mintIsh);
        runButton.setBorder(BorderFactory.createLineBorder(skyBlue));
        urlPanel.add(runButton, BorderLayout.LINE_END);
        urlPanel.add(urlTextField, BorderLayout.CENTER);

        JPanel workerPanel = new JPanel(new BorderLayout(2, 2));
        workerPanel.setVisible(true);
        workerPanel.setBackground(darkBlue);

        JLabel workerPrompt = new JLabel("Workers:");
        workerPrompt.setForeground(grayBrown);
        workerPrompt.setVisible(true);
        workerPrompt.setPreferredSize(new Dimension(150, 25));
        workerField = new JTextField("5");
        workerField.setVisible(true);
        workerField.setPreferredSize(new Dimension(610,25));
        workerField.setBackground(slate);
        workerField.setForeground(grayBrown);
        workerField.setFont(font);
        workerField.setBorder(BorderFactory.createLineBorder(skyBlue));
        workerField.setCaretColor(mintIsh);

        JPanel depthPanel = new JPanel(new BorderLayout(2,2));
        JLabel depthText = new JLabel("Maximum depth:"); // add to line start
        depthField = new JTextField("50");
        depthEnabled = new JCheckBox("Enabled");
        depthEnabled.setPreferredSize(new Dimension(90,25));
        depthPanel.setVisible(true);

        //Add Theming to new rows
        depthPanel.setBackground(darkBlue);
        depthText.setForeground(grayBrown);
        depthText.setVisible(true);
        depthText.setPreferredSize(new Dimension(150,25));
        depthField.setVisible(true);
        depthField.setPreferredSize(new Dimension(500,25));
        depthField.setBackground(slate);
        depthField.setForeground(grayBrown);
        depthField.setFont(font);
        depthField.setBorder(BorderFactory.createLineBorder(skyBlue));
        depthField.setCaretColor(mintIsh);
        depthEnabled.setIcon(unchecked);
        depthEnabled.setSelectedIcon(checked);
        depthEnabled.setForeground(mintIsh);
        depthEnabled.setBackground(slate);
        depthEnabled.setBorder(BorderFactory.createLineBorder(skyBlue));
        depthEnabled.setBorderPainted(true);
        depthPanel.add(depthText, BorderLayout.LINE_START);
        depthPanel.add(depthField, BorderLayout.CENTER);
        depthPanel.add(depthEnabled,BorderLayout.LINE_END);

        workerPanel.add(workerPrompt, BorderLayout.LINE_START);
        workerPanel.add(workerField, BorderLayout.CENTER);
        topPanel.add(urlPanel, BorderLayout.PAGE_START);
        topPanel.add(workerPanel, BorderLayout.CENTER);
        topPanel.add(depthPanel,BorderLayout.PAGE_END);

        //This panel will have the time limit row, elapsed time row and parsed pages row
        JPanel middlePanel = new JPanel(new BorderLayout(2,2));
        JPanel timeLimit = new JPanel(new BorderLayout(2,2));
        JPanel elapsedTime = new JPanel(new BorderLayout(2,2));
        JPanel parsedPages = new JPanel(new BorderLayout(2,2));
        middlePanel.setBackground(darkBlue);
        timeLimit.setBackground(darkBlue);
        elapsedTime.setBackground(darkBlue);
        parsedPages.setBackground(darkBlue);
        //*********************************************************************************************************************************/
        JLabel timeLimitLabel = new JLabel("Time Limit (S):"); // add to line start
        timeField = new JTextField("150");
        timeEnabled = new JCheckBox("Enabled");
        timeEnabled.setPreferredSize(new Dimension(90,25));
        timeLimit.setVisible(true);

        //Add Theming to new rows
        timeLimit.setBackground(darkBlue);
        timeLimitLabel.setForeground(grayBrown);
        timeLimitLabel.setVisible(true);
        timeLimitLabel.setPreferredSize(new Dimension(150,25));
        timeField.setVisible(true);
        timeField.setPreferredSize(new Dimension(550,25));
        timeField.setBackground(slate);
        timeField.setForeground(grayBrown);
        timeField.setFont(font);
        timeField.setBorder(BorderFactory.createLineBorder(skyBlue));
        timeField.setCaretColor(mintIsh);
        timeEnabled.setIcon(unchecked);
        timeEnabled.setSelectedIcon(checked);
        timeEnabled.setForeground(mintIsh);
        timeEnabled.setBackground(slate);
        timeEnabled.setBorder(BorderFactory.createLineBorder(skyBlue));
        timeEnabled.setBorderPainted(true);
        timeLimit.add(timeLimitLabel, BorderLayout.LINE_START);
        timeLimit.add(timeEnabled,BorderLayout.LINE_END);
        timeLimit.add(timeField, BorderLayout.CENTER);

        //****************************************************************************************************************************/
        //Elapsed time panel
        JLabel elapsedTimeLabel = new JLabel("Elapsed Time:");
        elapsedTimeLabel.setPreferredSize(new Dimension(150,25));
        elapsedTimeLabel.setForeground(grayBrown);
        JLabel elapsedTimeCounter = new JLabel("0:00");
        elapsedTimeCounter.setPreferredSize(new Dimension(150,25));
        elapsedTimeCounter.setForeground(grayBrown);
        elapsedTime.add(elapsedTimeLabel,BorderLayout.LINE_START);
        elapsedTime.add(elapsedTimeCounter,BorderLayout.CENTER);
        middlePanel.add(timeLimit,BorderLayout.PAGE_START);
        middlePanel.add(elapsedTime,BorderLayout.PAGE_END);
        //Parsed Pages panel
        JLabel parsedLabel = new JLabel("Parsed Pages:");
        parsedLabel.setPreferredSize(new Dimension(150,25));
        parsedLabel.setForeground(grayBrown);
        JLabel parsedCounter = new JLabel("0");
        parsedCounter.setPreferredSize(new Dimension(150,25));
        parsedCounter.setForeground(grayBrown);
        parsedPages.add(parsedLabel,BorderLayout.LINE_START);
        parsedPages.add(parsedCounter,BorderLayout.CENTER);

        //Export Panel
        JPanel savePanel = new JPanel(new BorderLayout(2,2));
        savePanel.setBackground(darkBlue);
        JLabel export = new JLabel("Export: ");
        export.setPreferredSize(new Dimension(150,25));
        fileSave = new JTextField();
        fileSave.setName("ExportUrlTextField");
        fileSave.setPreferredSize(new Dimension(500,25));
        fileSave.setBackground(slate);
        fileSave.setForeground(grayBrown);
        fileSave.setBorder(BorderFactory.createLineBorder(skyBlue));
        fileSave.setCaretColor(mintIsh);
        fileSave.setFont(font);
        JButton saveButton = new JButton("Save");
        saveButton.setName("ExportButton");
        saveButton.setPreferredSize(new Dimension(90, 25));
        saveButton.setBackground(slate);
        saveButton.setForeground(mintIsh);
        saveButton.setBorder(BorderFactory.createLineBorder(skyBlue));
        savePanel.setBorder(BorderFactory.createEmptyBorder());
        savePanel.setBackground(darkBlue);
        export.setForeground(grayBrown);
        savePanel.setVisible(true);
        export.setVisible(true);
        fileSave.setVisible(true);
        savePanel.add(export, BorderLayout.LINE_START);
        savePanel.add(fileSave,BorderLayout.CENTER);
        savePanel.add(saveButton,BorderLayout.LINE_END);


        JPanel endPanel = new JPanel(new BorderLayout(2,2));
        endPanel.setBackground(darkBlue);
        endPanel.add(parsedPages,BorderLayout.PAGE_START);
        endPanel.add(savePanel,BorderLayout.PAGE_END);

        mainPanel.add(topPanel, BorderLayout.PAGE_START);
        mainPanel.add(middlePanel,BorderLayout.LINE_START);
        mainPanel.add(endPanel,BorderLayout.PAGE_END);
        mainPanel.setBackground(darkBlue);


        add(mainPanel, BorderLayout.PAGE_START);

        //Make text fields only accept numbers
        workerField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                char c = ke.getKeyChar();
                workerField.setEditable(((c >= '0') && (c <= '9')) || (c == KeyEvent.VK_BACK_SPACE));
            }
        });
        timeField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                char c = ke.getKeyChar();
                timeField.setEditable(((c >= '0') && (c <= '9')) || (c == KeyEvent.VK_BACK_SPACE));
            }
        });
        depthField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                char c = ke.getKeyChar();
                depthField.setEditable(((c >= '0') && (c <= '9')) || (c == KeyEvent.VK_BACK_SPACE));
            }
        });
        runButton.addActionListener(actionEvent -> runButton());
        saveButton.addActionListener(actionEvent -> saveButton());
        pack();
    }
    private void runButton(){
        /*// Rows shift down by 1 after removal, so it's necessary to remove them from the end
        urlTextField.setEnabled(false);
        runButton.setEnabled(false);
        workerField.setEnabled(false);
        depthField.setEnabled(false);
        timeField.setEnabled(false);
        depthEnabled.setEnabled(false);
        timeEnabled.setEnabled(false);
        Thread t = new Thread(() -> {
            Parser parser = new Parser(urlTextField.getText(),Integer.parseInt(workerField.getText()), depthEnabled.isSelected(), timeEnabled.isSelected(), Integer.parseInt(depthField.getText()), Integer.parseInt(timeField.getText()));
            try {
                for (Map.Entry<String, String> pair : parser.getLinksAndTitles().entrySet()) {
                    model.addRow(new String[]{pair.getKey(), pair.getValue()});
                }
            } catch (FileNotFoundException | MalformedURLException ignored) {
                titleLabel.setText("Provided URL is invalid.");
            } catch (ConnectException ignored) {
                titleLabel.setText("Connection refused.");
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            urlTextField.setEnabled(true);
            runButton.setEnabled(true);
            workerField.setEnabled(true);
            depthField.setEnabled(true);
            timeField.setEnabled(true);
            depthEnabled.setEnabled(true);
            timeEnabled.setEnabled(true);
        });
        t.start();
*/
    }
    private void saveButton() {
        //Save into file
        String fileName = fileSave.getText();
        StringBuilder fileContents = new StringBuilder();
        /*for(int i = 0; i < titlesTable.getRowCount(); i++) {
            fileContents.append((String) titlesTable.getModel().getValueAt(i, 0));
            fileContents.append("\n").append((String) titlesTable.getModel().getValueAt(i, 1)).append("\n");
        }*/
        try (PrintWriter out = new PrintWriter(fileName)) {
            out.print(fileContents);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(new JFrame(), e.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}


