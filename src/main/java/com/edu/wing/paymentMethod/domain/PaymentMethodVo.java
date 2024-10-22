package com.edu.wing.paymentMethod.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class PaymentMethodVo {

  @Id
  private int paymentMethodNo;
  private String paymentMethodName;
}
