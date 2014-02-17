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
package com.voa.weixin.work;

import java.util.Date;
import java.util.UUID;

import com.voa.weixin.model.UpdateFile;
import com.voa.weixin.service.UpdateFileService;
import com.voa.weixin.task.UpdateFileTask;
import com.voa.weixin.task.Work;
import com.voa.weixin.task.WorkException;

/**
 * 上传文件的回调工作
 * @author zhaiyuxin
 *
 */
public class UpdateFileWork extends Work {

	@Override
	public void failedToDo() throws WorkException {

	}

	@Override
	public void toDo() throws WorkException {
		UpdateFileTask fileTask = (UpdateFileTask) task;

		String filePath = fileTask.getUpdateFile().getAbsolutePath();
		Date createAt = new Date(weixinResult.getLong("created_at") * 1000);
		String mediaId = weixinResult.getString("media_id");
		String type = weixinResult.getString("type");

		UpdateFile file = new UpdateFile();
		file.setCreateAt(createAt);
		file.setFid(UUID.randomUUID().toString());
		file.setFtype(type);
		file.setMediaId(mediaId);
		file.setFilePath(filePath);

		UpdateFileService service = new UpdateFileService();
		service.save(file);
	}
}
