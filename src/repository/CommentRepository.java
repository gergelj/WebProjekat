/***********************************************************************
 * Module:  CommentRepository.java
 * Author:  Geri
 * Purpose: Defines the Class CommentRepository
 ***********************************************************************/

package repository;

import java.util.*;

import beans.Comment;
import repository.abstractrepository.ICommentRepository;
import repository.csv.CSVRepository;
import repository.csv.IEagerCsvRepository;
import repository.csv.stream.ICsvStream;
import repository.sequencer.LongSequencer;

public class CommentRepository extends CSVRepository<Comment> implements ICommentRepository, IEagerCsvRepository<Comment> {
   
	public CommentRepository(ICsvStream<Comment> stream, LongSequencer sequencer) {
		super("Comment", stream, sequencer);
	}
   
   private void bind() {
      // TODO: implement
   }
   
   public Comment getEager(long id) {
      // TODO: implement
      return null;
   }
   
   public List<Comment> getAllEager() {
      // TODO: implement
      return null;
   }

}