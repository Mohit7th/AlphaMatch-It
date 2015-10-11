package gui;

/**
 * @author Mohit Uniyal
 *  15/9/2015 It is board game in which user has to click twice on two different button to find the matching images
 *  completed : 11/10/2015
 */

import java.util.ArrayList;
import java.util.Random;

import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainGUI extends Application implements EventHandler<ActionEvent> {

	// 64 buttons that will be displayed on the board
	private Button all64Btn[];
	// Image and ImageView for 64 buttons
	private Image allImg[];
	private ImageView first32Iv[], second32Iv[];

	public MainGUI() {
		all64Btn = new Button[64];
		allImg = new Image[32];
		first32Iv = new ImageView[32];
		second32Iv = new ImageView[32];
		initImageViews();
	}

	// Initializing all image and imageView elements
	private void initImageViews() {
		for (int i = 0; i < 32; i++) {
			// one by one accessing all 32 images
			allImg[i] = new Image("./res/img" + (i + 1) + ".png");
			first32Iv[i] = new ImageView(allImg[i]);
			second32Iv[i] = new ImageView(allImg[i]);

			first32Iv[i].setFitHeight(50);
			second32Iv[i].setFitHeight(50);
			first32Iv[i].setFitWidth(50);
			second32Iv[i].setFitWidth(50);
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	private BorderPane mainLayoutBp;

	public void start(Stage gameStg) throws Exception {
		gameStg.setTitle("AlphaMatch : Mohit Uniyal");
		mainLayoutBp = new BorderPane();
		addAllElements(mainLayoutBp);
		Scene scene = new Scene(mainLayoutBp, 500, 550);
		gameStg.setScene(scene);
		gameStg.show();
		initalDisplayImg();
		//after 2 sec all the images will be hidden
		 PauseTransition delay = new PauseTransition(Duration.seconds(4));
		 delay.setOnFinished(event -> {
			 hideAllImages();
	      });
		 delay.play();
	}
	
	//set all the buttons with images randomly
	private void initalDisplayImg(){
		boolean flag=true;
		String[] val; int num;
		for(int i=0; i<64; i++){
			val = all64Btn[i].getId().split("_");
			num = Integer.parseInt(val[2]);
			if(flag){
				all64Btn[i].setGraphic(first32Iv[num-1]);
				flag = false;
			}else{
				all64Btn[i].setGraphic(second32Iv[num-1]);
				flag = true;
			}
		}
	}
	
	//removing all images
	private void hideAllImages(){
		for(int i=0; i<64; i++){
			all64Btn[i].setGraphic(null);
		}
	}

	private Label tryLbl;
	// adding all components to the borderpane
	private void addAllElements(BorderPane mainLayoutBp) {
		Label gameNameLbl = new Label("AlphaMatch-It");
		Button refreshBtn = new Button("New Game");
		refreshBtn.setOnAction(this);

		gameNameLbl.setStyle("-fx-font-size: 25pt");
		refreshBtn.setStyle("-fx-font-size: 12pt; -fx-background-color: #c3c4c4, linear-gradient(#d6d6d6 50%, white 100%), radial-gradient(center 50% -40%, radius 200%, #e6e6e6 45%, rgba(230,230,230,0) 50%);"
				+ "-fx-background-radius: 30; -fx-background-insets: 0,1,1; -fx-text-fill: black; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 3, 0.0, 0, 1);");

		HBox gameNameHb = new HBox(10);
		gameNameHb.setAlignment(Pos.CENTER);
		gameNameHb.setPadding(new Insets(20, 0, 0, 0));
		gameNameHb.getChildren().addAll(gameNameLbl, refreshBtn);

		tryLbl = new Label("Total Try : 0");
		tryLbl.setAlignment(Pos.TOP_RIGHT);
		HBox timerHb = new HBox();
		timerHb.getChildren().add(tryLbl);
		timerHb.setAlignment(Pos.CENTER_RIGHT);
		
		timerHb.setPadding(new Insets(0, 30, 0, 0));
		VBox topContentVb = new VBox();
		topContentVb.getChildren().addAll(gameNameHb, timerHb);
		mainLayoutBp.setTop(topContentVb);
		add64Buttons(mainLayoutBp);
	}

	// arrays that will contain random numbers from 1 to 32, without repetition
	private int randNum1[], randNum2[];
	{
		randNum1 = new int[32];
		randNum2 = new int[32];
	}

	// generating two list of random numbers
	private void generateRandomNum() {
		Random randGen = new Random();
		ArrayList<Integer> list = new ArrayList<>();
		int btnCtr = 0, n = 0;
		while (list.size() < 32) {
			n = randGen.nextInt(32) + 1;
			if (!list.contains(n)) {
				list.add(n);
				randNum1[btnCtr] = n;
				btnCtr++;
			}
		}

		list.removeAll(list);
		btnCtr = 0;
		while (list.size() < 32) {
			n = randGen.nextInt(32) + 1;
			if (!list.contains(n)) {
				list.add(n);
				randNum2[btnCtr] = n;
				btnCtr++;
			}
		}

	}

	private GridPane buttonGp;
	// adding 64 buttons to the board
	private void add64Buttons(BorderPane mainLayoutBp) {

		int btnCtr = 0;
		tryLbl.setText("Total Try : 0");
		generateRandomNum();
		buttonGp = new GridPane();
		buttonGp.setHgap(5);
		buttonGp.setVgap(5);
		
		int t = 0, k = 0;
		boolean alterFlag = false;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				all64Btn[btnCtr] = new Button();
				if (alterFlag) {
					all64Btn[btnCtr].setId("btn_" + btnCtr + "_"
							+ randNum1[t++]);
					alterFlag = false;
				} else {
					all64Btn[btnCtr].setId("btn_" + btnCtr + "_"
							+ randNum2[k++]);
					alterFlag = true;
				}
				all64Btn[btnCtr].setPadding(new Insets(0, 0, 0, 0));
				all64Btn[btnCtr].setPrefHeight(50);
				all64Btn[btnCtr].setPrefWidth(50);
				all64Btn[btnCtr].setOnAction(this);
				all64Btn[btnCtr].setStyle("-fx-font-size: 12pt; -fx-background-color: #c3c4c4, linear-gradient(#d6d6d6 50%, white 100%), radial-gradient(center 50% -40%, radius 200%, #e6e6e6 45%, rgba(230,230,230,0) 50%);"
						+ " -fx-background-insets: 0,1,1; -fx-text-fill: black; -fx-effect: dropshadow(three-pass-box, blue, 3, 0.0, 0, 1);");

				buttonGp.add(all64Btn[btnCtr++], j, i);
			}
		}
		HBox buttonGpHb = new HBox();
		buttonGpHb.getChildren().add(buttonGp);
		buttonGpHb.setAlignment(Pos.CENTER);
		buttonGpHb.setPadding(new Insets(20, 0, 0, 0));
		mainLayoutBp.setCenter(buttonGpHb);
		mainLayoutBp.setStyle("-fx-background-color: skyblue;");

	}

	private boolean alterFlag = false;
	private int lastVal = 0, btnClk = 0, totalTry=0;
	private Button lastBtnPressed, secLastBtnPressed;

	// handling all button events
	public void handle(ActionEvent ae) {
		Button pressedBtn = (Button) ae.getSource();
		if (!"New Game".equals(pressedBtn.getText())) {
			String[] val = pressedBtn.getId().split("_");
			int num = Integer.parseInt(val[2]);

			// To provide flip animation on the buttons
			RotateTransition rt = new RotateTransition(Duration.millis(1000),
					pressedBtn);
			rt.setAxis(Rotate.Y_AXIS);
			rt.setFromAngle(0);
			rt.setToAngle(360);
			rt.setCycleCount(1);
			rt.play();
			//

			btnClk++;
			if (alterFlag) {
				pressedBtn.setGraphic(second32Iv[num - 1]);
				// check whether user has clicked on same button or not
				if (lastVal == num && pressedBtn != lastBtnPressed) {
					buttonGp.getChildren()
							.removeAll(pressedBtn, lastBtnPressed);
				}
				secLastBtnPressed = pressedBtn;
				alterFlag = false;
				lastVal = 0;
			} else {
				// after two buttons pressed removing last two buttons images
				if (btnClk == 3) {
					tryLbl.setText("Total Try : "+(++totalTry));
					lastBtnPressed.setGraphic(null);
					secLastBtnPressed.setGraphic(null);
					btnClk = 1;
				}
				lastVal = num;
				lastBtnPressed = pressedBtn;
				alterFlag = true;
				pressedBtn.setGraphic(first32Iv[num - 1]);
			}
		} else {
				add64Buttons(mainLayoutBp);
				initalDisplayImg();
				//after 2 sec all the images will be hidden
				 PauseTransition delay = new PauseTransition(Duration.seconds(4));
				 delay.setOnFinished(event -> {
					 hideAllImages();
			      });
				 delay.play();
		}
	}
}
