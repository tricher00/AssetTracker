import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Main {
	private JPanel contentPane;
    private LoginPanel login;
    private DevicesPanel devices;

    private void displayGUI()
    {
        JFrame frame = new JFrame("Asset Tracker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(
            BorderFactory.createEmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new CardLayout());

        login = new LoginPanel(contentPane); 

        contentPane.add(login, "Login");

        frame.getContentPane().add(contentPane, BorderLayout.CENTER);
        frame.getContentPane().setPreferredSize(new Dimension(1000, 500));
        frame.pack();   
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    public static void main(String... args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                new Main().displayGUI();
            }
        });
    }
}
