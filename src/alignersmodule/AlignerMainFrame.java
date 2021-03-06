/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alignersmodule;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.DefaultCaret;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author saima
 */
public class AlignerMainFrame extends javax.swing.JFrame {
    
    private Map<String, List<String>> commandMap;
    private Map<String, Aligner> alignerMap;
    private JFrame settingsFrame;
    private static String outputFilePath;
    private static String inputFilePath;
    private static String refFilePath;
    /**
     * Creates new form MainForm
     */
    public AlignerMainFrame() {
        commandMap = new HashMap<String, List<String>>();
        alignerMap = new HashMap<String, Aligner>();
        outputFilePath = "";
        initComponents();
        setHashMapForCommands();
        loadToolsFromXml();
        /*try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        int w = this.getSize().width;
        int h = this.getSize().height;
        int x = (dim.width - w) / 2;
        int y = (dim.height - h) / 2;

        this.setLocation(x, y);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        doneButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        toolsPanel = new javax.swing.JPanel();
        checkboxesPanel = new javax.swing.JPanel();
        buttonsPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sequence Alignment");

        doneButton.setText("Done");
        doneButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                doneButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        toolsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder("Mapping Tools"), "Sequence Aligners", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ubuntu", 0, 15))); // NOI18N
        toolsPanel.setLayout(new java.awt.GridLayout(1, 0));

        checkboxesPanel.setLayout(new java.awt.GridLayout(1, 0));
        toolsPanel.add(checkboxesPanel);

        buttonsPanel.setLayout(new java.awt.GridLayout(1, 0));
        toolsPanel.add(buttonsPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(toolsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(doneButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cancelButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(toolsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(doneButton)
                    .addComponent(cancelButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void doneButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_doneButtonActionPerformed
        // TODO add your handling code here:
        RunAligners runAlignersObj = new RunAligners(alignerMap);
        outputFilePath = runAlignersObj.runAllAligners();
        this.dispose();
    }//GEN-LAST:event_doneButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        // TODO add your handling code here:
        outputFilePath = "Program cancelled";
        this.dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed
    
    private void setHashMapForCommands()
    {
        List<String> bwaCmd = new ArrayList<String>();
        bwaCmd.add("bwa index -p bwa-index " + refFilePath);
        bwaCmd.add("bwa aln bwa-index " + inputFilePath + " > bwa-output.sai");
        bwaCmd.add("bwa samse bwa-index bwa-output.sai " + inputFilePath + " > bwa-output.sam");
        
        List<String> bwamemCmd = new ArrayList<String>();
        bwamemCmd.add("bwa index -p bwamem-index " + refFilePath);
        bwamemCmd.add("bwa mem bwamem-index " + inputFilePath + " > bwamem-output.sam");
        
        List<String> bowtie2Cmd = new ArrayList<String>();
        bowtie2Cmd.add("bowtie2-build " + refFilePath + " bowtie2-index");
        bowtie2Cmd.add("bowtie2 -x bowtie2-index -U " + inputFilePath + " -S bowtie2-output.sam");
        
        commandMap.put("BWA", bwaCmd);
        commandMap.put("BWA-MEM", bwamemCmd);
        commandMap.put("Bowtie2", bowtie2Cmd);
    }
    
    private void settingsButtonActionPerformed(final java.awt.event.ActionEvent evt) {                                             
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                settingsFrame = new JFrame("Settings");
                settingsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                panel.setOpaque(true);
                
                JButton fromButton = (JButton)evt.getSource();
                final String toolName = (String)fromButton.getClientProperty("toolName");
                String cmdText = alignerMap.get(toolName).getRunCommand();
                
                final JTextArea textArea = new JTextArea(20, 60);
                textArea.setText(cmdText);
                textArea.setWrapStyleWord(true);
                textArea.setEditable(true);
                Font font = new Font("SANS_SERIF", Font.PLAIN, 13);
                textArea.setFont(font);
                JScrollPane scroller = new JScrollPane(textArea);
                scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
                scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                
                JButton button = new JButton("Save");
                button.setAlignmentX(Component.CENTER_ALIGNMENT);
                DefaultCaret caret = (DefaultCaret) textArea.getCaret();
                caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
                panel.add(scroller);
                panel.add(button);
                
                button.addActionListener(new java.awt.event.ActionListener() 
                {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent evt) 
                    {
                        alignerMap.get(toolName).setRunCommand(textArea.getText());
                        settingsFrame.dispose();
                    }
                });

                settingsFrame.getContentPane().add(BorderLayout.CENTER, panel);
                settingsFrame.pack();
                settingsFrame.setLocationByPlatform(true);
                settingsFrame.setVisible(true);
                settingsFrame.setResizable(false);
            }
        });
    }
    
    private void loadToolsFromXml()
    {
        try {
            File inputFile = new File("/home/saima/NetBeansProjects/AlignersModule/AlignersConfiguration.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputFile);
            document.getDocumentElement().normalize();
            NodeList nodeList = document.getElementsByTagName("tool");
            
            ((GridLayout)checkboxesPanel.getLayout()).setColumns(1);
            ((GridLayout)checkboxesPanel.getLayout()).setRows(0);
            
            ((GridLayout)buttonsPanel.getLayout()).setColumns(1);
            ((GridLayout)buttonsPanel.getLayout()).setRows(0);
            
            for (int i = 0; i < nodeList.getLength(); i++) 
            {
                Node node = nodeList.item(i);
                if (node instanceof Element) {
                    Element element = (Element) node;
                    String toolName = element.getElementsByTagName("name").item(0).getTextContent().trim();
                    String directoryPath = element.getElementsByTagName("directory").item(0).getTextContent().trim();
                    //System.out.println(toolName);
                    JCheckBox check = new JCheckBox(toolName);
                    JButton settingsButton = new JButton("Change Settings");
                    settingsButton.putClientProperty("toolName", toolName);
                    
                    checkboxesPanel.add(check);
                    //checkboxesPanel.revalidate();
                    //checkboxesPanel.repaint();
                    buttonsPanel.add(settingsButton);
                    //buttonsPanel.revalidate();
                    //buttonsPanel.repaint();
                    Aligner aligner = new Aligner();
                    aligner.setToolName(toolName);
                    aligner.setDirectoryPath(directoryPath);
                    if(commandMap.get(toolName) == null)
                        aligner.setRunCommand("");
                    else
                        aligner.createRunCommand(commandMap.get(toolName));
                    aligner.setCheckbox(check);                   
                    
                    settingsButton.addActionListener(new java.awt.event.ActionListener() 
                    {
                        @Override
                        public void actionPerformed(java.awt.event.ActionEvent evt) 
                        {
                            settingsButtonActionPerformed(evt);
                        }
                    });
                    
                    alignerMap.put(toolName, aligner);
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        //inputFilePath = "/home/saima/bowtie2-2.2.9/example/reads/reads_1.fq";
        //refFilePath = "/home/saima/bowtie2-2.2.9/example/reference/lambda_virus.fa";
        inputFilePath = args[0];
        refFilePath = args[1];
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AlignerMainFrame().setVisible(true);
            }
        });
        
        while (outputFilePath == null || outputFilePath.equals("")) {
            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        System.out.println(outputFilePath);
    }
    
    
    public String getMainFrame(String inputFilePath, String refFilePath) {
        this.inputFilePath = inputFilePath;
        this.refFilePath = refFilePath;
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AlignerMainFrame().setVisible(true);
            }
        });
        
        while (outputFilePath == null || outputFilePath.equals("")) {
            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        return outputFilePath;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel buttonsPanel;
    private javax.swing.JButton cancelButton;
    private javax.swing.JPanel checkboxesPanel;
    private javax.swing.JButton doneButton;
    private javax.swing.JPanel toolsPanel;
    // End of variables declaration//GEN-END:variables
}
