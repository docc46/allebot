package com.kuros.automatgae;

import com.kuros.automatgae.connection.Connector;
import com.kuros.automatgae.model.*;
import com.kuros.automatgae.model.msg.MsgTemplate;
import com.kuros.automatgae.model.userOffers.UserOffersResponse;
import com.kuros.automatgae.repository.OrderRepository;
import com.kuros.automatgae.repository.TemplateRepository;
import com.kuros.automatgae.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;


@RestController
public class Controller {

    Logger logger = Logger.getLogger(Controller.class.getName());

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private TemplateRepository templateRepository;

    Connector connector = Connector.getInstance();

    ModelAndView homeModelAndView = new ModelAndView("index");
    ModelAndView dataModelAndView = new ModelAndView("data");
    ModelAndView codesModelAndView = new ModelAndView("codes");
    ModelAndView templatesModelAndView = new ModelAndView("templates");
    ModelAndView newTemplateModelAndView = new ModelAndView("newtemplate");

    private Sender sender = new Sender();

    @Scheduled(fixedRate = 20000)
    public void ha(){
        if(connector.getToken()!=null)
            connector.refreshToken(connector.getRefreshToken());
    }

    @RequestMapping(value = "/templates", method = RequestMethod.GET)
    public ModelAndView getTemplates(){
        List<MsgTemplate> templates = templateRepository.findAll();
        List<MsgTemplate> detailedTemplates = connector.getTemplatesDetails(templates);

        templatesModelAndView.addObject("templates",detailedTemplates);
        return templatesModelAndView;
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ModelAndView newTemplate(){
        if(connector.getToken()!=null) {
            UserOffersResponse resp = connector.getUsersOffers();
            newTemplateModelAndView.addObject("newtemp", new MsgTemplate());
            newTemplateModelAndView.addObject("auctions", resp);
        }
        return newTemplateModelAndView;
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public ModelAndView newTemplate(@ModelAttribute("newtemp") MsgTemplate msgTemplate){
        templateRepository.save(msgTemplate);
        return new ModelAndView("redirect:/templates");
    }

    @RequestMapping(value = "/codes", method = RequestMethod.GET)
    public ModelAndView getCodes(){
        codesModelAndView.addObject("voucher", new VoucherBatch());
        return codesModelAndView;
    }

    @RequestMapping(value = "/codes", method = RequestMethod.POST)
    public ModelAndView addCodes(@ModelAttribute("voucher") VoucherBatch voucherBatch){
        String[] lines = voucherBatch.getVouchers().split(" 00:59:59 ");
        for (String line : lines) {
            String[] voucherData = line.split(";");
            Voucher voucher = new Voucher();
            voucher.setTransId(voucherData[0]);
            voucher.setSerialNumber(voucherData[1]);
            voucher.setVoucherCode(voucherData[2]);
            voucher.setValue(voucherData[3].replace(',','.'));
            voucherRepository.save(voucher);
        }
        return codesModelAndView;
    }

    @RequestMapping(value = "/auth")
    public ModelAndView authorize(@RequestParam(value = "code", required = false) String code) {
        if (code != null && connector.getToken() == null) {
            connector.authorizeAndGetToken(code);
        }
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/")
    public ModelAndView home(){
        homeModelAndView.addObject("isConnected", connector.isConnected());
        return homeModelAndView;
    }
}
