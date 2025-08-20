package com.oracle.coffee.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oracle.coffee.dao.OrdersDao;
import com.oracle.coffee.dto.ClientDto;
import com.oracle.coffee.dto.PageRequestDto;
import com.oracle.coffee.dto.PageRespDto;
import com.oracle.coffee.dto.orders.OrdersDetailDto;
import com.oracle.coffee.dto.orders.OrdersDto;
import com.oracle.coffee.dto.orders.OrdersListDto;
import com.oracle.coffee.dto.orders.OrdersPageDto;
import com.oracle.coffee.dto.orders.OrdersProductDto;
import com.oracle.coffee.dto.orders.OrdersRefuseDto;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OrdersServiceImpl implements OrdersService {

	private final OrdersDao ordersDao;
	
	private final ClientService clientService;
	private final EmpService empService;
	private final EmailService emailService;
	
	@Scheduled(cron = "0 0 11 * * *")
	public void autoChangeOrdersDetailStatus() {
		// 납기일이 오늘인 ordersDetail status 출고완료(2)로 변경
		ordersDao.updateOrdersDetailsDelivery();

		System.out.println("==================================================");
		System.out.println("================ 오늘자 납품 출고 완료 ================");
		System.out.println("==================================================");
	}
	
	@Override
	public List<OrdersProductDto> getProducts() {
		// 판매 가능한 제품 목록
		// (isorder = true) && (isdel = false) && (price 존재)
		return ordersDao.getProducts();
	}
	
	@Override
	public int upsertInformation(OrdersDto order) {
		// 수주 정보 저장
		int orderCode = 0;
		List<OrdersDetailDto> details = order.getOrders_details();
		// create
		if (order.getOrder_code() == 0) {	
			// orders 생성
			ordersDao.createOrders(order);
			orderCode = order.getOrder_code();
			// ordersDetail
			for (OrdersDetailDto detail : details) {
				detail.setOrder_code(orderCode);
			}
		
		// update
		} else {
			// orders 비고 변경
			ordersDao.updateOrders(order);
			orderCode = order.getOrder_code();
			// ordersDetail (전체 엎어치기)
			ordersDao.deleteOrdersDetails(orderCode);
		}
		
		// ordersDetail 추가
		ordersDao.createOrdersDetails(details);		
		return orderCode;
	}

	@Override
	public OrdersDto get(int orderCode) {
		OrdersDto order = ordersDao.findByCode(orderCode);
		return order;
	}
	
	@Override
	public void request(int orderCode) {
		// 수주 요청 (가맹점 -> 본사)
		// 요청상태(1)로 변경 (orders)
		OrdersDto order = ordersDao.findByCode(orderCode);
		
		int targetStatus = 1;
		order.setOrder_status(targetStatus);
		ordersDao.requestOrders(order);
		
		// 담당자에게 요청 알림 이메일 전송
		try {
			ClientDto client = clientService.getSingleClient(order.getOrders_client_code());
			String targetMail = "min__37@naver.com";
			String title = "[수주 요청] " + client.getClient_name();
			String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); 
			String contents = now + "에 수주 요청이 등록되었습니다.\n\n"
					+ "상호명: " + client.getClient_name() + "\n"
					+ "총액: " + order.calculateTotalPrice() + "원";
			
			emailService.sendEmail(targetMail, title, contents);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private List<OrdersDetailDto> notApprovedDetails(OrdersDto order) {
		// orders_detail 수량 확인 후 "불가능"한 모든 orders_detail 리턴하는 메서드
		// detail 모두 가능한 경우: 수주 승인 상태(4)로 변경 
		// 						수주 상세 출고예정(1) 상태로 변경
		
		List<OrdersDetailDto> details = order.getOrders_details();
		List<Integer> approvedPrdCodes = ordersDao.approveOrdersDetails(order.getOrder_code());

		// 요청한 details 중 한 제품이라도 재고 모자라면 승인 불가
		// 프로시저에서 return된 approvedPrdCodes가 details와 다른 경우: 수주 승인 안됨
		// 모든 제품이 재고가 있으면 프로시저에서 이미 "출고예정"으로 변경됨 => 빈 리스트 return
		List<OrdersDetailDto> disabled = new ArrayList<>();
		for (OrdersDetailDto detail : details) {
			int orderedPrdCode = detail.getProduct_code();
			if (!approvedPrdCodes.contains(orderedPrdCode)) disabled.add(detail);
		}
		
		return disabled;
	}
	
	@Async
	public void autoApprove(int orderCode) {
		System.out.println("====> Auto approve task start");
		// 자동 승인 로직
		OrdersDto order = ordersDao.findByCode(orderCode);
		
		// 금액 제한 확인
		BigDecimal finalPrice = order.calculateTotalPrice();
		BigDecimal upperLimit = new BigDecimal(1000000);
		if (finalPrice.compareTo(upperLimit) == 1) {
			// 자동 승인 불가 이메일
			System.out.println("====> Auto approve FAIL : Price is too big");
			return;
		}

		// 재고 확인
		// => 원재료 발주/생산 자동신청
		List<OrdersDetailDto> disabled = notApprovedDetails(order);
		if (disabled.size() == 0) {
			// 수주 승인 상태(4)로 변경 
			int targetStatus = 4;
			order.setOrder_status(targetStatus);
			int systemUserCode = 2999;
			order.setOrders_perm_code(systemUserCode);	// system
			// 승인 당시 총액
			order.setOrder_final_price(finalPrice);
			
			ordersDao.approveOrders(order);
			
			System.out.println("====> Auto approve SUCCESS");
			// 자동 승인 완료 이메일
			try {
				ClientDto client = clientService.getSingleClient(order.getOrders_client_code());
				String targetMail = "min__37@naver.com";
				String title = "[수주 자동승인] " + client.getClient_name();
				String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); 
				String contents = now + "에 수주가 자동 승인되었습니다.\n\n"
						+ "상호명: " + client.getClient_name() + "\n"
						+ "총액: " + order.calculateTotalPrice() + "원";
				
				emailService.sendEmail(targetMail, title, contents);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			// 자동 승인 불가 이메일
			System.out.println("====> Auto approve FAIL : Stock is less than request");
		}
	}

	@Override
	public List<OrdersDetailDto> approve(int approverCode, int orderCode) {
		// 수동 승인
		OrdersDto order = ordersDao.findByCode(orderCode);
		order.setOrders_perm_code(approverCode);

		List<OrdersDetailDto> disabled = notApprovedDetails(order);
		if (disabled.size() == 0) {
			// 수주 승인 상태(4)로 변경 
			int targetStatus = 4;
			order.setOrder_status(targetStatus);
			order.setOrders_perm_code(approverCode);
			// 승인 당시 총액
			order.setOrder_final_price(order.calculateTotalPrice());
			
			ordersDao.approveOrders(order);
		}
		
		// 승인 불가 재고 return
		return disabled;
	}

	@Override
	public void refuseOrCancel(OrdersRefuseDto refuse) {
		// 수주 취소(5)/반려(3) 상태로 변경 (요청 상태에서 가능)
		// 취소(가맹점): 취소상태(4) + order_perm_code/order_refuse NULL
		// 반려(본사): 반려상태(3) + 승인자코드/거부사유 NOT NULL
		
		OrdersDto order = ordersDao.findByCode(refuse.getOrder_code());
		
		int targetStatus = refuse.getReason() == null ? 5 : 3;
		
		order.setOrder_status(targetStatus);
		order.setOrder_refuse(refuse.getReason());
		order.setOrders_perm_code(refuse.getOrder_perm_code());

		// 취소 당시 총액
		order.setOrder_final_price(order.calculateTotalPrice());
		ordersDao.refuseOrders(order);
	}

	@Override
	public void delete(int orderCode) {
		ordersDao.deleteOrdersDetails(orderCode);
		ordersDao.deleteOrders(orderCode);
	}

	@Override
	public PageRespDto<OrdersListDto, Paging> list(PageRequestDto page) {
		int totalCount = ordersDao.totalCount();
		Paging paging = new Paging(totalCount, String.valueOf(page.getPage()));
		OrdersPageDto ordersPage = new OrdersPageDto(paging.getStart(), paging.getEnd());
		List<OrdersListDto> list = ordersDao.list(ordersPage);
		
		// 확정전 예상 금액
		for (OrdersListDto order : list) {
			if (order.getOrder_status() > 1) continue;
			OrdersDto detail_order = ordersDao.findByCode(order.getOrder_code());
			order.setOrder_final_price(detail_order.calculateTotalPrice());
		}
		return new PageRespDto<OrdersListDto, Paging>(list, paging);
	}

	@Override
	public PageRespDto<OrdersListDto, Paging> list(PageRequestDto page, int clientCode) {
		int totalCount = ordersDao.totalCount(clientCode);
		Paging paging = new Paging(totalCount, String.valueOf(page.getPage()));
		OrdersPageDto ordersPage = new OrdersPageDto(paging.getStart(), paging.getEnd(), clientCode);
		List<OrdersListDto> list = ordersDao.list(ordersPage);
		
		// 확정전 예상 금액
		for (OrdersListDto order : list) {
			if (order.getOrder_status() > 1) continue;
			OrdersDto detail_order = ordersDao.findByCode(order.getOrder_code());
			order.setOrder_final_price(detail_order.calculateTotalPrice());
		}
		return new PageRespDto<OrdersListDto, Paging>(list, paging);
	}

	@Override
	public List<OrdersDetailDto> currentOrder() {
		System.out.println("OrdersServiceImpl currentOrder start...");
	
		return ordersDao.currentOrder();
	}

	@Override
	public List<OrdersDto> excellentClient() {
		System.out.println("OrdersServiceImpl excellentClient start...");
		
		return ordersDao.excellentClient();
	}

	@Override
	public List<OrdersDto> monthTotalPrice() {
		System.out.println("OrdersServiceImpl monthTotalPrice start...");
		
		return ordersDao.monthTotalPrice();
	}


}
