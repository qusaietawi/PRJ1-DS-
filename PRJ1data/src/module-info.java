module PRJ1data {
	requires javafx.controls;
	 opens application to javafx.base;
	    exports application to javafx.graphics;

}