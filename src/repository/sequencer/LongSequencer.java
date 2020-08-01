/***********************************************************************
 * Module:  LongSequencer.java
 * Author:  Geri
 * Purpose: Defines the Class LongSequencer
 ***********************************************************************/

package repository.sequencer;

public class LongSequencer {
   private long nextId;
   

   public void initialize(long initId) {
      nextId = initId;
   }
   
   public long generateId() {
      return ++nextId;
   }

}