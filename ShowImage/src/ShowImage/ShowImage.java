/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ShowImage;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.io.*;
import javax.swing.event.ListSelectionEvent;

/**
 *
 * @RAJNEESH KUMAR SHARMA 
 */
public class ShowImage extends JFrame{
    JFrame jf;
    JList l1;
    JLabel timeL, indX;
    JTextField timeF;
    DefaultListModel model;
    JPanel panel, panel1, panel2, panel3;
    JSplitPane splitpaneH, splitpaneV;
    JButton Next, Prev, Start, Stop;
    JLabel jl;
    ImageIcon img;
    JMenu file, edit;
    JMenuItem open,openf, Exit;
    JMenuBar mb;
    JFileChooser fileChooser;
    JScrollPane pane;
    
    
    
    int listCount, time = 3000, idx, LC;
    String path;
    Timer timer;

    public ShowImage() {
        jf = this;
        setSize(1080, 720);
        setVisible(true);
        setTitle("RS_PhotoViewer");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        panel =new JPanel(new BorderLayout());
        add(panel);
        
        panel1 = new JPanel(new BorderLayout());
        panel2 = new JPanel(new BorderLayout());
        panel3 = new JPanel(new FlowLayout());
        pane = new JScrollPane(panel1);
        

        splitpaneV = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitpaneV.setDividerLocation(600);
        splitpaneV.setLeftComponent(panel2);
        splitpaneV.setRightComponent(panel3);
        
        splitpaneH = new JSplitPane();
        splitpaneH.setDividerLocation(200);
        splitpaneH.setLeftComponent(pane);
        splitpaneH.setRightComponent(splitpaneV);
        
        panel.add(splitpaneH,BorderLayout.CENTER);
        
        
        
        init();
        
        fileChooser = new JFileChooser();
    }
    
    private void init(){
        mb = new JMenuBar();
        setJMenuBar(mb);
        model = new DefaultListModel();
        l1 = new JList(model);
        
        file = new JMenu("File");
        file.setCursor(new Cursor(Cursor.HAND_CURSOR));
        mb.add(file);
        edit = new JMenu("Edit");
        edit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        mb.add(edit);
        open = new JMenuItem("Open");
        file.add(open);
        open.addActionListener((ActionEvent e) -> {
            if (fileChooser.showOpenDialog(jf) == JFileChooser.APPROVE_OPTION){
                path = fileChooser.getSelectedFile().toString();
                System.out.println(fileChooser.getSelectedFile().toString());
                img = new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(780, 580, Image.SCALE_DEFAULT));
                jl.setIcon(img);
            }
        });
        openf = new JMenuItem("Open Folder");
        file.add(openf);
        openf.addActionListener((ActionEvent e) -> {
            //fileChooser.setCurrentDirectory(new java.io.File("."));
            fileChooser.setDialogTitle("Select Folder");
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            if (fileChooser.showOpenDialog(jf) == JFileChooser.APPROVE_OPTION) {
                File folder = new File(fileChooser.getSelectedFile().toString());
                File[] listOfFiles = folder.listFiles();
                model.removeAllElements();
                listCount = 0;
                for (File file1 : listOfFiles) {
                    if (file1.isFile()) {
                        int p = file1.getPath().lastIndexOf('.');
                        String ext = file1.getPath().substring(p + 1);
                        //System.out.println(file.getPath());
                        if (ext.compareTo("jpg")==0 || ext.compareTo("jpeg")==0 || ext.compareTo("png")==0 || ext.compareTo("gif")==0 || ext.compareTo("icon")==0 || ext.compareTo("tif")==0 || ext.compareTo("bmp")==0 || ext.compareTo("svg")==0) {
                            model.addElement(file1.getPath());
                            listCount++;
                            l1.setSelectedIndex(0);
                        }
                    }
                }
                LC =listCount;
                indX = new JLabel(String.valueOf(l1.getSelectedIndex()+1) + "/" + String.valueOf(LC));
                panel3.add(indX);
            }
        });
        
        
        
        panel1.add(l1,BorderLayout.CENTER);
        l1.addListSelectionListener((ListSelectionEvent e) -> {
            img = new ImageIcon(new ImageIcon(l1.getSelectedValue().toString()).getImage().getScaledInstance(780, 580, Image.SCALE_DEFAULT));
            jl.setIcon(img);
        });
        
        jl = new JLabel(img);
        jl.setCursor(new Cursor(Cursor.MOVE_CURSOR));
        panel2.add(jl);
        panel2.setBackground(Color.GRAY);
        
        //img = new ImageIcon(new ImageIcon("C:/Users/Student/Documents/NetBeansProjects/ShowImage/src/ShowImage/Barrett_M82A1.jpg").getImage().getScaledInstance(590, 384, Image.SCALE_DEFAULT));
        
        timeL = new JLabel("Slideshow Duration (in Seconds) : ");
        timeL.setCursor(new Cursor(Cursor.TEXT_CURSOR));
        panel3.add(timeL);
        
        timeF = new JTextField();
        timeF.setCursor(new Cursor(Cursor.TEXT_CURSOR));
        timeF.setColumns(4);
        panel3.add(timeF);
        
        idx = 0;
        Prev = new JButton("<<");
        Prev.setCursor(new Cursor(Cursor.HAND_CURSOR));
        Prev.addActionListener((ActionEvent e) -> {
            if (l1.getSelectedIndex()>0 && l1.getSelectedIndex()<LC){
                l1.setSelectedIndex(l1.getSelectedIndex() - 1);
                idx =l1.getSelectedIndex();
            } else {
                l1.setSelectedIndex(LC - 1);
            }
            indX.setText(String.valueOf(l1.getSelectedIndex()+1) + "/" + String.valueOf(LC));
        });
        panel3.add(Prev);
        
        Start = new JButton("Start Show");
        Start.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel3.add(Start);
        Start.addActionListener((ActionEvent e) -> {
            timer = new Timer(time, (ActionEvent x1) -> {
                Next.doClick();
            });
            timer.start();
        });
        
        Stop = new JButton("End Show");
        Stop.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel3.add(Stop);
        Stop.addActionListener((ActionEvent e) -> {
            l1.setSelectedIndex(l1.getSelectedIndex());
            timer.stop();
        });
        
        Next = new JButton(">>");
        Next.setCursor(new Cursor(Cursor.HAND_CURSOR));
        Next.addActionListener((ActionEvent e) -> {
            if (l1.getSelectedIndex()>=0 && l1.getSelectedIndex()<LC - 1){
                l1.setSelectedIndex(l1.getSelectedIndex() + 1);
                idx = l1.getSelectedIndex();
            } else {
                l1.setSelectedIndex(0);
            }
            indX.setText(String.valueOf(l1.getSelectedIndex()+1) + "/" + String.valueOf(LC));
            if (timer.isRunning()) {
                try {
                    time= Integer.parseInt(timeF.getText())*1000;
                } catch (NumberFormatException y1) {
                    time = 3000;
                }
            }
        });
        panel3.add(Next);
        
        
    }
    
    
    public static void main (String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ShowImage();
        });
    }
    
}