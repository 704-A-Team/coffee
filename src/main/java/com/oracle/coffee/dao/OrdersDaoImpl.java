package com.oracle.coffee.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.coffee.dto.orders.OrdersDetailDto;
import com.oracle.coffee.dto.orders.OrdersDto;
import com.oracle.coffee.dto.orders.OrdersListDto;
import com.oracle.coffee.dto.orders.OrdersPageDto;
import com.oracle.coffee.dto.orders.OrdersProductDto;
import com.oracle.coffee.dto.orders.OrdersRefuseDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrdersDaoImpl implements OrdersDao {
	
	private final SqlSession session;
	
	@Override
	public List<OrdersProductDto> getProducts() {
		List<OrdersProductDto> products = null;
		
		try {
			products = session.selectList("selectOrdersProducts");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return products;
	}

	@Override
	public void createOrders(OrdersDto order) {
		try {
			session.insert("insertOrders", order);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void createOrdersDetails(List<OrdersDetailDto> ordersdetails) {
		try {
			session.insert("insertAllOrdersDetails", ordersdetails);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public OrdersDto findByCode(int orderCode) {
		OrdersDto order = null;
		
		try {
			order = session.selectOne("selectOrderByCode", orderCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return order;
	}

	@Override
	public void updateOrders(OrdersDto order) {
		try {
			session.update("updateOrders", order);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void updateOrdersFinalPrice(OrdersDto order) {
		try {
			session.update("updateOrdersFinalPrice", order);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteOrdersDetails(int orderCode) {
		try {
			session.delete("deleteAllOrdersDetails", orderCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void requestOrders(OrdersDto order) {
		try {
			session.update("requestOrders", order);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteOrders(int orderCode) {
		try {
			session.update("deleteOrders", orderCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int totalCount() {
		int count = 0;

		try {
			count = session.selectOne("totalCountOrders");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int totalCount(int clientCode) {
		int count = 0;

		try {
			count = session.selectOne("totalCountOrdersByClient", clientCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public List<OrdersListDto> list(OrdersPageDto page) {
		// mybatis에서 parameter를 1개만 받을 수 있기 때문에 OrdersPageDto 사용 => 메서드 오버로딩 불가
		
		List<OrdersListDto> list = null;
		
		try {
			if (page.getClient_code() == 0) list = session.selectList("listOrders", page);
			else list = session.selectList("listOrdersByClient", page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public void refuseOrders(OrdersDto order) {
		try {
			session.update("refuseOrders", order);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
