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

import com.voa.weixin.Carp;
import com.voa.weixin.task.Task;

/**
 * 获得公众号所有的关注用户
 * @author zhaiyuxin
 *
 */
public class GetAllFans {

	public static void main(String[] args)throws Exception{
		
		Carp.ROOTPATH = System.getProperty("user.dir")+File.separator;
		Carp carp = Carp.getInstance();
		carp.init();
		
		Task task = carp.getTaskRepertory().getTaskByName("fans");

		task.send();
		
		carp.destroy();
	}
	
}
