package tronlikegame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author durza9390
 */
public class Launcher extends JFrame {

    private JTextField hostname;
    private JLabel labelStatus;
    private JLabel labelPlayerName;
    private JLabel labelHostname;
    private JLabel labelPort;
    private JButton launchBtn;
    private JTextField playerName;
    private JTextField port;
    private JLabel status;

    private final String DEFAULT_PLAYERNAME = "Player 1";
    private final String RESSOURCES_CHARACTERS = "ressources/characters.dat";

    /**
     * Creates new form Launcher
     */
    public Launcher() {
        initComponents();
        launchBtn.addActionListener((ActionEvent e) -> {
            try {
                WindowGame game = new WindowGame(playerName.getText(), hostname.getText(), Integer.parseInt(port.getText()));
                status.setText("Connected");
            } catch (Exception ex) {
                ex.printStackTrace();
                status.setText("Error: " + ex.getMessage());
            }
        });
    }

    private String getRandomLine(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        ArrayList<String> lines = new ArrayList<>();

        // Add lines to ArrayList
        String line;
        while ((line = reader.readLine()) != null)
            lines.add(line);

        // Random line
        return lines.get(new Random().nextInt(lines.size()));
    }

    private void initComponents() {
        String localPlayerName = DEFAULT_PLAYERNAME;

        if (new File(RESSOURCES_CHARACTERS).exists()) {
            try {
                localPlayerName = getRandomLine(RESSOURCES_CHARACTERS);
                if (localPlayerName.length() < 2) {
                    localPlayerName = DEFAULT_PLAYERNAME;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        labelPlayerName = new JLabel();
        labelHostname = new JLabel();
        playerName = new JTextField();
        hostname = new JTextField();
        labelPort = new JLabel();
        port = new JTextField();
        labelStatus = new JLabel();
        status = new JLabel();
        launchBtn = new JButton();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        labelPlayerName.setText("Player name");

        labelHostname.setText("Hostname");

        playerName.setText(localPlayerName);
        playerName.addActionListener(evt -> playerNameActionPerformed(evt));

        hostname.setText("localhost");

        labelPort.setText("Port");

        port.setText("8000");

        labelStatus.setText("Status");

        status.setText("not connected");

        launchBtn.setText("Launch");

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(labelPlayerName)
                                .addGap(12, 12, 12)
                                .addComponent(playerName, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(labelHostname)
                                .addGap(28, 28, 28)
                                .addComponent(hostname, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(labelPort)
                                .addGap(70, 70, 70)
                                .addComponent(port, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(labelStatus)
                                .addGap(53, 53, 53)
                                .addComponent(status, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(157, 157, 157)
                                .addComponent(launchBtn))
        );

        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(2, 2, 2)
                                                .addComponent(labelPlayerName))
                                        .addComponent(playerName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(12, 12, 12)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(2, 2, 2)
                                                .addComponent(labelHostname))
                                        .addComponent(hostname, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(12, 12, 12)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(labelPort)
                                        .addComponent(port, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(7, 7, 7)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(labelStatus)
                                        .addComponent(status))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(launchBtn)
                                .addContainerGap())
        );

        pack();

        setSize(600, 480);
    }

    private void playerNameActionPerformed(ActionEvent evt) {
        // TODO add your handling code here:
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Launcher.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Launcher.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Launcher.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Launcher.class.getName()).log(Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new Launcher().setVisible(true));
    }

}
