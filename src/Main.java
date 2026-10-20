public class Main {

    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(() -> {

            JobSyncGUI gui = new JobSyncGUI();
            gui.setVisible(true);

        });
    }
}