/***********************************************************************
 * Module:  ISequencer.java
 * Author:  Geri
 * Purpose: Defines the Interface ISequencer
 ***********************************************************************/

package repository.sequencer;

import java.util.*;

public interface ISequencer <T> {
   void initialize(T initId);
   T generateId();

}