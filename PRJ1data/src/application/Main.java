package application;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;

import java.io.FileReader;

public class Main extends Application {
	MyList<Employee> mylist = new MyList<>(100); // Custom list to store employees with an initial size of 100
	TableView<Employee> tableView = new TableView<>();

	ObservableList<Employee> employeeList = FXCollections.observableArrayList();
	String EmployeeID;
	String Name;
	String Age;
	String Department;
	String DateOfJoining;
	String Gender;

	public static void main(String[] args) {

		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		FileChooser fileChooser = new FileChooser(); // File chooser for selecting employee data file
		fileChooser.setTitle("Open Employee Data File");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

		File file = fileChooser.showOpenDialog(primaryStage);

		if (file != null) { // If a file is selected
			try (BufferedReader read = new BufferedReader(new FileReader(file))) {
				String line;
				boolean firstLine = true; // Skip the first line (header)
				while ((line = read.readLine()) != null) {
					if (firstLine) {
						firstLine = false; // Continue after skipping the first line
						continue;
					}
					String[] data = line.split(",");
					if (data.length == 6) { // Ensure the correct number of columns (6)
						String employeeID = data[0];
						String name = data[1];
						String age = data[2];
						String department = data[3];
						String dateOfJoining = data[4];
						String gender = data[5];
						int age1 = Integer.parseInt(age); // Convert age to integer
						Employee employee = new Employee(employeeID, name, age1, department, dateOfJoining, gender);
						mylist.insert(employee);
					} else {
						System.out.println("Invalid data format: " + line);
					}
				}
			} catch (IOException ex) {
				System.out.println("Error reading the file.");
			} catch (NumberFormatException ex) {
				System.out.println("Invalid input in age.");
			} catch (Exception ex) {
				System.out.println("Invalid input.");
			}
		} else {

			System.out.println("File selection canceled."); // If the user cancels the file selection
		}
		// Create TabPane with multiple tabs for different operations
		TabPane tabPane = new TabPane();
		Tab tab1 = new Tab("Add New Employee");
		tab1.setContent(addNewEmployee());
		Tab tab2 = new Tab("Delete Employee by ID");
		tab2.setContent(deleteEmployeeById());
		Tab tab3 = new Tab("Search Employee by ID");
		tab3.setContent(searchEmployeeById());
		Tab tab4 = new Tab("Employee Statistics");
		tab4.setContent(displayEmployeeStatistics());
		Tab tab5 = new Tab("Employee Table View");
		tab5.setContent(createEmployeeTable());
		tabPane.getTabs().addAll(tab1, tab2, tab3, tab4, tab5);
		// TableView<Employee> employeeTableView = createEmployeeTable();
		VBox vbox = new VBox(10, tabPane);
		Scene scene = new Scene(vbox, 800, 600);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	// Method to create the TableView for displaying employee data

	TableView<Employee> createEmployeeTable() {

		TableColumn<Employee, String> idColumn = new TableColumn<>(" Employee ID");
		idColumn.setCellValueFactory(new PropertyValueFactory<>("employeeID"));

		TableColumn<Employee, String> nameColumn = new TableColumn<>("Aame");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

		TableColumn<Employee, Integer> ageColumn = new TableColumn<>("Age");
		ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));

		TableColumn<Employee, String> departmentColumn = new TableColumn<>("Department");
		departmentColumn.setCellValueFactory(new PropertyValueFactory<>("department"));

		TableColumn<Employee, String> dateColumn = new TableColumn<>("Date Of Joining ");
		dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfJoining"));

