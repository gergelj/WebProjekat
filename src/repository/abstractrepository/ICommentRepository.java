/***********************************************************************
 * Module:  ICommentRepository.java
 * Author:  Geri
 * Purpose: Defines the Interface ICommentRepository
 ***********************************************************************/

package repository.abstractrepository;

import java.util.List;

import beans.Comment;
import exceptions.DatabaseException;

public interface ICommentRepository extends IRepository<Comment> {
	
	List<Comment> getCommentsByApartment(long apartmentId) throws DatabaseException;
}