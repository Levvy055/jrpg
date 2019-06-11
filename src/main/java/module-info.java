open module jrpg {
	exports pl.hopelew.jrpg;
	exports pl.hopelew.jrpg.entities;
	exports org.mapeditor.core;

	requires transitive javafx.base;
	requires transitive javafx.graphics;
	requires javafx.controls;
	requires javafx.fxml;

	requires java.desktop;
	requires java.compiler;
	requires java.logging;
	requires java.sql;
	requires java.xml;
	requires java.xml.bind;

	requires org.controlsfx.controls;
	requires com.jfoenix;
	requires eu.hansolo.tilesfx;
	
	requires gson;
	requires com.google.common;
	requires org.apache.commons.lang3;
	requires org.apache.logging.log4j;
	
	requires static lombok;
	requires jdk.management.agent;
	requires jnativehook;
	
//  requires fxgl.all;

//  opens pl.hopelew.jrpg.controllers to javafx.fxml;
//  opens pl.hopelew.jrpg.controllers.mainmenu to javafx.fxml; 
//  opens pl.hopelew.jrpg.controllers.game to javafx.fxml, fxgl.all;

}