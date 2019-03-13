module jrpg {
	exports pl.hopelew.jrpg;

	requires transitive javafx.base;
	requires transitive javafx.graphics;

	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.web;
	
	requires java.logging;
	
	requires transitive org.scenicview.scenicview;
	requires org.controlsfx.controls;
	
	requires static lombok;
		
	opens pl.hopelew.jrpg to javafx.fxml;
}