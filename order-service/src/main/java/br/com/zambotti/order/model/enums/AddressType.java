package br.com.zambotti.order.model.enums;

public enum AddressType {

	RESIDENCIAL(1, "Residencial"),
	COMERCIAL(2, "Comercial"),
	ENTREGA(3, "Entrega"),
	OUTROS(4, "Outros");

	private int code;

	private String description;

	private AddressType(int code, String description) {

		this.code = code;

		this.description = description;

	}

	public int getCode() {

		return code;

	}

	public String getDescription() {

		return description;

	}

	public static AddressType toEnum(Integer code) {

		if (code == null) {

			return null;

		}

		for (AddressType addressType : AddressType.values()) {

			if (code.equals(Integer.valueOf(addressType.getCode()))) {

				return addressType;

			}

		}

		throw new IllegalArgumentException("Id inválido: " + code);

	}

}
