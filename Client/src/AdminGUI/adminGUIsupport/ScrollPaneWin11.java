package AdminGUI.adminGUIsupport;

/**
 * Custom JScrollPane that implements a Windows 11-style scrollbar with custom layout and scroll behavior.
 */
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Insets;
import java.awt.Rectangle;
import java.util.Objects;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;

public class ScrollPaneWin11 extends JScrollPane {

    /**
     * Constructor that initializes the custom scroll bar UI and scroll behavior.
     */
    public ScrollPaneWin11() {
        getVerticalScrollBar().setUI(new ScrollBarWin11UI());
        getHorizontalScrollBar().setUI(new ScrollBarWin11UI());
        getVerticalScrollBar().setUnitIncrement(10);
        getHorizontalScrollBar().setUnitIncrement(10);
        setLayout(new ScrollLayout());
    } // End ScrollPaneWin11

    /**
     * Override to disable optimized drawing for custom rendering.
     *
     * @return false to disable optimized drawing.
     */
    @Override
    public boolean isOptimizedDrawingEnabled() {
        return false;
    } // End isOptimizedDrawingEnabled

    /**
     * Override to update the UI components and set their Z-order for proper visibility.
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
     * Custom layout for the JScrollPane to adjust bounds and positioning of components.
     */
    private class ScrollLayout extends ScrollPaneLayout {

        /**
         * Override to customize the layout of the JScrollPane components (viewport and scrollbars).
         *
         * @param parent The parent container of the JScrollPane.
         */
        @Override
        public void layoutContainer(Container parent) {
            super.layoutContainer(parent);
            if (parent instanceof JScrollPane) {
                JScrollPane scroll = (JScrollPane) parent;
                Rectangle rec = scroll.getViewport().getBounds();
                Insets insets = parent.getInsets();
                int rhHeight = 0;

                // Adjust the row header height
                if (scroll.getColumnHeader() != null) {
                    Rectangle rh = scroll.getColumnHeader().getBounds();
                    rhHeight = rh.height;
                }

                // Update the viewport size
                rec.width = scroll.getBounds().width - (insets.left + insets.right);
                rec.height = scroll.getBounds().height - (insets.top + insets.bottom) - rhHeight;

                if (Objects.nonNull(viewport)) {
                    viewport.setBounds(rec);
                }

                // Adjust the horizontal scrollbar size
                if (!Objects.isNull(hsb)) {
                    Rectangle hrc = hsb.getBounds();
                    hrc.width = rec.width;
                    hsb.setBounds(hrc);
                }
            }
        } // End layoutContainer
    } // End ScrollLayout
} // End ScrollPaneWin11