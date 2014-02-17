/**
  	This program is free softwaimport java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
   the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.voa.weixin.model;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * @author zhaiyuxin
 *
 */
public class ImgAndTextMessage extends Message {

	private List<Article> articles = new ArrayList<Article>();

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

	public List<Article> getArticles() {
		return articles;
	}

	public void addArticle(Article article) {
		this.articles.add(article);
	}

	@Override
	public String toJson() {
		JSONArray jsonArticles = new JSONArray();
		for (Article article : articles) {
			JSONObject jsonArticle = new JSONObject();
			jsonArticle.element("title", article.getTitle());
			jsonArticle.element("description", article.getDescription());
			jsonArticle.element("url", article.getUrl());
			jsonArticle.element("picurl", article.getPicurl());

			jsonArticles.add(jsonArticle);
		}

		JSONObject articlesJson = new JSONObject();
		articlesJson.element("articles", jsonArticles);

		JSONObject jsonObj = new JSONObject();
		jsonObj.element("touser", touser);
		jsonObj.element("msgtype", "news");
		jsonObj.element("news", articlesJson);

		return jsonObj.toString();
	}
}
