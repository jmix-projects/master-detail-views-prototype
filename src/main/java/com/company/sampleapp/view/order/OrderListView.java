package com.company.sampleapp.view.order;

import com.company.sampleapp.entity.Order;
import com.company.sampleapp.entity.OrderLine;
import com.company.sampleapp.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.component.UiComponentUtils;
import io.jmix.flowui.component.tabsheet.JmixTabSheet;
import io.jmix.flowui.kit.action.Action;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.kit.component.HasActions;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.*;
import io.jmix.flowui.view.*;

@Route(value = "orders", layout = MainView.class)
@ViewController("Order.list")
@ViewDescriptor("order-list-view.xml")
@LookupComponent("ordersDataGrid")
@DialogMode(width = "64em")
public class OrderListView extends StandardListView<Order> {
    @ViewComponent
    private CollectionLoader<Order> ordersDl;
    @ViewComponent
    private VerticalLayout listLayout;
    @ViewComponent
    private FormLayout form;
    @ViewComponent
    private JmixButton saveBtn;
    @ViewComponent
    private JmixButton cancelBtn;
    @ViewComponent
    private DataContext dataContext;
    @ViewComponent
    private InstanceContainer<Order> orderDc;
    @ViewComponent
    private CollectionContainer<Order> ordersDc;
    @ViewComponent
    private InstanceLoader<Order> orderDl;
    @ViewComponent
    private JmixTabSheet tabSheet;

    @Subscribe
    public void onInit(final InitEvent event) {
        ordersDl.setDataContext(null);
        updateControls(false);
    }

    @Subscribe("ordersDataGrid.create")
    public void onCustomersDataGridCreate(final ActionPerformedEvent event) {
        dataContext.clear();
        Order entity = dataContext.create(Order.class);
        orderDc.setItem(entity);
        updateControls(true);
    }

    @Subscribe("ordersDataGrid.edit")
    public void onCustomersDataGridEdit(final ActionPerformedEvent event) {
        form.setEnabled(true);
        updateControls(true);
    }

    @Subscribe("saveBtn")
    public void onSaveBtnClick(final ClickEvent<JmixButton> event) {
        dataContext.save();
        ordersDc.replaceItem(orderDc.getItem());
        updateControls(false);
    }

    @Subscribe("cancelBtn")
    public void onCancelBtnClick(final ClickEvent<JmixButton> event) {
        dataContext.clear();
        orderDl.load();
        updateControls(false);
    }

    @Subscribe(id = "ordersDc", target = Target.DATA_CONTAINER)
    public void onCustomersDcItemChange(final InstanceContainer.ItemChangeEvent<Order> event) {
        Order entity = event.getItem();
        dataContext.clear();
        if (entity != null) {
            orderDl.setEntityId(entity.getId());
            orderDl.load();
        } else {
            orderDc.setItem(null);
        }
    }

    @Install(to = "orderLinesDataGrid.create", subject = "initializer")
    private void orderLinesDataGridCreateInitializer(final OrderLine orderLine) {
        orderLine.setOrder(orderDc.getItem());
    }

    private void updateControls(boolean editing) {
        for (Component component : UiComponentUtils.getComponents(tabSheet)) {
            if (component instanceof HasValueAndElement<?, ?> field) {
                field.setReadOnly(!editing);
            }
            if (component instanceof HasActions grid) {
                for (Action action : grid.getActions()) {
                    action.setEnabled(editing);
                }
            }
        }
        saveBtn.setEnabled(editing);
        cancelBtn.setEnabled(editing);
        listLayout.setEnabled(!editing);
    }
}