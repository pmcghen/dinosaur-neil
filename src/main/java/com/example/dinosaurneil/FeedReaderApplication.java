package com.example.dinosaurneil;

import com.example.dinosaurneil.util.StringToLocalDateTime;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class FeedReaderApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        BorderPane pane = new BorderPane();
        HBox topBar = initTopBar();
        HBox bottomBar = new HBox(24);

        Label addFeedLabel = new Label("Add feed URL");
        TextField feedUrlTextField = new TextField();
        Button addFeedButton = new Button("Add");

        Accordion feedAccordion = new Accordion();
        ScrollPane sidebar = initSidebar(feedAccordion);
        ScrollPane mainContainer = initMainContainer();

        pane.setPadding(new Insets(12));

        addFeedLabel.setFont(Font.font("Comfortaa", FontWeight.BOLD, 16));

        addFeedButton.setFont(Font.font("Comfortaa", 16));
        addFeedButton.getStyleClass().setAll("btn", "btn-primary");

        bottomBar.getChildren().addAll(addFeedLabel, feedUrlTextField, addFeedButton);
        bottomBar.setPadding(new Insets(12, 0, 0, 0));
        bottomBar.setAlignment(Pos.CENTER_LEFT);

        ArrayList<Feed> feeds = new ArrayList<>();
        ArrayList<FeedItem> feedItems = new ArrayList<>();

        addFeedButton.setOnAction((e) -> addFeed(feeds, feedItems, feedAccordion, mainContainer, feedUrlTextField));

        feedUrlTextField.setOnKeyPressed((e) -> {
            if (e.getCode() == KeyCode.ENTER) {
                addFeed(feeds, feedItems, feedAccordion, mainContainer, feedUrlTextField);
            }
        });

        pane.setLeft(sidebar);
        pane.setCenter(mainContainer);
        pane.setTop(topBar);
        pane.setBottom(bottomBar);

        Scene scene = new Scene(pane, 1024, 768);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());

        stage.setTitle("Dinosaur Neil RSS Reader");
        stage.setScene(scene);
        stage.show();
    }

    private HBox initTopBar() {
        HBox topBar = new HBox(24);
        Label headerTitle = new Label();

        headerTitle.setText("\uD83E\uDD96 Dinosaur Neil RSS Reader");
        headerTitle.setFont(Font.font("Comfortaa", FontWeight.BLACK, 24));

        topBar.getChildren().add(headerTitle);
        topBar.setPadding(new Insets(0, 0 , 12, 0));

        return topBar;
    }

    private ScrollPane initSidebar(Accordion contentContainer) {
        ScrollPane sidebarContainer = new ScrollPane();

        sidebarContainer.setPrefViewportHeight(700);
        sidebarContainer.setPrefViewportWidth(255);
        sidebarContainer.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sidebarContainer.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sidebarContainer.setContent(contentContainer);

        sidebarContainer.getStyleClass().setAll("panel", "panel-primary");

        return sidebarContainer;
    }

    private ScrollPane initMainContainer() {
        ScrollPane mainContainer = new ScrollPane();

        mainContainer.setPrefViewportHeight(700);
        mainContainer.setPrefViewportWidth(720);
        mainContainer.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        mainContainer.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        return mainContainer;
    }

    private void addFeed(ArrayList<Feed> feeds, ArrayList<FeedItem> feedItems, Accordion feedAccordion, ScrollPane mainContainer, TextField feedUrlTextField) {
        // TODO Break this method into smaller chunks
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
            LocalDateTime lastBuildDate;

            if (feed.selectFirst("lastBuildDate") == null) {
                lastBuildDate = LocalDateTime.now();
            } else {
                lastBuildDate = StringToLocalDateTime.convertStringToDateTime(Objects.requireNonNull(feed.selectFirst("lastBuildDate")).text());
            }

            Feed currentFeed = new Feed();

            currentFeed.setId(feedCount);
            currentFeed.setTitle(Objects.requireNonNull(feed.selectFirst("title")).text());
            currentFeed.setLink(Objects.requireNonNull(feed.selectFirst("link")).text());
            currentFeed.setDescription(Objects.requireNonNull(feed.selectFirst("description")).text());
            currentFeed.setLastBuildDate(lastBuildDate);

            feeds.add(currentFeed);

            Elements currentFeedItems = doc.getElementsByTag("item");

            for (Element currentFeedItem :
                    currentFeedItems) {
                FeedItem currentItem = new FeedItem();

                currentItem.setId(itemCount);
                currentItem.setParentId(feedCount);
                currentItem.setTitle(Objects.requireNonNull(currentFeedItem.selectFirst("title")).text());
                currentItem.setLink(Objects.requireNonNull(currentFeedItem.selectFirst("link")).text());
                currentItem.setDescription(Objects.requireNonNull(currentFeedItem.selectFirst("description")).text());
                currentItem.setPubDate(StringToLocalDateTime.convertStringToDateTime(Objects.requireNonNull(currentFeedItem.selectFirst("pubDate")).text()));

                feedItems.add(currentItem);
                itemCount++;
            }
        }

        populateSidebar(feeds, feedItems, feedAccordion, mainContainer);

        feedUrlTextField.clear();
    }

    private void populateSidebar(ArrayList<Feed> feeds, ArrayList<FeedItem> feedItems, Accordion feedAccordion, ScrollPane container) {
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

                    itemButton.setOnAction((ev) -> populateMainContent(feedItem, container));

                    feedItemsContainer.getChildren().add(itemButton);
                }
            }

            feedPane.setContent(feedItemsContainer);

            feedAccordion.getPanes().add(feedPane);
        }
    }

    private void populateMainContent(FeedItem feedItem, ScrollPane container) {
        WebView feedView = new WebView();
        WebEngine feedEngine = feedView.getEngine();

        feedEngine.loadContent(feedItem.getDescription());

        feedView.setPrefWidth(720);
        feedView.setPrefHeight(645);

        container.setContent(feedView);
    }

    public static void main(String[] args) {
        launch();
    }
}