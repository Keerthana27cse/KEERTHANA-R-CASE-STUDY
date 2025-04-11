package com.java.AssetManagement.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Reservations {
      private int reservationId;
      private int assetId;
      private int employeeId;
      private Date reservationDate;
      private Date startDate;
      private Date endDate;
      private ReservationStatus reservationStatus;
}
