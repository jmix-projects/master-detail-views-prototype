package com.company.sampleapp.view.orderline;

import com.company.sampleapp.entity.OrderLine;

import com.company.sampleapp.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "orderLines/:id", layout = MainView.class)
@ViewController("OrderLine.detail")
@ViewDescriptor("order-line-detail-view.xml")
@EditedEntityContainer("orderLineDc")
public class OrderLineDetailView extends StandardDetailView<OrderLine> {
}