package crawler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.util.Map;

public class WebCrawler extends JFrame {
    private JTextField urlTextField;
    private JLabel titleLabel;
    private JTable titlesTable;
    private JButton runButton;
    private DefaultTableModel model;
    private JTextField fileSave;
    private final Font font = new Font("Courier",Font.BOLD,12);
    private final Color darkBlue = new Color(4,6,15);
    private final Color slate = new Color(3,53,62);
    private final Color skyBlue = new Color(2,148,165);
    private final Color grayBrown = new Color(167,156,147);
    private final Color probablyRed = new Color(193,64,61);

    public WebCrawler() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setTitle("Web Crawler");
        setLayout(new BorderLayout(2, 2));
        setBackground(darkBlue);
        setForeground(darkBlue);
        initUI();
    }
    private void initUI(){
        JPanel mainPanel = new JPanel(new BorderLayout(2, 2));
        mainPanel.setVisible(true);
        mainPanel.setBackground(darkBlue);

        JPanel urlTitlePanel = new JPanel(new BorderLayout(2, 2));
        urlTitlePanel.setVisible(true);
        urlTitlePanel.setBackground(darkBlue);

        JPanel urlPanel = new JPanel(new BorderLayout(2, 2));
        urlPanel.setVisible(true);
        urlPanel.setBackground(darkBlue);

        JLabel urlPrompt = new JLabel("URL:");
        urlPrompt.setForeground(grayBrown);
        urlPrompt.setVisible(true);
        urlPrompt.setPreferredSize(new Dimension(40, 25));
        urlPanel.add(urlPrompt, BorderLayout.LINE_START);

        urlTextField = new JTextField("https://www.example.com");
        urlTextField.setVisible(true);
        urlTextField.setName("UrlTextField");
        urlTextField.setPreferredSize(new Dimension(500, 25));
        urlTextField.setBackground(slate);
        urlTextField.setForeground(grayBrown);
        urlTextField.setFont(font);
        urlTextField.setBorder(BorderFactory.createLineBorder(skyBlue));
        urlTextField.setCaretColor(probablyRed);
        urlPanel.add(urlTextField, BorderLayout.CENTER);

        JPanel titlePanel = new JPanel(new BorderLayout(2, 2));
        titlePanel.setVisible(true);
        titlePanel.setBackground(darkBlue);

        JLabel titlePrompt = new JLabel("Title:");
        titlePrompt.setForeground(grayBrown);
        titlePrompt.setVisible(true);
        titlePrompt.setPreferredSize(new Dimension(40, 25));
        titlePanel.add(titlePrompt, BorderLayout.LINE_START);

        titleLabel = new JLabel("Example Domain");
        titleLabel.setForeground(grayBrown);
        titleLabel.setVisible(true);
        titleLabel.setName("TitleLabel");
        titleLabel.setPreferredSize(new Dimension(500, 25));
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        urlTitlePanel.add(urlPanel, BorderLayout.PAGE_START);
        urlTitlePanel.add(titlePanel, BorderLayout.PAGE_END);

        mainPanel.add(urlTitlePanel, BorderLayout.CENTER);

        runButton = new JButton("Parse");
        runButton.setVisible(true);
        runButton.setName("RunButton");
        runButton.setPreferredSize(new Dimension(90, 25));
        runButton.setBackground(slate);
        runButton.setForeground(probablyRed);
        runButton.setBorder(BorderFactory.createLineBorder(skyBlue));
        urlPanel.add(runButton, BorderLayout.LINE_END);

        add(mainPanel, BorderLayout.PAGE_START);


        JPanel resultPanel = new JPanel(new BorderLayout(2, 2));
        resultPanel.setVisible(true);
        resultPanel.setBackground(darkBlue);

        model = new DefaultTableModel(0, 2);
        model.setColumnIdentifiers(new String[]{"URL", "Title"});


        titlesTable = new JTable(model);
        titlesTable.setVisible(true);
        titlesTable.setName("TitlesTable");
        titlesTable.setEnabled(false);
        titlesTable.setBackground(slate);
        titlesTable.setForeground(grayBrown);
        titlesTable.setFont(font);
        titlesTable.getTableHeader().setBackground(slate);
        titlesTable.getTableHeader().setForeground(grayBrown);
        titlesTable.getTableHeader().setFont(font);
        titlesTable.getTableHeader().setBorder(BorderFactory.createEmptyBorder());
        JScrollPane scrollPane = new JScrollPane(titlesTable);
        scrollPane.setVisible(true);
        scrollPane.getViewport().setBackground(darkBlue);
        scrollPane.setBorder(BorderFactory.createLineBorder(skyBlue));
        scrollPane.getVerticalScrollBar().setBackground(slate);
        resultPanel.add(scrollPane, BorderLayout.CENTER);
        resultPanel.setBorder(BorderFactory.createEmptyBorder());
        resultPanel.setForeground(darkBlue);

        JPanel savePanel = new JPanel(new BorderLayout(2,2));
        JLabel export = new JLabel("Export: ");
        fileSave = new JTextField();
        fileSave.setName("ExportUrlTextField");

        fileSave.setPreferredSize(new Dimension(500,25));
        JButton saveButton = new JButton("Save");
        saveButton.setName("ExportButton");
        savePanel.setBorder(BorderFactory.createEmptyBorder());
        savePanel.setBackground(darkBlue);
        export.setForeground(grayBrown);
        savePanel.setVisible(true);
        export.setVisible(true);
        fileSave.setVisible(true);
        savePanel.add(export, BorderLayout.LINE_START);
        savePanel.add(fileSave,BorderLayout.CENTER);
        savePanel.add(saveButton,BorderLayout.LINE_END);

        add(resultPanel, BorderLayout.CENTER);
        add(savePanel, BorderLayout.SOUTH);

        runButton.addActionListener(actionEvent -> runButton());
        saveButton.addActionListener(actionEvent -> {
            try {
                saveButton();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
        pack();
    }
    private void runButton(){
        // Rows shift down by 1 after removal, so it's necessary to remove them from the end
        for (int i = titlesTable.getRowCount() - 1; i >= 0; i--) {
            model.removeRow(i);
        }
        titleLabel.setText("Please wait, parsing data...");
        urlTextField.setEnabled(false);
        runButton.setEnabled(false);
        Thread t = new Thread(() -> {
            Parser parser = new Parser(urlTextField.getText());
            try {
                for (Map.Entry<String, String> pair : parser.getLinksAndTitles().entrySet()) {
                    model.addRow(new String[]{pair.getKey(), pair.getValue()});
                }
                titleLabel.setText(parser.getTitle());
            } catch (FileNotFoundException | MalformedURLException ignored) {
                titleLabel.setText("Provided URL is invalid.");
            } catch (ConnectException ignored) {
                titleLabel.setText("Connection refused.");
            } catch (IOException e) {
                e.printStackTrace();
            }
            urlTextField.setEnabled(true);
            runButton.setEnabled(true);
        });
        t.start();
    }
    private void saveButton() throws FileNotFoundException {
        //Save into file
        String fileName = fileSave.getText();
        StringBuilder fileContents = new StringBuilder();
        for(int i = 0; i < titlesTable.getRowCount(); i++) {
            fileContents.append((String) titlesTable.getModel().getValueAt(i, 0));
            fileContents.append("\n").append((String) titlesTable.getModel().getValueAt(i, 1)).append("\n");
        }
        try (PrintWriter out = new PrintWriter(fileName)) {
            out.print(fileContents);
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }
}


