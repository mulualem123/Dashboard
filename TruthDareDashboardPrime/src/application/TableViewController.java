package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;


public class TableViewController implements Initializable {

	@FXML
	private TableView<TruthDare> ques_Table;
	@FXML
	private TableColumn<TruthDare, Integer> id_col;
	@FXML
	private TableColumn<TruthDare, String> ques_col;
	@FXML
	private TableColumn<TruthDare, Integer> age_col;
	@FXML
	private TableColumn<TruthDare, String> gender_col;
	@FXML
	private TextField ipText;
	@FXML
	private TextField idTextField;
	@FXML
	private TextField quesTextField;
	@FXML
	private TextField ageTextField;
	@FXML
	private TextField gendTextField;

	String insertSql = "INSERT INTO `truthques`(`IDNum`,`Question`, `Age`, `Gender`) VALUES (?, ?, ?, ?)";

	String query = null;
	String ip = "127.0.0.1";
	Connection conn = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	TruthDare truthDare = null;
	int index = -1;

	ObservableList<TruthDare> QuesDareList = FXCollections.observableArrayList( );

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loadTruth();
		//System.out.println(QuesDareList.get(0).isQuestion());
	}


	@FXML
	public void loadDares() {
		//initialization
		insertSql = "INSERT INTO `dares`(`IDNum`,`dare`, `Age`, `Gender`) VALUES (?, ?, ?, ?)"; //initialization
		ipText.setText(ip);

		//Connection
		DBConnection dbClass = new DBConnection(ip);
		conn = dbClass.getConnection();
		//conn = DBConnection.getConnection();

		refreshTable("SELECT * FROM `dares`", "dare");

		ques_col.setText("Dares");
		id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
		ques_col.setCellValueFactory(new PropertyValueFactory<>("question"));
		age_col.setCellValueFactory(new PropertyValueFactory<>("age"));
		gender_col.setCellValueFactory(new PropertyValueFactory<>("gender"));

		idTextField.setText(Integer.valueOf(QuesDareList.size())+""); // can be formated 001


	}

	@FXML
	public void loadTruth() {
		//Connection
		DBConnection dbClass = new DBConnection(ip);
		conn = dbClass.getConnection();
		//conn = DBConnection.getConnection();

		//initialization
		insertSql = "INSERT INTO `truthques`(`IDNum`,`Question`, `Age`, `Gender`) VALUES (?, ?, ?, ?)"; 
		ipText.setText(ip);

		refreshTable("SELECT * FROM `truthques`", "question");

		ques_col.setText("Truths");
		id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
		ques_col.setCellValueFactory(new PropertyValueFactory<>("question"));
		age_col.setCellValueFactory(new PropertyValueFactory<>("age"));
		gender_col.setCellValueFactory(new PropertyValueFactory<>("gender"));

		idTextField.setText(Integer.valueOf(QuesDareList.size())+""); // can be formated 001


	}

	private void refreshTable(String queryHolder, String statementColum) {

		try { 
			QuesDareList.clear();
			query = queryHolder; 
			preparedStatement = conn.prepareStatement(query); 
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) { 
				QuesDareList.add(new TruthDare(resultSet.getInt("IDNum"),resultSet.getString(statementColum),resultSet.getInt("age"),resultSet.getString("gender") ));
				ques_Table.setItems(QuesDareList);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public  void updateIP () {

		ip=ipText.getText().toString();
		DBConnection dbClass = new DBConnection(ip);
		conn = dbClass.getConnection();
		JOptionPane.showMessageDialog(null, "connected");
		pageLoad();

	}
	@FXML
	public  void inserTruth () {
		conn = DBConnection.getConnection();

		try {
			preparedStatement = conn.prepareStatement(insertSql);

			preparedStatement.setString(1, idTextField.getText());
			preparedStatement.setString(2, quesTextField.getText());
			preparedStatement.setInt(3, Integer.parseInt(ageTextField.getText()));
			preparedStatement.setString(4, gendTextField.getText());

			preparedStatement.execute();

			cleanFields();

			pageLoad();

			//JOptionPane.showMessageDialog(null, "Question added successfuly!");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);

		}

	}	
	@FXML
	public void Edit() {
		try {
			String updateQue;
			if (ques_col.getText().equals("Truths")) {
				 updateQue = "UPDATE `truthques` SET `IDNum`='"+idTextField.getText()+"',`question`='"+quesTextField.getText()+"',`age`='"+ageTextField.getText()+"',`gender`='"+gendTextField.getText()+"' WHERE IDNum='"+idTextField.getText()+"'";
			}else {
				updateQue = "UPDATE `dares` SET `IDNum`='"+idTextField.getText()+"',`dare`='"+quesTextField.getText()+"',`age`='"+ageTextField.getText()+"',`gender`='"+gendTextField.getText()+"' WHERE IDNum='"+idTextField.getText()+"'";			}
			preparedStatement = conn.prepareStatement(updateQue);
			preparedStatement.execute();
			cleanFields();
			pageLoad();

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@FXML
	public void Delete() {
		try {
			preparedStatement = conn.prepareStatement("DELETE FROM `truthques` WHERE IDNum='"+idTextField.getText()+"'");
			preparedStatement.execute();
			cleanFields();

			pageLoad();
			cleanFields();

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void cleanFields() {
		quesTextField.clear();
		ageTextField.clear();
		gendTextField.clear();
	}
	public void pageLoad() {

		if (ques_col.getText().equals("Truths")) {
			loadTruth();
		}else {
			loadDares();
		}

	}
	public void getSelected(MouseEvent e) {
		index= ques_Table.getSelectionModel().getSelectedIndex();


		if(index > -1) {

			idTextField.setText(id_col.getCellData(index).toString());
			quesTextField.setText(ques_col.getCellData(index).toString());
			ageTextField.setText(age_col.getCellData(index).toString());
			gendTextField.setText(gender_col.getCellData(index).toString());

		}else {
			return;
		}

	}
}
