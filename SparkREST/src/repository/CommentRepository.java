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
import repository.sequencer.ISequencer;

public class CommentRepository extends CSVRepository<Comment,Long> implements ICommentRepository, IEagerCsvRepository<Comment,Long> {
   
	public CommentRepository(ICsvStream<Comment> stream, ISequencer<Long> sequencer) {
		super("Comment", stream, sequencer);
	}
   
   private void bind() {
      // TODO: implement
   }
   
   public Comment getEager(Long id) {
      // TODO: implement
      return null;
   }
   
   public List<Comment> getAllEager() {
      // TODO: implement
      return null;
   }

}