package com.learning.springdemo.web;

import com.learning.springdemo.Order;
import com.learning.springdemo.User;
import com.learning.springdemo.data.OrderRepository;
import com.learning.springdemo.data.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.security.Principal;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
//@ConfigurationProperties(prefix = "taco.orders") // section to look up in configuration file
public class OrderController {

    private final OrderRepository orderRepo;
    private final UserRepository userRepo;

    private final OrderProps props;

//    private int pageSize = 20;
//    // This is invoked by Spring auto configuration?
//    public void setPageSize(int pageSize) {
//        this.pageSize = pageSize;
//    }

    public OrderController(OrderRepository ordeRepo, UserRepository userRepo, OrderProps props) {
        this.orderRepo = ordeRepo;
        this.userRepo = userRepo;
        this.props = props;
    }

    @GetMapping("/current")
    public String orderForm() {
        return "orderForm";
    }

    @GetMapping
    public String ordersForUser(@AuthenticationPrincipal User user, Model model) {
//        Pageable pageable = PageRequest.of(0, pageSize);
        Pageable pageable = PageRequest.of(0, props.getPageSize());
        model.addAttribute("orders", orderRepo.findByUserOrderByPlacedAtDesc(user, pageable));
        return "orderList";
    }

    @PostMapping
    public String processOrder(@Valid Order order, Errors errors,
                               SessionStatus sessionStatus,
                               @AuthenticationPrincipal User user) {
        if (errors.hasErrors()) {
            return "orderForm";
        }

        order.setUser(user);

        orderRepo.save(order);
        sessionStatus.setComplete();

        return "redirect:/";
    }
}
