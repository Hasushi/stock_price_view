package org.kobeU.stock_view.view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class CustomTableCellRenderer extends DefaultTableCellRenderer {
    private final JTable table;

    public CustomTableCellRenderer(JTable table) {
        this.table = table;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (column == 1) { // "Price"列
            double currentPrice = Double.parseDouble(value.toString());
            double previousPrice = row + 1 < table.getRowCount() ?
                    Double.parseDouble(table.getValueAt(row + 1, column).toString()) : currentPrice;

            if (currentPrice > previousPrice) {
                cell.setForeground(Color.RED);
            } else if (currentPrice < previousPrice) {
                cell.setForeground(Color.BLUE);
            } else {
                cell.setForeground(Color.BLACK);
            }
        } else {
            cell.setForeground(Color.BLACK); // 他の列はデフォルト
        }

        return cell;
    }
}
