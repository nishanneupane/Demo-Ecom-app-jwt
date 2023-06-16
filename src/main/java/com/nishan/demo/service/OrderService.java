package com.nishan.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.nishan.demo.dto.CartDtoItem;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.param.checkout.SessionCreateParams.LineItem;
import com.stripe.param.checkout.SessionCreateParams.Mode;
import com.stripe.param.checkout.SessionCreateParams.PaymentMethodType;

@Service
public class OrderService {
	@Value("${BASE_URL}")
	private String baseUrl;
	
	@Value("${STRIPE_SECRET_KEY}")
	private String apikey;
	
	public Session createSession(List<CartDtoItem> checkoutItemDtos) throws StripeException{
		
		//sucess and failure url
		
		String sucessurl=baseUrl+"payment/sucess";
		
		String failureUrl=baseUrl+"payment/failed";
		
		Stripe.apiKey=apikey;
		
		List<com.stripe.param.checkout.SessionCreateParams.LineItem>  sessionItemList=new ArrayList<>();
		
		for(CartDtoItem checkoutItemDto:checkoutItemDtos) {
			sessionItemList.add(createSessionLineItem(checkoutItemDto));
		}
		
		SessionCreateParams createParams=SessionCreateParams.builder()
				.addPaymentMethodType(PaymentMethodType.CARD)
				.setMode(Mode.PAYMENT)
				.setCancelUrl(failureUrl)
				.setSuccessUrl(sucessurl)
				.addAllLineItem(sessionItemList)
				.build();
		
		return Session.create(createParams);
	}

	private LineItem createSessionLineItem(CartDtoItem checkoutItemDto) {
		
		return SessionCreateParams.LineItem.builder()
				.setPriceData(createPriceData(checkoutItemDto))
				.setQuantity((Long.parseLong(String.valueOf(checkoutItemDto.getQuantity()))))
				.build()
				;
	}

	private SessionCreateParams.LineItem.PriceData createPriceData(CartDtoItem checkoutItemDto) {
		
		return SessionCreateParams.LineItem.PriceData.builder().setCurrency("usd")
				.setUnitAmount((long)checkoutItemDto.getPrice()*100)
				.setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
				.setName(checkoutItemDto.getProductName())
				.build()).build();
	}

}
