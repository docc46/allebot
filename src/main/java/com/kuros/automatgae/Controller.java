package com.kuros.automatgae;

import com.kuros.automatgae.connection.Connector;
import com.kuros.automatgae.model.*;
import com.kuros.automatgae.model.messages.MsgTemplate;
import com.kuros.automatgae.model.orderEvents.OrderEvent;
import com.kuros.automatgae.model.orderEvents.OrderEventResponse;
import com.kuros.automatgae.model.userOffers.UserOffersResponse;
import com.kuros.automatgae.repository.OrderRepository;
import com.kuros.automatgae.repository.TemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;


@RestController
public class Controller {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TemplateRepository templateRepository;

    Connector connector = Connector.getInstance();

    ModelAndView homeModelAndView = new ModelAndView("index");
    ModelAndView templatesModelAndView = new ModelAndView("templates");
    ModelAndView newTemplateModelAndView = new ModelAndView("newtemplate");
    ModelAndView historyModelAndView = new ModelAndView("history");
    ModelAndView apiLoginModelAndView = new ModelAndView("login");

    private Sender sender = new Sender();

    @Scheduled(fixedRate = 20000)
    public void refreshToken() {
        if (connector.getToken() != null)
            connector.refreshToken(connector.getRefreshToken());
    }

    @RequestMapping(value = "/history", method = RequestMethod.GET)
    public ModelAndView getHistory() {
        historyModelAndView.addObject("orders",orderRepository.findByOrderByBoughtAtDesc());
        return historyModelAndView;
    }

    @RequestMapping(value = "/templates", method = RequestMethod.GET)
    public ModelAndView getTemplates() {
        List<MsgTemplate> templates = templateRepository.findAll();
        List<MsgTemplate> detailedTemplates = connector.getTemplatesDetails(templates);

        templatesModelAndView.addObject("templates", detailedTemplates);
        return templatesModelAndView;
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ModelAndView newTemplate() {
        if (connector.getToken() != null) {
            UserOffersResponse resp = connector.getUsersOffers();
            newTemplateModelAndView.addObject("newtemp", new MsgTemplate());
            newTemplateModelAndView.addObject("auctions", resp);
        }
        return newTemplateModelAndView;
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public ModelAndView newTemplate(@ModelAttribute("newtemp") MsgTemplate msgTemplate) {
        templateRepository.save(msgTemplate);
        return new ModelAndView("redirect:/templates");
    }

    @RequestMapping(value = "/auth")
    public ModelAndView authorize(@RequestParam(value = "code", required = false) String code) {
        if (code != null && connector.getToken() == null) {
            connector.authorizeAndGetToken(code);
        }
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/")
    public ModelAndView home() {
        if(connector.isConnected()){
            homeModelAndView.addObject("activeOffers", connector.getUsersOffers().getCount());
            homeModelAndView.addObject("activeTemplates", templateRepository.count());
            homeModelAndView.addObject("mailsSent", orderRepository.countByMsgSentTrue());
            return homeModelAndView;
        }
        else return apiLoginModelAndView;
    }

    @Scheduled(fixedDelay = 10000)
    public void refreshOrders() throws IOException, MessagingException {
        if (connector.getToken() != null) {
            OrderEventResponse orderEventResponse = connector.getOrderEvents();
            if (orderEventResponse.getEvents().size() > 0)
                connector.setLastSeenId(orderEventResponse.getEvents().get(orderEventResponse.getEvents().size() - 1).getId());
            for (OrderEvent event : orderEventResponse.getEvents()) {
                for (Order order : event.getOrders()) {
                    Optional<MsgTemplate> template = templateRepository.findById(order.getOfferId());
                    if (template.isPresent()) {
                        sender.sendMessage(order.getEmail(), "Allegro purchase", template.get().getText());
                        order.setMsgSent(true);
                    }
                    orderRepository.save(order);
                }
            }
        }
    }
}
