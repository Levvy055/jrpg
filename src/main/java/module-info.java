open module jrpg {
	exports pl.hopelew.jrpg;
	exports pl.hopelew.jrpg.entities;

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
	requires com.jfoenix;
	requires com.google.common;
	requires java.desktop;
	requires gson;
	requires java.sql;
	
//  requires fxgl.all;

//  opens pl.hopelew.jrpg.controllers to javafx.fxml;
//  opens pl.hopelew.jrpg.controllers.mainmenu to javafx.fxml; 
//  opens pl.hopelew.jrpg.controllers.game to javafx.fxml, fxgl.all;

}