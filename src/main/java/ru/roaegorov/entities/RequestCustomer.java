package ru.roaegorov.entities;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import ru.roaegorov.validators.EmailValidator;
import ru.roaegorov.validators.StringValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class RequestCustomer {

    private final String firstName;
    private final String lastName;
    private final String email;

    public static RequestCustomer fromRequestParameters(@NonNull HttpServletRequest request) {
        return new RequestCustomer(
                request.getParameter("firstname"),
                request.getParameter("lastname"),
                request.getParameter("email"));
    }

    public void setAsRequestAttributes(@NonNull HttpServletRequest request) {
        request.setAttribute("firstname", firstName);
        request.setAttribute("lastname", lastName);
        request.setAttribute("email", email);
    }

    public List<String> validate() {
        List<String> violations = new ArrayList<>();
        if (!StringValidator.validate(firstName)) {
            violations.add("First Name is mandatory");
        }
        if (!StringValidator.validate(lastName)) {
            violations.add("Last Name is mandatory");
        }
        if (!EmailValidator.validate(email)) {
            violations.add("Email must be a well-formed address");
        }
        return violations;
    }
}