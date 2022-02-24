/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Library;

import java.awt.Color;
import java.awt.Component;
import static javax.management.Query.value;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author frodriguez
 */
public class RenderCheckBox extends JCheckBox implements TableCellRenderer {

    private final JComponent component = new JCheckBox();

    public RenderCheckBox() {
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable jtable, Object value, boolean bln, boolean bln1, int i, int i1) {
        ((JCheckBox) component).setBackground(new Color(240, 255, 240));
        boolean b = ((Boolean) value).booleanValue();
        ((JCheckBox) component).setSelected(b);
        return ((JCheckBox) component);
    }

}
