package sample;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import pl.zankowski.iextrading4j.api.stocks.Quote;
import pl.zankowski.iextrading4j.client.IEXCloudClient;
import pl.zankowski.iextrading4j.client.IEXCloudTokenBuilder;
import pl.zankowski.iextrading4j.client.IEXTradingApiVersion;
import pl.zankowski.iextrading4j.client.IEXTradingClient;
import pl.zankowski.iextrading4j.client.rest.request.stocks.QuoteRequestBuilder;

public class Controller {

    @FXML
    VBox vboxLabel;
    @FXML
    Label ciscoLabel;
    @FXML
    Label appleLabel;
    @FXML
    Label ibmLabel;
    @FXML
    Label tencentLabel;
    @FXML
    Label microsoftLabel;
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
        final Quote microsoft = iexTradingClient.executeRequest(new QuoteRequestBuilder()
                .withSymbol("MSFT")
                .build());
        //System.out.println(quote.getChange());

        xAxis.setLabel("IEX by volume");
        xAxis.setTickLabelRotation(90);
        yAxis.setLabel("Company");
        barChart.setLegendVisible(false);

        XYChart.Series<String, Number> companyPrice = new XYChart.Series();
        companyPrice.getData().add(new XYChart.Data( cisco.getSymbol(), cisco.getLatestPrice()));
        companyPrice.getData().add(new XYChart.Data( apple.getSymbol(), apple.getLatestPrice()));
        companyPrice.getData().add(new XYChart.Data( ibm.getSymbol(), ibm.getLatestPrice()));
        companyPrice.getData().add(new XYChart.Data( tencent.getSymbol(), tencent.getLatestPrice()));
        companyPrice.getData().add(new XYChart.Data( microsoft.getSymbol(), microsoft.getLatestPrice()));
        barChart.getData().add(companyPrice);

        for (final XYChart.Series<String, Number> series : barChart.getData()) {
            for (final XYChart.Data<String, Number> data : series.getData()) {
                Tooltip tooltip = new Tooltip();
                tooltip.setText(data.getXValue().toString() +" "+
                        data.getYValue().toString());
                Tooltip.install(data.getNode(), tooltip);
            }
        }

        ciscoLabel.setText(" " + String.valueOf(cisco.getChange()) + "   " + cisco.getSymbol());
        changeColor(cisco.getChange().doubleValue(), ciscoLabel);
        appleLabel.setText(" " + String.valueOf(apple.getChange()) + "   " + apple.getSymbol());
        changeColor(apple.getChange().doubleValue(), appleLabel);
        ibmLabel.setText(" " + String.valueOf(ibm.getChange()) + "   " + ibm.getSymbol());
        changeColor(ibm.getChange().doubleValue(), ibmLabel);
        tencentLabel.setText(" " + String.valueOf(tencent.getChange()) + "   " + tencent.getSymbol());
        changeColor(tencent.getChange().doubleValue(), tencentLabel);
        tencentLabel.setText(" " + String.valueOf(tencent.getChange()) + "   " + tencent.getSymbol());
        changeColor(tencent.getChange().doubleValue(), tencentLabel);
        microsoftLabel.setText(" " + String.valueOf(microsoft.getChange()) + "   " + microsoft.getSymbol());
        changeColor(microsoft.getChange().doubleValue(), microsoftLabel);

        Node n = barChart.lookup(".data0.chart-bar");
        n.setStyle("-fx-bar-fill: #ce712f");
        n = barChart.lookup(".data1.chart-bar");
        n.setStyle("-fx-bar-fill: #53a0e0");
        n = barChart.lookup(".data2.chart-bar");
        n.setStyle("-fx-bar-fill: #653fa3");
        n = barChart.lookup(".data3.chart-bar");
        n.setStyle("-fx-bar-fill: #dbd95e");
        n = barChart.lookup(".data3.chart-bar");
        n.setStyle("-fx-bar-fill: #2d994f");
    }

    private void changeColor(double change, Label label){
        if(change>0){
            label.setBackground(new Background(new BackgroundFill(
                    Color.rgb(60, 183, 79),
                    CornerRadii.EMPTY,
                    Insets.EMPTY
            )));
        }else{
            label.setBackground(new Background(new BackgroundFill(
                    Color.rgb(221, 102, 84),
                    CornerRadii.EMPTY,
                    Insets.EMPTY
            )));
        }
    }

}
