open module jrpg {
	exports pl.hopelew.jrpg;
	exports pl.hopelew.jrpg.entities;

	requires transitive javafx.base;
	requires transitive javafx.graphics;

	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.web;

	requires java.logging;
	requires java.desktop;
	requires java.sql;

	requires transitive org.scenicview.scenicview;

	requires org.controlsfx.controls;
	requires com.jfoenix;

	requires static lombok;
	
	requires gson;
	requires com.google.common;
	requires org.apache.commons.lang3;
	requires org.apache.logging.log4j;
	
//  requires fxgl.all;

//  opens pl.hopelew.jrpg.controllers to javafx.fxml;
//  opens pl.hopelew.jrpg.controllers.mainmenu to javafx.fxml; 
//  opens pl.hopelew.jrpg.controllers.game to javafx.fxml, fxgl.all;

}