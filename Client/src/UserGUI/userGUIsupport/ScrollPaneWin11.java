package userGUIsupport;

import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Insets;
import java.awt.Rectangle;
import java.util.Objects;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;

/**
 * Custom JScrollPane for Windows 11 style scrollbars.
 */
public class ScrollPaneWin11 extends JScrollPane {

    /**
     * Constructor that customizes the scrollbars and sets the layout.
     */
    public ScrollPaneWin11() {
        getVerticalScrollBar().setUI(new ScrollBarWin11UI());
        getHorizontalScrollBar().setUI(new ScrollBarWin11UI());
        getVerticalScrollBar().setUnitIncrement(10);
        getHorizontalScrollBar().setUnitIncrement(10);
        setLayout(new ScrollLayout());
    } // End ScrollPaneWin11 constructor

    /**
     * Overridden method to disable optimized drawing.
     * @return false to disable optimized drawing.
     */
    @Override
    public boolean isOptimizedDrawingEnabled() {
        return false;
    } // End isOptimizedDrawingEnabled

    /**
     * Overridden method to update the UI components of the scroll pane.
     */
    @Override
    public void updateUI() {
        super.updateUI();
        EventQueue.invokeLater(() -> {
            setComponentZOrder(getVerticalScrollBar(), 0);
            setComponentZOrder(getHorizontalScrollBar(), 1);
            setComponentZOrder(getViewport(), 2);
            getVerticalScrollBar().setOpaque(false);
            getHorizontalScrollBar().setOpaque(false);
        });
    } // End updateUI

    /**
     * Custom layout for the scroll pane.
     */
    private class ScrollLayout extends ScrollPaneLayout {

        /**
         * Layouts the container components based on the scroll pane's bounds and insets.
         * @param parent The parent container.
         */
        @Override
        public void layoutContainer(Container parent) {
            super.layoutContainer(parent);
            if (parent instanceof JScrollPane) {
                JScrollPane scroll = (JScrollPane) parent;
                Rectangle rec = scroll.getViewport().getBounds();
                Insets insets = parent.getInsets();
                int rhHeight = 0;
                if (scroll.getColumnHeader() != null) {
                    Rectangle rh = scroll.getColumnHeader().getBounds();
                    rhHeight = rh.height;
                }
                rec.width = scroll.getBounds().width - (insets.left + insets.right);
                rec.height = scroll.getBounds().height - (insets.top + insets.bottom) - rhHeight;
                if (Objects.nonNull(viewport)) {
                    viewport.setBounds(rec);
                }
                if (!Objects.isNull(hsb)) {
                    Rectangle hrc = hsb.getBounds();
                    hrc.width = rec.width;
                    hsb.setBounds(hrc);
                }
            }
        } // End layoutContainer
    } // End ScrollLayout
} // End ScrollPaneWin11