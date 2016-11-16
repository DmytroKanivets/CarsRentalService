package com.mkr.managedbeans;

import com.mkr.dao.ClientsDAO;
import org.apache.logging.log4j.LogManager;

import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@ManagedBean(name = "navigation")
@ApplicationScoped
public class NavigationController {

    public String getAllOrdersPage() {
        return "/admin/allOrders?faces-redirect=true";
    }

    public String getCarStatusPage() {
        return "/admin/carStatus";
    }

    public String getCarsPage() {
        return "/admin/cars?faces-redirect=true";
    }

    public String getNewOrderPage() {
        return "/cars/newOrder?faces-redirect=true";
    }

    public String getLoginPage() {
        return "/login?faces-redirect=true";
    }

    public String getOrdersPage() {
        return "/cars/orders?faces-redirect=true";
    }

    public String getSelectCarPage() {
        return "/cars/selectCar";
    }

    public String getRegistrationPage() {
        return "/register?faces-redirect=true";
    }

    public String getLogoutPage() {
        getRequest().getSession().invalidate();
        return getIndexPage();
    }

    public String getIndexPage() {
        return "/index?faces-redirect=true";
    }

    public String getAddNewsPage() {
        return "/admin/addNews?faces-redirect=true";
    }

    public String getAddCarPage() {
        return "/admin/addCar?faces-redirect=true";
    }

    @EJB
    ClientsDAO clientsDAO;

    private HttpServletRequest getRequest() {
        return (HttpServletRequest) (FacesContext.getCurrentInstance().getExternalContext().getRequest());
    }

    private String login;
    private String password;

    public String logIn() {
        HttpServletRequest request = getRequest();

        try {
            request.login(login, password);
        } catch (ServletException exception) {
            return "/error?face-redirect=true";
        }
        return "/index?faces-redirect=true";
    }

    public boolean isUser() {
        return getRequest().isUserInRole("user");
    }

    public boolean isAdmin() {
        return getRequest().isUserInRole("admin");
    }

    public String getUserName() {
        String login = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();

        if (login == null)
            return "Guest";
        return clientsDAO.getClientByLogin(login).getName();
    }

    public boolean isLoggedIn() {
        return FacesContext.getCurrentInstance().getExternalContext().getRemoteUser() != null;
    }

    public String getName() {
        return  FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
