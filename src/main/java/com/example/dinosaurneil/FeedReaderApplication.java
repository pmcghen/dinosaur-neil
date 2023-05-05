package com.example.dinosaurneil;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
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
        ScrollPane sidebarContainer = new ScrollPane();
        VBox sidebar = new VBox();
        Accordion feedAccordion = new Accordion();
        ScrollPane mainContainer = new ScrollPane();
        VBox mainContent = new VBox();

        WebView feedView = new WebView();
        WebEngine feedEngine = feedView.getEngine();

        pane.setPadding(new Insets(12));

        addFeedLabel.setFont(Font.font("Comfortaa", FontWeight.BOLD, 16));

        addFeedButton.setFont(Font.font("Comfortaa", 16));
        addFeedButton.getStyleClass().setAll("btn", "btn-primary");

        headerTitle.setText("\uD83E\uDD96 Dinosaur Neil RSS Reader");
        headerTitle.setFont(Font.font("Comfortaa", FontWeight.BLACK, 24));

        topBar.getChildren().add(headerTitle);
        topBar.setPadding(new Insets(0, 0 , 12, 0));

        sidebarContainer.setPrefViewportHeight(700);
        sidebarContainer.setPrefViewportWidth(255);
        sidebarContainer.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sidebarContainer.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sidebarContainer.setContent(sidebar);

        mainContainer.setPrefViewportHeight(700);
        mainContainer.setPrefViewportWidth(720);
        mainContainer.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        mainContainer.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        mainContainer.setContent(mainContent);

        sidebar.getChildren().add(feedAccordion);
        sidebarContainer.getStyleClass().setAll("panel", "panel-primary");

        bottomBar.getChildren().addAll(addFeedLabel, feedUrlTextField, addFeedButton);
        bottomBar.setPadding(new Insets(12, 0, 0, 0));
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

                    feedItems.add(currentItem);
                    itemCount++;
                }
            }

            feedAccordion.getPanes().clear();

            for (Feed feed : feeds) {
                TitledPane feedPane = new TitledPane();
                feedPane.setText(feed.getTitle());
                VBox feedItemsContainer = new VBox();

                for (FeedItem feedItem : feedItems) {
                    if (feedItem.getParentId() == feed.getId()) {
                        Button itemButton = new Button();
                        itemButton.setText(feedItem.getTitle());
                        itemButton.getStyleClass().addAll("btn", "btn-default");
                        itemButton.setPrefWidth(230);
                        itemButton.setPadding(new Insets(0, 0, 0, 0));
                        itemButton.setTextAlignment(TextAlignment.LEFT);

                        itemButton.setOnAction((ev) -> {
                            mainContent.getChildren().clear();

                            feedEngine.loadContent(feedItem.getDescription());

                            feedView.setPrefWidth(720);
                            feedView.setPrefHeight(645);
                        });

                        feedItemsContainer.getChildren().add(itemButton);
                    }
                }

                feedPane.setContent(feedItemsContainer);

                feedAccordion.getPanes().add(feedPane);
            }
        });

        mainContainer.setContent(feedView);

        pane.setLeft(sidebarContainer);
        pane.setCenter(mainContainer);
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