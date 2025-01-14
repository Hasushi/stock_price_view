package org.kobeU.stock_view.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class MainView extends JFrame {

    private JTable stockTable;
    private DefaultTableModel tableModel;
    private JPanel chartPanel;
    private JComboBox<String> symbolSelector;
    private JButton fetchButton;

    private ActionListener fetchListener; // リスナーを登録

    public MainView() {
        setTitle("Real-Time Stock Tracker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        // テーブルのセットアップ
        setupTable();

        // グラフのセットアップ
        setupChart();

        // シンボルセレクトボックスとボタンのセットアップ
        setupInputPanel();

        // メインレイアウト
        add(new JScrollPane(stockTable), BorderLayout.NORTH);
        add(chartPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void setupTable() {
        String[] columns = {"Symbol", "Price", "Change %"};
        tableModel = new DefaultTableModel(columns, 0);
        stockTable = new JTable(tableModel);
        stockTable.getColumn("Price").setCellRenderer(new CustomTableCellRenderer(stockTable));
        stockTable.getColumn("Change %").setCellRenderer(new CustomTableCellRenderer(stockTable));
    }

    private void setupChart() {
        chartPanel = new JPanel();
        chartPanel.setLayout(new BorderLayout());
        JLabel placeholder = new JLabel("Chart will be displayed here.", SwingConstants.CENTER);
        placeholder.setFont(new Font("Arial", Font.PLAIN, 16));
        chartPanel.add(placeholder, BorderLayout.CENTER);
    }

    private void setupInputPanel() {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        // シンボル選択ボックスの作成
        symbolSelector = new JComboBox<>(new String[]{"日経平均", "日米ドル", "Google"});
        fetchButton = new JButton("Fetch Stock Data");

        fetchButton.addActionListener(e -> {
            if (fetchListener != null) {
                fetchListener.actionPerformed(e); // リスナーを呼び出し
            }
        });

        inputPanel.add(new JLabel("Select Symbol: "));
        inputPanel.add(symbolSelector);
        inputPanel.add(fetchButton);

        add(inputPanel, BorderLayout.SOUTH);
    }

    public void setFetchListener(ActionListener listener) {
        this.fetchListener = listener; // リスナーを登録
    }

    public String getSelectedSymbol() {
        // シンボル選択に応じて適切なAPIシンボルを返す
        String selected = (String) symbolSelector.getSelectedItem();
        switch (selected) {
            case "日経平均":
                return "^N225"; // Yahoo Financeでの日経平均のシンボル
            case "日米ドル":
                return "JPY=X"; // 日米ドルのシンボル
            case "Google":
                return "GOOGL"; // Googleのシンボル
            default:
                return ""; // 不正な場合は空文字
        }
    }

    public void updateTable(String symbol, double price, double changePercent) {
        SwingUtilities.invokeLater(() -> {
            tableModel.insertRow(0, new Object[]{symbol, price, String.format("%.2f%%", changePercent)});
            stockTable.revalidate();
            stockTable.repaint();
        });
    }

    public void updateChart(List<Double> prices, List<Long> timestamps) {
        SwingUtilities.invokeLater(() -> {
            chartPanel.removeAll();
            JPanel chart = createChart(prices, timestamps);
            chartPanel.add(chart, BorderLayout.CENTER);
            chartPanel.revalidate();
            chartPanel.repaint();
        });
    }

    private JPanel createChart(List<Double> prices, List<Long> timestamps) {
        JPanel chart = new JPanel();
        chart.setLayout(new GridLayout(prices.size(), 1));

        for (int i = 0; i < prices.size(); i++) {
            JLabel label = new JLabel(String.format("Time: %d, Price: %.2f", timestamps.get(i), prices.get(i)));
            chart.add(label);
        }

        return chart;
    }

    public void resetTable() {
        SwingUtilities.invokeLater(() -> {
            tableModel.setRowCount(0); // テーブルのすべての行を削除
            stockTable.revalidate();  // テーブルを再構築
            stockTable.repaint();     // テーブルを再描画
        });
    }


    public void showError(String message) {
        JOptionPane.showMessageDialog(this, "Error: " + message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
