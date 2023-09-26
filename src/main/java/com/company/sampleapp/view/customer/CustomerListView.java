package com.company.sampleapp.view.customer;

import com.company.sampleapp.entity.Customer;
import com.company.sampleapp.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.kit.action.BaseAction;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.*;
import io.jmix.flowui.view.*;

@Route(value = "customers", layout = MainView.class)
@ViewController("Customer.list")
@ViewDescriptor("customer-list-view.xml")
@LookupComponent("customersDataGrid")
@DialogMode(width = "64em")
public class CustomerListView extends StandardListView<Customer> {

    @ViewComponent
    private DataContext dataContext;
    @ViewComponent
    private CollectionContainer<Customer> customersDc;
    @ViewComponent
    private InstanceContainer<Customer> customerDc;
    @ViewComponent
    private CollectionLoader<Customer> customersDl;
    @ViewComponent
    private InstanceLoader<Customer> customerDl;
    @ViewComponent("customersDataGrid.edit")
    private BaseAction customersDataGridEdit;
    @ViewComponent
    private JmixButton saveBtn;
    @ViewComponent
    private JmixButton cancelBtn;
    @ViewComponent
    private FormLayout form;
    @ViewComponent
    private VerticalLayout listLayout;

    @Subscribe
    public void onInit(final InitEvent event) {
        customersDl.setDataContext(null);

        updateControls(false);
    }

    @Subscribe("customersDataGrid.create")
    public void onCustomersDataGridCreate(final ActionPerformedEvent event) {
        dataContext.clear();
        Customer entity = dataContext.create(Customer.class);
        customerDc.setItem(entity);
        updateControls(true);
    }

    @Subscribe("customersDataGrid.edit")
    public void onCustomersDataGridEdit(final ActionPerformedEvent event) {
        updateControls(true);
    }

    @Subscribe("saveBtn")
    public void onSaveBtnClick(final ClickEvent<JmixButton> event) {
        dataContext.save();
        customersDc.replaceItem(customerDc.getItem());
        updateControls(false);
    }

    @Subscribe("cancelBtn")
    public void onCancelBtnClick(final ClickEvent<JmixButton> event) {
        dataContext.clear();
        customerDl.load();
        updateControls(false);
    }

    @Subscribe(id = "customersDc", target = Target.DATA_CONTAINER)
    public void onCustomersDcItemChange(final InstanceContainer.ItemChangeEvent<Customer> event) {
        Customer entity = event.getItem();
        dataContext.clear();
        if (entity != null) {
            customerDl.setEntityId(entity.getId());
            customerDl.load();
            customersDataGridEdit.setEnabled(true);
        } else {
            customerDc.setItem(null);
            customersDataGridEdit.setEnabled(false);
        }
    }

    private void updateControls(boolean editing) {
        form.getChildren().forEach(component -> {
            if (component instanceof HasValueAndElement<?,?> field) {
                field.setReadOnly(!editing);
            }
        });
        saveBtn.setEnabled(editing);
        cancelBtn.setEnabled(editing);
        listLayout.setEnabled(!editing);
    }
}