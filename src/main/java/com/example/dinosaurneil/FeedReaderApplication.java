package com.example.dinosaurneil;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class FeedReaderApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        BorderPane pane = new BorderPane();
        HBox topBar = new HBox(24);
        HBox bottomBar = new HBox(24);
        Label headerTitle = new Label();
        Label addFeedLabel = new Label("Add feed URL");
        TextField feedUrlTextField = new TextField();
        Button addFeedButton = new Button("Add");

        addFeedLabel.setFont(Font.font("Comfortaa", FontWeight.BOLD, 16));

        addFeedButton.setFont(Font.font("Comfortaa", 16));
        addFeedButton.getStyleClass().setAll("btn", "btn-primary");

        headerTitle.setText("Dinosaur Neil RSS Reader");
        headerTitle.setFont(Font.font("Comfortaa", FontWeight.BLACK, 24));

        topBar.getChildren().add(headerTitle);
        topBar.setPadding(new Insets(12));

        bottomBar.getChildren().addAll(addFeedLabel, feedUrlTextField, addFeedButton);
        bottomBar.setPadding(new Insets(12));
        bottomBar.setAlignment(Pos.CENTER_LEFT);

        ArrayList<Feed> feeds = new ArrayList<>();
        ArrayList<FeedItem> feedItems = new ArrayList<>();

        addFeedButton.setOnAction((e) -> {
            Document doc = null;

            try {
                doc = Jsoup.connect(feedUrlTextField.getText()).get();
            } catch (IOException ex) {
                System.err.println("\uD83D\uDEA9 Error! " + ex);
            }

            assert doc != null;
            int feedCount = feeds.size() + 1;

            Elements feedParent = doc.select("channel");
            int itemCount = 1;

            for (Element feed :
                    feedParent) {
                String lastBuildDate;

                if (feed.selectFirst("lastBuildDate") == null) {
                    lastBuildDate = "Wed, 03 May 2023 00:00:00 +0000";
                } else {
                    lastBuildDate = Objects.requireNonNull(feed.selectFirst("lastBuildDate")).text();
                }

                Feed currentFeed = new Feed(feedCount,
                        Objects.requireNonNull(feed.selectFirst("title")).text(),
                        Objects.requireNonNull(feed.selectFirst("link")).text(),
                        Objects.requireNonNull(feed.selectFirst("description")).text(),
                        lastBuildDate);
                Elements currentFeedItems = doc.getElementsByTag("item");

                feeds.add(currentFeed);

                for (Element currentFeedItem :
                        currentFeedItems) {
                    FeedItem currentItem = new FeedItem(itemCount,
                            feedCount,
                            Objects.requireNonNull(currentFeedItem.selectFirst("title")).text(),
                            Objects.requireNonNull(currentFeedItem.selectFirst("link")).text(),
                            Objects.requireNonNull(currentFeedItem.selectFirst("description")).text(),
                            Objects.requireNonNull(currentFeedItem.selectFirst("pubDate")).text());
                    System.out.println(currentItem);
                    itemCount++;
                }
            }
        });

        pane.setTop(topBar);
        pane.setBottom(bottomBar);

        Scene scene = new Scene(pane, 1024, 768);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());

        stage.setTitle("Dinosaur Neil RSS Reader");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}