		TableColumn<Employee, String> genderColumn = new TableColumn<>("Gender");
		genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));

		tableView.getColumns().addAll(idColumn, nameColumn, ageColumn, departmentColumn, dateColumn, genderColumn);

		for (int i = 0; i < mylist.size(); i++) {
			Employee employee = mylist.getAt(i);
			if (employee != null) {
				employeeList.add(employee);// Add employees to the observable list
			}
		}

		tableView.setItems(employeeList); // Set the observable list to the TableView
		return tableView;
	}

	// Method for adding a new employee

	private VBox addNewEmployee() {
		Label label1 = new Label("Employee ID:");
		TextField idField = new TextField();
		Label label2 = new Label("Name:");
		TextField nameField = new TextField();
		Label label3 = new Label("Age:");
		TextField ageField = new TextField();
		Label label4 = new Label("Department:");
		TextField deptField = new TextField();
		Label label5 = new Label("Date of Joining:");
		TextField dateField = new TextField();
		Label label6 = new Label("Gender:");
		TextField genderField = new TextField();
		Button saveButton = new Button("Save");
		saveButton.setOnAction(event -> saveEmployeesToFile());

		Button addButton = new Button("Add Employee");
		addButton.setOnAction(event -> {
			EmployeeID = idField.getText();
			Name = nameField.getText();
			Age = ageField.getText();
			Department = deptField.getText();
			DateOfJoining = dateField.getText();
			Gender = genderField.getText();

			if (EmployeeID.isEmpty() || Name.isEmpty() || Age.isEmpty() || Department.isEmpty()
					|| DateOfJoining.isEmpty() || Gender.isEmpty()) {
				Alert alert = new Alert(Alert.AlertType.WARNING);
				alert.setTitle("Validation Error");
				alert.setContentText("Please fill all fields.");
				alert.showAndWait();
				return;
			}

			try {
				// Validate age input

				int Age1 = Integer.parseInt(Age);
				if (Age1 <= 0) {
					Alert alert = new Alert(Alert.AlertType.WARNING);
					alert.setTitle("Validation Error");
					alert.setContentText("Age must be a positive number.");
					alert.showAndWait();
					return;

				}
				// Validate gender input

				if (!Gender.equalsIgnoreCase("male") && !Gender.equalsIgnoreCase("female")) {
					Alert alert0 = new Alert(Alert.AlertType.WARNING);
					alert0.setTitle("Validation Error");
					alert0.setContentText("Gender must be either 'male' or 'female'.");
					alert0.showAndWait();
					return;

				}

				Employee employee = new Employee(EmployeeID, Name, Age1, Department, DateOfJoining, Gender);
				mylist.insert(employee);
				employeeList.add(employee); // Update the TableView's ObservableList

				// Clear input fields
				idField.clear();
				nameField.clear();
				ageField.clear();
				deptField.clear();
				dateField.clear();
				genderField.clear();

				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setContentText("Employee added successfully.");
				alert.showAndWait();
			} catch (NumberFormatException e) {
				Alert alert = new Alert(Alert.AlertType.WARNING);
				alert.setTitle("Validation Error");
				alert.setContentText("Age must be a valid number.");
				alert.showAndWait();
			}
		});

		HBox buttons = new HBox(10, addButton, saveButton);
		VBox vbox = new VBox(10, label1, idField, label2, nameField, label3, ageField, label4, deptField, label5,
				dateField, label6, genderField, buttons);
		return vbox;
	}
	// Method for deleting employee by ID

	private VBox deleteEmployeeById() {
		Label label1 = new Label("Enter Employee ID to Delete:");
		TextField idField = new TextField();
		Button deleteButton = new Button("Delete Employee");
		Button saveButton = new Button("Save");
		saveButton.setOnAction(event -> saveEmployeesToFile());

		deleteButton.setOnAction(event -> {
			String employeeID = idField.getText();
			Employee tempEmployee = new Employee(employeeID, "", 0, "", "", "");
			boolean isDeleted = mylist.delete(tempEmployee);

			if (isDeleted) {
				// Remove from the ObservableList
				employeeList.removeIf(employee -> employee.getEmployeeID().equals(employeeID));
			}

			Alert alert = new Alert(isDeleted ? AlertType.INFORMATION : AlertType.ERROR);
			alert.setContentText(isDeleted ? "Employee deleted successfully." : "Employee not found.");
			alert.showAndWait();

			// Clear the input field
			idField.clear();
		});
		HBox buttons = new HBox(10, deleteButton, saveButton);
		VBox vbox = new VBox(10, label1, idField, buttons);
		return vbox;
	}
	// Method for searching employee by ID

	private VBox searchEmployeeById() {
		Label label1 = new Label("Enter Employee ID to Search:");
		TextField idField = new TextField();
		Button searchButton = new Button("Search Employee");

		searchButton.setOnAction(event -> {
			String employeeID = idField.getText();
			Employee tempEmployee = new Employee(employeeID, "", 0, "", "", "");
			boolean isFound = mylist.search(tempEmployee);

			Alert alert = new Alert(isFound ? AlertType.INFORMATION : AlertType.ERROR);
			if (isFound) {
				Employee foundEmployee = getEmployeeById(employeeID);
				StringBuilder employeeDetails = new StringBuilder();
				employeeDetails.append("Employee ID: ").append(foundEmployee.getEmployeeID()).append("\n")
						.append("Name: ").append(foundEmployee.getName()).append("\n").append("Age: ")
						.append(foundEmployee.getAge()).append("\n").append("Department: ")
						.append(foundEmployee.getDepartment()).append("\n").append("Date of Joining: ")
						.append(foundEmployee.getDateOfJoining()).append("\n").append("Gender: ")
						.append(foundEmployee.getGender());
				alert.setContentText(employeeDetails.toString());
			} else {
				alert.setContentText("Employee not found.");
			}
			alert.showAndWait();

			idField.clear();
		});

		VBox vbox = new VBox(10, label1, idField, searchButton);
		return vbox;
	}

	private Employee getEmployeeById(String employeeID) {
		for (int i = 0; i < mylist.size(); i++) {
			Employee employee = mylist.getAt(i);
			if (employee != null && employee.getEmployeeID().equals(employeeID)) {
				return employee;
			}
		}
		return null;
	}
