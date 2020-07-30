/***********************************************************************
 * Module:  IReservationRepository.java
 * Author:  Geri
 * Purpose: Defines the Interface IReservationRepository
 ***********************************************************************/

package repository.abstractrepository;

import java.util.*;

import beans.Reservation;

public interface IReservationRepository extends IRepository<Reservation,Long> {
}