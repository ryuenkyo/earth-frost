/**
The MIT License (MIT) * Copyright (c) 2016 铭飞科技(mingsoft.net)

 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:

 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.mingsoft.cms.biz.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mingsoft.base.dao.IBaseDao;
import com.mingsoft.basic.biz.ICategoryBiz;
import com.mingsoft.basic.biz.IColumnBiz;
import com.mingsoft.basic.biz.IModelBiz;
import com.mingsoft.basic.biz.impl.BasicBizImpl;
import com.mingsoft.cms.biz.IArticleBiz;
import com.mingsoft.cms.dao.IArticleDao;
import com.mingsoft.cms.entity.ArticleEntity;
import com.mingsoft.mdiy.biz.IContentModelBiz;
import com.mingsoft.mdiy.entity.ContentModelEntity;
import com.mingsoft.util.PageUtil;

import net.mingsoft.basic.util.BasicUtil;

/**
 * 
 * @ClassName:  ArticleBizImpl   
 * @Description:TODO(文章管理业务层实现类 || 继承BasicBizImpl || 实现IArticleBiz)   
 * @author: 铭飞开发团队
 * @date:   2018年1月31日 下午2:53:53   
 *     
 * @Copyright: 2018 www.mingsoft.net Inc. All rights reserved.
 */
@Service("ArticleBizImpl")
public class ArticleBizImpl extends BasicBizImpl implements IArticleBiz {

	
	/**
	 * 文章持久化处理
	 */
	private IArticleDao articleDao;
	
	

	/**
	 * 栏目业务处理
	 */
	@Autowired
	private ICategoryBiz categoryBiz; 

	/**
	 * 自定类型义业务处理
	 */
	@Autowired
	private IColumnBiz columnBiz;
	
	/**
	 * 自定义模型
	 */
	@Autowired
	private IContentModelBiz contentModelBiz;
	
	/**
	 * 模块管理业务层
	 */
	@Autowired
	private IModelBiz modelBiz;

	@Override
	@Deprecated
	public int count(int webId, int[] basicCategoryId, String flag, String noFlag,ArticleEntity article) {
		return articleDao.count(webId, basicCategoryId, flag, noFlag,article);
	}


	/**
	 * 获取Article的持久化层
	 * 
	 * @return 返回持Article的久化对象
	 */
	public IArticleDao getArticleDao() {
		return articleDao;
	}

	@Override
	@Deprecated
	public ArticleEntity getByCategoryId(int categoryId) {
		// TODO Auto-generated method stub
		List list = articleDao.getByCategoryId(categoryId);
		if (list != null && list.size() > 0) {
			return (ArticleEntity) list.get(0);
		}
		return null;
	}

	@Override
	public ArticleEntity getById(int basicId) {
		// TODO Auto-generated method stub
		ArticleEntity article = (ArticleEntity) articleDao.getEntity(basicId);
		String contentModelTableName = null;
		int ccmi = article.getColumn().getColumnContentModelId(); // 内容模型编号
		if (ccmi > 0) {
			ContentModelEntity contentModel = (ContentModelEntity) contentModelBiz.getEntity(ccmi);
			contentModelTableName = contentModel.getCmTableName();
		}
		List temp = articleDao.getById(basicId, contentModelTableName);
		if (temp != null && temp.size() > 0) {
			return (ArticleEntity) temp.get(0);
		}
		return null;
	}



	/**
	 * 获取IBaseDao的持久化层
	 * 
	 * @return 返回持articleDao的久化对象
	 */
	@Override
	protected IBaseDao getDao() {
		// TODO Auto-generated method stub
		return articleDao;
	}

	@Override
	@Deprecated
	public ArticleEntity getNext(int appId, int basicId,Integer categoryId) {
		// TODO Auto-generated method stub
		return articleDao.getNextOrPrevious(appId, basicId, true,categoryId);
	}

	@Override
	@Deprecated
	public ArticleEntity getPrevious(int appId, int basicId,Integer categoryId) {
		// TODO Auto-generated method stub
		return articleDao.getNextOrPrevious(appId, basicId, false,categoryId);
	}
	@Override
	@Deprecated
	public int getSearchCount(ContentModelEntity contentModel,Map wherMap, int websiteId,List  ids) {
		if (contentModel!=null) {
			return articleDao.getSearchCount(contentModel.getCmTableName(),wherMap, websiteId,ids);
		}
		return articleDao.getSearchCount(null,wherMap, websiteId,ids);
	}

	@Override
	public List<ArticleEntity> query(int webId, int[] basicCategoryIds, String flag, String noFlag, String orderBy,
			boolean order,String beginTime,String endTime, ArticleEntity article) {
		// TODO Auto-generated method stub
		if(article == null) {
			article = new ArticleEntity();
		}
		return articleDao.query(webId, basicCategoryIds, flag, noFlag, orderBy, order, beginTime, endTime, article);
	}

	/**
	 * 根据页面栏目的id获取与其绑定的文章实体
	 * 
	 * @param basicCategoryId
	 * @return 文章实体
	 */
	@Override
	@Deprecated
	public List<ArticleEntity> queryListByColumnId(int basicCategoryId) {
		// TODO Auto-generated method stub
		return articleDao.queryListByColumnId(basicCategoryId);
	}


	@Deprecated
	public List<ArticleEntity> queryListForSearch(ContentModelEntity conntentModel, Map whereMap, PageUtil page, int websiteId,List ids,Map orders) {
		List<ArticleEntity> articleList = new ArrayList<ArticleEntity>();
		String tableName = null;
		if (conntentModel!=null) {
			tableName = conntentModel.getCmTableName();
		}
		// 查找所有符合条件的文章实体
		articleList = articleDao.queryListForSearch(tableName, whereMap, page, websiteId,ids,orders);
		return articleList;
	}

	/**
	 * 设置Article的持久化层
	 * 
	 * @param articleDao
	 */
	@Autowired
	public void setArticleDao(IArticleDao articleDao) {
		this.articleDao = articleDao;
	}


	@Override
	public List<Integer> queryIdsByCategoryId(int categoryId, String beginTime,String endTime) {
		return this.articleDao.queryIdsByCategoryId(categoryId,BasicUtil.getAppId(), beginTime, endTime);
	}
}