package com.company.sampleapp.view.area;

import com.company.sampleapp.entity.Area;

import com.company.sampleapp.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "areas", layout = MainView.class)
@ViewController("Area.list")
@ViewDescriptor("area-list-view.xml")
@LookupComponent("areasDataGrid")
@DialogMode(width = "64em")
public class AreaListView extends StandardListView<Area> {
}