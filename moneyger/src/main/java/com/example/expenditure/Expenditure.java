package com.example.expenditure;

import am.ik.yavi.builder.ValidatorBuilder;
import am.ik.yavi.core.ConstraintViolations;
import am.ik.yavi.core.Validator;
import am.ik.yavi.fn.Either;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.time.LocalDate;

@JsonDeserialize(builder = ExpenditureBuilder.class)
public class Expenditure {

	private final Integer expenditureId;

	private final String expenditureName;

	private final int unitPrice;

	private final int quantity;

	private final LocalDate expenditureDate;

	private static Validator<Expenditure> validator = ValidatorBuilder.of(Expenditure.class)
			.constraint(Expenditure::getExpenditureId, "expenditureId", c -> c.isNull())
			// "expenditureName"は空ではなく、文字数は255以下
			.constraint(Expenditure::getExpenditureName, "expenditureName", c -> c.lessThan(255))
			.constraint(Expenditure::getExpenditureName, "expenditureName", c -> c.notEmpty())
			// "unitPrice"は0より大きい
			.constraint(Expenditure::getUnitPrice, "unitPrice", c -> c.greaterThan(0))
			// "quantity"は0より大きい
			.constraint(Expenditure::getQuantity, "quantity", c -> c.greaterThan(0))
			// .constraint(...)
			.constraintOnObject(Expenditure::getExpenditureDate, "expenditureDate", c -> c.notNull())
			.build();

	Expenditure(Integer expenditureId, String expenditureName, int unitPrice, int quantity, LocalDate expenditureDate) {
		this.expenditureId = expenditureId;
		this.expenditureName = expenditureName;
		this.unitPrice = unitPrice;
		this.quantity = quantity;
		this.expenditureDate = expenditureDate;
	}

	public Integer getExpenditureId() {
		return expenditureId;
	}


	public String getExpenditureName() {
		return expenditureName;
	}


	public int getUnitPrice() {
		return unitPrice;
	}


	public int getQuantity() {
		return quantity;
	}


	public LocalDate getExpenditureDate() {
		return expenditureDate;
	}

    public Either<ConstraintViolations, Expenditure> validate() {
        return validator.validateToEither(this);
    }

	@Override
	public String toString() {
		return "Expenditure{" +
				"expenditureId=" + expenditureId +
				", expenditureName='" + expenditureName + '\'' +
				", unitPrice=" + unitPrice +
				", quantity=" + quantity +
				", expenditureDate=" + expenditureDate +
				'}';
	}
}
