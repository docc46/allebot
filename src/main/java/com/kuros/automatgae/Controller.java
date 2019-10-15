package com.kuros.automatgae;

import com.kuros.automatgae.connection.Connector;
import com.kuros.automatgae.model.*;
import com.kuros.automatgae.model.orderEvents.OrderEvent;
import com.kuros.automatgae.model.orderEvents.OrderEventResponse;
import com.kuros.automatgae.repository.OrderRepository;
import com.kuros.automatgae.repository.TpayOrderRepository;
import com.kuros.automatgae.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;


@RestController
public class Controller {

    Logger logger = Logger.getLogger(Controller.class.getName());

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private TpayOrderRepository tpayOrderRepository;

    ModelAndView efcModelAndView = new ModelAndView("efc");
    ModelAndView dataModelAndView = new ModelAndView("data");
    ModelAndView codesModelAndView = new ModelAndView("codes").addObject("senderStatus",Connector.isSenderOn());

    private Sender sender = new Sender();

    @RequestMapping(value = "/listentpay", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String getTpayTransaction1(@RequestParam Map<String, String> parameters) throws IOException, MessagingException {
        logger.info("cos przyszlo :)");
        logger.info(parameters.toString());
        TpayOrder tpayOrder = new TpayOrder(parameters.get("tr_id"),parameters.get("tr_date"),parameters.get("tr_amount"),parameters.get("tr_paid"),parameters.get("tr_error"),
                parameters.get("tr_email"),null);
        tpayOrderRepository.save(tpayOrder);
        sendTpayCode(tpayOrder);
        return "TRUE";
    }

    @RequestMapping(value = "/sender", method = RequestMethod.POST)
    public ModelAndView switchSender(){
        Connector.setSenderOn(!Connector.isSenderOn());
        efcModelAndView.addObject("senderStatus",Connector.isSenderOn());
        return efcModelAndView;
    }

    @RequestMapping(value = "/codes", method = RequestMethod.POST)
    public ModelAndView AddCodes(@ModelAttribute("voucher") VoucherBatch voucherBatch){
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

    @RequestMapping(value = "/codes", method = RequestMethod.GET)
    public ModelAndView getCodes(){
        codesModelAndView.addObject("voucher", new VoucherBatch());
        return codesModelAndView;
    }

    @RequestMapping
    public ModelAndView authorize(@RequestParam(value = "code", required = false) String code) {

        if (code != null && Connector.getToken() == null) {
		    Connector.authorizeAndGetToken(code);
 		
     		efcModelAndView.addObject("token", Connector.getToken());
            efcModelAndView.addObject("expires",Connector.getExpiresin());
        }
        return efcModelAndView;
    }

    @RequestMapping(value = "/data")
    public ModelAndView getData(@RequestParam(value = "offerid", required = false) String offerId){
        if(offerId==null) dataModelAndView.addObject("data",orderRepository.findAll());
        else dataModelAndView.addObject("data",orderRepository.findByOfferId(offerId));
        return dataModelAndView;
    }

    public void sendTpayCode(TpayOrder tpayOrder) throws IOException, MessagingException {
        Voucher voucher = voucherRepository.findFirstBySentFalseAndValue(tpayOrder.getTr_paid());
        if(voucher!=null){
            tpayOrder.setVoucher(voucher);
            voucher.setSent(true);
            voucher.setTpayOrder(tpayOrder);
            voucherRepository.save(voucher);
            sender.sendMessage(tpayOrder.getTr_email(),"PSC24.pl - Twój kod Paysafecard","oto kody",voucher);
        }
        tpayOrderRepository.save(tpayOrder);
    }

    public void sendAllegroCodes(Order order) throws MessagingException, IOException {
            for (int i = 0; i < order.getQuantity(); i++) {
                Voucher voucher = voucherRepository.findFirstBySentFalseAndValue(OfferResolver.resolveIdToVoucherValue(order.getOfferId()));
                if(voucher!=null) {
                    order.getVouchers().add(voucher);
                    voucher.setSent(true);
                    voucher.setOrder(order);
                    voucherRepository.save(voucher);
                    sender.sendMessage(order.getEmail(),"PSC Allegro", "oto kod", voucher);
                }
            }
            orderRepository.save(order);
    }

    @Scheduled(fixedDelay = 10000)
    public void refresh() throws IOException, MessagingException {
        if (Connector.getToken()!=null){
            logger.info("refreshing data...");
            OrderEventResponse orderEventResponse = Connector.getOrderEvents();
            System.out.println(orderEventResponse.getEvents().size());
            if(orderEventResponse.getEvents().size()>0)
            Connector.setLastSeenId(orderEventResponse.getEvents().get(orderEventResponse.getEvents().size()-1).getId());
            for (OrderEvent event : orderEventResponse.getEvents()) {
                for (Order order : event.getOrders()) {
                    orderRepository.save(order);
                    if(Connector.isSenderOn()) {
                        logger.info("sending codes");
                        sendAllegroCodes(order);
                    }
                }
            }
            logger.info("data fresh!");
            logger.info("sender: " + Connector.isSenderOn());
        }
    }

}
