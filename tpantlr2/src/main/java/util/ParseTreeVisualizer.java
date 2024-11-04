package util;

import org.antlr.v4.gui.TreeViewer;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.tree.ParseTree;

import javax.swing.*;
import java.util.List;

public class ParseTreeVisualizer {
    public static void visualize(ParseTree tree, Parser parser) {
        JFrame frame = new JFrame("Parse Tree");
        JPanel panel = new JPanel();
        TreeViewer viewer = new TreeViewer(List.of(parser.getRuleNames()), tree);
        viewer.setScale(1.5); // Scale for better readability
        panel.add(viewer);
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }
}
