/***********************************************************************
 * Module:  ISequencer.java
 * Author:  Geri
 * Purpose: Defines the Interface ISequencer
 ***********************************************************************/

package repository.sequencer;

import java.util.*;

public interface ISequencer <T> {
   /** @param initId */
   void initialize(T initId);
   T generateId();

}