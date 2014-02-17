/**
  	This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.voa.weixin.message.send;

import java.util.ArrayList;
import java.util.List;

import com.voa.weixin.message.MessageType;
import com.voa.weixin.message.recieve.RecieveMessage;
import com.voa.weixin.model.Article;

/**
 * 对应图文混排消息
 * @author zhaiyuxin
 *
 */
public class SendTxtAndImgMessage extends SendMessage {

	private List<Article> articles;

	public SendTxtAndImgMessage(RecieveMessage recieveMessage) {
		super(recieveMessage);
		this.msgType = MessageType.TYPE_NEWS;
		this.articles = new ArrayList<Article>();
	}

	@Override
	public String toXml() {
		StringBuffer sb = new StringBuffer();
		sb.append("<xml><ToUserName><![CDATA[");
		sb.append(this.toUserName);
		sb.append("]]></ToUserName>");
		sb.append("<FromUserName><![CDATA[");
		sb.append(this.fromUserName);
		sb.append("]]></FromUserName>");
		sb.append("<CreateTime>");
		sb.append(this.createTime.getTime() / 1000);
		sb.append("</CreateTime>");
		sb.append("<MsgType><![CDATA[news]]></MsgType>");
		sb.append("<ArticleCount>").append(this.articles.size()).append(
				"</ArticleCount>");
		sb.append("<Articles>");
		for (Article article : articles) {
			sb.append("<item><Title><![CDATA[");
			sb.append(article.getTitle());
			sb.append("]]></Title> ");
			sb.append("<Description><![CDATA[");
			sb.append(article.getDescription());
			sb.append("]]></Description>");
			sb.append("<PicUrl><![CDATA[");
			sb.append(article.getPicurl());
			sb.append("]]></PicUrl>");
			sb.append("<Url><![CDATA[");
			sb.append(article.getUrl());
			sb.append("]]></Url></item>");
		}
		sb.append("</Articles>");
		sb.append("</xml>");
		return null;
	}

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

}
