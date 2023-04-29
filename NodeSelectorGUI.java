import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NodeSelectorGUI {

    public String curSelected = "";
    public String prevSelected = "";
    JLabel nodeTextLabel; 

    public class SetSelectedNodeAction implements ActionListener {

        private NodeSelectorGUI gui;
        private NodeButton button;

        public SetSelectedNodeAction(NodeSelectorGUI gui, NodeButton button) {
            this.gui = gui;
            this.button = button;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            this.gui.setCurSelected(this.button.getName());
            this.button.setBackground(new Color(0, 255, 0));
            this.gui.getNodeTextLabel().setText(this.gui.curSelected);
        }
    }

    public class NodeButton extends JButton {

        private boolean isCube;
        private String name;

        private ActionListener nodeActionListener;
        private NodeSelectorGUI gui;

        public NodeButton(NodeSelectorGUI gui, boolean isCube, int row, int height) {
            this.isCube = isCube;
            this.name = "";
            this.gui = gui;

            if (height == 1)
                this.name += "H";
            else if (height == 2)
                this.name += "M";
            else
                this.name += "L";

            this.name += String.format("%s", row);

            this.setText(this.name);

            if (this.isCube) {
                this.setBackground(new Color(213, 38, 202));
                this.setForeground(new Color(255, 255, 255));
            } else {
                this.setBackground(new Color(255, 230, 0));
                this.setForeground(new Color(0, 0, 0));
            }

            this.nodeActionListener = new SetSelectedNodeAction(this.gui, this);

            this.addActionListener(this.nodeActionListener);
        }

        public String getName() {
            return this.name;
        }
    }

    public static void generateCol(NodeSelectorGUI gui, JPanel panel, int rowNum) {
        int lpadx = 5;
        int rpadx = 5;
        if (rowNum == 1)
            lpadx = 0;
        else if (rowNum % 3 == 1)
            lpadx += 20;
        else if (rowNum == 9)
            rpadx = 0;

        boolean isCube = false;
        if (rowNum % 3 == 2)
            isCube = true;

        for (int y = 1; y <= 3; y++) {
            int tpady = 10;
            int bpady = 10;
            if (y == 1)
                tpady = 0;
            if (y == 3)
                bpady = 0;
            NodeButton button = gui.new NodeButton(gui, isCube, rowNum, y);
            button.setPreferredSize(new Dimension(100, 150));
            addComponent(panel, button, 200 * rowNum, 300 * y, 200, 300, lpadx, tpady, rpadx, bpady,
                    GridBagConstraints.CENTER, 0);
        }
    }

    private static void addComponent(Container container, Component component, int gridx, int gridy,
            int gridwidth, int gridheight, int lpadx, int tpady, int rpadx, int bpady, int anchor, int fill) {

        GridBagConstraints gbc = new GridBagConstraints(gridx, gridy, gridwidth, gridheight, 1.0, 1.0,
                anchor, fill, new Insets(tpady, lpadx, bpady, rpadx), 0, 0);

        container.add(component, gbc);
    }

    public void setCurSelected(String node) {
        this.curSelected = node;
    }

    public JLabel getNodeTextLabel() { 
        return this.nodeTextLabel; 
    }

    public NodeSelectorGUI() {
        JFrame frame = new JFrame("Node Selector");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1500, 1500);

        // cube8Button.setEnabled(false);

        JPanel panel = new JPanel(new GridBagLayout());
        for (int i = 1; i <= 9; i++) {
            generateCol(this, panel, i);
        }

        this.nodeTextLabel = new JLabel("None");
        this.nodeTextLabel.setPreferredSize(new Dimension(100, 50));
        this.nodeTextLabel.setFont(new Font("Arial", 1, 20)); 
        addComponent(panel, nodeTextLabel, 200, 1200, 200, 300, 0, 10, 10, 10, GridBagConstraints.CENTER, 0);
        // midPanel.setPreferredSize(new Dimension(100, 100));
        frame.add(panel);

        frame.pack();

        frame.setVisible(true);
    }

    public static void main(String[] args) {

        NodeSelectorGUI gui = new NodeSelectorGUI();
        // Thread printThread = new Thread() {
        // @Override
        // public void run() {
        // while (true) {
        // //if (!gui.curSelected.equals(gui.prevSelected)) {
        // System.out.println(String.format("Selected Node: %s", gui.curSelected));
        // gui.prevSelected = gui.curSelected;
        // //}
        // //System.out.println("Thread running!");
        // }
        // }
        // };
        // printThread.start();
    }
}