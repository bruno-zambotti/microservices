package br.com.zambotti.order.model.enums;

public enum OrderStatus {

	NOVO(0, "NOVO"),
	AGUARDANDO_PAGAMENTO(1, "AGUARDANDO PAGAMENTO"),
	EM_SEPARACAO(2, "EM SEPARAÇÃO"),
	EM_DESLOCAMENTO(3, "EM DESLOCAMENTO"),
	ENTREGUE(4, "ENTREGUE");

	private int code;

	private String description;

	private OrderStatus(int code, String description) {

		this.code = code;

		this.description = description;

	}

	public int getCode() {

		return code;

	}

	public String getDescription() {

		return description;

	}

	public static OrderStatus toEnum(Integer code) {

		if (code == null) {

			return null;

		}

		for (OrderStatus sales : OrderStatus.values()) {

			if (code.equals(Integer.valueOf(sales.getCode()))) {

				return sales;

			}

		}

		throw new IllegalArgumentException("Id inválido: " + code);

	}

}
