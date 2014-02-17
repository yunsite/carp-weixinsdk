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

import java.io.File;
import java.io.FileOutputStream;

import org.apache.commons.io.IOUtils;

import com.voa.weixin.task.DownloadFileTask;
import com.voa.weixin.task.Work;
import com.voa.weixin.task.WorkException;

/**
 * 下载文件回调工作
 * @author zhaiyuxin
 *
 */
public class DownloadFileWork extends Work {

	@Override
	public void failedToDo() throws WorkException {
		// TODO Auto-generated method stub

	}

	@Override
	public void toDo() throws WorkException {
		try {
			DownloadFileTask task = (DownloadFileTask) this.task;
			byte[] bs = IOUtils.toByteArray(task.getInputStream());

			FileOutputStream out = new FileOutputStream(new File("c:/tt.jpg"));
			IOUtils.write(bs, out);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
