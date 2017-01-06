package jcapax.jdbc.dao;

import java.util.List;

public interface Dao <T, K>{
	void insert(T a);
	void delete(T a);
	void update(T a);
	
	List<T> listAll();
	T listOne(Long K);
}
