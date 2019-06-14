package sample;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import pl.zankowski.iextrading4j.api.stocks.Quote;
import pl.zankowski.iextrading4j.client.IEXCloudClient;
import pl.zankowski.iextrading4j.client.IEXCloudTokenBuilder;
import pl.zankowski.iextrading4j.client.IEXTradingApiVersion;
import pl.zankowski.iextrading4j.client.IEXTradingClient;
import pl.zankowski.iextrading4j.client.rest.request.stocks.QuoteRequestBuilder;

public class Controller {

    @FXML
    Label label;
    @FXML
    PieChart pieChart;
    @FXML
    NumberAxis xAxis;
    @FXML
    CategoryAxis yAxis;
    @FXML
    BarChart<String, Number> barChart;

    public void initialize() {

        final IEXCloudClient iexTradingClient = IEXTradingClient.create(IEXTradingApiVersion.IEX_CLOUD_BETA_SANDBOX,
                new IEXCloudTokenBuilder()
                        .withPublishableToken("Tpk_18dfe6cebb4f41ffb219b9680f9acaf2")
                        .withSecretToken("Tsk_3eedff6f5c284e1a8b9bc16c54dd1af3")
                        .build());
        final Quote cisco = iexTradingClient.executeRequest(new QuoteRequestBuilder()
                .withSymbol("CSCO")
                .build());
        final Quote apple = iexTradingClient.executeRequest(new QuoteRequestBuilder()
                .withSymbol("AAPL")
                .build());
        final Quote ibm = iexTradingClient.executeRequest(new QuoteRequestBuilder()
                .withSymbol("IBM")
                .build());
        final Quote tencent = iexTradingClient.executeRequest(new QuoteRequestBuilder()
                .withSymbol("TME")
                .build());
        //System.out.println(quote.getChange());
        //label.setText(String.valueOf(quote.getWeek52High()));
        xAxis.setLabel("IEX by volume");
        xAxis.setTickLabelRotation(90);
        yAxis.setLabel("Company");
        barChart.setLegendVisible(false);

        XYChart.Series<String, Number> companyPrice = new XYChart.Series();
        companyPrice.getData().add(new XYChart.Data( cisco.getSymbol(), cisco.getLatestPrice()));
        companyPrice.getData().add(new XYChart.Data( apple.getSymbol(), apple.getLatestPrice()));
        companyPrice.getData().add(new XYChart.Data( ibm.getSymbol(), ibm.getLatestPrice()));
        companyPrice.getData().add(new XYChart.Data( tencent.getSymbol(), tencent.getLatestPrice()));
        barChart.getData().add(companyPrice);

        Node n = barChart.lookup(".data0.chart-bar");
        n.setStyle("-fx-bar-fill: #ce712f");
        n = barChart.lookup(".data1.chart-bar");
        n.setStyle("-fx-bar-fill: #53a0e0");
        n = barChart.lookup(".data2.chart-bar");
        n.setStyle("-fx-bar-fill: #2d994f");
        n = barChart.lookup(".data3.chart-bar");
        n.setStyle("-fx-bar-fill: #dbd95e");

    }

}
