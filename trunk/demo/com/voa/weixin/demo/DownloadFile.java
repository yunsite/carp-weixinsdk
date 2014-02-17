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
package com.voa.weixin.demo;

import java.io.File;
import java.util.Enumeration;
import java.util.Properties;

import com.voa.weixin.Carp;
import com.voa.weixin.task.DownloadFileTask;

public class DownloadFile {

	public static void main(String[] args) throws Exception {
		
		Carp.ROOTPATH = System.getProperty("user.dir")+File.separator;
		Carp carp = Carp.getInstance();
		carp.init();

		DownloadFileTask task = (DownloadFileTask) carp.getTaskRepertory()
				.getTaskByName("downloadfile");
		//mediaId,需要根据weixin服务器传回的数据填写，可先执行UpdateFile，在数据库中查找mediaId
		task
				.setMediaId("wfvPPnhChKK00uTf_PzadtQ6C2w1H6XKjFV7pY68jbKB3DxTsINAgILfn3bs_NxO");

		task.send();
		
		carp.destroy();
	}
}
