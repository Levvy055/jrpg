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
	requires org.apache.commons.lang3;
		
	opens pl.hopelew.jrpg.controllers to javafx.fxml;
	opens pl.hopelew.jrpg.controllers.mainmenu to javafx.fxml;
}