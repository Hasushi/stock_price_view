package org.kobeU.stock_view;

import org.kobeU.stock_view.controller.StockController;
import org.kobeU.stock_view.model.StockService;
import org.kobeU.stock_view.view.MainView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StockService stockService = new StockService();
            MainView mainView = new MainView();
            new StockController(stockService, mainView);
        });
    }
}