// Method for displaying employee statistics

	private VBox displayEmployeeStatistics() {
		TabPane tabPane = new TabPane();

		// Tab for Age Statistics
		Tab ageTab = new Tab("Age Statistics");
		ageTab.setContent(createAgeStatisticsContent());

		// Tab for Gender Statistics
		Tab genderTab = new Tab("Gender Statistics");
		genderTab.setContent(createGenderStatisticsContent());

		// Tab for Department Statistics
		Tab departmentTab = new Tab("Department Statistics");
		departmentTab.setContent(createDepartmentStatisticsContent());

		tabPane.getTabs().addAll(ageTab, genderTab, departmentTab);

		VBox vbox = new VBox(10, tabPane);
		return vbox;
	}

	private VBox createAgeStatisticsContent() {
		int ageUnder30 = 0;
		int age30to50 = 0;
		int ageAbove50 = 0;

		for (int i = 0; i < mylist.size(); i++) {
			Employee employee = mylist.getAt(i);
			if (employee != null) {
				if (employee.getAge() < 30) {
					ageUnder30++;
				} else if (employee.getAge() <= 50) {
					age30to50++;
				} else {
					ageAbove50++;
				}
			}
		}
		Label ageStat = new Label("Under 30: " + ageUnder30 + "\n30 to 50: " + age30to50 + "\nAbove 50: " + ageAbove50);
		ageStat.setStyle("-fx-font-size: 16px;");
		VBox vbox = new VBox(10, ageStat);
		return vbox;
	}

	private VBox createGenderStatisticsContent() {
		int maleCount = 0;
		int femaleCount = 0;

		for (int i = 0; i < mylist.size(); i++) {
			Employee employee = mylist.getAt(i);
			if (employee != null) {
				if (employee.getGender().equalsIgnoreCase("Male")) {
					maleCount++;
				} else if (employee.getGender().equalsIgnoreCase("Female")) {
					femaleCount++;
				}
			}
		}

		Label genderStat = new Label("Male: " + maleCount + "\nFemale: " + femaleCount);
		genderStat.setStyle("-fx-font-size: 16px;");

		VBox vbox = new VBox(10, genderStat);
		return vbox;
	}

	private VBox createDepartmentStatisticsContent() {
		Label departmentLabel = new Label("Enter Department Name:");
		TextField departmentField = new TextField();
		Button departmentButton = new Button("Display Number of Employees");

		departmentButton.setOnAction(event -> {
			String department = departmentField.getText().trim();
			String deptStats = getEmployeeCountByDepartment(department);
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Employees Grouped by Department");
			alert.setContentText(deptStats);
			alert.showAndWait();
		});

		VBox vbox = new VBox(10);
		vbox.getChildren().addAll(departmentLabel, departmentField, departmentButton);
		return vbox;
	}

	private String getEmployeeCountByDepartment(String department) {
		int departmentCount = 0;

		for (int i = 0; i < mylist.size(); i++) {
			Employee employee = mylist.getAt(i);
			if (employee != null && employee.getDepartment().equalsIgnoreCase(department)) {
				departmentCount++;
			}
		}

		return department + ": " + departmentCount;
	}

	private void saveEmployeesToFile() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save Employee Data");
		fileChooser.setInitialFileName("updatedEmployee.txt");
		File file = fileChooser.showSaveDialog(null);

		if (file != null) {
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
				for (int i = 0; i < mylist.size(); i++) {
					Employee employee = mylist.getAt(i);
					if (employee != null) {
						writer.write(employee.getEmployeeID() + "," + employee.getName() + "," + employee.getAge() + ","
								+ employee.getDepartment() + "," + employee.getDateOfJoining() + ","
								+ employee.getGender());
						writer.newLine();
					}
				}

				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Save Successful");
				alert.setContentText("Employee data has been saved successfully.");
				alert.showAndWait();
			} catch (IOException e) {

				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("File Error");
				alert.setContentText("An error occurred while saving the file.");
				alert.showAndWait();
			}
		}
	}

}