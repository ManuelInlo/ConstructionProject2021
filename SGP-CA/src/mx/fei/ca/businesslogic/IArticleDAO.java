
package mx.fei.ca.businesslogic;

import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.Article;

public interface IArticleDAO {
    public boolean saveArticle (Article article) throws BusinessConnectionException;    
    public boolean updateArticle (Article article, String ISSN) throws BusinessConnectionException;    
}
