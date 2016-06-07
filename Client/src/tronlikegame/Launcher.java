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
        super("TRONTRON");

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

    /**
     * Get a random line from a text file
     *
     * @param filename Filename path
     * @return A random string from the given text file
     * @throws IOException
     */
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

    /**
     * Initalize the components for the launcher
     */
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

        playerName = new JTextField(localPlayerName);
        playerName.addActionListener(evt -> playerNameActionPerformed(evt));

        hostname = new JTextField("localhost");
        port = new JTextField("8000");
        status = new JLabel("not connected");
        status.setForeground(Color.WHITE);
        status.setBorder(new EmptyBorder(5, 5, 5, 5));

        launchBtn = new JButton("Launch");
        launchBtn.setPreferredSize(new Dimension(200, 52));
        launchBtn.setBackground(Color.BLACK);
        launchBtn.setFont(new Font("Sans", Font.PLAIN, 14));
        launchBtn.setForeground(Color.WHITE);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel mainForm = new JPanel();

        // Pair of labels-inputs
        String[] labels = {"Player name: ", "Hostname: ", "Port: ", "Status: "};
        JComponent[] inputs = {playerName, hostname, port, status};

        int numPairs = Math.min(labels.length, inputs.length);

        JPanel p = new JPanel(new SpringLayout());
        for (int i = 0; i < numPairs; ++i) {
            JLabel l = new JLabel(labels[i], JLabel.TRAILING);
            l.setFont(new Font("Sans", Font.PLAIN, 14));
            l.setForeground(Color.WHITE);

            p.add(l);
            p.add(inputs[i]);
        }

        // Lay out the panel
        SpringUtilities.makeCompactGrid(p,
                numPairs, 2, //rows, cols
                6, 6,        //initX, initY
                6, 6);       //xPad, yPad

        p.setBackground(Color.BLACK);

        p.setPreferredSize(new Dimension(320, 160));

        mainForm.setBackground(Color.BLACK);
        mainForm.add(p);

        Container pane = getContentPane();
        pane.setBackground(Color.BLACK);
        pane.add(mainForm, BorderLayout.CENTER);
        pane.add(launchBtn, BorderLayout.PAGE_END);
        pane.add(panelLogo, BorderLayout.NORTH);

        pack();

        setResizable(false);
        setSize(600, 460);
        setLocationRelativeTo(null); // Center the launcher
    }

    private void playerNameActionPerformed(ActionEvent evt) {
        // TODO add your handling code here:
    }

    /**
     * The main for the client application
     *
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
