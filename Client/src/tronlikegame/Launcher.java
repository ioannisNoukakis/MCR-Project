package tronlikegame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
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
    private JButton launchBtn;
    private JTextField playerName;
    private JTextField port;
    private JLabel status;

    private final String DEFAULT_PLAYERNAME = "Player 1";
    private final String RESSOURCES_CHARACTERS = "ressources/characters.dat";
    private final String RESSOURCES_LOGO = "ressources/logo.png";

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

        ImageIcon icon = new ImageIcon(RESSOURCES_LOGO);
        JLabel labelLogo = new JLabel();
        labelLogo.setBackground(new Color(0, 0, 0, 0));
        labelLogo.setHorizontalAlignment(SwingConstants.CENTER);
        labelLogo.setIcon(icon);

        ImagePanel panelLogo = new ImagePanel(RESSOURCES_LOGO);
        panelLogo.setPreferredSize(new Dimension(300, 160));
        panelLogo.setBackground(Color.BLACK);

        playerName = new JTextField();
        hostname = new JTextField();
        port = new JTextField();
        status = new JLabel();
        launchBtn = new JButton();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        playerName.setText(localPlayerName);
        playerName.addActionListener(evt -> playerNameActionPerformed(evt));
        hostname.setText("localhost");
        port.setText("8000");
        status.setText("not connected");

        launchBtn.setText("Launch");
        launchBtn.setPreferredSize(new Dimension(200, 50));
        launchBtn.setBackground(Color.DARK_GRAY);

        JPanel mainForm = new JPanel();

        String[] labels = {"Player name: ", "Hostname: ", "Port: ", "Status"};
        JComponent[] inputs = {playerName, hostname, port, status};

        int numPairs = Math.min(labels.length, inputs.length);

        JPanel p = new JPanel(new SpringLayout());
        for (int i = 0; i < numPairs; ++i) {
            JLabel l = new JLabel(labels[i], JLabel.TRAILING);

            p.add(l);
            p.add(inputs[i]);
        }

        // Lay out the panel.
        SpringUtilities.makeCompactGrid(p,
                numPairs, 2, //rows, cols
                6, 6,        //initX, initY
                6, 6);       //xPad, yPad

        p.setBackground(Color.DARK_GRAY);

        p.setPreferredSize(new Dimension(320, 180));

        mainForm.setBackground(Color.BLACK);
        mainForm.add(p);
        p.setBorder(new EmptyBorder(10, 10, 10, 10));

        Container pane = getContentPane();
        pane.setBackground(Color.BLACK);
        pane.add(mainForm, BorderLayout.CENTER);
        pane.add(launchBtn, BorderLayout.PAGE_END);
        pane.add(panelLogo, BorderLayout.NORTH);

        pack();

        setSize(600, 480);
        setLocationRelativeTo(null); // Center the launcher
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
