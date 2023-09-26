package com.company.sampleapp.view.area;

import com.company.sampleapp.entity.Area;

import com.company.sampleapp.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "areas/:id", layout = MainView.class)
@ViewController("Area.detail")
@ViewDescriptor("area-detail-view.xml")
@EditedEntityContainer("areaDc")
public class AreaDetailView extends StandardDetailView<Area> {
}