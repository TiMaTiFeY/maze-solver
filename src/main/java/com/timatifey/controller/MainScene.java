package com.timatifey.controller;
import com.timatifey.model.*;
import com.timatifey.view.MazePainter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.embed.swing.SwingFXUtils;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.module.FindException;

public class MainScene {
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private final MazePainter painter = new MazePainter();
    private final MazeSolver solver = new MazeSolver();
    private final MazeReader reader = new MazeReader();
    private final MazeWriter writer = new MazeWriter();
    private final MazeGenerator mazeGenerator = new MazeGenerator();
    private Maze maze;
    private Maze solvedMaze;

    @FXML
    private ImageView imageView;

    @FXML
    private TextField heightField;

    @FXML
    private TextField widthField;

    @FXML
    private ComboBox<String> generateAlg;

    @FXML
    private ComboBox<String> solveAlg;

    @FXML
    private Label error;

    @FXML
    private TextField nameOutputFile;

    @FXML
    public void initialize() {
        ObservableList<String> langs1 = FXCollections.observableArrayList("Backtracking", "Simulation \"Live\"");
        ObservableList<String> langs2 = FXCollections.observableArrayList("Wave algorithm");
        generateAlg.setItems(langs1);
        generateAlg.setValue("Backtracking");

        heightField.setText("80");
        widthField.setText("80");

        solveAlg.setItems(langs2);
        solveAlg.setValue("Wave algorithm");

        error.setText("");

        nameOutputFile.setText("maze");
    }

    @FXML
    public void openFile() {
        try {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(stage);
            String fileName = file.getAbsolutePath();
            maze = reader.readMazeFromFile(fileName);
            showMaze(maze);
        } catch (IOException | NullPointerException err) {
            error.setText("Error in file reading");
        }
    }

    @FXML
    public void writeFile() {
        try {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File dir = directoryChooser.showDialog(stage);
            String path = dir.getAbsolutePath();
            if (solvedMaze == null) {
                writer.writeMapToFile(maze, path + nameOutputFile.getText() + ".txt");
            }
            else {
                writer.writeMapToFile(solvedMaze, path + nameOutputFile.getText() + ".txt");
            }
        } catch (IOException | NullPointerException err) {
            error.setText("Error in file writing");
        }
    }

    @FXML
    public void writeImage() {
        try {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File dir = directoryChooser.showDialog(stage);
            String path = dir.getAbsolutePath();
            if (solvedMaze == null) {
                painter.saveMazeToImg(maze, path + nameOutputFile.getText() + ".png");
            } else {
                painter.saveMazeToImg(solvedMaze, path + nameOutputFile.getText() + ".png");
            }
        } catch (IOException | NullPointerException err) {
            error.setText("Error in file writing");
        }
    }

    @FXML
    private void generateMaze() {
        try {
            switch (generateAlg.getValue()) {
                case "Backtracking": {
                    maze = mazeGenerator.generateMazeByBacktracking(height(), width());
                    break;
                }
                case "Simulation \"Live\"": {
                    maze = mazeGenerator.generateMazeWithLiveSimulation(
                            height(),
                            width(),
                            0.45f,
                            4,
                            1,
                            1
                    );
                    break;
                }
                default:
                    break;
            }
            showMaze(maze);
        } catch (IllegalArgumentException ignored) {
        }
    }

    @FXML
    private void solveMaze() {
        try {
            if (maze == null) {
                generateMaze();
            }
            solvedMaze = solver.solveByWave(maze);
            error.setText("");
            showMaze(solvedMaze);
        } catch (FindException | NullPointerException err) {
            error.setText("No solution!");
        }
    }

    private void showMaze(Maze maze) {
        BufferedImage img = painter.paintMaze(maze);
        imageView.setImage(SwingFXUtils.toFXImage(img, null));
        if (img.getHeight() >= img.getWidth()) {
            imageView.setFitHeight(1000.0);
            imageView.setFitWidth(Math.round(img.getWidth() * 1000.0 / img.getHeight()));
        } else {
            imageView.setFitWidth(1300.0);
            imageView.setFitHeight(Math.round(img.getHeight() * 1300.0 / img.getWidth()));
        }
    }

    private int height() {
        int a = 50;
        try {
            a = Integer.parseInt(heightField.getText());
            error.setText("");
            if (a < 5)  {
                throw new IllegalArgumentException();
            }
            return a;
        } catch (IllegalArgumentException ignored) {
            error.setText("WRONG HEIGHT (min 5)");
        }
        return a;
    }

    private int width() {
        int a = 50;
        try {
            a = Integer.parseInt(widthField.getText());
            error.setText("");
            if (a < 5)  {
                throw new IllegalArgumentException();
            }
            return a;
        } catch (IllegalArgumentException ignored) {
            error.setText("WRONG WIDTH (min 5)");
        }
        return a;
    }

}
