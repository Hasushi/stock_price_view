package org.kobeU.stock_view.view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Objects;

public class ChartPanel extends JPanel {
    private List<Double> prices;
    private List<Long> timestamps;

    public ChartPanel() {
        setPreferredSize(new Dimension(800, 400));
        setBackground(Color.WHITE);
    }

    public void updateChartData(List<Double> prices, List<Long> timestamps) {
        this.prices = prices.stream().filter(Objects::nonNull).toList();
        this.timestamps = timestamps.stream().filter(Objects::nonNull).toList();
        repaint(); // 再描画
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // データがnullまたは空の場合は描画しない
        if (prices == null || timestamps == null || prices.isEmpty() || timestamps.isEmpty()) {
            return;
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // パネルのサイズを取得
        int width = getWidth();
        int height = getHeight();

        // 最大値と最小値を計算
        double maxPrice = prices.stream().mapToDouble(Double::doubleValue).max().orElse(1);
        double minPrice = prices.stream().mapToDouble(Double::doubleValue).min().orElse(0);

        // X軸とY軸のスケールを計算
        int padding = 50;
        double xScale = (width - 2 * padding) / (double) timestamps.size();
        double yScale = (height - 2 * padding) / (maxPrice - minPrice);

        // X軸とY軸を描画
        g2d.setColor(Color.BLACK);
        g2d.drawLine(padding, height - padding, width - padding, height - padding); // X軸
        g2d.drawLine(padding, padding, padding, height - padding); // Y軸

        // グラフを描画
        g2d.setColor(Color.BLUE);
        for (int i = 0; i < prices.size() - 1; i++) {
            int x1 = (int) (padding + i * xScale);
            int y1 = (int) (height - padding - (prices.get(i) - minPrice) * yScale);
            int x2 = (int) (padding + (i + 1) * xScale);
            int y2 = (int) (height - padding - (prices.get(i + 1) - minPrice) * yScale);
            g2d.drawLine(x1, y1, x2, y2);
        }
    }
}
