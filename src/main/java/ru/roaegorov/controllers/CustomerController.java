package ru.roaegorov.controllers;

import lombok.NonNull;
import ru.roaegorov.entities.RequestCustomer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CustomerController", urlPatterns = "/processcustomer")
public class CustomerController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        RequestCustomer customer = RequestCustomer.fromRequestParameters(request);
        customer.setAsRequestAttributes(request);
        List<String> violations = customer.validate();

        if (!violations.isEmpty()) {
            request.setAttribute("violations", violations);
        }

        String url = determineUrl(violations);
        forwardResponse(url, request, response);
    }

    private String determineUrl(@NonNull List<String> violations) {
        if (!violations.isEmpty()) {
            return "/";
        } else {
            return "/WEB-INF/views/customerinfo.jsp";
        }
    }

    private void forwardResponse(@NonNull String url, HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

}
